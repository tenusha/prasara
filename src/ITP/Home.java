/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ITP;

import Mainternance.Maintenance;
import Mainternance.ViewMachine;
import Mainternance.ViewRepair;
import Mainternance.ViewService;
import Mainternance.ViewTools;
import Mainternance.ViewVehicle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import static inventory.dbCon.con;
import inventory.inventory;
import inventory.viewColor;
import inventory.viewGarments;
import inventory.viewRecipe;
import inventory.viewSize;
import inventory.viewStyle;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import loadinglist.Loading;
import login.login;
import production.production;
import production.process;
import shanuka.viewChemicals;
import shanuka.viewMaterials;

/**
 *
 * @author Ranmal
 */
public class Home extends javax.swing.JFrame {

    public static String user = "";
    public static String eid = "";
    public static int user_level = 0;

    GridBagLayout layout = new GridBagLayout();
    //tmg
    public static inventory i;
    public static viewStyle viewStyle;
    public static viewRecipe viewRecipe;
    public static viewColor viewColor;
    public static viewSize vSize;
    public static viewGarments vGarments;
    static EditPwd ep;

    //ran
    static StoresPackingList spl;
    static GloveView gv;
    static Date fillDate;
    public static Dashboard dsb;
    static FabricView fv;
    static GarmentView gav;
    static PO po;
    static POGloveView gpo;
    static POFabricView fpo;
    static POGarmentView gapo;

    //vgr
    static listCustomers lc;
    static people peo;
    static listEmployees le;
    static listSuppliers ls;
    static bestEmployee be;

    //ara
    public static production pro;
    public static process process;

    //udata
    static attendance att;

    //dumpa
    public static ViewMachine ViewMachine;
    public static ViewRepair ViewRepair;
    public static ViewTools ViewTools;
    public static ViewService ViewService;
    public static ViewVehicle ViewVehicle;
    public static Maintenance Maintenance;
    public static Loading Loading;

    //shanuka
    public static viewChemicals chemical;
    public static viewMaterials material;

    methods m = new methods();

    public Home() throws SQLException {
        initComponents();

    }

    public Home(String user) throws SQLException {
        initComponents();
        //tenusha fuck you m.getImage(jLabel2, user);

        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee WHERE username = '" + user + "'");
        rs.next();
        un.setText(rs.getString("fname"));
        eid = rs.getString("empNo");
        user_level = rs.getInt("user_level");

        //set image
        String sql = "select image from employee where empNo='" + eid + "'";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        if (rs1.next()) {
            byte[] img = rs1.getBytes(1);
            ImageIcon image = new ImageIcon(img);
            Image im = image.getImage();
            Image s = im.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon newImage = new ImageIcon(s);
            jLabel2.setIcon(newImage);
        }
        //temp
        jLabel2.setBorder(BorderFactory.createLineBorder(Color.decode("#a0a0a0")));

        this.user = user;

        fillDate = showDate();
        showTime();

        //vgr
        lc = new listCustomers();
        peo = new people();
        le = new listEmployees();
        ls = new listSuppliers();
        be = new bestEmployee();

        //ran
        spl = new StoresPackingList();
        gv = new GloveView();
        dsb = new Dashboard();
        fv = new FabricView();
        gav = new GarmentView();
        po = new PO();
        gpo = new POGloveView();
        fpo = new POFabricView();
        gapo = new POGarmentView();

        //ara
        pro = new production();
        process = new process();

        //udara
        att = new attendance();

        //tmg
        i = new inventory();
        viewStyle = new viewStyle();
        viewRecipe = new viewRecipe();
        viewColor = new viewColor();
        vSize = new viewSize();
        vGarments = new viewGarments();
        ep = new EditPwd();

        //dumpa
        ViewMachine = new ViewMachine();
        ViewRepair = new ViewRepair();
        ViewTools = new ViewTools();
        ViewService = new ViewService();
        ViewVehicle = new ViewVehicle();
        Maintenance = new Maintenance();
        Loading = new Loading();

        //shanuka
        chemical = new viewChemicals();
        material = new viewMaterials();

        //dumpa
        dpMethod(ViewMachine, layout);
        dpMethod(ViewRepair, layout);
        dpMethod(ViewTools, layout);
        dpMethod(ViewService, layout);
        dpMethod(ViewVehicle, layout);
        dpMethod(Maintenance, layout);
        dpMethod(Loading, layout);

        //vgr
        dpMethod(lc, layout);
        dpMethod(peo, layout);
        dpMethod(le, layout);
        dpMethod(ls, layout);
        dpMethod(be, layout);

        //tmg
        dpMethod(i, layout);
        dpMethod(viewStyle, layout);
        dpMethod(viewRecipe, layout);
        dpMethod(viewColor, layout);
        dpMethod(vSize, layout);
        dpMethod(vGarments, layout);
        dpMethod(ep, layout);

        //ran
        dpMethod(spl, layout);
        dpMethod(gv, layout);
        dpMethod(dsb, layout);
        dpMethod(fv, layout);
        dpMethod(gav, layout);
        dpMethod(po, layout);
        dpMethod(gpo, layout);
        dpMethod(fpo, layout);
        dpMethod(gapo, layout);

        //shanuka
        dpMethod(chemical, layout);
        dpMethod(material, layout);

        //ara
        dpMethod(pro, layout);
        dpMethod(process, layout);

        //udara
        dpMethod(att, layout);

        if (rs.getString("nic").hashCode() == rs.getInt("password")) {
            dynaDisplay(ep);
        } else {
            dynaDisplay(dsb);
        }
    }

