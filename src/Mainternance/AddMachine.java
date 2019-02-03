/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mainternance;

/**
 *
 * @author pandu
 */
import inventory.dbCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date; 
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Mainternance.ViewMachine;
import java.text.SimpleDateFormat;
import javax.swing.JComponent;


public class AddMachine extends javax.swing.JFrame {

    /**
     * Creates new form AddMachine
     */
    dbCon db = new dbCon();
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int row = -1;
    boolean flagForEdit =false;
    validation v=new validation();
   String edit;
    
    public AddMachine() {
        initComponents();
        
          
          con= db.ConnectDB();
           viewEdit(false);
      
          clear();
    }

    AddMachine(String machrefno_edit, String machname_edit, String manu_edit, String Date_edit, String dept_edit, String status_edit) {

        
        
        initComponents();//load init components

        con = db.ConnectDB();//db connection

         this.edit="edit";
        try {
            //set data to swing components
            java.util.Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(Date_edit);
            machrefno.setText(machrefno_edit);
            machname.setText(machname_edit);
            manu.setSelectedItem(manu_edit);
            Date.setDate(date);
            dept.setSelectedItem(dept_edit);
            stat.setSelectedItem(status_edit);
            flagForEdit=true;
            machrefno.setEditable(false);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        stat = new javax.swing.JComboBox<>();
        manu = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        dept = new javax.swing.JComboBox<>();
        Date = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        machrefno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        machname = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(514, 482));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Add Machine");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 13, 372, -1));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 490, 13));

        jLabel3.setText("Machine Ref no");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 110, 35));

        jLabel4.setText("Manufacturer");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, 35));

        stat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand New", "Used One" }));
        stat.setSelectedIndex(-1);
        jPanel1.add(stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 180, 25));

        manu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electrolux", "Speed Queen", "Whirlpool", "Unimac", "Magtag", "Wascomat" }));
        manu.setSelectedIndex(-1);
        jPanel1.add(manu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 180, 25));

        jLabel6.setText("Department");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 90, 35));

        jLabel7.setText("Status");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 50, 35));

        submit.setBackground(new java.awt.Color(0, 0, 255));
        submit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("Add Machine");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 140, 40));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 490, 13));

        dept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B/W Qc", "Washing ", "Hydro ", "Wet Qc", "Drying ", "Cool drying", "Chemicals", "A/W Qc" }));
        dept.setSelectedIndex(-1);
        jPanel1.add(dept, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 180, 25));
        jPanel1.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 180, -1));

        jLabel2.setText("Date of purchase");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        machrefno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machrefnoActionPerformed(evt);
            }
        });
        jPanel1.add(machrefno, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 180, 25));

        jLabel5.setText("Machine Name");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        machname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machnameActionPerformed(evt);
            }
        });
        jPanel1.add(machname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 180, 25));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Minimize.png"))); // NOI18N
        jLabel14.setText("jLabel14");
        jLabel14.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel14.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel14.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Close.png"))); // NOI18N
        jLabel17.setText("jLabel14");
        jLabel17.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel17.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 0, -1, -1));

        btnEdit.setBackground(new java.awt.Color(0, 0, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit Machine");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 140, 40));

        error.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 364, 290, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    boolean formValidate() throws SQLException{
        boolean flag=true;
        
        //if(v.validateDateForward(new SimpleDateFormat("dd-MM-yyyy").format(Date.getDate()), error)){
    if ((stat.getSelectedIndex()) != -1) {
        if((manu.getSelectedIndex())!= -1){
            if((dept.getSelectedIndex())!= -1){
                if(machrefno.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add Machine Reference No", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                 if(! (machrefno.getText().startsWith("M") && machrefno.getText().length() >1) ){
                    JOptionPane.showMessageDialog(null, "Invalid Machine Number Format.Enter M<Numeric> Only ", "Error", JOptionPane.ERROR_MESSAGE);
                     return false; 
                    }
                    
                else
                {
                    
                    if(v.validateDateForward(new SimpleDateFormat("dd-MM-yyyy").format(Date.getDate()), error)){
                       ResultSet rs = con.createStatement().executeQuery("SELECT machinerefno FROM machine WHERE machinerefno = '"+machrefno.getText()+"'");
                        if(rs.next()){
                            
                                 if(edit.equals("edit")){
                                return true;
                            }
                            else{
                                
                                JOptionPane.showMessageDialog(null, "Tool No  exist", "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                            }
                    
                    }else{
                      return false;
                    }
                        
                }
               
                    if(machname.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Add Machine Name", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }   
                
            }
            else{
                JOptionPane.showMessageDialog(null, "select department", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "select Manufacturer", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    else{
        JOptionPane.showMessageDialog(null, "select status", "Error", JOptionPane.ERROR_MESSAGE);
           return false;
    }
        //}
        return flag; 
    }

    private void machnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_machnameActionPerformed

    private void machrefnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machrefnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_machrefnoActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
     
    
    try{    
        if((formValidate())==true){
           
        try {

            String machinerefno = machrefno.getText();
            String machinename = machname.getText();
            String manufacturer = String.valueOf(manu.getSelectedItem());  
            Date oDate = Date.getDate();//date selection combo eke name eka.     
            java.sql.Date sqlDate=new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
            String department =String.valueOf(dept.getSelectedItem());
            String status=String.valueOf(stat.getSelectedItem());

            String sql = "insert into machine (machinerefno, machinename, manufacturer, dateofpurchase,department,status) values(?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, machinerefno);
                pst.setString(2, machinename);
                pst.setString(3, manufacturer);
                pst.setDate(4, sqlDate);//pass a sql date object
                pst.setString(5, department);
                pst.setString(6, status);//status ekata ena walue eka pass karanna.
                
                
                int result = pst.executeUpdate();
                System.out.println(result);
                if (result == 1) {
                     JOptionPane.showMessageDialog (this, "New Machine Added", "Done", JOptionPane.INFORMATION_MESSAGE);
                     ViewMachine b=new ViewMachine();
                     b.loadData();
                     clear();
                }
                   
                    } catch (Exception ex) {
                        Logger.getLogger(AddMachine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
         catch (SQLException ex) {
            Logger.getLogger(AddMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        try {

            if ((formValidate()) == true) {
                // JOptionPane.showMessageDialog(this, "Fill required fields");
                try {

                    String machinerefno = machrefno.getText();
                    String machinename = machname.getText();
                    String manufacturer = String.valueOf(manu.getSelectedItem());

                    Date oDate = Date.getDate();//date selection combo eke name eka.

                    //dateofpurchase eka date object ekak kara.
                    java.sql.Date sqlDate = new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
                    //simple date format ekata danna one nehe. 
                    String department = String.valueOf(dept.getSelectedItem());//meka sql walata pass karanna
                    String status = String.valueOf(stat.getSelectedItem());

                    String sql = "update machine set machinename=?, manufacturer=?, dateofpurchase=? ,department=? ,status=? where machinerefno=? ";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, machinename);
                    pst.setString(2, manufacturer);
                    pst.setDate(3, sqlDate);//pass a sql date object
                    pst.setString(4, department);
                    pst.setString(5, status);//status ekata ena walue eka pass karanna.
                    pst.setString(6, machinerefno);

                    int result = pst.executeUpdate();
                    if (result == 1) {

                        JOptionPane.showMessageDialog(this, "Machine  updated");
                        flagForEdit = false;
                        viewEdit(flagForEdit);
                        clear();

                    }

                } catch (Exception ex) {
                    Logger.getLogger(AddMachine.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(AddMachine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddMachine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddMachine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddMachine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddMachine().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AddMachine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker Date;
    public javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> dept;
    private javax.swing.JLabel error;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField machname;
    private javax.swing.JTextField machrefno;
    private javax.swing.JComboBox<String> manu;
    private javax.swing.JComboBox<String> stat;
    public javax.swing.JButton submit;
    // End of variables declaration//GEN-END:variables

    
    
    
    
private void clear() {

        machrefno.setText(null);
        machname.setText(null);
        manu.setSelectedItem(null);
        Date.setDate(null);
        dept.setSelectedItem(null);
        stat.setSelectedItem(null);
        
    }

   private void viewEdit(boolean flag) {
        btnEdit.setVisible(flag);
        submit.setVisible(!flag);
    }
}