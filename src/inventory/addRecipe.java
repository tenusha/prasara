/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import ITP.Home;
import com.mysql.jdbc.Connection;
import static inventory.dbCon.con;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Tenusha
 */
public class addRecipe extends javax.swing.JFrame {

    String st = "";
    String stl = "";
    String cu = "";

    /**
     * Creates new form addRecipe
     */
    public addRecipe() {
        initComponents();
        setDate();

        AutoCompleteDecorator.decorate(customer);
        AutoCompleteDecorator.decorate(style);
        AutoCompleteDecorator.decorate(color);
        AutoCompleteDecorator.decorate(chemical);

        dbCon db = new dbCon();
        db.connect();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT customer FROM style");
            while (rs.next()) {
                customer.addItem(rs.getString("customer"));
            }

            customer.setSelectedIndex(-1);
            color.setSelectedIndex(-1);
            style.setSelectedIndex(-1);
            chemical.setSelectedIndex(-1);

            chemical.setVisible(false);
            che.setVisible(false);
            ml.setVisible(false);
            qty.setVisible(false);
            quantity.setVisible(false);
            ml.setVisible(false);

        } catch (SQLException ex) {
            Logger.getLogger(addStyle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public addRecipe(String s, String sty, String cus) {
        initComponents();
        stl = sty;
        cu = cus;
        st = s;

        AutoCompleteDecorator.decorate(customer);
        AutoCompleteDecorator.decorate(style);
        AutoCompleteDecorator.decorate(color);

        dbCon db = new dbCon();
        db.connect();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT customer FROM style");
            while (rs.next()) {
                customer.addItem(rs.getString("customer"));
            }
            ResultSet rs1 = con.createStatement().executeQuery("SELECT styleNo FROM recipe");
            while (rs1.next()) {
                style.addItem(rs1.getString("styleNo"));
            }

            jLabel1.setText("Edit Recipe");
            submit.setText("Edit Recipe");

            editText();
        } catch (SQLException ex) {
            Logger.getLogger(addStyle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d = new Date();
        date.setText(df.format(d));
        date.setEditable(false);
    }

    boolean checkDouble(String no) {
        try {
            double d = Double.parseDouble(no);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    boolean checkInt(String no) {
        try {
            Integer d = Integer.parseInt(no);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    void editText() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM recipe WHERE styleNo='" + stl + "' AND customer = '" + cu + "'");
        rs.next();
        customer.setSelectedItem(rs.getString("customer"));
        date.setText(rs.getString("date"));
        style.setSelectedItem(rs.getString("styleNo"));
        updateColor();

        color.setSelectedItem(rs.getString("color"));
        rTime.setText(rs.getString("rTime"));
        wSide.setText(rs.getString("washSide"));
        notes.setText(rs.getString("notes"));
        int bq = rs.getInt("bwqc");
        if (bq == 1) {
            bwqc.setSelected(true);
        }
        int chem = rs.getInt("Chemicals");
        ResultSet rs3 = con.createStatement().executeQuery("SELECT chemical FROM chemical");
        while (rs3.next()) {
            chemical.addItem(rs3.getString("chemical"));
        }
        chemical.setSelectedIndex(-1);
        chemical.setVisible(false);
        che.setVisible(false);
        ml.setVisible(false);
        qty.setVisible(false);
        quantity.setVisible(false);
        ml.setVisible(false);

        if (chem == 1) {
            chemicals.setSelected(true);
            chemical.setSelectedItem(rs.getString("chemical"));
            quantity.setText(rs.getString("chemicalQty"));
            chemical.setVisible(true);
            chemical.setEnabled(false);
            che.setVisible(true);
            ml.setVisible(true);
            qty.setVisible(true);
            quantity.setVisible(true);
            quantity.setEditable(false);
            ml.setVisible(true);

            chemicals.setEnabled(false);

        }
        int w1 = rs.getInt("w1");
        if (w1 == 1) {
            washing1.setSelected(true);
        }
        int h1 = rs.getInt("h1");
        if (h1 == 1) {
            hydro1.setSelected(true);
        }
        int wqc = rs.getInt("wetqc");
        if (wqc == 1) {
            wetqc.setSelected(true);
        }
        int d1 = rs.getInt("d1");
        if (d1 == 1) {
            drying1.setSelected(true);
        }
        int aq = rs.getInt("awqc");
        if (aq == 1) {
            awqc.setSelected(true);
        }

        date.setEditable(false);
        customer.setEnabled(false);
        style.setEnabled(false);

    }

    boolean formValidate() throws SQLException {
        if ((customer.getSelectedIndex()) != -1) {
            if ((style.getSelectedIndex()) != -1) {
                if (checkInt(rTime.getText())) {
                    if (chemicals.isSelected()) {
                        if (chemical.getSelectedIndex() == -1) {
                            chemical.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            chemicall.setText("Select a chemical");
                            return false;
                        } else {
                            if (quantity.getText().equals("")) {
                                quantity.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                quantityl.setText("please add chemical quantity");
                                return false;
                            } else {
                                if (checkDouble(quantity.getText())) {
                                    ResultSet rs5 = con.createStatement().executeQuery("SELECT qty FROM chemical WHERE chemical='" + chemical.getSelectedItem() + "'");
                                    rs5.next();
                                    double q = Double.parseDouble(quantity.getText());
                                    if (q > rs5.getInt("qty")) {
                                        quantity.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                        quantityl.setText("not enough chemicals");
                                        return false;
                                    } else {
                                        //if chemical is selected
                                    }
                                } else {
                                    quantity.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                    quantityl.setText("Enter a double value to quantity");
                                    return false;
                                }
                            }
                        }
                    } else {
                        //if chemical is not selected
                    }
                } else {
                    rTime.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    rTimel.setText("invalid recipe time");
                    return false;
                }
            } else {
                if (st.equals("edit")) {

                } else {
                    style.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    stylel.setText("please select a style");
                    return false;
                }
            }
        } else {
            customer.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            customerl.setText("please select a customer");
            return false;
        }

        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM recipe WHERE styleNo='" + String.valueOf(style.getSelectedItem()) + "' AND customer = '" + String.valueOf(customer.getSelectedItem()) + "'");
        if (rs.next()) {
            if (st.equals("edit")) {
                return true;
            } else {
                style.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                stylel.setText("recipe exist");
                return false;
            }
        } else {
            return true;
        }
    }

    void updateColor() {
        ResultSet rs2;
        color.removeAllItems();
        try {
            rs2 = con.createStatement().executeQuery("SELECT color FROM color WHERE style = '" + String.valueOf(style.getSelectedItem()) + "' AND customer='" + String.valueOf(customer.getSelectedItem()) + "'");
            while (rs2.next()) {
                color.addItem(rs2.getString("color"));
            }
            color.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(addRecipe.class.getName()).log(Level.SEVERE, null, ex);
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
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        date = new javax.swing.JTextField();
        customer = new javax.swing.JComboBox<>();
        color = new javax.swing.JComboBox<>();
        style = new javax.swing.JComboBox<>();
        rTime = new javax.swing.JTextField();
        wSide = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        awqc = new javax.swing.JCheckBox();
        bwqc = new javax.swing.JCheckBox();
        chemicals = new javax.swing.JCheckBox();
        washing1 = new javax.swing.JCheckBox();
        hydro1 = new javax.swing.JCheckBox();
        wetqc = new javax.swing.JCheckBox();
        drying1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ml = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        quantity = new javax.swing.JTextField();
        chemical = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        che = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        quantityl = new javax.swing.JLabel();
        customerl = new javax.swing.JLabel();
        stylel = new javax.swing.JLabel();
        rTimel = new javax.swing.JLabel();
        chemicall = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(820, 480));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(820, 480));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(940, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 800, 13));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Add Recipe");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 372, -1));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 800, 13));
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 270, 30));

        customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerActionPerformed(evt);
            }
        });
        jPanel1.add(customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 270, 30));

        jPanel1.add(color, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 270, 30));

        style.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                styleActionPerformed(evt);
            }
        });
        jPanel1.add(style, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 270, 30));
        jPanel1.add(rTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 270, 30));
        jPanel1.add(wSide, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 270, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Select Departments");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 180, 30));

        awqc.setText("A/W QC");
        jPanel1.add(awqc, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 350, -1, -1));

        bwqc.setText("B/W QC");
        jPanel1.add(bwqc, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, -1, -1));

        chemicals.setText("Chemicals");
        chemicals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemicalsActionPerformed(evt);
            }
        });
        jPanel1.add(chemicals, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, -1));

        washing1.setText("Washing");
        jPanel1.add(washing1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));

        hydro1.setText("Hydro");
        jPanel1.add(hydro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, -1, -1));

        wetqc.setText("Wet QC");
        jPanel1.add(wetqc, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, -1, -1));

        drying1.setText("Drying");
        jPanel1.add(drying1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, -1, -1));

        notes.setColumns(20);
        notes.setRows(5);
        jScrollPane1.setViewportView(notes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 270, 60));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Mins");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 30, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Date");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 80, 30));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Customer");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 80, 30));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Style No");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 80, 30));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Style Color");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 80, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Recipe Time");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 80, 30));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Washing Side");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 80, 30));

        ml.setText("ml");
        jPanel1.add(ml, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 430, 20, 30));

        submit.setBackground(new java.awt.Color(0, 0, 255));
        submit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("Add Recipe");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 140, 40));

        quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityActionPerformed(evt);
            }
        });
        jPanel1.add(quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 430, 70, 30));

        jPanel1.add(chemical, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 130, 30));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("Notes");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 80, 30));

        che.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        che.setText("Chemical");
        jPanel1.add(che, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 80, 30));

        qty.setText("qty");
        jPanel1.add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 30, 30));

        minimize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Minimize.png"))); // NOI18N
        minimize.setAlignmentY(0.0F);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 30, -1));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventoryImages/Close.png"))); // NOI18N
        close.setAlignmentY(0.0F);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 30, -1));

        quantityl.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(quantityl, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 460, 200, 20));

        customerl.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(customerl, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 160, 30));

        stylel.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(stylel, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 160, 30));

        rTimel.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(rTimel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 130, 30));

        chemicall.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(chemicall, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 130, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        customer.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        customerl.setText("");
        style.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        stylel.setText("");
        chemical.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        chemicall.setText("");
        rTime.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        rTimel.setText("");
        quantity.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        quantityl.setText("");

        try {
            if (formValidate() == true) {
                String dt = date.getText();
                String cus = String.valueOf(customer.getSelectedItem());
                String sty = String.valueOf(style.getSelectedItem());
                String col = String.valueOf(color.getSelectedItem());
                String rt = rTime.getText();
                String chemicalNo = String.valueOf(chemical.getSelectedItem());

                double cheQty = 0.0;
                if (quantity.getText().equals("")) {

                } else {
                    cheQty = Double.parseDouble(quantity.getText());
                }
                String ws = wSide.getText();
                String nt = notes.getText();
                int bq = 0;
                if (bwqc.isSelected()) {
                    bq = 1;
                }
                int chem = 0;
                if (chemicals.isSelected()) {
                    chem = 1;
                }
                int w1 = 0;
                if (washing1.isSelected()) {
                    w1 = 1;
                }
                int h1 = 0;
                if (hydro1.isSelected()) {
                    h1 = 1;
                }
                int wqc = 0;
                if (wetqc.isSelected()) {
                    wqc = 1;
                }
                int d1 = 0;
                if (drying1.isSelected()) {
                    d1 = 1;
                }
                int aq = 0;
                if (awqc.isSelected()) {
                    aq = 1;
                }

                if (st.equals("edit")) {
                    con.prepareStatement("UPDATE recipe SET "
                            + "color = '" + col + "' ,rTime='" + rt + "'"
                            + ",washSide='" + ws + "' ,notes='" + nt + "' ,chemical ='" + chemicalNo + "',chemicalQty='" + cheQty + "',bwqc='" + bq + "' ,chemicals='" + chem + "' ,w1='" + w1 + "'"
                            + ",h1='" + h1 + "',wetqc='" + wqc + "' ,d1='" + d1 + "'"
                            + ", awqc='" + aq + "'"
                            + "WHERE styleNo='" + stl + "' AND customer = '" + cu + "'").execute();
                    JOptionPane.showMessageDialog(null, "Recipe Updated", "Done", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {
                    try {
                        con.prepareStatement("INSERT INTO recipe VALUES('" + cus + "','" + dt + "','" + sty + "','" + col + "','" + rt + "','" + ws + "','" + nt + "','" + chemicalNo + "','" + cheQty + "','" + bq + "','" + chem + "','" + w1 + "','" + h1 + "','" + wqc + "','" + d1 + "','" + aq + "')").execute();

                        con.prepareStatement("UPDATE chemical SET qty = qty - " + cheQty + " WHERE chemical ='" + chemicalNo + "'").execute();

                        JOptionPane.showMessageDialog(null, "New Recipe Added", "Done", JOptionPane.INFORMATION_MESSAGE);

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        Date d = new Date();
                        date.setText(df.format(d));
                        date.setEditable(false);
                        customer.setSelectedIndex(-1);
                        style.setSelectedIndex(-1);
                        color.setSelectedIndex(-1);
                        rTime.setText(null);
                        chemical.setSelectedIndex(-1);
                        quantity.setText("");
                        wSide.setText(null);
                        notes.setText(null);
                        bwqc.setSelected(false);
                        chemicals.setSelected(false);
                        washing1.setSelected(false);
                        hydro1.setSelected(false);
                        wetqc.setSelected(false);
                        drying1.setSelected(false);
                        awqc.setSelected(false);

                        chemical.setVisible(false);
                        che.setVisible(false);
                        ml.setVisible(false);
                        qty.setVisible(false);
                        quantity.setVisible(false);
                        ml.setVisible(false);

                        setDate();

                    } catch (SQLException ex) {
                        Logger.getLogger(addRecipe.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(addStyle.class.getName()).log(Level.SEVERE, null, ex);
        }
        Home.viewRecipe.refresh();
        Home.chemical.refresh();
    }//GEN-LAST:event_submitActionPerformed

    private void styleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_styleActionPerformed
        // TODO add your handling code here:
        updateColor();
    }//GEN-LAST:event_styleActionPerformed

    private void customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerActionPerformed
        ResultSet rs2;
        style.removeAllItems();
        try {
            String custc = String.valueOf(customer.getSelectedItem());
            rs2 = con.createStatement().executeQuery("SELECT DISTINCT styleNo FROM style WHERE customer = '" + custc + "' AND styleNo NOT IN (SELECT styleNo from recipe WHERE customer = '" + custc + "')");
            while (rs2.next()) {
                style.addItem(rs2.getString("styleNo"));
            }
            style.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(addRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_customerActionPerformed

    private void chemicalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemicalsActionPerformed
        // TODO add your handling code here:
        if (chemicals.isSelected()) {
            chemical.setVisible(true);
            che.setVisible(true);
            ml.setVisible(true);
            qty.setVisible(true);
            quantity.setVisible(true);
            ml.setVisible(true);

            ResultSet rs3;
            try {
                chemical.removeAllItems();
                rs3 = con.createStatement().executeQuery("SELECT chemical FROM chemical");
                while (rs3.next()) {
                    chemical.addItem(rs3.getString("chemical"));
                    chemical.setSelectedIndex(-1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(addRecipe.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            chemical.setVisible(false);
            che.setVisible(false);
            ml.setVisible(false);
            qty.setVisible(false);
            quantity.setVisible(false);
            ml.setVisible(false);
        }
    }//GEN-LAST:event_chemicalsActionPerformed

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeMouseClicked

    private void quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityActionPerformed

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
            java.util.logging.Logger.getLogger(addRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addRecipe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox awqc;
    private javax.swing.JCheckBox bwqc;
    private javax.swing.JLabel che;
    private javax.swing.JComboBox<String> chemical;
    private javax.swing.JLabel chemicall;
    private javax.swing.JCheckBox chemicals;
    private javax.swing.JLabel close;
    private javax.swing.JComboBox<String> color;
    private javax.swing.JComboBox<String> customer;
    private javax.swing.JLabel customerl;
    private javax.swing.JTextField date;
    private javax.swing.JCheckBox drying1;
    private javax.swing.JCheckBox hydro1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel minimize;
    private javax.swing.JLabel ml;
    private javax.swing.JTextArea notes;
    private javax.swing.JLabel qty;
    private javax.swing.JTextField quantity;
    private javax.swing.JLabel quantityl;
    private javax.swing.JTextField rTime;
    private javax.swing.JLabel rTimel;
    private javax.swing.JComboBox<String> style;
    private javax.swing.JLabel stylel;
    public javax.swing.JButton submit;
    private javax.swing.JTextField wSide;
    private javax.swing.JCheckBox washing1;
    private javax.swing.JCheckBox wetqc;
    // End of variables declaration//GEN-END:variables
}
