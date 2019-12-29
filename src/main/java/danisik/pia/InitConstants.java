package danisik.pia;

public class InitConstants {
    // User.
    public static final String DEFAULT_ADMIN1_USERNAME = "Admin001";
    public static final String DEFAULT_ADMIN1_PASSWORD = "1234";
    public static final String DEFAULT_ADMIN1_NAME = "Josef Kulihrášek";
    public static final String DEFAULT_ADMIN1_BIRTH_NUMBER = "456789/0123";
    public static final String DEFAULT_ADMIN1_ADDRESS = "Sedláčkova 209/16, 301 00 Plzeň-Vnitřní Město";
    public static final String DEFAULT_ADMIN1_EMAIL = "kulihrasek@seznam.cz";
    public static final String DEFAULT_ADMIN1_PHONE_NUMBER = "123456789";
    public static final String DEFAULT_ADMIN1_CARD_NUMBER = "4339992979647585";

    public static final String DEFAULT_USER1_USERNAME = "User0001";
    public static final String DEFAULT_USER1_PASSWORD = "0001";
    public static final String DEFAULT_USER1_NAME = "Vojtěch Gorgron";
    public static final String DEFAULT_USER1_BIRTH_NUMBER = "970123/1233";
    public static final String DEFAULT_USER1_ADDRESS = "Radonická 30, 420 44 Radonice";
    public static final String DEFAULT_USER1_EMAIL = "gorgron@seznam.cz";
    public static final String DEFAULT_USER1_PHONE_NUMBER = "098 898 111";
    public static final String DEFAULT_USER1_CARD_NUMBER = "1027892979647393";

    public static final String DEFAULT_USER2_USERNAME = "User0002";
    public static final String DEFAULT_USER2_PASSWORD = "0002";
    public static final String DEFAULT_USER2_NAME = "Jana Opletalová";
    public static final String DEFAULT_USER2_BIRTH_NUMBER = "123456/0293";
    public static final String DEFAULT_USER2_ADDRESS = "Kadaňská 20, 202 01 Kadaň";
    public static final String DEFAULT_USER2_EMAIL = "opletalova@seznam.cz";
    public static final String DEFAULT_USER2_PHONE_NUMBER = "121 232 343";
    public static final String DEFAULT_USER2_CARD_NUMBER = "1234567890123456";


    // Invoice.
    public static final Long DEFAULT_INVOICE1_ID = 0L;
    public static final String DEFAULT_INVOICE1_DATE_EXPOSURE = "12.7.2019";
    public static final String DEFAULT_INVOICE1_DATE_DUE = "13.7.2019";
    public static final String DEFAULT_INVOICE1_DATE_FRUITION_PERFORM = "14.7.2019";
    public static final Long DEFAULT_INVOICE1_SYMBOL_VARIABLE = 30303030L;
    public static final Long DEFAULT_INVOICE1_SYMBOL_CONSTANT = 1212L;
    public static final Boolean DEFAULT_INVOICE1_CANCELLED = false;

    public static final Long DEFAULT_INVOICE2_ID = 1L;
    public static final String DEFAULT_INVOICE2_DATE_EXPOSURE = "15.7.2019";
    public static final String DEFAULT_INVOICE2_DATE_DUE = "16.7.2019";
    public static final String DEFAULT_INVOICE2_DATE_FRUITION_PERFORM = "17.7.2019";
    public static final Long DEFAULT_INVOICE2_SYMBOL_VARIABLE = 40404040L;
    public static final Long DEFAULT_INVOICE2_SYMBOL_CONSTANT = 344334L;
    public static final Boolean DEFAULT_INVOICE2_CANCELLED = true;


    // Contact.
    public static final String DEFAULT_SUPPLIER_NAME = "ZF Engineering Pilsen s.r.o";
    public static final String DEFAULT_SUPPLIER_RESIDENCE = "Univerzitní 1159/53, 301 00 Plzeň 3";
    public static final String DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER = "26343398";
    public static final String DEFAULT_SUPPLIER_TAX_IDENTIFICATION_NUMBER = "CZ26343398";
    public static final String DEFAULT_SUPPLIER_PHONE_NUMBER = "373 736 311";
    public static final String DEFAULT_SUPPLIER_EMAIL= "info-plzen@zf.com";
    public static final String DEFAULT_SUPPLIER_BANK_ACCOUNT = "1234567936/0600";

    public static final String DEFAULT_CUSTOMER_NAME = "STS Kadan";
    public static final String DEFAULT_CUSTOMER_RESIDENCE = "Kadan 302/10, 432 01 Kadan";
    public static final String DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER = "12345678";
    public static final String DEFAULT_CUSTOMER_TAX_IDENTIFICATION_NUMBER = "CZ22334567";
    public static final String DEFAULT_CUSTOMER_PHONE_NUMBER = "323 012 478";
    public static final String DEFAULT_CUSTOMER_EMAIL= "sts@sts.com";
    public static final String DEFAULT_CUSTOMER_BANK_ACCOUNT = "1234567890/0800";


    // Role.
    public static final String DEFAULT_ROLE_ADMIN_CODE = "ADMIN";
    public static final String DEFAULT_ROLE_ADMIN_NAME = "Admins";

    public static final String DEFAULT_ROLE_USER_CODE = "USER";
    public static final String DEFAULT_ROLE_USER_NAME = "Users";

    public static final String DEFAULT_ROLE_PURSER_CODE = "PURSER";
    public static final String DEFAULT_ROLE_PURSER_NAME = "Pursers";


    // Goods.
    public static final String DEFAULT_GOODS1_NAME = "Nike Airforce 1";
    public static final Long DEFAULT_GOODS1_QUANTITY = 1L;
    public static final Float DEFAULT_GOODS1_PRICE_PER_ONE = 1000F;
    public static final Float DEFAULT_GOODS1_DISCOUNT = 2F;
    public static final Float DEFAULT_GOODS1_TAX_RATE = 21F;

    public static final String DEFAULT_GOODS2_NAME = "Adidas Plus 3";
    public static final Long DEFAULT_GOODS2_QUANTITY = 2L;
    public static final Float DEFAULT_GOODS2_PRICE_PER_ONE = 500F;
    public static final Float DEFAULT_GOODS2_DISCOUNT = 1.5F;
    public static final Float DEFAULT_GOODS2_TAX_RATE = 15F;

    public static final String DEFAULT_GOODS3_NAME = "Pedro";
    public static final Long DEFAULT_GOODS3_QUANTITY = 10L;
    public static final Float DEFAULT_GOODS3_PRICE_PER_ONE = 2F;
    public static final Float DEFAULT_GOODS3_DISCOUNT = 0F;
    public static final Float DEFAULT_GOODS3_TAX_RATE = 21F;
}
