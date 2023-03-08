package services;

import dataAccess.*;
import requestAndResult.ClearResult;

import java.sql.Connection;
import java.sql.SQLException;

public class ClearService {
    ClearResult clearResult = new ClearResult();
    Database db = new Database();

    public ClearResult clear() throws DataAccessException, SQLException {

        try {
            System.out.println("Start clear handler");

            Connection conn = db.getConnection();

            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            UserDao userDao = new UserDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            personDao.clear();
            eventDao.clear();
            userDao.clear();
            authTokenDao.clearToken();

            clearResult.setMessage("Clear succeeded.");
            clearResult.setSuccess(true);


            return clearResult;
        } catch (SQLException e) {
            clearResult.setMessage("Error:[" + e.getMessage() + "]");
            clearResult.setSuccess(false);
        } catch (DataAccessException e) {
            clearResult.setMessage("Error:[" + e.getMessage() + "]");
            clearResult.setSuccess(false);
        }
        finally {
            db.closeConnection(false);
            return clearResult;
        }

    }

}

