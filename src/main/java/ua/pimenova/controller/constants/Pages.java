package ua.pimenova.controller.constants;

public final class Pages {

    //common
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_ERROR = "jsp/errorPage.jsp";
    public static final String SIGNUP_PAGE = "jsp/signUp.jsp";

    //user
    public static final String USER_PROFILE = "jsp/user/userProfile.jsp";
    public static final String CREATE_ORDER = "jsp/user/createOrder.jsp";
    public static final String ORDERS_LIST_PAGE = "jsp/user/ordersList.jsp";
    public static final String UPDATE_ORDER_PAGE = "jsp/user/updateOrder.jsp";
    public static final String ACCOUNT_PAGE = "jsp/user/account.jsp";

    //manager
    public static final String MANAGER_PROFILE = "jsp/manager/managerProfile.jsp";
    public static final String REPORTS = "jsp/manager/reports.jsp";
    public static final String PACKAGES = "jsp/manager/packages.jsp";
    public static final String UPDATE_PACKAGE = "jsp/manager/updateShipment.jsp";

    private Pages() {}
}
