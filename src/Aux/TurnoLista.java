package Aux;

public class TurnoLista {

    private String hospital;
    private String departamento;
    private String unidad;
    private int numTurno;
    private String horaInforme;
    private int numInforme;
    private int pacientesInicio;
    private int pacientesAdmitidos;
    private int pacientesAlta;
    private int pacientesAtendidosDesdeAnterior;
    private int pacientesAtendidosDia;

    public TurnoLista(String hospital, String departamento, String unidad,
            int numTurno, String horaInforme, int numInforme,
            int pacientesInicio, int pacientesAdmitidos,
            int pacientesAlta, int pacientesAtendidosDesdeAnterior,
            int pacientesAtendidosDia) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.numTurno = numTurno;
        this.horaInforme = horaInforme;
        this.numInforme = numInforme;
        this.pacientesInicio = pacientesInicio;
        this.pacientesAdmitidos = pacientesAdmitidos;
        this.pacientesAlta = pacientesAlta;
        this.pacientesAtendidosDesdeAnterior = pacientesAtendidosDesdeAnterior;
        this.pacientesAtendidosDia = pacientesAtendidosDia;
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

    public String getHoraInforme() {
        return horaInforme;
    }

    public int getNumInforme() {
        return numInforme;
    }

    public int getPacientesInicio() {
        return pacientesInicio;
    }

    public int getPacientesAdmitidos() {
        return pacientesAdmitidos;
    }

    public int getPacientesAlta() {
        return pacientesAlta;
    }

    public int getPacientesAtendidosDesdeAnterior() {
        return pacientesAtendidosDesdeAnterior;
    }

    public int getPacientesAtendidosDia() {
        return pacientesAtendidosDia;
    }
}
