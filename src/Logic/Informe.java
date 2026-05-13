package Logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Informe {

    private String codUnidad;
    private int numInforme;
    private LocalDate fecha;
    private LocalTime hora;
    private int pacientesAtendInf;
    private int pacientesAlta;
    private int pacientesAdmit;
    private int pacientesRegist;
    private int numTurno;

    public Informe(String codUnidad, int numInforme, LocalDate fecha, LocalTime hora,
            int pacientesAtendInf, int pacientesAlta, int pacientesAdmit,
            int pacientesRegist, int numTurno) {
        this.codUnidad = codUnidad;
        this.numInforme = numInforme;
        this.fecha = fecha;
        this.hora = hora;
        this.pacientesAtendInf = pacientesAtendInf;
        this.pacientesAlta = pacientesAlta;
        this.pacientesAdmit = pacientesAdmit;
        this.pacientesRegist = pacientesRegist;
        this.numTurno = numTurno;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public int getNumInforme() {
        return numInforme;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPacientesAtendInf() {
        return pacientesAtendInf;
    }

    public int getPacientesAlta() {
        return pacientesAlta;
    }

    public int getPacientesAdmit() {
        return pacientesAdmit;
    }

    public int getPacientesRegist() {
        return pacientesRegist;
    }

    public int getNumTurno() {
        return numTurno;
    }
}
