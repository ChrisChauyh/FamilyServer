package dataAccess;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private Person secondPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException, SQLException {
        db = new Database();
        bestPerson = new Person("Ting1357", "Ting", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135");
        secondPerson = new Person("Chris1357", "Chris", "Yu Hin", "Chau", "m", "Chau134", "Wong246", "Ting246");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown()
            /*
            this is after each test case
             */ {
        db.closeConnection(false);
    }


    @Test
    void createPersonPass() throws DataAccessException {
        pDao.createPerson(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson.getPersonID(), compareTest.getPersonID());
        assertEquals(bestPerson.getAssociatedUsername(), compareTest.getAssociatedUsername());
        assertEquals(bestPerson.getFirstName(), compareTest.getFirstName());
        assertEquals(bestPerson.getLastName(), compareTest.getLastName());
        assertEquals(bestPerson.getGender(), compareTest.getGender());
        assertEquals(bestPerson.getFatherID(), compareTest.getFatherID());
        assertEquals(bestPerson.getMotherID(), compareTest.getMotherID());
        assertEquals(bestPerson.getSpouseID(), compareTest.getSpouseID());
    }

    @Test
    void createPersonFail() throws DataAccessException {
        pDao.createPerson(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.createPerson(bestPerson));
    }

    @Test
    void findPass() throws DataAccessException {
        pDao.createPerson(bestPerson);
        pDao.createPerson(secondPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson.getPersonID(), compareTest.getPersonID());
        assertEquals(bestPerson.getAssociatedUsername(), compareTest.getAssociatedUsername());
        assertEquals(bestPerson.getFirstName(), compareTest.getFirstName());
        assertEquals(bestPerson.getLastName(), compareTest.getLastName());
        assertEquals(bestPerson.getGender(), compareTest.getGender());
        assertEquals(bestPerson.getFatherID(), compareTest.getFatherID());
        assertEquals(bestPerson.getMotherID(), compareTest.getMotherID());
        assertEquals(bestPerson.getSpouseID(), compareTest.getSpouseID());

    }

    @Test
    void findFail() throws DataAccessException {
        pDao.createPerson(bestPerson);
        String test = null;
        assertThrows(DataAccessException.class, () -> pDao.find(test));

    }

    @Test
    void findallPersonsPass() throws DataAccessException {

    }

    @Test
    void findallPersonsFail() throws DataAccessException {

             }
    @Test
    void clear() throws  DataAccessException{
        pDao.createPerson(bestPerson);
        pDao.createPerson(secondPerson);
        pDao.clear();
        assertThrows(DataAccessException.class, () -> pDao.find(bestPerson.getPersonID()));
        assertThrows(DataAccessException.class, () ->  pDao.find(secondPerson.getPersonID()));


    }

    @Test
    void clearsinglePass() throws DataAccessException {
        pDao.createPerson(bestPerson);
        pDao.createPerson(secondPerson);
        pDao.clearsingle(bestPerson.getPersonID());
        assertThrows(DataAccessException.class, () -> pDao.find(bestPerson.getPersonID()));
    }

    @Test
    void clearsingleFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> pDao.clearsingle(bestPerson.getPersonID()));
        assertThrows(DataAccessException.class, () -> pDao.clearsingle(null));

    }
}