/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shanuka;

import ITP.Home;
import inventory.dbCon;
import static inventory.dbCon.con;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author it16175358
 */
public class addMaterial extends javax.swing.JFrame {

    String st = "";

    /**
     * Creates new form addMaterials
     */
    public addMaterial() {
        initComponents();
        getContentPane().setBackground(Color.white);

        dbCon d = new dbCon();
        d.connect();
    }

    public addMaterial(String mat) throws SQLException {
        initComponents();
        jButton1.setText("Edit Material");
        st = "edit";

        getContentPane().setBackground(Color.white);

        dbCon d = new dbCon();
        d.connect();

        ResultSet edit = con.createStatement().executeQuery("SELECT * FROM material WHERE materialNo = '" + mat + "'");
        edit.next();

        mno.setText(mat);
        mno.setEditable(false);

        mu.setText(edit.getString("unit"));
        mc.setText(edit.getString("color"));
        cat.setSelectedItem(edit.getString("category"));
        des.setText(edit.getString("description"));

    }

    boolean formVal() throws SQLException {

        if (mno.getText().equals("")) {
            mno.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            error.setText("please add material no");
            return false;
        } else {
            if (cat.getSelectedIndex() == -1) {
                cat.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                error.setText("please select a category");
                return false;
            } else {
                if (mu.getText().equals("")) {
                    mu.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    error.setText("please add material unit");
                    return false;
                } else {
                    if (mc.getText().equals("")) {
                        mc.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        error.setText("please add material color");
                        return false;
                    } else {
                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM material WHERE materialNo = '" + mno.getText() + "'");
                        if (rs.next()) {
                            if (st.equals("edit")) {
                                return true;
                            } else {
                                mno.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                error.setText("Material Exist");
                                //JOptionPane.showMessageDialog(null, "customer's style color exist", "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        error = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        mno = new javax.swing.JTextField();
        cat = new javax.swing.JComboBox<>();
        mu = new javax.swing.JTextField();
        mc = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        des = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(533, 419));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(533, 419));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        error.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, 220, 30));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 54, -1, -1));

        jLabel2.setText("Materials No");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        jLabel4.setText("Category");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, -1, -1));

        jLabel5.setText("Material Unit");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        jLabel6.setText("Material Colour");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Material Details For Invoice");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 157, 40));
        getContentPane().add(mno, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 330, 30));

        cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silk", "Cotton", "Matte", "Polyestrine" }));
        cat.setSelectedIndex(-1);
        getContentPane().add(cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 330, 31));
        getContentPane().add(mu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 330, 30));
        getContentPane().add(mc, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 330, 29));

        des.setColumns(20);
        des.setRows(5);
        jScrollPane1.setViewportView(des);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 330, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 204));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Material");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, -1, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Add Material");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 372, -1));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 510, 13));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 510, 13));

        minimize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Minimize.png"))); // NOI18N
        minimize.setAlignmentY(0.0F);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        getContentPane().add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 0, -1, -1));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Close.png"))); // NOI18N
        close.setAlignmentY(0.0F);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here:
        //System.exit(0);
        this.dispose();
    }//GEN-LAST:event_closeMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            mno.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            mu.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            mc.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            des.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            cat.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
            error.setText("");
            String cate = (String) cat.getSelectedItem();
            if (formVal()) {
                if (st.equals("edit")) {
                    con.prepareStatement("UPDATE material set category='"+cate+"', unit='"+mu.getText()+"' ,color='"+mc.getText()+"', description='"+des.getText()+"' WHERE materialNo='"+mno.getText()+"'").execute();
                    JOptionPane.showMessageDialog(null, "Material Updated", "Done", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {

                    try {
                        con.prepareStatement("INSERT INTO material VALUES('" + mno.getText() + "','" + cate + "','" + mu.getText() + "','" + mc.getText() + "','" + des.getText() + "')").execute();
                        JOptionPane.showMessageDialog(null, "New Material Added", "Done", JOptionPane.INFORMATION_MESSAGE);

                        mno.setText("");
                        mu.setText("");
                        mc.setText("");
                        des.setText("");
                        cat.setSelectedIndex(-1);
                    } catch (SQLException ex) {
                        Logger.getLogger(addMaterial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(addMaterial.class.getName()).log(Level.SEVERE, null, ex);
        }
        Home.material.refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(addMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addMaterial().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cat;
    private javax.swing.JLabel close;
    private javax.swing.JTextArea des;
    private javax.swing.JLabel error;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField mc;
    private javax.swing.JLabel minimize;
    private javax.swing.JTextField mno;
    private javax.swing.JTextField mu;
    // End of variables declaration//GEN-END:variables
}
