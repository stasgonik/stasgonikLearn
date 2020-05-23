public class application {
    public static void main(String[] args) {
        account gloria = account.createAccount(currency.EUR);
        System.out.println(gloria.toString());
    }
}
