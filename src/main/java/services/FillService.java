package services;

import com.google.gson.Gson;
import dataAccess.*;
import json.*;
import json.LocationData;
import model.Event;
import model.Person;
import model.User;
import requestAndResult.FillResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FillService extends Database {
    FillResult fillResult = new FillResult();
    Database db = new Database();
    Connection conn = db.getConnection();
    PersonDao personDao = new PersonDao(conn);
    EventDao eventDao = new EventDao(conn);
    UserDao userDao = new UserDao(conn);
    Random random = new Random();

    Gson gson = new Gson();

    Fname fname = (Fname) readJson("src/main/java/json/fnames.json", Fname.class);
    Mname mname = (Mname) readJson("src/main/java/json/mnames.json", Mname.class);
    Sname sname = (Sname) readJson("src/main/java/json/snames.json", Sname.class);
    LocationData locationData = (LocationData) readJson("src/main/java/json/locations.json", LocationData.class);
    int personsAdded = 0;
    int eventsAdded = 0;

    public FillService() throws DataAccessException, SQLException, FileNotFoundException {

    }

    public FillResult fill(String username, Integer gens) throws DataAccessException, SQLException {
/**
 * Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given username, it is deleted.
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
 * More details can be found in the earlier section titled “Family History Information Generation”
 */
        try {
            System.out.println("Start fill handler");
            db.openConnection();
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
            int birthdate = 1998;
            //Generate current user's generations
            generatePerson(curUser.getGender().toString(), gens, curUser, birthdate);

            fillResult.setMessage("Successfully added " + personsAdded + " persons, " + eventsAdded + " events to the database.");
            fillResult.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException | SQLException e) {
            fillResult.setMessage("Error:[" + e + "]");
            fillResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return fillResult;
        }
    }

    private Person generatePerson(String gender, int generations, User curUser, int birthdate) throws SQLException, DataAccessException {
        //Parents
        Person father = null;
        Person mother = null;
        if (generations > 1) {
            mother = generatePerson("f", generations - 1, curUser, birthdate - random.nextInt(birthdate - (birthdate - 30)));
            father = generatePerson("m", generations - 1, curUser, birthdate - random.nextInt(birthdate - (birthdate - 30)));
            //set them to spouse ID
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());
            //add marriage events
            generateMarriage(father, mother);
        }
        Person person = new Person();
        person.setPersonID(UUID.randomUUID().toString());
        person.setGender(gender);
        person.setAssociatedUsername(curUser.getUsername());
        person.setFatherID(father.getPersonID());
        person.setMotherID(mother.getPersonID());
        if (gender.equals("f")) {
            person.setFirstName(fname.getData()[random.nextInt(fname.getData().length - 1)]);
        } else {
            person.setFirstName(mname.getData()[random.nextInt(mname.getData().length - 1)]);
        }
        person.setLastName(sname.getData()[random.nextInt(sname.getData().length - 1)]);
        //generate Birth and death events
        generateBDEvents(person, birthdate);
        //sql create person
        personDao.createPerson(person);
        personsAdded++;
        return person;
    }

    private void generateBDEvents(Person person, int birthdate) throws DataAccessException {
        Location birthLocation = locationData.getData()[random.nextInt()];
        Location deathLocation = locationData.getData()[random.nextInt()];
        Event birth = new Event(UUID.randomUUID().toString(), person.getAssociatedUsername(), person.getPersonID(), birthLocation.getLatitude(), birthLocation.getLongitude(), birthLocation.getCountry(), birthLocation.getCity(), "birth", birthdate);
        Event death = new Event(UUID.randomUUID().toString(), person.getAssociatedUsername(), person.getPersonID(), deathLocation.getLatitude(), deathLocation.getLongitude(), deathLocation.getCountry(), deathLocation.getCity(), "death", birthdate + 30);
        eventDao.insert(birth);
        eventDao.insert(death);
        eventsAdded += 2;
    }

    private void generateMarriage(Person father, Person mother) throws DataAccessException {
        Location location = locationData.getData()[random.nextInt()];
        Event marriagefather = new Event(UUID.randomUUID().toString(), father.getAssociatedUsername(), father.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "marriage", 2000);
        Event marriagemother = new Event(UUID.randomUUID().toString(), mother.getAssociatedUsername(), mother.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "marriage", 2000);
        eventDao.insert(marriagemother);
        eventDao.insert(marriagefather);
        eventsAdded += 2;
    }

    private Object readJson(String filepath, Class toJson) throws FileNotFoundException {
        File file = new File(filepath);
        FileReader fileReader = new FileReader(file);
        return gson.fromJson(fileReader, toJson);
    }
}

