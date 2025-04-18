import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DataUnloadTest {
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");


    @Test
    void testIPS_NotPrivate() {
        for (int i = 0; i < 1000; i++) {
            String ip = DataUnload.ip();
//            System.out.printf("RESULT [%s]: %s",i,ip+"\n");
            assertFalse(isPrivateIP(ip), () -> "GENERATED IP IS PRIVATE :: "+ip);
            assertTrue(IPV4_PATTERN.matcher(ip).matches(), () -> "INVALID IP FORMAT :: "+ip);
        }
    }

    @Test
    void testEmail_ValidFormat() {
        String result = DataUnload.email();
        assertTrue(result.contains("@") && !result.contains(" "));
    }

    @Test
    void testNames_UppercaseAndLowercase() {
        String[] results = {DataUnload.firstName(), DataUnload.lastName(), DataUnload.fullName()};
        for (String result : results) {
            assertNotNull(result);
            assertFalse(result.trim().isEmpty());
            assertTrue(Character.isUpperCase(result.charAt(0)));
        }
    }

    @Test
    void testCountry_ValidFormat() {
        for (int i = 0; i <= 10; i++) {
            String result = DataUnload.country();
//            System.out.printf("RESULT [%s]: %s",i,result+"\n");
            assertNotNull(result);
            assertFalse(result.trim().isEmpty());
        }
    }

    private boolean isPrivateIP(String ip) {
        String[] octets = ip.split("\\.");
        int first = Integer.parseInt(octets[0]);
        int second = Integer.parseInt(octets[1]);

        return first == 10 ||                                      // 10.0.0.0/8
                (first == 172 && second >= 16 && second <= 31) ||  // 172.16.0.0/12
                (first == 192 && second == 168) ||                 // 192.168.0.0/16
                (first == 169 && second == 254);                   // 169.254.0.0/16
    }
}