package requestAndResult;

import handlers.RegisterHandler;

/**
 * pass out register results
 *
 */

public class RegisterResult extends RegisterHandler {
    /**
     *     status of success
     */


    private Boolean isSucccess;
    /**
     *     output messages
     */
    private String message;

    public Boolean getSucccess() {
        return isSucccess;
    }

    public void setSucccess(Boolean succcess) {
        isSucccess = succcess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
