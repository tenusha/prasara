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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.DriverManager;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date; 




public class AddVehicle extends javax.swing.JFrame {

    /**
     * Creates new form AddVehicle
     */
    dbCon db = new dbCon();
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int row = -1;
    boolean flagForEdit =false;
     validation v=new validation();
     String edit;
    
    public AddVehicle() {
        initComponents();
        con= db.ConnectDB();
        viewEdit(false);
        clear();
        
    }

    AddVehicle(String veno_edit, String mod_edit, String typev_edit, String col_edit, String cumil_edit, String Date_edit, String yr_edit, String delname_edit, String statv_edit) throws SQLException
    {
         initComponents();//load init components

        con = db.ConnectDB();//db connection
         this.edit="edit";
        try {
            //set data to swing components
            java.util.Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(Date_edit);
            veno.setText(veno_edit);
            mod.setText(mod_edit);
            typev.setSelectedItem(typev_edit);
            col.setSelectedItem(col_edit);
            cumil.setText(cumil_edit);
            Date.setDate(date);
            yr.setText(yr_edit);
            delname.setText(delname_edit);
            statv.setSelectedItem(statv_edit);
            veno.setEditable(false);
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
        jLabel3 = new javax.swing.JLabel();
        statv = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        typev = new javax.swing.JComboBox<>();
        Date = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        veno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mod = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cumil = new javax.swing.JTextField();
        col = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        yr = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        delname = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(514, 482));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Add Vechile");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 13, 372, -1));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 490, 13));

        jLabel3.setText("Vechile  No");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 80, 35));

        statv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand New", "Second Owner", "Rented", " " }));
        statv.setSelectedIndex(-1);
        jPanel1.add(statv, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 180, 20));

        jLabel6.setText("Type");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 70, 35));

        jLabel7.setText("Status");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 50, 35));

        submit.setBackground(new java.awt.Color(0, 0, 255));
        submit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("Add Vehicle");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 140, 40));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 490, 13));

        typev.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "car", "van", "lorry", "bike", "tractors", "container truck" }));
        typev.setSelectedIndex(-1);
        jPanel1.add(typev, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 180, 20));

        Date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateActionPerformed(evt);
            }
        });
        jPanel1.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 180, -1));

        jLabel2.setText("Date of purchase");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        veno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                venoFocusLost(evt);
            }
        });
        veno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venoActionPerformed(evt);
            }
        });
        jPanel1.add(veno, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 180, 20));

        jLabel5.setText("Model");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modActionPerformed(evt);
            }
        });
        jPanel1.add(mod, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 180, 20));

        jLabel8.setText(" Colour");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        cumil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cumilActionPerformed(evt);
            }
        });
        cumil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cumilKeyTyped(evt);
            }
        });
        jPanel1.add(cumil, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 180, -1));

        col.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "white", "Black", "sky blue", "red", "grey", "orange" }));
        col.setSelectedIndex(-1);
        jPanel1.add(col, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 180, -1));

        jLabel9.setText("Current Milage");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        jLabel10.setText("Year");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 80, 20));

        yr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yrActionPerformed(evt);
            }
        });
        yr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yrKeyTyped(evt);
            }
        });
        jPanel1.add(yr, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 180, -1));

        jLabel11.setText("Dealer Name");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));
        jPanel1.add(delname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 180, -1));

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
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Minimize.png"))); // NOI18N
        jLabel16.setText("jLabel14");
        jLabel16.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel16.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel16.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, -1));

        btnEdit.setBackground(new java.awt.Color(0, 0, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Add Vehicle");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 140, 40));
        jPanel1.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 414, 150, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     boolean formValidate() throws SQLException{
        boolean flag=true;
    if ((typev.getSelectedIndex()) != -1) {
        if((col.getSelectedIndex())!= -1){
            if((statv.getSelectedIndex())!= -1){
                if(veno.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add Vehicle No", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if( ! v.vehiclenum(veno.getText())){
                    JOptionPane.showMessageDialog(null, "Invalid Vehicle No Format", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                 else
                {
                    
                     ResultSet rs = con.createStatement().executeQuery("SELECT vehicleno FROM vehicle WHERE vehicleno = '"+veno.getText()+"'");
                        if(rs.next()){
                            if(edit.equals("edit")){
                                return true;
                            }
                            else{
                                
                                JOptionPane.showMessageDialog(null, "vehicle No  exist", "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                              }
                        
                   
                                               
                }
                if(mod.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add Model Name", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }   
                if(delname.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add dealer Name", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }  
                if(yr.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Add year", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                } 
                 String A = yr.getText();
                int myint = Integer.parseInt(A);
  
                if(myint >= 2018 && myint <= 1990)
                 {
                    JOptionPane.showMessageDialog(null, "Invalid year", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                } 
                 if(Date.getDate()== null){
                    JOptionPane.showMessageDialog(null, "Add Date", "Error", JOptionPane.ERROR_MESSAGE);
                    flag= false;
                }
                else{
                      if(cumil.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Add current milage", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;  
                        }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "select status", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "select Color", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    else{
        JOptionPane.showMessageDialog(null, "select type", "Error", JOptionPane.ERROR_MESSAGE);
           return false;
    }
        return flag; 
    }
    
    
    
    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        try{ 
            
         if((formValidate())==true){
           
            try {

                String vehicleno = veno.getText();
                String model = mod.getText();               
                String typeofvehicle = String.valueOf(typev.getSelectedItem());  
                String color = String.valueOf(col.getSelectedItem()); 
                String currentmilage=cumil.getText();
                Date oDate = Date.getDate();//date selection combo eke name eka.     
                java.sql.Date sqlDate=new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
                String year=yr.getText();
                String dealername = delname.getText();
                String status = String.valueOf(statv.getSelectedItem());

            String sql = "insert into vehicle (vehicleno, model,  typeofvehicle, color,currentmilage,dateofpurchase,year,dealername,status) values(?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, vehicleno);
                pst.setString(2, model);
                pst.setString(3,  typeofvehicle);
                pst.setString(4,  color);
                pst.setString(5, currentmilage);
                pst.setDate(6, sqlDate);//pass a sql date object
                pst.setString(7, year);
                pst.setString(8, dealername);
                pst.setString(9, status);//status ekata ena walue eka pass karanna.
                
                
                int result = pst.executeUpdate();
                System.out.println(result);
                if (result == 1) {
                     JOptionPane.showMessageDialog (this, "New Vehicle Added", "Done", JOptionPane.INFORMATION_MESSAGE);
                     clear();
                }
                   
                    } catch (Exception ex) {
                        Logger.getLogger(AddVehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
         catch (SQLException ex) {
            Logger.getLogger(AddVehicle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitActionPerformed

    private void venoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_venoActionPerformed

    private void modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modActionPerformed

    private void DateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateActionPerformed

    private void yrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yrActionPerformed

    private void cumilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cumilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cumilActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        // TODO add your handling code here:
         try{ 
            
         if((formValidate())==true){
           
            try {

                String vehicleno = veno.getText();
                String model = mod.getText();               
                String typeofvehicle = String.valueOf(typev.getSelectedItem());  
                String color = String.valueOf(col.getSelectedItem()); 
                String currentmilage=cumil.getText();
                Date oDate = Date.getDate();//date selection combo eke name eka.     
                java.sql.Date sqlDate=new java.sql.Date(oDate.getTime());//sql date ekakata convert karala thiyenne
                String year=yr.getText();
                String dealername = delname.getText();
                String status = String.valueOf(statv.getSelectedItem());

            String sql = "update vehicle set  model=? ,typeofvehicle=?, color=?,currentmilage=?,dateofpurchase=?,year=?,dealername=?,status=? where vehicleno=?";
                pst = con.prepareStatement(sql);
                
                pst.setString(1, model);
                pst.setString(2,  typeofvehicle);
                pst.setString(3,  color);
                pst.setString(4, currentmilage);
                pst.setDate(5, sqlDate);//pass a sql date object
                pst.setString(6, year);
                pst.setString(7, dealername);
                pst.setString(8, status);//status ekata ena walue eka pass karanna.
                pst.setString(9, vehicleno);
                
                int result = pst.executeUpdate();
                System.out.println(result);
                if (result == 1) {
                    JOptionPane.showMessageDialog(this, "Vehicle  updated");
                        flagForEdit = false;
                        viewEdit(flagForEdit);
                        clear();
                }
                   
                    } catch (Exception ex) {
                        Logger.getLogger(AddVehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
         catch (SQLException ex) {
            Logger.getLogger(AddVehicle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void yrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yrKeyTyped
        // TODO add your handling code here:
         char ch=evt.getKeyChar();
       if(!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || ch ==KeyEvent.VK_DELETE ))
       {
           getToolkit().beep();
           evt.consume();
       }
        if (yr.getText().length() >= 4 ) // limit to 3 characters
        {
            getToolkit().beep();    
            evt.consume();
        }   
    }//GEN-LAST:event_yrKeyTyped

    private void cumilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cumilKeyTyped
        // TODO add your handling code here:
         char ch=evt.getKeyChar();
       if(!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || ch ==KeyEvent.VK_DELETE ))
       {
           getToolkit().beep();
           evt.consume();
       }
        if (yr.getText().length() >= 4 ) // limit to 3 characters
        {
            getToolkit().beep();    
            evt.consume();
        }  
    }//GEN-LAST:event_cumilKeyTyped

    private void venoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_venoFocusLost
        // TODO add your handling code here:
          boolean stat= v.vehiclenum(veno.getText());
        if(stat=true)
        {
            
        }
        else
        {
            
            JOptionPane.showMessageDialog(null,"Invalid vehicle format");
            veno.setText(" ");
            veno.grabFocus();
        }
    }//GEN-LAST:event_venoFocusLost

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
            java.util.logging.Logger.getLogger(AddVehicle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddVehicle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddVehicle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddVehicle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddVehicle().setVisible(true);
                }
                 catch (Exception e) {
                 Logger.getLogger(AddVehicle.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker Date;
    public javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> col;
    private javax.swing.JTextField cumil;
    private javax.swing.JTextField delname;
    private javax.swing.JLabel error;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField mod;
    private javax.swing.JComboBox<String> statv;
    public javax.swing.JButton submit;
    private javax.swing.JComboBox<String> typev;
    private javax.swing.JTextField veno;
    private javax.swing.JTextField yr;
    // End of variables declaration//GEN-END:variables

private void clear() {

        veno.setText(null);
        mod.setText(null);
        cumil.setText(null);
        yr.setText(null);
        delname.setText(null);
        typev.setSelectedItem(null);
        Date.setDate(null);
        col.setSelectedItem(null);
        statv.setSelectedItem(null);
        
    }
 private void viewEdit(boolean flag) {
        btnEdit.setVisible(flag);
        submit.setVisible(!flag);
    }

}
