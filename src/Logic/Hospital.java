package Logic;

import java.util.ArrayList;

public class Hospital {

    private String name;
    private String id;
    private ArrayList<Departamento> depts;

    public Hospital(String name, String id) {
        this.name = name;
        this.id = id;
        depts = new ArrayList<>();
    }

}
