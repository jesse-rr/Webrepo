import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataUnload {
    private static final Random random = new Random();
    private static final Map<String, String> PHONE_FORMATS = loadPhoneFormats("phone.txt");

    private static Map<String, String> loadPhoneFormats(String path) {
        try {
            return Files.readAllLines(Paths.get("src/main/java/" + path))
                    .stream()
                    .filter(line -> !line.isEmpty() && line.contains("|"))
                    .collect(Collectors.toMap(
                            line -> line.split("\\|")[0],
                            line -> line.split("\\|")[1]
                    ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load phone formats", e);
        }
    }

    public static CountryBuilder country() {
        return new CountryBuilder();
    }

    public static class CountryBuilder {
        private String isoCode;

        public CountryBuilder iso(String isoCode) {
            this.isoCode = isoCode.toUpperCase();
            if (!PHONE_FORMATS.containsKey(this.isoCode)) {
                throw new IllegalArgumentException("Invalid ISO country code: " + isoCode);
            }
            return this;
        }

        public String get() {
            if (isoCode == null) {
                List<String> keys = new ArrayList<>(PHONE_FORMATS.keySet());
                isoCode = keys.get(random.nextInt(keys.size()));
            }
            return PHONE_FORMATS.get(isoCode);
        }

        public String name() {
            if (isoCode == null) {
                List<String> keys = new ArrayList<>(PHONE_FORMATS.keySet());
                isoCode = keys.get(random.nextInt(keys.size()));
            }
            return new Locale("", isoCode).getDisplayCountry();
        }
    }

    public static String randomCountryIso() {
        List<String> keys = new ArrayList<>(PHONE_FORMATS.keySet());
        return keys.get(random.nextInt(keys.size()));
    }

    public static String randomPhoneFormat() {
        return country().get();
    }

    public static String getPhoneFormat(String isoCode) {
        return country().iso(isoCode).get();
    }

    public static void main(String[] args) {
        System.out.println("Random country ISO: " + randomCountryIso());
        System.out.println("Random phone format: " + randomPhoneFormat());
        System.out.println("US phone format: " + getPhoneFormat("US"));
        System.out.println("France info: " + country().iso("FR").name() + " - " + country().iso("FR").get());
    }
}