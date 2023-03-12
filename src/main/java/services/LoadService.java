package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import requestAndResult.ClearResult;
import requestAndResult.LoadRequest;
import requestAndResult.LoadResult;

import java.sql.Connection;
import java.sql.SQLException;

public class LoadService{
    LoadResult loadResult = new LoadResult();
    Database db = new Database();
    private int countUsers = 0;
    private int countPersons = 0;
    private int countEvents = 0;
    private String[] personFields= {"personID", "associatedUsername", "firstName", "lastName", "gender"};
    private String[] eventFields = {"eventID","associatedUsername","personID","latitude","longitude","country","city","eventType","year"};
    private String[] userFields = {"username","password","email","firstName","lastName","gender","personID"};
    public LoadResult load(LoadRequest request) throws DataAccessException, SQLException {

        try {

            System.out.println("Start load handler");
            ClearService clearService = new ClearService();
            ClearResult clearResult = clearService.clear();
            if(clearResult.getSuccess()){
                db.openConnection();
                Connection conn = db.getConnection();
                PersonDao personDao = new PersonDao(conn);
                EventDao eventDao = new EventDao(conn);
                UserDao userDao = new UserDao(conn);
                if (request.getUsers() != null && request.getPersons() != null && request.getEvents() != null) {
                    for (User user : request.getUsers()) {
                        if(user.getLastName().isBlank() || user.getFirstName().isBlank()|| user.getPersonID().isBlank()|| user.getPassword().isBlank() || user.getGender().isBlank()|| user.getUsername().isBlank()|| user.getEmail().isBlank()) {
                            throw new DataAccessException("Error[Invalid request data (missing values, invalid values, etc.)]");
                        }
                        userDao.createUser(user);
                        countUsers++;
                    }
                    for (Person person : request.getPersons()) {
                        if(person.getAssociatedUsername().isBlank() || person.getPersonID().isBlank()|| person.getFirstName().isBlank()|| person.getLastName().isBlank()|| person.getGender().isBlank()) {
                            throw new DataAccessException("Invalid request data (missing values, invalid values, etc.)");
                        }
                        personDao.createPerson(person);
                        countPersons++;
                    }
                    for (Event event : request.getEvents()) {
                        if(event.getEventID().isBlank() || event.getAssociatedUsername().isBlank()|| event.getLatitude() == null|| event.getLongitude()== null|| event.getCountry().isBlank()|| event.getCity().isBlank()|| event.getEventType().isBlank()|| event.getPersonID().isBlank()|| event.getYear() ==0 || event.getYear() == null) {
                            throw new DataAccessException("Invalid request data (missing values, invalid values, etc.)");
                        }
                        eventDao.insert(event);
                        countEvents++;
                    }

                }else{
                    throw new DataAccessException("Invalid request data (missing values, invalid values, etc.)");
                }
                loadResult.setMessage("Successfully added "+ countUsers +" users, "+ countPersons +" persons, and "+ countEvents +" events to the database.");
                loadResult.setSuccess(true);
                db.closeConnection(true);

            }else{
                loadResult.setMessage("Error:Internal server error]");
                loadResult.setSuccess(false);
                db.closeConnection(false);
            }

        } catch (SQLException e) {
            loadResult.setMessage("Error:Internal server error]");
            loadResult.setSuccess(false);
            db.closeConnection(false);

        }catch (DataAccessException e){
            loadResult.setMessage("Error:["+ e.getMessage()+ "]");
            loadResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            if(db.getConnection() != null)
            {
                db.closeConnection(false);
            }
            return loadResult;
        }
    }
}
