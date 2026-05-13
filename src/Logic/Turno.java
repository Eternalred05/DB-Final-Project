package Logic;

public class Turno {

    private int numTurno;
    private int cantPacientes;
    private int pacientesAtend;
    private String codMedico;
    private String codUnidad;

    public Turno(int numTurno, int cantPacientes, int pacientesAtend, String codMedico, String codUnidad) {
        this.numTurno = numTurno;
        this.cantPacientes = cantPacientes;
        this.pacientesAtend = pacientesAtend;
        this.codMedico = codMedico;
        this.codUnidad = codUnidad;
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

    public String getCodMedico() {
        return codMedico;
    }

    public String getCodUnidad() {
        return codUnidad;
    }
}
