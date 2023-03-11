package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class ClearServiceTest {

    private static Database db;
    private static Connection conn;
    private static ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException, SQLException
    {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        clearService = new ClearService();
        PersonDao personDao = new PersonDao(conn);
        personDao.createPerson(new Person("Ting1357", "Ting", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135"));
        EventDao eventDao = new EventDao(conn);
        eventDao.insert(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));  UserDao userDao = new UserDao(conn);
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.generateToken("Chris");
    }
    @AfterEach
    public void tearDown()
    {
        db.closeConnection(false);
    }
    @Test
    public void ClearServiceTestPositive() throws DataAccessException, SQLException {

        clearService.clear();
    }

    @Test
    public void ClearServiceTestNegative() throws SQLException, DataAccessException {
        clearService.clear();
        assertThrows(SQLException.class, ()->clearService.clear());
    }



}