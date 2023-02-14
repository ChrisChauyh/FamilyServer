package DataAccess;


import model.Authtokens;

import java.sql.Connection;

public class AuthTokenDao {
private final Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    Authtokens getUserbyTokens(String tokens)
    {
        //grab username and password using the token
        Authtokens authtokens = null;
        return authtokens;
    }
    String generateToken(String username)
    {
        //generate Tokens based on the username
        String generatedToken ="";
        return generatedToken;
    }
    public void clearToken() throws DataAccessException
    {
        //clear the token they currently have
    }

}
