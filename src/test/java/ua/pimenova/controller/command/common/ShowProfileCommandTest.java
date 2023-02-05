package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowProfileCommandTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @InjectMocks
    ShowProfileCommand command;

    User user = new User(1, "password", "Ivan", "Ivanov", "+380111111111", "email",
            0, User.Role.USER, "City", "Street", "Postal Code");
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    void testUserIsUser() {
        String path = command.execute(request, response);

        assertEquals("jsp/user/userProfile.jsp", path);
    }

    @Test
    void testUserIsManager() {
        user.setRole(User.Role.MANAGER);

        String path = command.execute(request, response);

        assertEquals("jsp/manager/managerProfile.jsp", path);
    }
}