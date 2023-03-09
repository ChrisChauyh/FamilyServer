package json;

import java.util.Objects;

public class MaleName{
    String firstName;
}
class MaleNameData{
    MaleName[] data;
}
    Reader reader = new FileReader("mnames.json");
    MaleNameData maleNameData = gson.fromJson(reader,MaleNameData.class );