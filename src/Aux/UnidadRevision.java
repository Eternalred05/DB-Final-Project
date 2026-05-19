package Aux;

public class UnidadRevision {

    private String hospital;
    private String departamento;
    private String unidad;
    private int totalPacientesUnidad;
    private String nombreMedico;
    private int pacientesAtendidosMedico;
    private double porcentajeMedico;

    public UnidadRevision(String hospital, String departamento, String unidad,
            int totalPacientesUnidad, String nombreMedico,
            int pacientesAtendidosMedico, double porcentajeMedico) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.totalPacientesUnidad = totalPacientesUnidad;
        this.nombreMedico = nombreMedico;
        this.pacientesAtendidosMedico = pacientesAtendidosMedico;
        this.porcentajeMedico = porcentajeMedico;
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

    public int getTotalPacientesUnidad() {
        return totalPacientesUnidad;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public int getPacientesAtendidosMedico() {
        return pacientesAtendidosMedico;
    }

    public double getPorcentajeMedico() {
        return porcentajeMedico;
    }
}
