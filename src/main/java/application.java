import java.sql.*;
public class application {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            // add application code here
            conn.close();
        }
        catch (Exception ex) {
            ex.getMessage();
        }
        account gloria = account.createAccount();
        System.out.println(gloria.toString());
    }
}
