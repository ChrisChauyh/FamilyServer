package DataAccess;

import model.User;
import java.sql.*;
public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    void createUser(User user)
    {
        //all the java code to pass in user information
//sql code and java code
        //pass in all SQL columns

    }
    boolean validate(String username, String password)
    {
        //use username and password to check if it is in the system.
        return false;
    }

    User getUserbyId(String userID)
    {
        //grab the user info by the userID
        User user = null;
        return user;
    }

    User getUserbyUsername(String username)
    {
        //grab the user info by the username
        User user = null;
        return user;
    }
    public void clearUser() throws DataAccessException{
        //clear a user in the database
    }

}
