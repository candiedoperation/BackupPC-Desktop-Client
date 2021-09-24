/*
    BackupPC Desktop Client
    Copyright (C) 2021  Atheesh Thirumalairajan

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package tk.cyberphase.bpcdesktopclient;

/**
 *
 * @author atheesh
 */
public class fileChooserDialog extends javax.swing.JDialog {
    
    rsyncDaemonConfAdd rsyncDaemonConfAddInstance;
    
    public void registerFileSelectionCompleteCallback(callbackInterface callbackinterface, String chosenFilePath) {
        callbackinterface.fileChoosingComplete(chosenFilePath);
    }
    
    public fileChooserDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);       
        initComponents();
        this.setLocationRelativeTo(null); //Initialize the Window at Screen Center
        System.out.println("[WARN] This Constructor does not have the ability to implement File Browser.");
    }    
        
    public fileChooserDialog(java.awt.Frame parent, boolean modal, rsyncDaemonConfAdd rsyncDaemonConfAddArgInstance) {
        super(parent, modal);
        rsyncDaemonConfAddInstance = rsyncDaemonConfAddArgInstance;        
        initComponents();
        this.setLocationRelativeTo(null); //Initialize the Window at Screen Center
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configFileChooserElement = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Choose a Folder for Backup");
        setAlwaysOnTop(true);
        setModal(true);
        setResizable(false);

        configFileChooserElement.setAcceptAllFileFilterUsed(false);
        configFileChooserElement.setApproveButtonText("Choose Folder");
        configFileChooserElement.setApproveButtonToolTipText("");
        configFileChooserElement.setFileHidingEnabled(false);
        configFileChooserElement.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        configFileChooserElement.setFocusCycleRoot(false);
        configFileChooserElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configFileChooserElementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(configFileChooserElement, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(configFileChooserElement, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void configFileChooserElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configFileChooserElementActionPerformed
        // TODO add your handling code here:
        System.out.print(evt.getActionCommand());
        if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
            callbackInterface callbackinterface = rsyncDaemonConfAddInstance;
            registerFileSelectionCompleteCallback(callbackinterface, configFileChooserElement.getSelectedFile().getAbsolutePath());
            //System.out.println(configFileChooserElement.getSelectedFile().getAbsolutePath()); //Debugging
            this.dispose();
        } else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection")) {
            this.dispose();
        }
    }//GEN-LAST:event_configFileChooserElementActionPerformed

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
            java.util.logging.Logger.getLogger(fileChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fileChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fileChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fileChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                fileChooserDialog dialog = new fileChooserDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JFileChooser configFileChooserElement;
    // End of variables declaration//GEN-END:variables
}
