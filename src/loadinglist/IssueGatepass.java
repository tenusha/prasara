/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadinglist;

/**
 *
 * @author pandu
 */
import inventory.dbCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class IssueGatepass extends javax.swing.JFrame {

    /**
     * Creates new form IssueGatepass
     */
    dbCon db = new dbCon();
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int row = -1;
    boolean flagForEdit =false;
    //String loadlist;
   String lostid;
    
    public IssueGatepass() throws SQLException{
        initComponents();
        
         con=db.ConnectDB();
         viewEdit(false);
         jPanel1.setVisible(true);
         ResultSet rs1 = con.createStatement().executeQuery("SELECT  empNo FROM employee");
        while(rs1.next()){
            emp.addItem(rs1.getString("empNo"));
        }
        emp.setSelectedIndex(-1);
    }

    IssueGatepass(String vehicleno_edit, String grn_edit, String loadlistId,String empno_edit) throws SQLException{
        
       con = db.ConnectDB();//db connection
        initComponents();
         ResultSet rs1 = con.createStatement().executeQuery("SELECT  empNo FROM employee");
        while(rs1.next()){
            emp.addItem(rs1.getString("empNo"));
        }
        emp.setSelectedIndex(-1);
       
        try {
            //set data to swing components
           // java.util.Date date = new Date();
           // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           // date = sdf.parse(Date1_edit);
            veno.setText(vehicleno_edit);
            grn.setText(grn_edit);
            emp.setSelectedItem(empno_edit);
            
            lostid=loadlistId;
           // Date.setDate(date);
            
            flagForEdit=true;
            
            if(flagForEdit){
                //use to show an edit button instead of add Machine button
                 viewEdit(flagForEdit);
            }

        } catch (Exception e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        loadingcustomer = new javax.swing.JLabel();
        Customer = new javax.swing.JLabel();
        Stylel = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        Date = new org.jdesktop.swingx.JXDatePicker();
        Datel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        veno = new javax.swing.JTextField();
        grn = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        emp = new javax.swing.JComboBox<>();

        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(514, 482));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Issue New Gatepass");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 13, 372, -1));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 490, 13));

        loadingcustomer.setText("Vehicle No");
        jPanel1.add(loadingcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 100, 35));

        Customer.setText("Gatepass no");
        jPanel1.add(Customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 90, 35));

        Stylel.setText("Issued by");
        jPanel1.add(Stylel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 90, 35));

        submit.setBackground(new java.awt.Color(0, 0, 255));
        submit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("Issue Gatepass");
        submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitMouseClicked(evt);
            }
        });
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 140, 40));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 490, 13));
        jPanel1.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 200, 30));

        Datel.setText("Date");
        jPanel1.add(Datel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Minimize.png"))); // NOI18N
        jLabel19.setText("jLabel14");
        jLabel19.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel19.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel19.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Close.png"))); // NOI18N
        jLabel20.setText("jLabel14");
        jLabel20.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel20.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel20.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, -1));
        jPanel1.add(veno, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 200, 30));
        jPanel1.add(grn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 200, 30));

        btnEdit.setBackground(new java.awt.Color(0, 0, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Issue Gatepass");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 140, 40));

        jPanel1.add(emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 200, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

      boolean formValidate() throws SQLException
    {
    boolean flag=true;
    
        
           
                if((emp.getSelectedIndex()) == -1){
                    JOptionPane.showMessageDialog(null, "Add Employee No", "Error", JOptionPane.ERROR_MESSAGE);
                    flag= false;
                }
                if(grn.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add Gatepass No", "Error", JOptionPane.ERROR_MESSAGE);
                    flag= false;
                }
                if(Date.getDate()== null){
                    JOptionPane.showMessageDialog(null, "Add Date", "Error", JOptionPane.ERROR_MESSAGE);
                    flag= false;
                }
                 else{
                 if(veno.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add vehicle No", "Error", JOptionPane.ERROR_MESSAGE);
                    flag= false;
                 }
                }
                return flag;
        
    }
    
    
    private void submitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitMouseClicked
        // TODO add your handling code here:
        

    }//GEN-LAST:event_submitMouseClicked

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
         try {
////begin edit

           if((formValidate())==true){
              // JOptionPane.showMessageDialog(this, "Fill required fields");
              try {
                  
                  String vehicleno =veno.getText();
                  String gatepass = grn.getText();
                  Date oDate = Date.getDate();//date selection combo eke name eka.

                  java.sql.Date sqlDate=new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
            //simple date format ekata danna one nehe. 
                 String empNo = String.valueOf(emp.getSelectedItem());  
                  
                 
                  
                String sql = "update  loadlist  set vehicleno=?, gatepass=? ,dateofdept=?,empNo=?  where loadlistno=? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, vehicleno);
                pst.setString(2, gatepass);
                pst.setDate(3, sqlDate);
                pst.setString(4, empNo);
                pst.setString(5, lostid);
                
                int result = pst.executeUpdate();
                if (result == 1) {
                   
                   JOptionPane.showMessageDialog (this, " GRN and Vechile No Added", "Done", JOptionPane.INFORMATION_MESSAGE);
                   clear();
                   
                }
                   
               } catch (Exception ex) {
                        Logger.getLogger(IssueGatepass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
         catch (SQLException ex) {
            Logger.getLogger(IssueGatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitActionPerformed

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        
        
        try {

            if ((formValidate()) == true) {
                // JOptionPane.showMessageDialog(this, "Fill required fields");
                try {

                    String vehicleno = veno.getText();
                    
                    String gatepass = grn.getText(); ;

                    Date oDate = Date.getDate();//date selection combo eke name eka.

                    //dateofpurchase eka date object ekak kara.
                    java.sql.Date sqlDate = new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
                    //simple date format ekata danna one nehe. 
                    String empNo = String.valueOf(this.emp.getSelectedItem());//meka sql walata pass karanna
                    

                     String sql = "update  loadlist  set vehicleno=?, gatepass=? ,dateofdept=?,empNo=?  where loadlistno=? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, vehicleno);
                pst.setString(2, gatepass);
                pst.setDate(3, sqlDate);
                pst.setString(4, empNo);
                pst.setString(5, lostid);
                   

                    int result = pst.executeUpdate();
                    if (result == 1) {

                        JOptionPane.showMessageDialog(this, "IssueGatepass  updated");
                        flagForEdit = false;
                        viewEdit(flagForEdit);
                        clear();
                        this.setVisible(false);

                    }

                } catch (Exception ex) {
                    Logger.getLogger(IssueGatepass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnEditActionPerformed

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
            java.util.logging.Logger.getLogger(IssueGatepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueGatepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueGatepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueGatepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IssueGatepass().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(IssueGatepass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Customer;
    private org.jdesktop.swingx.JXDatePicker Date;
    private javax.swing.JLabel Datel;
    private javax.swing.JLabel Stylel;
    public javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> emp;
    private javax.swing.JTextField grn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel loadingcustomer;
    public javax.swing.JButton submit;
    private javax.swing.JTextField veno;
    // End of variables declaration//GEN-END:variables

private void clear() {

        grn.setText(null);
        veno.setText(null);
        Date.setDate(null);
        emp.setSelectedItem(null);
           
    }
 private void viewEdit(boolean flag) {
        btnEdit.setVisible(flag);
        submit.setVisible(!flag);
    }
}
