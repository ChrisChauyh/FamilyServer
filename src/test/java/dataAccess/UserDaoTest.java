package dataAccess;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private User bestUser;
    private User secondUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException, SQLException {
        db = new Database();
        bestUser = new User("Ting", "liu", "Tingliu@gmail.com",
                "Ting Ting", "Liu", "f", "Ting1357");
        secondUser = new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357");

        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown()
            /*
            this is after each test case
             */ {
        db.closeConnection(false);
    }

    @Test
    public void registerPass() throws DataAccessException {
        uDao.createUser(bestUser);
        User compareTest = uDao.find(bestUser.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestUser.getPersonID(), compareTest.getPersonID());
        assertEquals(bestUser.getUsername(), compareTest.getUsername());
        assertEquals(bestUser.getPassword(), compareTest.getPassword());
        assertEquals(bestUser.getEmail(), compareTest.getEmail());
        assertEquals(bestUser.getFirstName(), compareTest.getFirstName());
        assertEquals(bestUser.getLastName(), compareTest.getLastName());
        assertEquals(bestUser.getGender(), compareTest.getGender());
    }

    @Test
    public void registerFail() throws DataAccessException {
        uDao.createUser(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.createUser(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.createUser(bestUser);
        uDao.createUser(secondUser);
        User compareTest = uDao.find(bestUser.getPersonID());
        assertEquals(bestUser.getPersonID(), compareTest.getPersonID());
        assertEquals(bestUser.getUsername(), compareTest.getUsername());
        assertEquals(bestUser.getPassword(), compareTest.getPassword());
        assertEquals(bestUser.getEmail(), compareTest.getEmail());
        assertEquals(bestUser.getFirstName(), compareTest.getFirstName());
        assertEquals(bestUser.getLastName(), compareTest.getLastName());
        assertEquals(bestUser.getGender(), compareTest.getGender());
    }

    @Test
    public void findFail() throws DataAccessException {
        uDao.createUser(bestUser);
        String test = null;
        assertThrows(DataAccessException.class, () -> uDao.find(test));
    }

    @Test
    public void clear() throws DataAccessException {
        uDao.createUser(bestUser);
        uDao.createUser(secondUser);
        uDao.clear();
        assertThrows(DataAccessException.class, () -> uDao.find(bestUser.getPersonID()));
        assertThrows(DataAccessException.class, () ->  uDao.find(secondUser.getPersonID()));
    }

    @Test
    public void clearsinglePass() throws DataAccessException {
        uDao.createUser(bestUser);
        uDao.createUser(secondUser);
        uDao.clearsingleuser(bestUser.getUsername());
        assertThrows(DataAccessException.class, () -> uDao.find(bestUser.getPersonID()));
    }

    @Test
    public void clearsingleFailed() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> uDao.clearsingleuser(bestUser.getUsername()));
        assertThrows(DataAccessException.class, () -> uDao.clearsingleuser(null));
    }
    @Test
    public void validatePass() throws DataAccessException{
        // Arrange
        uDao.createUser(bestUser);
        String existingUsername = bestUser.getUsername();
        String nonExistingUsername = "testuser2";
        String emptyUsername = "";
        String nullUsername = null;

        // Act
        boolean isExistingUser = uDao.validate(existingUsername);
        boolean isNonExistingUser =  uDao.validate(nonExistingUsername);
        boolean isEmptyUser =  uDao.validate(emptyUsername);

        // Assert
        assertTrue(isExistingUser);
        assertFalse(isNonExistingUser);
        assertFalse(isEmptyUser);
        assertThrows(DataAccessException.class, () ->  uDao.validate(nullUsername));

    }
    @Test
    public void validateFailed() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> uDao.validate(null));
    }

}
