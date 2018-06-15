/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package production;
import static production.dbCon.con;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ITP.Home;
import Mainternance.validation;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfTemplate;
//import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Dimension;
//import com.itextpdf.awt.PdfGraphics2D;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import net.connectcode.Code128Auto;

/**
 *
 * @author Axio
 */
public class production extends javax.swing.JPanel {

    int tot;
    int maxPages;
    int pg = 1;
    int lim = 15;
    int offset = 0;

    /**
     * Creates new form production
     *
     * @throws java.sql.SQLException
     */

    public production() throws SQLException {
        initComponents();

        Pre_Pro.setBackground(new Color(0, 0, 0, 0));

        Pre_Pro.setVisible(true);
        FirstBath.setVisible(false);
        F_AQLPanel.setVisible(false);
        F_FirstBathStatus.setVisible(false);
        F_IssueFB.setVisible(false);
        F_ListFBandAQL.setVisible(false);
        PPro.setVisible(false);
        P_IssuePP.setVisible(false);
        Tag.setVisible(false);

        dbCon d = new dbCon();
        d.connect();

        GenTag.setVisible(false);

        FirstBath n1 = new FirstBath();
        F_ListFBandAQL n2 = new F_ListFBandAQL();
        PPro n3 = new PPro();
        Tag n4 = new Tag();
    }
    
    boolean validateDateBackward(String val, JLabel label){
        int []dayArr = new int[3];
        int []arr=new int[3];
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        int a=0;
        int b=0;
        
        StringTokenizer tk1 = new StringTokenizer(today, "-");
        while(tk1.hasMoreElements()){
            dayArr[b]=Integer.parseInt(tk1.nextToken());
            //System.out.println(arr[a]);
            b++;
        }
        
        if(val.contains("[a-zA-Z]+")){
            label.setText("Invalid Date !");
            return false;
        }else {
            if(!val.equals("") && !val.equals("dd-mm-yyyy")){
                StringTokenizer tk = new StringTokenizer(val, "-");
                while(tk.hasMoreElements()){
                    arr[a]=Integer.parseInt(tk.nextToken());
                    //System.out.println(arr[a]);
                    a++;
                }
                int check=dayArr[0]-arr[0];
                if((check<=0) && (arr[2] == dayArr[2])&& (arr[1] >= dayArr[1])){
                    return true;
                }
                else{
                    label.setText("Invalid future date !");
                    return false;
                }
            }else{
                    label.setText("Date field cannot be empty !");
                    return false;
            }
        }
    }

    public class FirstBath extends javax.swing.JPanel {

        int tot;
        int maxPages;
        int pg = 1;
        int lim = 15;
        int offset = 0;

        public FirstBath() throws SQLException {
            FBdelete.setVisible(false);

            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");

            ResultSet rs = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs.next()) {
                FBIEmp.addItem(rs.getString("empNo"));
                FBIWsu.addItem(rs.getString("empNo"));
                FBIBWst.addItem(rs.getString("empNo"));
                FBIBC.addItem(rs.getString("empNo"));
            }
            FBIEmp.setSelectedIndex(-1);
            FBIWsu.setSelectedIndex(-1);
            FBIBWst.setSelectedIndex(-1);
            FBIBC.setSelectedIndex(-1);
        }

    }

    public class F_ListFBandAQL extends javax.swing.JPanel {

        int tot;
        int maxPages;
        int pg = 1;
        int lim = 15;
        int offset = 0;

        public F_ListFBandAQL() throws SQLException {
            ListFBandAQLedit.setVisible(false);
            ListFBandAQLdelete.setVisible(false);

            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        }

    }

    public class PPro extends javax.swing.JPanel {

        int tot;
        int maxPages;
        int pg = 1;
        int lim = 15;
        int offset = 0;

        public PPro() throws SQLException {
            PProedit.setVisible(false);
            PProdelete.setVisible(false);

            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

            ResultSet rs = con.createStatement().executeQuery("SELECT empNo FROM employee");
            while (rs.next()) {
                PIWSu.addItem(rs.getString("empNo"));
                PIBWSt.addItem(rs.getString("empNo"));
                PIBC.addItem(rs.getString("empNo"));
            }
            PIWSu.setSelectedIndex(-1);
            PIBWSt.setSelectedIndex(-1);
            PIBC.setSelectedIndex(-1);
        }
    }

    public class Tag extends javax.swing.JPanel {
// add valus from the db
        int tot;
        int maxPages;
        int pg = 1;
        int lim = 15;
        int offset = 0;

        public Tag() throws SQLException {
            tagPrint.setVisible(false);

            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        }

    }

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

