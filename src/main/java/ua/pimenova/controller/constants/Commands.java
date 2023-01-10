package ua.pimenova.controller.constants;

public final class Commands {
    private Commands() {}

    //common

    public static final String CALCULATE = "/calculate";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SHOW_SIGNUP_PAGE = "/signup_page";
    public static final String SIGN_UP = "/signup";
    public static final String HOME = "/home";
    public static final String PROFILE = "/profile";
    public static final String UPDATE_PROFILE = "/update";
    public static final String GET_PDF = "/pdf";
    public static final String ERROR = "/error";

    //user commands

    public static final String CREATE_ORDER = "/createOrder";
    public static final String GET_ORDERS = "/orders";
    public static final String SHOW_PAGE_CREATE_ORDER = "/pageCreate";
    public static final String DELETE_ORDER = "/deleteOrder";
    public static final String UPDATE_ORDER_BY_USER = "/updateOrder_user";
    public static final String SHOW_PAGE_UPDATE_ORDER = "/update_page";
    public static final String ACCOUNT = "/account";
    public static final String TOP_UP_ACCOUNT = "/top_up";
    public static final String TRANSACTION = "/transaction";

    //manager

    public static final String GET_REPORTS = "/reports";
    public static final String GET_PACKAGES = "/packages";
    public static final String UPDATE_ORDER_BY_MANAGER = "/updateStatus";
    public static final String SHOW_PAGE_UPDATE_ORDER_MANAGER = "/updateShipment_page";
}
