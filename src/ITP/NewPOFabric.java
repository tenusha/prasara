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
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Ranmal
 */
public class NewPOFabric extends javax.swing.JFrame {

    public static boolean edit;
    
    /**
     * Creates new form NewPOFabric
     */
    
    public NewPOFabric(boolean edt,String pk) {
        
            initComponents();
            AutoCompleteDecorator.decorate(jComboBox1);
            AutoCompleteDecorator.decorate(jComboBox2);
            AutoCompleteDecorator.decorate(jComboBox4);
            AutoCompleteDecorator.decorate(jComboBox3);
            AutoCompleteDecorator.decorate(jComboBox6);
            AutoCompleteDecorator.decorate(jComboBox5);
            AutoCompleteDecorator.decorate(jComboBox7);
            
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            jTextField1.setText(dt.format(Home.fillDate));
            
            edit=edt;
            jLabel1.setText("EDIT FABRIC PURCHASE ORDER");
            jButton2.setText("Update PO");
            System.out.println(edit+" "+pk);
            
            dbCon d = new dbCon();
            d.connect();
            
        try {    
            ResultSet rs1 = dbCon.con.createStatement().executeQuery("SELECT * FROM splfabric WHERE batchNo ='"+pk+"'");
            while(rs1.next()){
                jComboBox4.addItem(rs1.getString("customer"));
                jComboBox1.addItem(rs1.getString("batchNo"));
                jComboBox2.addItem(rs1.getString("style"));
                jComboBox3.addItem(rs1.getString("color"));
                jComboBox5.addItem(rs1.getString("pktListNo"));
                jComboBox7.addItem(rs1.getString("material"));
                jComboBox6.addItem(rs1.getString("totLenMeters"));
            }
                    
            ResultSet rs2 = dbCon.con.createStatement().executeQuery("SELECT * FROM pofabric WHERE batchNo ='"+pk+"'");
            while(rs2.next()){
                jTextField1.setText(rs2.getString("poDate"));
                jTextField4.setText(rs2.getString("poNo"));
                jTextField11.setText(rs2.getString("unitCost"));
                jTextField12.setText(rs2.getString("totCost"));
                
                /*string to date conversion*/
                String s = rs2.getString("expDate");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date ed;
                ed = df.parse(s);
                Date.setDate(ed);
                
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public NewPOFabric() {
        
            initComponents();
            AutoCompleteDecorator.decorate(jComboBox1);
            AutoCompleteDecorator.decorate(jComboBox2);
            AutoCompleteDecorator.decorate(jComboBox4);
            AutoCompleteDecorator.decorate(jComboBox3);
            AutoCompleteDecorator.decorate(jComboBox6);
            AutoCompleteDecorator.decorate(jComboBox5);
            AutoCompleteDecorator.decorate(jComboBox7);
            
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            jTextField1.setText(dt.format(Home.fillDate));
            
            edit=false;
            System.out.println(edit);
  
            
            dbCon d = new dbCon();
            d.connect();
            
        try {   
            ResultSet rs1 = dbCon.con.createStatement().executeQuery("SELECT DISTINCT pktListNo FROM splfabric WHERE PO_Status=0");
            while(rs1.next()){
                jComboBox5.addItem(rs1.getString("pktListNo"));
            }
            
            ResultSet rs2 = dbCon.con.createStatement().executeQuery("SELECT MAX(poNo) as po FROM pofabric");
            if(rs2.next()){
               if(rs2.getString("po") != null){ 
                int i =Integer.parseInt(rs2.getString("po"));
                int l = i+1;
                String s = Integer.toString(l);
                jTextField4.setText(s);
               }
              else{
                jTextField4.setText("1"); 
                } 
            }
            
            
            
            
            //filter option from other DBs
            
            jComboBox5.setSelectedIndex(-1);
            jComboBox6.setSelectedIndex(-1);
            jComboBox7.setSelectedIndex(-1);
            jComboBox4.setSelectedIndex(-1);
            jComboBox3.setSelectedIndex(-1);
            jComboBox2.setSelectedIndex(-1);
            jComboBox1.setSelectedIndex(-1);
            
            
            jTextField11.setText("0.0");
            jTextField12.setText("0.0");
            
            
            
            ;
        } catch (SQLException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
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

        jSeparator2 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox7 = new javax.swing.JComboBox<>();
        Date = new org.jdesktop.swingx.JXDatePicker();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(400, 120));
        setMinimumSize(new java.awt.Dimension(780, 500));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 760, 10));

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("ADD FABRIC PURCHASE ORDER");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 430, 40));

