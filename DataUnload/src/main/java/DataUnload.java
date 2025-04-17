import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.stream.Collectors;

public class DataUnload {
    private static final Random random = new Random();

    private static List<String> firstNames = readFile("firstname.txt");
    private static List<String> lastNames = readFile("lastname.txt");
    private static List<String> emailProviders = readFile("email_providers.txt");
    private static List<String> streets = readFile("street.txt");
    private static List<String> countries = readFile("country.txt");
    private static List<String> verbs = readFile("verbs.txt");
    private static final Map<String, String> phones = loadPhoneFormats("phone.txt");
    private static final Map<String, String> postals = loadPhoneFormats("cep.txt");
    private static final List<Currency> currencies = new ArrayList<>(Currency.getAvailableCurrencies());

    private static List<String> readFile(String filename) {
        try {
            return Files.readAllLines(Paths.get("src/main/java/" + filename));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filename, e);
        }
    }

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

    private static String getRandomParam(List<String> params) {
        if (params == null || params.isEmpty()) {
            throw new RuntimeException("Parameter list is empty!");
        }
        return params.get(random.nextInt(params.size()));
    }

    public static String firstname() {
        return getRandomParam(firstNames);
    }

    public static String lastname() {
        return getRandomParam(lastNames);
    }

    public static String fullName() {
        return firstname() + " " + lastname();
    }

    public static String email() {
        return (firstname().toLowerCase() + lastname().toLowerCase() + "@" + getRandomParam(emailProviders)).replaceAll("\\s+", "");
    }

    public static String domain() {
        return "www." + getRandomParam(verbs)+getRandomParam(verbs) + ".com";
    }

    public static String url() {
        String[] protocols = {"http://", "https://"};
        return protocols[random.nextInt(protocols.length)] + domain();
    }

    public static String street() {
        String street = getRandomParam(streets);
        return street.toUpperCase().charAt(0) + street.substring(1).toLowerCase();
    }

    public static String country() {
        return getRandomParam(countries);
    }

    public static String iso() {
        List<String> keys = new ArrayList<>(phones.keySet());
        return keys.get(random.nextInt(keys.size()));
    }

    // TODO its hard
    public static String phone() {
        List<String> phoneFormats = new ArrayList<>(phones.values());
        return getRandomParam(phoneFormats);
    }

    public static String phone(String iso) {
        if (!phones.containsKey(iso)) {
            throw new IllegalArgumentException("No phone format found for ISO code: " + iso);
        }
        return phones.get(iso);
    }

    public static String currency() {
        return currencies.get(random.nextInt(currencies.size())).getCurrencyCode();
    }

    public static BigDecimal price() {
        double value = 0.01 + (1000 - 0.01) * random.nextDouble();
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal price(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        double value = min + (max - min) * random.nextDouble();
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    public class date {
        public static LocalDateTime past() {
            LocalDateTime now = LocalDateTime.now();
            return LocalDateTime.from(now.minusDays(random.nextInt(1, 365)).atZone(ZoneId.systemDefault()));
        }

        public static LocalDateTime future() {
            return LocalDateTime.now().plusDays(random.nextInt(1, 365));
        }

        public static LocalDateTime between(LocalDateTime min, LocalDateTime max) {
            return Instant.ofEpochMilli(min.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + (long) (random.nextDouble() * (max.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - min.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
    }

    public class postal {
        static List<String> values = new ArrayList<>(postals.values());
        public static String format() {
            return getRandomParam(values);
        }
        public static String value() {
            String format = format();
            for (int i = 0; i < format.length(); i++) {
                format.replace('X', (char) random.nextInt());
            }
            return format;
        }
    }

    public static void main(String[] args) {
        System.out.println(firstname());
        System.out.println(lastname());
        System.out.println(fullName());
        System.out.println(email());
        System.out.println(phone());
        System.out.println(domain());
        System.out.println(url());
        System.out.println(street());
        System.out.println(country());
        System.out.println(iso());
//        System.out.println(phone());
//        System.out.println(phone("BR")); // TODO FIX
        System.out.println(currency());
        System.out.println(price());
        System.out.println(price(12.00, 30.12));
        System.out.println(date.past());
        System.out.println(date.future());
        System.out.println(DataUnload.date.between(date.past(), date.future()));
        System.out.println(postal.format());
        System.out.println(postal.value());
    }
}