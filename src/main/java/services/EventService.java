package services;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import model.Event;
import requestAndResult.EventResult;

import java.sql.Connection;
import java.util.ArrayList;

public class EventService {
    EventResult eventResult = new EventResult();
    Database db = new Database();
    public EventResult load(String authToken) throws DataAccessException {
        try{
                db.openConnection();
                System.out.println("Start event handler");
                Connection conn = db.getConnection();
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                EventDao eventDao = new EventDao(conn);

                //authToken to username
                String username = authTokenDao.getUserbyTokens(authToken);

                //print out all associated usernames
                ArrayList<Event> eventList = eventDao.findallEvents(username);
                if(eventList.size()<1)
                {
                    throw new Exception("Error:[Internal server error]");
                }
            if(authToken == null || authToken ==""|| username == null || username =="")
            {
                throw new DataAccessException("Invalid auth token");
            }else{
                eventResult.setData(eventList);
                eventResult.setSuccess(true);
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            eventResult.setMessage("Error:[Invalid auth token]");
            eventResult.setSuccess(false);
            db.closeConnection(false);
        }catch (Exception e)
        {
            e.printStackTrace();
            eventResult.setMessage(e.getMessage());
            eventResult.setSuccess(false);
            db.closeConnection(false);
        } finally {
            return eventResult;
        }
    }


}
