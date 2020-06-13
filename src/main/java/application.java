import java.sql.*;

public class application {

    public static void main(String[] args) {
        account test = account.createAccount();
        System.out.println(test.toString());
    }
}
