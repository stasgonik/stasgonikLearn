package com.epam.GSI;


public class constants {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/GSI";
    static final String USER = "stasgonik";
    static final String PASS = "";
    public static final int bank = 42;
    public static final double extractLess5k = 0.005;
    public static final double extractLess100k = 0.003;
    public static final double extractMore100k = 0.002;
    public static final double chargeLess5k = 0.003;
    public static final double chargeLess100k = 0.002;
    public static final double chargeMore100k = 0.001;
    public static final double transferLess10k = 0.009;
    public static final double transferLess100k = 0.007;
    public static final double transferLess500k = 0.005;
    public static final double transferMore500k = 0.004;
    public static final double creditBaseCurrency = 1.17;
    public static final double creditOtherCurrency = 1.08;
    public static String extractLess5String = "0.5";
    public static String extractLess100String = "0.3";
    public static String extractMore100String = "0.2";
    public static String chargeLess5String = "0.3";
    public static String chargeLess100String = "0.2";
    public static String chargeMore100String = "0.1";
    public static String transferLess10String = "0.9";
    public static String transferLess100String = "0.7";
    public static String transferLess500String = "0.5";
    public static String transferMore500String = "0.4";
    public static String creditBaseString = "17";
    public static String creditOtherString = "8";
}
