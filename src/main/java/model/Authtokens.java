package model;

import java.util.Objects;

public class Authtokens {
private String authtoken;
private String username;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtokens that = (Authtokens) o;
        return authtoken.equals(that.authtoken) && username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }
}
