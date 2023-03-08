package requestAndResult;

import model.Person;

public class PersonResult {
    private Person[] data;
    private String message;

    private Boolean success;
    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }



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
