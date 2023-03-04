package requestAndResult;

/**
 * pass out register results
 *
 */

public class RegisterResult {
    /**
     *     status of success
     */


    private String message;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
