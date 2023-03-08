package requestAndResult;

public class PersonIDResult {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private String message;
    private boolean success;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

/**
     * Success Response Body:
     * {
     * 	"associatedUsername":"susan",	// string
     * 	"personID":"7255e93e",		// string
     * 	"firstName":"Stuart",			// string
     * 	"lastName":"Klocke",			// string
     * 	"gender":"m",				// string: "f" or "m"
     * 	"fatherID": "7255e93e",	// string [OPTIONAL, can be missing]
     * 	"motherID":"d3gz214j",	// string [OPTIONAL, can be missing]
     * 	"spouseID":"f42126c8"	,	// string [OPTIONAL, can be missing]
     * "success":true				// boolean
     * }
     *
     * Error Response Body:
     * {
     * 	"message":"Error:[Description of the error]",	// string
     * "success":false							// boolean
     * }
     */
}
