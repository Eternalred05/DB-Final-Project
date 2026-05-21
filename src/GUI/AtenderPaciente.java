package GUI;

import DAO.*;
import Logic.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Connection.ConnectionManager;
import java.awt.Component;
import java.util.ArrayList;

public class AtenderPaciente extends javax.swing.JDialog {

    private UnidadDAO unidadDAO = new UnidadDAO();
    private TurnoDAO turnoDAO = new TurnoDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    public AtenderPaciente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Atender Paciente");
        cargarUnidades();
        configurarEventos();
        configurarTabla();
    }

    private void configurarTabla() {
        String[] cols = {"Hist. Clínica", "Nombre", "Dirección", "Fecha Nac."};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPendientes.setModel(model);
        tablaPendientes.getTableHeader().setReorderingAllowed(false);
    }

    private void cargarUnidades() {
        comboUnidad.removeAllItems();
        comboUnidad.addItem(null);
        ArrayList<Unidad> unidades = unidadDAO.listarUnidades();
        for (Unidad u : unidades) {
            comboUnidad.addItem(u);
        }

        comboUnidad.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    value = "Ninguna";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        comboTurno.removeAllItems();
        comboTurno.addItem(null);
        comboTurno.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    value = "Ninguno";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        lblContador.setText("Atendidos: 0 / 0");
    }

    private void cargarTurnos(String codUnidad) {
        comboTurno.removeAllItems();
        comboTurno.addItem(null);
        if (codUnidad != null) {
            ArrayList<Turno> turnos = turnoDAO.listarTurnosPorUnidad(codUnidad);
            for (Turno t : turnos) {
                comboTurno.addItem(t);
            }
        }
    }

    private void configurarEventos() {
        comboUnidad.addActionListener(e -> {
            Unidad u = (Unidad) comboUnidad.getSelectedItem();
            String codUnidad = (u != null) ? u.getId() : null;
            cargarTurnos(codUnidad);

            DefaultTableModel model = (DefaultTableModel) tablaPendientes.getModel();
            model.setRowCount(0);
            lblContador.setText("Atendidos: 0 / 0");
        });

        comboTurno.addActionListener(e -> {
            Object selected = comboTurno.getSelectedItem();
            if (selected instanceof Turno) {
                Turno t = (Turno) selected;
                cargarPacientesPendientes(t.getCodUnidad(), t);
                actualizarContador(t);
            } else {

                DefaultTableModel model = (DefaultTableModel) tablaPendientes.getModel();
                model.setRowCount(0);
                lblContador.setText("Atendidos: 0 / 0");
            }
        });
        btnAtender.addActionListener(e -> atenderPaciente());
        btnNoAtender.addActionListener(e -> noAtenderPaciente());
    }

    private void cargarPacientesPendientes(String codUnidad, Turno turno) {
        DefaultTableModel model = (DefaultTableModel) tablaPendientes.getModel();
        model.setRowCount(0);
        ArrayList<Paciente> pendientes = pacienteDAO.listarPacientesNoAtendidos(codUnidad);
        for (Paciente p : pendientes) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getAddress(),
                p.getBirthDate()
            });
        }
    }

    private void actualizarContador(Turno turno) {
        lblContador.setText("Atendidos: " + turno.getPacientesAtend() + " / " + turno.getCantPacientes());
    }

    private String solicitarCausa() {
        String causa = JOptionPane.showInputDialog(this, "Ingrese la causa por la que no fue atendido:");
        String resultado;
        if (causa != null && causa.trim().isEmpty()) {
            resultado = null;
        } else {
            resultado = causa;
        }
        return resultado;
    }

    private void atenderPaciente() {
        boolean accionRealizada = false;
        String mensaje = "";
        String numHist = null;
        String codUnidad = null;
        Turno turno = null;
        int filaSeleccionada = -1;
        filaSeleccionada = tablaPendientes.getSelectedRow();

        int filasSize = 0;
        filasSize = tablaPendientes.getSelectedRowCount();

        if (filasSize != 1) {
            JOptionPane.showMessageDialog(null, "Seleccione solo un paciente", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (filaSeleccionada != -1) {
            numHist = (String) tablaPendientes.getValueAt(filaSeleccionada, 0);
            turno = (Turno) comboTurno.getSelectedItem();

            if (turno != null) {
                codUnidad = turno.getCodUnidad();
                Connection conn = null;
                try {
                    conn = ConnectionManager.getInstance().retrieveConnection();
                    conn.setAutoCommit(false);

                    String sqlPac = "UPDATE Paciente SET atendido = TRUE, causa = NULL WHERE codUnidad = ? AND numHistClinica = ? AND atendido = FALSE";
                    PreparedStatement stmtPac = conn.prepareStatement(sqlPac);
                    stmtPac.setString(1, codUnidad);
                    stmtPac.setString(2, numHist);
                    int filasPac = stmtPac.executeUpdate();
                    stmtPac.close();

                    if (filasPac > 0) {

                        String sqlTurno = "UPDATE Turno SET pacientesAtend = pacientesAtend + 1 WHERE codUnidad = ? AND numTurno = ? AND pacientesAtend < cantPacientes";
                        PreparedStatement stmtTurno = conn.prepareStatement(sqlTurno);
                        stmtTurno.setString(1, codUnidad);
                        stmtTurno.setInt(2, turno.getNumTurno());
                        int filasTurno = stmtTurno.executeUpdate();
                        stmtTurno.close();

                        if (filasTurno > 0) {
                            conn.commit();
                            accionRealizada = true;
                            mensaje = "Paciente atendido correctamente.";
                        } else {
                            conn.rollback();
                            mensaje = "No se puede atender, ya se ha atendido el máximo de pacientes de este turno.";
                        }
                    } else {
                        conn.rollback();
                        mensaje = "El paciente ya estaba atendido o no existe.";
                    }
                } catch (Exception e) {
                    if (conn != null) {
                        try {
                            conn.rollback();
                        } catch (Exception ex) {
                        }
                    }
                    mensaje = "Error: " + e.getMessage();
                } finally {
                    if (conn != null) {
                        try {
                            conn.setAutoCommit(true);
                            conn.close();
                        } catch (Exception ex) {
                        }
                    }
                }
            } else {
                mensaje = "Seleccione un turno primero.";
            }
        } else {
            mensaje = "Seleccione un paciente de la tabla.";
        }

        if (!mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        if (accionRealizada) {
            Turno turnoActualizado = turnoDAO.obtenerTurno(codUnidad, turno.getNumTurno());
            cargarPacientesPendientes(codUnidad, turnoActualizado);
            actualizarContador(turnoActualizado);
        }
    }

    private void noAtenderPaciente() {
        boolean accionRealizada = false;
        String mensaje = "";
        String numHist = null;
        String codUnidad = null;
        Turno turno = null;
        int filaSeleccionada = -1;

        filaSeleccionada = tablaPendientes.getSelectedRow();
        int filasSize = 0;
        filasSize = tablaPendientes.getSelectedRowCount();

        if (filasSize != 1) {
            JOptionPane.showMessageDialog(null, "Seleccione solo un paciente", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (filaSeleccionada != -1) {
            numHist = (String) tablaPendientes.getValueAt(filaSeleccionada, 0);
            turno = (Turno) comboTurno.getSelectedItem();

            if (turno != null) {
                codUnidad = turno.getCodUnidad();
                String causa = solicitarCausa();

                if (causa != null) {
                    Connection conn = null;
                    try {
                        conn = ConnectionManager.getInstance().retrieveConnection();
                        conn.setAutoCommit(false);

                        String sqlPac = "UPDATE Paciente SET causa = ? WHERE codUnidad = ? AND numHistClinica = ? AND atendido = FALSE";
                        PreparedStatement stmtPac = conn.prepareStatement(sqlPac);
                        stmtPac.setString(1, causa);
                        stmtPac.setString(2, codUnidad);
                        stmtPac.setString(3, numHist);
                        int filasAfectadas = stmtPac.executeUpdate();
                        stmtPac.close();

                        if (filasAfectadas > 0) {
                            conn.commit();
                            accionRealizada = true;
                            mensaje = "Causa registrada correctamente.";
                        } else {
                            conn.rollback();
                            mensaje = "El paciente ya fue atendido o no se encontró.";
                        }
                    } catch (Exception e) {
                        if (conn != null) {
                            try {
                                conn.rollback();
                            } catch (Exception ex) {
                            }
                        }
                        mensaje = "Error: " + e.getMessage();
                    } finally {
                        if (conn != null) {
                            try {
                                conn.setAutoCommit(true);
                                conn.close();
                            } catch (Exception ex) {
                            }
                        }
                    }
                } else {
                    mensaje = "Operación cancelada.";
                }
            } else {
                mensaje = "Seleccione un turno primero.";
            }
        } else {
            mensaje = "Seleccione un paciente de la tabla.";
        }

        if (!mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensaje);
        }

        if (accionRealizada) {
            Turno turnoActualizado = turnoDAO.obtenerTurno(codUnidad, turno.getNumTurno());
            cargarPacientesPendientes(codUnidad, turnoActualizado);
            actualizarContador(turnoActualizado);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboUnidad = new javax.swing.JComboBox<>();
        comboTurno = new javax.swing.JComboBox<>();
        btnAtender = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPendientes = new javax.swing.JTable();
        lblContador = new javax.swing.JLabel();
        btnNoAtender = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Unidad");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Turno");

        btnAtender.setText("Atender Paciente");
        btnAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderActionPerformed(evt);
            }
        });

        tablaPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaPendientes);

        lblContador.setText("Contador");

        btnNoAtender.setText("No Atender");
        btnNoAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoAtenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 131, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(222, 222, 222)
                                .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(182, 182, 182))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(258, 258, 258))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNoAtender)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAtender))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblContador)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContador)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 470, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtender)
                    .addComponent(btnNoAtender))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(160, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(57, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderActionPerformed

    }//GEN-LAST:event_btnAtenderActionPerformed

    private void btnNoAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoAtenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNoAtenderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListadoPacients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListadoPacients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListadoPacients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListadoPacients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AtenderPaciente dialog = new AtenderPaciente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtender;
    private javax.swing.JButton btnNoAtender;
    private javax.swing.JComboBox<Object> comboTurno;
    private javax.swing.JComboBox<Object> comboUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContador;
    private javax.swing.JTable tablaPendientes;
    // End of variables declaration//GEN-END:variables
}
