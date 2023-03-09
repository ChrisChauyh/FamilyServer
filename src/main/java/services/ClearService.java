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
            db.openConnection();
            Connection conn = db.getConnection();
            //clear all tokens
            new PersonDao(conn).clear();
            new EventDao(conn).clear();
            new UserDao(conn).clear();
            new AuthTokenDao(conn).clearToken();
            db.closeConnection(true);
            clearResult.setMessage("Clear succeeded.");
            clearResult.setSuccess(true);

            return clearResult;
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            clearResult.setMessage("Error:[" + e.getMessage() + "]");
            clearResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return clearResult;
        }

    }

}

