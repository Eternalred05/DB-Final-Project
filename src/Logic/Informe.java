package Logic;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Informe {
    private LocalDateTime time;
    private LocalDate date;
    private int idNumber;
    private int pacientesAtendidos;
    private int pacientesDeAltas;
    private int cantAdmitidos;
    private int pacientesActuales; // (los pacientes del registro oficial más los admitidos

    public Informe(LocalDateTime time, LocalDate date, int idNumber, int pacientesAtendidos, int pacientesDeAltas, int cantAdmitidos, int pacientesActuales) {
        this.time = time;
        this.date = date;
        this.idNumber = idNumber;
        this.pacientesAtendidos = pacientesAtendidos;
        this.pacientesDeAltas = pacientesDeAltas;
        this.cantAdmitidos = cantAdmitidos;
        this.pacientesActuales = pacientesActuales;
    }
    
    
    
    

}
