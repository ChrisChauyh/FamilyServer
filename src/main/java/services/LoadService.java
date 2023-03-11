package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import requestAndResult.LoadRequest;
import requestAndResult.LoadResult;

import java.sql.Connection;
import java.sql.SQLException;

public class LoadService extends ClearService {
    LoadResult loadResult = new LoadResult();
    Database db = new Database();
    private int countUsers = 0;
    private int countPersons = 0;
    private int countEvents = 0;

    public LoadResult load(LoadRequest request) throws DataAccessException, SQLException {

        try {
            db.openConnection();
            System.out.println("Start load handler");
            clear();
            if (clearResult.getSuccess()) {
                Connection conn = db.getConnection();
                PersonDao personDao = new PersonDao(conn);
                EventDao eventDao = new EventDao(conn);
                UserDao userDao = new UserDao(conn);
                if (request.getUsers() != null && request.getPersons() != null && request.getEvents() != null) {
                    for (User user : request.getUsers()) {
                        userDao.createUser(user);
                        countUsers++;
                    }
                    for (Person person : request.getPersons()) {
                        personDao.createPerson(person);
                        countPersons++;
                    }
                    for (Event event : request.getEvents()) {
                        eventDao.insert(event);
                        countEvents++;
                    }
                }else{
                    throw new DataAccessException("Invalid request data (missing values, invalid values, etc.)");
                }
            }

            loadResult.setMessage("Successfully added "+ countUsers +" users, "+ countPersons +" persons, and "+ countEvents +" events to the database.");
            loadResult.setSuccess(true);
            db.closeConnection(true);
            return loadResult;
        } catch (SQLException e) {
            loadResult.setMessage("Error:Internal server error]");
            loadResult.setSuccess(false);
            db.closeConnection(false);

        }catch (DataAccessException e){
            loadResult.setMessage("Error:["+ e.getMessage()+ "]");
            loadResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return loadResult;
        }
    }

}
