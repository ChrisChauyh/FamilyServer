package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import model.Event;
import requestAndResult.EventResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventService {
    EventResult eventResult = new EventResult();
    Database db = new Database();
    public EventResult load(String authToken) throws DataAccessException, SQLException {
        try{
            if(authToken != null || authToken !="")
            {
                db.openConnection();
                System.out.println("Start event handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                EventDao eventDao = new EventDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);

                //print out all associated usernames
                ArrayList<Event> eventList = eventDao.findallEvents(username);
                eventResult.setData(eventList);
                eventResult.setSuccess(true);
                db.closeConnection(true);
                /**
                 * Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
                 */

            }else{
                eventResult.setMessage("Error:[Invalid auth token]");
                eventResult.setSuccess(false);
                db.closeConnection(false);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            eventResult.setMessage("Error:[" + e +"]");
            eventResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return eventResult;
        }
    }


}
