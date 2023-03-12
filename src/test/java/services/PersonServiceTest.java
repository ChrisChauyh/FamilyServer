package services;

import dataAccess.*;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonServiceTest {
    private Database db;
    private String token;
    private String token2;
    private PersonResult personResult;

    private Person bestPerson;
    private Person secondPerson;
    private Person thirdPerson;
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

        userDao.createUser(new User("Ting", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));


        token = authTokenDao.generateToken("Ting");
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Ting123"));
        token2 = authTokenDao.generateToken("Chris");
        db.closeConnection(true);
    }


    @Test
    public void eventServicePositive() throws DataAccessException, SQLException {
        PersonService personService = new PersonService();
        personResult = personService.load(token);
        assertEquals(3, personResult.getData().size());
        for(Person person: personResult.getData())
        {
            if(person.equals(bestPerson))
            {
                assertEquals("Ting1357",person.getPersonID());
                assertEquals("Ting",person.getAssociatedUsername());
                assertEquals("TingTing",person.getFirstName());
                assertEquals("Liu",person.getLastName());
                assertEquals("f",person.getGender());
                assertEquals("liu135",person.getFatherID());
                assertEquals("liu246",person.getMotherID());
                assertEquals("Chris135",person.getSpouseID());
            }
            if(person.equals(secondPerson))
            {
                assertEquals("Chris1357",person.getPersonID());
                assertEquals("Ting",person.getAssociatedUsername());
                assertEquals("Yu Hin",person.getFirstName());
                assertEquals("Chau",person.getLastName());
                assertEquals("m",person.getGender());
                assertEquals("Chau134",person.getFatherID());
                assertEquals("Wong246",person.getMotherID());
                assertEquals("Ting246",person.getSpouseID());
            }
            if(person.equals(thirdPerson))
            {
                assertEquals("ASddd",person.getPersonID());
                assertEquals("Ting",person.getAssociatedUsername());
                assertEquals("ergrg",person.getFirstName());
                assertEquals("wef",person.getLastName());
                assertEquals("m",person.getGender());
                assertEquals("Chau134",person.getFatherID());
                assertEquals("Wong246",person.getMotherID());
                assertEquals("Ting246",person.getSpouseID());
            }
        }
    }
    @Test
    public void eventServiceNegative() throws DataAccessException, SQLException {
        PersonService personService = new PersonService();
        personResult = personService.load("123");
        assertEquals("Error:[Invalid auth token]",personResult.getMessage());
        personResult = personService.load(token2);
        assertEquals("Error:[Internal server error]",personResult.getMessage());
    }
}