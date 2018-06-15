/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ITP;

import inventory.dbCon;
import static inventory.dbCon.con;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import production.production;

/**
 *
 * @author Axio
 */
public class attendance extends javax.swing.JPanel {

    int tot;
    int maxPages;
    int pg = 1;
    int lim = 15;
    int offset = 0;

    /**
     * Creates new form attendance
     */
    public attendance() throws SQLException {
        initComponents();
        int count1;
        int count2;
        MainMenu.setBackground(new Color(0, 0, 0, 0));

        MainMenu.setVisible(true);
        AttendanceList.setVisible(false);
        RequestLeave.setVisible(false);
        RequestList.setVisible(false);

        dbCon d = new dbCon();
        d.connect();

        ResultSet rs3 = con.createStatement().executeQuery("SELECT DISTINCT empNo FROM employee");
        while (rs3.next()) {
            id.addItem(rs3.getString("empNo"));
        }
        id.setSelectedIndex(-1);

        updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
        addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");

        updateMax("SELECT COUNT(*) AS count FROM request", total);
        addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");

        ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM attendance WHERE today =1");
        rs.next();
        count1 = rs.getInt("count");

        ResultSet rs5 = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM attendance WHERE today =0");
        rs5.next();
        count2 = rs5.getInt("count");

        ResultSet rs6 = con.createStatement().executeQuery("SELECT empNo FROM Employee");
        while (rs6.next()) {
            id.addItem(rs6.getString("empNo"));
        }

        jTextField3.setText(String.valueOf(count1));
        jTextField3.setEditable(false);
        jTextField1.setText(String.valueOf(count2));
        jTextField1.setEditable(false);

        addtoAttendanceTable();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String time = dtf.format(now);

        if (time.equals("17:30:00")) {
            con.prepareStatement("UPDATE attendance SET today = 0").execute();
        }

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now1 = LocalDateTime.now();

        String datex = dtf1.format(now1);
        String thisYear = new SimpleDateFormat("yyyy").format(new java.util.Date());
        if (datex.equals("" + thisYear + "/12/31 17:30:00")) {
            con.prepareStatement("UPDATE attendance SET today=0, attended=0, leaved=0").execute();
        } else {
        }

    }

    /*public boolean requestValidate(){
        
        
        if((name.getSelectedIndex())!= -1){
                if((id.getSelectedIndex())!= -1){
                    if((category.getSelectedIndex())!= -1){
                        if((reason.getText() != null)){
                                if(fd.getDate() != null){
                                    if(td.getDate() != null){
                                           return true;
                        }else{
                            JOptionPane.showMessageDialog(jComboBox4, "Select a ToDate", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                            }
                        }else{
                            JOptionPane.showMessageDialog(jComboBox4, "Select a FromDate", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    }else{
                        JOptionPane.showMessageDialog(jComboBox4, "Type Reason", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }else{
                    JOptionPane.showMessageDialog(jComboBox4, "Select a Category", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
        }else{
            JOptionPane.showMessageDialog(jComboBox4, "Select a id", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }else{
            JOptionPane.showMessageDialog(jComboBox4, "Select a Name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }     
                        
        
 } */
    void updateMax(String q, javax.swing.JLabel tox) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery(q);
        rs.next();
        tot = rs.getInt("count");

        int i = tot % lim;
        if (i > 0) {
            maxPages = tot / lim + 1;
        } else {
            maxPages = tot / lim;
        }
        tox.setText(Integer.toString(maxPages));

        if (pg > maxPages) {
            pg = maxPages;
            page.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
        }
        if (maxPages == 1) {
            pg = 1;
            page.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
        }
    }

