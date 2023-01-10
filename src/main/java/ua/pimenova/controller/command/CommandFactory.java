package ua.pimenova.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import ua.pimenova.controller.command.common.*;
import ua.pimenova.controller.command.manager.GetPackagesCommand;
import ua.pimenova.controller.command.manager.GetReportsCommand;
import ua.pimenova.controller.command.manager.ShowUpdateShipmentCommand;
import ua.pimenova.controller.command.manager.UpdateOrderByManagerCommand;
import ua.pimenova.controller.command.user.*;
import ua.pimenova.model.database.dao.impl.FreightDaoImpl;
import ua.pimenova.model.database.dao.impl.OrderDaoImpl;
import ua.pimenova.model.database.dao.impl.ReceiverDaoImpl;
import ua.pimenova.model.database.dao.impl.UserDaoImpl;
import ua.pimenova.model.service.FreightService;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.ReceiverService;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.service.impl.FreightServiceImpl;
import ua.pimenova.model.service.impl.OrderServiceImpl;
import ua.pimenova.model.service.impl.ReceiverServiceImpl;
import ua.pimenova.model.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory;

    private Map<String, ICommand> commands;

    private CommandFactory() {
        commands = new HashMap<>();
        UserService userService = new UserServiceImpl(UserDaoImpl.getInstance());
        OrderService orderService = new OrderServiceImpl(OrderDaoImpl.getInstance());
        FreightService freightService = new FreightServiceImpl(FreightDaoImpl.getInstance());
        ReceiverService receiverService = new ReceiverServiceImpl(ReceiverDaoImpl.getInstance());

        //common commands
        commands.put("calculate", new CalculateCommand());
        commands.put("login", new LoginCommand(userService));
        commands.put("logout", new LogoutCommand());
        commands.put("signup_page", new ShowSignUpPageCommand());
        commands.put("signup", new SignupCommand(userService));
        commands.put("home", new ShowHomePageCommand());
        commands.put("profile", new ShowProfileCommand());
        commands.put("update", new UpdateProfileCommand(userService));
        commands.put("pdf", new PdfBuilderCommand(orderService, userService, receiverService));
        commands.put("error", new ShowErrorPageCommand());

        //user commands
        commands.put("createOrder", new CreateOrderCommand(orderService));
        commands.put("orders", new GetOrdersCommand(orderService));
        commands.put("pageCreate", new ShowCreateOrderPageCommand());
        commands.put("deleteOrder", new DeleteOrderCommand(orderService));
        commands.put("updateOrder_user", new UpdateOrderByUserCommand(orderService,
                freightService, receiverService));
        commands.put("update_page", new ShowUpdateOrderPageCommand(orderService));
        commands.put("account", new ShowAccountCommand());
        commands.put("top_up", new TopUpCommand(userService));
        commands.put("transaction", new TransactionCommand(orderService, userService));

        //manager
        commands.put("reports", new GetReportsCommand(orderService, userService, receiverService));
        commands.put("packages", new GetPackagesCommand(orderService));
        commands.put("updateShipment_page", new ShowUpdateShipmentCommand(orderService));
        commands.put("updateStatus", new UpdateOrderByManagerCommand(orderService));
    }

    public static synchronized CommandFactory getFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    public ICommand getCommand(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String commandName = StringUtils.substringAfter(requestURI, "/delivery/");
        return commands.get(commandName);
    }
}
