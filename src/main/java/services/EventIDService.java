package services;

import dataAccess.*;
import model.Event;
import requestAndResult.EventIDResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventIDService {

    EventIDResult eventIDResult = new EventIDResult();
    Database db = new Database();

    public EventIDResult load(String authToken, String eventID) throws DataAccessException, SQLException {
        try {
            db.openConnection();
            System.out.println("Start eventID handler");
            Connection conn = db.getConnection();
            if (authToken != null || authToken != "") {

                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                EventDao eventDao = new EventDao(conn);
                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);
                ArrayList<Event> eventList = eventDao.findallEvents(username);
                Event temp = null;
                for(Event event : eventList)
                {
                    if(event.getEventID().equals(eventID))
                    {
                        temp = event;
                        eventIDResult.setAssociatedUsername(event.getAssociatedUsername());
                        eventIDResult.setEventID(event.getEventID());
                        eventIDResult.setPersonID(event.getPersonID());
                        eventIDResult.setLatitude(event.getLatitude());
                        eventIDResult.setLongitude(event.getLongitude());
                        eventIDResult.setCountry(event.getCountry());
                        eventIDResult.setCity(event.getCity());
                        eventIDResult.setEventType(event.getEventType());
                        eventIDResult.setYear(event.getYear());
                        eventIDResult.setSuccess(true);
                        break;
                    }
                }
                if(temp != null)
                {
                    db.closeConnection(true);
                }else{
                    eventIDResult.setMessage("Error:[Invalid eventID parameter]");
                    eventIDResult.setSuccess(false);
                    db.closeConnection(false);
                }

            } else if (eventID == "") {
                eventIDResult.setMessage("Error:[Invalid eventID parameter]");
                eventIDResult.setSuccess(false);
                db.closeConnection(false);
            } else {
                eventIDResult.setMessage("Error:[Invalid auth token]");
                eventIDResult.setSuccess(false);
                db.closeConnection(false);
            }
        } catch (DataAccessException e) {
            eventIDResult.setMessage("Error:[" + e + "]");
            eventIDResult.setSuccess(false);
            db.closeConnection(false);
        }

        // Close the database connection
        finally {
            return eventIDResult;
        }
    }
}