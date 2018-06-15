/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package production;

import java.awt.Color;
import ITP.Home;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static production.dbCon.con;

/**
 *
 * @author Axio
 */
public class process extends javax.swing.JPanel {

    int tot;
    int maxPages;
    int pg = 1;
    int lim = 15;
    int offset = 0;
    production pro = new production();

    /**
     * Creates new form process
     *
     * @throws java.sql.SQLException
     */
    public process() throws SQLException {
        initComponents();

        jButton1.setVisible(false);

        Production.setBackground(new Color(0, 0, 0, 0));

        Production.setVisible(false);
        BWQC.setVisible(false);
        Chem.setVisible(false);
        Wash.setVisible(false);
        Hyd.setVisible(false);
        WetQC.setVisible(false);
        Dry.setVisible(false);
        AWQC.setVisible(false);
        Finish.setVisible(false);
        BWQC1.setVisible(false);
        Chem1.setVisible(false);
        Wash1.setVisible(false);
        Hyd1.setVisible(false);
        WetQC1.setVisible(false);
        Dry1.setVisible(false);
        AWQC1.setVisible(false);
        Finish1.setVisible(false);

        dbCon d = new dbCon();
        d.connect();
        // when initizalised valus add the table when system is start
        updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
        addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
        addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
        updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
        addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
        updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
        addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
        updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
        addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
        updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
        addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
        updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
        addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
        updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
        addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);

    }

    // making format to selected format
    void updateMax(String q, javax.swing.JLabel tox, javax.swing.JTextField pad) throws SQLException {
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
            pad.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
        }
        if (maxPages == 1) {
            pg = 1;
            pad.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
        }
    }

    void addProcessTable(String q, javax.swing.JTable jb) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Tag Number", "Date", "Time", "Employee"}, 0);

            while (rs1.next()) {
                String tag = rs1.getString("Tag_No");
                String date = rs1.getString("Date");
                String time = rs1.getString("Time");
                String emp = rs1.getString("Emp_No");

                Object[] row = {tag, date, time, emp};
                model.addRow(row);
            }
            jb.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addDryProcessTable(String q, javax.swing.JTable jb) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Tag Number", "Machine Number", "Date", "Time", "Employee"}, 0);

            while (rs1.next()) {
                String tag = rs1.getString("Tag_No");
                String mah = rs1.getString("Machine_No");
                String date = rs1.getString("Date");
                String time = rs1.getString("Time");
                String emp = rs1.getString("Emp_No");

                Object[] row = {tag, mah, date, time, emp};
                model.addRow(row);
            }
            jb.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean DryValidate() throws SQLException {
        if ((Dry_tag.getSelectedIndex()) != -1) {
            if ((Dry_mah.getSelectedIndex()) != -1) {
                if ((Dry_emp.getSelectedIndex()) == -1) {
                    //JOptionPane.showMessageDialog(null, "Employee not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    Dry_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    Dry_err.setText("Employee not Selected");
                    return false;
                }
            } else {
                // JOptionPane.showMessageDialog(null, "Machine Number not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                Dry_mah.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                Dry_err.setText("Machine Number not Selected");
                return false;
            }
        } else {
            // JOptionPane.showMessageDialog(null, "Tag Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            Dry_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            Dry_err.setText("Tag Not Selected");
            return false;
        }
        return true;
    }
    // null validate

    boolean TableValidate(javax.swing.JComboBox tb, javax.swing.JComboBox tb1, javax.swing.JLabel lb) throws SQLException {
        if ((tb.getSelectedIndex()) != -1) {
            if ((tb1.getSelectedIndex()) != -1) {
            } else {
                //JOptionPane.showMessageDialog(null, "Employee not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                tb1.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                lb.setText("Employee not Selected");
                return false;
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Tag Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            tb.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            lb.setText("Tag Not Selected");
            return false;
        }
        return true;
    }
    // scaning part null validate

    boolean AutoValidate(javax.swing.JTextField tx, javax.swing.JTextField tx1, javax.swing.JLabel lb) throws SQLException {
        String rh = tx.getText();
        String rh1 = tx1.getText();

        if (rh.equals("")) {
            //JOptionPane.showMessageDialog(null, "Tag Not Scaned", "Error", JOptionPane.ERROR_MESSAGE);
            tx.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            lb.setText("Tag Not Scaned");
            return false;
        } else {
            if ((rh1.equals(""))) {
                //JOptionPane.showMessageDialog(null, "Employee not Scaned", "Error", JOptionPane.ERROR_MESSAGE);
                tx1.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                lb.setText("Employee not Scaned");
                return false;
            }
        }
        return true;
    }

    boolean AutoDryValidate(javax.swing.JTextField tx, javax.swing.JTextField tx1, javax.swing.JComboBox tc) throws SQLException {
        String rh = tx.getText();
        String rh1 = tx1.getText();

        if (rh.equals("")) {
            //JOptionPane.showMessageDialog(null, "Tag Not Scaned", "Error", JOptionPane.ERROR_MESSAGE);
            tx.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            Dry_err.setText("Tag Not Scaned ");
            return false;
        } else {
            if ((rh1.equals(""))) {
                //JOptionPane.showMessageDialog(null, "Employee not Scaned", "Error", JOptionPane.ERROR_MESSAGE);
                tx1.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                Dry_err.setText("Employee not Scaned ");
                return false;
            } else {
                if ((tc.getSelectedIndex()) == -1) {
                    //JOptionPane.showMessageDialog(null, "Machine Number not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    tc.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    Dry_err.setText("Machine Number not Selected");
                    return false;
                }
            }
        }
        return true;
    }
    //compare rows of tag and proccess chek table and add rows witch are not in proccess chk table from tag table

    void addtoProcesschkTable() throws SQLException {
        String bat;
        String cus;
        String col;
        String style;
        int tag;
        /*int bwqc;
    int chem;
    int wash;
    int hydro;
    int wetqc;
    int dry;
    int awqc;*/

        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No, t.BLP_No, t.Customer, t.Color, t.Style_No FROM tag t LEFT JOIN processchk p ON t.BLP_No = p.BLP_No WHERE t.Tag_Status = 'Printed' AND p.Tag_No IS NULL");
        while (rs.next()) {
            tag = rs.getInt("Tag_No");
            bat = rs.getString("BLP_No");
            cus = rs.getString("Customer");
            style = rs.getString("Style_No");
            col = rs.getString("Color");

            con.prepareStatement("INSERT INTO processchk (Tag_No,BLP_No,Customer,Style_No,Color) VALUES('" + tag + "','" + bat + "','" + cus + "','" + style + "','" + col + "')").execute();
            /*ResultSet rs1 = con.createStatement().executeQuery("SELECT bwqc,chemicals,wash,hydro,wetqc,dry,awqc FROM recipe WHERE customer = '"+cus+" AND styleNo = '"+style+"' AND color = '"+col+"' ");
            rs1.next();
            bwqc = rs.getInt("bwqc");
            chem = rs.getInt("chemicals");
            wash = rs.getInt("wash");
            hydro = rs.getInt("hydro");
            wetqc = rs.getInt("wetqc");
            dry = rs.getInt("dry");
            awqc = rs.getInt("awqc");
            
            con.prepareStatement("INSERT INTO processchk (Tag_No,BLP_No,Customer,Style_No,Color,bwqc,chemicals,wash,hydro,wetqc,dry,awqc) VALUES('"+tag+"','"+bat+"','"+cus+"','"+style+"','"+col+"',"+bwqc+","+chem+","+wash+","+hydro+","+wetqc+","+dry+","+awqc+")").execute();*/
        }

    }
    // this methode chek whether the a packing list follows tha recipy given 

    boolean processchk(int tg, String colname, String Deptname) throws SQLException {
        int tag = tg; //get parameters and asign to the variable
        String cus;
        String style;
        int count;
        int bwqc, chem, wash, hydro, wetqc, dry, awqc;
        int bwqcck, chemck, washck, hydrock, wetqcck, dryck, awqcck;
        //get customer and style and asign to the two variable
        ResultSet rs = con.createStatement().executeQuery("SELECT Customer, Style_No FROM tag WHERE Tag_No = " + tag + "");
        rs.next();
        cus = rs.getString("Customer");
        style = rs.getString("Style_No");

        //get recipy from the recipy table using CUSTOMER AND STYLE  a        
        try {
            ResultSet rs1 = con.createStatement().executeQuery("SELECT bwqc,chemicals,w1,h1,wetqc,d1,awqc FROM recipe WHERE customer = '" + cus + "' AND styleNo = '" + style + "'");
            rs1.next();
            bwqc = rs1.getInt("bwqc");
            chem = rs1.getInt("chemicals");
            wash = rs1.getInt("w1");
            hydro = rs1.getInt("h1");
            wetqc = rs1.getInt("wetqc");
            dry = rs1.getInt("d1");
            awqc = rs1.getInt("awqc");

            ResultSet rs2 = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM " + Deptname + " WHERE Tag_No = " + tag + "");
            rs2.next();
            count = rs2.getInt("count");//check the tag is printed befor and get raw count

            if (count == 0) {
                con.prepareStatement("UPDATE processchk set " + colname + " = 1 WHERE Tag_No = " + tag + "").execute();//using that tag number and col name is going to 1
                ResultSet rs3 = con.createStatement().executeQuery("SELECT bwqc, chemicals, wash, hydro, wetqc, dry, awqc FROM processchk WHERE Tag_No = " + tag + "");//get all values from da procces table 
                rs3.next();
                bwqcck = rs3.getInt("bwqc");
                chemck = rs3.getInt("chemicals");
                washck = rs3.getInt("wash");
                hydrock = rs3.getInt("hydro");
                wetqcck = rs3.getInt("wetqc");
                dryck = rs3.getInt("dry");
                awqcck = rs3.getInt("awqc");

                if (bwqcck == bwqc) {
                    if (chemck == chem && !Deptname.equals("bwqc")) {
                        if (washck == wash && !Deptname.equals("chemicals")) {
                            if (hydrock == hydro && !Deptname.equals("wash")) {
                                if (wetqcck == wetqc && !Deptname.equals("hydro")) {
                                    if (dryck == dry && !Deptname.equals("wetqc")) {
                                        if (awqcck == awqc && !Deptname.equals("dry")) {
                                            if (colname.equals("finish")) {
                                                return true;
                                            }
                                        } else if (awqcck < awqc && !Deptname.equals("dry")) {
                                            con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                            JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in after wash quality check department", "Error", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        } else if (awqcck > awqc && !Deptname.equals("dry")) {
                                            con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                            JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into after wash quality check department", "Error", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }
                                    } else if (dryck < dry && !Deptname.equals("wetqc")) {
                                        con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                        JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in dry department", "Error", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    } else if (dryck > dry && !Deptname.equals("wetqc")) {
                                        con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                        JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into dry department", "Error", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }
                                } else if (wetqcck < wetqc && !Deptname.equals("hydro")) {
                                    con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                    JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in wet quality check department", "Error", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                } else if (wetqcck > wetqc && !Deptname.equals("hydro")) {
                                    con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                    JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into wet quality check department", "Error", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                }
                            } else if (hydrock < hydro && !Deptname.equals("wash")) {
                                con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in hydro department", "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            } else if (hydrock > hydro && !Deptname.equals("wash")) {
                                con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                                JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into hydro department", "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        } else if (washck < wash && !Deptname.equals("chemicals")) {
                            con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                            JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in washing department", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        } else if (washck > wash && !Deptname.equals("chemicals")) {
                            con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                            JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into washing department", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } else if (chemck < chem && !Deptname.equals("bwqc")) {
                        con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                        JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in chemicals department", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    } else if (chemck > chem && !Deptname.equals("bwqc")) {
                        con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                        JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into chemicals department", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else if (bwqcck < bwqc) {
                    con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                    JOptionPane.showMessageDialog(null, "Tag was NOT SCANNED in bwqc department", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else if (bwqcck > bwqc) {
                    con.prepareStatement("UPDATE processchk set " + colname + " = 0 WHERE Tag_No = " + tag + "").execute();
                    JOptionPane.showMessageDialog(null, "Item is NOT SUPPOSE to go into bwqc department", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tag was ALREADY SCANNED in this department", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please add a recipe", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        Production = new javax.swing.JPanel();
        BWQCButton = new javax.swing.JButton();
        ChemicalsButton = new javax.swing.JButton();
        WashingButton = new javax.swing.JButton();
        HydroButton = new javax.swing.JButton();
        WetQCButton = new javax.swing.JButton();
        DryingButton = new javax.swing.JButton();
        AWQCButton = new javax.swing.JButton();
        FinishingButton = new javax.swing.JButton();
        BWQC = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel96 = new javax.swing.JLabel();
        jComboBox25 = new javax.swing.JComboBox<>();
        jLabel97 = new javax.swing.JLabel();
        search2 = new javax.swing.JTextField();
        BWQCBack = new javax.swing.JButton();
        BWQCchk = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        total4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        page4 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Chem = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel101 = new javax.swing.JLabel();
        jComboBox26 = new javax.swing.JComboBox<>();
        jLabel102 = new javax.swing.JLabel();
        search3 = new javax.swing.JTextField();
        ChemBack = new javax.swing.JButton();
        chem_check = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        page5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        total5 = new javax.swing.JLabel();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        search4 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Wash = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        jLabel106 = new javax.swing.JLabel();
        jComboBox27 = new javax.swing.JComboBox<>();
        jLabel107 = new javax.swing.JLabel();
        WashBack = new javax.swing.JButton();
        Washchk = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        page6 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        total6 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        search5 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Hyd = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jLabel111 = new javax.swing.JLabel();
        jComboBox28 = new javax.swing.JComboBox<>();
        jLabel112 = new javax.swing.JLabel();
        HydBack = new javax.swing.JButton();
        Hydrochk = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        page7 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        total7 = new javax.swing.JLabel();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        search6 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        WetQC = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jLabel116 = new javax.swing.JLabel();
        jComboBox29 = new javax.swing.JComboBox<>();
        jLabel117 = new javax.swing.JLabel();
        WetQCBack = new javax.swing.JButton();
        WetQCchk = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        page8 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        total8 = new javax.swing.JLabel();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        search10 = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        Dry = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jLabel121 = new javax.swing.JLabel();
        jComboBox30 = new javax.swing.JComboBox<>();
        jLabel122 = new javax.swing.JLabel();
        search7 = new javax.swing.JTextField();
        DryBack = new javax.swing.JButton();
        Drychk = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        page9 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        total9 = new javax.swing.JLabel();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        search11 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        AWQC = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jLabel126 = new javax.swing.JLabel();
        jComboBox31 = new javax.swing.JComboBox<>();
        jLabel127 = new javax.swing.JLabel();
        search8 = new javax.swing.JTextField();
        AWQCBack = new javax.swing.JButton();
        AWQCchk = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        page10 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        total10 = new javax.swing.JLabel();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        search12 = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        Finish = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jLabel140 = new javax.swing.JLabel();
        jComboBox32 = new javax.swing.JComboBox<>();
        jLabel153 = new javax.swing.JLabel();
        search9 = new javax.swing.JTextField();
        FinBack = new javax.swing.JButton();
        Finchk = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        page11 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        total11 = new javax.swing.JLabel();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        search13 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        BWQC1 = new javax.swing.JPanel();
        BWQCBack1 = new javax.swing.JButton();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel103 = new javax.swing.JLabel();
        BWQCchk3add = new javax.swing.JButton();
        jSeparator23 = new javax.swing.JSeparator();
        BWQCchk4addmanually = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BW_ctag = new javax.swing.JTextField();
        BW_cemp = new javax.swing.JTextField();
        BW_time = new javax.swing.JTextField();
        BW_tag = new javax.swing.JComboBox<>();
        BW_emp = new javax.swing.JComboBox<>();
        BW_err = new javax.swing.JLabel();
        BW_date = new javax.swing.JTextField();
        Chem1 = new javax.swing.JPanel();
        addchemback = new javax.swing.JButton();
        jSeparator25 = new javax.swing.JSeparator();
        add_chem_man_add = new javax.swing.JButton();
        add_che_add = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Chem_ctag = new javax.swing.JTextField();
        Chem_cemp = new javax.swing.JTextField();
        Chem_time = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        Chem_emp = new javax.swing.JComboBox<>();
        Chem_tag = new javax.swing.JComboBox<>();
        Chem_err = new javax.swing.JLabel();
        Chem_date = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Wash1 = new javax.swing.JPanel();
        add_wash_back = new javax.swing.JButton();
        jSeparator29 = new javax.swing.JSeparator();
        Add_wash_manu_add = new javax.swing.JButton();
        add_wash_add = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        Wash_ctag = new javax.swing.JTextField();
        Wash_cemp = new javax.swing.JTextField();
        Wash_time = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        Wash_emp = new javax.swing.JComboBox<>();
        Wash_tag = new javax.swing.JComboBox<>();
        Wash_err = new javax.swing.JLabel();
        Wash_date = new javax.swing.JTextField();
        Hyd1 = new javax.swing.JPanel();
        add_hydro_back = new javax.swing.JButton();
        jSeparator33 = new javax.swing.JSeparator();
        jLabel100 = new javax.swing.JLabel();
        Hydro_time = new javax.swing.JTextField();
        Hydro_cemp = new javax.swing.JTextField();
        Hydro_ctag = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        add_hydro_add = new javax.swing.JButton();
        add_hydro_manu_add = new javax.swing.JButton();
        Hydro_emp = new javax.swing.JComboBox<>();
        Hydro_tag = new javax.swing.JComboBox<>();
        Hydro_err = new javax.swing.JLabel();
        Hydro_date = new javax.swing.JTextField();
        WetQC1 = new javax.swing.JPanel();
        cheak_wet_qc_back = new javax.swing.JButton();
        jSeparator37 = new javax.swing.JSeparator();
        Wet_addMan = new javax.swing.JButton();
        Wet_cAdd = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        Wet_ctag = new javax.swing.JTextField();
        Wet_cemp = new javax.swing.JTextField();
        Wet_time = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        Wet_emp = new javax.swing.JComboBox<>();
        Wet_tag = new javax.swing.JComboBox<>();
        Wet_err = new javax.swing.JLabel();
        Wet_date = new javax.swing.JTextField();
        Dry1 = new javax.swing.JPanel();
        add_dry_back = new javax.swing.JButton();
        jSeparator41 = new javax.swing.JSeparator();
        add_dry_manu_add = new javax.swing.JButton();
        add_dry_add = new javax.swing.JButton();
        jLabel132 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        Dry_time = new javax.swing.JTextField();
        Dry_ctag = new javax.swing.JTextField();
        Dry_cemp = new javax.swing.JTextField();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        Dry_mah = new javax.swing.JComboBox<>();
        Dry_tag = new javax.swing.JComboBox<>();
        Dry_emp = new javax.swing.JComboBox<>();
        Dry_err = new javax.swing.JLabel();
        Dry_date = new javax.swing.JTextField();
        Dry_cmah = new javax.swing.JComboBox<>();
        AWQC1 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        AWQC1Back = new javax.swing.JButton();
        jSeparator45 = new javax.swing.JSeparator();
        AWQC1chk = new javax.swing.JButton();
        jSeparator47 = new javax.swing.JSeparator();
        AWQCchk15 = new javax.swing.JButton();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        AW_ctag = new javax.swing.JTextField();
        jLabel156 = new javax.swing.JLabel();
        AW_cemp = new javax.swing.JTextField();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        AW_time = new javax.swing.JTextField();
        AW_emp = new javax.swing.JComboBox<>();
        AW_tag = new javax.swing.JComboBox<>();
        AW_err = new javax.swing.JLabel();
        AW_date = new javax.swing.JTextField();
        Finish1 = new javax.swing.JPanel();
        Finish1back = new javax.swing.JButton();
        jSeparator38 = new javax.swing.JSeparator();
        Finish1add = new javax.swing.JButton();
        Finish1add1 = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        Fin_ctag = new javax.swing.JTextField();
        Fin_cemp = new javax.swing.JTextField();
        Fin_time = new javax.swing.JTextField();
        jLabel118 = new javax.swing.JLabel();
        Fin_emp = new javax.swing.JComboBox<>();
        Fin_tag = new javax.swing.JComboBox<>();
        Fin_err = new javax.swing.JLabel();
        Fin_date = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel141 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel142 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(940, 480));
        setMinimumSize(new java.awt.Dimension(940, 480));
        setPreferredSize(new java.awt.Dimension(940, 480));

        jPanel1.setMinimumSize(new java.awt.Dimension(940, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(940, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        Production.setMinimumSize(new java.awt.Dimension(940, 480));
        Production.setPreferredSize(new java.awt.Dimension(940, 480));
        Production.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BWQCButton.setBackground(new java.awt.Color(48, 48, 240));
        BWQCButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BWQCButton.setForeground(new java.awt.Color(255, 255, 255));
        BWQCButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-services-48.png"))); // NOI18N
        BWQCButton.setText("B/W QC");
        BWQCButton.setToolTipText("");
        BWQCButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BWQCButton.setMaximumSize(new java.awt.Dimension(120, 120));
        BWQCButton.setMinimumSize(new java.awt.Dimension(120, 120));
        BWQCButton.setPreferredSize(new java.awt.Dimension(120, 120));
        BWQCButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BWQCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCButtonActionPerformed(evt);
            }
        });
        Production.add(BWQCButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 120, 120));

        ChemicalsButton.setBackground(new java.awt.Color(48, 48, 240));
        ChemicalsButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ChemicalsButton.setForeground(new java.awt.Color(255, 255, 255));
        ChemicalsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-experiment-48.png"))); // NOI18N
        ChemicalsButton.setText("Chemicals");
        ChemicalsButton.setToolTipText("");
        ChemicalsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChemicalsButton.setMaximumSize(new java.awt.Dimension(120, 120));
        ChemicalsButton.setMinimumSize(new java.awt.Dimension(120, 120));
        ChemicalsButton.setPreferredSize(new java.awt.Dimension(120, 120));
        ChemicalsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ChemicalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChemicalsButtonActionPerformed(evt);
            }
        });
        Production.add(ChemicalsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 120, 120));

        WashingButton.setBackground(new java.awt.Color(48, 48, 240));
        WashingButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WashingButton.setForeground(new java.awt.Color(255, 255, 255));
        WashingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-washing-machine-50.png"))); // NOI18N
        WashingButton.setText("Washing");
        WashingButton.setToolTipText("");
        WashingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WashingButton.setMaximumSize(new java.awt.Dimension(120, 120));
        WashingButton.setMinimumSize(new java.awt.Dimension(120, 120));
        WashingButton.setPreferredSize(new java.awt.Dimension(120, 120));
        WashingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WashingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WashingButtonActionPerformed(evt);
            }
        });
        Production.add(WashingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 190, 120, 120));

        HydroButton.setBackground(new java.awt.Color(48, 48, 240));
        HydroButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        HydroButton.setForeground(new java.awt.Color(255, 255, 255));
        HydroButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-geothermal-50.png"))); // NOI18N
        HydroButton.setText("Hydro");
        HydroButton.setToolTipText("");
        HydroButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        HydroButton.setMaximumSize(new java.awt.Dimension(120, 120));
        HydroButton.setMinimumSize(new java.awt.Dimension(120, 120));
        HydroButton.setPreferredSize(new java.awt.Dimension(120, 120));
        HydroButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        HydroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HydroButtonActionPerformed(evt);
            }
        });
        Production.add(HydroButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 120, 120));

        WetQCButton.setBackground(new java.awt.Color(48, 48, 240));
        WetQCButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WetQCButton.setForeground(new java.awt.Color(255, 255, 255));
        WetQCButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-wet-50.png"))); // NOI18N
        WetQCButton.setText("Wet QC");
        WetQCButton.setToolTipText("");
        WetQCButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WetQCButton.setMaximumSize(new java.awt.Dimension(120, 120));
        WetQCButton.setMinimumSize(new java.awt.Dimension(120, 120));
        WetQCButton.setPreferredSize(new java.awt.Dimension(120, 120));
        WetQCButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WetQCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WetQCButtonActionPerformed(evt);
            }
        });
        Production.add(WetQCButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 190, 120, 120));

        DryingButton.setBackground(new java.awt.Color(48, 48, 240));
        DryingButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DryingButton.setForeground(new java.awt.Color(255, 255, 255));
        DryingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-hair-dryer-50.png"))); // NOI18N
        DryingButton.setText("Drying");
        DryingButton.setToolTipText("");
        DryingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DryingButton.setMaximumSize(new java.awt.Dimension(120, 120));
        DryingButton.setMinimumSize(new java.awt.Dimension(120, 120));
        DryingButton.setPreferredSize(new java.awt.Dimension(120, 120));
        DryingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        DryingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DryingButtonActionPerformed(evt);
            }
        });
        Production.add(DryingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 120, 120));

        AWQCButton.setBackground(new java.awt.Color(48, 48, 240));
        AWQCButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AWQCButton.setForeground(new java.awt.Color(255, 255, 255));
        AWQCButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-idea-50.png"))); // NOI18N
        AWQCButton.setText("A/W QC");
        AWQCButton.setToolTipText("");
        AWQCButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AWQCButton.setMaximumSize(new java.awt.Dimension(120, 120));
        AWQCButton.setMinimumSize(new java.awt.Dimension(120, 120));
        AWQCButton.setPreferredSize(new java.awt.Dimension(120, 120));
        AWQCButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AWQCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQCButtonActionPerformed(evt);
            }
        });
        Production.add(AWQCButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 120, 120));

        FinishingButton.setBackground(new java.awt.Color(48, 48, 240));
        FinishingButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FinishingButton.setForeground(new java.awt.Color(255, 255, 255));
        FinishingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-finish-flag-filled-50.png"))); // NOI18N
        FinishingButton.setText("Finishing");
        FinishingButton.setToolTipText("");
        FinishingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FinishingButton.setMaximumSize(new java.awt.Dimension(120, 120));
        FinishingButton.setMinimumSize(new java.awt.Dimension(120, 120));
        FinishingButton.setPreferredSize(new java.awt.Dimension(120, 120));
        FinishingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FinishingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinishingButtonActionPerformed(evt);
            }
        });
        Production.add(FinishingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 120, 120));

        jLayeredPane1.add(Production, "card18");

        BWQC.setBackground(new java.awt.Color(255, 255, 255));
        BWQC.setMinimumSize(new java.awt.Dimension(940, 480));
        BWQC.setPreferredSize(new java.awt.Dimension(940, 480));
        BWQC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("B/W QC");
        BWQC.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        BWQC.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable5.setRowHeight(35);
        jScrollPane5.setViewportView(jTable5);

        BWQC.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Show :");
        BWQC.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox25.setSelectedIndex(2);
        jComboBox25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox25ActionPerformed(evt);
            }
        });
        BWQC.add(jComboBox25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("entries");
        BWQC.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        search2.setText("Search..");
        search2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search2CaretUpdate(evt);
            }
        });
        search2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search2MouseClicked(evt);
            }
        });
        BWQC.add(search2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        BWQCBack.setBackground(new java.awt.Color(255, 51, 0));
        BWQCBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BWQCBack.setForeground(new java.awt.Color(255, 255, 255));
        BWQCBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        BWQCBack.setText("Go Back");
        BWQCBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCBackActionPerformed(evt);
            }
        });
        BWQC.add(BWQCBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        BWQCchk.setBackground(new java.awt.Color(0, 2, 240));
        BWQCchk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BWQCchk.setForeground(new java.awt.Color(255, 255, 255));
        BWQCchk.setText("Check B/W QC Tags");
        BWQCchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCchkActionPerformed(evt);
            }
        });
        BWQC.add(BWQCchk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton23.setBackground(new java.awt.Color(0, 0, 255));
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        BWQC.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        jButton24.setBackground(new java.awt.Color(0, 0, 255));
        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        BWQC.add(jButton24, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        total4.setForeground(new java.awt.Color(255, 255, 255));
        total4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BWQC.add(total4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("/");
        BWQC.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        page4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page4ActionPerformed(evt);
            }
        });
        BWQC.add(page4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jButton29.setBackground(new java.awt.Color(0, 0, 255));
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        BWQC.add(jButton29, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        jButton30.setBackground(new java.awt.Color(0, 0, 255));
        jButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        BWQC.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton12.setBackground(new java.awt.Color(71, 71, 116));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton12.setText("Print");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        BWQC.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        BWQC.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(BWQC, "card9");

        Chem.setBackground(new java.awt.Color(255, 255, 255));
        Chem.setMinimumSize(new java.awt.Dimension(940, 480));
        Chem.setPreferredSize(new java.awt.Dimension(940, 480));
        Chem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("Chemicals");
        Chem.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Chem.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable6.setRowHeight(35);
        jScrollPane6.setViewportView(jTable6);

        Chem.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setText("Show :");
        Chem.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox26.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox26.setSelectedIndex(2);
        jComboBox26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox26ActionPerformed(evt);
            }
        });
        Chem.add(jComboBox26, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setText("entries");
        Chem.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        search3.setText("Search..");
        search3.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search3CaretUpdate(evt);
            }
        });
        search3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search3MouseClicked(evt);
            }
        });
        Chem.add(search3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        ChemBack.setBackground(new java.awt.Color(255, 51, 0));
        ChemBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ChemBack.setForeground(new java.awt.Color(255, 255, 255));
        ChemBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        ChemBack.setText("Go Back");
        ChemBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChemBackActionPerformed(evt);
            }
        });
        Chem.add(ChemBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        chem_check.setBackground(new java.awt.Color(0, 2, 240));
        chem_check.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        chem_check.setForeground(new java.awt.Color(255, 255, 255));
        chem_check.setText("Check Chemicals Tag");
        chem_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chem_checkActionPerformed(evt);
            }
        });
        Chem.add(chem_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 190, 30));

        jButton31.setBackground(new java.awt.Color(0, 0, 255));
        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        Chem.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton33.setBackground(new java.awt.Color(0, 0, 255));
        jButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        Chem.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page5ActionPerformed(evt);
            }
        });
        Chem.add(page5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("/");
        Chem.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total5.setForeground(new java.awt.Color(255, 255, 255));
        total5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Chem.add(total5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton34.setBackground(new java.awt.Color(0, 0, 255));
        jButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        Chem.add(jButton34, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton35.setBackground(new java.awt.Color(0, 0, 255));
        jButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        Chem.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search4.setText("Search");
        Chem.add(search4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton13.setBackground(new java.awt.Color(71, 71, 116));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton13.setText("Print");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        Chem.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Chem.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Chem, "card9");

        Wash.setBackground(new java.awt.Color(255, 255, 255));
        Wash.setMinimumSize(new java.awt.Dimension(940, 480));
        Wash.setPreferredSize(new java.awt.Dimension(940, 480));
        Wash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Washing");
        Wash.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Wash.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable13.setRowHeight(35);
        jScrollPane7.setViewportView(jTable13);

        Wash.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Show :");
        Wash.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox27.setSelectedIndex(2);
        jComboBox27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox27ActionPerformed(evt);
            }
        });
        Wash.add(jComboBox27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel107.setForeground(new java.awt.Color(255, 255, 255));
        jLabel107.setText("entries");
        Wash.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        WashBack.setBackground(new java.awt.Color(255, 51, 0));
        WashBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        WashBack.setForeground(new java.awt.Color(255, 255, 255));
        WashBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        WashBack.setText("Go Back");
        WashBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WashBackActionPerformed(evt);
            }
        });
        Wash.add(WashBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        Washchk.setBackground(new java.awt.Color(0, 2, 240));
        Washchk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Washchk.setForeground(new java.awt.Color(255, 255, 255));
        Washchk.setText("Check Washing Tag");
        Washchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WashchkActionPerformed(evt);
            }
        });
        Wash.add(Washchk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton36.setBackground(new java.awt.Color(0, 0, 255));
        jButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        Wash.add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton37.setBackground(new java.awt.Color(0, 0, 255));
        jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        Wash.add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page6ActionPerformed(evt);
            }
        });
        Wash.add(page6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("/");
        Wash.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total6.setForeground(new java.awt.Color(255, 255, 255));
        total6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Wash.add(total6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton38.setBackground(new java.awt.Color(0, 0, 255));
        jButton38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        Wash.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton39.setBackground(new java.awt.Color(0, 0, 255));
        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        Wash.add(jButton39, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search5.setText("Search..");
        search5.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search5CaretUpdate(evt);
            }
        });
        search5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search5MouseClicked(evt);
            }
        });
        Wash.add(search5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton14.setBackground(new java.awt.Color(71, 71, 116));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton14.setText("Print");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        Wash.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Wash.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Wash, "card9");

        Hyd.setBackground(new java.awt.Color(255, 255, 255));
        Hyd.setMinimumSize(new java.awt.Dimension(940, 480));
        Hyd.setPreferredSize(new java.awt.Dimension(940, 480));
        Hyd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel109.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setText("Hydro");
        Hyd.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Hyd.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable14.setRowHeight(35);
        jScrollPane8.setViewportView(jTable14);

        Hyd.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setText("Show :");
        Hyd.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox28.setSelectedIndex(2);
        jComboBox28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox28ActionPerformed(evt);
            }
        });
        Hyd.add(jComboBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setText("entries");
        Hyd.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        HydBack.setBackground(new java.awt.Color(255, 51, 0));
        HydBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HydBack.setForeground(new java.awt.Color(255, 255, 255));
        HydBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        HydBack.setText("Go Back");
        HydBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HydBackActionPerformed(evt);
            }
        });
        Hyd.add(HydBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        Hydrochk.setBackground(new java.awt.Color(0, 2, 240));
        Hydrochk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Hydrochk.setForeground(new java.awt.Color(255, 255, 255));
        Hydrochk.setText("Check Hydro Tag");
        Hydrochk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HydrochkActionPerformed(evt);
            }
        });
        Hyd.add(Hydrochk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton40.setBackground(new java.awt.Color(0, 0, 255));
        jButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        Hyd.add(jButton40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton41.setBackground(new java.awt.Color(0, 0, 255));
        jButton41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        Hyd.add(jButton41, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page7ActionPerformed(evt);
            }
        });
        Hyd.add(page7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("/");
        Hyd.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total7.setForeground(new java.awt.Color(255, 255, 255));
        total7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hyd.add(total7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton42.setBackground(new java.awt.Color(0, 0, 255));
        jButton42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        Hyd.add(jButton42, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton43.setBackground(new java.awt.Color(0, 0, 255));
        jButton43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        Hyd.add(jButton43, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search6.setText("Search..");
        search6.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search6CaretUpdate(evt);
            }
        });
        search6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search6MouseClicked(evt);
            }
        });
        Hyd.add(search6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton15.setBackground(new java.awt.Color(71, 71, 116));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton15.setText("Print");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        Hyd.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Hyd.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Hyd, "card9");

        WetQC.setBackground(new java.awt.Color(255, 255, 255));
        WetQC.setMinimumSize(new java.awt.Dimension(940, 480));
        WetQC.setPreferredSize(new java.awt.Dimension(940, 480));
        WetQC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel114.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("Wet QC");
        WetQC.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        WetQC.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable15.setRowHeight(35);
        jScrollPane9.setViewportView(jTable15);

        WetQC.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText("Show :");
        WetQC.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox29.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox29.setSelectedIndex(2);
        jComboBox29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox29ActionPerformed(evt);
            }
        });
        WetQC.add(jComboBox29, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setText("entries");
        WetQC.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        WetQCBack.setBackground(new java.awt.Color(255, 51, 0));
        WetQCBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        WetQCBack.setForeground(new java.awt.Color(255, 255, 255));
        WetQCBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        WetQCBack.setText("Go Back");
        WetQCBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WetQCBackActionPerformed(evt);
            }
        });
        WetQC.add(WetQCBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        WetQCchk.setBackground(new java.awt.Color(0, 2, 240));
        WetQCchk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        WetQCchk.setForeground(new java.awt.Color(255, 255, 255));
        WetQCchk.setText("Check Wet QC Tag");
        WetQCchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WetQCchkActionPerformed(evt);
            }
        });
        WetQC.add(WetQCchk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton44.setBackground(new java.awt.Color(0, 0, 255));
        jButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });
        WetQC.add(jButton44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton45.setBackground(new java.awt.Color(0, 0, 255));
        jButton45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });
        WetQC.add(jButton45, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page8ActionPerformed(evt);
            }
        });
        WetQC.add(page8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("/");
        WetQC.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total8.setForeground(new java.awt.Color(255, 255, 255));
        total8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WetQC.add(total8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton46.setBackground(new java.awt.Color(0, 0, 255));
        jButton46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        WetQC.add(jButton46, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton47.setBackground(new java.awt.Color(0, 0, 255));
        jButton47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        WetQC.add(jButton47, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search10.setText("Search..");
        search10.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search10CaretUpdate(evt);
            }
        });
        search10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search10MouseClicked(evt);
            }
        });
        WetQC.add(search10, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton16.setBackground(new java.awt.Color(71, 71, 116));
        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton16.setText("Print");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        WetQC.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        WetQC.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(WetQC, "card9");

        Dry.setBackground(new java.awt.Color(255, 255, 255));
        Dry.setMinimumSize(new java.awt.Dimension(940, 480));
        Dry.setPreferredSize(new java.awt.Dimension(940, 480));
        Dry.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel119.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(255, 255, 255));
        jLabel119.setText("Drying");
        Dry.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Dry.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable16.setRowHeight(35);
        jScrollPane10.setViewportView(jTable16);

        Dry.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setText("Show :");
        Dry.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox30.setSelectedIndex(2);
        jComboBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox30ActionPerformed(evt);
            }
        });
        Dry.add(jComboBox30, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel122.setForeground(new java.awt.Color(255, 255, 255));
        jLabel122.setText("entries");
        Dry.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        search7.setText("Search..");
        search7.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search7CaretUpdate(evt);
            }
        });
        search7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search7MouseClicked(evt);
            }
        });
        Dry.add(search7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        DryBack.setBackground(new java.awt.Color(255, 51, 0));
        DryBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DryBack.setForeground(new java.awt.Color(255, 255, 255));
        DryBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        DryBack.setText("Go Back");
        DryBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DryBackActionPerformed(evt);
            }
        });
        Dry.add(DryBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        Drychk.setBackground(new java.awt.Color(0, 2, 240));
        Drychk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Drychk.setForeground(new java.awt.Color(255, 255, 255));
        Drychk.setText("Check Drying Tag");
        Drychk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrychkActionPerformed(evt);
            }
        });
        Dry.add(Drychk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton48.setBackground(new java.awt.Color(0, 0, 255));
        jButton48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });
        Dry.add(jButton48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton49.setBackground(new java.awt.Color(0, 0, 255));
        jButton49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        Dry.add(jButton49, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page9ActionPerformed(evt);
            }
        });
        Dry.add(page9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("/");
        Dry.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total9.setForeground(new java.awt.Color(255, 255, 255));
        total9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Dry.add(total9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton50.setBackground(new java.awt.Color(0, 0, 255));
        jButton50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });
        Dry.add(jButton50, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton51.setBackground(new java.awt.Color(0, 0, 255));
        jButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        Dry.add(jButton51, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search11.setText("Search");
        Dry.add(search11, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton17.setBackground(new java.awt.Color(71, 71, 116));
        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton17.setText("Print");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        Dry.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Dry.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Dry, "card9");

        AWQC.setBackground(new java.awt.Color(255, 255, 255));
        AWQC.setMinimumSize(new java.awt.Dimension(940, 480));
        AWQC.setPreferredSize(new java.awt.Dimension(940, 480));
        AWQC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel124.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 255, 255));
        jLabel124.setText("A/W QC");
        AWQC.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        AWQC.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable17.setRowHeight(35);
        jScrollPane11.setViewportView(jTable17);

        AWQC.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Show :");
        AWQC.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox31.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox31.setSelectedIndex(2);
        jComboBox31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox31ActionPerformed(evt);
            }
        });
        AWQC.add(jComboBox31, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setText("entries");
        AWQC.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        search8.setText("Search..");
        search8.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search8CaretUpdate(evt);
            }
        });
        search8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search8MouseClicked(evt);
            }
        });
        AWQC.add(search8, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        AWQCBack.setBackground(new java.awt.Color(255, 51, 0));
        AWQCBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AWQCBack.setForeground(new java.awt.Color(255, 255, 255));
        AWQCBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        AWQCBack.setText("Go Back");
        AWQCBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQCBackActionPerformed(evt);
            }
        });
        AWQC.add(AWQCBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        AWQCchk.setBackground(new java.awt.Color(0, 2, 240));
        AWQCchk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AWQCchk.setForeground(new java.awt.Color(255, 255, 255));
        AWQCchk.setText("Check A/W QC Tag");
        AWQCchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQCchkActionPerformed(evt);
            }
        });
        AWQC.add(AWQCchk, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        jButton52.setBackground(new java.awt.Color(0, 0, 255));
        jButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });
        AWQC.add(jButton52, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton53.setBackground(new java.awt.Color(0, 0, 255));
        jButton53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });
        AWQC.add(jButton53, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page10ActionPerformed(evt);
            }
        });
        AWQC.add(page10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("/");
        AWQC.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total10.setForeground(new java.awt.Color(255, 255, 255));
        total10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AWQC.add(total10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton54.setBackground(new java.awt.Color(0, 0, 255));
        jButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });
        AWQC.add(jButton54, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton55.setBackground(new java.awt.Color(0, 0, 255));
        jButton55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });
        AWQC.add(jButton55, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search12.setText("Search");
        AWQC.add(search12, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton18.setBackground(new java.awt.Color(71, 71, 116));
        jButton18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton18.setText("Print");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        AWQC.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        AWQC.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(AWQC, "card9");

        Finish.setBackground(new java.awt.Color(255, 255, 255));
        Finish.setMinimumSize(new java.awt.Dimension(940, 480));
        Finish.setPreferredSize(new java.awt.Dimension(940, 480));
        Finish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FinishMouseClicked(evt);
            }
        });
        Finish.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel130.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setText("Finishing");
        Finish.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Finish.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jScrollPane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane12MouseClicked(evt);
            }
        });

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date & Time", "Tag No", "Start Time", "End Time", "Emp No"
            }
        ));
        jTable18.setRowHeight(35);
        jTable18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable18MouseEntered(evt);
            }
        });
        jScrollPane12.setViewportView(jTable18);

        Finish.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setText("Show :");
        Finish.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jComboBox32.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jComboBox32.setSelectedIndex(2);
        jComboBox32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox32ActionPerformed(evt);
            }
        });
        Finish.add(jComboBox32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel153.setForeground(new java.awt.Color(255, 255, 255));
        jLabel153.setText("entries");
        Finish.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        search9.setText("Search..");
        search9.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                search9CaretUpdate(evt);
            }
        });
        search9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search9MouseClicked(evt);
            }
        });
        Finish.add(search9, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        FinBack.setBackground(new java.awt.Color(255, 51, 0));
        FinBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        FinBack.setForeground(new java.awt.Color(255, 255, 255));
        FinBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        FinBack.setText("Go Back");
        FinBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinBackActionPerformed(evt);
            }
        });
        Finish.add(FinBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        Finchk.setBackground(new java.awt.Color(0, 2, 240));
        Finchk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Finchk.setForeground(new java.awt.Color(255, 255, 255));
        Finchk.setText("Check Finishing Tag");
        Finchk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinchkActionPerformed(evt);
            }
        });
        Finish.add(Finchk, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 190, 30));

        jButton56.setBackground(new java.awt.Color(0, 0, 255));
        jButton56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });
        Finish.add(jButton56, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton57.setBackground(new java.awt.Color(0, 0, 255));
        jButton57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });
        Finish.add(jButton57, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        page11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page11ActionPerformed(evt);
            }
        });
        Finish.add(page11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("/");
        Finish.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        total11.setForeground(new java.awt.Color(255, 255, 255));
        total11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Finish.add(total11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jButton58.setBackground(new java.awt.Color(0, 0, 255));
        jButton58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });
        Finish.add(jButton58, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        jButton59.setBackground(new java.awt.Color(0, 0, 255));
        jButton59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });
        Finish.add(jButton59, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        search13.setText("Search");
        Finish.add(search13, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 140, 30));

        jButton1.setBackground(new java.awt.Color(71, 71, 116));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Send Email");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Finish.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, 180, 30));

        jButton19.setBackground(new java.awt.Color(71, 71, 116));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton19.setText("Print");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        Finish.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Finish.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Finish, "card9");

        BWQC1.setBackground(new java.awt.Color(255, 255, 255));
        BWQC1.setMinimumSize(new java.awt.Dimension(940, 480));
        BWQC1.setPreferredSize(new java.awt.Dimension(940, 480));
        BWQC1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BWQCBack1.setBackground(new java.awt.Color(255, 51, 0));
        BWQCBack1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BWQCBack1.setForeground(new java.awt.Color(255, 255, 255));
        BWQCBack1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        BWQCBack1.setText("Go Back");
        BWQCBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCBack1ActionPerformed(evt);
            }
        });
        BWQC1.add(BWQCBack1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        BWQC1.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel103.setText("Add before wash quality check tag ");
        BWQC1.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        BWQCchk3add.setBackground(new java.awt.Color(0, 2, 240));
        BWQCchk3add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BWQCchk3add.setForeground(new java.awt.Color(255, 255, 255));
        BWQCchk3add.setText("Add");
        BWQCchk3add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCchk3addActionPerformed(evt);
            }
        });
        BWQC1.add(BWQCchk3add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));
        BWQC1.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        BWQCchk4addmanually.setBackground(new java.awt.Color(0, 2, 240));
        BWQCchk4addmanually.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BWQCchk4addmanually.setForeground(new java.awt.Color(255, 255, 255));
        BWQCchk4addmanually.setText("Add Manually");
        BWQCchk4addmanually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWQCchk4addmanuallyActionPerformed(evt);
            }
        });
        BWQC1.add(BWQCchk4addmanually, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        jLabel8.setText("Tag Number");
        BWQC1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setText("Enter B/W Qc Information");
        BWQC1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel22.setText("Tag Number");
        BWQC1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel23.setText("Employee No");
        BWQC1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Barcode Read");
        BWQC1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, 50));

        jLabel7.setText("Time");
        BWQC1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        jLabel6.setText("Employee No");
        BWQC1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, -1));

        jLabel5.setText("Date");
        BWQC1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        BW_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BW_ctagFocusGained(evt);
            }
        });
        BWQC1.add(BW_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 390, 30));

        BW_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BW_cempFocusGained(evt);
            }
        });
        BWQC1.add(BW_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 390, 30));
        BWQC1.add(BW_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 340, 30));

        BW_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BW_tagActionPerformed(evt);
            }
        });
        BWQC1.add(BW_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 340, 30));

        BW_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BW_empActionPerformed(evt);
            }
        });
        BWQC1.add(BW_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 340, 30));

        BW_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BW_err.setForeground(new java.awt.Color(255, 0, 0));
        BW_err.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BWQC1.add(BW_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        BWQC1.add(BW_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 340, 340, 30));

        jLayeredPane1.add(BWQC1, "card9");

        Chem1.setBackground(new java.awt.Color(255, 255, 255));
        Chem1.setMinimumSize(new java.awt.Dimension(940, 480));
        Chem1.setPreferredSize(new java.awt.Dimension(940, 480));
        Chem1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addchemback.setBackground(new java.awt.Color(255, 51, 0));
        addchemback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addchemback.setForeground(new java.awt.Color(255, 255, 255));
        addchemback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        addchemback.setText("Go Back");
        addchemback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addchembackActionPerformed(evt);
            }
        });
        Chem1.add(addchemback, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        Chem1.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        add_chem_man_add.setBackground(new java.awt.Color(0, 2, 240));
        add_chem_man_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_chem_man_add.setForeground(new java.awt.Color(255, 255, 255));
        add_chem_man_add.setText("Add manually");
        add_chem_man_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_chem_man_addActionPerformed(evt);
            }
        });
        Chem1.add(add_chem_man_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        add_che_add.setBackground(new java.awt.Color(0, 2, 240));
        add_che_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_che_add.setForeground(new java.awt.Color(255, 255, 255));
        add_che_add.setText("Add");
        add_che_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_che_addActionPerformed(evt);
            }
        });
        Chem1.add(add_che_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));

        jLabel24.setText("  Tag Number");
        Chem1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel25.setText("Barcode Reader");
        Chem1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel26.setText("Time");
        Chem1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        jLabel27.setText("Tag Number");
        Chem1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel28.setText("Enter deptChemicals Information");
        Chem1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel29.setText("   Employee No");
        Chem1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        jLabel37.setText("Employee No");
        Chem1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        Chem_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Chem_ctagFocusGained(evt);
            }
        });
        Chem1.add(Chem_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 390, 30));

        Chem_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Chem_cempFocusGained(evt);
            }
        });
        Chem1.add(Chem_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 390, 30));
        Chem1.add(Chem_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 220, 30));

        jLabel105.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel105.setText("Add chemicals tag");
        Chem1.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        Chem_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Chem_empActionPerformed(evt);
            }
        });
        Chem1.add(Chem_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 220, 30));

        Chem_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Chem_tagActionPerformed(evt);
            }
        });
        Chem1.add(Chem_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 220, 30));

        Chem_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Chem_err.setForeground(new java.awt.Color(255, 0, 0));
        Chem1.add(Chem_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        Chem1.add(Chem_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 220, 30));

        jLabel1.setText("Date");
        Chem1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        jLayeredPane1.add(Chem1, "card9");

        Wash1.setBackground(new java.awt.Color(255, 255, 255));
        Wash1.setMinimumSize(new java.awt.Dimension(940, 480));
        Wash1.setPreferredSize(new java.awt.Dimension(940, 480));
        Wash1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add_wash_back.setBackground(new java.awt.Color(255, 51, 0));
        add_wash_back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add_wash_back.setForeground(new java.awt.Color(255, 255, 255));
        add_wash_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        add_wash_back.setText("Go Back");
        add_wash_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_wash_backActionPerformed(evt);
            }
        });
        Wash1.add(add_wash_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        Wash1.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        Add_wash_manu_add.setBackground(new java.awt.Color(0, 2, 240));
        Add_wash_manu_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Add_wash_manu_add.setForeground(new java.awt.Color(255, 255, 255));
        Add_wash_manu_add.setText("Add Manually");
        Add_wash_manu_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_wash_manu_addActionPerformed(evt);
            }
        });
        Wash1.add(Add_wash_manu_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        add_wash_add.setBackground(new java.awt.Color(0, 2, 240));
        add_wash_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_wash_add.setForeground(new java.awt.Color(255, 255, 255));
        add_wash_add.setText("Add");
        add_wash_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_wash_addActionPerformed(evt);
            }
        });
        Wash1.add(add_wash_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));

        jLabel20.setText(" Tag Number");
        Wash1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel39.setText("Barcode Reader");
        Wash1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel43.setText("Date");
        Wash1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        jLabel45.setText("Tag Number");
        Wash1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel46.setText("Enter Washing Information");
        Wash1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel48.setText("Time");
        Wash1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        jLabel49.setText("Employee No");
        Wash1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, -1));

        jLabel52.setText("Employee No");
        Wash1.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        Wash_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Wash_ctagFocusGained(evt);
            }
        });
        Wash1.add(Wash_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 390, 30));

        Wash_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Wash_cempFocusGained(evt);
            }
        });
        Wash1.add(Wash_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 390, 30));
        Wash1.add(Wash_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 230, 30));

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel98.setText("Add washing tag");
        Wash1.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Wash_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wash_empActionPerformed(evt);
            }
        });
        Wash1.add(Wash_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 230, 30));

        Wash_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wash_tagActionPerformed(evt);
            }
        });
        Wash1.add(Wash_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 230, 30));

        Wash_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Wash_err.setForeground(new java.awt.Color(255, 0, 0));
        Wash1.add(Wash_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        Wash1.add(Wash_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 340, 230, 30));

        jLayeredPane1.add(Wash1, "card9");

        Hyd1.setBackground(new java.awt.Color(255, 255, 255));
        Hyd1.setMinimumSize(new java.awt.Dimension(940, 480));
        Hyd1.setPreferredSize(new java.awt.Dimension(940, 480));
        Hyd1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add_hydro_back.setBackground(new java.awt.Color(255, 51, 0));
        add_hydro_back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add_hydro_back.setForeground(new java.awt.Color(255, 255, 255));
        add_hydro_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        add_hydro_back.setText("Go Back");
        add_hydro_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_hydro_backActionPerformed(evt);
            }
        });
        Hyd1.add(add_hydro_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        Hyd1.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel100.setText("Add hydro tag");
        Hyd1.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        Hyd1.add(Hydro_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 240, 30));

        Hydro_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Hydro_cempFocusGained(evt);
            }
        });
        Hyd1.add(Hydro_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 390, 30));

        Hydro_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Hydro_ctagFocusGained(evt);
            }
        });
        Hyd1.add(Hydro_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 390, 30));

        jLabel76.setText("Employee No");
        Hyd1.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        jLabel75.setText("Employee No ");
        Hyd1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, -1, -1));

        jLabel74.setText("Time");
        Hyd1.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, -1, -1));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel73.setText("Enter Hydro Information");
        Hyd1.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel72.setText("Tag Number");
        Hyd1.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, -1, -1));

        jLabel71.setText("Date");
        Hyd1.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel68.setText("Barcode reader");
        Hyd1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel56.setText("  Tag Number");
        Hyd1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        add_hydro_add.setBackground(new java.awt.Color(0, 2, 240));
        add_hydro_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_hydro_add.setForeground(new java.awt.Color(255, 255, 255));
        add_hydro_add.setText("Add");
        add_hydro_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_hydro_addActionPerformed(evt);
            }
        });
        Hyd1.add(add_hydro_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));

        add_hydro_manu_add.setBackground(new java.awt.Color(0, 2, 240));
        add_hydro_manu_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_hydro_manu_add.setForeground(new java.awt.Color(255, 255, 255));
        add_hydro_manu_add.setText("Add Manually");
        add_hydro_manu_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_hydro_manu_addActionPerformed(evt);
            }
        });
        Hyd1.add(add_hydro_manu_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        Hydro_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hydro_empActionPerformed(evt);
            }
        });
        Hyd1.add(Hydro_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, 240, 30));

        Hydro_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hydro_tagActionPerformed(evt);
            }
        });
        Hyd1.add(Hydro_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 240, 30));

        Hydro_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Hydro_err.setForeground(new java.awt.Color(255, 0, 0));
        Hyd1.add(Hydro_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        Hyd1.add(Hydro_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 340, 240, 30));

        jLayeredPane1.add(Hyd1, "card9");

        WetQC1.setBackground(new java.awt.Color(255, 255, 255));
        WetQC1.setMinimumSize(new java.awt.Dimension(940, 480));
        WetQC1.setPreferredSize(new java.awt.Dimension(940, 480));
        WetQC1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cheak_wet_qc_back.setBackground(new java.awt.Color(255, 51, 0));
        cheak_wet_qc_back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cheak_wet_qc_back.setForeground(new java.awt.Color(255, 255, 255));
        cheak_wet_qc_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        cheak_wet_qc_back.setText("Go Back");
        cheak_wet_qc_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cheak_wet_qc_backActionPerformed(evt);
            }
        });
        WetQC1.add(cheak_wet_qc_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        WetQC1.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        Wet_addMan.setBackground(new java.awt.Color(0, 2, 240));
        Wet_addMan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Wet_addMan.setForeground(new java.awt.Color(255, 255, 255));
        Wet_addMan.setText("Add Manually");
        Wet_addMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wet_addManActionPerformed(evt);
            }
        });
        WetQC1.add(Wet_addMan, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        Wet_cAdd.setBackground(new java.awt.Color(0, 2, 240));
        Wet_cAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Wet_cAdd.setForeground(new java.awt.Color(255, 255, 255));
        Wet_cAdd.setText("Add");
        Wet_cAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wet_cAddActionPerformed(evt);
            }
        });
        WetQC1.add(Wet_cAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));

        jLabel77.setText("Tag Number");
        WetQC1.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, 10));

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel87.setText("Barcode Reader");
        WetQC1.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel88.setText("Date");
        WetQC1.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        jLabel93.setText("Tag Number");
        WetQC1.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel120.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel120.setText("Enter wet Qc Information");
        WetQC1.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel123.setText("Time");
        WetQC1.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        jLabel125.setText("Employee No");
        WetQC1.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        jLabel128.setText("Employee No");
        WetQC1.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        Wet_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Wet_ctagFocusGained(evt);
            }
        });
        WetQC1.add(Wet_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 390, 30));

        Wet_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Wet_cempFocusGained(evt);
            }
        });
        WetQC1.add(Wet_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 390, 30));
        WetQC1.add(Wet_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 220, 30));

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel115.setText("Add wet quality check tag");
        WetQC1.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Wet_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wet_empActionPerformed(evt);
            }
        });
        WetQC1.add(Wet_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 220, 30));

        Wet_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wet_tagActionPerformed(evt);
            }
        });
        WetQC1.add(Wet_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 220, 30));

        Wet_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Wet_err.setForeground(new java.awt.Color(255, 0, 0));
        WetQC1.add(Wet_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        WetQC1.add(Wet_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 340, 220, 30));

        jLayeredPane1.add(WetQC1, "card9");

        Dry1.setBackground(new java.awt.Color(255, 255, 255));
        Dry1.setMinimumSize(new java.awt.Dimension(940, 480));
        Dry1.setPreferredSize(new java.awt.Dimension(940, 480));
        Dry1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add_dry_back.setBackground(new java.awt.Color(255, 51, 0));
        add_dry_back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add_dry_back.setForeground(new java.awt.Color(255, 255, 255));
        add_dry_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        add_dry_back.setText("Go Back");
        add_dry_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_dry_backActionPerformed(evt);
            }
        });
        Dry1.add(add_dry_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        Dry1.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        add_dry_manu_add.setBackground(new java.awt.Color(0, 2, 240));
        add_dry_manu_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_dry_manu_add.setForeground(new java.awt.Color(255, 255, 255));
        add_dry_manu_add.setText("Add Manually");
        add_dry_manu_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_dry_manu_addActionPerformed(evt);
            }
        });
        Dry1.add(add_dry_manu_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        add_dry_add.setBackground(new java.awt.Color(0, 2, 240));
        add_dry_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_dry_add.setForeground(new java.awt.Color(255, 255, 255));
        add_dry_add.setText("Add");
        add_dry_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_dry_addActionPerformed(evt);
            }
        });
        Dry1.add(add_dry_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 200, 190, 30));

        jLabel132.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel132.setText("Barcode Reader");
        Dry1.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel134.setText(" Tag Number");
        Dry1.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, -1));

        jLabel135.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel135.setText("Enter Drying Information");
        Dry1.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel136.setText("  Drying Maching No");
        Dry1.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, -1, -1));

        jLabel137.setText("Date");
        Dry1.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jLabel138.setText("Drying Machine No");
        Dry1.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, -1, -1));

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel129.setText("Add drying tag");
        Dry1.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel149.setText("Employee No");
        Dry1.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, -1, -1));

        jLabel150.setText("Time");
        Dry1.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, -1, -1));
        Dry1.add(Dry_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 220, 30));

        Dry_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Dry_ctagFocusGained(evt);
            }
        });
        Dry1.add(Dry_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 260, 30));

        Dry_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Dry_cempFocusGained(evt);
            }
        });
        Dry1.add(Dry_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 260, 30));

        jLabel151.setText("Employee No");
        Dry1.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        jLabel152.setText("Tag number");
        Dry1.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        Dry_mah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M001", "M002" }));
        Dry_mah.setSelectedIndex(-1);
        Dry_mah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dry_mahActionPerformed(evt);
            }
        });
        Dry1.add(Dry_mah, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 220, 30));

        Dry_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dry_tagActionPerformed(evt);
            }
        });
        Dry1.add(Dry_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 220, 30));

        Dry_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dry_empActionPerformed(evt);
            }
        });
        Dry1.add(Dry_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 220, 30));

        Dry_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Dry_err.setForeground(new java.awt.Color(255, 0, 0));
        Dry_err.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Dry1.add(Dry_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 190, 40));
        Dry1.add(Dry_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 220, 30));

        Dry_cmah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M001", "M002" }));
        Dry_cmah.setSelectedIndex(-1);
        Dry_cmah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dry_cmahActionPerformed(evt);
            }
        });
        Dry1.add(Dry_cmah, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 220, 30));

        jLayeredPane1.add(Dry1, "card9");

        AWQC1.setBackground(new java.awt.Color(255, 255, 255));
        AWQC1.setMinimumSize(new java.awt.Dimension(940, 480));
        AWQC1.setPreferredSize(new java.awt.Dimension(940, 480));
        AWQC1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel139.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel139.setText("Add after wash quality check tag");
        AWQC1.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        AWQC1Back.setBackground(new java.awt.Color(255, 51, 0));
        AWQC1Back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AWQC1Back.setForeground(new java.awt.Color(255, 255, 255));
        AWQC1Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        AWQC1Back.setText("Go Back");
        AWQC1Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQC1BackActionPerformed(evt);
            }
        });
        AWQC1.add(AWQC1Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        AWQC1.add(jSeparator45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        AWQC1chk.setBackground(new java.awt.Color(0, 2, 240));
        AWQC1chk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AWQC1chk.setForeground(new java.awt.Color(255, 255, 255));
        AWQC1chk.setText("Add");
        AWQC1chk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQC1chkActionPerformed(evt);
            }
        });
        AWQC1.add(AWQC1chk, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));
        AWQC1.add(jSeparator47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        AWQCchk15.setBackground(new java.awt.Color(0, 2, 240));
        AWQCchk15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AWQCchk15.setForeground(new java.awt.Color(255, 255, 255));
        AWQCchk15.setText("Add Manualy");
        AWQCchk15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AWQCchk15ActionPerformed(evt);
            }
        });
        AWQC1.add(AWQCchk15, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 410, 190, 30));

        jLabel154.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel154.setText("Barcode Reader");
        AWQC1.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel155.setText("Tag Number");
        AWQC1.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, 10));

        AW_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AW_ctagFocusGained(evt);
            }
        });
        AWQC1.add(AW_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 390, 30));

        jLabel156.setText("Employee No");
        AWQC1.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        AW_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AW_cempFocusGained(evt);
            }
        });
        AWQC1.add(AW_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 390, 30));

        jLabel157.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel157.setText("Enter AW QC Information");
        AWQC1.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel158.setText("Tag Number");
        AWQC1.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel159.setText("Date");
        AWQC1.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        jLabel160.setText("Time");
        AWQC1.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        jLabel161.setText("Employee No");
        AWQC1.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));
        AWQC1.add(AW_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 220, 30));

        AW_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AW_empActionPerformed(evt);
            }
        });
        AWQC1.add(AW_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 220, 30));

        AW_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AW_tagActionPerformed(evt);
            }
        });
        AWQC1.add(AW_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 220, 30));

        AW_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AW_err.setForeground(new java.awt.Color(255, 0, 0));
        AWQC1.add(AW_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 380, 190, -1));
        AWQC1.add(AW_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 340, 220, 30));

        jLayeredPane1.add(AWQC1, "card9");

        Finish1.setBackground(new java.awt.Color(255, 255, 255));
        Finish1.setMinimumSize(new java.awt.Dimension(940, 480));
        Finish1.setPreferredSize(new java.awt.Dimension(940, 480));
        Finish1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Finish1back.setBackground(new java.awt.Color(255, 51, 0));
        Finish1back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Finish1back.setForeground(new java.awt.Color(255, 255, 255));
        Finish1back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/Back.png"))); // NOI18N
        Finish1back.setText("Go Back");
        Finish1back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Finish1backActionPerformed(evt);
            }
        });
        Finish1.add(Finish1back, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));
        Finish1.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        Finish1add.setBackground(new java.awt.Color(0, 2, 240));
        Finish1add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Finish1add.setForeground(new java.awt.Color(255, 255, 255));
        Finish1add.setText("Add");
        Finish1add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Finish1addActionPerformed(evt);
            }
        });
        Finish1.add(Finish1add, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 190, 30));

        Finish1add1.setBackground(new java.awt.Color(0, 2, 240));
        Finish1add1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Finish1add1.setForeground(new java.awt.Color(255, 255, 255));
        Finish1add1.setText("Add Manually");
        Finish1add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Finish1add1ActionPerformed(evt);
            }
        });
        Finish1.add(Finish1add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, 190, 30));

        jLabel95.setText("Tag Number");
        Finish1.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, 20));

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel108.setText("Barcode Reader");
        Finish1.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel110.setText("Date");
        Finish1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        jLabel113.setText("Tag Number");
        Finish1.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel167.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel167.setText("Enter Finishing Information");
        Finish1.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel168.setText("Time");
        Finish1.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        jLabel169.setText("Employee No");
        Finish1.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        jLabel170.setText("Employee No");
        Finish1.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        Fin_ctag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Fin_ctagFocusGained(evt);
            }
        });
        Finish1.add(Fin_ctag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 390, 30));

        Fin_cemp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Fin_cempFocusGained(evt);
            }
        });
        Finish1.add(Fin_cemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 390, 30));
        Finish1.add(Fin_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 220, 30));

        jLabel118.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel118.setText("Add finishing tag");
        Finish1.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Fin_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Fin_empActionPerformed(evt);
            }
        });
        Finish1.add(Fin_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 220, 30));

        Fin_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Fin_tagActionPerformed(evt);
            }
        });
        Finish1.add(Fin_tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 220, 30));

        Fin_err.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Fin_err.setForeground(new java.awt.Color(255, 0, 0));
        Finish1.add(Fin_err, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 190, -1));
        Finish1.add(Fin_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 340, 220, 30));

        jLayeredPane1.add(Finish1, "card9");

        jPanel1.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -5, 950, 490));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Production Process");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pre-Production Process");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 10, 30));

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText("PROCESS");
        jPanel1.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 390, 40));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 82, 690, 10));

        jLabel142.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Production.png"))); // NOI18N
        jLabel142.setText("jLabel3");
        jPanel1.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 50, 40));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 950, 490));

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

    private void BWQCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCButtonActionPerformed
        // TODO add your handling code here:
        BWQC.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BWQCButtonActionPerformed

    private void ChemicalsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChemicalsButtonActionPerformed
        // TODO add your handling code here:
        Chem.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ChemicalsButtonActionPerformed

    private void WashingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WashingButtonActionPerformed
        // TODO add your handling code here:
        Wash.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_WashingButtonActionPerformed

    private void HydroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HydroButtonActionPerformed
        // TODO add your handling code here:
        Hyd.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_HydroButtonActionPerformed

    private void WetQCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WetQCButtonActionPerformed
        // TODO add your handling code here:
        WetQC.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_WetQCButtonActionPerformed

    private void DryingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DryingButtonActionPerformed
        // TODO add your handling code here:
        Dry.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DryingButtonActionPerformed

    private void AWQCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQCButtonActionPerformed
        // TODO add your handling code here:
        AWQC.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AWQCButtonActionPerformed

    private void FinishingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinishingButtonActionPerformed
        // TODO add your handling code here:
        Finish.setVisible(true);
        Production.setVisible(false);
        Home.pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addtoProcesschkTable();
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FinishingButtonActionPerformed

    private void BWQCBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        BWQC.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_BWQCBackActionPerformed

    private void BWQCchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCchkActionPerformed
        BWQC1.setVisible(true);
        BWQC.setVisible(false);
        try {
            BW_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN bwqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                BW_tag.addItem(rs.getString("Tag_No"));
            }
            BW_tag.setSelectedIndex(-1);

            BW_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                BW_emp.addItem(rs1.getString("empNo"));
            }
            BW_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        BW_date.setText(String.valueOf(java.time.LocalDate.now()));
        BW_date.setEnabled(false);
        BW_time.setText(String.valueOf(java.time.LocalTime.now()));
        BW_time.setEnabled(false);
    }//GEN-LAST:event_BWQCchkActionPerformed

    private void ChemBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChemBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        Chem.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_ChemBackActionPerformed

    private void chem_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chem_checkActionPerformed
        // TODO add your handling code here:
        Chem1.setVisible(true);
        Chem.setVisible(false);
        try {
            Chem_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN chemicals b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Chem_tag.addItem(rs.getString("Tag_No"));
            }
            Chem_tag.setSelectedIndex(-1);

            Chem_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Chem_emp.addItem(rs1.getString("empNo"));
            }
            Chem_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Chem_date.setText(String.valueOf(java.time.LocalDate.now()));
        Chem_date.setEnabled(false);
        Chem_time.setText(String.valueOf(java.time.LocalTime.now()));
        Chem_time.setEnabled(false);
    }//GEN-LAST:event_chem_checkActionPerformed

    private void WashBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WashBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        Wash.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_WashBackActionPerformed

    private void WashchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WashchkActionPerformed
        // TODO add your handling code here:
        Wash1.setVisible(true);
        Wash.setVisible(false);
        try {
            Wash_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN wash b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Wash_tag.addItem(rs.getString("Tag_No"));
            }
            Wash_tag.setSelectedIndex(-1);

            Wash_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Wash_emp.addItem(rs1.getString("empNo"));
            }
            Wash_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Wash_date.setText(String.valueOf(java.time.LocalDate.now()));
        Wash_date.setEnabled(false);
        Wash_time.setText(String.valueOf(java.time.LocalTime.now()));
        Wash_time.setEnabled(false);
    }//GEN-LAST:event_WashchkActionPerformed

    private void HydBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HydBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        Hyd.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_HydBackActionPerformed

    private void HydrochkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HydrochkActionPerformed
        // TODO add your handling code here:
        Hyd1.setVisible(true);
        Hyd.setVisible(false);
        try {
            Hydro_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN hydro b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Hydro_tag.addItem(rs.getString("Tag_No"));
            }
            Hydro_tag.setSelectedIndex(-1);

            Hydro_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Hydro_emp.addItem(rs1.getString("empNo"));
            }
            Hydro_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Hydro_date.setText(String.valueOf(java.time.LocalDate.now()));
        Hydro_date.setEnabled(false);
        Hydro_time.setText(String.valueOf(java.time.LocalTime.now()));
        Hydro_time.setEnabled(false);
    }//GEN-LAST:event_HydrochkActionPerformed

    private void WetQCBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WetQCBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        WetQC.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_WetQCBackActionPerformed

    private void WetQCchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WetQCchkActionPerformed
        // TODO add your handling code here:
        WetQC1.setVisible(true);
        WetQC.setVisible(false);
        try {
            Wet_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN wetqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Wet_tag.addItem(rs.getString("Tag_No"));
            }
            Wet_tag.setSelectedIndex(-1);

            Wet_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Wet_emp.addItem(rs1.getString("empNo"));
            }
            Wet_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Wet_date.setText(String.valueOf(java.time.LocalDate.now()));
        Wet_date.setEnabled(false);
        Wet_time.setText(String.valueOf(java.time.LocalTime.now()));
        Wet_time.setEnabled(false);
    }//GEN-LAST:event_WetQCchkActionPerformed

    private void DryBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DryBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        Dry.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_DryBackActionPerformed

    private void DrychkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrychkActionPerformed
        // TODO add your handling code here:
        Dry1.setVisible(true);
        Dry.setVisible(false);
        try {
            Dry_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN dry b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Dry_tag.addItem(rs.getString("Tag_No"));
            }
            Dry_tag.setSelectedIndex(-1);

            Dry_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Dry_emp.addItem(rs1.getString("empNo"));
            }
            Dry_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Dry_date.setText(String.valueOf(java.time.LocalDate.now()));
        Dry_date.setEnabled(false);
        Dry_time.setText(String.valueOf(java.time.LocalTime.now()));
        Dry_time.setEnabled(false);
    }//GEN-LAST:event_DrychkActionPerformed

    private void AWQCBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQCBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        AWQC.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_AWQCBackActionPerformed

    private void AWQCchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQCchkActionPerformed
        // TODO add your handling code here:
        AWQC1.setVisible(true);
        AWQC.setVisible(false);
        try {
            AW_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN awqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                AW_tag.addItem(rs.getString("Tag_No"));
            }
            AW_tag.setSelectedIndex(-1);

            AW_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                AW_emp.addItem(rs1.getString("empNo"));
            }
            AW_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        AW_date.setText(String.valueOf(java.time.LocalDate.now()));
        AW_date.setEnabled(false);
        AW_time.setText(String.valueOf(java.time.LocalTime.now()));
        AW_time.setEnabled(false);
    }//GEN-LAST:event_AWQCchkActionPerformed

    private void FinBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinBackActionPerformed
        // TODO add your handling code here:
        Home.pro.setVisible(false);
        Production.setVisible(true);
        Finish.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_FinBackActionPerformed

    private void FinchkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinchkActionPerformed
        // TODO add your handling code here:
        Finish.setVisible(false);
        Finish1.setVisible(true);
        try {
            Fin_tag.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN finish b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
            while (rs.next()) {
                Fin_tag.addItem(rs.getString("Tag_No"));
            }
            Fin_tag.setSelectedIndex(-1);

            Fin_emp.removeAllItems();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs1.next()) {
                Fin_emp.addItem(rs1.getString("empNo"));
            }
            Fin_emp.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        Fin_date.setText(String.valueOf(java.time.LocalDate.now()));
        Fin_date.setEnabled(false);
        Fin_time.setText(String.valueOf(java.time.LocalTime.now()));
        Fin_time.setEnabled(false);
    }//GEN-LAST:event_FinchkActionPerformed

    private void BWQCBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCBack1ActionPerformed
        // TODO add your handling code here:
        BWQC1.setVisible(false);
        BWQC.setVisible(true);
    }//GEN-LAST:event_BWQCBack1ActionPerformed

    private void addchembackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addchembackActionPerformed
        // TODO add your handling code here:
        Chem1.setVisible(false);
        Chem.setVisible(true);
    }//GEN-LAST:event_addchembackActionPerformed

    private void add_wash_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_wash_backActionPerformed
        // TODO add your handling code here:
        Wash1.setVisible(false);
        Wash.setVisible(true);
    }//GEN-LAST:event_add_wash_backActionPerformed

    private void add_hydro_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_hydro_backActionPerformed
        Hyd1.setVisible(false);
        Hyd.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_add_hydro_backActionPerformed

    private void cheak_wet_qc_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cheak_wet_qc_backActionPerformed
        WetQC1.setVisible(false);
        WetQC.setVisible(true); // TODO add your handling code here:
    }//GEN-LAST:event_cheak_wet_qc_backActionPerformed

    private void add_dry_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_dry_backActionPerformed
        Dry1.setVisible(false);
        Dry.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_add_dry_backActionPerformed

    private void AWQC1BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQC1BackActionPerformed
        // TODO add your handling code here:
        AWQC.setVisible(true);
        AWQC1.setVisible(false);
    }//GEN-LAST:event_AWQC1BackActionPerformed

    private void Finish1backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Finish1backActionPerformed
        try {
            // TODO add your handling code here:
            Finish.setVisible(true);
            Finish1.setVisible(false);
            updateMax("SELECT COUNT(*) AS count FROM finish", total4, page4);
            addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Finish1backActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        Home.process.setVisible(true);
        Home.pro.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        Home.pro.setVisible(true);
        Home.process.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        pg = 1;
        page4.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
            addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        if (pg > 1) {
            pg--;
            page4.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
            addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void page4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page4ActionPerformed
        int p = Integer.parseInt((String) page4.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
                addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page4ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        if (pg < maxPages) {
            pg++;
            page4.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
            addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        pg = maxPages;
        page4.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
            addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jComboBox25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox25ActionPerformed
        if (jComboBox25.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox25.setSelectedItem("15");
            pg = 1;
            page4.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
                addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox25.getSelectedItem());
            pg = 1;
            page4.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
                addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox25ActionPerformed

    private void search2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search2MouseClicked
        search2.setText(null);
    }//GEN-LAST:event_search2MouseClicked

    private void search2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search2CaretUpdate
        String sh = search2.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
                addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM bwqc WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable5);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search2CaretUpdate

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        pg = 1;
        page5.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
            addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        if (pg > 1) {
            pg--;
            page5.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
            addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton33ActionPerformed

    private void page5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page5ActionPerformed
        int p = Integer.parseInt((String) page5.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
                addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page5ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        if (pg < maxPages) {
            pg++;
            page5.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
            addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        pg = maxPages;
        page5.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
            addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jComboBox26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox26ActionPerformed
        if (jComboBox26.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox26.setSelectedItem("15");
            pg = 1;
            page5.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
                addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox26.getSelectedItem());
            pg = 1;
            page5.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
                addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox26ActionPerformed

    private void search3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search3MouseClicked
        search3.setText(null);
    }//GEN-LAST:event_search3MouseClicked

    private void search3CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search3CaretUpdate
        String sh = search2.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
                addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM chemicals WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable6);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search3CaretUpdate

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        pg = 1;
        page6.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
            addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        if (pg > 1) {
            pg--;
            page6.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
            addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton37ActionPerformed

    private void page6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page6ActionPerformed
        int p = Integer.parseInt((String) page6.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
                addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page6ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        if (pg < maxPages) {
            pg++;
            page6.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
            addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        pg = maxPages;
        page6.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
            addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jComboBox27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox27ActionPerformed
        if (jComboBox27.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox27.setSelectedItem("15");
            pg = 1;
            page6.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
                addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox27.getSelectedItem());
            pg = 1;
            page6.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
                addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox27ActionPerformed

    private void search5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search5MouseClicked
        search5.setText(null);
    }//GEN-LAST:event_search5MouseClicked

    private void search5CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search5CaretUpdate
        String sh = search5.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
                addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM wash WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable13);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search5CaretUpdate

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        pg = 1;
        page7.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
            addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        if (pg > 1) {
            pg--;
            page7.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
            addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton41ActionPerformed

    private void page7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page7ActionPerformed
        int p = Integer.parseInt((String) page7.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
                addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_page7ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        if (pg < maxPages) {
            pg++;
            page7.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
            addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        pg = maxPages;
        page7.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
            addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jComboBox28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox28ActionPerformed
        if (jComboBox28.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox28.setSelectedItem("15");
            pg = 1;
            page7.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
                addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox28.getSelectedItem());
            pg = 1;
            page7.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
                addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox28ActionPerformed

    private void search6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search6MouseClicked
        search6.setText(null);
    }//GEN-LAST:event_search6MouseClicked

    private void search6CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search6CaretUpdate
        String sh = search6.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
                addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM hydro WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable14);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search6CaretUpdate

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        pg = 1;
        page8.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
            addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        if (pg > 1) {
            pg--;
            page8.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
            addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton45ActionPerformed

    private void page8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page8ActionPerformed
        int p = Integer.parseInt((String) page8.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
                addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_page8ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        if (pg < maxPages) {
            pg++;
            page8.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
            addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        pg = maxPages;
        page8.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
            addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jComboBox29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox29ActionPerformed
        if (jComboBox29.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox29.setSelectedItem("15");
            pg = 1;
            page8.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
                addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox29.getSelectedItem());
            pg = 1;
            page8.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
                addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox29ActionPerformed

    private void search10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search10MouseClicked
        search10.setText(null);
    }//GEN-LAST:event_search10MouseClicked

    private void search10CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search10CaretUpdate
        String sh = search10.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
                addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM wetqc WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable15);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search10CaretUpdate

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        pg = 1;
        page9.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
            addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        if (pg > 1) {
            pg--;
            page9.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
            addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton49ActionPerformed

    private void page9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page9ActionPerformed
        int p = Integer.parseInt((String) page9.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
                addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_page9ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        if (pg < maxPages) {
            pg++;
            page9.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
            addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        pg = maxPages;
        page9.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
            addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jComboBox30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox30ActionPerformed
        if (jComboBox30.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox30.setSelectedItem("15");
            pg = 1;
            page9.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
                addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox30.getSelectedItem());
            pg = 1;
            page9.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
                addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox30ActionPerformed

    private void search7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search7MouseClicked
        search7.setText(null);
    }//GEN-LAST:event_search7MouseClicked

    private void search7CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search7CaretUpdate
        String sh = search7.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
                addDryProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addDryProcessTable("SELECT * FROM dry WHERE Tag_No like '%" + sh + "%' or Machine_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable16);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search7CaretUpdate

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        pg = 1;
        page10.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
            addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        if (pg > 1) {
            pg--;
            page10.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
            addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton53ActionPerformed

    private void page10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page10ActionPerformed
        int p = Integer.parseInt((String) page10.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
                addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_page10ActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        if (pg < maxPages) {
            pg++;
            page10.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
            addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        pg = maxPages;
        page10.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
            addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jComboBox31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox31ActionPerformed
        if (jComboBox31.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox31.setSelectedItem("15");
            pg = 1;
            page10.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
                addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox31.getSelectedItem());
            pg = 1;
            page10.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
                addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox31ActionPerformed

    private void search8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search8MouseClicked
        search8.setText(null);
    }//GEN-LAST:event_search8MouseClicked

    private void search8CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search8CaretUpdate
        String sh = search8.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
                addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM awqc WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable17);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search8CaretUpdate

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        pg = 1;
        page11.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
            addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        if (pg > 1) {
            pg--;
            page11.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
            addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton57ActionPerformed

    private void page11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page11ActionPerformed
        int p = Integer.parseInt((String) page11.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_page11ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        if (pg < maxPages) {
            pg++;
            page11.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
            addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        pg = maxPages;
        page11.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
            addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jComboBox32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox32ActionPerformed
        if (jComboBox32.getSelectedItem().equals("")) {
            lim = 15;
            jComboBox32.setSelectedItem("15");
            pg = 1;
            page11.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) jComboBox32.getSelectedItem());
            pg = 1;
            page11.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jComboBox32ActionPerformed

    private void search9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search9MouseClicked
        search9.setText(null);
    }//GEN-LAST:event_search9MouseClicked

    private void search9CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_search9CaretUpdate
        String sh = search9.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addProcessTable("SELECT * FROM finish WHERE Tag_No like '%" + sh + "%' or Date like '%" + sh + "%' or Time like '%" + sh + "%' or Emp_No like '%" + sh + "%'", jTable18);
            } catch (SQLException ex) {
                Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_search9CaretUpdate

    private void BW_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BW_tagActionPerformed
        BW_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        BW_err.setText(null);
    }//GEN-LAST:event_BW_tagActionPerformed

    private void Fin_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Fin_tagActionPerformed
        Fin_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Fin_err.setText(null);
    }//GEN-LAST:event_Fin_tagActionPerformed

    private void BWQCchk4addmanuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCchk4addmanuallyActionPerformed

        try {
            if (TableValidate(BW_tag, BW_emp, BW_err)) {
                int tag = Integer.parseInt(String.valueOf(BW_tag.getSelectedItem()));
                String emp = String.valueOf(BW_emp.getSelectedItem());
                String date = BW_date.getText();
                String time = BW_time.getText();

                String col = "bwqc";
                String dep = "bwqc";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO bwqc (Tag_No,Date,Time,Emp_No) VALUES(" + tag + ",'" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "BWQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        BW_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN bwqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            BW_tag.addItem(rs.getString("Tag_No"));
                        }
                        BW_tag.setSelectedIndex(-1);
                        BW_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM bwqc", total4, page4);
                        addProcessTable("SELECT * FROM bwqc LIMIT " + lim + " OFFSET " + offset + "", jTable5);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BWQCchk4addmanuallyActionPerformed

    private void add_chem_man_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_chem_man_addActionPerformed

        try {
            if (TableValidate(Chem_tag, Chem_emp, Chem_err)) {
                int tag = Integer.parseInt(String.valueOf(Chem_tag.getSelectedItem()));
                String emp = String.valueOf(Chem_emp.getSelectedItem());
                String date = Chem_date.getText();
                String time = Chem_time.getText();

                String col = "chemicals";
                String dep = "chemicals";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO chemicals (Tag_No,Date,Time,Emp_No) VALUES(" + tag + ",'" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "chemicals Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Chem_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN chemicals b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Chem_tag.addItem(rs.getString("Tag_No"));
                        }
                        Chem_tag.setSelectedIndex(-1);
                        Chem_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM chemicals", total5, page5);
                        addProcessTable("SELECT * FROM chemicals LIMIT " + lim + " OFFSET " + offset + "", jTable6);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_add_chem_man_addActionPerformed

    private void Add_wash_manu_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_wash_manu_addActionPerformed

        try {
            if (TableValidate(Wash_tag, Wash_emp, Wash_err)) {
                int tag = Integer.parseInt(String.valueOf(Wash_tag.getSelectedItem()));
                String emp = String.valueOf(Wash_emp.getSelectedItem());
                String date = Wash_date.getText();
                String time = Wash_time.getText();

                String col = "wash";
                String dep = "wash";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO wash (Tag_No,Date,Time,Emp_No) VALUES(" + tag + ",'" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "wash Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Wash_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN wash b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Wash_tag.addItem(rs.getString("Tag_No"));
                        }
                        Wash_tag.setSelectedIndex(-1);
                        Wash_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM wash", total6, page6);
                        addProcessTable("SELECT * FROM wash LIMIT " + lim + " OFFSET " + offset + "", jTable13);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_Add_wash_manu_addActionPerformed

    private void add_hydro_manu_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_hydro_manu_addActionPerformed

        try {
            if (TableValidate(Hydro_tag, Hydro_emp, Hydro_err)) {
                int tag = Integer.parseInt(String.valueOf(Hydro_tag.getSelectedItem()));
                String emp = String.valueOf(Hydro_emp.getSelectedItem());
                String date = Hydro_date.getText();
                String time = Hydro_time.getText();

                String col = "hydro";
                String dep = "hydro";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO hydro (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "hydro Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Hydro_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN hydro b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Hydro_tag.addItem(rs.getString("Tag_No"));
                        }
                        Hydro_tag.setSelectedIndex(-1);
                        Hydro_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM hydro", total7, page7);
                        addProcessTable("SELECT * FROM hydro LIMIT " + lim + " OFFSET " + offset + "", jTable14);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_add_hydro_manu_addActionPerformed

    private void Wet_addManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wet_addManActionPerformed

        try {
            if (TableValidate(Wet_tag, Wet_emp, Wet_err)) {
                int tag = Integer.parseInt(String.valueOf(Wet_tag.getSelectedItem()));
                String emp = String.valueOf(Wet_emp.getSelectedItem());
                String date = Wet_date.getText();
                String time = Wet_time.getText();

                String col = "wetqc";
                String dep = "wetqc";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO wetqc (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "wetQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Wet_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN wetqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Wet_tag.addItem(rs.getString("Tag_No"));
                        }
                        Wet_tag.setSelectedIndex(-1);
                        Wet_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM wetqc", total8, page8);
                        addProcessTable("SELECT * FROM wetqc LIMIT " + lim + " OFFSET " + offset + "", jTable15);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_Wet_addManActionPerformed

    private void add_dry_manu_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_dry_manu_addActionPerformed

        try {
            if (DryValidate()) {
                int tag = Integer.parseInt(String.valueOf(Dry_tag.getSelectedItem()));
                String emp = String.valueOf(Dry_emp.getSelectedItem());
                String mah = String.valueOf(Dry_mah.getSelectedItem());
                String date = Dry_date.getText();
                String time = Dry_time.getText();

                String col = "dry";
                String dep = "dry";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO dry (Tag_No,Machine_No,Date,Time,Emp_No) VALUES('" + tag + "','" + mah + "','" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "dry Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Dry_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN dry b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Dry_tag.addItem(rs.getString("Tag_No"));
                        }
                        Dry_tag.setSelectedIndex(-1);
                        Dry_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM dry", total9, page9);
                        addProcessTable("SELECT * FROM dry LIMIT " + lim + " OFFSET " + offset + "", jTable16);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_add_dry_manu_addActionPerformed

    private void AWQCchk15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQCchk15ActionPerformed

        try {
            if (TableValidate(AW_tag, AW_emp, AW_err)) {
                int tag = Integer.parseInt(String.valueOf(AW_tag.getSelectedItem()));
                String emp = String.valueOf(AW_emp.getSelectedItem());
                String date = AW_date.getText();
                String time = AW_time.getText();

                String col = "awqc";
                String dep = "awqc";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO awqc (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "AWQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        AW_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN awqc b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            AW_tag.addItem(rs.getString("Tag_No"));
                        }
                        AW_tag.setSelectedIndex(-1);
                        AW_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM awqc", total10, page10);
                        addProcessTable("SELECT * FROM awqc LIMIT " + lim + " OFFSET " + offset + "", jTable17);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_AWQCchk15ActionPerformed

    private void Finish1add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Finish1add1ActionPerformed

        try {
            if (TableValidate(Fin_tag, Fin_emp, Fin_err)) {
                int tag = Integer.parseInt(String.valueOf(Fin_tag.getSelectedItem()));
                String blp;
                String emp = String.valueOf(Fin_emp.getSelectedItem());
                String date = Fin_date.getText();
                String time = Fin_time.getText();

                String col = "finish";
                String dep = "finish";

                try {
                    if (processchk(tag, col, dep)) {
                        con.prepareStatement("INSERT INTO finish (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + date + "','" + time + "','" + emp + "')").execute();
                        JOptionPane.showMessageDialog(null, "Finish Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                        Fin_tag.removeAllItems();
                        ResultSet rs = con.createStatement().executeQuery("SELECT t.Tag_No FROM tag t LEFT JOIN finish b ON t.Tag_No = b.Tag_No WHERE t.Tag_Status ='Printed' AND b.Tag_No IS NULL");
                        while (rs.next()) {
                            Fin_tag.addItem(rs.getString("Tag_No"));
                        }
                        Fin_tag.setSelectedIndex(-1);
                        Fin_emp.setSelectedIndex(-1);

                        updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                        addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);

                        ResultSet rs1 = con.createStatement().executeQuery("SELECT BLP_No FROM tag WHERE Tag_No = " + tag + "");
                        rs1.next();
                        blp = rs1.getString("BLP_No");
                        con.prepareStatement("UPDATE processplan SET Finished = 1 WHERE BLP_No = '" + blp + "'");

                        pro.updateMax("SELECT COUNT(*) AS count FROM processplan", pro.total1, pro.page1);
                        pro.addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_Finish1add1ActionPerformed

    private void BWQCchk3addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWQCchk3addActionPerformed
        String tag = BW_ctag.getText();
        String emp = BW_cemp.getText();

        try {
            if (AutoValidate(BW_ctag, BW_cemp, BW_err)) {
                con.prepareStatement("INSERT INTO bwqc (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "BWQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);
                updateMax("SELECT COUNT(*) AS count FROM finish", total4, page4);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable5);

            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        BW_ctag.setText(null);
        BW_cemp.setText(null);
    }//GEN-LAST:event_BWQCchk3addActionPerformed

    private void add_che_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_che_addActionPerformed
        String tag = Chem_ctag.getText();
        String emp = Chem_cemp.getText();

        try {
            if (AutoValidate(Chem_ctag, Chem_cemp, Chem_err)) {
                con.prepareStatement("INSERT INTO chemicals (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "CHEMICALS Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total5, page5);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable6);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Chem_ctag.setText(null);
        Chem_cemp.setText(null);
    }//GEN-LAST:event_add_che_addActionPerformed

    private void add_wash_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_wash_addActionPerformed
        String tag = Wash_ctag.getText();
        String emp = Wash_cemp.getText();

        try {
            if (AutoValidate(Wash_ctag, Wash_cemp, Wash_err)) {
                con.prepareStatement("INSERT INTO wash (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "WASH Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total6, page6);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable13);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Wash_ctag.setText(null);
        Wash_cemp.setText(null);
    }//GEN-LAST:event_add_wash_addActionPerformed

    private void add_hydro_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_hydro_addActionPerformed
        String tag = Hydro_ctag.getText();
        String emp = Hydro_cemp.getText();

        try {
            if (AutoValidate(Hydro_ctag, Hydro_cemp, Hydro_err)) {
                con.prepareStatement("INSERT INTO hydro (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "HYDRO Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total7, page7);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable14);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Hydro_ctag.setText(null);
        Hydro_cemp.setText(null);
    }//GEN-LAST:event_add_hydro_addActionPerformed

    private void Wet_cAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wet_cAddActionPerformed
        String tag = Wet_ctag.getText();
        String emp = Wet_cemp.getText();

        try {
            if (AutoValidate(Wet_ctag, Wet_cemp, Wet_err)) {
                con.prepareStatement("INSERT INTO wetqc (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "WETQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total8, page8);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable15);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Wet_ctag.setText(null);
        Wet_cemp.setText(null);
    }//GEN-LAST:event_Wet_cAddActionPerformed

    private void add_dry_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_dry_addActionPerformed
        String tag = Dry_ctag.getText();
        String emp = Dry_cemp.getText();
        String mah = String.valueOf(Dry_cmah.getSelectedItem());

        try {
            if (AutoDryValidate(Dry_ctag, Dry_cemp, Dry_cmah)) {
                con.prepareStatement("INSERT INTO dry (Tag_No,Machine_No,Date,Time,Emp_No) VALUES('" + tag + "','" + mah + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "DRY Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total9, page9);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable16);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Dry_ctag.setText(null);
        Dry_cemp.setText(null);
    }//GEN-LAST:event_add_dry_addActionPerformed

    private void AWQC1chkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AWQC1chkActionPerformed
        String tag = AW_ctag.getText();
        String emp = AW_cemp.getText();

        try {
            if (AutoValidate(AW_ctag, AW_cemp, AW_err)) {
                con.prepareStatement("INSERT INTO awqc (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "AWQC Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total10, page10);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable17);
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        AW_ctag.setText(null);
        AW_cemp.setText(null);
    }//GEN-LAST:event_AWQC1chkActionPerformed

    private void Finish1addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Finish1addActionPerformed
        String tag = Fin_ctag.getText();
        String emp = Fin_cemp.getText();

        try {
            if (AutoValidate(Fin_ctag, Fin_cemp, Fin_err)) {
                con.prepareStatement("INSERT INTO finish (Tag_No,Date,Time,Emp_No) VALUES('" + tag + "','" + java.time.LocalDate.now() + "','" + java.time.LocalTime.now() + "','" + emp + "')").execute();
                JOptionPane.showMessageDialog(null, "FINISH Updated", "Done", JOptionPane.INFORMATION_MESSAGE);

                updateMax("SELECT COUNT(*) AS count FROM finish", total11, page11);
                addProcessTable("SELECT * FROM finish LIMIT " + lim + " OFFSET " + offset + "", jTable18);

                con.prepareStatement("UPDATE processplan SET Finished = 1 WHERE BLP_No = '" + tag + "'");

                pro.updateMax("SELECT COUNT(*) AS count FROM processplan", pro.total1, pro.page1);
                pro.addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }

        Fin_ctag.setText(null);
        Fin_cemp.setText(null);
    }//GEN-LAST:event_Finish1addActionPerformed

    private void Dry_cmahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dry_cmahActionPerformed
        Dry_cmah.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_cmahActionPerformed

    private void BW_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BW_empActionPerformed
        BW_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        BW_err.setText(null);
    }//GEN-LAST:event_BW_empActionPerformed

    private void Chem_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Chem_tagActionPerformed
        Chem_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Chem_err.setText(null);
    }//GEN-LAST:event_Chem_tagActionPerformed

    private void Chem_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Chem_empActionPerformed
        Chem_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Chem_err.setText(null);
    }//GEN-LAST:event_Chem_empActionPerformed

    private void Wash_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wash_tagActionPerformed
        Wash_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Wash_err.setText(null);
    }//GEN-LAST:event_Wash_tagActionPerformed

    private void Wash_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wash_empActionPerformed
        Wash_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Wash_err.setText(null);
    }//GEN-LAST:event_Wash_empActionPerformed

    private void Hydro_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hydro_tagActionPerformed
        Hydro_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Hydro_err.setText(null);
    }//GEN-LAST:event_Hydro_tagActionPerformed

    private void Hydro_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hydro_empActionPerformed
        Hydro_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Hydro_err.setText(null);
    }//GEN-LAST:event_Hydro_empActionPerformed

    private void Wet_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wet_tagActionPerformed
        Wet_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Wet_err.setText(null);
    }//GEN-LAST:event_Wet_tagActionPerformed

    private void Wet_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wet_empActionPerformed
        Wet_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Wet_err.setText(null);
    }//GEN-LAST:event_Wet_empActionPerformed

    private void Dry_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dry_tagActionPerformed
        Dry_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_tagActionPerformed

    private void Dry_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dry_empActionPerformed
        Dry_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_empActionPerformed

    private void Dry_mahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dry_mahActionPerformed
        Dry_mah.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_mahActionPerformed

    private void AW_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AW_tagActionPerformed
        AW_tag.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        AW_err.setText(null);
    }//GEN-LAST:event_AW_tagActionPerformed

    private void AW_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AW_empActionPerformed
        AW_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        AW_err.setText(null);
    }//GEN-LAST:event_AW_empActionPerformed

    private void Fin_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Fin_empActionPerformed
        Fin_emp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        Fin_err.setText(null);
    }//GEN-LAST:event_Fin_empActionPerformed

    private void BW_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BW_ctagFocusGained
        BW_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        BW_err.setText(null);
    }//GEN-LAST:event_BW_ctagFocusGained

    private void BW_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BW_cempFocusGained
        BW_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        BW_err.setText(null);
    }//GEN-LAST:event_BW_cempFocusGained

    private void Chem_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Chem_ctagFocusGained
        Chem_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Chem_err.setText(null);
    }//GEN-LAST:event_Chem_ctagFocusGained

    private void Chem_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Chem_cempFocusGained
        Chem_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Chem_err.setText(null);
    }//GEN-LAST:event_Chem_cempFocusGained

    private void Wash_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Wash_ctagFocusGained
        Wash_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Wash_err.setText(null);
    }//GEN-LAST:event_Wash_ctagFocusGained

    private void Wash_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Wash_cempFocusGained
        Wash_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Wash_err.setText(null);
    }//GEN-LAST:event_Wash_cempFocusGained

    private void Hydro_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Hydro_ctagFocusGained
        Hydro_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Hydro_err.setText(null);
    }//GEN-LAST:event_Hydro_ctagFocusGained

    private void Hydro_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Hydro_cempFocusGained
        Hydro_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Hydro_err.setText(null);
    }//GEN-LAST:event_Hydro_cempFocusGained

    private void Wet_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Wet_ctagFocusGained
        Wet_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Wet_err.setText(null);
    }//GEN-LAST:event_Wet_ctagFocusGained

    private void Wet_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Wet_cempFocusGained
        Wet_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Wet_err.setText(null);
    }//GEN-LAST:event_Wet_cempFocusGained

    private void Dry_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Dry_ctagFocusGained
        Dry_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_ctagFocusGained

    private void Dry_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Dry_cempFocusGained
        Dry_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Dry_err.setText(null);
    }//GEN-LAST:event_Dry_cempFocusGained

    private void AW_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AW_ctagFocusGained
        AW_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        AW_err.setText(null);
    }//GEN-LAST:event_AW_ctagFocusGained

    private void AW_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AW_cempFocusGained
        AW_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        AW_err.setText(null);
    }//GEN-LAST:event_AW_cempFocusGained

    private void Fin_ctagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Fin_ctagFocusGained
        Fin_ctag.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Fin_err.setText(null);
    }//GEN-LAST:event_Fin_ctagFocusGained

    private void Fin_cempFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Fin_cempFocusGained
        Fin_cemp.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));
        Fin_err.setText(null);
    }//GEN-LAST:event_Fin_cempFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int po = 0;
        String compname = null;
        double up = 0;
        String style = null;
        double tc = 0;
        String pk = null;
        String email = null;

        int r = jTable18.getSelectedRow();
        int tag = Integer.parseInt(jTable18.getValueAt(r, 0).toString());
        try {

            ResultSet r50 = con.createStatement().executeQuery("SELECT BLP_No FROM tag WHERE Tag_No = " + tag);
            while (r50.next()) {

                pk = r50.getString("BLP_No");

                ResultSet r51 = con.createStatement().executeQuery("SELECT * FROM splglove s,poglove p WHERE s.lotNo=p.lotNo AND p.lotNo ='" + pk + "'");
                while (r51.next()) {

                    po = r51.getInt("poNo");
                    compname = r51.getString("customer");
                    up = r51.getDouble("unitCost");
                    style = r51.getString("style");
                    tc = r51.getDouble("totCost");

                }
                ResultSet rs = con.createStatement().executeQuery("select email from customer where name='" + compname + "'");
                if (rs.next()) {
                    email = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*System.out.println(po);
        System.out.println(compname);
        System.out.println(up);
        System.out.println(style);
        System.out.println(tc);
        System.out.println(email);*/
        int x = JOptionPane.showConfirmDialog(null, "Do you want Send the mail?");
        if (x == 0) {
            try {
                final String username = "reach.prasara@gmail.com";
                final String password = "ruchika123";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = null;
                session = session.getInstance(props, new javax.mail.Authenticator() {

                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

                try {
                    javax.mail.Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject("Regarding Request Completion");
                    message.setContent("<body style=\"background-color:whitesmoke;\"><center><div align='center' style=\"background-color:white; max-width: 600px; padding: 10px\"><center><img src=http://www.prasarawashing.com/images/logo.png alt='Prasara LOGO'></center>"
                            + "<br><br>"
                            + ""
                            + "<center><h1>" + compname.toUpperCase() + "</h1></center>"
                            + "<br><font color=\"black\"><center><l>Thank you for trusting us in ensuring to make your life much simpler & easier.</l></center>"
                            + "<br><br> <h4 align='left'><u>ORDER DETAILS :- Tag Number=" + pk + "</u><h4>"
                            + "<br> <div align='center' padding><table align='center' cellpadding='25'>"
                            + "<tr>"
                            + "    <th><b>Reference</b></th>"
                            + "    <th><b>Product</b></th>"
                            + "    <th><b>Unit Price</b></th>"
                            + "    <th><b>Quantity</b></th>"
                            + "    <th><b>Total Price</b></th>"
                            + "</tr>"
                            + "<tr>"
                            + "    <td>" + po + "</td>"
                            + "    <td>" + style + "</td>"
                            + "    <td>" + up + "</td>"
                            + "    <td>" + tc / up + "</td>"
                            + "    <td>" + tc + "</td>"
                            + "</tr>"
                            + "</table></div>"
                            + "<br><div align='right' style=\"line-height: 2;\"> <b>Total Amount(Rs):</b> " + tc
                            + "<br><b>Discounts(Rs):</b> " + "0.00"
                            + "<br><b>Total Tax(Rs):</b> " + "0%"
                            + "<br><b>Total Paid(Rs):</b> " + "0.00</div>"
                            + "<br><br>"
                            + "<div align='center'>If any details with regards to the above report is concerning, please contact us.</div><br><br>"
                            + "<br><br>"
                            + "<div align='left' style=\"line-height: 1.5;\">Thank you! <br>Product Management Division<br>PRASARA WASHING PLANT</font></div>"
                            + "<br><footer><font color=\"black\">+94 31 4927863 / 0714 904 740 <br> dankotuwa@prasarawashing.com, seywash@vinet.lk<br>Prasara Washing Plant Dankotuwa (Pvt) Ltd. Negombo Road, Thabarawila, Waikkala.</font></footer></body></center></div>", "text/html");

                    Transport.send(message);
                    //System.out.println("Email sent");

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

                JOptionPane.showMessageDialog(null, " Email has ben sent!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (x == 1) {
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jScrollPane12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane12MouseClicked
        // TODO add your handling code here:
        /*String s = (String) jTable18.getValueAt(jTable18.getSelectedRow(), 0);
        if (s != null && !s.trim().equals("")) {
        jButton1.setVisible(true);
        }*/
    }//GEN-LAST:event_jScrollPane12MouseClicked

    private void FinishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FinishMouseClicked
        // TODO add your handling code here:
        jButton1.setVisible(false);
    }//GEN-LAST:event_FinishMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Home.Report(jTable5, "Before Wash Quality Check Details", "E:\\Reports\\BWQC.pdf");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Home.Report(jTable6, "Chemical Wash Details", "E:\\Reports\\chem.pdf");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Home.Report(jTable13, "Washing Details", "E:\\Reports\\wash.pdf");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Home.Report(jTable14, "Hydro Washing Details", "E:\\Reports\\hydro.pdf");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        Home.Report(jTable15, "Wet Quality Check Details", "E:\\Reports\\WetQC.pdf");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Home.Report(jTable16, "Drying Details", "E:\\Reports\\dry.pdf");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        Home.Report(jTable17, "After Wash quality Check Details", "E:\\Reports\\AWQC.pdf");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        Home.Report(jTable18, "Finished Washing Details", "E:\\Reports\\finish.pdf");
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jTable18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable18MouseClicked
        // TODO add your handling code here:
        String s = (String) jTable18.getValueAt(jTable18.getSelectedRow(), 0);
        if (s != null && !s.trim().equals("")) {
        jButton1.setVisible(true);
        }
    }//GEN-LAST:event_jTable18MouseClicked

    private void jTable18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable18MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable18MouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AWQC;
    private javax.swing.JPanel AWQC1;
    private javax.swing.JButton AWQC1Back;
    private javax.swing.JButton AWQC1chk;
    private javax.swing.JButton AWQCBack;
    private javax.swing.JButton AWQCButton;
    private javax.swing.JButton AWQCchk;
    private javax.swing.JButton AWQCchk15;
    private javax.swing.JTextField AW_cemp;
    private javax.swing.JTextField AW_ctag;
    private javax.swing.JTextField AW_date;
    private javax.swing.JComboBox<String> AW_emp;
    private javax.swing.JLabel AW_err;
    private javax.swing.JComboBox<String> AW_tag;
    private javax.swing.JTextField AW_time;
    private javax.swing.JButton Add_wash_manu_add;
    private javax.swing.JPanel BWQC;
    private javax.swing.JPanel BWQC1;
    private javax.swing.JButton BWQCBack;
    private javax.swing.JButton BWQCBack1;
    private javax.swing.JButton BWQCButton;
    private javax.swing.JButton BWQCchk;
    private javax.swing.JButton BWQCchk3add;
    private javax.swing.JButton BWQCchk4addmanually;
    private javax.swing.JTextField BW_cemp;
    private javax.swing.JTextField BW_ctag;
    private javax.swing.JTextField BW_date;
    private javax.swing.JComboBox<String> BW_emp;
    private javax.swing.JLabel BW_err;
    private javax.swing.JComboBox<String> BW_tag;
    private javax.swing.JTextField BW_time;
    private javax.swing.JLabel Background;
    private javax.swing.JPanel Chem;
    private javax.swing.JPanel Chem1;
    private javax.swing.JButton ChemBack;
    private javax.swing.JTextField Chem_cemp;
    private javax.swing.JTextField Chem_ctag;
    private javax.swing.JTextField Chem_date;
    private javax.swing.JComboBox<String> Chem_emp;
    private javax.swing.JLabel Chem_err;
    private javax.swing.JComboBox<String> Chem_tag;
    private javax.swing.JTextField Chem_time;
    private javax.swing.JButton ChemicalsButton;
    private javax.swing.JPanel Dry;
    private javax.swing.JPanel Dry1;
    private javax.swing.JButton DryBack;
    private javax.swing.JTextField Dry_cemp;
    private javax.swing.JComboBox<String> Dry_cmah;
    private javax.swing.JTextField Dry_ctag;
    private javax.swing.JTextField Dry_date;
    private javax.swing.JComboBox<String> Dry_emp;
    private javax.swing.JLabel Dry_err;
    private javax.swing.JComboBox<String> Dry_mah;
    private javax.swing.JComboBox<String> Dry_tag;
    private javax.swing.JTextField Dry_time;
    private javax.swing.JButton Drychk;
    private javax.swing.JButton DryingButton;
    private javax.swing.JButton FinBack;
    private javax.swing.JTextField Fin_cemp;
    private javax.swing.JTextField Fin_ctag;
    private javax.swing.JTextField Fin_date;
    private javax.swing.JComboBox<String> Fin_emp;
    private javax.swing.JLabel Fin_err;
    private javax.swing.JComboBox<String> Fin_tag;
    private javax.swing.JTextField Fin_time;
    private javax.swing.JButton Finchk;
    private javax.swing.JPanel Finish;
    private javax.swing.JPanel Finish1;
    private javax.swing.JButton Finish1add;
    private javax.swing.JButton Finish1add1;
    private javax.swing.JButton Finish1back;
    private javax.swing.JButton FinishingButton;
    private javax.swing.JPanel Hyd;
    private javax.swing.JPanel Hyd1;
    private javax.swing.JButton HydBack;
    private javax.swing.JButton HydroButton;
    private javax.swing.JTextField Hydro_cemp;
    private javax.swing.JTextField Hydro_ctag;
    private javax.swing.JTextField Hydro_date;
    private javax.swing.JComboBox<String> Hydro_emp;
    private javax.swing.JLabel Hydro_err;
    private javax.swing.JComboBox<String> Hydro_tag;
    private javax.swing.JTextField Hydro_time;
    private javax.swing.JButton Hydrochk;
    private javax.swing.JPanel Production;
    private javax.swing.JPanel Wash;
    private javax.swing.JPanel Wash1;
    private javax.swing.JButton WashBack;
    private javax.swing.JTextField Wash_cemp;
    private javax.swing.JTextField Wash_ctag;
    private javax.swing.JTextField Wash_date;
    private javax.swing.JComboBox<String> Wash_emp;
    private javax.swing.JLabel Wash_err;
    private javax.swing.JComboBox<String> Wash_tag;
    private javax.swing.JTextField Wash_time;
    private javax.swing.JButton Washchk;
    private javax.swing.JButton WashingButton;
    private javax.swing.JPanel WetQC;
    private javax.swing.JPanel WetQC1;
    private javax.swing.JButton WetQCBack;
    private javax.swing.JButton WetQCButton;
    private javax.swing.JButton WetQCchk;
    private javax.swing.JButton Wet_addMan;
    private javax.swing.JButton Wet_cAdd;
    private javax.swing.JTextField Wet_cemp;
    private javax.swing.JTextField Wet_ctag;
    private javax.swing.JTextField Wet_date;
    private javax.swing.JComboBox<String> Wet_emp;
    private javax.swing.JLabel Wet_err;
    private javax.swing.JComboBox<String> Wet_tag;
    private javax.swing.JTextField Wet_time;
    private javax.swing.JButton add_che_add;
    private javax.swing.JButton add_chem_man_add;
    private javax.swing.JButton add_dry_add;
    private javax.swing.JButton add_dry_back;
    private javax.swing.JButton add_dry_manu_add;
    private javax.swing.JButton add_hydro_add;
    private javax.swing.JButton add_hydro_back;
    private javax.swing.JButton add_hydro_manu_add;
    private javax.swing.JButton add_wash_add;
    private javax.swing.JButton add_wash_back;
    private javax.swing.JButton addchemback;
    private javax.swing.JButton cheak_wet_qc_back;
    private javax.swing.JButton chem_check;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JComboBox<String> jComboBox25;
    private javax.swing.JComboBox<String> jComboBox26;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox28;
    private javax.swing.JComboBox<String> jComboBox29;
    private javax.swing.JComboBox<String> jComboBox30;
    private javax.swing.JComboBox<String> jComboBox31;
    private javax.swing.JComboBox<String> jComboBox32;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator45;
    private javax.swing.JSeparator jSeparator47;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField page10;
    private javax.swing.JTextField page11;
    private javax.swing.JTextField page4;
    private javax.swing.JTextField page5;
    private javax.swing.JTextField page6;
    private javax.swing.JTextField page7;
    private javax.swing.JTextField page8;
    private javax.swing.JTextField page9;
    private javax.swing.JTextField search10;
    private javax.swing.JTextField search11;
    private javax.swing.JTextField search12;
    private javax.swing.JTextField search13;
    private javax.swing.JTextField search2;
    private javax.swing.JTextField search3;
    private javax.swing.JTextField search4;
    private javax.swing.JTextField search5;
    private javax.swing.JTextField search6;
    private javax.swing.JTextField search7;
    private javax.swing.JTextField search8;
    private javax.swing.JTextField search9;
    private javax.swing.JLabel total10;
    private javax.swing.JLabel total11;
    private javax.swing.JLabel total4;
    private javax.swing.JLabel total5;
    private javax.swing.JLabel total6;
    private javax.swing.JLabel total7;
    private javax.swing.JLabel total8;
    private javax.swing.JLabel total9;
    // End of variables declaration//GEN-END:variables
}
