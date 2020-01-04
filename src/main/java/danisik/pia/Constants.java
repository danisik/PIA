package danisik.pia;

import java.text.DecimalFormat;

public class Constants {
    // For all.
    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

    public static final String REQUEST_PARAM_ID = "id";

    public static final Integer PASSWORD_LENGTH = 4;
    public static final Integer USERNAME_LENGTH = 8;
    public static final String PASSWORD_SYMBOLS = "0123456789";
    public static final String USERNAME_SYMBOLS = PASSWORD_SYMBOLS + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final String ATTRIBUTE_NAME_MESSAGE = "message";

    public static final String VALIDATE_CARD_NUMBER_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
    public static final String VALIDATE_BIRTH_NUMBER_REGEX = "^(?:\\d{6}\\/\\d{4})$";
    public static final String VALIDATE_ADDRESS_REGEX = "^(.*[^0-9]+) (([1-9][0-9]*)\\/)?([1-9][0-9]*[a-cA-C]?)?([0-9]{3} [0-9]{2}) (.*[^0-9]+)$";
    public static final String VALIDATE_EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
    public static final String VALIDATE_PHONE_NUMBER_REGEX = "^(\\+420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$";
    // User.
    public static final String ATTRIBUTE_NAME_USER = "user";
    public static final String ATTRIBUTE_NAME_USERS = "users";
    public static final String ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE = "message";
    public static final String USER_PASSWORD_CHANGE_MESSAGE = "Heslo bylo úspěšně změněno.";
    public static final String REQUEST_PARAM_USER_OLD_PASSWORD = "oldPassword";
    public static final String REQUEST_PARAM_USER_NEW_PASSWORD = "newPassword";
    public static final String REQUEST_PARAM_USER_NEW_PASSWORD_CONFIRMATION = "newPasswordConfirmation";
    public static final String ADMIN_EDIT_USER_ROLE_MESSAGE = "Informations updated, but you can't change your role!";


    // Contact.
    public static final String ATTRIBUTE_NAME_CONTACT = "contact";
    public static final String ATTRIBUTE_NAME_CONTACTS = "contacts";
    public static final String CONTACT_DELETE_MESSAGE = "Kontakt je stále používán jako dodavatel/odebíratel ve fakturách!";


    // Invoice.
    public static final String ATTRIBUTE_NAME_INVOICE = "invoice";
    public static final String ATTRIBUTE_NAME_INVOICES = "invoices";
    public static final String ATTRIBUTE_NAME_INVOICES_SUPPLIER = "contactSupplier";
    public static final String ATTRIBUTE_NAME_INVOICES_CUSTOMER = "contactCustomer";
    public static final String ATTRIBUTE_NAME_INVOICES_BUTTON_NAME = "action";
    public static final String ATTRIBUTE_NAME_INVOICES_INVOICE_ID = "invoiceID";
    public static final String INVOICE_CANCEL_STRING = "cancelInvoice";
    public static final String INVOICE_CANCELLED_MESSAGE = "Faktura je stornovaná, nelze ji upravovat!";
    public static final Integer INVOICE_DEFAULT_INIT_COUNT_GOODS = 3;


    // InvoiceType.
    public static final String ATTRIBUTE_NAME_INVOICE_TYPE = "invoiceType";
    public static final String ATTRIBUTE_NAME_INVOICE_TYPES = "invoiceTypes";


    // Role.
    public static final String ATTRIBUTE_NAME_ROLE = "role";
    public static final String ATTRIBUTE_NAME_ROLES = "roles";


    // Goods.
    public static final String ATTRIBUTE_NAME_GOODS = "goods";
    public static final String ATTRIBUTE_NAME_WARES = "wares";
}
