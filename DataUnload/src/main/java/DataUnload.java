import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class DataUnload {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private static final List<String> FIRST_NAMES = readFile("firstname.txt");
    private static final List<String> LAST_NAMES = readFile("lastname.txt");
    private static final List<String> GENRES = List.of("Male", "Female", "Other", "Undefined");
    private static final List<String> EMAIL_PROVIDERS = readFile("email_provider.txt");
    private static final List<String> STREETS = readFile("street.txt");
    private static final List<String> CITIES = readFile("city.txt");
    private static final List<String> VERBS = readFile("verb.txt");
    private static final Map<String, String> PHONES = loadIntoMap("iso_phone.txt");
    private static final Map<String, String> POSTAL = loadIntoMap("iso_postal.txt");
    private static final String[][] PUBLIC_IPS = {
            {"1.0.0.0", "9.255.255.255"},
            {"11.0.0.0", "172.15.255.255"},
            {"172.32.0.0", "192.167.255.255"},
            {"192.169.0.0", "223.255.255.255"}
    };
    private static final String[] TIME_ZONES = TimeZone.getAvailableIDs();
    private static final List<Currency> CURRENCIES = List.copyOf(Currency.getAvailableCurrencies());
    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private static final String[] ALL_COUNTRY_NAMES = Arrays.stream(COUNTRY_CODES)
            .map(code -> new Locale("", code).getDisplayCountry(Locale.ENGLISH))
            .toArray(String[]::new);

    private DataUnload() {
        throw new AssertionError("Cannot instantiate static utility class");
    }

    // ===== NAME GENERATORS =====
    public static String firstName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()));
    }

    public static String lastName() {
        return LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()));
    }

    public static String fullName() {
        return firstName() + " " + lastName();
    }

    public static String genre() {
        return GENRES.get(RANDOM.nextInt(GENRES.size()));
    }

    public static int age(int min, int max) {
        return RANDOM.nextInt(min, max);
    }

    public static int age(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static LocalDate birthDate() {
        return LocalDate.now().minusYears(RANDOM.nextInt(100))
                .minusMonths(RANDOM.nextInt(12))
                .minusDays(RANDOM.nextInt(28));
    }

    // ===== ADDRESS GENERATORS =====
    public static String street() {
        String street = STREETS.get(RANDOM.nextInt(STREETS.size()));
        return Character.toUpperCase(street.charAt(0)) + street.substring(1).toLowerCase();
    }

    public static String city() {
        return CITIES.get(RANDOM.nextInt(CITIES.size()));
    }

    public static String country() {
        return ALL_COUNTRY_NAMES[RANDOM.nextInt(ALL_COUNTRY_NAMES.length)];
    }

    public static String iso() {
        return COUNTRY_CODES[RANDOM.nextInt(COUNTRY_CODES.length)];
    }

    public static String timeZone() {
        return TIME_ZONES[RANDOM.nextInt(TIME_ZONES.length)];
    }

    public static String postal() {
        String iso = COUNTRY_CODES[RANDOM.nextInt(COUNTRY_CODES.length)];
        return generateFromFormat(POSTAL.get(iso), iso);
    }

    public static String postal(String iso) {
        String format = POSTAL.get(iso.toUpperCase());
        if (format == null) {
            throw new IllegalArgumentException("No postal format for ISO: " + iso);
        }
        return generateFromFormat(format, iso);
    }

    // ===== PHONE GENERATORS =====
    public static String phone() {
        return phone(new ArrayList<>(PHONES.keySet()).get(RANDOM.nextInt(PHONES.size())));
    }

    public static String phone(String iso) {
        String format = PHONES.get(iso);
        if (format == null) throw new IllegalArgumentException("No phone format for ISO: " + iso);
        return generateFromFormat(format, 'X');
    }

    // ===== DATE/TIME GENERATORS =====
    public static LocalDateTime past() {
        return LocalDateTime.now().minusDays(RANDOM.nextInt(365));
    }

    public static LocalDateTime future() {
        return LocalDateTime.now().plusDays(RANDOM.nextInt(365));
    }

    public static LocalDateTime between(LocalDateTime start, LocalDateTime end) {
        long startEpoch = start.atZone(ZONE_ID).toInstant().toEpochMilli();
        long endEpoch = end.atZone(ZONE_ID).toInstant().toEpochMilli();
        long randomEpoch = startEpoch + (long) (RANDOM.nextDouble() * (endEpoch - startEpoch));
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(randomEpoch), ZONE_ID);
    }

    // ===== INTERNET GENERATORS =====
    public static String email() {
        return (firstName().toLowerCase() + lastName().toLowerCase() +
                "@" + EMAIL_PROVIDERS.get(RANDOM.nextInt(EMAIL_PROVIDERS.size())))
                .replaceAll("\\s+", "");
    }

    public static String domain() {
        return VERBS.get(RANDOM.nextInt(VERBS.size())) +
                VERBS.get(RANDOM.nextInt(VERBS.size())) + ".com";
    }

    public static String url() {
        return (RANDOM.nextBoolean() ? "https://" : "http://") + domain();
    }

    // DOES NOT GENERATE PRIVATE IPS
    public static String ip() {
        String[] range = PUBLIC_IPS[RANDOM.nextInt(PUBLIC_IPS.length)];
        return generateIpInRange(range[0], range[1]);
    }

    // ===== PRICE GENERATORS =====
    public static double price() {
        return 0.99 + RANDOM.nextDouble(1000);
    }

    public static String currency() {
        return CURRENCIES.get(RANDOM.nextInt(CURRENCIES.size())).getCurrencyCode();
    }

    public static String credit_card() {
        return generateFromFormat("XXXX-XXXX-XXXX-XXXX", 'X');
    }

    // ===== HELPER METHODS =====
    private static String generateFromFormat(String format, char placeholder) {
        StringBuilder sb = new StringBuilder();
        for (char c : format.toCharArray()) {
            sb.append(c == placeholder ? RANDOM.nextInt(10) : c);
        }
        return sb.toString();
    }

    private static String generateFromFormat(String format, String iso) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            switch (c) {
                case 'N':
                    sb.append(RANDOM.nextInt(10));
                    break;
                case 'A':
                    sb.append((char) ('A' + RANDOM.nextInt(26)));
                    break;
                case '?':
                    sb.append(RANDOM.nextBoolean() ? (char) ('A' + RANDOM.nextInt(26)) : RANDOM.nextInt(10));
                    break;
                case 'C':
                    if (i + 1 < format.length() && format.charAt(i + 1) == 'C') {
                        sb.append(iso);
                        i++;
                    } else { sb.append(c); }
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    private static String generateIpInRange(String range1, String range2) {
        int[] start = Arrays.stream(range1.split("\\.")).mapToInt(Integer::parseInt).toArray();
        int[] end = Arrays.stream(range2.split("\\.")).mapToInt(Integer::parseInt).toArray();
        return RANDOM.nextInt(start[0], end[0] + 1) + "." +
                RANDOM.nextInt(start[1], end[1] + 1) + "." +
                RANDOM.nextInt(start[2], end[2] + 1) + "." +
                RANDOM.nextInt(start[3], end[3] + 1);
    }

    // ===== FILE LOADING =====
    private static List<String> readFile(String filename) {
        try {
            return Files.readAllLines(Paths.get("src/main/java/data/" + filename));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filename, e);
        }
    }

    private static Map<String, String> loadIntoMap(String path) {
        try {
            return Files.readAllLines(Paths.get("src/main/java/data/" + path))
                    .stream()
                    .filter(line -> !line.isEmpty() && line.contains(":"))
                    .collect(Collectors.toMap(
                            line -> line.split(":")[0],
                            line -> line.split(":")[1]
                    ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load phone formats", e);
        }
    }
}