import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validators {
    static class NameValidator {
        private Pattern pattern;

        private static final String NAMES_PATTERN =
                "^[_A-Za-z-\\+]+$";
        public NameValidator() {
            pattern = Pattern.compile(NAMES_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
    static class NumberValidator {
        private Pattern pattern;

        private static final String NAMES_PATTERN =
                "^[_,.0-9\\+]+$";
        public NumberValidator() {
            pattern = Pattern.compile(NAMES_PATTERN);
        }
        public boolean validate(final String hex) {
            Matcher matcher = pattern.matcher(hex);

            return matcher.matches();
        }
    }
}
