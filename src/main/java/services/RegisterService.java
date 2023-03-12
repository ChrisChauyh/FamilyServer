package services;

import dataAccess.*;
import model.User;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * a service to create new user
 */
public class RegisterService {
    /**
     * register user, create 4 generation of persons , also create an authtoken
     *
     * @param request
     * @return Registerresult object
     */
    RegisterResult registerResult = new RegisterResult();
    Database db = new Database();

    public RegisterResult register(RegisterRequest request) throws DataAccessException, SQLException, FileNotFoundException {
        /**
         * Creates a new user account (user row in the database)
         * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
         * Logs the user in
         * Returns an authtoken
         */
        try {
            System.out.println("Start register handler.");
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            UserDao userDao = new UserDao(conn);
            String[] fields = {"username", "password", "gender", "email", "firstName", "lastName"};
            for (String field : fields) {
                if (getField(request, field) == null ||getField(request, field) == "" ) {
                    throw new Exception("Error[Request property missing or has invalid value]");
                }
            }

            //create authtoken and a new user
            if (!userDao.validateUsername(request.getUsername())) {
                User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), UUID.randomUUID().toString());
                userDao.createUser(user);
                String curAuthToken = authTokenDao.generateToken(user.getUsername());
                registerResult.setAuthtoken(curAuthToken);
                registerResult.setUsername(user.getUsername());
                registerResult.setPersonID(user.getPersonID());
                registerResult.setSuccess(true);
                db.closeConnection(true);
                FillService fillService = new FillService();
                fillService.fill(registerResult.getUsername(),4);

            } else {
                registerResult.setMessage("Error:[Username already taken by another user]");
                registerResult.setSuccess(false);
                db.closeConnection(false);
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            registerResult.setMessage("Error:[Internal server error]");
            registerResult.setSuccess(false);
            db.closeConnection(false);
        }catch(Exception e) {
            registerResult.setMessage(e.getMessage());
            registerResult.setSuccess(false);
            db.closeConnection(false);
        }finally
         {
            return registerResult;
        }

    }
    private Object getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }
}
