package Aux;

public class UnidadExitosa {

    private String hospital;
    private String departamento;
    private String unidad;
    private int numTurno;
    private String nombreMedico;
    private int pacientesAtendidosTurno;
    private int totalPacientesAtendidosMedico;

    public UnidadExitosa(String hospital, String departamento, String unidad,
            int numTurno, String nombreMedico,
            int pacientesAtendidosTurno, int totalPacientesAtendidosMedico) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.numTurno = numTurno;
        this.nombreMedico = nombreMedico;
        this.pacientesAtendidosTurno = pacientesAtendidosTurno;
        this.totalPacientesAtendidosMedico = totalPacientesAtendidosMedico;
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

    public int getNumTurno() {
        return numTurno;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public int getPacientesAtendidosTurno() {
        return pacientesAtendidosTurno;
    }

    public int getTotalPacientesAtendidosMedico() {
        return totalPacientesAtendidosMedico;
    }
}
