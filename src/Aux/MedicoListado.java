package Aux;

public class MedicoListado {

    private String hospital;
    private String departamento;
    private String unidad;
    private String nombreMed;
    private String especialidad;
    private String numLicencia;
    private String telefono;
    private int experiencia;

    public MedicoListado(String hospital, String departamento, String unidad,
            String nombreMed, String especialidad, String numLicencia,
            String telefono, int experiencia) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.nombreMed = nombreMed;
        this.especialidad = especialidad;
        this.numLicencia = numLicencia;
        this.telefono = telefono;
        this.experiencia = experiencia;
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

    public String getNombreMed() {
        return nombreMed;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getNumLicencia() {
        return numLicencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getExperiencia() {
        return experiencia;
    }
}
