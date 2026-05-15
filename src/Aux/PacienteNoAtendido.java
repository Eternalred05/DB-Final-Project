package Aux;

public class PacienteNoAtendido {

    private String numHistClinica;
    private String nombrePac;
    private String direccion;
    private String causa;
    private String numTurno; 

    public PacienteNoAtendido(String numHistClinica, String nombrePac, String direccion, String causa, String numTurno) {
        this.numHistClinica = numHistClinica;
        this.nombrePac = nombrePac;
        this.direccion = direccion;
        this.causa = causa;
        this.numTurno = numTurno;
    }

    public String getNumHistClinica() {
        return numHistClinica;
    }

    public String getNombrePac() {
        return nombrePac;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCausa() {
        return causa;
    }

    public String getNumTurno() {
        return numTurno;
    }
}
