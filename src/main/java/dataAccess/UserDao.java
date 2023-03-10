package dataAccess;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//data access objects
public class UserDao {
    /**
     *     object for establish connection
     */
    private final Connection conn;

    /**
     *     constructor for establish connection
     * @param conn
     * @throws SQLException
     */
    public UserDao(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     *
     *     all the java code to pass in user information when create a new user
     *     sql code and java code
     *     pass in all SQL columns
     * @param user object
     * @throws DataAccessException
     */
    public void createUser(User user) throws DataAccessException {
        String sql = "INSERT INTO USER (username, password, email, firstName, lastName,gender,personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an User into the database");

        }

    }

    /**
     *    use username to check if a user is in the system.
     * @param username

     * @return false  if user is in the system already
     * @throws DataAccessException
     */

    public boolean validateUsername(String username)throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            if(username == null)
            {
                throw new SQLException();
            }
            stmt.setString(1,username);
            if(stmt.executeQuery().next())
            {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an user in the database");
        }

    }
    public boolean validatePassword(String username, String password)throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            if(username == null|| password == null)
            {
                throw new SQLException();
            }
            stmt.setString(1,username);
            stmt.setString(2,password);
            if(stmt.executeQuery().next())
            {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an user in the database");
        }

    }


    /**
     * grab the user info by the userID
     * @param username
     * @return user object
     */
    public User find(String username)throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            } else {
                    throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an user in the database");
        }
    }

    /**
     * clear all user in the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM User;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

    /**
     * delete a user based on username
     * @param username
     */
    public void clearsingleuser(String username) throws DataAccessException {
        String sql = "DELETE FROM User WHERE username = ?"; // Changed to DELETE statement, removed semicolon
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate(); // Changed to executeUpdate() to execute DELETE statement
            if (rowsAffected == 0) { // Check if any rows were affected
                throw new SQLException("User with username " + username + " not found"); // Throw exception if no rows were affected
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting User from the database"); // Changed error message
        }
    }

}
