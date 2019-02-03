package ITP;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vimukthi
 */
public class listCustomers extends javax.swing.JPanel {

    /**
     * Creates new form panel2
     */
    methods m = new methods();

    public listCustomers() {
        initComponents();
        m.dbConnection();
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
        edit.setVisible(false);
        delete.setVisible(false);
        if (Home.user_level != 5) {
            delete.setVisible(false);
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

        jLabel1 = new javax.swing.JLabel();
        edit = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        Print = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        previous = new javax.swing.JButton();
        first = new javax.swing.JButton();
        next = new javax.swing.JButton();
        last = new javax.swing.JButton();
        pageNo = new javax.swing.JLabel();
        slash = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        addSupplier = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(940, 480));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Customers");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        edit.setBackground(new java.awt.Color(0, 0, 204));
        edit.setForeground(new java.awt.Color(255, 255, 255));
        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/edit.png"))); // NOI18N
        edit.setText("Edit");
        edit.setPreferredSize(new java.awt.Dimension(71, 25));
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 415, -1, -1));

        delete.setBackground(new java.awt.Color(204, 0, 0));
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/delete.png"))); // NOI18N
        delete.setText("Delete");
        delete.setPreferredSize(new java.awt.Dimension(71, 25));
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 415, 90, -1));

        Print.setBackground(new java.awt.Color(71, 71, 116));
        Print.setForeground(new java.awt.Color(255, 255, 255));
        Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        Print.setText("Print");
        Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintActionPerformed(evt);
            }
        });
        add(Print, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 80, -1));

        refresh.setBackground(new java.awt.Color(25, 176, 9));
        refresh.setForeground(new java.awt.Color(255, 255, 255));
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/refresh.png"))); // NOI18N
        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 90, -1));

        back.setBackground(new java.awt.Color(219, 76, 13));
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        back.setText("Back");
        back.setToolTipText("");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 80, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("entries");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 70, 40));

        jTextField1.setText("1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 40, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "15", "20" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 50, 20));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("show");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 70, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
            },
            new String [] {
                "Customer ID", "Name", "Email", "Phone", "Address", "City", "Bill address", "State", "Postal Code", "VAT", "SVAT"
            }
        ));
        jTable1.setRowHeight(35);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 890, 220));

        previous.setBackground(new java.awt.Color(71, 71, 116));
        previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/previous.png"))); // NOI18N
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });
        add(previous, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 410, 40, 30));

        first.setBackground(new java.awt.Color(71, 71, 116));
        first.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/first.png"))); // NOI18N
        first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstActionPerformed(evt);
            }
        });
        add(first, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 410, 40, 30));

        next.setBackground(new java.awt.Color(71, 71, 116));
        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/next.png"))); // NOI18N
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        add(next, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 410, 40, 30));

        last.setBackground(new java.awt.Color(71, 71, 116));
        last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/images/last.png"))); // NOI18N
        last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastActionPerformed(evt);
            }
        });
        add(last, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 410, 40, 30));

        pageNo.setForeground(new java.awt.Color(255, 255, 255));
        pageNo.setText("4");
        add(pageNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 40, 30));

        slash.setForeground(new java.awt.Color(255, 255, 255));
        slash.setText("/");
        add(slash, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 10, 30));

        search.setText("Search");
        search.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                m.search(search.getText(), "select * from customer where name like '%"+search.getText()+"%' or email like '%"+search.getText()+"%' or custID like '%"+search.getText()+"%' or phone like '%"+search.getText()+"%' or address like '%"+search.getText()+"%' or city like '%"+search.getText()+"%' or ba like '%"+search.getText()+"%' or ba1 like '%"+search.getText()+"%' or ba2 like '%"+search.getText()+"%' or ba3 like '%"+search.getText()+"%' or state like '%"+search.getText()+"%' or pc like '%"+search.getText()+"%' or vat like '%"+search.getText()+"%' or svat like '%"+search.getText()+"%'", jTable1);
            }
            public void removeUpdate(DocumentEvent e){
                m.search(search.getText(), "select * from customer where name like '%"+search.getText()+"%' or email like '%"+search.getText()+"%' or custID like '%"+search.getText()+"%' or phone like '%"+search.getText()+"%' or address like '%"+search.getText()+"%' or city like '%"+search.getText()+"%' or ba like '%"+search.getText()+"%' or ba1 like '%"+search.getText()+"%' or ba2 like '%"+search.getText()+"%' or ba3 like '%"+search.getText()+"%' or state like '%"+search.getText()+"%' or pc like '%"+search.getText()+"%' or vat like '%"+search.getText()+"%' or svat like '%"+search.getText()+"%'", jTable1);
            }
            public void insertUpdate(DocumentEvent e){
                m.search(search.getText(), "select * from customer where name like '%"+search.getText()+"%' or email like '%"+search.getText()+"%' or custID like '%"+search.getText()+"%' or phone like '%"+search.getText()+"%' or address like '%"+search.getText()+"%' or city like '%"+search.getText()+"%' or ba like '%"+search.getText()+"%' or ba1 like '%"+search.getText()+"%' or ba2 like '%"+search.getText()+"%' or ba3 like '%"+search.getText()+"%' or state like '%"+search.getText()+"%' or pc like '%"+search.getText()+"%' or vat like '%"+search.getText()+"%' or svat like '%"+search.getText()+"%'", jTable1);
            }
        });
        add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, 30));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 880, 30));

        addSupplier.setBackground(new java.awt.Color(10, 24, 224));
        addSupplier.setForeground(new java.awt.Color(255, 255, 255));
        addSupplier.setText("+ Add Customer");
        addSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSupplierActionPerformed(evt);
            }
        });
        add(addSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 120, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));
    }// </editor-fold>//GEN-END:initComponents

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        //16166752addCustomer a = new addCustomer((String)jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        //16166752a.setVisible(true);
        //System.out.println(rowNo);

        addCustomer a = new addCustomer((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        a.setVisible(true);
    }//GEN-LAST:event_editActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        String s = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        if (s != null && !s.trim().equals("")) {
            edit.setVisible(true);
            delete.setVisible(true);
            if (Home.user_level != 5) {
                delete.setVisible(false);
            }
        } else {
            edit.setVisible(false);
            delete.setVisible(false);
        }


    }//GEN-LAST:event_jTable1MouseClicked

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        Home.lc.setVisible(false);
        Home.peo.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    private void addSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSupplierActionPerformed
        new addCustomer().setVisible(true);
    }//GEN-LAST:event_addSupplierActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("1");
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_firstActionPerformed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        // TODO add your handling code here:
        int no = Integer.parseInt(jTextField1.getText());
        if (no > 1) {
            no--;
        }
        jTextField1.setText(Integer.toString(no));
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_previousActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
        int no = Integer.parseInt(jTextField1.getText());
        int no1 = Integer.parseInt(pageNo.getText());
        if (no < no1) {
            no++;
        }
        jTextField1.setText(Integer.toString(no));
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_nextActionPerformed

    private void lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastActionPerformed
        // TODO add your handling code here:
        int no1 = Integer.parseInt(pageNo.getText());
        jTextField1.setText(Integer.toString(no1));
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_lastActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        edit.setVisible(false);
        delete.setVisible(false);
    }//GEN-LAST:event_formMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        String s = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        String name = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 1);
        int result = JOptionPane.showConfirmDialog(null, "Do you really want to delete " + name + "?", "Warning !", 0);
        if (result == JOptionPane.YES_OPTION) {
            try {
                int n = m.deleterow(s, "customer", "custID");
                if (n == 1) {
                    JOptionPane.showMessageDialog(null, name + " is successfully deleted.", "", JOptionPane.INFORMATION_MESSAGE);
                    m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
                } else {
                    JOptionPane.showMessageDialog(null, " Sorry! Something went wrong. ", "", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(listEmployees.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
    }//GEN-LAST:event_refreshActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        int labelNumber = Integer.parseInt(pageNo.getText());
        int pageNumber = Integer.parseInt(jTextField1.getText());
        if (labelNumber <= pageNumber) {
            m.dynamicTable(jTextField1, jComboBox1, pageNo, jTable1, "customer", "custID, name, email, phone, address, city, ba, state, pc, vat, svat");
        } else {
            jTextField1.setText(pageNo.getText());
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintActionPerformed
        // TODO add your handling code here:
        Home.Report(jTable1, "Customer List", "E:\\Reports\\customerList.pdf");
    }//GEN-LAST:event_PrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Print;
    private javax.swing.JButton addSupplier;
    private javax.swing.JButton back;
    private javax.swing.JButton delete;
    private javax.swing.JButton edit;
    private javax.swing.JButton first;
    protected javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    protected javax.swing.JTable jTable1;
    protected javax.swing.JTextField jTextField1;
    private javax.swing.JButton last;
    private javax.swing.JButton next;
    protected javax.swing.JLabel pageNo;
    private javax.swing.JButton previous;
    private javax.swing.JButton refresh;
    private javax.swing.JTextField search;
    private javax.swing.JLabel slash;
    // End of variables declaration//GEN-END:variables
}