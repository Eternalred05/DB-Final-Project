DROP TABLE IF EXISTS Informe   CASCADE;
DROP TABLE IF EXISTS Turno     CASCADE;
DROP TABLE IF EXISTS Paciente  CASCADE;
DROP TABLE IF EXISTS Doctor    CASCADE;
DROP TABLE IF EXISTS Unidad    CASCADE;
DROP TABLE IF EXISTS Departamento CASCADE;
DROP TABLE IF EXISTS Hospital  CASCADE;

DROP FUNCTION IF EXISTS trg_no_repetir_nombre_hospital()     CASCADE;
DROP FUNCTION IF EXISTS trg_no_repetir_nombre_departamento() CASCADE;
DROP FUNCTION IF EXISTS trg_no_repetir_nombre_unidad()       CASCADE;
DROP FUNCTION IF EXISTS trg_paciente_mayor_16()              CASCADE;
DROP FUNCTION IF EXISTS trg_max_medicos_por_unidad()         CASCADE;
DROP FUNCTION IF EXISTS trg_turno_exitoso()                  CASCADE;

DROP FUNCTION IF EXISTS insertar_hospital(VARCHAR, VARCHAR)                                             CASCADE;
DROP FUNCTION IF EXISTS modificar_hospital(VARCHAR, VARCHAR)                                            CASCADE;
DROP FUNCTION IF EXISTS eliminar_hospital(VARCHAR)                                                      CASCADE;

DROP FUNCTION IF EXISTS insertar_departamento(VARCHAR, VARCHAR, VARCHAR)                                CASCADE;
DROP FUNCTION IF EXISTS modificar_departamento(VARCHAR, VARCHAR, VARCHAR)                               CASCADE;
DROP FUNCTION IF EXISTS eliminar_departamento(VARCHAR)                                                  CASCADE;

DROP FUNCTION IF EXISTS insertar_unidad(VARCHAR, VARCHAR, VARCHAR, VARCHAR)                             CASCADE;
DROP FUNCTION IF EXISTS modificar_unidad(VARCHAR, VARCHAR, VARCHAR, VARCHAR)                            CASCADE;
DROP FUNCTION IF EXISTS eliminar_unidad(VARCHAR)                                                        CASCADE;

DROP FUNCTION IF EXISTS insertar_medico(VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, INTEGER, VARCHAR)  CASCADE;
DROP FUNCTION IF EXISTS modificar_medico(VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, INTEGER, VARCHAR) CASCADE;
DROP FUNCTION IF EXISTS eliminar_medico(VARCHAR)                                                        CASCADE;

DROP FUNCTION IF EXISTS insertar_paciente(VARCHAR, VARCHAR, VARCHAR, VARCHAR, DATE, BOOLEAN, VARCHAR)   CASCADE;
DROP FUNCTION IF EXISTS modificar_paciente(VARCHAR, VARCHAR, VARCHAR, VARCHAR, DATE, BOOLEAN, VARCHAR)  CASCADE;
DROP FUNCTION IF EXISTS eliminar_paciente(VARCHAR, VARCHAR)                                             CASCADE;

DROP FUNCTION IF EXISTS insertar_turno(VARCHAR, INTEGER, INTEGER, INTEGER, VARCHAR)                     CASCADE;
DROP FUNCTION IF EXISTS modificar_turno(VARCHAR, INTEGER, INTEGER, INTEGER, VARCHAR)                    CASCADE;
DROP FUNCTION IF EXISTS eliminar_turno(VARCHAR, INTEGER)                                                CASCADE;

DROP FUNCTION IF EXISTS insertar_informe(VARCHAR, INTEGER, DATE, TIME, INTEGER, INTEGER, INTEGER, INTEGER) CASCADE;
DROP FUNCTION IF EXISTS modificar_informe(VARCHAR, INTEGER, DATE, TIME, INTEGER, INTEGER, INTEGER, INTEGER) CASCADE;
DROP FUNCTION IF EXISTS eliminar_informe(VARCHAR, INTEGER)                                              CASCADE;

