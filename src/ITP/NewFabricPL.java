/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ITP;

import com.sun.glass.events.KeyEvent;
import inventory.dbCon;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Ranmal
 */
public class NewFabricPL extends javax.swing.JFrame {

    
    public static boolean edit;
    
    /**
     * Creates new form NewFabricPL
     */
    public NewFabricPL(boolean edt,String pk) {
        
            initComponents();
            AutoCompleteDecorator.decorate(jComboBox1);
            AutoCompleteDecorator.decorate(jComboBox2);
            AutoCompleteDecorator.decorate(jComboBox4);
            AutoCompleteDecorator.decorate(jComboBox3);
            AutoCompleteDecorator.decorate(jComboBox5);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            jTextField1.setText(dt.format(Home.fillDate));
            jTextField6.setEditable(false);
            
            edit=edt;
            jLabel1.setText("EDIT FABRIC PACKING LIST");
            jButton1.setText("Update");
            System.out.println(edit+" "+pk);
            
            dbCon d = new dbCon();
            d.connect();
          try {
            ResultSet rs1= dbCon.con.createStatement().executeQuery("SELECT name FROM customer");
            while(rs1.next()){
                jComboBox4.addItem(rs1.getString("name"));
            }
            
            
            ResultSet rs3= dbCon.con.createStatement().executeQuery("SELECT empNo FROM employee ");
            while(rs3.next()){
                jComboBox2.addItem(rs3.getString("empNo"));
            }
            
            //filter option from other DBs
            
            ResultSet rs2 = dbCon.con.createStatement().executeQuery("SELECT * FROM splfabric WHERE batchNo ='"+pk+"'");
            while(rs2.next()){
                jTextField2.setText(rs2.getString("pktListNo"));
                jTextField4.setText(rs2.getString("GRN"));
                jTextField6.setText(rs2.getString("batchNo"));
                jTextField9.setText(rs2.getString("rolls"));
                jTextField5.setText(rs2.getString("lengthPerRoll"));
                jTextField12.setText(rs2.getString("totLenMeters"));
                jTextField13.setText(rs2.getString("totLenYards"));
                
                jComboBox4.setSelectedItem(rs2.getString("customer"));
                jComboBox1.setSelectedItem(rs2.getString("style"));
                jComboBox2.setSelectedItem(rs2.getString("empNo"));
                jComboBox3.setSelectedItem(rs2.getString("color"));
                jComboBox5.setSelectedItem(rs2.getString("material"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public NewFabricPL() {
        
            initComponents();
            AutoCompleteDecorator.decorate(jComboBox1);
            AutoCompleteDecorator.decorate(jComboBox2);
            AutoCompleteDecorator.decorate(jComboBox4);
            AutoCompleteDecorator.decorate(jComboBox3);
            AutoCompleteDecorator.decorate(jComboBox5);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            jTextField1.setText(dt.format(Home.fillDate));
            
            edit=false;
            System.out.println(edit);
            
            dbCon d = new dbCon();
            d.connect();
            
            
         try {
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT name FROM customer");
            while(rs.next()){
                jComboBox4.addItem(rs.getString("name"));
            }
            
            ResultSet rs2= dbCon.con.createStatement().executeQuery("SELECT empNo FROM employee ");
            while(rs2.next()){
                jComboBox2.addItem(rs2.getString("empNo"));
            }
            //filter option from other DBs
            
            jComboBox4.setSelectedIndex(-1);
            jComboBox2.setSelectedIndex(-1);
            jTextField9.setText("0");
            jTextField5.setText("0.0");
            jTextField12.setText("0.0");
            jTextField13.setText("0.0");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
         
        
    }
    
    
    public boolean formValidate() throws SQLException{
        
        
        ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT batchNo FROM splfabric WHERE batchNo = '"+jTextField6.getText()+"'");
        
            if(jTextField6.getText().equals("")){
                //JOptionPane.showMessageDialog(jComboBox4, "Empty Batch Number ", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField6.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Empty Batch Number");
                return false; 
            }if(! (jTextField6.getText().startsWith("B") && jTextField6.getText().length() >1) ){
                //JOptionPane.showMessageDialog(jComboBox4, "Invalid Batch Number Format.Enter B<Numeric> Only ", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField6.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("<html>Invalid Batch Number Format<br>Enter B(Numeric) Only<html>");
                return false; 
            }if(rs.next() && edit == false){
                //JOptionPane.showMessageDialog(jComboBox4, "Batch Number Already Exist", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField6.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Batch Number Already Exist");
                return false;
            }if(jTextField2.getText().equals("")){
                //JOptionPane.showMessageDialog(jComboBox4, "Empty Packing List Number", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField2.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Empty Packing List Number");
                return false;
            }if(! (jTextField2.getText().startsWith("P") && jTextField2.getText().length() >1) ){
                //JOptionPane.showMessageDialog(jComboBox4, "Invalid Packing List Number Format.Enter P<Numeric> Only ", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField2.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("<html>Invalid Packing List Number Format<br>Enter P(Numeric) Only<html>");
                return false; 
            }if(jTextField4.getText().equals("")){
                //JOptionPane.showMessageDialog(jComboBox4, "Empty GRN Number", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField4.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Empty GRN Number");
                return false; 
            }if(! (jTextField4.getText().startsWith("P") && jTextField4.getText().length() >1) ){
                //JOptionPane.showMessageDialog(jComboBox4, "Invalid GRN Number Format.Enter P<Numeric> Only ", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField4.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("<html>Invalid Packing List Number Format<br>Enter P(Numeric) Only<html>");
                return false;
            }if(jTextField5.getText().equals("")){
                //JOptionPane.showMessageDialog(jComboBox4, "Empty Length Per Roll", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField5.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Empty Length Per Roll");
                return false; 
            }if(jTextField9.getText().equals("")){
                //JOptionPane.showMessageDialog(jComboBox4, "Empty Rolls", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField9.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel6.setText("Empty Rolls");
                return false;
            }
                               
                               
        
        
        if((jComboBox4.getSelectedIndex())!= -1){
                if((jComboBox1.getSelectedIndex())!= -1){
                    if((jComboBox3.getSelectedIndex())!= -1){
                        if((jComboBox5.getSelectedIndex())!= -1){
                            if((jComboBox2.getSelectedIndex())!= -1){
                        }else{
                            //JOptionPane.showMessageDialog(jComboBox4, "Select a Employee", "Error", JOptionPane.ERROR_MESSAGE);
                            jComboBox2.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            jLabel6.setText("Select a Employee");
                            return false;
                            }
                        }else{
                            //JOptionPane.showMessageDialog(jComboBox4, "Select a Material", "Error", JOptionPane.ERROR_MESSAGE);
                            jComboBox5.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            jLabel6.setText("Select a Material");
                            return false;
                        }
                    }else{
                        //JOptionPane.showMessageDialog(jComboBox4, "Select a Color", "Error", JOptionPane.ERROR_MESSAGE);
                        jComboBox3.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        jLabel6.setText("Select a Color");
                        return false;
                    }
                }else{
                    //JOptionPane.showMessageDialog(jComboBox4, "Select a Style", "Error", JOptionPane.ERROR_MESSAGE);
                    jComboBox1.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    jLabel6.setText("Select a Style");
                    return false;
                }
        }else{
            //JOptionPane.showMessageDialog(jComboBox4, "Select a Customer", "Error", JOptionPane.ERROR_MESSAGE);
            jComboBox4.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            jLabel6.setText("Select a Customer");
            return false;
        }     
                        
        
        
        return true;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        setLocation(new java.awt.Point(400, 120));
        setMinimumSize(new java.awt.Dimension(780, 500));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CREATE NEW FABRIC PACKING LIST");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 430, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Date");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 30, 30));

        jTextField1.setEditable(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 260, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText(" Packing List Number");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, 30));

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 260, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Customer");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 60, 30));

        jComboBox4.setMinimumSize(new java.awt.Dimension(6, 20));
        jComboBox4.setPreferredSize(new java.awt.Dimension(6, 20));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 260, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("GRN Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 90, 30));

        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4FocusGained(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 260, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Batch No");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 70, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Style");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 50, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Color");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 50, 30));

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 90, 30));

        jTextField9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField9FocusGained(evt);
            }
        });
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField9KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, 60, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Rolls");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 50, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Material");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 50, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Length/Roll (m)");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, 100, 30));

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 330, 110, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Employee");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 80, 30));

        jButton1.setBackground(new java.awt.Color(53, 231, 74));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Finish");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 420, 110, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Total Length (m)");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 130, 30));

        jTextField12.setEditable(false);
        getContentPane().add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 140, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Total Length (Yards)");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 130, 30));

        jTextField13.setEditable(false);
        getContentPane().add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 140, 30));

        jButton2.setBackground(new java.awt.Color(53, 148, 240));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Calculate");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, 100, -1));

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 460, 10));

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 760, 10));

        jSeparator5.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 20, 120));

        jComboBox3.setMaximumSize(new java.awt.Dimension(100, 30));
        jComboBox3.setMinimumSize(new java.awt.Dimension(100, 30));
        jComboBox3.setPreferredSize(new java.awt.Dimension(100, 30));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 120, 30));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silk", "Cotton", "Polyesterine", "Matte" }));
        jComboBox5.setSelectedIndex(-1);
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 110, 30));

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
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 30, 30));

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
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 30, 30));

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 760, 10));

        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, 100, 30));

        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 100, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 300, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
       char ch = evt.getKeyChar();
       if( !(Character.isDigit(ch) || ch == 'P' || ch == KeyEvent.VK_BACKSPACE  || ch == KeyEvent.VK_DELETE)){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox4, "Enter P<Numeric> Format Only", "Error", JOptionPane.ERROR_MESSAGE);
           
       }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        char ch = evt.getKeyChar();
       if( !(Character.isDigit(ch) || ch == 'P' || ch == KeyEvent.VK_BACKSPACE  || ch == KeyEvent.VK_DELETE)){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox4, "Enter P<Numeric> Format Only", "Error", JOptionPane.ERROR_MESSAGE);
       }  
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyTyped
        char ch = evt.getKeyChar();
       if( !(Character.isDigit(ch) || ch == KeyEvent.VK_BACKSPACE  || ch == KeyEvent.VK_DELETE)){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox4, "Enter Only Whole Numeric Values", "Error", JOptionPane.ERROR_MESSAGE);
       }  
    }//GEN-LAST:event_jTextField9KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        char ch = evt.getKeyChar();
       if( !(Character.isDigit(ch) || ch == KeyEvent.VK_BACKSPACE || ch == '.' || ch ==KeyEvent.VK_DELETE)){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox4, "Enter Only Whole and Decimal Numeric Values", "Error", JOptionPane.ERROR_MESSAGE);
       }  
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        
        try {
            if(formValidate() == true){            
                String date = jTextField1.getText();
                String pln = jTextField2.getText();
                String grn = jTextField4.getText();
                String bn = jTextField6.getText();
                int roll = Integer.parseInt(jTextField9.getText());
                double lpr = Double.parseDouble(jTextField5.getText());
                double tly = Double.parseDouble(jTextField13.getText());
                double tlm = Double.parseDouble(jTextField12.getText());
                
                String cus = String.valueOf(jComboBox4.getSelectedItem());
                String style = String.valueOf(jComboBox1.getSelectedItem());
                String color = String.valueOf(jComboBox3.getSelectedItem());
                String mat = String.valueOf(jComboBox5.getSelectedItem());
                String emp = String.valueOf(jComboBox2.getSelectedItem());
                
                
                
                
                try {
                    
                    
                    if( edit == false){
                        dbCon.con.prepareStatement("INSERT INTO splfabric(batchNo,pktDate,customer,pktListNo,GRN,empNo,style,color,material,rolls,lengthPerRoll,totLenYards,totLenMeters) VALUES('"+bn+"','"+date+"','"+cus+"','"+pln+"','"+grn+"','"+emp+"','"+style+"','"+color+"','"+mat+"',"+roll+","+lpr+","+tly+","+tlm+")" ).execute();
                        JOptionPane.showMessageDialog (jComboBox4, "New Fabric Stores and Packing List Added Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                        
                        //arage awula insert table magula
                        
                        jTextField2.setText(null);
                        jTextField6.setText(null);
                        jTextField4.setText(null);
                        jTextField9.setText("0");
                        jTextField5.setText("0.0");
                        jTextField12.setText("0.0");
                        jTextField13.setText("0.0");
                        
                        jComboBox2.setSelectedIndex(-1);
                        jComboBox1.setSelectedIndex(-1);
                        jComboBox3.setSelectedIndex(-1);
                        jComboBox4.setSelectedIndex(-1);
                        jComboBox5.setSelectedIndex(-1);
                    }
                    
                    else{
                        
                        dbCon.con.prepareStatement("UPDATE splfabric SET pktDate='"+date+"',customer='"+cus+"',pktListNo='"+pln+"',GRN='"+grn+"',empNo='"+emp+"',style='"+style+"',color='"+color+"',material='"+mat+"',rolls="+roll+",lengthPerRoll="+lpr+",totLenMeters="+tlm+",totLenYards="+tly+"WHERE batchNo='"+bn+"'" ).execute();
                        dbCon.con.prepareStatement("UPDATE listaqlandfb SET Color='"+color+"',Customer='"+cus+"',Style_No='"+style+"' WHERE BLP_No='"+bn+"'").execute();
                        JOptionPane.showMessageDialog (jComboBox4, "Fabric Stores and Packing List Updated Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                        
                        
                    }
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }} catch (SQLException ex) {
            Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
       double x = Double.parseDouble(jTextField9.getText());
       double y = Double.parseDouble(jTextField5.getText());
       
       double m = (x*y);
       double yar = (m*1.1);
       
       jTextField12.setText(Double.toString(m));
       jTextField13.setText(Double.toString(yar));
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        char ch = evt.getKeyChar();
       if( !( Character.isDigit(ch) || ch == 'B' || ch == KeyEvent.VK_BACKSPACE  || ch == KeyEvent.VK_DELETE )){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox4, "Enter B<Numeric> Format Only", "Error", JOptionPane.ERROR_MESSAGE);
       }  
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
      
        
        try {
            String data2 = String.valueOf(jComboBox4.getSelectedItem());
            jComboBox1.removeAllItems();
            jComboBox3.removeAllItems();
            
            
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT DISTINCT styleNo FROM style WHERE customer='"+data2+"' AND type='Fabrics'");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("styleNo"));
            }
            jComboBox1.setSelectedIndex(-1);
            jComboBox3.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        
        try {
            String data = String.valueOf(jComboBox1.getSelectedItem());
            String data2 = String.valueOf(jComboBox4.getSelectedItem());
           
            jComboBox3.removeAllItems();
            
            
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT DISTINCT colorName FROM color WHERE customer='"+data2+"' AND style='"+data+"'");
            while(rs.next()){
                jComboBox3.addItem(rs.getString("colorName"));
            }
            
            jComboBox3.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(NewFabricPL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
        jTextField6.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        jLabel6.setText(null);
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        jTextField2.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        jLabel6.setText(null);
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
        jTextField4.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        jLabel6.setText(null);
    }//GEN-LAST:event_jTextField4FocusGained

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        
        jComboBox4.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel6.setText(null); 
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jComboBox1.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel6.setText(null); 
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        jComboBox3.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel6.setText(null); 
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        jComboBox5.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel6.setText(null); 
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        jComboBox2.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel6.setText(null); 
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        jTextField9.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        jLabel6.setText(null);
    }//GEN-LAST:event_jTextField9FocusGained

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
        jTextField5.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        jLabel6.setText(null);
    }//GEN-LAST:event_jTextField5FocusGained

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
            java.util.logging.Logger.getLogger(NewFabricPL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewFabricPL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewFabricPL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewFabricPL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewFabricPL().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
