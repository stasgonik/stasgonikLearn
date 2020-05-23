public class application {
    public static void main(String[] args) {
        user gloria = new user("Rayna", "Jorge", "Sullivan");
        account glor = new account(1, gloria, currency.USD, 50);
        System.out.println(glor.toString());
    }
}