CREATE TABLE Hospital (
    codHospital  VARCHAR(20)  NOT NULL,
    nombreHosp   VARCHAR(150) NOT NULL,
    CONSTRAINT pk_hospital PRIMARY KEY (codHospital)
);

CREATE TABLE Departamento (
    codDpt       VARCHAR(20)  NOT NULL,
    nombreDpt    VARCHAR(150) NOT NULL,
    codHospital  VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_departamento PRIMARY KEY (codDpt),
    CONSTRAINT fk_dpt_hospital FOREIGN KEY (codHospital)
        REFERENCES Hospital(codHospital)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Unidad (
    codUnidad    VARCHAR(20)  NOT NULL,
    nombreUnidad VARCHAR(150) NOT NULL,
    ubicacion    VARCHAR(200) NOT NULL,
    codDpt       VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_unidad PRIMARY KEY (codUnidad),
    CONSTRAINT fk_unidad_dpt FOREIGN KEY (codDpt)
        REFERENCES Departamento(codDpt)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Doctor (
    codMedico    VARCHAR(20)  NOT NULL,
    nombreMed    VARCHAR(200) NOT NULL,
    telefono     VARCHAR(20),
    especialidad VARCHAR(100) NOT NULL,
    numLicencia  VARCHAR(50)  NOT NULL,
    experiencia  INTEGER      NOT NULL CHECK (experiencia >= 0),
    codUnidad    VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_medico PRIMARY KEY (codMedico),
    CONSTRAINT fk_medico_unidad FOREIGN KEY (codUnidad)
        REFERENCES Unidad(codUnidad)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT uq_licencia UNIQUE (numLicencia)
);

CREATE TABLE Paciente (
    codUnidad      VARCHAR(20)  NOT NULL,
    numHistClinica VARCHAR(20)     NOT NULL,
    nombrePac      VARCHAR(200) NOT NULL,
    direccion      VARCHAR(250) NOT NULL,
    nacimiento     DATE         NOT NULL,
    atendido       BOOLEAN      NOT NULL DEFAULT FALSE,
    causa          VARCHAR(300),
    CONSTRAINT pk_paciente PRIMARY KEY (codUnidad, numHistClinica),
    CONSTRAINT fk_paciente_unidad FOREIGN KEY (codUnidad)
        REFERENCES Unidad(codUnidad)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_causa CHECK (
        (atendido = TRUE  AND causa IS NULL) OR
        (atendido = FALSE)
    )
);

CREATE TABLE Turno (
    codUnidad      VARCHAR(20) NOT NULL,
    numTurno       INTEGER     NOT NULL,
    cantPacientes  INTEGER     NOT NULL CHECK (cantPacientes >= 0),
    pacientesAtend INTEGER     NOT NULL DEFAULT 0 CHECK (pacientesAtend >= 0),
    codMedico      VARCHAR(20) NOT NULL,
    CONSTRAINT pk_turno PRIMARY KEY (codUnidad, numTurno),
    CONSTRAINT fk_turno_unidad FOREIGN KEY (codUnidad)
        REFERENCES Unidad(codUnidad)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_turno_medico FOREIGN KEY (codMedico)
        REFERENCES Doctor(codMedico)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_atend_lte_cant CHECK (pacientesAtend <= cantPacientes)
);

CREATE TABLE Informe (
    codUnidad         VARCHAR(20) NOT NULL,
    numInforme        INTEGER     NOT NULL,
    fecha             DATE        NOT NULL,
    hora              TIME        NOT NULL,
    pacientesAtendInf INTEGER     NOT NULL DEFAULT 0 CHECK (pacientesAtendInf >= 0),
    pacientesAlta     INTEGER     NOT NULL DEFAULT 0 CHECK (pacientesAlta     >= 0),
    pacientesAdmit    INTEGER     NOT NULL DEFAULT 0 CHECK (pacientesAdmit    >= 0),
    pacientesRegist   INTEGER     NOT NULL DEFAULT 0 CHECK (pacientesRegist   >= 0),
    numTurno          INTEGER     NOT NULL,
    CONSTRAINT pk_informe PRIMARY KEY (codUnidad, numInforme),
    CONSTRAINT fk_informe_turno FOREIGN KEY (codUnidad, numTurno)
        REFERENCES Turno(codUnidad, numTurno)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_dpt_hospital  ON Departamento(codHospital);
CREATE INDEX idx_unidad_dpt    ON Unidad(codDpt);
CREATE INDEX idx_medico_unidad ON Doctor(codUnidad);
CREATE INDEX idx_pac_unidad    ON Paciente(codUnidad);
CREATE INDEX idx_turno_medico  ON Turno(codMedico);
CREATE INDEX idx_informe_turno ON Informe(codUnidad, numTurno);

CREATE OR REPLACE FUNCTION trg_no_repetir_nombre_hospital()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Hospital
        WHERE LOWER(nombreHosp) = LOWER(NEW.nombreHosp)
          AND codHospital <> NEW.codHospital
    ) THEN
        RAISE EXCEPTION 'Ya existe un hospital con el nombre "%"', NEW.nombreHosp;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_hospital_nombre_unico
BEFORE INSERT OR UPDATE ON Hospital
FOR EACH ROW EXECUTE PROCEDURE trg_no_repetir_nombre_hospital();

CREATE OR REPLACE FUNCTION trg_no_repetir_nombre_departamento()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Departamento
        WHERE LOWER(nombreDpt)  = LOWER(NEW.nombreDpt)
          AND codHospital        = NEW.codHospital
          AND codDpt            <> NEW.codDpt
    ) THEN
        RAISE EXCEPTION 'Ya existe un departamento "%" en ese hospital', NEW.nombreDpt;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_departamento_nombre_unico
BEFORE INSERT OR UPDATE ON Departamento
FOR EACH ROW EXECUTE PROCEDURE trg_no_repetir_nombre_departamento();

CREATE OR REPLACE FUNCTION trg_no_repetir_nombre_unidad()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Unidad
        WHERE LOWER(nombreUnidad) = LOWER(NEW.nombreUnidad)
          AND codDpt               = NEW.codDpt
          AND codUnidad           <> NEW.codUnidad
    ) THEN
        RAISE EXCEPTION 'Ya existe una unidad "%" en ese departamento', NEW.nombreUnidad;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_unidad_nombre_unico
