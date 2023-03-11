package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.Person;
import requestAndResult.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonService {
    PersonResult personResult = new PersonResult();
    Database db = new Database();
    public PersonResult load(String authToken) throws DataAccessException, SQLException{
        try{
            db.openConnection();
            Connection conn = db.getConnection();

                System.out.println("Start person handler");

                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                PersonDao personDao = new PersonDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);

                //print out all associated usernames
                ArrayList<Person> personList = personDao.findallPersons(username);
            if(authToken == null || authToken ==""|| username == null || username =="")
            {
               throw new DataAccessException("Invalid auth token");
            }else{
                personResult.setData(personList);
                personResult.setSuccess(true);
                db.closeConnection(true);
            }

        } catch (DataAccessException e) {
            personResult.setMessage("Error:[Invalid auth token]");
            personResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return personResult;
        }
    }


}
