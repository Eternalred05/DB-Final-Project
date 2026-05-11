package Logic;

import java.util.ArrayList;

public class Unidad {

    private String name;
    private String id;
    private String location;
    private String codDpt;

    public Unidad(String name, String id, String location, String codDpt) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.codDpt = codDpt;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getCodDpt() {
        return codDpt;
    }

}
