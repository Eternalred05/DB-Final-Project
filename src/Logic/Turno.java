package Logic;

public class Turno {

    private Doctor doctor;
    private int numTurno;
    private int pacientesAtendidos;
    private int pacientesAsignados;

    public Turno(Doctor doctor, int numTurno, int pacientesAtendidos, int pacientesAsignados) {
        this.doctor = doctor;
        this.numTurno = numTurno;
        this.pacientesAtendidos = pacientesAtendidos;
        this.pacientesAsignados = pacientesAsignados;
    }

}