BEFORE INSERT OR UPDATE ON Unidad
FOR EACH ROW EXECUTE PROCEDURE trg_no_repetir_nombre_unidad();

CREATE OR REPLACE FUNCTION trg_paciente_mayor_16()
RETURNS TRIGGER AS $$
BEGIN
    IF DATE_PART('year', AGE(NOW()::DATE, NEW.nacimiento)) < 16 THEN
        RAISE EXCEPTION 'No se puede registrar un paciente menor de 16 años (nacimiento: %)', NEW.nacimiento;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_edad_paciente
BEFORE INSERT OR UPDATE ON Paciente
FOR EACH ROW EXECUTE PROCEDURE trg_paciente_mayor_16();

CREATE OR REPLACE FUNCTION trg_max_medicos_por_unidad()
RETURNS TRIGGER AS $$
DECLARE
    total INTEGER;
BEGIN

    SELECT COUNT(*) INTO total
    FROM Doctor
    WHERE codUnidad = NEW.codUnidad
      AND codMedico <> NEW.codMedico;  

    IF total >= 10 THEN
        RAISE EXCEPTION 'La unidad "%" ya tiene 10 médicos asignados (máximo permitido)', NEW.codUnidad;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_max_medicos
BEFORE INSERT OR UPDATE ON Doctor
FOR EACH ROW EXECUTE PROCEDURE trg_max_medicos_por_unidad();

CREATE OR REPLACE FUNCTION trg_turno_exitoso()
RETURNS TRIGGER AS $$
DECLARE
    porcentaje NUMERIC;
