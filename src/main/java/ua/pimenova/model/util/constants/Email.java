package ua.pimenova.model.util.constants;

/**
 * Contains letter's subjects and bodies
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class Email {
    /** For new users only */
    public static final String SUBJECT_GREETINGS = "Welcome to Cargo Delivery Application!";

    /** Any notification letter subject */
    public static final String SUBJECT_NOTIFICATION = "Cargo Delivery notification!";

    // Place user's name instead of %s
    public static final String HELLO = "Hello %s,<br>";
    public static final String INFORMATION = "We have some important information for you:";
    public static final String SIGNATURE = "Yours truly,<br>Cargo Delivery team.";
    public static final String A_HREF = "<a href=";
    public static final String DOUBLE_ENTER = "<br><br>";

    public static final String MESSAGE_GREETINGS = HELLO +
            "Thank you for choosing Cargo Delivery!<br>" +
            DOUBLE_ENTER +
            "We have prepared some useful information for you:<br>" +
            "In your profile, you can create new shipments, pay for them, and also download invoices of lading in PDF format." +
            DOUBLE_ENTER +
            "<h4>Your orders</h4>" +
            "Navigate through " + A_HREF + "%s" + "/orders>orders</a>, " +
            "check detailed information, payment and execution statuses." +
            DOUBLE_ENTER +
            SIGNATURE;

    public static final String MESSAGE_CREATE_ORDER = HELLO + INFORMATION +
            DOUBLE_ENTER +
            "<h4>You have created new order.</h4><br>" +
            "Total cost: %s, UAH.<br>" +
            "You need to pay for the delivery service in order for the cargo to be processed." +
            DOUBLE_ENTER +
            "You can also check more detailed information about the cargo " +
            A_HREF + "%s" + "/orders>here</a>." + DOUBLE_ENTER + SIGNATURE;

    public static final String MESSAGE_TOP_UP_ACCOUNT = HELLO + INFORMATION +
            DOUBLE_ENTER +
            "<h4>You have replenished your account in the amount of %s UAH.</h4><br>" +
            "Current account: %s, UAH.<br>" +
            DOUBLE_ENTER +
            "You can also check more detailed information about it at your " +
            A_HREF + "%s" + "/profile>profile</a>." + DOUBLE_ENTER + SIGNATURE;

    public static final String MESSAGE_CHANGE_ORDER_STATUS = HELLO + INFORMATION +
            DOUBLE_ENTER +
            "<h4>Your order statuses have changed.</h4><br>" +
            "Payment status: %s.<br>" +
            "Execution status: %s.<br>" +
            DOUBLE_ENTER +
            "Navigate through " + A_HREF + "%s" + "/orders>orders</a>, " +
            "check detailed information, payment and execution statuses." +
            DOUBLE_ENTER + SIGNATURE;

    public static final String MESSAGE_RESET_PASSWORD = HELLO + INFORMATION +
            DOUBLE_ENTER + "Your temporary password is %s. Do not forget to change it in your profile!" +
            DOUBLE_ENTER + "Enter your account " +
            A_HREF + "%s" + "/home>here</a>, " + DOUBLE_ENTER + SIGNATURE;

    private Email() {}
}
