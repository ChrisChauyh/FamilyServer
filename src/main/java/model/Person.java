package model;

import java.util.Objects;

/**
 * a class representing a person
 *
 */
public class Person {
    /**
     *the id refer to a user
     */
    private String personID;
    /**
     *the username that this person associated to
     */

    private String associatedUsername;
    /**
     *this person's first name
     */

    private String firstName;
    /**
     *this person's lastname
     */

    private String lastName;
    /**
     *
     */
    //this person's gender
    private String gender;
    /**
     *this person's fatherID
     */
    private String fatherID;
    /**
     *this person's mother ID
     */
    private String motherID;
    /**
     *this person's spouse ID
     */
    private String spouseID;
    /**
     * Create constructor for the whole person object
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    /**
     * chack two objects if they are equal or not
     * @param o to compare
     * @return return a boolean to show if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) && associatedUsername.equals(person.associatedUsername) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && gender.equals(person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }
    /**
     * sinple hascode method
     * @return hash for an object
     */
    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
