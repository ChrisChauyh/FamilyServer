package services;

import dataAccess.*;
import model.Person;
import requestAndResult.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonService {
    PersonResult personResult = new PersonResult();
    Database db = new Database();
    public PersonResult load(String authToken) throws DataAccessException, SQLException{
        try{
            if(authToken != null || authToken !="")
            {
                System.out.println("Start person handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                PersonDao personDao = new PersonDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);

                //print out all associated usernames
                List<Person> personList = personDao.findallPersons(username);
                Person[] tmpPerson = personList.toArray(new Person[personList.size()]);
                personResult.setData(tmpPerson);
                personResult.setSuccess(true);
            }else{
                personResult.setMessage("Error:[Invalid auth token]");
                personResult.setSuccess(false);
            }
        } catch (DataAccessException e) {
            personResult.setMessage("Error:[" + e +"]");
            personResult.setSuccess(false);
        } finally {
            db.closeConnection(false);
            return personResult;
        }
    }


}
