package requestAndResult;

public class FillResult {
    //objects to json
    private Boolean isSucccess;
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
