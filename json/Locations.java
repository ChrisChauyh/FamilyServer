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

    public json.FemaleName[] getData() {
        return data;
    }

    public void setData(json.FemaleName[] data) {
        this.data = data;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public json.FemaleNameData getFemaleNameData() {
        return femaleNameData;
    }

    public void setFemaleNameData(json.FemaleNameData femaleNameData) {
        this.femaleNameData = femaleNameData;
    }