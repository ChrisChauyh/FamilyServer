package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.User;
import requestAndResult.LoginRequest;
import requestAndResult.LoginResult;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {
    LoginResult loginResult = new LoginResult();
    Database db = new Database();

    public LoginResult login(LoginRequest request) throws DataAccessException, SQLException {
        try{
            System.out.println("Start login handler");
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            if(userDao.validate(request.getUsername(), request.getPassword())){
                String tempToken = authTokenDao.generateToken(request.getUsername());
                String tempUsername = authTokenDao.getUserbyTokens(tempToken);
                User tempuser = userDao.find(request.getUsername());
                if(tempUsername == request.getUsername())
                {
                    loginResult.setAuthtoken(tempToken);
                    loginResult.setUsername(tempuser.getUsername());
                    loginResult.setPersonID(tempuser.getPersonID());
                    loginResult.setSuccess(true);
                }
            }else{
                loginResult.setMessage("Error:[Wrong Username or password]");
                loginResult.setSuccess(false);
            }

        } catch (DataAccessException | SQLException e) {
            loginResult.setMessage("Error:[Request property missing or has invalid value.]");
            loginResult.setSuccess(false);
        } finally {
            db.closeConnection(false);
            return loginResult;
        }
    }
}
