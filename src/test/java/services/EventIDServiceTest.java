package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.EventIDResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EventIDServiceTest {

    private Database db;
    private String token;
    private EventIDResult eventIDResult;

    @BeforeEach
    public void setup() throws DataAccessException, SQLException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personDao.clear();
        ;
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();

        personDao.createPerson(new Person("Gale123A", "Chris", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135"));
        eventDao.insert(new Event("Biking_123A", "Chris", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        token = authTokenDao.generateToken("Chris");
        db.closeConnection(true);
    }

    @Test
    public void eventIDServicePositive() throws SQLException, DataAccessException {
        EventIDService eventIDService = new EventIDService();
        eventIDResult = eventIDService.load(token, "Biking_123A");
        assertEquals("Biking_123A", eventIDResult.getEventID());
        assertEquals("Chris", eventIDResult.getAssociatedUsername());
        assertEquals("Gale123A", eventIDResult.getPersonID());
        assertEquals(35.9f, eventIDResult.getLatitude());
        assertEquals(140.1f, eventIDResult.getLongitude());
        assertEquals("Japan", eventIDResult.getCountry());
        assertEquals("Ushiku", eventIDResult.getCity());
        assertEquals("Biking_Around", eventIDResult.getEventType());
        assertEquals(2016, eventIDResult.getYear());
    }

    @Test
    public void eventIDServiceNegative() throws SQLException, DataAccessException {
        EventIDService eventIDService = new EventIDService();
        eventIDResult = eventIDService.load(token, "Biking_12113A");
        assertEquals("Error:[Invalid eventID parameter]", eventIDResult.getMessage());
        eventIDResult = eventIDService.load(token, "");
        assertEquals("Error:[Invalid eventID parameter]", eventIDResult.getMessage());
        eventIDResult = eventIDService.load("123", "Biking_123A");
        assertEquals("Error:[Invalid auth token]", eventIDResult.getMessage());
    }
}