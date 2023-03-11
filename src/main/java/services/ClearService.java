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
            clearResult.setMessage("Clear succeeded.");
            clearResult.setSuccess(true);
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            clearResult.setMessage("Error:[Internal server error]");
            clearResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            db.closeConnection(true);
            return clearResult;
        }

    }

}

