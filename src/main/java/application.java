import java.sql.*;
import java.util.*;


public class application {

    public static void main(String[] args) {
        ArrayList<currency> list = new ArrayList<>();
        list.add(currency.UAH);
        list.add(currency.USD);
        list.add(currency.EUR);
        list.add(currency.GBP);
        for (currency c:list) {
            currencyDB.currencyUpdate(c);
        }

    }
}
