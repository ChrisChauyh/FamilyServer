package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.PersonIDResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonIDServiceTest {
    private Database db;
    private String token;
    private Person bestPerson;
    private Person secondPerson;
    private Person thirdPerson;
    private PersonIDResult personIDResult;

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
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();

        bestPerson = new Person("Ting1357", "Ting", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135");
        secondPerson = new Person("Chris1357", "Ting", "Yu Hin", "Chau", "m", "Chau134", "Wong246", "Ting246");
        thirdPerson = new Person("ASddd", "Ting", "ergrg", "wef", "m", "Chau134", "Wong246", "Ting246");
        personDao.createPerson(bestPerson);
        personDao.createPerson(secondPerson);
        personDao.createPerson(thirdPerson);

        eventDao.insert(new Event("Biking_123A", "Ting", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        token = authTokenDao.generateToken("Ting");
        db.closeConnection(true);
    }

    @Test
    public void personIDServicePositive() throws SQLException, DataAccessException {
        PersonIDService personIDService = new PersonIDService();
        personIDResult = personIDService.load(token, "Ting1357");
        assertEquals("Ting1357",personIDResult.getPersonID());
        assertEquals("Ting",personIDResult.getAssociatedUsername());
        assertEquals("TingTing",personIDResult.getFirstName());
        assertEquals("Liu",personIDResult.getLastName());
        assertEquals("f",personIDResult.getGender());
        assertEquals("liu135",personIDResult.getFatherID());
        assertEquals("liu246",personIDResult.getMotherID());
        assertEquals("Chris135",personIDResult.getSpouseID());
        assertEquals(true,personIDResult.isSuccess());



    }

    @Test
    public void personIDServiceNegative() throws SQLException, DataAccessException {
        PersonIDService personIDService = new PersonIDService();
        personIDResult = personIDService.load(token, "TTing1357");
        assertEquals("Error:[Requested person does not belong to this user]", personIDResult.getMessage());
        personIDResult = personIDService.load(token, "");
        assertEquals("Error:[Invalid personID parameter]", personIDResult.getMessage());
        personIDResult = personIDService.load("", "Ting135");
        assertEquals("Error:[Invalid auth token]", personIDResult.getMessage());
    }
}