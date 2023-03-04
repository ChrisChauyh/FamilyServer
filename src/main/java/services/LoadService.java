package services;

import dataAccess.DataAccessException;
import dataAccess.Database;
import requestAndResult.LoadResult;

import java.sql.SQLException;

public class LoadService {
    LoadResult loadResult = new LoadResult();
    Database db = new Database();
    public LoadResult load() throws DataAccessException, SQLException{
//        try{
//
////            public Boolean checkValues()
////            {
////                return (getUsers()!= null && getEvents()!=null && getPersons()!= null);
////            }
//        }catch (SQLException | DataAccessException e){
//            loadResult.setMessage("Error:[" + e.getMessage() + "]");
//            loadResult.setSuccess(false);
//
//        }
        return loadResult;
    }

}
