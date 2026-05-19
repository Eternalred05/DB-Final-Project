package Aux;

public class ResumenProceso {

    private String hospital;
    private String departamento;
    private String unidad;
    private int numTurno;
    private String horaInforme;         
    private int pacientesInicio;
    private int pacientesAtendidos;
    private int totalPacientes;
    private double porcentajeAtendidos;
    private int noAtendidosTotal;
    private int altas;
    private int extranjero;
    private int fueraProvincia;
    private int hospitalizadosOtraUnidad;
    private int otrasCausas;
    private int causaDesconocida;

    public ResumenProceso(String hospital, String departamento, String unidad, int numTurno,
            String horaInforme, int pacientesInicio, int pacientesAtendidos,
            int totalPacientes, double porcentajeAtendidos, int noAtendidosTotal,
            int altas, int extranjero, int fueraProvincia, int hospitalizadosOtraUnidad,
            int otrasCausas, int causaDesconocida) {
        this.hospital = hospital;
        this.departamento = departamento;
        this.unidad = unidad;
        this.numTurno = numTurno;
        this.horaInforme = horaInforme;
        this.pacientesInicio = pacientesInicio;
        this.pacientesAtendidos = pacientesAtendidos;
        this.totalPacientes = totalPacientes;
        this.porcentajeAtendidos = porcentajeAtendidos;
        this.noAtendidosTotal = noAtendidosTotal;
        this.altas = altas;
        this.extranjero = extranjero;
        this.fueraProvincia = fueraProvincia;
        this.hospitalizadosOtraUnidad = hospitalizadosOtraUnidad;
        this.otrasCausas = otrasCausas;
        this.causaDesconocida = causaDesconocida;
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

    public int getPacientesInicio() {
        return pacientesInicio;
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public int getTotalPacientes() {
        return totalPacientes;
    }

    public double getPorcentajeAtendidos() {
        return porcentajeAtendidos;
    }

    public int getNoAtendidosTotal() {
        return noAtendidosTotal;
    }

    public int getAltas() {
        return altas;
    }

    public int getExtranjero() {
        return extranjero;
    }

    public int getFueraProvincia() {
        return fueraProvincia;
    }

    public int getHospitalizadosOtraUnidad() {
        return hospitalizadosOtraUnidad;
    }

    public int getOtrasCausas() {
        return otrasCausas;
    }

    public int getCausaDesconocida() {
        return causaDesconocida;
    }
}
