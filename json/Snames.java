package json;

import java.util.Objects;

public class Surname{
    String surname;
}
class SurnameData{
    Surname[] data;
}
    Reader reader = new FileReader("locations.json");
    SurnameData surnameData = gson.fromJson(reader,SurnameData.class);