package Logic;

public class Turno {

    private Medico doctor;
    private int numTurno;
    private int pacientesAtendidos;
    private int pacientesAsignados;

    public Turno(Medico doctor, int numTurno, int pacientesAtendidos, int pacientesAsignados) {
        this.doctor = doctor;
        this.numTurno = numTurno;
        this.pacientesAtendidos = pacientesAtendidos;
        this.pacientesAsignados = pacientesAsignados;
    }

}
