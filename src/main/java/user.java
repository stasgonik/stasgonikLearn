import java.util.Scanner;

public class user {
    private String firstName;
    private String secondName;
    private String familyName;

    public user(String firstName, String secondName, String familyName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.familyName = familyName;
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
    public static user createUser() {
        Scanner sc = new Scanner(System.in);
        user us = new user();
        try {
            System.out.println("Enter first name:");
            us.setFirstName(sc.nextLine());
            System.out.println("Enter second name:");
            us.setSecondName(sc.nextLine());
            System.out.println("Enter family name:");
            us.setFamilyName(sc.nextLine());
        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return us;
    }

    @Override
    public String toString() {
        return familyName + " " + firstName + " " + secondName
                ;
    }
}
