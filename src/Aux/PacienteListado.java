package Aux;

public class PacienteListado {

    private String hospital;
    private String departamento;
    private String unidad;
    private String numHistClinica;
    private String nombrePac;
    private String fechaNacimiento;
    private String direccion;

    public PacienteListado(String hospital, String departamento, String unidad, String numHistClinica, String nombrePac, String fechaNacimiento, String direccion) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.numHistClinica = numHistClinica;
        this.nombrePac = nombrePac;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getNumHistClinica() {
        return numHistClinica;
    }

    public String getNombrePac() {
        return nombrePac;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }
}
