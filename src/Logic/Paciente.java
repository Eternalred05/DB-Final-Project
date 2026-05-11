package Logic;

import java.sql.Date;

public class Paciente {

    private String nombre;
    private String id;
    private String address;
    private Date birthDate;
    private String codUnidad;
    private boolean atendido;
    private String causaNoAtendido;

    public Paciente(String nombre, String id, String address, Date birthDate, String codUnidad, boolean atendido, String causaNoAtendido) {
        this.nombre = nombre;
        this.id = id;
        this.address = address;
        this.birthDate = birthDate;
        this.codUnidad = codUnidad;
        this.atendido = atendido;
        this.causaNoAtendido = causaNoAtendido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public String getCausaNoAtendido() {
        return causaNoAtendido;
    }

  
    

}
