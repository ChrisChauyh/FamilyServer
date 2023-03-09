package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.User;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * a service to create new user
 */
public class RegisterService{
    /**
     * register user, create 4 generation of persons , also create an authtoken
     * @param request
     * @return Registerresult object
     */
    RegisterResult registerResult = new RegisterResult();
    Database db = new Database();
    public RegisterResult register(RegisterRequest request) throws DataAccessException, SQLException {
            System.out.println("Start register handler.");
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            UserDao userDao = new UserDao(conn);
            String curUsername = request.getUsername();
            //create authtoken
            String curAuthToken = authTokenDao.generateToken(curUsername);

            if(!userDao.validate(curUsername,"test"))
            {
                User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), curAuthToken);
                userDao.createUser(user);

            }
/**
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */

        }
    }

}