    public void dpMethod(JPanel panel, GridBagLayout layout) {
        DynamicPanel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        DynamicPanel.add(panel, c);
        panel.setVisible(false);

    }

    Date showDate() {
        Date d = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy  MMM  dd");
        CurrDate.setText(dt.format(d));
        return d;

    }

    void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh : mm : ss a");
                CurrTime.setText(s.format(d));
            }
        }).start();

    }

    public static void Report(JTable table, String Title, String path) {

        Document d = new Document(PageSize.A3.rotate());
        try {

            PdfWriter wr = PdfWriter.getInstance(d, new FileOutputStream(path));
            d.open();
            Paragraph p = new Paragraph("Prasara Washing Plant", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.DARK_GRAY));
            p.setAlignment(Element.ALIGN_LEFT);
            d.add(p);

            Paragraph p1 = new Paragraph("Dankotuwa (Pvt)Ltd", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.DARK_GRAY));
            p1.setAlignment(Element.ALIGN_LEFT);
            d.add(p1);

            Paragraph p2 = new Paragraph(Title, FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.UNDERLINE, BaseColor.DARK_GRAY));
            p2.setAlignment(Element.ALIGN_LEFT);
            d.add(p2);

            Paragraph p3 = new Paragraph(Home.fillDate.toString(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.NORMAL, BaseColor.DARK_GRAY));
            p3.setAlignment(Element.ALIGN_LEFT);
            d.add(p3);

            Paragraph p4 = new Paragraph("        ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.NORMAL, BaseColor.DARK_GRAY));
            p4.setAlignment(Element.ALIGN_LEFT);
            d.add(p4);

            Paragraph p5 = new Paragraph("        ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.NORMAL, BaseColor.DARK_GRAY));
            p5.setAlignment(Element.ALIGN_LEFT);
            d.add(p5);

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            //adding table headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Paragraph(table.getColumnName(i)));
                cell.setBackgroundColor(BaseColor.GRAY);
                pdfTable.addCell(cell);
            }

            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    System.out.println(table.getModel().getValueAt(rows, cols));
                    String vg123=table.getModel().getValueAt(rows, 0).toString();
                    System.out.println(vg123);
                    if (vg123!=null && !vg123.trim().equals("")) {
                        pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());
                    }
                }
            }

            d.add(pdfTable);

            PdfContentByte cb = wr.getDirectContent();
            PdfTemplate tmp = cb.createTemplate(500, 500);
            Graphics2D g = tmp.createGraphics(500, 500);
            jLabel4.paint(g);
            g.dispose();
            cb.addTemplate(tmp, 700, 300);
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (d.isOpen()) {
                d.close();
            }
        }

        File f = new File(path);
        try {
            Desktop.getDesktop().open(f);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void dynaDisplay(JPanel panel) {
        //tmg
        i.setVisible(false);
        viewStyle.setVisible(false);
        viewRecipe.setVisible(false);
        viewColor.setVisible(false);
        vSize.setVisible(false);
        vGarments.setVisible(false);
        ep.setVisible(false);

        //ran
        dsb.setVisible(false);
        gv.setVisible(false);
        fv.setVisible(false);
        gav.setVisible(false);
        po.setVisible(false);
        gpo.setVisible(false);
        fpo.setVisible(false);
        gapo.setVisible(false);
        spl.setVisible(false);

        //vgr
        peo.setVisible(false);
        lc.setVisible(false);
        le.setVisible(false);
        ls.setVisible(false);
        be.setVisible(false);

        //ara
        pro.setVisible(false);
        process.setVisible(false);

        //udara
        att.setVisible(false);

        //dumpa
        ViewMachine.setVisible(false);
        ViewRepair.setVisible(false);
        ViewTools.setVisible(false);
        ViewService.setVisible(false);
        ViewVehicle.setVisible(false);
        Maintenance.setVisible(false);
        Loading.setVisible(false);

        //shanuka
        chemical.setVisible(false);
        material.setVisible(false);

        panel.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        SidePanel = new javax.swing.JPanel();
        Production = new javax.swing.JPanel();
        DashboardImg1 = new javax.swing.JLabel();
        Dashboard1 = new javax.swing.JLabel();
        PO = new javax.swing.JPanel();
        DashboardImg2 = new javax.swing.JLabel();
        Dashboard2 = new javax.swing.JLabel();
        LL = new javax.swing.JPanel();
        DashboardImg4 = new javax.swing.JLabel();
        Dashboard4 = new javax.swing.JLabel();
        Inventory = new javax.swing.JPanel();
        DashboardImg5 = new javax.swing.JLabel();
        Dashboard5 = new javax.swing.JLabel();
        People = new javax.swing.JPanel();
        DashboardImg7 = new javax.swing.JLabel();
        Dashboard7 = new javax.swing.JLabel();
        DB = new javax.swing.JPanel();
        DashboardImg8 = new javax.swing.JLabel();
        Dashboard8 = new javax.swing.JLabel();
        SPL = new javax.swing.JPanel();
        DashboardImg9 = new javax.swing.JLabel();
        Dashboard9 = new javax.swing.JLabel();
        Dash = new javax.swing.JPanel();
        DashboardImg10 = new javax.swing.JLabel();
        Dashboard10 = new javax.swing.JLabel();
        TopPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Minimize = new javax.swing.JLabel();
        Close = new javax.swing.JLabel();
        CurrDate = new javax.swing.JLabel();
        CurrTime = new javax.swing.JLabel();
        Calender = new javax.swing.JLabel();
        Clock = new javax.swing.JLabel();
        un = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        DynamicPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(100, 30));
        setMinimumSize(new java.awt.Dimension(1200, 610));
        setUndecorated(true);

        MainPanel.setMaximumSize(new java.awt.Dimension(1200, 610));
        MainPanel.setMinimumSize(new java.awt.Dimension(1200, 610));
        MainPanel.setPreferredSize(new java.awt.Dimension(1200, 610));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePanel.setBackground(new java.awt.Color(34, 38, 53));
        SidePanel.setMaximumSize(new java.awt.Dimension(260, 470));
        SidePanel.setMinimumSize(new java.awt.Dimension(260, 470));
        SidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Production.setBackground(new java.awt.Color(50, 61, 83));
        Production.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProductionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProductionMouseExited(evt);
            }
        });

        DashboardImg1.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Production.png"))); // NOI18N
        DashboardImg1.setText("jLabel1");
        DashboardImg1.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg1.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg1.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard1.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard1.setText("Process");

        javax.swing.GroupLayout ProductionLayout = new javax.swing.GroupLayout(Production);
        Production.setLayout(ProductionLayout);
        ProductionLayout.setHorizontalGroup(
            ProductionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        ProductionLayout.setVerticalGroup(
            ProductionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(Production, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, -1));

        PO.setBackground(new java.awt.Color(50, 61, 83));
        PO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                POMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                POMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                POMouseExited(evt);
            }
        });

        DashboardImg2.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/PO.png"))); // NOI18N
        DashboardImg2.setText("jLabel1");
        DashboardImg2.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg2.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg2.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard2.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard2.setText("Purchase Order");

        javax.swing.GroupLayout POLayout = new javax.swing.GroupLayout(PO);
        PO.setLayout(POLayout);
        POLayout.setHorizontalGroup(
            POLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(POLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        POLayout.setVerticalGroup(
            POLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(POLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(PO, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        LL.setBackground(new java.awt.Color(50, 61, 83));
        LL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LLMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LLMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LLMouseExited(evt);
            }
        });

        DashboardImg4.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/LL.png"))); // NOI18N
        DashboardImg4.setText("jLabel1");
        DashboardImg4.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg4.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg4.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard4.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard4.setText("Loading Lists");

        javax.swing.GroupLayout LLLayout = new javax.swing.GroupLayout(LL);
        LL.setLayout(LLLayout);
        LLLayout.setHorizontalGroup(
            LLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        LLLayout.setVerticalGroup(
            LLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(LL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, -1));

        Inventory.setBackground(new java.awt.Color(50, 61, 83));
        Inventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InventoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InventoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                InventoryMouseExited(evt);
            }
        });

        DashboardImg5.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Inventory.png"))); // NOI18N
        DashboardImg5.setText("jLabel1");
        DashboardImg5.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg5.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg5.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard5.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard5.setText("Inventory");

        javax.swing.GroupLayout InventoryLayout = new javax.swing.GroupLayout(Inventory);
        Inventory.setLayout(InventoryLayout);
        InventoryLayout.setHorizontalGroup(
            InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        InventoryLayout.setVerticalGroup(
            InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(Inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, -1, -1));

        People.setBackground(new java.awt.Color(50, 61, 83));
        People.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PeopleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PeopleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PeopleMouseExited(evt);
            }
        });

        DashboardImg7.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/People.png"))); // NOI18N
        DashboardImg7.setText("jLabel1");
        DashboardImg7.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg7.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg7.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard7.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard7.setText("People");

        javax.swing.GroupLayout PeopleLayout = new javax.swing.GroupLayout(People);
        People.setLayout(PeopleLayout);
        PeopleLayout.setHorizontalGroup(
            PeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PeopleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        PeopleLayout.setVerticalGroup(
            PeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(People, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, -1, -1));

        DB.setBackground(new java.awt.Color(50, 61, 83));
        DB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DBMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DBMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DBMouseExited(evt);
            }
        });

        DashboardImg8.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/DB.png"))); // NOI18N
        DashboardImg8.setText("jLabel1");
        DashboardImg8.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg8.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg8.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard8.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard8.setText("Maintanance");

        javax.swing.GroupLayout DBLayout = new javax.swing.GroupLayout(DB);
        DB.setLayout(DBLayout);
        DBLayout.setHorizontalGroup(
            DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        DBLayout.setVerticalGroup(
            DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(DB, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, -1, -1));

        SPL.setBackground(new java.awt.Color(50, 61, 83));
        SPL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SPLMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SPLMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SPLMouseExited(evt);
            }
        });

        DashboardImg9.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Stores.png"))); // NOI18N
        DashboardImg9.setText("jLabel1");
        DashboardImg9.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg9.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg9.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard9.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard9.setText("Stores & Packing Lists");

        javax.swing.GroupLayout SPLLayout = new javax.swing.GroupLayout(SPL);
        SPL.setLayout(SPLLayout);
        SPLLayout.setHorizontalGroup(
            SPLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SPLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard9)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        SPLLayout.setVerticalGroup(
            SPLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SPLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(SPL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        Dash.setBackground(new java.awt.Color(50, 61, 83));
        Dash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DashMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DashMouseExited(evt);
            }
        });

        DashboardImg10.setForeground(new java.awt.Color(255, 255, 255));
        DashboardImg10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Dashboard.png"))); // NOI18N
        DashboardImg10.setText("jLabel1");
        DashboardImg10.setMaximumSize(new java.awt.Dimension(50, 40));
        DashboardImg10.setMinimumSize(new java.awt.Dimension(50, 40));
        DashboardImg10.setPreferredSize(new java.awt.Dimension(50, 40));

        Dashboard10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard10.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard10.setText("Dashboard");

        javax.swing.GroupLayout DashLayout = new javax.swing.GroupLayout(Dash);
        Dash.setLayout(DashLayout);
        DashLayout.setHorizontalGroup(
            DashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardImg10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Dashboard10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        DashLayout.setVerticalGroup(
            DashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DashboardImg10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Dashboard10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePanel.add(Dash, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        MainPanel.add(SidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 260, 480));

        TopPanel.setBackground(new java.awt.Color(41, 47, 63));
        TopPanel.setMaximumSize(new java.awt.Dimension(1200, 130));
        TopPanel.setMinimumSize(new java.awt.Dimension(1200, 130));
        TopPanel.setPreferredSize(new java.awt.Dimension(1200, 130));
        TopPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/profile_default.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(103, 132));
        TopPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 40, 63, 80));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Welcome !!!!!!");
        TopPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 80, 130, -1));

        jButton1.setBackground(new java.awt.Color(15, 195, 15));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Pwd.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TopPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 80, 40, 40));

        Minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Minimize.png"))); // NOI18N
        Minimize.setText("jLabel1");
        Minimize.setMaximumSize(new java.awt.Dimension(30, 30));
        Minimize.setMinimumSize(new java.awt.Dimension(30, 30));
        Minimize.setPreferredSize(new java.awt.Dimension(30, 30));
        Minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MinimizeMouseClicked(evt);
            }
        });
        TopPanel.add(Minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 0, 30, 30));

        Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Close.png"))); // NOI18N
        Close.setText("jLabel1");
        Close.setMaximumSize(new java.awt.Dimension(30, 30));
        Close.setMinimumSize(new java.awt.Dimension(30, 30));
        Close.setPreferredSize(new java.awt.Dimension(30, 30));
        Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CloseMouseClicked(evt);
            }
        });
        TopPanel.add(Close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 0, 30, 30));

        CurrDate.setFont(new java.awt.Font("Stencil", 0, 20)); // NOI18N
        CurrDate.setForeground(new java.awt.Color(255, 255, 255));
        CurrDate.setText("DATE");
        TopPanel.add(CurrDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 190, 30));

        CurrTime.setFont(new java.awt.Font("BankGothic Md BT", 1, 20)); // NOI18N
        CurrTime.setForeground(new java.awt.Color(255, 255, 255));
        CurrTime.setText("CLOCK");
        TopPanel.add(CurrTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 210, 30));

        Calender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Calender.png"))); // NOI18N
        Calender.setText("jLabel1");
        Calender.setMaximumSize(new java.awt.Dimension(34, 30));
        Calender.setMinimumSize(new java.awt.Dimension(34, 30));
        Calender.setPreferredSize(new java.awt.Dimension(34, 30));
        TopPanel.add(Calender, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 34, 30));

        Clock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/Clock.png"))); // NOI18N
        Clock.setText("jLabel1");
        Clock.setMaximumSize(new java.awt.Dimension(34, 30));
        Clock.setMinimumSize(new java.awt.Dimension(34, 30));
        Clock.setPreferredSize(new java.awt.Dimension(34, 30));
        TopPanel.add(Clock, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 34, 30));

        un.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        un.setForeground(new java.awt.Color(255, 255, 255));
        TopPanel.add(un, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 100, 130, 20));

        jButton2.setBackground(new java.awt.Color(219, 76, 13));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/logout.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        TopPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 80, 40, 40));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ITP/Images/logo.png"))); // NOI18N
        TopPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 130));

        MainPanel.add(TopPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 130));

        DynamicPanel.setMaximumSize(new java.awt.Dimension(940, 480));
        DynamicPanel.setMinimumSize(new java.awt.Dimension(940, 480));
        DynamicPanel.setPreferredSize(new java.awt.Dimension(940, 480));

        javax.swing.GroupLayout DynamicPanelLayout = new javax.swing.GroupLayout(DynamicPanel);
        DynamicPanel.setLayout(DynamicPanelLayout);
        DynamicPanelLayout.setHorizontalGroup(
            DynamicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        DynamicPanelLayout.setVerticalGroup(
            DynamicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        MainPanel.add(DynamicPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 940, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizeMouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_MinimizeMouseClicked

    private void CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_CloseMouseClicked

    private void DashMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashMouseEntered
        setColor(Dash);

    }//GEN-LAST:event_DashMouseEntered

    private void SPLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SPLMouseEntered
        setColor(SPL);

    }//GEN-LAST:event_SPLMouseEntered

    private void POMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseEntered
        setColor(PO);

    }//GEN-LAST:event_POMouseEntered

    private void ProductionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductionMouseEntered
        setColor(Production);

    }//GEN-LAST:event_ProductionMouseEntered

    private void LLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LLMouseEntered
        setColor(LL);

    }//GEN-LAST:event_LLMouseEntered

    private void InventoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryMouseEntered
        setColor(Inventory);

    }//GEN-LAST:event_InventoryMouseEntered

    private void PeopleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PeopleMouseEntered
        setColor(People);

    }//GEN-LAST:event_PeopleMouseEntered

    private void DBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBMouseEntered
        setColor(DB);

    }//GEN-LAST:event_DBMouseEntered

    private void DashMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashMouseExited
        resetColor(Dash);
    }//GEN-LAST:event_DashMouseExited

    private void SPLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SPLMouseExited
        resetColor(SPL);
    }//GEN-LAST:event_SPLMouseExited

    private void POMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseExited
        resetColor(PO);
    }//GEN-LAST:event_POMouseExited

    private void ProductionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductionMouseExited
        resetColor(Production);
    }//GEN-LAST:event_ProductionMouseExited

    private void LLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LLMouseExited
        resetColor(LL);
    }//GEN-LAST:event_LLMouseExited

    private void InventoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryMouseExited
        resetColor(Inventory);
    }//GEN-LAST:event_InventoryMouseExited

    private void PeopleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PeopleMouseExited
        resetColor(People);
    }//GEN-LAST:event_PeopleMouseExited

    private void DBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBMouseExited
        resetColor(DB);
    }//GEN-LAST:event_DBMouseExited

    private void SPLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SPLMouseClicked
        dynaDisplay(spl);
    }//GEN-LAST:event_SPLMouseClicked

    private void POMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseClicked
        dynaDisplay(po);
    }//GEN-LAST:event_POMouseClicked

    private void DashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashMouseClicked
        dynaDisplay(dsb);
        dsb.updateDB();
    }//GEN-LAST:event_DashMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dynaDisplay(ep);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void InventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryMouseClicked
        dynaDisplay(i);
    }//GEN-LAST:event_InventoryMouseClicked

    private void PeopleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PeopleMouseClicked
        dynaDisplay(peo);
    }//GEN-LAST:event_PeopleMouseClicked

    private void ProductionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductionMouseClicked
        dynaDisplay(pro);
    }//GEN-LAST:event_ProductionMouseClicked

    private void LLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LLMouseClicked
        dynaDisplay(Loading);
    }//GEN-LAST:event_LLMouseClicked

    private void DBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBMouseClicked
        dynaDisplay(Maintenance);
    }//GEN-LAST:event_DBMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        login l = new login();
        l.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    void setColor(JPanel panel) {
        panel.setBackground(new Color(63, 80, 112));

    }

    void resetColor(JPanel panel) {
        panel.setBackground(new Color(50, 61, 83));

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Home().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Calender;
    private javax.swing.JLabel Clock;
    private javax.swing.JLabel Close;
    private javax.swing.JLabel CurrDate;
    private javax.swing.JLabel CurrTime;
    private javax.swing.JPanel DB;
    private javax.swing.JPanel Dash;
    private javax.swing.JLabel Dashboard1;
    private javax.swing.JLabel Dashboard10;
    private javax.swing.JLabel Dashboard2;
    private javax.swing.JLabel Dashboard4;
    private javax.swing.JLabel Dashboard5;
    private javax.swing.JLabel Dashboard7;
    private javax.swing.JLabel Dashboard8;
    private javax.swing.JLabel Dashboard9;
    private javax.swing.JLabel DashboardImg1;
    private javax.swing.JLabel DashboardImg10;
    private javax.swing.JLabel DashboardImg2;
    private javax.swing.JLabel DashboardImg4;
    private javax.swing.JLabel DashboardImg5;
    private javax.swing.JLabel DashboardImg7;
    private javax.swing.JLabel DashboardImg8;
    private javax.swing.JLabel DashboardImg9;
    private javax.swing.JPanel DynamicPanel;
    private javax.swing.JPanel Inventory;
    private javax.swing.JPanel LL;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JLabel Minimize;
    private javax.swing.JPanel PO;
    private javax.swing.JPanel People;
    private javax.swing.JPanel Production;
    private javax.swing.JPanel SPL;
    private javax.swing.JPanel SidePanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel4;
    private javax.swing.JLabel un;
    // End of variables declaration//GEN-END:variables
}
