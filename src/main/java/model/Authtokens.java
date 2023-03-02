package model;

import java.util.Objects;
/**
 * a class for authentication Token
 */

public class Authtokens {
    /**
     * the unique token that user has
     */

private String authtoken;
    /**
     *  the associated username
     */
private String username;
    /**
     * create constructor for authtoken and username
     * @param authtoken
     * @param username
     */
    public Authtokens(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * chack two objects if they are equal or not
     * @param o to compare
     * @return return a boolean to show if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtokens that = (Authtokens) o;
        return authtoken.equals(that.authtoken) && username.equals(that.username);
    }
    /**
     * sinple hascode method
     * @return hash for an object
     */
    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }
}
