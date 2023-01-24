package ua.pimenova.model.util.constants;

public class Email {
    public static final String SUBJECT_GREETINGS = "Welcome to Cargo Delivery Application!";

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



    private Email() {}
}