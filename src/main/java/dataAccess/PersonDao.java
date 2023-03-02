package dataAccess;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public void createPerson(Person person) throws DataAccessException
    {
        //create a person based on the name
        String sql = "INSERT INTO PERSON (personID, associatedUsername, firstName, lastName,gender,fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }

    }

    Person find(String personID) throws DataAccessException
    {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),rs.getString("firstName"),
                        rs.getString("lastName"),rs.getString("gender"),
                        rs.getString("fatherID"),rs.getString("motherID"),rs.getString("spouseID"));
                return person;
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    public List<Person> findallPersons(String associatedUsername) throws DataAccessException{
        //find all person that a user has
        Person person;
        ResultSet rs;
        List<Person> all = new LinkedList<>();
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            if(!rs.next()) {
                throw new SQLException();
            }
            while(rs.next()) {
                person = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),rs.getString("firstName"),
                        rs.getString("lastName"),rs.getString("gender"),
                        rs.getString("fatherID"),rs.getString("motherID"),rs.getString("spouseID"));
                all.add(person);
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all events in the database");
        }
    }

//clear all person from server
    public void clear() throws DataAccessException
    {//clear all person
        String sql = "DELETE FROM Person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
//clear single person based on the person ID
    public void clearsingle(String personID) throws DataAccessException
    {
        String sql = "DELETE FROM Person WHERE personID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, personID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) { // Check if any rows were affected
                throw new SQLException("Person with person ID " + personID + " not found"); // Throw exception if no rows were affected
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the single person table");
        }

    }
}
