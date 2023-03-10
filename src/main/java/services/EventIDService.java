package services;

import dataAccess.*;
import model.Event;
import requestAndResult.EventIDResult;

import java.sql.Connection;
import java.sql.SQLException;

public class EventIDService {

    EventIDResult eventIDResult = new EventIDResult();
    Database db = new Database();

    public EventIDResult load(String authToken, String eventID) throws DataAccessException, SQLException {
        try {
            if (authToken != null || authToken != "") {
                db.openConnection();
                System.out.println("Start eventID handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                EventDao eventDao = new EventDao(conn);
                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);
                Event event = eventDao.find(eventID);
                if (event.getAssociatedUsername() == username) {
                    eventIDResult.setAssociatedUsername(event.getAssociatedUsername());
                    eventIDResult.setEventID(event.getEventID());
                    eventIDResult.setPersonID(event.getPersonID());
                    eventIDResult.setLatitude(event.getLatitude());
                    eventIDResult.setLongitude(event.getLongitude());
                    eventIDResult.setCountry(event.getCountry());
                    eventIDResult.setCity(event.getCity());
                    eventIDResult.setEventType(event.getEventType());
                    eventIDResult.setYear(event.getYear());
                    db.closeConnection(true);
                    eventIDResult.setSuccess(true);
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
        } finally {
            return eventIDResult;
        }
    }
}