//Show tables in UI
    void addAttendanceTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Employee ID", "Employee Name", "Attended", "Leaves", "Current Status"}, 0);

            while (rs1.next()) {
                String eid = rs1.getString("eid");
                String ename = rs1.getString("ename");
                String attended = rs1.getString("attended");
                String leave = rs1.getString("leaved");
                String today = rs1.getString("today");

                Object[] row = {eid, ename, attended, leave, today};
                model.addRow(row);
            }
            jTable2.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(production.FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addRequestTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Employee ID", "Employee Name", "Category", "Leave Start", "Leave End", "Reason", "Status"}, 0);

            while (rs1.next()) {
                String eid = rs1.getString("eid");
                String ename = rs1.getString("ename");
                String category = rs1.getString("category");
                String lstart = rs1.getString("lstart");
                String lend = rs1.getString("lend");
                String reason = rs1.getString("reason");
                String status = rs1.getString("status");

                Object[] row = {eid, ename, category, lstart, lend, reason, status};
                model.addRow(row);
            }
            jTable1.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(production.FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addtoAttendanceTable() throws SQLException {
        String eid;
        String ename;

        ResultSet rs = con.createStatement().executeQuery("SELECT e.empNo , e.fname FROM employee e LEFT JOIN attendance a ON e.empNo = a.eid WHERE a.eid IS NULL");
        while (rs.next()) {
            eid = rs.getString("empNo");
            ename = rs.getString("fname");

            con.prepareStatement("INSERT INTO attendance (eid,ename) VALUES('" + eid + "','" + ename + "')").execute();
        }

        updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
        addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
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
        jLayeredPane1 = new javax.swing.JLayeredPane();
        MainMenu = new javax.swing.JPanel();
        FBButton1 = new javax.swing.JButton();
        FBButton2 = new javax.swing.JButton();
        FBButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        AttendanceList = new javax.swing.JPanel();
        Print = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        AttenBack = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        AttenRefresh = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        Item1 = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        page1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        present = new javax.swing.JButton();
        Search1 = new javax.swing.JTextField();
        absent = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        RequestLeave = new javax.swing.JPanel();
        td = new org.jdesktop.swingx.JXDatePicker();
        empname = new javax.swing.JLabel();
        empid = new javax.swing.JLabel();
        to = new javax.swing.JLabel();
        reason1 = new javax.swing.JLabel();
        reason2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        LeaReqBack = new javax.swing.JButton();
        LeaReqConfirm = new javax.swing.JButton();
        empid1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reason = new javax.swing.JTextArea();
        category = new javax.swing.JComboBox<>();
        fd = new org.jdesktop.swingx.JXDatePicker();
        name = new javax.swing.JComboBox<>();
        id = new javax.swing.JComboBox<>();
        LeaReqEdit = new javax.swing.JButton();
        error = new javax.swing.JLabel();
        RequestList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ReqLBack = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        ReqLRefresh = new javax.swing.JButton();
        ReqLdelete = new javax.swing.JButton();
        deny = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        page = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        Item = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Search = new javax.swing.JTextField();
        confirm = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        Print1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(940, 480));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        MainMenu.setBackground(new java.awt.Color(255, 255, 255));
        MainMenu.setMinimumSize(new java.awt.Dimension(940, 480));
        MainMenu.setPreferredSize(new java.awt.Dimension(940, 480));
        MainMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FBButton1.setBackground(new java.awt.Color(48, 48, 240));
        FBButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBButton1.setForeground(new java.awt.Color(255, 255, 255));
        FBButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-attendance-50.png"))); // NOI18N
        FBButton1.setText("Attendance");
        FBButton1.setToolTipText("");
        FBButton1.setBorder(null);
        FBButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FBButton1.setMinimumSize(new java.awt.Dimension(100, 100));
        FBButton1.setPreferredSize(new java.awt.Dimension(100, 100));
        FBButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FBButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBButton1ActionPerformed(evt);
            }
        });
        MainMenu.add(FBButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        FBButton2.setBackground(new java.awt.Color(48, 48, 240));
        FBButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBButton2.setForeground(new java.awt.Color(255, 255, 255));
        FBButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-form-50.png"))); // NOI18N
        FBButton2.setText("Apply Leave");
        FBButton2.setToolTipText("");
        FBButton2.setBorder(null);
        FBButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FBButton2.setMinimumSize(new java.awt.Dimension(100, 100));
        FBButton2.setPreferredSize(new java.awt.Dimension(100, 100));
        FBButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FBButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBButton2ActionPerformed(evt);
            }
        });
        MainMenu.add(FBButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, -1, -1));

        FBButton3.setBackground(new java.awt.Color(48, 48, 240));
        FBButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBButton3.setForeground(new java.awt.Color(255, 255, 255));
        FBButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-leave-50.png"))); // NOI18N
        FBButton3.setText("Leave Mgt");
        FBButton3.setToolTipText("");
        FBButton3.setBorder(null);
        FBButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FBButton3.setMinimumSize(new java.awt.Dimension(100, 100));
        FBButton3.setPreferredSize(new java.awt.Dimension(100, 100));
        FBButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FBButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBButton3ActionPerformed(evt);
            }
        });
        MainMenu.add(FBButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total On Leave:");
        MainMenu.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, -1, -1));
        MainMenu.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 380, 170, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total Attendance: ");
        MainMenu.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, -1, -1));
        MainMenu.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, 170, -1));

        jButton3.setBackground(new java.awt.Color(219, 76, 13));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        MainMenu.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 90, 30));

        jLayeredPane1.add(MainMenu, "card6");

        AttendanceList.setBackground(new java.awt.Color(255, 255, 255));
        AttendanceList.setMinimumSize(new java.awt.Dimension(940, 480));
        AttendanceList.setPreferredSize(new java.awt.Dimension(940, 480));
        AttendanceList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Print.setBackground(new java.awt.Color(71, 71, 116));
        Print.setForeground(new java.awt.Color(255, 255, 255));
        Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        Print.setText("Print");
        Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintActionPerformed(evt);
            }
        });
        AttendanceList.add(Print, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 140, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Attendance List of Employees");
        AttendanceList.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        AttenBack.setBackground(new java.awt.Color(255, 51, 0));
        AttenBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AttenBack.setForeground(new java.awt.Color(255, 255, 255));
        AttenBack.setText("Go Back");
        AttenBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttenBackActionPerformed(evt);
            }
        });
        AttendanceList.add(AttenBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        AttendanceList.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        AttenRefresh.setBackground(new java.awt.Color(0, 179, 50));
        AttenRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AttenRefresh.setForeground(new java.awt.Color(255, 255, 255));
        AttenRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        AttenRefresh.setText("Refresh");
        AttenRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        AttenRefresh.setIconTextGap(10);
        AttenRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttenRefreshActionPerformed(evt);
            }
        });
        AttendanceList.add(AttenRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 140, 30));

        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Show :");
        AttendanceList.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        Item1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        Item1.setSelectedIndex(2);
        Item1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Item1ActionPerformed(evt);
            }
        });
        AttendanceList.add(Item1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 70, 30));

        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Entries");
        AttendanceList.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Employee Name", "Category", "Workstation", "Attended", "Leaves", "Present/Absent"
            }
        ));
        jTable2.setRowHeight(35);
        jScrollPane2.setViewportView(jTable2);

        AttendanceList.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 910, 260));

        jButton9.setBackground(new java.awt.Color(0, 0, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        AttendanceList.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton10.setBackground(new java.awt.Color(0, 0, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        AttendanceList.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page1ActionPerformed(evt);
            }
        });
        AttendanceList.add(page1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("/");
        AttendanceList.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total1.setForeground(new java.awt.Color(255, 255, 255));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AttendanceList.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton11.setBackground(new java.awt.Color(0, 0, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        AttendanceList.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton12.setBackground(new java.awt.Color(0, 0, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        AttendanceList.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        present.setBackground(new java.awt.Color(0, 0, 255));
        present.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        present.setForeground(new java.awt.Color(255, 255, 255));
        present.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        present.setText("Present");
        present.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentActionPerformed(evt);
            }
        });
        AttendanceList.add(present, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 420, -1, 30));

        Search1.setText("Search");
        Search1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Search1CaretUpdate(evt);
            }
        });
        Search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Search1MouseClicked(evt);
            }
        });
        AttendanceList.add(Search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, 30));

        absent.setBackground(new java.awt.Color(0, 0, 255));
        absent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        absent.setForeground(new java.awt.Color(255, 255, 255));
        absent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        absent.setText("Absent");
        absent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absentActionPerformed(evt);
            }
        });
        AttendanceList.add(absent, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, -1, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Employee ID :-");
        AttendanceList.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 420, 100, 30));

        jTextField2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField2CaretUpdate(evt);
            }
        });
        AttendanceList.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 420, 170, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        AttendanceList.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(AttendanceList, "card3");

        RequestLeave.setBackground(new java.awt.Color(255, 255, 255));
        RequestLeave.setMinimumSize(new java.awt.Dimension(940, 480));
        RequestLeave.setPreferredSize(new java.awt.Dimension(940, 480));
        RequestLeave.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        td.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdActionPerformed(evt);
            }
        });
        RequestLeave.add(td, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 360, 200, 30));

        empname.setBackground(new java.awt.Color(150, 150, 150));
        empname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        empname.setText("Employee Name");
        empname.setMaximumSize(new java.awt.Dimension(100, 100));
        empname.setMinimumSize(new java.awt.Dimension(100, 100));
        empname.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(empname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 130, 37));

        empid.setBackground(new java.awt.Color(150, 150, 150));
        empid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        empid.setText("Category");
        empid.setMaximumSize(new java.awt.Dimension(100, 100));
        empid.setMinimumSize(new java.awt.Dimension(100, 100));
        empid.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(empid, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 130, 37));

        to.setBackground(new java.awt.Color(150, 150, 150));
        to.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        to.setText("To");
        to.setMaximumSize(new java.awt.Dimension(100, 100));
        to.setMinimumSize(new java.awt.Dimension(100, 100));
        to.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(to, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 130, 37));

        reason1.setBackground(new java.awt.Color(150, 150, 150));
        reason1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reason1.setText("Reason");
        reason1.setMaximumSize(new java.awt.Dimension(100, 100));
        reason1.setMinimumSize(new java.awt.Dimension(100, 100));
        reason1.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(reason1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 130, 37));

        reason2.setBackground(new java.awt.Color(150, 150, 150));
        reason2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reason2.setText("From");
        reason2.setMaximumSize(new java.awt.Dimension(100, 100));
        reason2.setMinimumSize(new java.awt.Dimension(100, 100));
        reason2.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(reason2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, 130, 37));
        RequestLeave.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel31.setText("Leave Request Form ");
        RequestLeave.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        LeaReqBack.setBackground(new java.awt.Color(255, 51, 0));
        LeaReqBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LeaReqBack.setForeground(new java.awt.Color(255, 255, 255));
        LeaReqBack.setText("Go Back");
        LeaReqBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaReqBackActionPerformed(evt);
            }
        });
        RequestLeave.add(LeaReqBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        LeaReqConfirm.setBackground(new java.awt.Color(0, 2, 240));
        LeaReqConfirm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LeaReqConfirm.setForeground(new java.awt.Color(255, 255, 255));
        LeaReqConfirm.setText("Apply");
        LeaReqConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaReqConfirmActionPerformed(evt);
            }
        });
        RequestLeave.add(LeaReqConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 430, 100, 30));

        empid1.setBackground(new java.awt.Color(150, 150, 150));
        empid1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        empid1.setText("Employee ID");
        empid1.setMaximumSize(new java.awt.Dimension(100, 100));
        empid1.setMinimumSize(new java.awt.Dimension(100, 100));
        empid1.setPreferredSize(new java.awt.Dimension(100, 100));
        RequestLeave.add(empid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 130, 37));

        reason.setColumns(20);
        reason.setRows(5);
        reason.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                reasonFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(reason);

        RequestLeave.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 490, -1));

        category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Official", "Personal" }));
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });
        RequestLeave.add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 150, 40));

        fd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fdActionPerformed(evt);
            }
        });
        RequestLeave.add(fd, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 360, 200, 30));

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        RequestLeave.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 490, 40));

        id.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                idItemStateChanged(evt);
            }
        });
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        RequestLeave.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 210, 40));

        LeaReqEdit.setBackground(new java.awt.Color(0, 2, 240));
        LeaReqEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LeaReqEdit.setForeground(new java.awt.Color(255, 255, 255));
        LeaReqEdit.setText("Edit");
        LeaReqEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaReqEditActionPerformed(evt);
            }
        });
        RequestLeave.add(LeaReqEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 430, 100, 30));

        error.setForeground(new java.awt.Color(255, 0, 0));
        RequestLeave.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, 490, 30));

        jLayeredPane1.add(RequestLeave, "card2");

        RequestList.setBackground(new java.awt.Color(255, 255, 255));
        RequestList.setMinimumSize(new java.awt.Dimension(940, 480));
        RequestList.setPreferredSize(new java.awt.Dimension(940, 480));
        RequestList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Request List of Employees");
        RequestList.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        ReqLBack.setBackground(new java.awt.Color(255, 51, 0));
        ReqLBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqLBack.setForeground(new java.awt.Color(255, 255, 255));
        ReqLBack.setText("Go Back");
        ReqLBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqLBackActionPerformed(evt);
            }
        });
        RequestList.add(ReqLBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        RequestList.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        ReqLRefresh.setBackground(new java.awt.Color(0, 179, 50));
        ReqLRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ReqLRefresh.setForeground(new java.awt.Color(255, 255, 255));
        ReqLRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        ReqLRefresh.setText("Refresh");
        ReqLRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ReqLRefresh.setIconTextGap(10);
        ReqLRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqLRefreshActionPerformed(evt);
            }
        });
        RequestList.add(ReqLRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 140, 30));

        ReqLdelete.setBackground(new java.awt.Color(255, 0, 0));
        ReqLdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ReqLdelete.setForeground(new java.awt.Color(255, 255, 255));
        ReqLdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/delete.png"))); // NOI18N
        ReqLdelete.setText("Delete");
        ReqLdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqLdeleteActionPerformed(evt);
            }
        });
        RequestList.add(ReqLdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, 100, 30));

        deny.setBackground(new java.awt.Color(0, 0, 255));
        deny.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deny.setForeground(new java.awt.Color(255, 255, 255));
        deny.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        deny.setText("Deny");
        deny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                denyActionPerformed(evt);
            }
        });
        RequestList.add(deny, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 420, 120, 30));

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        RequestList.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton7.setBackground(new java.awt.Color(0, 0, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        RequestList.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageActionPerformed(evt);
            }
        });
        RequestList.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("/");
        RequestList.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        jButton8.setBackground(new java.awt.Color(0, 0, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        RequestList.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        RequestList.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RequestList.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Show :");
        RequestList.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        Item.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        Item.setSelectedIndex(2);
        Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemActionPerformed(evt);
            }
        });
        RequestList.add(Item, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("entries");
        RequestList.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Employee Name", "Leave Start", "Leave End", "Reason", "Status"
            }
        ));
        jTable1.setRowHeight(35);
        jScrollPane1.setViewportView(jTable1);

        RequestList.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        Search.setText("Search");
        Search.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchCaretUpdate(evt);
            }
        });
        Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchMouseClicked(evt);
            }
        });
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });
        RequestList.add(Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 140, 30));

        confirm.setBackground(new java.awt.Color(0, 0, 255));
        confirm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirm.setForeground(new java.awt.Color(255, 255, 255));
        confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        confirm.setText("Confirm");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        RequestList.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 120, 30));

        edit.setBackground(new java.awt.Color(0, 0, 255));
        edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        edit.setForeground(new java.awt.Color(255, 255, 255));
        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        RequestList.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 120, 30));

        Print1.setBackground(new java.awt.Color(71, 71, 116));
        Print1.setForeground(new java.awt.Color(255, 255, 255));
        Print1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        Print1.setText("Print");
        Print1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Print1ActionPerformed(evt);
            }
        });
        RequestList.add(Print1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 140, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        RequestList.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(RequestList, "card4");

        jPanel1.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void FBButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBButton1ActionPerformed
        // TODO add your handling code here:
        MainMenu.setVisible(false);
        AttendanceList.setVisible(true);
        jLabel7.hide();

        try {
            addtoAttendanceTable();
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FBButton1ActionPerformed

    private void FBButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBButton2ActionPerformed
        // TODO add your handling code here:
        MainMenu.setVisible(false);
        RequestLeave.setVisible(true);
        jLabel7.hide();
        LeaReqEdit.setVisible(false);
        LeaReqConfirm.setVisible(true);
        jLabel31.setText("Apply Leave Form");
        id.setEnabled(true);
        name.setEnabled(true);
        category.setSelectedIndex(-1);
        name.setSelectedIndex(-1);
        id.setSelectedIndex(-1);
        reason.setText(null);
        fd.setDate(null);
        td.setDate(null);
    }//GEN-LAST:event_FBButton2ActionPerformed

    private void FBButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBButton3ActionPerformed
        // TODO add your handling code here:
        MainMenu.setVisible(false);
        RequestList.setVisible(true);
        jLabel7.hide();
    }//GEN-LAST:event_FBButton3ActionPerformed

    private void AttenBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttenBackActionPerformed
        MainMenu.setVisible(true);
        AttendanceList.setVisible(false);
        jLabel7.setVisible(true);
        int count1;
        int count2;

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM attendance WHERE today =1");
            rs.next();
            count1 = rs.getInt("count");

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM attendance WHERE today =0");
            rs1.next();
            count2 = rs1.getInt("count");

            jTextField3.setText(String.valueOf(count1));
            jTextField3.setEditable(false);
            jTextField1.setText(String.valueOf(count2));
            jTextField1.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AttenBackActionPerformed

    private void LeaReqBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaReqBackActionPerformed
        // TODO add your handling code here:
        MainMenu.setVisible(true);
        RequestLeave.setVisible(false);
        jLabel7.setVisible(true);
    }//GEN-LAST:event_LeaReqBackActionPerformed

    private void ReqLBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqLBackActionPerformed
        // TODO add your handling code here:
        MainMenu.setVisible(true);
        RequestList.setVisible(false);
        jLabel7.setVisible(true);
    }//GEN-LAST:event_ReqLBackActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Home.att.setVisible(false);
        Home.peo.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    //request table
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        pg = 1;
        page.setText(Integer.toString(pg));
        offset = (pg - 1) * lim;

        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (pg > 1) {
            pg--;
            page.setText(Integer.toString(pg));
        }
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void pageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageActionPerformed
        int p = Integer.parseInt((String) page.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                offset = (pg - 1) * lim;
                updateMax("SELECT COUNT(*) AS count FROM request", total);
                addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_pageActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (pg < maxPages) {
            pg++;
            page.setText(Integer.toString(pg));
        }
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        pg = maxPages;
        page.setText(Integer.toString(pg));
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    //attendance table
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        pg = 1;
        page1.setText(Integer.toString(pg));
        offset = (pg - 1) * lim;

        try {
            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (pg > 1) {
            pg--;
            page1.setText(Integer.toString(pg));
        }
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void page1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page1ActionPerformed
        int p = Integer.parseInt((String) page1.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                offset = (pg - 1) * lim;
                updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
                addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (pg < maxPages) {
            pg++;
            page1.setText(Integer.toString(pg));
        }
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        pg = maxPages;
        page1.setText(Integer.toString(pg));
        offset = (pg - 1) * lim;
        try {
            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed

    }//GEN-LAST:event_SearchActionPerformed

    //Request
    private void SearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchCaretUpdate
        // TODO add your handling code here:
        String sh = Search.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM request", total);
                addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addRequestTable("SELECT * FROM request WHERE eid like '%" + sh + "%' OR ename like '%" + sh + "%' OR category like '%" + sh + "%' OR status like '%" + sh + "%'");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SearchCaretUpdate

    //Attendance
    private void Search1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Search1CaretUpdate
        String sh = Search1.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
                addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addAttendanceTable("SELECT * FROM attendance WHERE eid like '%" + sh + "%' OR ename like '%" + sh + "%'");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_Search1CaretUpdate

    //attendance
    private void Item1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Item1ActionPerformed
        if (Item1.getSelectedItem().equals("")) {
            lim = 15;
            Item1.setSelectedItem("15");
            pg = 1;
            page1.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
            try {
                updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
                addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) Item1.getSelectedItem());
            pg = 1;
            page1.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
            try {
                updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
                addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_Item1ActionPerformed

    //request
    private void ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActionPerformed
        if (Item.getSelectedItem().equals("")) {
            lim = 15;
            Item.setSelectedItem("15");
            pg = 1;
            page.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
            try {
                updateMax("SELECT COUNT(*) AS count FROM request", total);
                addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) Item.getSelectedItem());
            pg = 1;
            page.setText(Integer.toString(pg));
            offset = (pg - 1) * lim;
            try {
                updateMax("SELECT COUNT(*) AS count FROM request", total);
                addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ItemActionPerformed

    private void AttenRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttenRefreshActionPerformed
        try {
            Search1.setText("Enter EID");
            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AttenRefreshActionPerformed

    private void ReqLRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqLRefreshActionPerformed
        try {
            Search.setText("Enter EID");
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReqLRefreshActionPerformed

    private void Search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Search1MouseClicked
        Search1.setText(null);
    }//GEN-LAST:event_Search1MouseClicked

    private void SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchMouseClicked
        Search.setText(null);
    }//GEN-LAST:event_SearchMouseClicked

    //attendance
    private void presentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentActionPerformed
        int r = jTable2.getSelectedRow();
        int a;
        String temp = (String) jTable2.getValueAt(r, 4);
        String eid = (String) jTable2.getValueAt(r, 0);

        try {
            int t;

            ResultSet rs = con.createStatement().executeQuery("SELECT attended from attendance WHERE eid='" + eid + "'");
            rs.next();
            a = rs.getInt("attended");

            t = Integer.parseInt(temp);
            if (t != 1) {
                a++;
                con.prepareStatement("UPDATE attendance set today=1,attended=" + a + " WHERE eid='" + eid + "'").execute();
            }

            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_presentActionPerformed

    private void absentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absentActionPerformed
        int r = jTable2.getSelectedRow();
        int a;
        String temp = (String) jTable2.getValueAt(r, 4);
        String eid = (String) jTable2.getValueAt(r, 0);
        int t;

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT leaved From attendance WHERE eid='" + eid + "'");
            rs.next();
            a = rs.getInt("leaved");

            t = Integer.parseInt(temp);
            if (t != 0) {
                a++;
                con.prepareStatement("UPDATE attendance set today=0,leaved=" + a + " WHERE eid='" + eid + "'").execute();
            }

            updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
            addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_absentActionPerformed

    //request
    private void denyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_denyActionPerformed
        int r = jTable1.getSelectedRow();
        String eid = (String) jTable1.getValueAt(r, 0);

        try {
            con.prepareStatement("UPDATE request set status='DENIED' WHERE eid='" + eid + "'").execute();

            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_denyActionPerformed

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
        int r = jTable1.getSelectedRow();
        int a;
        String eid = (String) jTable1.getValueAt(r, 0);
        String t = (String) jTable1.getValueAt(r, 6);
        String t1;

        try {
            String temp;
            int diff;

            ResultSet rs2 = con.createStatement().executeQuery("SELECT DATEDIFF(lend, lstart) AS DateDiff From request WHERE eid='" + eid + "'");
            rs2.next();
            temp = rs2.getString("DateDiff");
            diff = Integer.parseInt(temp);

            ResultSet rs1 = con.createStatement().executeQuery("SELECT leaved From attendance WHERE eid='" + eid + "'");
            rs1.next();
            a = rs1.getInt("leaved");

            con.prepareStatement("UPDATE request set status='GRANTED' WHERE eid='" + eid + "'").execute();

            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");

            ResultSet rs3 = con.createStatement().executeQuery("SELECT status FROM request WHERE eid = '" + eid + "'");
            rs3.next();
            t1 = rs3.getString("status");

            if (t1.equals("GRANTED")) {
                a = a + diff;
                con.prepareStatement("UPDATE attendance set leaved=" + a + " WHERE eid='" + eid + "'").execute();
            }

        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_confirmActionPerformed

    //request
    private void ReqLdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqLdeleteActionPerformed
        int r = jTable1.getSelectedRow();
        String eid = (String) jTable1.getValueAt(r, 0);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Selected request for leave will be deleted", "Warning", dialogButton);

        if (dialogResult == 0) {
            try {
                con.prepareStatement("DELETE FROM request WHERE eid='" + eid + "'").execute();

                JOptionPane.showMessageDialog(null, "Request Deleted!", "Done", JOptionPane.INFORMATION_MESSAGE);
                updateMax("SELECT COUNT(*) AS count FROM request", total);
                addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ReqLdeleteActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        id.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        error.setText(null);
    }//GEN-LAST:event_idActionPerformed

    private void LeaReqConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaReqConfirmActionPerformed
        String eid = String.valueOf(id.getSelectedItem());
        String ename = String.valueOf(name.getSelectedItem());
        String cat = String.valueOf(category.getSelectedItem());
        String rsn = reason.getText();
        String from = null;
        String to = null;
        int fromDate = 0;
        int toDate = 0;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df1 = new SimpleDateFormat("dd");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        String ff = null;
        String tt = null;

        //if(FBIssueValidate())
        //{
        //if(requestValidate()){
        if (name.getSelectedIndex() != -1) {
            if (id.getSelectedIndex() != -1) {
                if (category.getSelectedIndex() != -1) {
                    if (!rsn.equals("")) {
                        if (fd.getDate() != null) {
                            if (td.getDate() != null) {
                                from = df.format(fd.getDate());
                                to = df.format(td.getDate());

                                ff = df2.format(fd.getDate());
                                tt = df2.format(td.getDate());

                                fromDate = Integer.parseInt(df1.format(fd.getDate()));
                                toDate = Integer.parseInt(df1.format(td.getDate()));
                                if (validateDateBackward(from, to, error)) {
                                    if ((toDate - fromDate) <= 5) {
                                        try {
                                            con.prepareStatement("INSERT INTO request (eid,ename,category,lstart,lend,reason,status) VALUES('" + eid + "','" + ename + "','" + cat + "','" + ff + "','" + tt + "','" + rsn + "','PENDING')").execute();
                                            JOptionPane.showMessageDialog(null, "Request Sent", "Done", JOptionPane.INFORMATION_MESSAGE);

                                            id.setSelectedIndex(-1);
                                            name.setSelectedIndex(-1);
                                            category.setSelectedIndex(-1);
                                            reason.setText(null);

                                            updateMax("SELECT COUNT(*) AS count FROM request", total);
                                            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
                                        } catch (SQLException ex) {
                                            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        fd.setBorder(BorderFactory.createLineBorder(Color.red));
                                        error.setText("Maximum dates allowed is five !");
                                    }
                                }
                            } else {
                                td.setBorder(BorderFactory.createLineBorder(Color.red));
                                error.setText("To date field cannot be empty !");
                            }
                        } else {
                            fd.setBorder(BorderFactory.createLineBorder(Color.red));
                            error.setText("From date field cannot be empty !");
                        }
                    } else {
                        reason.setBorder(BorderFactory.createLineBorder(Color.red));
                        error.setText("Reason field cannot be empty !");
                    }
                } else {
                    category.setBorder(BorderFactory.createLineBorder(Color.red));
                    error.setText("Please Select a category !");
                }
            } else {
                id.setBorder(BorderFactory.createLineBorder(Color.red));
                error.setText("Please Select a ID !");
            }
        } else {
            name.setBorder(BorderFactory.createLineBorder(Color.red));
            error.setText("Please Select a name !");
        }

        // }
        //}

    }//GEN-LAST:event_LeaReqConfirmActionPerformed

    boolean validateDateBackward(String val, String today, JLabel label) {
        int[] dayArr = new int[3];
        int[] arr = new int[3];
        int[] cd = new int[3];
        String cday = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        int a = 0;
        int b = 0;
        int c=0;
        
        StringTokenizer tk2 = new StringTokenizer(cday, "-");
        while (tk2.hasMoreElements()) {
            cd[c] = Integer.parseInt(tk2.nextToken());
            //System.out.println(arr[a]);
            c++;
        }
        
        StringTokenizer tk1 = new StringTokenizer(today, "-");
        while (tk1.hasMoreElements()) {
            dayArr[b] = Integer.parseInt(tk1.nextToken());
            //System.out.println(arr[a]);
            b++;
        }

        if (val.contains("[a-zA-Z]+")) {
            label.setText("Invalid Date !");
            return false;
        } else {
            if (!val.equals("")&& !today.equals("")) {
                StringTokenizer tk = new StringTokenizer(val, "-");
                while (tk.hasMoreElements()) {
                    arr[a] = Integer.parseInt(tk.nextToken());
                    //System.out.println(arr[a]);
                    a++;
                }
                int check = dayArr[0]-arr[0];
                System.out.println(dayArr[0]);
                System.out.println(arr[0]);
                System.out.println(check);
                if ((check >= 0 && check <= 5) && (arr[2] == dayArr[2]) && (cd[0]<=arr[0])) {
                    return true;
                } else {
                    label.setText("Invalid Date !");
                    return false;
                }
            } else {
                label.setText("Date field cannot be empty !");
                return false;
            }
        }
    }
    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        int r = jTable1.getSelectedRow();
        String eid = (String) jTable1.getValueAt(r, 0);
        String ename = (String) jTable1.getValueAt(r, 1);
        String cat = (String) jTable1.getValueAt(r, 2);
        String lstart = (String) jTable1.getValueAt(r, 3);
        String lend = (String) jTable1.getValueAt(r, 4);
        String rsn = (String) jTable1.getValueAt(r, 5);
        String status = (String) jTable1.getValueAt(r, 6);

        RequestList.setVisible(false);
        RequestLeave.setVisible(true);
        LeaReqEdit.setVisible(true);
        LeaReqConfirm.setVisible(false);
        jLabel31.setText("Edit Leave Form");

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT empNo FROM Employee");
            while (rs.next()) {
                id.addItem(rs.getString("empNo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }

        id.setSelectedItem(eid);
        category.setSelectedItem(cat);
        reason.setText(rsn);
        id.setEnabled(false);
        name.setEnabled(false);

        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_editActionPerformed

    private void LeaReqEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaReqEditActionPerformed
        String rsn = reason.getText();
        String cat = String.valueOf(category.getSelectedItem());
        String eid = String.valueOf(id.getSelectedItem());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String Date = df.format(fd.getDate());
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String Date2 = df1.format(td.getDate());
        System.out.println(rsn);
        try {
            con.prepareStatement("UPDATE request set reason = '" + rsn + "' ,lstart = '" + Date + "',lend = '" + Date2 + "', category = '" + cat + "' WHERE eid = '" + eid + "'").execute();

            RequestLeave.setVisible(false);
            RequestList.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            updateMax("SELECT COUNT(*) AS count FROM request", total);
            addRequestTable("SELECT * FROM request LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_LeaReqEditActionPerformed

    private void idItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_idItemStateChanged
        String eid = String.valueOf(id.getSelectedItem());
        if (id.getSelectedIndex() != -1) {
            try {
                String n;
                name.removeAllItems();
                ResultSet rs1 = con.createStatement().executeQuery("SELECT fname FROM employee WHERE empNo = '" + eid + "'");
                rs1.next();

                name.addItem(rs1.getString("fname"));

                n = rs1.getString("fname");
                name.setSelectedItem(n);
                name.setEnabled(false);
            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_idItemStateChanged

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
        name.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        error.setText(null);
    }//GEN-LAST:event_nameActionPerformed

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed
        // TODO add your handling code here:
        category.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        error.setText(null);
    }//GEN-LAST:event_categoryActionPerformed

    private void fdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fdActionPerformed
        // TODO add your handling code here:
        fd.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        error.setText(null);
    }//GEN-LAST:event_fdActionPerformed

    private void tdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdActionPerformed
        // TODO add your handling code here:
        td.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        error.setText(null);
    }//GEN-LAST:event_tdActionPerformed

    private void reasonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_reasonFocusGained
        // TODO add your handling code here:
        reason.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        error.setText(null);
    }//GEN-LAST:event_reasonFocusGained

    private void PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintActionPerformed
        // TODO add your handling code here:
        Home.Report(jTable2, "Attendance Report", "E:\\Reports\\AttendanceReport.pdf");
    }//GEN-LAST:event_PrintActionPerformed

    private void Print1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Print1ActionPerformed
        // TODO add your handling code here:
        Home.Report(jTable1, "Leave Request Report", "E:\\Reports\\LeaveRequestReport.pdf");
    }//GEN-LAST:event_Print1ActionPerformed

    private void jTextField2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField2CaretUpdate

        if (jTextField2.getText().equals("") || jTextField2.getText().length() < 3) {
        } else {
            try {
                int cou;
                int eid = Integer.parseInt(jTextField2.getText());
                ResultSet rs = dbCon.con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM employee WHERE empNo = " + eid + "");
                rs.next();
                cou = rs.getInt("count");
                if (cou == 0) {
                    JOptionPane.showMessageDialog(jTable2, "Not a Valid Employee", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                  
                    int a,t;
                    ResultSet rs2 = con.createStatement().executeQuery("SELECT * from attendance WHERE eid=" + eid + "");
                    rs2.next();
                    a = rs2.getInt("attended");
                    t = rs2.getInt("today");
                    if (t != 1) {
                        a++;
                        con.prepareStatement("UPDATE attendance set today=1,attended=" + a + " WHERE eid=" + eid + "").execute();
                    }

                    updateMax("SELECT COUNT(*) AS count FROM attendance", total1);
                    addAttendanceTable("SELECT * FROM attendance LIMIT " + lim + " OFFSET " + offset + "");

                }

            } catch (SQLException ex) {
                Logger.getLogger(attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTextField2CaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttenBack;
    private javax.swing.JButton AttenRefresh;
    private javax.swing.JPanel AttendanceList;
    private javax.swing.JButton FBButton1;
    private javax.swing.JButton FBButton2;
    private javax.swing.JButton FBButton3;
    private javax.swing.JComboBox<String> Item;
    private javax.swing.JComboBox<String> Item1;
    private javax.swing.JButton LeaReqBack;
    private javax.swing.JButton LeaReqConfirm;
    private javax.swing.JButton LeaReqEdit;
    private javax.swing.JPanel MainMenu;
    private javax.swing.JButton Print;
    private javax.swing.JButton Print1;
    private javax.swing.JButton ReqLBack;
    private javax.swing.JButton ReqLRefresh;
    private javax.swing.JButton ReqLdelete;
    private javax.swing.JPanel RequestLeave;
    private javax.swing.JPanel RequestList;
    private javax.swing.JTextField Search;
    private javax.swing.JTextField Search1;
    private javax.swing.JButton absent;
    private javax.swing.JComboBox<String> category;
    private javax.swing.JButton confirm;
    private javax.swing.JButton deny;
    private javax.swing.JButton edit;
    private javax.swing.JLabel empid;
    private javax.swing.JLabel empid1;
    private javax.swing.JLabel empname;
    private javax.swing.JLabel error;
    private org.jdesktop.swingx.JXDatePicker fd;
    private javax.swing.JComboBox<String> id;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> name;
    private javax.swing.JTextField page;
    private javax.swing.JTextField page1;
    private javax.swing.JButton present;
    private javax.swing.JTextArea reason;
    private javax.swing.JLabel reason1;
    private javax.swing.JLabel reason2;
    private org.jdesktop.swingx.JXDatePicker td;
    private javax.swing.JLabel to;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    // End of variables declaration//GEN-END:variables
}
