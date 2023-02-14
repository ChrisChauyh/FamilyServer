package DataAccess;

import model.Person;

import java.sql.Connection;
import java.util.List;

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public boolean createPerson(Person person)
    {
        //create a person based on the name
        return false;
    }

    public Person findbyPersonId(String personID)
    {
        //find one person
        Person person = null;
        return person;
    }

    public List<String> findallPersons(String username)
    {
        //find all person that user has
        List<String> allperson = null;
        return allperson;
    }

    public void deleteSinglePerson(String username)
    {
        //delete a person based on username
    }

    public void clearall()
    {
        //clear all persons
    }
}
