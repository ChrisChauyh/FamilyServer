package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.Person;
import requestAndResult.PersonIDResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonIDService {
    PersonIDResult personIDResult = new PersonIDResult();
    Database db = new Database();

    public PersonIDResult load(String authToken, String personID) throws DataAccessException, SQLException {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            if (authToken != null || authToken != ""||personID!= ""|| personID != null) {

                System.out.println("Start personID handler");

                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                PersonDao personDao = new PersonDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);
                //print out all associated usernames
                ArrayList<Person> personList = personDao.findallPersons(username);
                Person temp = null;
                for (Person person : personList) {

                    if (person.getPersonID().equals(personID)) {
                        temp = person;
                        personIDResult.setAssociatedUsername(person.getAssociatedUsername());
                        personIDResult.setPersonID(person.getPersonID());
                        personIDResult.setFirstName(person.getFirstName());
                        personIDResult.setLastName(person.getLastName());
                        personIDResult.setGender(person.getGender());
                        if(person.getFatherID() != null)
                        {
                            personIDResult.setFatherID(person.getFatherID());
                        }
                        if(person.getMotherID() != null)
                        {
                            personIDResult.setMotherID(person.getMotherID());
                        }
                        if(person.getSpouseID() != null)
                        {
                            personIDResult.setSpouseID(person.getSpouseID());
                        }
                        personIDResult.setSuccess(true);
                        personIDResult.setMessage(null);
                        db.closeConnection(true);
                        break;
                    }
                }
                if(temp != null)
                {
                    db.closeConnection(true);
                }else{
                    personIDResult.setMessage("Error:[Requested person does not belong to this user]");
                    personIDResult.setSuccess(false);
                    db.closeConnection(false);
                }
            }if (personID.equals("")|| personID == null) {
                personIDResult.setMessage("Error:[Invalid personID parameter]");
                personIDResult.setSuccess(false);
                db.closeConnection(false);
            } else if(authToken.equals("")|| authToken == null) {
                personIDResult.setMessage("Error:[Invalid auth token]");
                personIDResult.setSuccess(false);
                db.closeConnection(false);
            }

        } catch (DataAccessException e) {
            personIDResult.setMessage("Error:[Invalid auth token]");
            personIDResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return personIDResult;
        }
    }
}
