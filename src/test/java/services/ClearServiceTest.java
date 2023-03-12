package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.ClearResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ClearServiceTest {

    private Database db;

    @BeforeEach
    public void setup() throws DataAccessException, SQLException
    {

        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personDao.clear();;
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();
        personDao.createPerson(new Person("Ting1357", "Ting", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135"));

        eventDao.insert(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        userDao.createUser(new User("Chris", "asdasd", "Chris@gmail.com",
                "YH", "Chau", "m", "Chris1357"));
        authTokenDao.generateToken("Chris");
        db.closeConnection(true);
    }
    @AfterEach
    public void tearDown()
    {

    }
    @Test
    public void ClearServiceTestPositive() throws DataAccessException, SQLException {
        ClearService clearService = new ClearService();
        ClearResult clearResult =clearService.clear();
        assertEquals(true,clearResult.getSuccess());
    }

}