package json;

import java.util.Objects;

public class FemaleName{
    String firstName;
}
class FemaleNameData{
    FemaleName[] data;
}
Reader reader = new FileReader("fnames.json");
FemaleNameData femaleNameData = gson.fromJson(reader,FemaleNameData.class );