BEGIN
    IF NEW.cantPacientes > 0 THEN
        porcentaje := (NEW.pacientesAtend::NUMERIC / NEW.cantPacientes) * 100;
        IF porcentaje < 80 THEN
            RAISE NOTICE 'ALERTA: El turno % de la unidad % debe ser revisado (atendidos: %, asignados: %, porcentaje: %)',
                NEW.numTurno, NEW.codUnidad, NEW.pacientesAtend, NEW.cantPacientes, ROUND(porcentaje,2);
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_turno_exitoso
AFTER INSERT OR UPDATE ON Turno
FOR EACH ROW EXECUTE PROCEDURE trg_turno_exitoso();

CREATE OR REPLACE FUNCTION insertar_hospital(
    p_codHospital VARCHAR,
    p_nombreHosp  VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Hospital(codHospital, nombreHosp)
    VALUES (p_codHospital, p_nombreHosp);
    RAISE NOTICE 'Hospital "%" insertado correctamente.', p_nombreHosp;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_hospital(
    p_codHospital VARCHAR,
    p_nombreHosp  VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Hospital SET nombreHosp = p_nombreHosp
    WHERE codHospital = p_codHospital;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Hospital con código "%" no encontrado.', p_codHospital;
    END IF;
    RAISE NOTICE 'Hospital "%" modificado correctamente.', p_codHospital;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_hospital(
    p_codHospital VARCHAR
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Hospital WHERE codHospital = p_codHospital;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Hospital con código "%" no encontrado.', p_codHospital;
    END IF;
    RAISE NOTICE 'Hospital "%" eliminado correctamente.', p_codHospital;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insertar_departamento(
    p_codDpt      VARCHAR,
    p_nombreDpt   VARCHAR,
    p_codHospital VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Departamento(codDpt, nombreDpt, codHospital)
    VALUES (p_codDpt, p_nombreDpt, p_codHospital);
    RAISE NOTICE 'Departamento "%" insertado correctamente.', p_nombreDpt;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_departamento(
    p_codDpt      VARCHAR,
    p_nombreDpt   VARCHAR,
    p_codHospital VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Departamento
    SET nombreDpt = p_nombreDpt, codHospital = p_codHospital
    WHERE codDpt = p_codDpt;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Departamento con código "%" no encontrado.', p_codDpt;
    END IF;
    RAISE NOTICE 'Departamento "%" modificado correctamente.', p_codDpt;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_departamento(
    p_codDpt VARCHAR
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Departamento WHERE codDpt = p_codDpt;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Departamento con código "%" no encontrado.', p_codDpt;
    END IF;
    RAISE NOTICE 'Departamento "%" eliminado correctamente.', p_codDpt;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION insertar_unidad(
    p_codUnidad    VARCHAR,
    p_nombreUnidad VARCHAR,
    p_ubicacion    VARCHAR,
    p_codDpt       VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Unidad(codUnidad, nombreUnidad, ubicacion, codDpt)
    VALUES (p_codUnidad, p_nombreUnidad, p_ubicacion, p_codDpt);
    RAISE NOTICE 'Unidad "%" insertada correctamente.', p_nombreUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_unidad(
    p_codUnidad    VARCHAR,
    p_nombreUnidad VARCHAR,
    p_ubicacion    VARCHAR,
    p_codDpt       VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Unidad
    SET nombreUnidad = p_nombreUnidad,
        ubicacion    = p_ubicacion,
        codDpt       = p_codDpt
    WHERE codUnidad = p_codUnidad;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Unidad con código "%" no encontrada.', p_codUnidad;
    END IF;
    RAISE NOTICE 'Unidad "%" modificada correctamente.', p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_unidad(
    p_codUnidad VARCHAR
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Unidad WHERE codUnidad = p_codUnidad;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Unidad con código "%" no encontrada.', p_codUnidad;
    END IF;
    RAISE NOTICE 'Unidad "%" eliminada correctamente.', p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insertar_medico(
    p_codMedico    VARCHAR,
    p_nombreMed    VARCHAR,
    p_telefono     VARCHAR,
    p_especialidad VARCHAR,
    p_numLicencia  VARCHAR,
    p_experiencia  INTEGER,
    p_codUnidad    VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Doctor(codMedico, nombreMed, telefono, especialidad, numLicencia, experiencia, codUnidad)
    VALUES (p_codMedico, p_nombreMed, p_telefono, p_especialidad, p_numLicencia, p_experiencia, p_codUnidad);
    RAISE NOTICE 'Médico "%" insertado correctamente.', p_nombreMed;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_medico(
    p_codMedico    VARCHAR,
    p_nombreMed    VARCHAR,
    p_telefono     VARCHAR,
    p_especialidad VARCHAR,
    p_numLicencia  VARCHAR,
    p_experiencia  INTEGER,
    p_codUnidad    VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Doctor
    SET nombreMed    = p_nombreMed,
        telefono     = p_telefono,
        especialidad = p_especialidad,
        numLicencia  = p_numLicencia,
        experiencia  = p_experiencia,
        codUnidad    = p_codUnidad
    WHERE codMedico = p_codMedico;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Médico con código "%" no encontrado.', p_codMedico;
    END IF;
    RAISE NOTICE 'Médico "%" modificado correctamente.', p_codMedico;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_medico(
    p_codMedico VARCHAR
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Doctor WHERE codMedico = p_codMedico;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Médico con código "%" no encontrado.', p_codMedico;
    END IF;
    RAISE NOTICE 'Médico "%" eliminado correctamente.', p_codMedico;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION insertar_paciente(
    p_codUnidad      VARCHAR,
    p_numHistClinica VARCHAR,
    p_nombrePac      VARCHAR,
    p_direccion      VARCHAR,
    p_nacimiento     DATE,
    p_atendido       BOOLEAN,
    p_causa          VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Paciente(codUnidad, numHistClinica, nombrePac, direccion, nacimiento, atendido, causa)
    VALUES (p_codUnidad, p_numHistClinica, p_nombrePac, p_direccion, p_nacimiento, p_atendido, p_causa);
    RAISE NOTICE 'Paciente "%" insertado en la unidad "%" correctamente.', p_nombrePac, p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_paciente(
    p_codUnidad      VARCHAR,
    p_numHistClinica VARCHAR,
    p_nombrePac      VARCHAR,
    p_direccion      VARCHAR,
    p_nacimiento     DATE,
    p_atendido       BOOLEAN,
    p_causa          VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Paciente
    SET nombrePac      = p_nombrePac,
        direccion      = p_direccion,
        nacimiento     = p_nacimiento,
        atendido       = p_atendido,
        causa          = p_causa
    WHERE codUnidad = p_codUnidad AND numHistClinica = p_numHistClinica;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Paciente (unidad: %, histClinica: %) no encontrado.', p_codUnidad, p_numHistClinica;
    END IF;
    RAISE NOTICE 'Paciente (unidad: %, histClinica: %) modificado correctamente.', p_codUnidad, p_numHistClinica;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_paciente(
    p_codUnidad      VARCHAR,
    p_numHistClinica VARCHAR
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Paciente
    WHERE codUnidad = p_codUnidad AND numHistClinica = p_numHistClinica;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Paciente (unidad: %, histClinica: %) no encontrado.', p_codUnidad, p_numHistClinica;
    END IF;
    RAISE NOTICE 'Paciente (unidad: %, histClinica: %) eliminado correctamente.', p_codUnidad, p_numHistClinica;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION insertar_turno(
    p_codUnidad      VARCHAR,
    p_numTurno       INTEGER,
    p_cantPacientes  INTEGER,
    p_pacientesAtend INTEGER,
    p_codMedico      VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Turno(codUnidad, numTurno, cantPacientes, pacientesAtend, codMedico)
    VALUES (p_codUnidad, p_numTurno, p_cantPacientes, p_pacientesAtend, p_codMedico);
    RAISE NOTICE 'Turno % de la unidad "%" insertado correctamente.', p_numTurno, p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_turno(
    p_codUnidad      VARCHAR,
    p_numTurno       INTEGER,
    p_cantPacientes  INTEGER,
    p_pacientesAtend INTEGER,
    p_codMedico      VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Turno
    SET cantPacientes  = p_cantPacientes,
        pacientesAtend = p_pacientesAtend,
        codMedico      = p_codMedico
    WHERE codUnidad = p_codUnidad AND numTurno = p_numTurno;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Turno (unidad: %, turno: %) no encontrado.', p_codUnidad, p_numTurno;
    END IF;
    RAISE NOTICE 'Turno % de la unidad "%" modificado correctamente.', p_numTurno, p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_turno(
    p_codUnidad VARCHAR,
    p_numTurno  INTEGER
) RETURNS VOID AS $$
BEGIN
    -- Los informes asociados se eliminan en cascada (ON DELETE CASCADE en Informe)
    DELETE FROM Turno
    WHERE codUnidad = p_codUnidad AND numTurno = p_numTurno;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Turno (unidad: %, turno: %) no encontrado.', p_codUnidad, p_numTurno;
    END IF;
    RAISE NOTICE 'Turno % y sus informes han sido eliminados.', p_numTurno;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insertar_informe(
    p_codUnidad         VARCHAR,
    p_numInforme        INTEGER,
    p_fecha             DATE,
    p_hora              TIME,
    p_pacientesAtendInf INTEGER,
    p_pacientesAlta     INTEGER,
    p_pacientesAdmit    INTEGER,
    p_pacientesRegist   INTEGER,
    p_numTurno          INTEGER
) RETURNS VOID AS $$
BEGIN
    INSERT INTO Informe(codUnidad, numInforme, fecha, hora,
                        pacientesAtendInf, pacientesAlta, pacientesAdmit, pacientesRegist, numTurno)
    VALUES (p_codUnidad, p_numInforme, p_fecha, p_hora,
            p_pacientesAtendInf, p_pacientesAlta, p_pacientesAdmit, p_pacientesRegist, p_numTurno);
    RAISE NOTICE 'Informe % de la unidad "%" insertado correctamente.', p_numInforme, p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modificar_informe(
    p_codUnidad         VARCHAR,
    p_numInforme        INTEGER,
    p_fecha             DATE,
    p_hora              TIME,
    p_pacientesAtendInf INTEGER,
    p_pacientesAlta     INTEGER,
    p_pacientesAdmit    INTEGER,
    p_pacientesRegist   INTEGER,
    p_numTurno          INTEGER
) RETURNS VOID AS $$
BEGIN
    UPDATE Informe
    SET fecha             = p_fecha,
        hora              = p_hora,
        pacientesAtendInf = p_pacientesAtendInf,
        pacientesAlta     = p_pacientesAlta,
        pacientesAdmit    = p_pacientesAdmit,
        pacientesRegist   = p_pacientesRegist,
        numTurno          = p_numTurno
    WHERE codUnidad = p_codUnidad AND numInforme = p_numInforme;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Informe (unidad: %, informe: %) no encontrado.', p_codUnidad, p_numInforme;
    END IF;
    RAISE NOTICE 'Informe % de la unidad "%" modificado correctamente.', p_numInforme, p_codUnidad;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_informe(
    p_codUnidad  VARCHAR,
    p_numInforme INTEGER
) RETURNS VOID AS $$
BEGIN
    DELETE FROM Informe
    WHERE codUnidad = p_codUnidad AND numInforme = p_numInforme;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Informe (unidad: %, informe: %) no encontrado.', p_codUnidad, p_numInforme;
    END IF;
    RAISE NOTICE 'Informe % de la unidad "%" eliminado correctamente.', p_numInforme, p_codUnidad;
END;
$$ LANGUAGE plpgsql;