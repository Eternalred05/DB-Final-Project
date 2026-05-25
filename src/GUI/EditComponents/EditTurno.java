package GUI.EditComponents;

import DAO.*;
import Logic.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EditTurno extends javax.swing.JDialog {

    private UnidadDAO unidadDAO = new UnidadDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private TurnoDAO turnoDAO = new TurnoDAO();
    private Turno turnoOriginal;

    public EditTurno(java.awt.Frame parent, boolean modal, Turno t) {
        super(parent, modal);
        setTitle("Editar Turno");
        initComponents();
        configureElements(t);
    }

    private void configureElements(Turno t) {
        this.turnoOriginal = t;
        unidadField.setText(t.getCodUnidad());
        unidadField.setEnabled(false);
        medField.setText(t.getCodMedico());
        medField.setEnabled(false);
        txtNumTurno.setText(String.valueOf(t.getNumTurno()));
        txtNumTurno.setEnabled(false);
        spinnerAsig.setValue(t.getCantPacientes());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNumTurno = new Utils.JTextFieldEdited();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        spinnerAsig = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        medField = new Utils.JTextFieldEdited();
        unidadField = new Utils.JTextFieldEdited();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Unidad");

        txtNumTurno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumTurno.setLimit(8);
        txtNumTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumTurnoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Médico Responsable");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Número de Turno");

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        spinnerAsig.setModel(new javax.swing.SpinnerNumberModel(1, 1, 15, 1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Pacientes a Asignar");

        medField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        medField.setLimit(8);
        medField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medFieldActionPerformed(evt);
            }
        });

        unidadField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        unidadField.setLimit(8);
        unidadField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unidadFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtNumTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(spinnerAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(unidadField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(medField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addGap(117, 117, 117))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unidadField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerAsig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumTurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumTurnoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int nuevaCantidad = (Integer) spinnerAsig.getValue();
        int pacientesAtendidos = turnoOriginal.getPacientesAtend();

        boolean datosValidos = true;

        if (nuevaCantidad < pacientesAtendidos) {
            JOptionPane.showMessageDialog(this,
                    "No se puede asignar menos pacientes (" + nuevaCantidad
                    + ") que los ya atendidos (" + pacientesAtendidos + ").",
                    "Valor no permitido", JOptionPane.WARNING_MESSAGE);
            datosValidos = false;
        }

        if (datosValidos) {
            try {
                turnoDAO.modificarTurno(
                        turnoOriginal.getCodUnidad(),
                        turnoOriginal.getNumTurno(),
                        nuevaCantidad,
                        pacientesAtendidos,
                        turnoOriginal.getCodMedico()
                );
                JOptionPane.showMessageDialog(this, "Turno modificado exitosamente.");
                this.dispose();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al modificar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void medFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medFieldActionPerformed

    private void unidadFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unidadFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unidadFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private Utils.JTextFieldEdited medField;
    private javax.swing.JSpinner spinnerAsig;
    private Utils.JTextFieldEdited txtNumTurno;
    private Utils.JTextFieldEdited unidadField;
    // End of variables declaration//GEN-END:variables
}
