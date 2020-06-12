import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class user {
    private String firstName;
    private String secondName;
    private String familyName;
    private int age;


    public user(String firstName, String secondName, String familyName, int age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.familyName = familyName;
        this.age = age;
    }

    public user() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static user createUser() {
        Scanner sc = new Scanner(System.in);
        user us = new user();
        userValidatorName validator = new userValidatorName();
        userNumberValidator validator2 = new userNumberValidator();

        int i = 0;
        try {
            do {
                System.out.println("Enter first name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setFirstName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter second name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setSecondName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==1);
            do {
                System.out.println("Enter family name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setFamilyName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==2);
            do {
                System.out.println("Enter your age:");
                String temp = sc.nextLine();
                if (validator2.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    us.setAge(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect age format. Use only numbers!");
                }
            }
            while (i==3);

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return us;
    }

    @Override
    public String toString() {
        return familyName + " " + firstName + " " + secondName + " , age: " + age
                ;
    }
}
class userValidatorName {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_A-Za-z-\\+]+$";
    public userValidatorName() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
class userNumberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+]+$";
    public userNumberValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
