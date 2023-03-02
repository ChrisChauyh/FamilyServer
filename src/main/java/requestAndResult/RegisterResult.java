package requestAndResult;

/**
 * pass out register results
 *
 */

public class RegisterResult {
    /**
     *     status of success
     */


    private Boolean isSucccess;
    /**
     *     output messages
     */
    private String message;


    private void successMessage()
    {

    }

    private void failMessage()
    {

    }

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
