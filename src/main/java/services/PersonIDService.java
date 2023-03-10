package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.Person;
import requestAndResult.PersonIDResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonIDService {
    PersonIDResult personIDResult = new PersonIDResult();
    Database db = new Database();

    public PersonIDResult load(String authToken, String personID) throws DataAccessException, SQLException {
        try {
            if (authToken != null || authToken != "") {
                db.openConnection();
                System.out.println("Start personID handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                PersonDao personDao = new PersonDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);
                //print out all associated usernames
                List<Person> personList = personDao.findallPersons(username);
                Person[] tmpPersons = personList.toArray(new Person[personList.size()]);
                for (Person person : tmpPersons) {
                    if (personID == person.getPersonID()) {
                        personIDResult.setAssociatedUsername(person.getAssociatedUsername());
                        personIDResult.setPersonID(person.getPersonID());
                        personIDResult.setFirstName(person.getFirstName());
                        personIDResult.setLastName(person.getLastName());
                        personIDResult.setGender(person.getGender());
                        if(person.getFatherID() != "")
                        {
                            personIDResult.setFatherID(person.getFatherID());
                        }
                        if(person.getMotherID() != "")
                        {
                            personIDResult.setMotherID(person.getMotherID());
                        }
                        if(person.getSpouseID() != "")
                        {
                            personIDResult.setSpouseID(person.getSpouseID());
                        }
                        personIDResult.setSuccess(true);
                        db.closeConnection(true);
                    }
                }
            } else if (personID == "") {
                personIDResult.setMessage("Error:[Invalid personID parameter]");
                personIDResult.setSuccess(false);
                db.closeConnection(false);
            } else {
                personIDResult.setMessage("Error:[Invalid auth token]");
                personIDResult.setSuccess(false);
                db.closeConnection(false);
            }

        } catch (DataAccessException e) {
            personIDResult.setMessage("Error:[" + e + "]");
            personIDResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return personIDResult;
        }
    }
}
