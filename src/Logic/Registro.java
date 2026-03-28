package Logic;

import java.time.LocalDate;

public class Registro {

    private Unidad unidad;
    private Paciente paciente;
    private String numeroHistoriaClinica;
    private LocalDate fechaRegistro;
    private String estado;
    private boolean atendido;
    private String causaNoAtendido;

    public Registro(Unidad unidad, Paciente paciente, String numeroHistoriaClinica, LocalDate fechaRegistro, String estado, boolean atendido, String causaNoAtendido) {
        this.unidad = unidad;
        this.paciente = paciente;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.atendido = atendido;
        this.causaNoAtendido = causaNoAtendido;
    }
    
    

}
