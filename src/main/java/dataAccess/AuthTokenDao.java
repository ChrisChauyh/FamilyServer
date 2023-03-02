package dataAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthTokenDao {
    private final Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    String getUserbyTokens(String token) throws DataAccessException {
        String username;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            return username;
            }else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an user in the database");
        }
    }

    String generateToken(String username) throws DataAccessException {
        //generate Tokens based on the username
        String sql = "INSERT INTO AUTHTOKEN (authtoken, username) VALUES(?,?)";
        String token = UUID.randomUUID().toString();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, username);
            stmt.executeUpdate();
            return token;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while creating an authtoken into the database");
        }
    }

    public void clearToken() throws DataAccessException {
        //clear tokens they currently have
        String sql = "DELETE FROM Authtoken;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }

}
