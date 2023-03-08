package services;

import dataAccess.*;
import model.Event;
import requestAndResult.EventResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventService {
    EventResult eventResult = new EventResult();
    Database db = new Database();
    public EventResult load(String authToken) throws DataAccessException, SQLException {
        try{
            if(authToken != null || authToken !="")
            {
                System.out.println("Start event handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                PersonDao personDao = new PersonDao(conn);
                EventDao eventDao = new EventDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);

                //print out all associated usernames
                List<Event> eventList = eventDao.findallEvents(username);
                Event[] tmpEvent = eventList.toArray(new Event[eventList.size()]);
                eventResult.setData(tmpEvent);
                eventResult.setSuccess(true);
                /**
                 * Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
                 */

            }else{
                eventResult.setMessage("Error:[Invalid auth token]");
                eventResult.setSuccess(false);
            }
        } catch (DataAccessException e) {
            eventResult.setMessage("Error:[" + e +"]");
            eventResult.setSuccess(false);
        } finally {
            db.closeConnection(false);
            return eventResult;
        }
    }


}
