package Logic;

public class Departamento {

    private String name;
    private String id;
    private String idHosp;

    public Departamento(String name, String id, String idHosp) {
        this.name = name;
        this.id = id;
        this.idHosp = idHosp;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getIdHosp() {
        return idHosp;
    }

    @Override
    public String toString() {
        return name;
    }

}