        jTextField1.setEditable(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 260, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("  Date");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 40, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Expiry Date");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 70, 30));

        jTextField4.setEditable(false);
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 260, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("   Purchase Order No");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 130, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText(" Packing List Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 130, 30));

        jComboBox5.setMinimumSize(new java.awt.Dimension(6, 20));
        jComboBox5.setPreferredSize(new java.awt.Dimension(6, 20));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 260, 30));

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
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 260, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText(" Customer");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 70, 30));

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 760, 10));

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
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 100, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Batch No");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 70, 30));

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 100, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Style");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 50, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Total Length (m)");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, 110, 30));

        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField11KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 120, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Unit Price/m");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 330, 90, 30));

        jTextField12.setEditable(false);
        getContentPane().add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 440, 120, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText(" Total Cost  :-");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, -1, 30));

        jComboBox3.setMaximumSize(new java.awt.Dimension(100, 30));
        jComboBox3.setMinimumSize(new java.awt.Dimension(100, 30));
        jComboBox3.setPreferredSize(new java.awt.Dimension(100, 30));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 120, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Color");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 50, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Material");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 50, 30));

        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 360, 110, 30));

        jButton3.setBackground(new java.awt.Color(53, 148, 240));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Calculate");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 440, 120, 30));

        jButton2.setBackground(new java.awt.Color(53, 231, 74));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Add PO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 220, 30));

        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, 110, 30));

        Date.setFormats("yyyy-MM-dd");
        Date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateActionPerformed(evt);
            }
        });
        getContentPane().add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 260, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 300, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if(formValidate() == true) {
        
        String pd = jTextField1.getText();
        
        /*date to string conversion*/
        Date ed = Date.getDate();
        Format df = new SimpleDateFormat("yyyy-MM-dd");
        String sd =df.format(ed);
        
        
        int pn = Integer.parseInt(jTextField4.getText());
        double uc = Double.parseDouble(jTextField11.getText());
        double tc = Double.parseDouble(jTextField12.getText());
        String bn = String.valueOf(jComboBox1.getSelectedItem());
        
        
        
        
      try { 
        if(edit==false){
            
                dbCon.con.prepareStatement("INSERT INTO pofabric VALUES("+pn+",'"+pd+"','"+sd+"',"+uc+","+tc+",'"+bn+"')").execute();
                dbCon.con.prepareStatement("UPDATE splfabric SET PO_Status = 1 WHERE batchNo = '"+bn+"'").execute();
                JOptionPane.showMessageDialog (jComboBox5, "New Fabric Purchase Order Added Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                
                ResultSet rs1 = dbCon.con.createStatement().executeQuery("SELECT DISTINCT pktListNo FROM splfabric WHERE PO_Status=0");
                jComboBox5.removeAllItems();
                while(rs1.next()){
                jComboBox5.addItem(rs1.getString("pktListNo"));
                }
            
                ResultSet rs2 = dbCon.con.createStatement().executeQuery("SELECT MAX(poNo) as po FROM pofabric");
                if(rs2.next()){
                    if(rs2.getString("po") != null){ 
                        int i =Integer.parseInt(rs2.getString("po"));
                        int l = i+1;
                        String s = Integer.toString(l);
                        jTextField4.setText(s);
                        }
                    else{
                       jTextField4.setText("1"); 
                    } 
                }
                
                
                Date.setDate(null);
                jComboBox2.setSelectedIndex(-1);
                jComboBox1.setSelectedIndex(-1);
                jComboBox3.setSelectedIndex(-1);
                jComboBox4.setSelectedIndex(-1);
                jComboBox5.setSelectedIndex(-1);
                jComboBox6.setSelectedIndex(-1);
                jComboBox7.setSelectedIndex(-1);
                
                jTextField11.setText("0.0");
                jTextField12.setText("0.0");
                
            }
        
        else{
            
            dbCon.con.prepareStatement("UPDATE pofabric SET expDate='"+sd+"',unitCost="+uc+",totCost="+tc+" WHERE poNo="+pn+"").execute();
                JOptionPane.showMessageDialog (jComboBox5, "Fabric Purchase Order Updated Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        
            
        }catch (SQLException ex) {
                Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
      
        
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
       if(edit==false){
        try {
            String data = String.valueOf(jComboBox5.getSelectedItem());
            String data2 = String.valueOf(jComboBox4.getSelectedItem());
            jComboBox1.removeAllItems();
            jComboBox2.removeAllItems();
            jComboBox3.removeAllItems();
            jComboBox6.removeAllItems();
            jComboBox7.removeAllItems();
            
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT DISTINCT batchNo FROM splfabric WHERE pktListNo='"+data+"' AND customer='"+data2+"' AND PO_Status=0");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("batchNo"));
            }
            jComboBox1.setSelectedIndex(-1);
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setSelectedIndex(-1);
            jComboBox7.setSelectedIndex(-1);
            jComboBox6.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
        }
       }  
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
       if(edit==false){
        try {
            String data = String.valueOf(jComboBox5.getSelectedItem());
            jComboBox4.removeAllItems();
            jComboBox1.removeAllItems();
            jComboBox2.removeAllItems();
            jComboBox3.removeAllItems();
            jComboBox6.removeAllItems();
            jComboBox7.removeAllItems();
            
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT DISTINCT customer FROM splfabric WHERE pktListNo='"+data+"' AND PO_Status=0");
            while(rs.next()){
                jComboBox4.addItem(rs.getString("customer"));
            }
            
            jComboBox4.setSelectedIndex(-1);
            jComboBox1.setSelectedIndex(-1);
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setSelectedIndex(-1);
            jComboBox7.setSelectedIndex(-1);
            jComboBox6.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
        }
       }  
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
      if(edit==false){ 
        try {
            String data = String.valueOf(jComboBox5.getSelectedItem());
            String data2 = String.valueOf(jComboBox4.getSelectedItem());
            String data3 = String.valueOf(jComboBox1.getSelectedItem());
            jComboBox2.removeAllItems();
            jComboBox3.removeAllItems();
            jComboBox7.removeAllItems();
            jComboBox6.removeAllItems();
            ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT DISTINCT style,color,material,totLenMeters FROM splfabric WHERE pktListNo='"+data+"' AND customer='"+data2+"' AND batchNo='"+data3+"'");
            while(rs.next()){
                jComboBox2.addItem(rs.getString("style"));
                jComboBox3.addItem(rs.getString("color"));
                jComboBox7.addItem(rs.getString("material"));
                jComboBox6.addItem(rs.getString("totLenMeters"));
            }
            
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setSelectedIndex(-1);
            jComboBox7.setSelectedIndex(-1);
            jComboBox6.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(NewPOFabric.class.getName()).log(Level.SEVERE, null, ex);
        } 
      }  
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
       
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
       
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jTextField11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyTyped
        char ch = evt.getKeyChar();
       if( !(Character.isDigit(ch) || ch == KeyEvent.VK_BACKSPACE || ch == '.' || ch ==KeyEvent.VK_DELETE)){
           evt.consume();
           JOptionPane.showMessageDialog(jComboBox5, "Enter Only Whole and Decimal Numeric Values", "Error", JOptionPane.ERROR_MESSAGE);
       }  
    }//GEN-LAST:event_jTextField11KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
       if( jComboBox6.getSelectedIndex() ==  -1 ){
        JOptionPane.showMessageDialog(jComboBox5, "Select Total Length", "Error", JOptionPane.ERROR_MESSAGE);   
       }
       else{
       double x = Double.parseDouble(jTextField11.getText());
       double y = Double.parseDouble(String.valueOf(jComboBox6.getSelectedItem()));
       
       double cost = (x*y);
       
       jTextField12.setText(Double.toString(cost));
       
      }
      
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void DateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DateKeyTyped
        
    }//GEN-LAST:event_DateKeyTyped

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jComboBox1.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        jComboBox5.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        jComboBox4.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        jComboBox2.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        jComboBox3.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        jComboBox7.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        jComboBox6.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void DateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateActionPerformed
        Date.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        jLabel10.setText(null);
    }//GEN-LAST:event_DateActionPerformed
        
    methods m = new methods();
    public boolean formValidate() {
        
   
            if(m.validateDateBackward(new SimpleDateFormat("dd-MM-yyyy").format(Date.getDate()), jLabel10)){
                if((jComboBox5.getSelectedIndex())!= -1){
                    if((jComboBox4.getSelectedIndex())!= -1){
                        if((jComboBox1.getSelectedIndex())!= -1){
                            if((jComboBox2.getSelectedIndex())!= -1){
                                if((jComboBox3.getSelectedIndex())!= -1){
                                    if((jComboBox7.getSelectedIndex())!= -1){
                                        if((jComboBox6.getSelectedIndex())!= -1){
                                                return true;
                                    }else{
                                        //JOptionPane.showMessageDialog(jComboBox5, "Select Total Length", "Error", JOptionPane.ERROR_MESSAGE);
                                        jComboBox6.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                        jLabel10.setText("Select Total Length");
                                        return false;
                                        }
                                }else{
                                    //JOptionPane.showMessageDialog(jComboBox5, "Select a Material", "Error", JOptionPane.ERROR_MESSAGE);
                                    jComboBox7.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                    jLabel10.setText("Select a Material");
                                    return false;
                                    }   
                            }else{
                                //JOptionPane.showMessageDialog(jComboBox5, "Select a Color", "Error", JOptionPane.ERROR_MESSAGE);
                                jComboBox3.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                jLabel10.setText("Select a Color");
                                return false;
                                }
                        }else{
                            //JOptionPane.showMessageDialog(jComboBox5, "Select a Style", "Error", JOptionPane.ERROR_MESSAGE);
                            jComboBox2.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            jLabel10.setText("Select a Style");
                            return false;
                            }
                    }else{
                        //JOptionPane.showMessageDialog(jComboBox5, "Select a BatchNo", "Error", JOptionPane.ERROR_MESSAGE);
                        jComboBox1.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        jLabel10.setText("Select a Batch Number");
                        return false;
                        }
                }else{
                      //JOptionPane.showMessageDialog(jComboBox5, "Select a Customer", "Error", JOptionPane.ERROR_MESSAGE);
                      jComboBox4.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                      jLabel10.setText("Select a Customer");
                      return false;
                      }
            }else{
                //JOptionPane.showMessageDialog(jComboBox5, "Select a Packing List Number", "Error", JOptionPane.ERROR_MESSAGE);
                jComboBox5.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                jLabel10.setText("Select a Packing List Number");
                return false;
                }
        }else{
                //JOptionPane.showMessageDialog(jComboBox5, "Select a Expiry Date", "Error", JOptionPane.ERROR_MESSAGE);
                Date.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                //jLabel10.setText("Select a Expiry Date");
                return false;
                }
         
    }
    
    
    
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
            java.util.logging.Logger.getLogger(NewPOFabric.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewPOFabric.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewPOFabric.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewPOFabric.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewPOFabric().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker Date;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
