import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validators {
    static class NameValidator {
        private final Pattern pattern;

        private static final String NAME_PATTERN =
                "^[_A-Za-z-+]+$";
        public NameValidator() {
            pattern = Pattern.compile(NAME_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class NumberValidator {
        private final Pattern pattern;

        private static final String NUMBER_PATTERN =
                "^[_,.0-9+]+$";
        public NumberValidator() {
            pattern = Pattern.compile(NUMBER_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class LoginValidator {
        private final Pattern pattern;

        private static final String LOGIN_PATTERN =
                "^[_A-Za-z0-9+]+$";
        public LoginValidator() {
            pattern = Pattern.compile(LOGIN_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class PhoneValidator {
        private final Pattern pattern;

        private static final String NUMBER_PATTERN =
                "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        public PhoneValidator() {
            pattern = Pattern.compile(NUMBER_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
}
