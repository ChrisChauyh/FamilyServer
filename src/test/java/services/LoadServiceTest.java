package services;

import com.google.gson.Gson;
import dataAccess.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.LoadRequest;
import requestAndResult.LoadResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadServiceTest {
    private Database db;
    private LoadRequest loadRequest = new LoadRequest();
    private LoadResult loadResult = new LoadResult();
    Gson gson;
private String jsonText;
private String jsonText2;
private String jsonText3;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException, IOException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        gson = new Gson();
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        UserDao userDao = new UserDao(conn);
        personDao.clear();
        eventDao.clear();
        authTokenDao.clearToken();
        userDao.clear();
        db.closeConnection(true);
        jsonText = new String(Files.readAllBytes(Paths.get("passoffFiles/LoadData.json")));
        jsonText2 = "{\n" +
                "\t\"users\":[\n      {\n" +
                "         \"username\":\"patrick\",\n" +
                "         \"password\":\"spencer\",\n" +
                "         \"email\":\"patrick@spencer.com\",\n" +
                "         \"firstName\":\"Patrick\",\n" +
                "         \"lastName\":\"Spencer\",\n" +
                "         \"gender\":\"m\",\n" +
                "         \"personID\":\"Patrick_Spencer\"\n" +
                "      }" +
                "\t\t\n" +
                "\t],\n" +
                "\t\"persons\":[\n{\n" +
                "         \"firstName\":\"Sheila\",\n" +
                "         \"lastName\":\"Parker\",\n" +
                "         \"gender\":\"f\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"spouseID\":\"Davis_Hyer\",\n" +
                "         \"fatherID\":\"Blaine_McGary\",\n" +
                "         \"motherID\":\"Betty_White\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"firstName\":\"Davis\",\n" +
                "         \"lastName\":\"Hyer\",\n" +
                "         \"gender\":\"m\",\n" +
                "         \"personID\":\"Davis_Hyer\",\n" +
                "         \"spouseID\":\"Sheila_Parker\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      }" +
                "\t\t\n" +
                "\t],\n" +
                "\t\"events\":[\n{\n" +
                "         \"eventType\":\"birth\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Melbourne\",\n" +
                "         \"country\":\"Australia\",\n" +
                "         \"latitude\":-36.1833,\n" +
                "         \"longitude\":144.9667,\n" +
                "         \"year\":1970,\n" +
                "         \"eventID\":\"Sheila_Birth\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"marriage\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Los Angeles\",\n" +
                "         \"country\":\"United States\",\n" +
                "         \"latitude\":34.0500,\n" +
                "         \"longitude\":-117.7500,\n" +
                "         \"year\":2012,\n" +
                "         \"eventID\":\"Sheila_Marriage\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"completed asteroids\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Qaanaaq\",\n" +
                "         \"country\":\"Denmark\",\n" +
                "         \"latitude\":77.4667,\n" +
                "         \"longitude\":-68.7667,\n" +
                "         \"year\":2014,\n" +
                "         \"eventID\":\"Sheila_Asteroids\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"COMPLETED ASTEROIDS\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Qaanaaq\",\n" +
                "         \"country\":\"Denmark\",\n" +
                "         \"latitude\":74.4667,\n" +
                "         \"longitude\":-60.7667,\n" +
                "         \"year\":2014,\n" +
                "         \"eventID\":\"Other_Asteroids\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      }" +
                "\t\t\n" +
                "\t]\n" +
                "}";

        jsonText3 = "{\n" +
                "\t\"users\":[\n      {\n" +
                "         \"username\":\"patrick\",\n" +
                "         \"password\":\"spencer\",\n" +
                "         \"email\":\"patrick@spencer.com\",\n" +
                "         \"firstName\":\"Patrick\",\n" +
                "         \"lastName\":\"Spencer\",\n" +
                "         \"gender\":\"m\",\n" +
                "         \"personID\":\"Patrick_Spencer\"\n" +
                "      }" +
                "\t\t\n" +
                "\t],\n" +
                "\t\"persons\":[\n{\n" +
                "         \"firstName\":\"Sheila\",\n" +
                "         \"gender\":\"f\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"spouseID\":\"Davis_Hyer\",\n" +
                "         \"fatherID\":\"Blaine_McGary\",\n" +
                "         \"motherID\":\"Betty_White\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"firstName\":\"Davis\",\n" +
                "         \"lastName\":\"Hyer\",\n" +
                "         \"gender\":\"m\",\n" +
                "         \"personID\":\"Davis_Hyer\",\n" +
                "         \"spouseID\":\"Sheila_Parker\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      }" +
                "\t\t\n" +
                "\t],\n" +
                "\t\"events\":[\n{\n" +
                "         \"eventType\":\"birth\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Melbourne\",\n" +
                "         \"country\":\"Australia\",\n" +
                "         \"latitude\":-36.1833,\n" +
                "         \"longitude\":144.9667,\n" +
                "         \"year\":1970,\n" +
                "         \"eventID\":\"Sheila_Birth\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"marriage\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Los Angeles\",\n" +
                "         \"country\":\"United States\",\n" +
                "         \"latitude\":34.0500,\n" +
                "         \"longitude\":-117.7500,\n" +
                "         \"year\":2012,\n" +
                "         \"eventID\":\"Sheila_Marriage\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"completed asteroids\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Qaanaaq\",\n" +
                "         \"country\":\"Denmark\",\n" +
                "         \"latitude\":77.4667,\n" +
                "         \"longitude\":-68.7667,\n" +
                "         \"year\":2014,\n" +
                "         \"eventID\":\"Sheila_Asteroids\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"eventType\":\"COMPLETED ASTEROIDS\",\n" +
                "         \"personID\":\"Sheila_Parker\",\n" +
                "         \"city\":\"Qaanaaq\",\n" +
                "         \"country\":\"Denmark\",\n" +
                "         \"latitude\":74.4667,\n" +
                "         \"longitude\":-60.7667,\n" +
                "         \"year\":2014,\n" +
                "         \"eventID\":\"Other_Asteroids\",\n" +
                "         \"associatedUsername\":\"sheila\"\n" +
                "      }" +
                "\t\t\n" +
                "\t]\n" +
                "}";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void loadServicePositive() throws SQLException, DataAccessException {
        //check how many people imported
        LoadService loadService =new LoadService();
        loadRequest = gson.fromJson(jsonText, LoadRequest.class);
        loadResult = loadService.load(loadRequest);
        assertEquals(true, loadResult.getMessage().contains(" 2 users"));
        assertEquals(true, loadResult.getMessage().contains(", 11 persons"));
        assertEquals(true, loadResult.getMessage().contains("and 19 events"));
        //second time
        LoadService loadService2 =new LoadService();
        loadRequest = gson.fromJson(jsonText2, LoadRequest.class);
        loadResult = loadService2.load(loadRequest);
        assertEquals(true, loadResult.getMessage().contains(" 1 users"));
        assertEquals(true, loadResult.getMessage().contains(", 2 persons"));
        assertEquals(true, loadResult.getMessage().contains("and 4 events"));



    }
    @Test
    public void loadServiceNegative() throws SQLException, DataAccessException {
        LoadService loadService =new LoadService();
        loadRequest = gson.fromJson(jsonText, LoadRequest.class);
        loadRequest.setEvents(null);
        loadResult = loadService.load(loadRequest);
        assertEquals("Error:[Invalid request data (missing values, invalid values, etc.)]",loadResult.getMessage());
        assertEquals(false,loadResult.getSuccess());

        LoadService loadService2 =new LoadService();
        loadRequest = gson.fromJson(jsonText3, LoadRequest.class);
        loadResult = loadService.load(loadRequest);
        assertEquals("Error:[Invalid request data (missing values, invalid values, etc.)]",loadResult.getMessage());
        assertEquals(false,loadResult.getSuccess());

    }
}