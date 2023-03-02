package dataAccess;

import model.Authtokens;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthTokenDaoTest {
    private Database db;
    private Authtokens firstAuth;
    private Authtokens secondAuth;
    private AuthTokenDao aDao;
    private String first;
    private String second;


    @BeforeEach
    public void setUp() throws DataAccessException, SQLException {
        db = new Database();
        first = "Test1";
        second = "Test2";
        Connection conn = db.getConnection();
        aDao = new AuthTokenDao(conn);
        aDao.clearToken();
    }

    @AfterEach
    public void tearDown()
            /*
            this is after each test case
             */ {
        db.closeConnection(false);
    }

    @Test
    void getUserbyTokensPass() throws DataAccessException {
        String tempauth1 = aDao.generateToken(first);
        String tempauth2 = aDao.generateToken(first);
        assertEquals(first, aDao.getUserbyTokens(tempauth1));
        assertEquals(first, aDao.getUserbyTokens(tempauth2));
    }

    @Test
    void getUserbyTokensFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> aDao.getUserbyTokens(first));
    }

    @Test
    void generateTokenPass() throws DataAccessException {
        String tempauth1 = aDao.generateToken(first);
        String tempauth2 = aDao.generateToken(second);
        assertEquals(first, aDao.getUserbyTokens(tempauth1));
        assertEquals(second, aDao.getUserbyTokens(tempauth2));
    }

    @Test
    void clearTokenPass() throws DataAccessException {
        String tempauth1 = aDao.generateToken(first);
        String tempauth2 = aDao.generateToken(second);
        aDao.clearToken();
        assertThrows(DataAccessException.class, () -> aDao.getUserbyTokens(tempauth1));
        assertThrows(DataAccessException.class, () -> aDao.getUserbyTokens(tempauth2));

    }
}