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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Ranmal
 */
public class addChemical extends javax.swing.JFrame {

    /**
     * 
     */
    String st="";
    
    public addChemical() throws SQLException {
        initComponents();
        getContentPane().setBackground(Color.white);
        
        dbCon d = new dbCon();
        d.connect();
        
        AutoCompleteDecorator.decorate(sup);
        sup.removeAllItems();
        
        ResultSet rs = con.createStatement().executeQuery("SELECT supID FROM supplier");
        while(rs.next()){
            sup.addItem(rs.getString("supID"));
        }
        sup.setSelectedIndex(-1);
    }
    
    public addChemical(String che) throws SQLException {
        initComponents();
        
        jButton1.setText("Edit Chemical");
        st="edit";
        
        getContentPane().setBackground(Color.white);
        
        dbCon d = new dbCon();
        d.connect();
        
        AutoCompleteDecorator.decorate(sup);
        sup.removeAllItems();
        
        ResultSet rs = con.createStatement().executeQuery("SELECT supID FROM supplier");
        while(rs.next()){
            sup.addItem(rs.getString("supID"));
        }
        sup.setSelectedIndex(-1);
        
        ResultSet edit = con.createStatement().executeQuery("SELECT * FROM chemical WHERE chemical = '"+che+"'");
        edit.next();
        
        chemical.setText(edit.getString("chemical"));
        chemical.setEditable(false);
        
        name.setText(edit.getString("name"));
        qty.setText(edit.getString("qty"));
        color.setText(edit.getString("color"));
        sup.setSelectedItem(edit.getString("supplier"));
        cd.setText(edit.getString("description"));
        
        qty.setEditable(false);
        
    }
    
    boolean checkDouble(String no){
        try  {  
            double d = Double.parseDouble(no);  
        }  
        catch(NumberFormatException nfe){  
            return false;  
        }  
        return true; 
    }
    
    boolean formVal() throws SQLException{
        if(chemical.getText().equals("")){
            chemical.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            error.setText("please add a chemical no");
            return false;
        }
        else{
            if(name.getText().equals("")){
                name.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                error.setText("please add chemical name");
                return false;
            }
            else{
                if(sup.getSelectedIndex()==-1){
                    sup.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    error.setText("please select a supplier");
                    return false;
                }
                else{
                    if(color.getText().equals("")){
                        color.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        error.setText("please add chemical colour");
                        return false;
                    }
                    else{
                        if(qty.getText().equals("")){
                            qty.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            error.setText("please add chemical quantity");
                            return false;
                        }
                        else{
                            if(checkDouble(qty.getText())){
                                //return true;
                                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM chemical WHERE chemical = '"+chemical.getText()+"'");
                                if(rs.next()){
                                    if(st.equals("edit")){
                                        return true;
                                    }
                                    else{
                                        chemical.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                        error.setText("Chemical Exist");
                                        //JOptionPane.showMessageDialog(null, "customer's style color exist", "Error", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }
                                }
                                else{
                                    return true;
                                }
                            }
                            else{
                                qty.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                error.setText("invalid quantity");
                                return false;
                            }
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

        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        chemical = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        error = new javax.swing.JLabel();
        sup = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        color = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cd = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(540, 468));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(540, 468));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Chemical Ref No");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 115, -1, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Add Chemical");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 372, -1));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 520, 13));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 520, 13));

        chemical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemicalActionPerformed(evt);
            }
        });
        getContentPane().add(chemical, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 114, 380, 30));
        getContentPane().add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 154, 380, 30));

        jLabel3.setText("Chemical Name");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 155, -1, 30));

        jLabel4.setText("Supplier");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 195, -1, 30));

        error.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 219, 33));

        sup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sup.setSelectedIndex(-1);
        getContentPane().add(sup, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 194, 380, 30));

        jLabel5.setText("Chemical Color");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 235, -1, 30));

        color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorActionPerformed(evt);
            }
        });
        getContentPane().add(color, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 234, 380, 30));

        jLabel6.setText("Chemical Quantity (g)");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 275, -1, 30));

        qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyActionPerformed(evt);
            }
        });
        getContentPane().add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 274, 380, 30));

        jLabel7.setText("Chemical Details");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 315, -1, 20));

        cd.setColumns(20);
        cd.setRows(5);
        jScrollPane1.setViewportView(cd);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 314, 380, 83));

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Chemical");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 415, 110, 30));

        minimize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Minimize.png"))); // NOI18N
        minimize.setAlignmentY(0.0F);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        getContentPane().add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, -1, -1));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Close.png"))); // NOI18N
        close.setAlignmentY(0.0F);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chemicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemicalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chemicalActionPerformed

    private void colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorActionPerformed

    private void qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            chemical.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            name.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            qty.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            color.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
            sup.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
            error.setText("");
            
            if(formVal()){
                if(st.equals("edit")){
                    con.prepareStatement("UPDATE chemical set name='"+name.getText()+"', qty="+Double.parseDouble(qty.getText())+" ,supplier='"+sup.getSelectedItem()+"', color='"+color.getText()+"' ,description='"+cd.getText()+"' WHERE chemical ='"+chemical.getText()+"'").execute();
                    JOptionPane.showMessageDialog (null, "Chemcal Updated", "Done", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                }
                else{
                    try {
                        String ch = chemical.getText();
                        String na = name.getText();
                        String sp = (String) sup.getSelectedItem();
                        double qt = Double.parseDouble(qty.getText());
                        String cl = color.getText();
                        String des = cd.getText();

                        con.prepareStatement("INSERT INTO chemical VALUES('"+ch+"','"+na+"',"+qt+",'"+sp+"','"+cl+"','"+des+"')").execute();
                        JOptionPane.showMessageDialog (null, "New Chemical Added", "Done", JOptionPane.INFORMATION_MESSAGE);

                        chemical.setText("");
                        name.setText("");
                        qty.setText("");
                        color.setText("");
                        cd.setText("");
                        sup.setSelectedIndex(-1);

                    } catch (SQLException ex) {
                        Logger.getLogger(addChemical.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(addChemical.class.getName()).log(Level.SEVERE, null, ex);
        }
        Home.chemical.refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here:
        //System.exit(0);
        this.dispose();
    }//GEN-LAST:event_closeMouseClicked

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
            java.util.logging.Logger.getLogger(addChemical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addChemical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addChemical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addChemical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new addChemical().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(addChemical.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea cd;
    private javax.swing.JTextField chemical;
    private javax.swing.JLabel close;
    private javax.swing.JTextField color;
    private javax.swing.JLabel error;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel minimize;
    private javax.swing.JTextField name;
    private javax.swing.JTextField qty;
    private javax.swing.JComboBox sup;
    // End of variables declaration//GEN-END:variables
}
