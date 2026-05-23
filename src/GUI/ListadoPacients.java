package GUI;

import DAO.*;
import Logic.*;
import Aux.*;
import GUI.EditComponents.EditPaciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ListadoPacients extends javax.swing.JDialog {

    private HospitalDAO hospitalDAO = new HospitalDAO();
    private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private UnidadDAO unidadDAO = new UnidadDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    public ListadoPacients(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Listado de Pacientes");
        configurarTabla();
        cargarHospitales();
        configurarEventos();

    }

    private void configurarTabla() {
        String[] columnas = {"Hospital", "Departamento", "Unidad", "Nº Hist. Clínica",
            "Nombre", "Fecha Nac.", "Dirección", "codUnidad"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPacientes.setModel(model);
        tablaPacientes.getTableHeader().setReorderingAllowed(false);

        tablaPacientes.getColumnModel().getColumn(7).setMinWidth(0);
        tablaPacientes.getColumnModel().getColumn(7).setMaxWidth(0);
        tablaPacientes.getColumnModel().getColumn(7).setWidth(0);
    }

    private void cargarHospitales() {
        comboHospital.removeAllItems();
        comboDepartamento.removeAllItems();
        comboUnidad.removeAllItems();

        comboHospital.addItem(new Hospital("-- Todos los hospitales --", null));
        ArrayList<Hospital> hospitales = hospitalDAO.listarHospitales();
        for (Hospital h : hospitales) {
            comboHospital.addItem(h);
        }
    }

    private void cargarDepartamentos(String codHospital) {
        comboDepartamento.removeAllItems();
        comboUnidad.removeAllItems();

        comboDepartamento.addItem(new Departamento("-- Todos los departamentos --", null, null));
        if (codHospital == null) {
            ArrayList<Departamento> todos = departamentoDAO.listarDpt();
            for (Departamento d : todos) {
                comboDepartamento.addItem(d);
            }
        } else {
            ArrayList<Departamento> filtrados = departamentoDAO.listarDepartamentosPorHospital(codHospital);
            for (Departamento d : filtrados) {
                comboDepartamento.addItem(d);
            }
        }
    }

    private void cargarUnidades(String codDpt) {
        comboUnidad.removeAllItems();
        comboUnidad.addItem(new Unidad("-- Todas las unidades --", null, null, null));
        if (codDpt != null) {
            ArrayList<Unidad> unidades = unidadDAO.listarUnidadesPorDepartamento(codDpt);
            for (Unidad u : unidades) {
                comboUnidad.addItem(u);
            }
        }
    }

    private void configurarEventos() {
        comboHospital.addActionListener(e -> {
            Hospital hosp = (Hospital) comboHospital.getSelectedItem();
            String codHosp = (hosp != null) ? hosp.getId() : null;
            cargarDepartamentos(codHosp);
        });

        comboDepartamento.addActionListener(e -> {
            Departamento dept = (Departamento) comboDepartamento.getSelectedItem();
            String codDpt = (dept != null) ? dept.getId() : null;
            cargarUnidades(codDpt);
        });
    }

    private void llenarTabla(ArrayList<PacienteListado> lista) {
        DefaultTableModel model = (DefaultTableModel) tablaPacientes.getModel();
        model.setRowCount(0);
        for (PacienteListado p : lista) {
            model.addRow(new Object[]{
                p.getHospital(),
                p.getDepartamento(),
                p.getUnidad(),
                p.getNumHistClinica(),
                p.getNombrePac(),
                p.getFechaNacimiento(),
                p.getDireccion(),
                p.getCodUnidad()
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboHospital = new javax.swing.JComboBox<>();
        comboUnidad = new javax.swing.JComboBox<>();
        comboDepartamento = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Unidad");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hospital");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Departamento");

        jButton1.setText("Generar Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaPacientes);

        jButton2.setText("Dar de Baja");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(163, 163, 163)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(166, 166, 166))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(comboHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(comboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(138, 138, 138)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboHospital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addGap(19, 19, 19))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(160, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(57, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Hospital hosp = (Hospital) comboHospital.getSelectedItem();
        Departamento dept = (Departamento) comboDepartamento.getSelectedItem();
        Unidad unid = (Unidad) comboUnidad.getSelectedItem();

        String codHosp = (hosp != null && hosp.getId() != null) ? hosp.getId() : null;
        String codDpt = (dept != null && dept.getId() != null) ? dept.getId() : null;
        String codUni = (unid != null && unid.getId() != null) ? unid.getId() : null;

        try {
            ArrayList<PacienteListado> lista = pacienteDAO.listarPacientesReporte(codHosp, codDpt, codUni);
            llenarTabla(lista);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int filas = tablaPacientes.getSelectedRows().length;
        if (filas != 1) {
            JOptionPane.showMessageDialog(null, "Seleccione solo un paciente", "Error al escoger un paciente", HEIGHT);
        } else {
            int fila = tablaPacientes.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tablaPacientes.getModel();
            String numHistClinica = model.getValueAt(fila, 3).toString();
            String codUnidad = model.getValueAt(fila, 7).toString();
            String nombrePac = model.getValueAt(fila, 4).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar al paciente " + nombrePac + " (HC " + numHistClinica + ")?",
                    "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    pacienteDAO.eliminarPaciente(codUnidad, numHistClinica);
                    JOptionPane.showMessageDialog(this, "Paciente eliminado.");
                    jButton1ActionPerformed(null);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        boolean exists = false;
        int filasSeleccionadas = tablaPacientes.getSelectedRows().length;

        if (filasSeleccionadas == 1) {
            exists = true;
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione solo un paciente para modificar.",
                    "Error al escoger un paciente", JOptionPane.WARNING_MESSAGE);
        }

        if (exists) {
            int fila = tablaPacientes.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tablaPacientes.getModel();
            String numHistClinica = model.getValueAt(fila, 3).toString();
            String codUnidad = model.getValueAt(fila, 7).toString();

            Paciente paciente = pacienteDAO.obtenerPaciente(codUnidad, numHistClinica);
            boolean pacienteExiste = (paciente != null);

            if (!pacienteExiste) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el paciente en la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (pacienteExiste) {
                try {
                    EditPaciente ah = new EditPaciente(null, true, paciente);
                    ah.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ah.setLocationRelativeTo(null);
                    ah.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jButton1ActionPerformed(null);
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

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
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListadoPacients dialog = new ListadoPacients(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<Object> comboDepartamento;
    private javax.swing.JComboBox<Object> comboHospital;
    private javax.swing.JComboBox<Object> comboUnidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPacientes;
    // End of variables declaration//GEN-END:variables
}
