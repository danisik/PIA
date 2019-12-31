package danisik.pia;

public class Constants {
    // For all.
    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

    public static final String REQUEST_PARAM_ID = "id";

    // User.
    public static final String ATTRIBUTE_NAME_USER = "user";
    public static final String ATTRIBUTE_NAME_USERS = "users";
    public static final String ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE = "message";
    public static final String USER_PASSWORD_CHANGE_MESSAGE = "Heslo bylo úspěšně změněno.";
    public static final String REQUEST_PARAM_USER_OLD_PASSWORD = "oldPassword";
    public static final String REQUEST_PARAM_USER_NEW_PASSWORD = "newPassword";
    public static final String REQUEST_PARAM_USER_NEW_PASSWORD_CONFIRMATION = "newPasswordConfirmation";


    // Contact.
    public static final String ATTRIBUTE_NAME_CONTACT = "contact";
    public static final String ATTRIBUTE_NAME_CONTACTS = "contacts";


    // Invoice.
    public static final String ATTRIBUTE_NAME_INVOICE = "invoice";
    public static final String ATTRIBUTE_NAME_INVOICES = "invoices";
    public static final String ATTRIBUTE_NAME_INVOICES_SUPPLIER = "contactSupplier";
    public static final String ATTRIBUTE_NAME_INVOICES_CUSTOMER = "contactCustomer";
    public static final String ATTRIBUTE_NAME_INVOICES_BUTTON_NAME = "action";
    public static final String ATTRIBUTE_NAME_INVOICES_INVOICE_ID = "invoiceID";
    public static final String INVOICE_CANCEL_STRING = "cancelInvoice";
    public static final String INVOICE_CANCELLED_MESSAGE = "Faktura je stornovaná, nelze ji upravovat!";


    // InvoiceType.
    public static final String ATTRIBUTE_NAME_INVOICE_TYPE = "invoiceType";
    public static final String ATTRIBUTE_NAME_INVOICE_TYPES = "invoiceTypes";
    public static final String ATTRIBUTE_NAME_INVOICE_CANCELLED_MESSAGE = "message";


    // Role.
    public static final String ATTRIBUTE_NAME_ROLE = "role";
    public static final String ATTRIBUTE_NAME_ROLES = "roles";
}
