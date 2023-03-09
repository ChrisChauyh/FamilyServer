package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import requestAndResult.FillResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FillService extends Database {
    FillResult fillResult = new FillResult();
    Database db = new Database();
    Connection conn = db.getConnection();
    PersonDao personDao = new PersonDao(conn);
    EventDao eventDao = new EventDao(conn);
    UserDao userDao = new UserDao(conn);

    Integer personsAdded = 0;
    Integer eventsAdded = 0;

    public FillService() throws DataAccessException, SQLException {

    }

    public FillResult fill(String username, Integer gens) throws DataAccessException, SQLException {
/**
 * Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given username, it is deleted.
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
 * More details can be found in the earlier section titled “Family History Information Generation”
 */try {
            System.out.println("Start fill handler");

            //If there is any data in the database already
            //associated with the given username, it is deleted.
            List<Person> existPersons = personDao.findallPersons(username);
            List<Event> existEvents = eventDao.findallEvents(username);
            for (Person person : existPersons) {
                if (person.getAssociatedUsername() == username) {
                    personDao.clearsingle(person.getPersonID());
                }
            }
            for (Event event : existEvents) {
                if (event.getAssociatedUsername() == username) {
                    eventDao.clearsingle(event.getEventID());
                }
            }


            User curUser = userDao.find(username);
            Person person = new Person(curUser.getPersonID(), curUser.getUsername(),
                    curUser.getFirstName(), curUser.getLastName(), curUser.getGender(),
                    UUID.randomUUID().toString(), UUID.randomUUID().toString(), "");
            personsAdded ++;
            generateEvents(person);
            //Generate current user's generations
            ArrayList<Person> persons = new ArrayList<>();
            generatePersons(person, gens, persons);

            fillResult.setMessage("Successfully added "+ personsAdded +" persons, "+ eventsAdded +" events to the database.");
            fillResult.setSuccess(true);

        } catch (DataAccessException | SQLException e) {
            fillResult.setMessage("Error:[" + e + "]");
            fillResult.setSuccess(false);
        } finally {
            db.closeConnection(false);
            return fillResult;
        }
    }

    private ArrayList<Person> generatePersons(Person person, int gens, ArrayList<Person> totalPersons) throws SQLException, DataAccessException {
        if (gens == 0) {
            return totalPersons;
        }
        //Parents
        Person father = new Person(person.getFatherID(), person.getAssociatedUsername(), "random Firstname", person.getLastName(), "m",UUID.randomUUID().toString(),UUID.randomUUID().toString(),"");
        Person mother = new Person(person.getMotherID(),  person.getAssociatedUsername(),"random Firstname", "randomLastname","f",UUID.randomUUID().toString(),UUID.randomUUID().toString(),"");
        //set them as each other's spouse
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        //SQL create both persons
        personDao.createPerson(father);
        totalPersons.add(father);
        personDao.createPerson(mother);
        totalPersons.add(mother);
        personsAdded+=2;
        //call generate events
        generateEvents(father);
        generateEvents(mother);
        //recursive calls
        generatePersons(father, gens - 1,totalPersons);
        generatePersons(mother, gens - 1,totalPersons);
        return totalPersons;
    }

    private void generateEvents(Person person) throws SQLException, DataAccessException {
        Event birth = new Event("Birth", person.getAssociatedUsername(), person.getPersonID(), 3.14F,3.14F,"country","city","eventtype",2000);
        Event marriage = new Event("Marriage", person.getAssociatedUsername(), person.getPersonID(), 3.14F,3.14F,"country","city","eventtype",2000);
        Event death = new Event("Death", person.getAssociatedUsername(), person.getPersonID(), 3.14F,3.14F,"country","city","eventtype",2000);
        eventDao.insert(birth);
        eventDao.insert(marriage);
        eventDao.insert(death);
        eventsAdded +=3;
    }


}

