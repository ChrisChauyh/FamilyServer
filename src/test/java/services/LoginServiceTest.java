package services;

import com.google.gson.Gson;
import dataAccess.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.LoginRequest;
import requestAndResult.LoginResult;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private Database db;
    private LoginRequest loginRequest = new LoginRequest();
    private LoginResult loginResult = new LoginResult();
    private User bestUser;
    private User secondUser;
    Gson gson;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException, IOException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        gson = new Gson();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personDao.clear();
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();
        bestUser = new User("Ting", "liu", "Tingliu@gmail.com",
                "Ting Ting", "Liu", "f", "Ting1357");
        secondUser = new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357");

        userDao.createUser(bestUser);
        userDao.createUser(secondUser);
        db.closeConnection(true);

    }

    @Test
    public void loginServicePositive() throws SQLException, DataAccessException {
        LoginService loginService = new LoginService();
        loginRequest.setUsername("Ting");
        loginRequest.setPassword("liu");
        loginResult = loginService.login(loginRequest);
        assertNotNull(loginResult.getAuthtoken());
        assertEquals("Ting", loginResult.getUsername());
        assertEquals("Ting1357", loginResult.getPersonID());
        assertTrue(loginResult.getSuccess());

        LoginService loginService2 = new LoginService();
        loginRequest.setUsername("Chris");
        loginRequest.setPassword("asdasd");
        loginResult = loginService2.login(loginRequest);
        assertNotNull(loginResult.getAuthtoken());
        assertEquals("Chris", loginResult.getUsername());
        assertEquals("Chris1357", loginResult.getPersonID());
        assertTrue(loginResult.getSuccess());

    }

    @Test
    public void loginServiceNegative() throws SQLException, DataAccessException {
        LoginService loginService = new LoginService();
        loginRequest.setUsername(null);
        loginRequest.setPassword("asdasd");
        loginResult = loginService.login(loginRequest);
        assertEquals("Error:[Request property missing or has invalid value.]", loginResult.getMessage());
        assertFalse(loginResult.getSuccess());

        loginRequest.setUsername("Chris");
        loginRequest.setPassword("aq");
        loginResult = loginService.login(loginRequest);
        assertEquals("Error:[Wrong Username or password]", loginResult.getMessage());
        assertFalse(loginResult.getSuccess());


    }

}