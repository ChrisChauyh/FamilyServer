package services;

import dataAccess.*;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private Database db;
    private RegisterRequest registerRequest = new RegisterRequest();
    private RegisterRequest registerRequest2 = new RegisterRequest();
    private RegisterResult registerResult = new RegisterResult();

    private ArrayList<Person> personArrayList;
    private ArrayList<Event> eventArrayList;

    @BeforeEach
    public void setup() throws SQLException, DataAccessException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personDao.clear();
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();

        registerRequest.setUsername("Chris");
        registerRequest.setPassword("12345");
        registerRequest.setFirstName("Chau");
        registerRequest.setLastName("Chau");
        registerRequest.setGender("m");
        registerRequest.setEmail("Chris@gmail.com");

        db.closeConnection(true);
    }

    @Test
    public void registerServicePositive() throws SQLException, FileNotFoundException, DataAccessException {
        RegisterService registerService = new RegisterService();
        registerResult = registerService.register(registerRequest);
        assertNotNull(registerResult.getAuthtoken());
        assertEquals("Chris", registerResult.getUsername());
        assertNotNull(registerResult.getPersonID());
        assertNull(registerResult.getMessage());
        assertEquals(true,registerResult.getSuccess());
        db.openConnection();

        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personArrayList =personDao.findallPersons("Chris");
        eventArrayList = eventDao.findallEvents("Chris");
        db.closeConnection(false);
        //persons created including user
        assertEquals(31, personArrayList.size(),"4 generations: 1+2+4+8+16");
        //events that didn't add user death date
        assertEquals(91,eventArrayList.size(),"all person have a birthdate(31), all person besides user have a death date(30)," +
                "parents both have a marriage event(31) ");

    }
    @Test
    public void registerServiceNegative() throws SQLException, FileNotFoundException, DataAccessException {
        RegisterService registerService = new RegisterService();
        registerResult = registerService.register(registerRequest);
        registerResult = registerService.register(registerRequest);
        assertEquals("Error:[Username already taken by another user]",registerResult.getMessage());
        assertFalse(registerResult.getSuccess());
        //check for invalid names
        registerRequest.setUsername(null);
        registerResult = registerService.register(registerRequest);
        assertEquals("Error[Request property missing or has invalid value]",registerResult.getMessage());
        assertFalse(registerResult.getSuccess());

        registerRequest.setUsername("");
        registerResult = registerService.register(registerRequest);
        assertEquals("Error[Request property missing or has invalid value]",registerResult.getMessage());
        assertFalse(registerResult.getSuccess());
    }

    @AfterEach
    public void clear() throws DataAccessException, SQLException {

    }

}