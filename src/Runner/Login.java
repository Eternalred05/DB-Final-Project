/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Runner;

import java.awt.event.ItemEvent;

/**
 *
 * @author Alexandro
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        passwordField.setEchoChar('\u2022');
        updateButtonState();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accessButton = new javax.swing.JButton();
        passwordField = new Utils.JPasswordFieldEdited();
        passCheck = new java.awt.Checkbox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userField = new Utils.JTextFieldEdited();
        iconLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accessButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        accessButton.setText("Acceder");
        accessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessButtonActionPerformed(evt);
            }
        });
        getContentPane().add(accessButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 90, 40));

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        passwordField.setLimit(5);
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordFieldKeyReleased(evt);
            }
        });
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 217, -1));

        passCheck.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passCheck.setLabel("Mostrar");
        passCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                passCheckItemStateChanged(evt);
            }
        });
        getContentPane().add(passCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Contraseña");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("Usuario");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, -1, -1));

        userField.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        userField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userFieldActionPerformed(evt);
            }
        });
        userField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                userFieldKeyReleased(evt);
            }
        });
        getContentPane().add(userField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 217, -1));

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/LoginBG.jpg"))); // NOI18N
        getContentPane().add(iconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void passCheckItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_passCheckItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // Acción cuando se selecciona
            passwordField.setEchoChar((char) 0);
        } else {
            // Acción cuando se deselecciona
            passwordField.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_passCheckItemStateChanged

    private void userFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userFieldActionPerformed

    }//GEN-LAST:event_userFieldActionPerformed

    private void accessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessButtonActionPerformed
        String password = passwordField.getText();
        String user = userField.getText();


    }//GEN-LAST:event_accessButtonActionPerformed

    private void userFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userFieldKeyReleased
        updateButtonState();
    }//GEN-LAST:event_userFieldKeyReleased

    private void passwordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyReleased
        updateButtonState();
    }//GEN-LAST:event_passwordFieldKeyReleased
    private void updateButtonState() {
        if (!userField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            accessButton.setEnabled(true);
        } else {
            accessButton.setEnabled(false);
        }

    }

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accessButton;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private java.awt.Checkbox passCheck;
    private Utils.JPasswordFieldEdited passwordField;
    private Utils.JTextFieldEdited userField;
    // End of variables declaration//GEN-END:variables
}
