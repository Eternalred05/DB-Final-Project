package Aux;

public class DatosInforme {

    private int pacientesInicio;
    private int pacientesAtendidosTurno;
    private int pacientesEnRegistro;
    private int atendidosDesdeUltimo;
    private int ultimoNumInforme;
    private int sumaAtendidosPrevios;

    public DatosInforme(int pacientesInicio, int pacientesAtendidosTurno, int pacientesEnRegistro,
            int atendidosDesdeUltimo, int ultimoNumInforme, int sumaAtendidosPrevios) {
        this.pacientesInicio = pacientesInicio;
        this.pacientesAtendidosTurno = pacientesAtendidosTurno;
        this.pacientesEnRegistro = pacientesEnRegistro;
        this.atendidosDesdeUltimo = atendidosDesdeUltimo;
        this.ultimoNumInforme = ultimoNumInforme;
        this.sumaAtendidosPrevios = sumaAtendidosPrevios;
    }

    public int getPacientesInicio() {
        return pacientesInicio;
    }

    public int getPacientesAtendidosTurno() {
        return pacientesAtendidosTurno;
    }

    public int getPacientesEnRegistro() {
        return pacientesEnRegistro;
    }

    public int getAtendidosDesdeUltimo() {
        return atendidosDesdeUltimo;
    }

    public int getUltimoNumInforme() {
        return ultimoNumInforme;
    }

    public int getSumaAtendidosPrevios() {
        return sumaAtendidosPrevios;
    }
}
