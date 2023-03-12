package services;

import dataAccess.*;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
class FillServiceTest {
    private FillResult fillResult = new FillResult();

    private Database db;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
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
        userDao.createUser(new User("Ting", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        db.closeConnection(true);


    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    public void fillServicePositive() throws SQLException, FileNotFoundException, DataAccessException {
        FillService fillService = new FillService();
        fillResult = fillService.fill("Ting", 2);
        assertEquals(true, fillResult.getMessage().contains("added 7 persons"));
        assertEquals(true, fillResult.getMessage().contains("and 19 events"));
        assertEquals(true, fillResult.getSuccess());
        fillResult.setSuccess(null);
        fillResult.setMessage(null);
        fillResult = fillService.fill("Ting", 10);
        assertEquals(true, fillResult.getMessage().contains("added 2047 persons"));
        assertEquals(true, fillResult.getMessage().contains("and 6139 events"));
        assertEquals(true, fillResult.getSuccess());
    }

    @Test
    public void fillServiceNegative() throws SQLException, FileNotFoundException, DataAccessException {
        FillService fillService = new FillService();
        fillResult = fillService.fill("Tingasd", 2);
        assertEquals("Error:[Wrong username or generations parameter]",fillResult.getMessage());
        fillResult = fillService.fill(null, null);
        assertEquals("Error:[Invalid username or generations parameter]",fillResult.getMessage());


    }
}