import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validators {
    static class NameValidator {
        private Pattern pattern;

        private static final String NAME_PATTERN =
                "^[_A-Za-z-\\+]+$";
        public NameValidator() {
            pattern = Pattern.compile(NAME_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class NumberValidator {
        private Pattern pattern;

        private static final String NUMBER_PATTERN =
                "^[_,.0-9\\+]+$";
        public NumberValidator() {
            pattern = Pattern.compile(NUMBER_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class LoginValidator {
        private Pattern pattern;

        private static final String LOGIN_PATTERN =
                "^[_A-Za-z0-9\\+]+$";
        public LoginValidator() {
            pattern = Pattern.compile(LOGIN_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
}
