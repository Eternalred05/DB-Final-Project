package Aux;

public class PacienteNoAtendido {

    private String numHistClinica;
    private String nombrePac;
    private String direccion;
    private String causa;

    public PacienteNoAtendido(String numHistClinica, String nombrePac, String direccion, String causa) {
        this.numHistClinica = numHistClinica;
        this.nombrePac = nombrePac;
        this.direccion = direccion;
        this.causa = causa;
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
}