//Show tables in UI
    void addFBTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Style Number", "Color", "Customer", "Date", "Type", "Employee"}, 0);

            while (rs1.next()) {
                String style = rs1.getString("Style_No");
                String col = rs1.getString("Color");
                String date = rs1.getString("Date");
                String cus = rs1.getString("Customer");
                String type = rs1.getString("Type");
                String emp = rs1.getString("Employee");

                Object[] row = {style, col, cus, date, type, emp};
                model.addRow(row);
            }
            jTable1.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addAQLandFBTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Batch/Lot/Portion Number", "Style Number", "Color", "Customer", "Type", "First Bath Status", "AQL Status"}, 0);

            while (rs1.next()) {
                String blp = rs1.getString("BLP_No");
                String style = rs1.getString("Style_No");
                String col = rs1.getString("Color");
                String cus = rs1.getString("Customer");
                String type = rs1.getString("Type");
                String fb = rs1.getString("First_Bath");
                String aql = rs1.getString("AQL");

                Object[] row = {blp, style, col, cus, type, fb, aql};
                model.addRow(row);
            }
            jTable3.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addPProTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Process_No", "Batch/Lot/Portion Number", "Style Number", "Color", "Customer", "Date", "Type", "Washing Supervisor", "Employee", "Process Status"}, 0);

            while (rs1.next()) {
                String pno = rs1.getString("Process_No");
                String blp = rs1.getString("BLP_No");
                String style = rs1.getString("Style_No");
                String cus = rs1.getString("Customer");
                String col = rs1.getString("Color");
                String date = rs1.getString("Date");
                String type = rs1.getString("Type");
                String ws = rs1.getString("W_Supervisor");
                String emp = rs1.getString("Employee");
                String fn = rs1.getString("Finished");

                Object[] row = {pno, blp, style, cus, col, date, type, ws, emp, fn};
                model.addRow(row);
            }
            jTable2.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// create tag table and display the tag table
    void addTagTable(String q) throws SQLException {
        try {
            ResultSet rs1 = con.createStatement().executeQuery(q);
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Tag Number", "Batch/Portion/Lot Number", "Date", "Customer", "Style Number", "Color", "Tag Status"}, 0);

            while (rs1.next()) {
                String tag = rs1.getString("Tag_No");
                String blp = rs1.getString("BLP_No");
                String date = rs1.getString("Date");
                String cus = rs1.getString("Customer");
                String style = rs1.getString("Style_No");
                String col = rs1.getString("Color");
                String status = rs1.getString("Tag_Status");

                Object[] row = {tag, blp, date, cus, style, col, status};
                model.addRow(row);
            }
            jTable4.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//Functions that are used to Validate
    boolean FBIssueValidate() throws SQLException {
        if ((FBIType.getSelectedIndex()) != -1) {
            if ((FBICus.getSelectedIndex()) != -1) {
                if ((FBIStyle.getSelectedIndex()) != -1) {
                    if ((FBICol.getSelectedIndex()) != -1) {
                        if ((FBIEmp.getSelectedIndex()) != -1) {
                            if ((FBIWsu.getSelectedIndex()) != -1) {
                                if ((FBIBWst.getSelectedIndex()) != -1) {
                                    if ((FBIBC.getSelectedIndex()) != -1) {
                                        if (!validateDateBackward(new SimpleDateFormat("dd-MM-yyyy").format(FBIDate.getDate()), FBIError)) {
                                            //JOptionPane.showMessageDialog(null, "Date not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                                            FBIDate.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                            //FBIError.setText("Date not Selected");
                                            return false;
                                        }
                                    } else {
                                        //JOptionPane.showMessageDialog(null, "BW Q/C not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                                        FBIBC.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                        FBIError.setText("BW Q/C not Selected");
                                        return false;
                                    }
                                } else {
                                    //JOptionPane.showMessageDialog(null, "BW Store not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                                    FBIBWst.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                    FBIError.setText("BW Store not Selected");
                                    return false;
                                }
                            } else {
                                //JOptionPane.showMessageDialog(null, "W Supervisor not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                                FBIWsu.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                FBIError.setText("W Supervisor not Selected");
                                return false;
                            }
                        } else {
                            //JOptionPane.showMessageDialog(null, "Employee not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                            FBIEmp.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            FBIError.setText("Employee not Selected");
                            return false;
                        }
                    } else {
                        //JOptionPane.showMessageDialog(null, "Color not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                        FBICol.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        FBIError.setText("Color not Selected");
                        return false;
                    }
                } else {
                    //JOptionPane.showMessageDialog(null, "Style not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    FBIStyle.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    FBIError.setText("Style not Selected");
                    return false;
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Customer not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                FBICus.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                FBIError.setText("Customer not Selected");
                return false;
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Type Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            FBIType.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            FBIError.setText("Type Not Selected");
            return false;
        }
        return true;
    }

    boolean PIssueValidate() throws SQLException {
        if ((PIType.getSelectedIndex()) != -1) {
            if ((PIBLPno.getSelectedIndex()) != -1) {
                if ((PIWSu.getSelectedIndex()) != -1) {
                    if ((PIBWSt.getSelectedIndex()) != -1) {
                        if ((PIBC.getSelectedIndex()) != -1) {
                            if (!validateDateBackward(new SimpleDateFormat("dd-MM-yyyy").format(PIDate.getDate()), PIError)) {
                                //JOptionPane.showMessageDialog(null, "Date not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                                PIDate.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                                //PIError.setText("Date not Selected");
                                return false;
                            }
                        } else {
                            //JOptionPane.showMessageDialog(null, "BW Q/C not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                            PIBC.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            PIError.setText("BW Q/C not Selected");
                            return false;
                        }
                    } else {
                        //JOptionPane.showMessageDialog(null, "BW Store not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                        PIBWSt.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        PIError.setText("BW Store not Selected");
                        return false;
                    }
                } else {
                    //JOptionPane.showMessageDialog(null, "W Supervisor not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    PIWSu.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    PIError.setText("W Supervisor not Selected");
                    return false;
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Respective Portion Number, Batch Number or Lot Number not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                PIBLPno.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                PIError.setText("<html>Respective Portion Number, Batch Number<br> or Lot Number not Selected<html>");
                return false;
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Type Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            PIType.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            PIError.setText("Type Not Selected");
            return false;
        }
        return true;
    }

    boolean AQLValidate() throws SQLException {
        if ((AQLType.getSelectedIndex()) != -1) {
            if ((AQLBLPno.getSelectedIndex()) != -1) {
                if ((AQLStatus.getSelectedIndex()) == -1) {
                    //JOptionPane.showMessageDialog(null, "Status not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    AQLStatus.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    AQLError.setText("Status not Selected");
                    return false;
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Respective Portion Number, Batch Number or Lot Number not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                AQLBLPno.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                AQLError.setText("<html>Respective Portion Number, Batch Number<br> or Lot Number not Selected<html>");
                return false;
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Type Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            AQLType.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            AQLError.setText("Type Not Selected");
            return false;
        }
        return true;
    }

    boolean FBStatusValidate() throws SQLException {
        if ((FBStatusType.getSelectedIndex()) != -1) {
            if ((FBStatusCus.getSelectedIndex()) != -1) {
                if ((FBStatusStyle.getSelectedIndex()) != -1) {
                    if ((FBStatusCol.getSelectedIndex()) != -1) {
                        if ((FBStatusStatus.getSelectedIndex()) == -1) {
                            //JOptionPane.showMessageDialog(null, "Status not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                            FBStatusStatus.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                            FBStatusError.setText("Status not Selected");
                            return false;
                        }
                    } else {
                        //JOptionPane.showMessageDialog(null, "Color not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                        FBStatusCol.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                        FBStatusError.setText("Color not Selected");
                        return false;
                    }
                } else {
                    //JOptionPane.showMessageDialog(null, "Style Number not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    FBStatusStyle.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    FBStatusError.setText("Style Number not Selected");
                    return false;
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Customer Not Selected", "Error", JOptionPane.ERROR_MESSAGE);
                FBStatusCus.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                FBStatusError.setText("Customer Not Selected");
                return false;
            }
        } else {
            FBStatusType.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            FBStatusError.setText("Type not Selected");
            return false;
        }

        return true;
    }

//Functions that are used to add Gloves, Fabric and Garment to listaqlandfb Table
    void addGlovetoTable() throws SQLException {
        String lot;
        String cus;
        String style;
        int count;
        ResultSet rs = con.createStatement().executeQuery("SELECT sg.lotNo, sg.customer, sg.style FROM SPLGlove sg LEFT JOIN listaqlandfb l ON sg.lotNo = l.BLP_No WHERE l.BLP_No IS NULL");
        while (rs.next()) {
            lot = rs.getString("lotNo");
            cus = rs.getString("customer");
            style = rs.getString("style");

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM firstbath WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = 'None'");
            rs1.next();
            count = rs1.getInt("count");

            if (count > 0) {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + lot + "','" + style + "','None','" + cus + "','Gloves',1,0)").execute();
            } else {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + lot + "','" + style + "','None','" + cus + "','Gloves',0,0)").execute();
            }
        }

        updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
        addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
    }

    void addFabrictoTable() throws SQLException {
        String bat;
        String cus;
        String col;
        String style;
        int count;
        ResultSet rs = con.createStatement().executeQuery("SELECT sf.batchNo, sf.customer, sf.color, sf.style FROM SPLFabric sf LEFT JOIN listaqlandfb l ON sf.batchNo = l.BLP_No WHERE l.BLP_No IS NULL");
        while (rs.next()) {
            bat = rs.getString("batchNo");
            cus = rs.getString("customer");
            style = rs.getString("style");
            col = rs.getString("color");

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM firstbath WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "'");
            rs1.next();
            count = rs1.getInt("count");

            if (count > 0) {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + bat + "','" + style + "','" + col + "','" + cus + "','Fabrics',1,0)").execute();
            } else {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + bat + "','" + style + "','" + col + "','" + cus + "','Fabrics',0,0)").execute();
            }
        }

        updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
        addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
    }

    void addGarmenttoTable() throws SQLException {
        String por;
        String cus;
        String col;
        String style;
        int count;
        ResultSet rs = con.createStatement().executeQuery("SELECT sg.portionNo, sg.customer, sg.color, sg.style FROM SPLGarment sg LEFT JOIN listaqlandfb l ON sg.portionNo = l.BLP_No WHERE l.BLP_No IS NULL");
        while (rs.next()) {
            por = rs.getString("portionNo");
            cus = rs.getString("customer");
            style = rs.getString("style");
            col = rs.getString("color");

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM firstbath WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "'");
            rs1.next();
            count = rs1.getInt("count");

            if (count > 0) {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + por + "','" + style + "','" + col + "','" + cus + "','Garments',1,0)").execute();
            } else {
                con.prepareStatement("INSERT INTO listaqlandfb (BLP_No,Style_No,Color,Customer,Type,First_Bath,AQL) VALUES('" + por + "','" + style + "','" + col + "','" + cus + "','Garments',0,0)").execute();
            }
        }

        updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
        addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
    }

    void addProcesstoTagTable() throws SQLException {
        String bat;
        String cus;
        String col;
        String style;

        ResultSet rs = con.createStatement().executeQuery("SELECT p.BLP_No, p.Customer, p.Color, p.Style_No FROM processplan p LEFT JOIN tag t ON p.BLP_No = t.BLP_No WHERE p.BLP_No IS NOT NULL AND t.BLP_No IS NULL");
        while (rs.next()) {
            bat = rs.getString("BLP_No");
            cus = rs.getString("Customer");
            style = rs.getString("Style_No");
            col = rs.getString("Color");

            con.prepareStatement("INSERT INTO tag (BLP_No,Date,Customer,Style_No,Color) VALUES('" + bat + "','" + java.time.LocalDate.now() + "','" + cus + "','" + style + "','" + col + "')").execute();
        }

        updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
        addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
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
        Pre_Pro = new javax.swing.JPanel();
        FBButton = new javax.swing.JButton();
        Pro_PlanButton = new javax.swing.JButton();
        TagsButton = new javax.swing.JButton();
        FirstBath = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        FBsearch = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        FBItem = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        FBBack = new javax.swing.JButton();
        FBRefresh = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        page = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        FBdelete = new javax.swing.JButton();
        ListFBandAQL = new javax.swing.JButton();
        IssueNFB = new javax.swing.JButton();
        FBStatus = new javax.swing.JButton();
        AQL = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        F_AQLPanel = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        AQLBack = new javax.swing.JButton();
        AQLSave = new javax.swing.JButton();
        AQLType = new javax.swing.JComboBox<>();
        AQLBLPno = new javax.swing.JComboBox<>();
        AQLStatus = new javax.swing.JComboBox<>();
        AQLStyleno = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        AQLCus = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        AQLCol = new javax.swing.JLabel();
        AQLError = new javax.swing.JLabel();
        F_FirstBathStatus = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        FBStatusStatus = new javax.swing.JComboBox<>();
        FBStatusCus = new javax.swing.JComboBox<>();
        FBStatusStyle = new javax.swing.JComboBox<>();
        FBStatusBack = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        jLabel143 = new javax.swing.JLabel();
        FBStatusCol = new javax.swing.JComboBox<>();
        jLabel144 = new javax.swing.JLabel();
        FBStatusType = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        FBStatusError = new javax.swing.JLabel();
        F_IssueFB = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        FBIBC = new javax.swing.JComboBox<>();
        FBIEmp = new javax.swing.JComboBox<>();
        FBIWsu = new javax.swing.JComboBox<>();
        FBIBWst = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        IssNFBBack = new javax.swing.JButton();
        IssNFBSave = new javax.swing.JButton();
        FBIType = new javax.swing.JComboBox<>();
        FBICus = new javax.swing.JComboBox<>();
        FBIStyle = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        FBICol = new javax.swing.JComboBox<>();
        FBIDate = new org.jdesktop.swingx.JXDatePicker();
        FBIError = new javax.swing.JLabel();
        F_ListFBandAQL = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        ListFBandAQLItem = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        ListAQLandFBsearch = new javax.swing.JTextField();
        ListFBandAQLBack1 = new javax.swing.JButton();
        ListFBandAQLRefresh = new javax.swing.JButton();
        ListFBandAQLdelete = new javax.swing.JButton();
        ListFBandAQLedit = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        total3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        page3 = new javax.swing.JTextField();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        PPro = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        PProsearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel91 = new javax.swing.JLabel();
        PProItem = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        PProBack = new javax.swing.JButton();
        PProIssueNPP = new javax.swing.JButton();
        PProRefresh = new javax.swing.JButton();
        PProdelete = new javax.swing.JButton();
        PProedit = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        total1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        page1 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        P_IssuePP = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        PIType = new javax.swing.JComboBox<>();
        jLabel81 = new javax.swing.JLabel();
        PIWSu = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        PIBWSt = new javax.swing.JComboBox<>();
        jLabel83 = new javax.swing.JLabel();
        PIBC = new javax.swing.JComboBox<>();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        PIProno = new javax.swing.JLabel();
        IssuePPBack = new javax.swing.JButton();
        IssuePPSave = new javax.swing.JButton();
        PIBLPno = new javax.swing.JComboBox<>();
        jLabel148 = new javax.swing.JLabel();
        PICol = new javax.swing.JLabel();
        PIStyleno = new javax.swing.JLabel();
        PICus = new javax.swing.JLabel();
        PIDate = new org.jdesktop.swingx.JXDatePicker();
        PIError = new javax.swing.JLabel();
        Tag = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel89 = new javax.swing.JLabel();
        TagItem = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        Tagsearch = new javax.swing.JTextField();
        tagBack = new javax.swing.JButton();
        tagRefresh = new javax.swing.JButton();
        tagPrint = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        total2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        page2 = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel141 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        GenTag = new javax.swing.JPanel();
        GenTagNo = new javax.swing.JLabel();
        GenTColor = new javax.swing.JLabel();
        GenT1 = new javax.swing.JLabel();
        GenTBarcode = new javax.swing.JLabel();
        GenT3 = new javax.swing.JLabel();
        GenTCus = new javax.swing.JLabel();
        GenT5 = new javax.swing.JLabel();
        GenTStyle = new javax.swing.JLabel();
        GenT7 = new javax.swing.JLabel();

        jPanel1.setMinimumSize(new java.awt.Dimension(940, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(940, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        Pre_Pro.setMaximumSize(new java.awt.Dimension(940, 480));
        Pre_Pro.setMinimumSize(new java.awt.Dimension(940, 480));
        Pre_Pro.setPreferredSize(new java.awt.Dimension(940, 480));
        Pre_Pro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FBButton.setBackground(new java.awt.Color(48, 48, 240));
        FBButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBButton.setForeground(new java.awt.Color(255, 255, 255));
        FBButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-bath-48.png"))); // NOI18N
        FBButton.setText("First Bath");
        FBButton.setToolTipText("");
        FBButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FBButton.setMaximumSize(new java.awt.Dimension(120, 120));
        FBButton.setMinimumSize(new java.awt.Dimension(120, 120));
        FBButton.setPreferredSize(new java.awt.Dimension(120, 120));
        FBButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBButtonActionPerformed(evt);
            }
        });
        Pre_Pro.add(FBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 120, 120));

        Pro_PlanButton.setBackground(new java.awt.Color(48, 48, 240));
        Pro_PlanButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Pro_PlanButton.setForeground(new java.awt.Color(255, 255, 255));
        Pro_PlanButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-realtime-48.png"))); // NOI18N
        Pro_PlanButton.setText("Plan Process");
        Pro_PlanButton.setToolTipText("");
        Pro_PlanButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Pro_PlanButton.setMaximumSize(new java.awt.Dimension(120, 120));
        Pro_PlanButton.setMinimumSize(new java.awt.Dimension(120, 120));
        Pro_PlanButton.setPreferredSize(new java.awt.Dimension(120, 120));
        Pro_PlanButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Pro_PlanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Pro_PlanButtonActionPerformed(evt);
            }
        });
        Pre_Pro.add(Pro_PlanButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 120, 120));

        TagsButton.setBackground(new java.awt.Color(48, 48, 240));
        TagsButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TagsButton.setForeground(new java.awt.Color(255, 255, 255));
        TagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-tags-48.png"))); // NOI18N
        TagsButton.setText("Tags");
        TagsButton.setToolTipText("");
        TagsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TagsButton.setMaximumSize(new java.awt.Dimension(120, 120));
        TagsButton.setMinimumSize(new java.awt.Dimension(120, 120));
        TagsButton.setPreferredSize(new java.awt.Dimension(120, 120));
        TagsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        TagsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TagsButtonActionPerformed(evt);
            }
        });
        Pre_Pro.add(TagsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 120, 120));

        jLayeredPane1.add(Pre_Pro, "card3");
        Pre_Pro.getAccessibleContext().setAccessibleName("");
        Pre_Pro.getAccessibleContext().setAccessibleDescription("");

        FirstBath.setBackground(new java.awt.Color(255, 255, 255));
        FirstBath.setMinimumSize(new java.awt.Dimension(940, 480));
        FirstBath.setPreferredSize(new java.awt.Dimension(940, 480));
        FirstBath.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        FirstBath.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("First Bath");
        FirstBath.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Style No", "Color", "Batch No", "Customer/Factory", "Type", "Status", "Employee"
            }
        ));
        jTable1.setRowHeight(35);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        FirstBath.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        FBsearch.setText("Search..");
        FBsearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                FBsearchCaretUpdate(evt);
            }
        });
        FBsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FBsearchMouseClicked(evt);
            }
        });
        FBsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBsearchActionPerformed(evt);
            }
        });
        FirstBath.add(FBsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 180, 30));

        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Show :");
        FirstBath.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        FBItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        FBItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBItemActionPerformed(evt);
            }
        });
        FirstBath.add(FBItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("entries");
        FirstBath.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        FBBack.setBackground(new java.awt.Color(255, 51, 0));
        FBBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        FBBack.setForeground(new java.awt.Color(255, 255, 255));
        FBBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        FBBack.setText("Go Back");
        FBBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBBackActionPerformed(evt);
            }
        });
        FirstBath.add(FBBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        FBRefresh.setBackground(new java.awt.Color(0, 179, 50));
        FBRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FBRefresh.setForeground(new java.awt.Color(255, 255, 255));
        FBRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        FBRefresh.setText("Refresh");
        FBRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        FBRefresh.setIconTextGap(10);
        FBRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBRefreshActionPerformed(evt);
            }
        });
        FirstBath.add(FBRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 30));

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        FirstBath.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        jButton8.setBackground(new java.awt.Color(0, 0, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        FirstBath.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FirstBath.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("/");
        FirstBath.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        page.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageActionPerformed(evt);
            }
        });
        FirstBath.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jButton7.setBackground(new java.awt.Color(0, 0, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        FirstBath.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        FirstBath.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        FBdelete.setBackground(new java.awt.Color(255, 0, 0));
        FBdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FBdelete.setForeground(new java.awt.Color(255, 255, 255));
        FBdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/delete.png"))); // NOI18N
        FBdelete.setText("Delete");
        FBdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBdeleteActionPerformed(evt);
            }
        });
        FirstBath.add(FBdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, 100, 30));

        ListFBandAQL.setBackground(new java.awt.Color(0, 2, 240));
        ListFBandAQL.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ListFBandAQL.setForeground(new java.awt.Color(255, 255, 255));
        ListFBandAQL.setText("+  List First Bath and AQL");
        ListFBandAQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLActionPerformed(evt);
            }
        });
        FirstBath.add(ListFBandAQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 210, 30));

        IssueNFB.setBackground(new java.awt.Color(0, 2, 240));
        IssueNFB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IssueNFB.setForeground(new java.awt.Color(255, 255, 255));
        IssueNFB.setText("+  Issue New First Bath");
        IssueNFB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IssueNFBActionPerformed(evt);
            }
        });
        FirstBath.add(IssueNFB, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 190, 30));

        FBStatus.setBackground(new java.awt.Color(0, 2, 240));
        FBStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FBStatus.setForeground(new java.awt.Color(255, 255, 255));
        FBStatus.setText("+  First Bath Status");
        FBStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusActionPerformed(evt);
            }
        });
        FirstBath.add(FBStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 160, 30));

        AQL.setBackground(new java.awt.Color(0, 2, 240));
        AQL.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AQL.setForeground(new java.awt.Color(255, 255, 255));
        AQL.setText("+  AQL");
        AQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLActionPerformed(evt);
            }
        });
        FirstBath.add(AQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 80, 30));

        jButton11.setBackground(new java.awt.Color(71, 71, 116));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton11.setText("Print");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        FirstBath.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        FirstBath.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(FirstBath, "card5");

        F_AQLPanel.setBackground(new java.awt.Color(255, 255, 255));
        F_AQLPanel.setMinimumSize(new java.awt.Dimension(940, 480));
        F_AQLPanel.setPreferredSize(new java.awt.Dimension(940, 480));
        F_AQLPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        F_AQLPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel31.setText("AQL");
        F_AQLPanel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Type :");
        F_AQLPanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Number    :");
        F_AQLPanel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Style No :");
        F_AQLPanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Status :");
        F_AQLPanel.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        AQLBack.setBackground(new java.awt.Color(255, 51, 0));
        AQLBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AQLBack.setForeground(new java.awt.Color(255, 255, 255));
        AQLBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        AQLBack.setText("Go Back");
        AQLBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLBackActionPerformed(evt);
            }
        });
        F_AQLPanel.add(AQLBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        AQLSave.setBackground(new java.awt.Color(0, 2, 240));
        AQLSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AQLSave.setForeground(new java.awt.Color(255, 255, 255));
        AQLSave.setText("Update AQL Status");
        AQLSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLSaveActionPerformed(evt);
            }
        });
        F_AQLPanel.add(AQLSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, 190, 30));

        AQLType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Garments", "Gloves", "Fabrics" }));
        AQLType.setSelectedIndex(-1);
        AQLType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLTypeActionPerformed(evt);
            }
        });
        F_AQLPanel.add(AQLType, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 240, 40));

        AQLBLPno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLBLPnoActionPerformed(evt);
            }
        });
        F_AQLPanel.add(AQLBLPno, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 240, 40));

        AQLStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pass", "Fail" }));
        AQLStatus.setSelectedIndex(-1);
        AQLStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AQLStatusActionPerformed(evt);
            }
        });
        F_AQLPanel.add(AQLStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 90, 40));

        AQLStyleno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        F_AQLPanel.add(AQLStyleno, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, -1, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Customer :");
        F_AQLPanel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, -1));

        AQLCus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        F_AQLPanel.add(AQLCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Color :");
        F_AQLPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, -1, -1));

        AQLCol.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        F_AQLPanel.add(AQLCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, -1, -1));

        AQLError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AQLError.setForeground(new java.awt.Color(255, 0, 0));
        AQLError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        F_AQLPanel.add(AQLError, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 400, 40));

        jLayeredPane1.add(F_AQLPanel, "card2");

        F_FirstBathStatus.setBackground(new java.awt.Color(255, 255, 255));
        F_FirstBathStatus.setMinimumSize(new java.awt.Dimension(940, 480));
        F_FirstBathStatus.setPreferredSize(new java.awt.Dimension(940, 480));
        F_FirstBathStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel38.setText("First Bath Status");
        F_FirstBathStatus.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        F_FirstBathStatus.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Customer :");
        F_FirstBathStatus.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setText("Status :");
        F_FirstBathStatus.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, -1, -1));

        FBStatusStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pass", "Fail" }));
        FBStatusStatus.setSelectedIndex(-1);
        FBStatusStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusStatusActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 80, 40));

        FBStatusCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusCusActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 260, 40));

        FBStatusStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusStyleActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusStyle, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 260, 40));

        FBStatusBack.setBackground(new java.awt.Color(255, 51, 0));
        FBStatusBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        FBStatusBack.setForeground(new java.awt.Color(255, 255, 255));
        FBStatusBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        FBStatusBack.setText("Go Back");
        FBStatusBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusBackActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        Add.setBackground(new java.awt.Color(0, 2, 240));
        Add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Add.setForeground(new java.awt.Color(255, 255, 255));
        Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Add.png"))); // NOI18N
        Add.setText("Add Status");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 420, 130, 30));

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel143.setText("Style No :");
        F_FirstBathStatus.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, -1, -1));

        FBStatusCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusColActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 260, 40));

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel144.setText("    Color :");
        F_FirstBathStatus.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, -1, -1));

        FBStatusType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Garments", "Gloves", "Fabrics" }));
        FBStatusType.setSelectedIndex(-1);
        FBStatusType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBStatusTypeActionPerformed(evt);
            }
        });
        F_FirstBathStatus.add(FBStatusType, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 260, 40));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setText("Type :");
        F_FirstBathStatus.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, -1, -1));

        FBStatusError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBStatusError.setForeground(new java.awt.Color(255, 0, 0));
        F_FirstBathStatus.add(FBStatusError, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, 290, -1));

        jLayeredPane1.add(F_FirstBathStatus, "card6");

        F_IssueFB.setBackground(new java.awt.Color(255, 255, 255));
        F_IssueFB.setMinimumSize(new java.awt.Dimension(940, 480));
        F_IssueFB.setPreferredSize(new java.awt.Dimension(940, 480));
        F_IssueFB.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel53.setText("Issue New First Bath");
        F_IssueFB.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        F_IssueFB.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 940, 10));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel58.setText("Type");
        F_IssueFB.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setText("Employee");
        F_IssueFB.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setText("Date");
        F_IssueFB.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText("W. Supervisor");
        F_IssueFB.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, -1, -1));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("B/W Stores");
        F_IssueFB.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, -1, -1));

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel64.setText("B/C QC");
        F_IssueFB.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setText("Customer");
        F_IssueFB.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        FBIBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIBCActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIBC, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 240, 30));

        FBIEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIEmpActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 240, 30));

        FBIWsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIWsuActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIWsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 240, 30));

        FBIBWst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIBWstActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIBWst, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 240, 30));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setText("Style");
        F_IssueFB.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));
        F_IssueFB.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        IssNFBBack.setBackground(new java.awt.Color(255, 51, 0));
        IssNFBBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        IssNFBBack.setForeground(new java.awt.Color(255, 255, 255));
        IssNFBBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        IssNFBBack.setText("Go Back");
        IssNFBBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IssNFBBackActionPerformed(evt);
            }
        });
        F_IssueFB.add(IssNFBBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        IssNFBSave.setBackground(new java.awt.Color(0, 2, 240));
        IssNFBSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IssNFBSave.setForeground(new java.awt.Color(255, 255, 255));
        IssNFBSave.setText("Issue First Bath Process Plan");
        IssNFBSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IssNFBSaveActionPerformed(evt);
            }
        });
        F_IssueFB.add(IssNFBSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, 230, 30));

        FBIType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Garments", "Gloves", "Fabrics" }));
        FBIType.setSelectedIndex(-1);
        FBIType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBITypeActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIType, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 240, 30));

        FBICus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBICusActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBICus, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 240, 30));

        FBIStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIStyleActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIStyle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 240, 30));

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setText("Color");
        F_IssueFB.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        FBICol.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        FBICol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIColActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBICol, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, 240, 30));

        FBIDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBIDateActionPerformed(evt);
            }
        });
        F_IssueFB.add(FBIDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 240, 30));

        FBIError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FBIError.setForeground(new java.awt.Color(255, 0, 0));
        F_IssueFB.add(FBIError, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 370, 230, 40));

        jLayeredPane1.add(F_IssueFB, "card9");

        F_ListFBandAQL.setBackground(new java.awt.Color(255, 255, 255));
        F_ListFBandAQL.setMinimumSize(new java.awt.Dimension(940, 480));
        F_ListFBandAQL.setPreferredSize(new java.awt.Dimension(940, 480));
        F_ListFBandAQL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("List of First Bath and AQL Status");
        F_ListFBandAQL.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        F_ListFBandAQL.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Style No", "Color", "Batch No", "Customer", "Type", "First Bath", "AQL"
            }
        ));
        jTable3.setRowHeight(35);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        F_ListFBandAQL.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Show :");
        F_ListFBandAQL.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        ListFBandAQLItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        ListFBandAQLItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLItemActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListFBandAQLItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("entries");
        F_ListFBandAQL.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        ListAQLandFBsearch.setText("Search..");
        ListAQLandFBsearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                ListAQLandFBsearchCaretUpdate(evt);
            }
        });
        ListAQLandFBsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListAQLandFBsearchMouseClicked(evt);
            }
        });
        ListAQLandFBsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListAQLandFBsearchActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListAQLandFBsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 200, 30));

        ListFBandAQLBack1.setBackground(new java.awt.Color(255, 51, 0));
        ListFBandAQLBack1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ListFBandAQLBack1.setForeground(new java.awt.Color(255, 255, 255));
        ListFBandAQLBack1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        ListFBandAQLBack1.setText("Go Back");
        ListFBandAQLBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLBack1ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListFBandAQLBack1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, 30));

        ListFBandAQLRefresh.setBackground(new java.awt.Color(0, 179, 50));
        ListFBandAQLRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ListFBandAQLRefresh.setForeground(new java.awt.Color(255, 255, 255));
        ListFBandAQLRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        ListFBandAQLRefresh.setText("Refresh");
        ListFBandAQLRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ListFBandAQLRefresh.setIconTextGap(10);
        ListFBandAQLRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLRefreshActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListFBandAQLRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 120, 30));

        ListFBandAQLdelete.setBackground(new java.awt.Color(255, 0, 0));
        ListFBandAQLdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ListFBandAQLdelete.setForeground(new java.awt.Color(255, 255, 255));
        ListFBandAQLdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/delete.png"))); // NOI18N
        ListFBandAQLdelete.setText("Delete");
        ListFBandAQLdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLdeleteActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListFBandAQLdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, 100, 30));

        ListFBandAQLedit.setBackground(new java.awt.Color(0, 0, 255));
        ListFBandAQLedit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ListFBandAQLedit.setForeground(new java.awt.Color(255, 255, 255));
        ListFBandAQLedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        ListFBandAQLedit.setText("Edit");
        ListFBandAQLedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListFBandAQLeditActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(ListFBandAQLedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, -1, 30));

        jButton25.setBackground(new java.awt.Color(0, 0, 255));
        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        jButton26.setBackground(new java.awt.Color(0, 0, 255));
        jButton26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        total3.setForeground(new java.awt.Color(255, 255, 255));
        total3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        F_ListFBandAQL.add(total3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("/");
        F_ListFBandAQL.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        page3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page3ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(page3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jButton27.setBackground(new java.awt.Color(0, 0, 255));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        jButton28.setBackground(new java.awt.Color(0, 0, 255));
        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

        jButton10.setBackground(new java.awt.Color(71, 71, 116));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Print.png"))); // NOI18N
        jButton10.setText("Print");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        F_ListFBandAQL.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        F_ListFBandAQL.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(F_ListFBandAQL, "card9");

        PPro.setBackground(new java.awt.Color(255, 255, 255));
        PPro.setMinimumSize(new java.awt.Dimension(940, 480));
        PPro.setPreferredSize(new java.awt.Dimension(940, 480));
        PPro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Plan Process");
        PPro.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        PPro.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        PProsearch.setText("Search..");
        PProsearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                PProsearchCaretUpdate(evt);
            }
        });
        PProsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PProsearchMouseClicked(evt);
            }
        });
        PProsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProsearchActionPerformed(evt);
            }
        });
        PPro.add(PProsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, 170, 30));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Production No", "Customer/Factory", "Style No", "Color", "W Supervisor"
            }
        ));
        jTable2.setRowHeight(35);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        PPro.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 910, 270));

        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("entries");
        PPro.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        PProItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        PProItem.setSelectedIndex(2);
        PProItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProItemActionPerformed(evt);
            }
        });
        PPro.add(PProItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 70, 30));

        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Show :");
        PPro.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        PProBack.setBackground(new java.awt.Color(255, 51, 0));
        PProBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PProBack.setForeground(new java.awt.Color(255, 255, 255));
        PProBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        PProBack.setText("Go Back");
        PProBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProBackActionPerformed(evt);
            }
        });
        PPro.add(PProBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        PProIssueNPP.setBackground(new java.awt.Color(0, 2, 240));
        PProIssueNPP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PProIssueNPP.setForeground(new java.awt.Color(255, 255, 255));
        PProIssueNPP.setText("+  Issue New Production Plan");
        PProIssueNPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProIssueNPPActionPerformed(evt);
            }
        });
        PPro.add(PProIssueNPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 250, 30));

        PProRefresh.setBackground(new java.awt.Color(0, 179, 50));
        PProRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PProRefresh.setForeground(new java.awt.Color(255, 255, 255));
        PProRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        PProRefresh.setText("Refresh");
        PProRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        PProRefresh.setIconTextGap(10);
        PProRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProRefreshActionPerformed(evt);
            }
        });
        PPro.add(PProRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 120, 30));

        PProdelete.setBackground(new java.awt.Color(255, 0, 0));
        PProdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PProdelete.setForeground(new java.awt.Color(255, 255, 255));
        PProdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/delete.png"))); // NOI18N
        PProdelete.setText("Delete");
        PProdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProdeleteActionPerformed(evt);
            }
        });
        PPro.add(PProdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, 100, 30));

        PProedit.setBackground(new java.awt.Color(0, 0, 255));
        PProedit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PProedit.setForeground(new java.awt.Color(255, 255, 255));
        PProedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        PProedit.setText("Edit");
        PProedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PProeditActionPerformed(evt);
            }
        });
        PPro.add(PProedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, -1, 30));

        jButton13.setBackground(new java.awt.Color(0, 0, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        PPro.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        jButton14.setBackground(new java.awt.Color(0, 0, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        PPro.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        total1.setForeground(new java.awt.Color(255, 255, 255));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PPro.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("/");
        PPro.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        page1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page1ActionPerformed(evt);
            }
        });
        PPro.add(page1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jButton15.setBackground(new java.awt.Color(0, 0, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        PPro.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        jButton16.setBackground(new java.awt.Color(0, 0, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        PPro.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

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
        PPro.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        PPro.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(PPro, "card7");

        P_IssuePP.setBackground(new java.awt.Color(255, 255, 255));
        P_IssuePP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel47.setText("Issue New Production Plan");
        P_IssuePP.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        P_IssuePP.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel78.setText("Production Plan No");
        P_IssuePP.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel79.setText("Date");
        P_IssuePP.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel80.setText("Type");
        P_IssuePP.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, -1, -1));

        PIType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Garments", "Gloves", "Fabrics" }));
        PIType.setSelectedIndex(-1);
        PIType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PITypeActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIType, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, 240, 30));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel81.setText("W. Supervisor");
        P_IssuePP.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, -1, -1));

        PIWSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIWSuActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIWSu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 240, 30));

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel82.setText("B/W Stores");
        P_IssuePP.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, -1, -1));

        PIBWSt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIBWStActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIBWSt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 240, 30));

        jLabel83.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel83.setText("B/C QC");
        P_IssuePP.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        PIBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIBCActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIBC, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 240, 30));
        P_IssuePP.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 940, 10));

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel84.setText("Customer");
        P_IssuePP.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel85.setText("Style");
        P_IssuePP.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel86.setText("Color");
        P_IssuePP.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, -1, -1));

        PIProno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        P_IssuePP.add(PIProno, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        IssuePPBack.setBackground(new java.awt.Color(255, 51, 0));
        IssuePPBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        IssuePPBack.setForeground(new java.awt.Color(255, 255, 255));
        IssuePPBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        IssuePPBack.setText("Go Back");
        IssuePPBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IssuePPBackActionPerformed(evt);
            }
        });
        P_IssuePP.add(IssuePPBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        IssuePPSave.setBackground(new java.awt.Color(0, 0, 255));
        IssuePPSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IssuePPSave.setForeground(new java.awt.Color(255, 255, 255));
        IssuePPSave.setText("Issue Process Plan");
        IssuePPSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IssuePPSaveActionPerformed(evt);
            }
        });
        P_IssuePP.add(IssuePPSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 430, 160, 30));

        PIBLPno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Garments", "Gloves", "Fabrics" }));
        PIBLPno.setSelectedIndex(-1);
        PIBLPno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIBLPnoActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIBLPno, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 240, 30));

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel148.setText("Number");
        P_IssuePP.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, -1, -1));

        PICol.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        P_IssuePP.add(PICol, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        PIStyleno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        P_IssuePP.add(PIStyleno, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        PICus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        P_IssuePP.add(PICus, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, -1, -1));

        PIDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIDateActionPerformed(evt);
            }
        });
        P_IssuePP.add(PIDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 240, 30));

        PIError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PIError.setForeground(new java.awt.Color(255, 0, 0));
        PIError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        P_IssuePP.add(PIError, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, 290, 50));

        jLayeredPane1.add(P_IssuePP, "card8");

        Tag.setBackground(new java.awt.Color(255, 255, 255));
        Tag.setMinimumSize(new java.awt.Dimension(940, 480));
        Tag.setPreferredSize(new java.awt.Dimension(940, 480));
        Tag.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Tags");
        Tag.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        Tag.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 940, 10));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Tag No", "Customer", "Style No", "Color"
            }
        ));
        jTable4.setRowHeight(35);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        Tag.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 910, 260));

        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Show :");
        Tag.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        TagItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        TagItem.setSelectedIndex(2);
        TagItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TagItemActionPerformed(evt);
            }
        });
        Tag.add(TagItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 30));

        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("entries");
        Tag.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        Tagsearch.setText("Search..");
        Tagsearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TagsearchCaretUpdate(evt);
            }
        });
        Tagsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TagsearchMouseClicked(evt);
            }
        });
        Tagsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TagsearchActionPerformed(evt);
            }
        });
        Tag.add(Tagsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 180, 30));

        tagBack.setBackground(new java.awt.Color(255, 51, 0));
        tagBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tagBack.setForeground(new java.awt.Color(255, 255, 255));
        tagBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Back.png"))); // NOI18N
        tagBack.setText("Go Back");
        tagBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagBackActionPerformed(evt);
            }
        });
        Tag.add(tagBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 100, -1));

        tagRefresh.setBackground(new java.awt.Color(0, 179, 50));
        tagRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tagRefresh.setForeground(new java.awt.Color(255, 255, 255));
        tagRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/refresh.png"))); // NOI18N
        tagRefresh.setText("Refresh");
        tagRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        tagRefresh.setIconTextGap(10);
        tagRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagRefreshActionPerformed(evt);
            }
        });
        Tag.add(tagRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 120, 30));

        tagPrint.setBackground(new java.awt.Color(0, 0, 255));
        tagPrint.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tagPrint.setForeground(new java.awt.Color(255, 255, 255));
        tagPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/edit.png"))); // NOI18N
        tagPrint.setText("Generate Tag");
        tagPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagPrintActionPerformed(evt);
            }
        });
        Tag.add(tagPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 420, -1, 30));

        jButton19.setBackground(new java.awt.Color(0, 0, 255));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-14 (2).png"))); // NOI18N
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        Tag.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 30, 30));

        jButton20.setBackground(new java.awt.Color(0, 0, 255));
        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-end-filled-14.png"))); // NOI18N
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        Tag.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 30, 30));

        total2.setForeground(new java.awt.Color(255, 255, 255));
        total2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tag.add(total2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 30, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("/");
        Tag.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 30, 30));

        page2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                page2ActionPerformed(evt);
            }
        });
        Tag.add(page2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 30, 30));

        jButton21.setBackground(new java.awt.Color(0, 0, 255));
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14.png"))); // NOI18N
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        Tag.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 30, 30));

        jButton22.setBackground(new java.awt.Color(0, 0, 255));
        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/production/Images/icons8-skip-to-start-14 (1).png"))); // NOI18N
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        Tag.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 30, 30));

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
        Tag.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 100, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        Tag.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jLayeredPane1.add(Tag, "card9");

        jPanel1.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -5, 950, 490));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 82, 690, 10));

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText("PROCESS");
        jPanel1.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 390, 40));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 10, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Production Process");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        jLabel142.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Production.png"))); // NOI18N
        jLabel142.setText("jLabel3");
        jPanel1.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 50, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pre-Production Process");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/IMG-20180315-WA0017.jpg"))); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 950, 490));

        GenTag.setBackground(new java.awt.Color(255, 255, 255));
        GenTag.setMaximumSize(new java.awt.Dimension(300, 500));
        GenTag.setMinimumSize(new java.awt.Dimension(300, 500));
        GenTag.setPreferredSize(new java.awt.Dimension(300, 500));
        GenTag.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        GenTagNo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        GenTag.add(GenTagNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 90, 30));

        GenTColor.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GenTag.add(GenTColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 240, 30));

        GenT1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        GenT1.setText("Tag No");
        GenTag.add(GenT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        GenTBarcode.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GenTBarcode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GenTag.add(GenTBarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 240, 80));

        GenT3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        GenT3.setText("Customer : ");
        GenTag.add(GenT3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        GenTCus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GenTag.add(GenTCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 240, 30));

        GenT5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        GenT5.setText("Style No :");
        GenTag.add(GenT5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        GenTStyle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GenTag.add(GenTStyle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 240, 30));

        GenT7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        GenT7.setText("Color :");
        GenTag.add(GenT7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jPanel1.add(GenTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

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

    private void FBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBButtonActionPerformed
        // TODO add your handling code here:
        FirstBath.setVisible(true);
        Home.process.setVisible(false);
        Pre_Pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addGlovetoTable();
            addFabrictoTable();
            addGarmenttoTable();
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FBButtonActionPerformed

    private void Pro_PlanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Pro_PlanButtonActionPerformed
        // TODO add your handling code here:
        PPro.setVisible(true);
        Home.process.setVisible(false);
        Pre_Pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();
    }//GEN-LAST:event_Pro_PlanButtonActionPerformed

    private void TagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TagsButtonActionPerformed
        // TODO add your handling code here:
        Tag.setVisible(true);
        Home.process.setVisible(false);
        Pre_Pro.setVisible(false);
        jLabel2.hide();
        jLabel3.hide();
        Background.hide();

        try {
            addProcesstoTagTable();
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TagsButtonActionPerformed

    private void FBsearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_FBsearchCaretUpdate
        // TODO add your handling code here:       
        String sh = FBsearch.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addFBTable("SELECT * FROM firstbath WHERE Style_No like '%"+sh+"%' or Color like '%"+sh+"%' or Date like '%"+sh+"%' or Customer like '%"+sh+"%' or Type like '%"+sh+"%' or Employee like '%"+sh+"%'");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBsearchCaretUpdate

    private void FBsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FBsearchMouseClicked
        // TODO add your handling code here:
        FBsearch.setText(null);
    }//GEN-LAST:event_FBsearchMouseClicked

    private void FBsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FBsearchActionPerformed

    private void FBItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBItemActionPerformed
        // TODO add your handling code here:
        if (FBItem.getSelectedItem().equals("")) {
            lim = 15;
            FBItem.setSelectedItem("15");
            pg = 1;
            page.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) FBItem.getSelectedItem());
            pg = 1;
            page.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBItemActionPerformed

    private void FBBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBBackActionPerformed
        // TODO add your handling code here:
        Pre_Pro.setVisible(true);
        Home.process.setVisible(false);
        FirstBath.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_FBBackActionPerformed

    private void FBRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBRefreshActionPerformed
        try {
            FBsearch.setText("Search..");
            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FBRefreshActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        pg = maxPages;
        page.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if (pg < maxPages) {
            pg++;
            page.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void pageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageActionPerformed
        // TODO add your handling code here:
        int p = Integer.parseInt((String) page.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_pageActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (pg > 1) {
            pg--;
            page.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        pg = 1;
        page.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(FirstBath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void FBdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBdeleteActionPerformed
        // TODO add your handling code here:
        int r = jTable1.getSelectedRow();
        String s = (String) jTable1.getValueAt(r, 0);
        String co = (String) jTable1.getValueAt(r, 1);
        String c = (String) jTable1.getValueAt(r, 2);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Selected First Bath information will be deleted", "Warning", dialogButton);

        if (dialogResult == 0) {
            try {
                con.prepareStatement("DELETE FROM firstbath WHERE Style_No='" + s + "' AND Customer='" + c + "' AND Color = '" + co + "'").execute();
                con.prepareStatement("UPDATE listaqlandfb set First_Bath = 0 ,AQL = 0 WHERE Style_No='" + s + "' AND Customer='" + c + "' AND Color = '" + co + "'").execute();
                JOptionPane.showMessageDialog(null, "First Bath deleted", "Done", JOptionPane.INFORMATION_MESSAGE);
                updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBdeleteActionPerformed

    private void ListFBandAQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLActionPerformed
        // TODO add your handling code here:
        F_ListFBandAQL.setVisible(true);
        FirstBath.setVisible(false);
    }//GEN-LAST:event_ListFBandAQLActionPerformed

    private void IssueNFBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IssueNFBActionPerformed
        // TODO add your handling code here:
        F_IssueFB.setVisible(true);
        FirstBath.setVisible(false);
    }//GEN-LAST:event_IssueNFBActionPerformed

    private void FBStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusActionPerformed
        // TODO add your handling code here:
        F_FirstBathStatus.setVisible(true);
        FirstBath.setVisible(false);
    }//GEN-LAST:event_FBStatusActionPerformed

    private void AQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLActionPerformed
        // TODO add your handling code here:
        F_AQLPanel.setVisible(true);
        FirstBath.setVisible(false);
    }//GEN-LAST:event_AQLActionPerformed

    private void AQLBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLBackActionPerformed
        // TODO add your handling code here:
        F_AQLPanel.setVisible(false);
        FirstBath.setVisible(true);
    }//GEN-LAST:event_AQLBackActionPerformed

    private void AQLSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLSaveActionPerformed
        try {
            //Code is for what happens when Save Button in AQL is clicked
            //Below 3 lines gets the values from AQL UI
            if (AQLValidate()) {
                String type = String.valueOf(AQLType.getSelectedItem());
                String blp = String.valueOf(AQLBLPno.getSelectedItem());
                String status = String.valueOf(AQLStatus.getSelectedItem());

                if (status.equals("Pass")) { //If Status is set to pass
                    try {
                        if (type.equals("Gloves")) { //If type is set to Gloves the below line updates the SQLGlove table's aql value                    
                            con.prepareStatement("UPDATE SPLGlove set AQL_FB_Status = 1 WHERE lotNo = '" + blp + "'").execute();
                        } else if (type.equals("Garment")) { //If type is set to Garments the below line updates the SQLGarment table's aql value                    
                            con.prepareStatement("UPDATE SPLGarment set AQL_FB_Status = 1 WHERE portionNo = '" + blp + "'").execute();
                        } else { //If type is set to Fabrics the below line updates the SQLFabric table's aql value                     
                            con.prepareStatement("UPDATE SPLFabric set AQL_FB_Status = 1 WHERE batchNo = '" + blp + "'").execute();
                        }
                        //Updates listaqlandfb table and show message
                        con.prepareStatement("UPDATE listaqlandfb set AQL = 1 WHERE BLP_No = '" + blp + "'").execute();
                        JOptionPane.showMessageDialog(null, "AQL Values Updated Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);

                        //Refreshes the listaqlandfb Table
                        updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                        addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");

                        AQLType.setSelectedIndex(-1);
                        AQLBLPno.setSelectedIndex(-1);
                        AQLStatus.setSelectedIndex(-1);
                        AQLStyleno.setText(null);
                        AQLCus.setText(null);
                        AQLCol.setText(null);

                    } catch (SQLException ex) {
                        Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //Shows message
                    JOptionPane.showMessageDialog(null, "AQL Values Updated Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);

                    AQLType.setSelectedIndex(-1);
                    AQLBLPno.setSelectedIndex(-1);
                    AQLStatus.setSelectedIndex(-1);
                    AQLStyleno.setText(null);
                    AQLCus.setText(null);
                    AQLCol.setText(null);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_AQLSaveActionPerformed

    private void FBStatusBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusBackActionPerformed
        // TODO add your handling code here:
        F_FirstBathStatus.setVisible(false);
        FirstBath.setVisible(true);
    }//GEN-LAST:event_FBStatusBackActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        try {
            //Here the Code Changes the First Bath Status
            //Gets Respective data from the UI
            if (FBStatusValidate()) { //Checks if the user have passed all the validations

                String cus = String.valueOf(FBStatusCus.getSelectedItem());
                String style = String.valueOf(FBStatusStyle.getSelectedItem());
                String col = String.valueOf(FBStatusCol.getSelectedItem());
                String status = String.valueOf(FBStatusStatus.getSelectedItem());
                String type = String.valueOf(FBStatusType.getSelectedItem());

                try {

                    if (type.equals("Gloves")) {
                        if (status.equals("Fail")) { //Code on what to do if Type = Gloves and FB status is failed
                            JOptionPane.showMessageDialog(null, "Results Saved", "Done", JOptionPane.INFORMATION_MESSAGE);

                            con.prepareStatement("DELETE FROM processplan WHERE Style_No='" + style + "' AND Customer='" + cus + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL").execute();

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                            FBStatusCus.setSelectedIndex(-1);
                            FBStatusStyle.setSelectedIndex(-1);
                            FBStatusCol.setSelectedIndex(-1);
                            FBStatusStatus.setSelectedIndex(-1);
                        } else //Code on what to do if Type = Gloves and FB status is Passed
                        {
                            String emp;
                            ResultSet rs = con.createStatement().executeQuery("SELECT Employee FROM processplan WHERE  Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL");
                            rs.next();
                            emp = rs.getString("Employee");

                            con.prepareStatement("UPDATE listaqlandfb set First_Bath= 1 WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "' AND Type = '" + type + "'").execute();
                            con.prepareStatement("INSERT INTO firstbath (Style_No,Color,Date,Customer,Type,Employee) VALUES('" + style + "','None','" + java.time.LocalDate.now() + "','" + cus + "','" + type + "','" + emp + "')").execute();
                            JOptionPane.showMessageDialog(null, "Results Added to First Bath and Updated in Respective places", "Done", JOptionPane.INFORMATION_MESSAGE);

                            con.prepareStatement("UPDATE processplan SET Finished = 1 WHERE Style_No='" + style + "' AND Customer='" + cus + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL").execute();

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");

                            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");

                            FBStatusCus.setSelectedIndex(-1);
                            FBStatusStyle.setSelectedIndex(-1);
                            FBStatusCol.setSelectedIndex(-1);
                            FBStatusStatus.setSelectedIndex(-1);
                            FBStatusType.setSelectedIndex(-1);

                            F_FirstBathStatus.setVisible(false);
                            FirstBath.setVisible(true);
                        }
                    } else {
                        if (status.equals("Fail")) { //Code on what to do if Type = Fabric or Garment and FB status is failed
                            JOptionPane.showMessageDialog(null, "Results Saved", "Done", JOptionPane.INFORMATION_MESSAGE);

                            con.prepareStatement("DELETE FROM processplan WHERE Style_No='" + style + "' AND Customer='" + cus + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL").execute();

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                            FBStatusCus.setSelectedIndex(-1);
                            FBStatusStyle.setSelectedIndex(-1);
                            FBStatusCol.setSelectedIndex(-1);
                            FBStatusStatus.setSelectedIndex(-1);
                            FBStatusType.setSelectedIndex(-1);
                        } else //Code on what to do if Type = Fabric or Garment and FB status is Passed
                        {
                            String emp1;
                            ResultSet rs = con.createStatement().executeQuery("SELECT Employee FROM processplan WHERE  Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL");
                            rs.next();
                            emp1 = rs.getString("Employee");

                            con.prepareStatement("UPDATE listaqlandfb set First_Bath= 1 WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "' AND Type = '" + type + "'").execute();
                            con.prepareStatement("INSERT INTO firstbath (Style_No,Color,Date,Customer,Type,Employee) VALUES('" + style + "','" + col + "','" + java.time.LocalDate.now() + "','" + cus + "','" + type + "','" + emp1 + "')").execute();
                            JOptionPane.showMessageDialog(null, "Results Added to First Bath and Updated in Respective places", "Done", JOptionPane.INFORMATION_MESSAGE);

                            con.prepareStatement("UPDATE processplan SET Finished = 1 WHERE Style_No='" + style + "' AND Customer='" + cus + "' AND Color = '" + col + "' AND Type = '" + type + "' AND BLP_No IS NULL").execute();

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                            updateMax("SELECT COUNT(*) AS count FROM firstbath", total, page);
                            addFBTable("SELECT * FROM firstbath LIMIT " + lim + " OFFSET " + offset + "");

                            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");

                            FBStatusCus.setSelectedIndex(-1);
                            FBStatusStyle.setSelectedIndex(-1);
                            FBStatusCol.setSelectedIndex(-1);
                            FBStatusStatus.setSelectedIndex(-1);
                            FBStatusType.setSelectedIndex(-1);

                            F_FirstBathStatus.setVisible(false);
                            FirstBath.setVisible(true);
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_AddActionPerformed

    private void IssNFBBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IssNFBBackActionPerformed
        // TODO add your handling code here:
        F_IssueFB.setVisible(false);
        FirstBath.setVisible(true);
    }//GEN-LAST:event_IssNFBBackActionPerformed

    private void IssNFBSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IssNFBSaveActionPerformed
        try {
            //Get values of Issue First Bath UI

            if (FBIssueValidate()) {
                String type = String.valueOf(FBIType.getSelectedItem());
                String cus = String.valueOf(FBICus.getSelectedItem());
                String style = String.valueOf(FBIStyle.getSelectedItem());
                String col = String.valueOf(FBICol.getSelectedItem());
                String emp = String.valueOf(FBIEmp.getSelectedItem());
                String wsu = String.valueOf(FBIWsu.getSelectedItem());
                String bcqc = String.valueOf(FBIBC.getSelectedItem());
                String bwst = String.valueOf(FBIBWst.getSelectedItem());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String Date = df.format(FBIDate.getDate());
                int count;

                if (type.equals("Gloves")) {
                    try {
                        //Checks whether processplan already exsists
                        ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM processplan WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = 'None' AND BLP_No IS NULL");
                        rs.next();
                        count = rs.getInt("count");
                        if (count > 0) //What to do if processplan exsist
                        {
                            JOptionPane.showMessageDialog(null, "Process Plan already EXSITS", "Error", JOptionPane.ERROR_MESSAGE);
                            FBIType.setSelectedIndex(-1);
                            FBICus.setSelectedIndex(-1);
                            FBIStyle.setSelectedIndex(-1);
                            FBICol.setSelectedIndex(-1);
                            FBIEmp.setSelectedIndex(-1);
                            FBIWsu.setSelectedIndex(-1);
                            FBIBC.setSelectedIndex(-1);
                            FBIBWst.setSelectedIndex(-1);
                        } else { //what to do if process plan does not exsist
                            con.prepareStatement("INSERT INTO processplan (BLP_No,Style_No,Customer,Color,Date,Type,W_Supervisor,Employee,BC_QC,BW_Store) VALUES(NULL,'" + style + "','" + cus + "','None','" + Date + "','" + type + "','" + wsu + "','" + emp + "','" + bcqc + "','" + bwst + "')").execute();
                            JOptionPane.showMessageDialog(null, "First Bath Added to Process Plan", "Done", JOptionPane.INFORMATION_MESSAGE);

                            FBIType.setSelectedIndex(-1);
                            FBICus.setSelectedIndex(-1);
                            FBIStyle.setSelectedIndex(-1);
                            FBICol.setSelectedIndex(-1);
                            FBIEmp.setSelectedIndex(-1);
                            FBIWsu.setSelectedIndex(-1);
                            FBIBC.setSelectedIndex(-1);
                            FBIBWst.setSelectedIndex(-1);
                        }

                        updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                        addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
                    } catch (SQLException ex) {
                        Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) as 'count' FROM processplan WHERE Customer = '" + cus + "' AND Style_No = '" + style + "' AND Color = '" + col + "' AND BLP_No IS NULL");
                        rs.next();
                        count = rs.getInt("count");
                        if (count > 0) {
                            JOptionPane.showMessageDialog(null, "Process Plan already EXSITS", "Error", JOptionPane.ERROR_MESSAGE);
                            FBIType.setSelectedIndex(-1);
                            FBICus.setSelectedIndex(-1);
                            FBIStyle.setSelectedIndex(-1);
                            FBICol.setSelectedIndex(-1);
                            FBIEmp.setSelectedIndex(-1);
                            FBIWsu.setSelectedIndex(-1);
                            FBIBC.setSelectedIndex(-1);
                            FBIBWst.setSelectedIndex(-1);
                        } else {
                            con.prepareStatement("INSERT INTO processplan (BLP_No,Style_No,Customer,Color,Date,Type,W_Supervisor,Employee,BC_QC,BW_Store) VALUES(NULL,'" + style + "','" + cus + "','" + col + "','" + Date + "','" + type + "','" + wsu + "','" + emp + "','" + bcqc + "','" + bwst + "')").execute();
                            JOptionPane.showMessageDialog(null, "First Bath Added to Process Plan", "Done", JOptionPane.INFORMATION_MESSAGE);

                            FBIType.setSelectedIndex(-1);
                            FBICus.setSelectedIndex(-1);
                            FBIStyle.setSelectedIndex(-1);
                            FBICol.setSelectedIndex(-1);
                            FBIEmp.setSelectedIndex(-1);
                            FBIWsu.setSelectedIndex(-1);
                            FBIBC.setSelectedIndex(-1);
                            FBIBWst.setSelectedIndex(-1);
                        }

                        updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                        addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
                    } catch (SQLException ex) {
                        Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_IssNFBSaveActionPerformed

    private void ListFBandAQLItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLItemActionPerformed
        // TODO add your handling code here:
        if (ListFBandAQLItem.getSelectedItem().equals("")) {
            lim = 15;
            ListFBandAQLItem.setSelectedItem("15");
            pg = 1;
            page3.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) ListFBandAQLItem.getSelectedItem());
            pg = 1;
            page3.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ListFBandAQLItemActionPerformed

    private void ListAQLandFBsearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_ListAQLandFBsearchCaretUpdate
        // TODO add your handling code here:
        String sh = ListAQLandFBsearch.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addAQLandFBTable("SELECT * FROM listaqlandfb WHERE BLP_No like '%"+sh+"%' or Style_No like '%"+sh+"%' or Color like '%"+sh+"%' or Customer like '%"+sh+"%' or Type like '%"+sh+"%' or First_Bath like '%"+sh+"%' or AQL like '%"+sh+"%'");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ListAQLandFBsearchCaretUpdate

    private void ListAQLandFBsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListAQLandFBsearchMouseClicked
        // TODO add your handling code here:
        ListAQLandFBsearch.setText(null);
    }//GEN-LAST:event_ListAQLandFBsearchMouseClicked

    private void ListAQLandFBsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListAQLandFBsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ListAQLandFBsearchActionPerformed

    private void ListFBandAQLBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLBack1ActionPerformed
        // TODO add your handling code here:
        F_ListFBandAQL.setVisible(false);
        FirstBath.setVisible(true);
    }//GEN-LAST:event_ListFBandAQLBack1ActionPerformed

    private void ListFBandAQLRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLRefreshActionPerformed
        // TODO add your handling code here:
        try {
            ListAQLandFBsearch.setText("Search..");
            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ListFBandAQLRefreshActionPerformed

    private void ListFBandAQLdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLdeleteActionPerformed

        int r = jTable3.getSelectedRow();
        String b = (String) jTable3.getValueAt(r, 0);
        String s = (String) jTable3.getValueAt(r, 1);
        int count;

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM processplan WHERE BLP_No = '" + b + "'");
            rs.next();
            count = rs.getInt("count");

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Cannot DELETE the SELECTED row because PROCESS PLAN EXSIST for it", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Selected First Bath & AQL information will be deleted", "Warning", dialogButton);

                if (dialogResult == 0) {
                    try {
                        con.prepareStatement("DELETE FROM listaqlandfb WHERE BLP_No='" + b + "' AND Style_No='" + s + "'").execute();

                        JOptionPane.showMessageDialog(null, "First Bath and AQL deleted", "Done", JOptionPane.INFORMATION_MESSAGE);
                        updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                        addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
                    } catch (SQLException ex) {
                        Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_ListFBandAQLdeleteActionPerformed

    private void ListFBandAQLeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListFBandAQLeditActionPerformed
        int r = jTable3.getSelectedRow();
        String blp = (String) jTable3.getValueAt(r, 0);
        String style = (String) jTable3.getValueAt(r, 1);
        String col = (String) jTable3.getValueAt(r, 2);
        String cus = (String) jTable3.getValueAt(r, 3);
        String type = (String) jTable3.getValueAt(r, 4);
        String f = (String) jTable3.getValueAt(r, 5);
        String a = (String) jTable3.getValueAt(r, 6);
        int fb = Integer.parseInt(f);
        int aql = Integer.parseInt(a);
        int count;

        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery("SELECT COUNT(*) AS 'count' FROM processplan WHERE BLP_No = '" + blp + "'");
            rs.next();
            count = rs.getInt("count");

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Cannot EDIT the SELECTED row because PROCESS PLAN EXSIST for it", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    editlistAQLandFB ed = new editlistAQLandFB(blp, style, col, cus, type, fb, aql);
                    ed.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_ListFBandAQLeditActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        pg = maxPages;
        page3.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        if (pg < maxPages) {
            pg++;
            page3.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void page3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page3ActionPerformed
        // TODO add your handling code here:
        int p = Integer.parseInt((String) page3.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
                addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page3ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        if (pg > 1) {
            pg--;
            page3.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        pg = 1;
        page3.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM listaqlandfb", total3, page3);
            addAQLandFBTable("SELECT * FROM listaqlandfb LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void PProsearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_PProsearchCaretUpdate
        // TODO add your handling code here:
        String sh = PProsearch.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addPProTable("SELECT * FROM processplan WHERE Process_No like '%"+sh+"%' or BLP_No like '%"+sh+"%' or Style_No like '%"+sh+"%' or Color like '%"+sh+"%' or Customer like '%"+sh+"%' or Date like '%"+sh+"%' or Type like '%"+sh+"%' or W_Supervisor like '%"+sh+"%' or Employee like '%"+sh+"%'");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PProsearchCaretUpdate

    private void PProsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PProsearchMouseClicked
        // TODO add your handling code here:
        PProsearch.setText(null);
    }//GEN-LAST:event_PProsearchMouseClicked

    private void PProsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PProsearchActionPerformed

    private void PProItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProItemActionPerformed
        // TODO add your handling code here:
        if (PProItem.getSelectedItem().equals("")) {
            lim = 15;
            PProItem.setSelectedItem("15");
            pg = 1;
            page1.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) PProItem.getSelectedItem());
            pg = 1;
            page1.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PProItemActionPerformed

    private void PProBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProBackActionPerformed
        // TODO add your handling code here:
        Pre_Pro.setVisible(true);
        Home.process.setVisible(false);
        PPro.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_PProBackActionPerformed

    private void PProIssueNPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProIssueNPPActionPerformed
        // TODO add your handling code here:
        P_IssuePP.setVisible(true);
        PPro.setVisible(false);
    }//GEN-LAST:event_PProIssueNPPActionPerformed

    private void PProRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProRefreshActionPerformed
        // TODO add your handling code here:
        try {
            PProsearch.setText("Search..");
            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PProRefreshActionPerformed

    private void PProdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProdeleteActionPerformed
        // TODO add your handling code here:
        int r = jTable2.getSelectedRow();
        String p = (String) jTable2.getValueAt(r, 0);
        String b = (String) jTable2.getValueAt(r, 1);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Selected Process Plan information will be deleted", "Warning", dialogButton);

        if (dialogResult == 0) {
            try {
                con.prepareStatement("DELETE FROM processplan WHERE Process_No='" + p + "'").execute();
                if (!b.equals("")) {
                    con.prepareStatement("DELETE FROM tag WHERE BLP_No='" + b + "'").execute();
                }

                JOptionPane.showMessageDialog(null, "Process Plan deleted", "Done", JOptionPane.INFORMATION_MESSAGE);
                updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
                updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
                addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PProdeleteActionPerformed

    private void PProeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PProeditActionPerformed
        int r = jTable2.getSelectedRow();
        String pno = (String) jTable2.getValueAt(r, 0);
        String blp = (String) jTable2.getValueAt(r, 1);
        String style = (String) jTable2.getValueAt(r, 2);
        String cus = (String) jTable2.getValueAt(r, 3);
        String col = (String) jTable2.getValueAt(r, 4);
        String date = (String) jTable2.getValueAt(r, 5);
        String type = (String) jTable2.getValueAt(r, 6);
        String ws = (String) jTable2.getValueAt(r, 7);
        String emp = (String) jTable2.getValueAt(r, 8);
        String bwst;
        String bcqc;
        int ppno = Integer.parseInt(pno);

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT BC_QC,BW_Store FROM processplan WHERE Process_No = " + ppno + "");
            rs.next();
            bcqc = rs.getString("BC_QC");
            bwst = rs.getString("BW_Store");

            editProcessPlan ed = new editProcessPlan(ppno, blp, style, cus, col, date, type, ws, emp, bcqc, bwst);
            ed.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_PProeditActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        pg = maxPages;
        page1.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        if (pg < maxPages) {
            pg++;
            page1.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void page1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page1ActionPerformed
        // TODO add your handling code here:
        int p = Integer.parseInt((String) page1.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if (pg > 1) {
            pg--;
            page1.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        pg = 1;
        page1.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void IssuePPBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IssuePPBackActionPerformed
        // TODO add your handling code here:
        P_IssuePP.setVisible(false);
        PPro.setVisible(true);
    }//GEN-LAST:event_IssuePPBackActionPerformed

    private void TagItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TagItemActionPerformed
        if (TagItem.getSelectedItem().equals("")) {
            lim = 15;
            TagItem.setSelectedItem("15");
            pg = 1;
            page2.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
                addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lim = Integer.parseInt((String) TagItem.getSelectedItem());
            pg = 1;
            page2.setText(Integer.toString(pg));
            if (pg == 0) {
                offset = (pg) * lim;
            } else {
                offset = (pg - 1) * lim;
            }
            try {
                updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
                addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_TagItemActionPerformed

    private void TagsearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TagsearchCaretUpdate
        String sh = Tagsearch.getText();
        if (sh.equals("")) {
            try {
                updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
                addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                addTagTable("SELECT * FROM tag WHERE Tag_No like '%"+sh+"%' or BLP_No like '%"+sh+"%' or Date like '%"+sh+"%' or Customer like '%"+sh+"%' or Style_No like '%"+sh+"%' or Color like '%"+sh+"%' or Tag_Status like '%"+sh+"%'");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_TagsearchCaretUpdate

    private void TagsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TagsearchMouseClicked
        Tagsearch.setText(null);
    }//GEN-LAST:event_TagsearchMouseClicked

    private void TagsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TagsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TagsearchActionPerformed

    private void tagBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagBackActionPerformed
        // TODO add your handling code here:
        Pre_Pro.setVisible(true);
        Home.process.setVisible(false);
        Tag.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        Background.setVisible(true);
    }//GEN-LAST:event_tagBackActionPerformed

    private void tagRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagRefreshActionPerformed
        try {
            Tagsearch.setText("Search..");
            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tagRefreshActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        pg = maxPages;
        page2.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        if (pg < maxPages) {
            pg++;
            page2.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void page2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page2ActionPerformed
        int p = Integer.parseInt((String) page1.getText());
        if (p > maxPages || p < 1) {
        } else {
            try {
                pg = p;
                if (pg == 0) {
                    offset = (pg) * lim;
                } else {
                    offset = (pg - 1) * lim;
                }
                updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
                addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_page2ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        if (pg > 1) {
            pg--;
            page2.setText(Integer.toString(pg));
        }
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        pg = 1;
        page2.setText(Integer.toString(pg));
        if (pg == 0) {
            offset = (pg) * lim;
        } else {
            offset = (pg - 1) * lim;
        }
        try {
            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        Home.pro.setVisible(true);
        Home.process.setVisible(false);

    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        Home.process.setVisible(true);
        Home.pro.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void AQLTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLTypeActionPerformed
        AQLType.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        AQLError.setText(null);

        String type = String.valueOf(AQLType.getSelectedItem());

        if (type.equals("Garments")) {
            jLabel33.setText("Portion No :");
            AQLBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT BLP_No FROM listaqlandfb WHERE First_Bath =1 AND AQL = 0 AND Type = 'Garments'");
                while (rs.next()) {
                    AQLBLPno.addItem(rs.getString("BLP_No"));
                }
                AQLBLPno.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
            AQLStyleno.setText(null);
            AQLCus.setText(null);
            AQLCol.setText(null);
        } else if (type.equals("Gloves")) {
            jLabel33.setText("Lot No      :");
            AQLBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT BLP_No FROM listaqlandfb WHERE First_Bath =1 AND AQL = 0 AND Type = 'Gloves'");
                while (rs.next()) {
                    AQLBLPno.addItem(rs.getString("BLP_No"));
                }
                AQLBLPno.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
            AQLStyleno.setText(null);
            AQLCus.setText(null);
            AQLCol.setText(null);
        } else {
            jLabel33.setText("Batch No  :");
            AQLBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT BLP_No FROM listaqlandfb WHERE First_Bath =1 AND AQL = 0 AND Type = 'Fabrics'");
                while (rs.next()) {
                    AQLBLPno.addItem(rs.getString("BLP_No"));
                }
                AQLBLPno.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
            AQLStyleno.setText(null);
            AQLCus.setText(null);
            AQLCol.setText(null);
        }
    }//GEN-LAST:event_AQLTypeActionPerformed

    private void AQLBLPnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLBLPnoActionPerformed
        AQLBLPno.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        AQLError.setText(null);

        String blp = String.valueOf(AQLBLPno.getSelectedItem());

        try {
            ResultSet rs1 = con.createStatement().executeQuery("SELECT Style_No, Customer, Color FROM listaqlandfb WHERE BLP_No = '" + blp + "'");
            while (rs1.next()) {
                AQLStyleno.setText(rs1.getString("Style_No"));
                AQLCus.setText(rs1.getString("Customer"));
                AQLCol.setText(rs1.getString("Color"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AQLBLPnoActionPerformed

    private void FBStatusCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusCusActionPerformed
        FBStatusCus.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBStatusError.setText(null);

        String cus = String.valueOf(FBStatusCus.getSelectedItem());
        String type = String.valueOf(FBStatusType.getSelectedItem());

        try {
            FBStatusStyle.removeAllItems();
            ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Style_No FROM processplan WHERE Customer = '" + cus + "' AND Type = '" + type + "' AND BLP_No IS NULL");
            while (rs.next()) {
                FBStatusStyle.addItem(rs.getString("Style_No"));
            }
            FBStatusStyle.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_FBStatusCusActionPerformed

    private void FBStatusStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusStyleActionPerformed
        FBStatusStyle.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBStatusError.setText(null);

        String style = String.valueOf(FBStatusStyle.getSelectedItem());
        String cus = String.valueOf(FBStatusCus.getSelectedItem());
        String type = String.valueOf(FBStatusType.getSelectedItem());
        if (type.equals("Gloves")) {
            FBStatusCol.addItem("None");
            FBStatusCol.setSelectedItem("None");
            FBStatusCol.setEnabled(false);
        } else {
            try {
                FBStatusCol.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Color FROM processplan WHERE Customer = '" + cus + "' AND Type = '" + type + "' AND Style_No = '" + style + "' AND BLP_No IS NULL");
                while (rs.next()) {
                    FBStatusCol.addItem(rs.getString("Color"));
                }
                FBStatusCol.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBStatusStyleActionPerformed

    private void FBITypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBITypeActionPerformed
        FBIType.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);

        String type = String.valueOf(FBIType.getSelectedItem());
        if (type.equals("Gloves")) {
            FBICol.addItem("None");
            FBICol.setSelectedItem("None");
            FBICol.setEnabled(false);
            try {
                FBICus.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Customer FROM listaqlandfb WHERE Type = '" + type + "' AND First_Bath =0");
                while (rs.next()) {
                    FBICus.addItem(rs.getString("Customer"));
                }
                FBICus.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FBICol.setEnabled(true);
                FBICol.removeAllItems();
                FBICus.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Customer FROM listaqlandfb WHERE Type = '" + type + "' AND First_Bath =0");
                while (rs.next()) {
                    FBICus.addItem(rs.getString("Customer"));
                }
                FBICus.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBITypeActionPerformed

    private void FBICusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBICusActionPerformed
        FBICus.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);

        String type = String.valueOf(FBIType.getSelectedItem());
        String cus = String.valueOf(FBICus.getSelectedItem());
        if (FBIType.getSelectedIndex() != -1) {
            try {
                FBIStyle.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT Style_No FROM listaqlandfb WHERE Type = '" + type + "' AND Customer = '" + cus + "' AND First_Bath =0");
                while (rs.next()) {
                    FBIStyle.addItem(rs.getString("Style_No"));
                }
                FBIStyle.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FBICusActionPerformed

    private void FBIStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIStyleActionPerformed
        FBIStyle.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);

        String type = String.valueOf(FBIType.getSelectedItem());
        String cus = String.valueOf(FBICus.getSelectedItem());
        String style = String.valueOf(FBIStyle.getSelectedItem());

        if (FBIStyle.getSelectedIndex() != -1) {
            if (type.equals("Gloves")) {
                FBICol.addItem("None");
                FBICol.setSelectedItem("None");
                FBICol.setEnabled(false);
            } else {
                FBICol.setEnabled(true);
                try {
                    FBICol.removeAllItems();
                    ResultSet rs = con.createStatement().executeQuery("SELECT Color FROM listaqlandfb WHERE Type = '" + type + "' AND Customer = '" + cus + "' AND Style_No = '" + style + "' AND First_Bath = 0");
                    while (rs.next()) {
                        FBICol.addItem(rs.getString("Color"));
                    }
                    FBICol.setSelectedIndex(-1);
                } catch (SQLException ex) {
                    Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_FBIStyleActionPerformed

    private void FBIColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIColActionPerformed
        FBICol.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIColActionPerformed

    private void FBStatusColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusColActionPerformed
        FBStatusCol.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBStatusError.setText(null);
    }//GEN-LAST:event_FBStatusColActionPerformed

    private void FBStatusTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusTypeActionPerformed
        FBStatusType.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBStatusError.setText(null);

        String type = String.valueOf(FBStatusType.getSelectedItem());

        if (type.equals("Gloves")) {
            FBStatusCol.addItem("None");
            FBStatusCol.setSelectedItem("None");
            FBStatusCol.setEnabled(false);
            try {
                FBStatusCus.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Customer FROM processplan WHERE Type = '" + type + "' AND BLP_No IS NULL");
                while (rs.next()) {
                    FBStatusCus.addItem(rs.getString("Customer"));
                }
                FBStatusCus.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FBStatusCol.setEnabled(true);
            try {
                FBStatusCus.removeAllItems();
                ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Customer FROM processplan WHERE Type = '" + type + "' AND BLP_No IS NULL");
                while (rs.next()) {
                    FBStatusCus.addItem(rs.getString("Customer"));
                }
                FBStatusCus.setSelectedIndex(-1);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_FBStatusTypeActionPerformed

    private void PITypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PITypeActionPerformed
        PIType.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);

        String type = String.valueOf(PIType.getSelectedItem());
        int pno;

        ResultSet rs1;
        try {
            rs1 = con.createStatement().executeQuery("SELECT MAX(Process_No) as 'Process_No' FROM processplan");
            rs1.next();
            pno = rs1.getInt("Process_No");
            pno++;
            PIProno.setText(Integer.toString(pno));
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (type.equals("Garments")) {
            jLabel148.setText("Portion No ");
            PIBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT l.BLP_No FROM listaqlandfb l LEFT JOIN processplan p ON l.BLP_No = p.BLP_No WHERE l.Type = '" + type + "' AND l.First_Bath =1 AND l.AQL = 1 AND p.BLP_No IS NULL");
                while (rs.next()) {
                    PIBLPno.addItem(rs.getString("BLP_No"));
                }

                PIBLPno.setSelectedIndex(-1);
                PIWSu.setSelectedIndex(-1);
                PIBC.setSelectedIndex(-1);
                PIBWSt.setSelectedIndex(-1);
                PIStyleno.setText(null);
                PICus.setText(null);
                PICol.setText(null);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("Gloves")) {
            jLabel148.setText("Lot No   ");
            PIBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT l.BLP_No FROM listaqlandfb l LEFT JOIN processplan p ON l.BLP_No = p.BLP_No WHERE l.Type = '" + type + "' AND l.First_Bath =1 AND l.AQL = 1 AND p.BLP_No IS NULL");
                while (rs.next()) {
                    PIBLPno.addItem(rs.getString("BLP_No"));
                }

                PIBLPno.setSelectedIndex(-1);
                PIWSu.setSelectedIndex(-1);
                PIBC.setSelectedIndex(-1);
                PIBWSt.setSelectedIndex(-1);
                PIStyleno.setText(null);
                PICus.setText(null);
                PICol.setText(null);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jLabel148.setText("Batch No ");
            PIBLPno.removeAllItems();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT l.BLP_No FROM listaqlandfb l LEFT JOIN processplan p ON l.BLP_No = p.BLP_No WHERE l.Type = '" + type + "' AND l.First_Bath =1 AND l.AQL = 1 AND p.BLP_No IS NULL");
                while (rs.next()) {
                    PIBLPno.addItem(rs.getString("BLP_No"));
                }

                PIBLPno.setSelectedIndex(-1);
                PIWSu.setSelectedIndex(-1);
                PIBC.setSelectedIndex(-1);
                PIBWSt.setSelectedIndex(-1);
                PIStyleno.setText(null);
                PICus.setText(null);
                PICol.setText(null);
            } catch (SQLException ex) {
                Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PITypeActionPerformed

    private void PIBLPnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIBLPnoActionPerformed
        PIBLPno.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);

        String blp = String.valueOf(PIBLPno.getSelectedItem());

        try {
            ResultSet rs1 = con.createStatement().executeQuery("SELECT Style_No, Customer, Color FROM listaqlandfb WHERE BLP_No = '" + blp + "'");
            while (rs1.next()) {
                PIStyleno.setText(rs1.getString("Style_No"));
                PICus.setText(rs1.getString("Customer"));
                PICol.setText(rs1.getString("Color"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PIBLPnoActionPerformed

    private void IssuePPSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IssuePPSaveActionPerformed

        try {
            if (PIssueValidate()) {
                String type = String.valueOf(PIType.getSelectedItem());
                String blp = String.valueOf(PIBLPno.getSelectedItem());
                String cus = PICus.getText();
                String style = PIStyleno.getText();
                String col = PICol.getText();
                String wsu = String.valueOf(PIWSu.getSelectedItem());
                String bcqc = String.valueOf(PIBC.getSelectedItem());
                String bwst = String.valueOf(PIBWSt.getSelectedItem());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String Date = df.format(PIDate.getDate());

                try {

                    if (type.equals("Garments")) {
                        ResultSet rs = con.createStatement().executeQuery("SELECT PO_Status FROM SPLGarment WHERE portionNo = '" + blp + "' ");
                        rs.next();
                        int po = rs.getInt("PO_Status");
                        if (po == 0) {
                            JOptionPane.showMessageDialog(null, "Purchase Order NOT AVAILABLE for Garment with portionNo : " + blp + ", Couldn't Create Process Plan ", "Error", JOptionPane.INFORMATION_MESSAGE);
                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);
                        } else {

                            if (type.equals("Gloves")) { //If type is set to Gloves the below line updates the SQLGlove table's aql value
                                con.prepareStatement("UPDATE SPLGlove set PP_Status = 1 WHERE lotNo = '" + blp + "'").execute();
                            } else if (type.equals("Garment")) { //If type is set to Garments the below line updates the SQLGarment table's aql value
                                con.prepareStatement("UPDATE SPLGarment set PP_Status = 1 WHERE portionNo = '" + blp + "'").execute();
                            } else { //If type is set to Fabrics the below line updates the SQLFabric table's aql value
                                con.prepareStatement("UPDATE SPLFabric set PP_Status = 1 WHERE batchNo = '" + blp + "'").execute();
                            }

                            con.prepareStatement("INSERT INTO processplan (BLP_No,Style_No,Customer,Color,Date,Type,W_Supervisor,Employee,BC_QC,BW_Store) VALUES('" + blp + "','" + style + "','" + cus + "','" + col + "','" + Date + "','" + type + "','" + wsu + "',NULL,'" + bcqc + "','" + bwst + "')").execute();
                            JOptionPane.showMessageDialog(null, "Process Plan Crested", "Done", JOptionPane.INFORMATION_MESSAGE);

                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                        }
                    } else if (type.equals("Gloves")) {
                        ResultSet rs = con.createStatement().executeQuery("SELECT PO_Status FROM SPLGlove WHERE lotNo = '" + blp + "' ");
                        rs.next();
                        int po = rs.getInt("PO_Status");
                        if (po == 0) {
                            JOptionPane.showMessageDialog(null, "Purchase Order NOT AVAILABLE for Glove with lotNo : " + blp + ", Couldn't Create Process Plan ", "Error", JOptionPane.INFORMATION_MESSAGE);
                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);
                        } else {
                            con.prepareStatement("INSERT INTO processplan (BLP_No,Style_No,Customer,Color,Date,Type,W_Supervisor,Employee,BC_QC,BW_Store) VALUES('" + blp + "','" + style + "','" + cus + "','None','" + Date + "','" + type + "','" + wsu + "',NULL,'" + bcqc + "','" + bwst + "')").execute();
                            JOptionPane.showMessageDialog(null, "Process Plan Crested", "Done", JOptionPane.INFORMATION_MESSAGE);

                            if (type.equals("Gloves")) { //If type is set to Gloves the below line updates the SQLGlove table's aql value
                                con.prepareStatement("UPDATE SPLGlove set PP_Status = 1 WHERE lotNo = '" + blp + "'").execute();
                            } else if (type.equals("Garment")) { //If type is set to Garments the below line updates the SQLGarment table's aql value
                                con.prepareStatement("UPDATE SPLGarment set PP_Status = 1 WHERE portionNo = '" + blp + "'").execute();
                            } else { //If type is set to Fabrics the below line updates the SQLFabric table's aql value
                                con.prepareStatement("UPDATE SPLFabric set PP_Status = 1 WHERE batchNo = '" + blp + "'").execute();
                            }

                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                        }
                    } else if (type.equals("Fabrics")) {
                        ResultSet rs = con.createStatement().executeQuery("SELECT PO_Status FROM SPLFabric WHERE batchNo = '" + blp + "' ");
                        rs.next();
                        int po = rs.getInt("PO_Status");
                        if (po == 0) {
                            JOptionPane.showMessageDialog(null, "Purchase Order NOT AVAILABLE for Fabric with batchNo : " + blp + ", Couldn't Create Process Plan ", "Error", JOptionPane.INFORMATION_MESSAGE);
                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);
                        } else {
                            con.prepareStatement("INSERT INTO processplan (BLP_No,Style_No,Customer,Color,Date,Type,W_Supervisor,Employee,BC_QC,BW_Store) VALUES('" + blp + "','" + style + "','" + cus + "','" + col + "','" + Date + "','" + type + "','" + wsu + "',NULL,'" + bcqc + "','" + bwst + "')").execute();
                            JOptionPane.showMessageDialog(null, "Process Plan Crested", "Done", JOptionPane.INFORMATION_MESSAGE);

                            if (type.equals("Gloves")) { //If type is set to Gloves the below line updates the SQLGlove table's aql value
                                con.prepareStatement("UPDATE SPLGlove set PP_Status = 1 WHERE lotNo = '" + blp + "'").execute();
                            } else if (type.equals("Garment")) { //If type is set to Garments the below line updates the SQLGarment table's aql value
                                con.prepareStatement("UPDATE SPLGarment set PP_Status = 1 WHERE portionNo = '" + blp + "'").execute();
                            } else { //If type is set to Fabrics the below line updates the SQLFabric table's aql value
                                con.prepareStatement("UPDATE SPLFabric set PP_Status = 1 WHERE batchNo = '" + blp + "'").execute();
                            }

                            PIType.setSelectedIndex(-1);
                            PIBLPno.setSelectedIndex(-1);
                            PIWSu.setSelectedIndex(-1);
                            PIBC.setSelectedIndex(-1);
                            PIBWSt.setSelectedIndex(-1);
                            PICus.setText(null);
                            PIStyleno.setText(null);
                            PICol.setText(null);
                            PIProno.setText(null);

                            updateMax("SELECT COUNT(*) AS count FROM processplan", total1, page1);
                            addPProTable("SELECT * FROM processplan LIMIT " + lim + " OFFSET " + offset + "");

                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_IssuePPSaveActionPerformed

    private void tagPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagPrintActionPerformed
        int r = jTable4.getSelectedRow();
        String blp = (String) jTable4.getValueAt(r, 1);// get selected row primary key
        int tag;
        String style;
        String color;
        String cus;
        
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT Customer, Style_No, Color, Tag_No FROM tag WHERE BLP_No ='" + blp + "'");//get selected tag number details and asign then to the variable
            rs.next();
            style = rs.getString("Style_No");
            cus = rs.getString("Customer");
            color = rs.getString("Color");
            tag = rs.getInt("Tag_No");

            con.prepareStatement("UPDATE tag set Tag_Status = 'Printed' WHERE BLP_No = '" + blp + "'").execute();//updated to state of tag is printed

            updateMax("SELECT COUNT(*) AS count FROM tag", total2, page2);
            addTagTable("SELECT * FROM tag LIMIT " + lim + " OFFSET " + offset + "");//refash the tag table

            GenTagNo.setText(String.valueOf(tag));
            GenTCus.setText(cus);
            GenTColor.setText(color);
            GenTStyle.setText(style);//send asign value for the gen tag panal
        } catch (SQLException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }
        Code128Auto code128 = new Code128Auto();
        String barcode = code128.encode(blp);
        GenTBarcode.setText(barcode);
        GenTBarcode.setFont(new java.awt.Font("CCode128_S3_Trial", java.awt.Font.PLAIN, 24));//barcode genaration pk 

        Dimension size = GenTag.getSize();
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        GenTag.paint(g2);

        try {
            ImageIO.write(image, "png", new File("E:\\Test.png"));//convert to gen tag panal to image 
        } catch (IOException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);
        }

        File f = new File("E:\\Test.png");
        try {
            Desktop.getDesktop().open(f);
        } catch (IOException ex) {
            Logger.getLogger(production.class.getName()).log(Level.SEVERE, null, ex);//open and show created tag
        }


    }//GEN-LAST:event_tagPrintActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        FBdelete.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        ListFBandAQLedit.setVisible(true);
        ListFBandAQLdelete.setVisible(true);
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        PProedit.setVisible(true);
        PProdelete.setVisible(true);
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        tagPrint.setVisible(true);
    }//GEN-LAST:event_jTable4MouseClicked

    private void FBIEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIEmpActionPerformed
        FBIEmp.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIEmpActionPerformed

    private void FBIDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIDateActionPerformed
        FBIDate.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIDateActionPerformed

    private void FBIBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIBCActionPerformed
        FBIBC.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIBCActionPerformed

    private void FBIWsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIWsuActionPerformed
        FBIWsu.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIWsuActionPerformed

    private void FBIBWstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBIBWstActionPerformed
        FBIBWst.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBIError.setText(null);
    }//GEN-LAST:event_FBIBWstActionPerformed

    private void PIBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIBCActionPerformed
        PIBC.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);
    }//GEN-LAST:event_PIBCActionPerformed

    private void PIWSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIWSuActionPerformed
        PIWSu.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);
    }//GEN-LAST:event_PIWSuActionPerformed

    private void PIBWStActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIBWStActionPerformed
        PIBWSt.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);
    }//GEN-LAST:event_PIBWStActionPerformed

    private void PIDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIDateActionPerformed
        PIDate.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        PIError.setText(null);
    }//GEN-LAST:event_PIDateActionPerformed

    private void AQLStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AQLStatusActionPerformed
        AQLStatus.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        AQLError.setText(null);
    }//GEN-LAST:event_AQLStatusActionPerformed

    private void FBStatusStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBStatusStatusActionPerformed
        FBStatusStatus.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        FBStatusError.setText(null);
    }//GEN-LAST:event_FBStatusStatusActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       Home.Report(jTable3, "AQL and Firstbath Status of Packing Lists","E:\\Reports\\listAqlFb.pdf");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Home.Report(jTable1, "Firstbath Details","E:\\Reports\\firstbath.pdf");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Home.Report(jTable2, "Process Plan Details","E:\\Reports\\processplan.pdf");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Home.Report(jTable4, "Tag Details","E:\\Reports\\tag.pdf");
    }//GEN-LAST:event_jButton17ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AQL;
    private javax.swing.JComboBox<String> AQLBLPno;
    private javax.swing.JButton AQLBack;
    private javax.swing.JLabel AQLCol;
    private javax.swing.JLabel AQLCus;
    private javax.swing.JLabel AQLError;
    private javax.swing.JButton AQLSave;
    private javax.swing.JComboBox<String> AQLStatus;
    private javax.swing.JLabel AQLStyleno;
    private javax.swing.JComboBox<String> AQLType;
    private javax.swing.JButton Add;
    private javax.swing.JLabel Background;
    private javax.swing.JButton FBBack;
    private javax.swing.JButton FBButton;
    private javax.swing.JComboBox<String> FBIBC;
    private javax.swing.JComboBox<String> FBIBWst;
    private javax.swing.JComboBox<String> FBICol;
    private javax.swing.JComboBox<String> FBICus;
    private org.jdesktop.swingx.JXDatePicker FBIDate;
    private javax.swing.JComboBox<String> FBIEmp;
    private javax.swing.JLabel FBIError;
    private javax.swing.JComboBox<String> FBIStyle;
    private javax.swing.JComboBox<String> FBIType;
    private javax.swing.JComboBox<String> FBIWsu;
    private javax.swing.JComboBox<String> FBItem;
    private javax.swing.JButton FBRefresh;
    private javax.swing.JButton FBStatus;
    private javax.swing.JButton FBStatusBack;
    private javax.swing.JComboBox<String> FBStatusCol;
    private javax.swing.JComboBox<String> FBStatusCus;
    private javax.swing.JLabel FBStatusError;
    private javax.swing.JComboBox<String> FBStatusStatus;
    private javax.swing.JComboBox<String> FBStatusStyle;
    private javax.swing.JComboBox<String> FBStatusType;
    private javax.swing.JButton FBdelete;
    private javax.swing.JTextField FBsearch;
    private javax.swing.JPanel F_AQLPanel;
    private javax.swing.JPanel F_FirstBathStatus;
    private javax.swing.JPanel F_IssueFB;
    private javax.swing.JPanel F_ListFBandAQL;
    private javax.swing.JPanel FirstBath;
    private javax.swing.JLabel GenT1;
    private javax.swing.JLabel GenT3;
    private javax.swing.JLabel GenT5;
    private javax.swing.JLabel GenT7;
    private javax.swing.JLabel GenTBarcode;
    private javax.swing.JLabel GenTColor;
    private javax.swing.JLabel GenTCus;
    private javax.swing.JLabel GenTStyle;
    private javax.swing.JPanel GenTag;
    private javax.swing.JLabel GenTagNo;
    private javax.swing.JButton IssNFBBack;
    private javax.swing.JButton IssNFBSave;
    private javax.swing.JButton IssueNFB;
    private javax.swing.JButton IssuePPBack;
    private javax.swing.JButton IssuePPSave;
    private javax.swing.JTextField ListAQLandFBsearch;
    private javax.swing.JButton ListFBandAQL;
    private javax.swing.JButton ListFBandAQLBack1;
    private javax.swing.JComboBox<String> ListFBandAQLItem;
    private javax.swing.JButton ListFBandAQLRefresh;
    private javax.swing.JButton ListFBandAQLdelete;
    private javax.swing.JButton ListFBandAQLedit;
    private javax.swing.JComboBox<String> PIBC;
    private javax.swing.JComboBox<String> PIBLPno;
    private javax.swing.JComboBox<String> PIBWSt;
    private javax.swing.JLabel PICol;
    private javax.swing.JLabel PICus;
    private org.jdesktop.swingx.JXDatePicker PIDate;
    private javax.swing.JLabel PIError;
    private javax.swing.JLabel PIProno;
    private javax.swing.JLabel PIStyleno;
    private javax.swing.JComboBox<String> PIType;
    private javax.swing.JComboBox<String> PIWSu;
    private javax.swing.JPanel PPro;
    private javax.swing.JButton PProBack;
    private javax.swing.JButton PProIssueNPP;
    private javax.swing.JComboBox<String> PProItem;
    private javax.swing.JButton PProRefresh;
    private javax.swing.JButton PProdelete;
    private javax.swing.JButton PProedit;
    private javax.swing.JTextField PProsearch;
    private javax.swing.JPanel P_IssuePP;
    protected javax.swing.JPanel Pre_Pro;
    private javax.swing.JButton Pro_PlanButton;
    private javax.swing.JPanel Tag;
    private javax.swing.JComboBox<String> TagItem;
    private javax.swing.JButton TagsButton;
    private javax.swing.JTextField Tagsearch;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField page;
    protected javax.swing.JTextField page1;
    private javax.swing.JTextField page2;
    private javax.swing.JTextField page3;
    private javax.swing.JButton tagBack;
    private javax.swing.JButton tagPrint;
    private javax.swing.JButton tagRefresh;
    private javax.swing.JLabel total;
    protected javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel total3;
    // End of variables declaration//GEN-END:variables
}
