package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.EventResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventServiceTest {

    private Database db;
    private String token;
    private String token2;
    private EventResult eventResult;

    private Event event1;
    private Event event2;
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
        event1 = new Event("Biking_123A", "Chris", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventDao.insert(event1);
        event2 = new Event("Hiking-123A", "Chris", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventDao.insert(event2);
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        token = authTokenDao.generateToken("Chris");
        userDao.createUser(new User("Ting", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Ting123"));
        token2 = authTokenDao.generateToken("Ting");
        db.closeConnection(true);
    }


    @Test
    public void eventServicePositive() throws DataAccessException
    {
        EventService eventService = new EventService();
        eventResult = eventService.load(token);
        assertEquals(2, eventResult.getData().size());
        for(Event event: eventResult.getData())
        {
            if(event.equals(event1))
            {
                assertEquals("Biking_123A",event.getEventID());
                assertEquals("Chris",event.getAssociatedUsername());
                assertEquals("Gale123A",event.getPersonID());
                assertEquals(35.9f,event.getLatitude());
                assertEquals(140.1f,event.getLongitude());
                assertEquals("Japan",event.getCountry());
                assertEquals("Ushiku",event.getCity());
                assertEquals("Biking_Around",event.getEventType());
                assertEquals(2016,event.getYear());
            }
            if(event.equals(event2))
            {
                assertEquals("Hiking-123A",event.getEventID());
                assertEquals("Chris",event.getAssociatedUsername());
                assertEquals("Gale123A",event.getPersonID());
                assertEquals(35.9f,event.getLatitude());
                assertEquals(140.1f,event.getLongitude());
                assertEquals("Japan",event.getCountry());
                assertEquals("Ushiku",event.getCity());
                assertEquals("Biking_Around",event.getEventType());
                assertEquals(2016,event.getYear());
            }
        }
    }
    @Test
    public void eventServiceNegative() throws DataAccessException {
        EventService eventService = new EventService();
        eventResult = eventService.load("123");
        assertEquals("Error:[Invalid auth token]",eventResult.getMessage());
        eventResult = eventService.load(token2);
        assertEquals("Error:[Internal server error]",eventResult.getMessage());
    }
}