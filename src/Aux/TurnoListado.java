package Aux;

public class TurnoListado {

    private String hospital, departamento, unidad;
    private int numTurno, cantPacientes, pacientesAtend;
    private String nombreMedico, codMedico, codUnidad;

    public TurnoListado(String hospital, String departamento, String unidad,
            int numTurno, int cantPacientes, int pacientesAtend,
            String nombreMedico, String codMedico, String codUnidad) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.numTurno = numTurno;
        this.cantPacientes = cantPacientes;
        this.pacientesAtend = pacientesAtend;
        this.nombreMedico = nombreMedico;
        this.codMedico = codMedico;
        this.codUnidad = codUnidad;
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

    public int getCantPacientes() {
        return cantPacientes;
    }

    public int getPacientesAtend() {
        return pacientesAtend;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public String getCodMedico() {
        return codMedico;
    }

    public String getCodUnidad() {
        return codUnidad;
    }
}
