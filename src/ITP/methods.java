package ITP;




import com.mysql.jdbc.StringUtils;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vimukthi
 */
public class methods {
    Connection con;
    Statement st;
    String fileName=null;
    void dbConnection(){    //to establish data base connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/production", "root", "");
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    void search(String val, String query, JTable table){
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        //System.out.println(query);
        int count = -1;
        for (int i = 0; i < rowCount; i++) {  //to clear current table
            for (int j = 0; j < columnCount; j++) {
                table.getModel().setValueAt(" ", i, j);
            }
        }
        try {
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                count++;
                for(int b=0;b<columnCount; b++){
                    table.getModel().setValueAt(rs.getString(b+1), count, b);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    void setPageCount(int dbTableRows, int cbValue, JLabel label){
        int pc;
        
        if((dbTableRows%cbValue)!=0){
            pc=(dbTableRows/cbValue)+1;
        }
        else{
            pc=(dbTableRows/cbValue);
        }
        label.setText(Integer.toString(pc));
    }
    public int getRowCount(String tableName) {
        int rc = 0;
        try {
            ResultSet rs=st.executeQuery("select * from "+tableName);
            while(rs.next()){
                rc++;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rc;
    }
    void dynamicTable(JTextField tf, JComboBox cb, JLabel l, JTable table, String tableName) {
        /*setPageCount(getRowCount(tableName), Integer.parseInt(cb.getSelectedItem().toString()), l);
        
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        int count=-1;
        int tbValue =Integer.parseInt(tf.getText());   //get textbox value as int
        int cbValue =Integer.parseInt(cb.getSelectedItem().toString());//get combobox value as int
        int lValue  =Integer.parseInt(l.getText());   //get label value as int
        
        for (int i = 0; i < rowCount; i++) {  //to clear current table data
        for (int j = 0; j < columnCount; j++) {
        table.getModel().setValueAt(" ", i, j); //update table (i,j)cell
        }
        }
        try {
        if(lValue !=1){
        ResultSet rs2= st.executeQuery("select * from "+tableName+" limit "+cbValue+" offset "+cbValue*(tbValue-1));
        while(rs2.next()){
        count++;
        for(int b=0;b<columnCount; b++){
        table.getModel().setValueAt(rs2.getString(b+1), count, b);    //update table (count,b)cell
        }
        }
        }else{
        ResultSet rs2= st.executeQuery("select * from "+tableName+" limit "+cbValue);
        while(rs2.next()){
        count++;
        for(int b=0;b<columnCount; b++){
        table.getModel().setValueAt(rs2.getString(b+1), count, b);
        }
        }
        }
        } catch (SQLException ex) {
        System.out.println(ex);
        }*/
        dynamicTable(tf, cb, l, table, tableName, "*");
    }
    void dynamicTable(JTextField tf, JComboBox cb, JLabel l, JTable table, String tableName, String columns) {
        setPageCount(getRowCount(tableName), Integer.parseInt(cb.getSelectedItem().toString()), l);
        
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        int count=-1;
        int tbValue =Integer.parseInt(tf.getText());   //get textbox value as int
        int cbValue =Integer.parseInt(cb.getSelectedItem().toString());//get combobox value as int
        int lValue  =Integer.parseInt(l.getText());   //get label value as int
        if(tbValue<=lValue && tbValue!=0){
            for (int i = 0; i < rowCount; i++) {  //to clear current table data
                for (int j = 0; j < columnCount; j++) {
                    table.getModel().setValueAt(" ", i, j); //update table (i,j)cell
                }
            }
            try {
                if(lValue !=1){
                    ResultSet rs2= st.executeQuery("select "+columns+" from "+tableName+" limit "+cbValue+" offset "+cbValue*(tbValue-1));
                    while(rs2.next()){
                        count++;
                        for(int b=0;b<columnCount; b++){
                            table.getModel().setValueAt(rs2.getString(b+1), count, b);    //update table (count,b)cell
                        }
                    }
                }else{
                    ResultSet rs2= st.executeQuery("select "+columns+" from "+tableName+" limit "+cbValue);
                    while(rs2.next()){
                        count++;
                        for(int b=0;b<columnCount; b++){
                            table.getModel().setValueAt(rs2.getString(b+1), count, b);
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        else {
            tf.setText("1");
            dynamicTable(tf, cb, l, table, tableName, columns);
        }
    }
    boolean validateEmail(String val){ //www.stackoverflow.com
        if(!val.equals("")){
            String pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
            Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(val);
            if(matcher.find()){
               return true; 
            }
            else return false;
        }else return true;
    }
    boolean validatePhone(String val, JLabel label){ //www.stackoverflow.com
        if(!val.equals("")){
            if(val.startsWith("07")||val.startsWith("011")||val.startsWith("+94")){
                if(val.startsWith("+94")){
                    if(val.length()==12){
                        return true;
                    }
                    else {
                        label.setText("Invalid phone number !");
                        return false;
                    }
                }
                else if(val.length()==10){
                    return true;
                }
                else {
                    label.setText("Invalid phone number !");
                    return false;
                }
            }
            String ph_pattern="^\\+?[0-9. ()-]{10,25}$";
            if(val.matches(ph_pattern)){
                return true;
            }
            else{ 
                label.setText("Invalid phone number !");
                return false;
            }
        }else {
            label.setText("Phone number field cannot be empty !");
            return false;
        }
    }
    boolean validateNIC(String val){
        String nic_pattern="^[0-9]{9}V$";
        if(val.matches(nic_pattern)){
            return true;
        }
        else return false;
    }
    boolean validateVAT(String val){
        String vat_pattern="^[0-9]{9}-[0-9]{4}$";
        if(val.matches(vat_pattern)){
            return true;
        }
        else return false;
    }
    boolean validateEPF(String val){
        String epf_pattern="^S[0-9]{5}$";
        if(val.matches(epf_pattern)){
            return true;
        }
        else return false;
    }
    boolean validatePCode(String val){
        String epf_pattern="^[0-9]{5}$";
        if(val.matches(epf_pattern)){
            return true;
        }
        else return false;
    }
    boolean checkSubmit(){
        int no=JOptionPane.showConfirmDialog(null, "Do you want to save this data? ", "Check before Submitting", 1);
        if(no==JOptionPane.YES_OPTION){
            return true;
        }
        else return false;
    }
    boolean validateBirthday(String val, JLabel label){
        int []arr=new int[3];
        int a=0;
        if(val.contains("[a-zA-Z]+")){
            label.setText("Invalid Birth date !");
            return false;
        }else {
            if(!val.equals("") && !val.equals("dd-mm-yyyy")){
                StringTokenizer tk = new StringTokenizer(val, "-");
                while(tk.hasMoreElements()){
                    arr[a]=Integer.parseInt(tk.nextToken());
                    //System.out.println(arr[a]);
                    a++;
                }
                
                if((arr[0]<31 && arr[0]>0) && (arr[1]<13 && arr[1]>0) && (arr[2]>1950 && arr[2]<2000)){
                    return true;
                }
                else{
                    label.setText("Invalid Birth date !");
                    return false;
                }
            }else{
                    label.setText("Date of birth field cannot be empty !");
                    return false;
            }
        }
    }
    int deleterow(String s, String table, String firstColumn) throws SQLException{
        return(st.executeUpdate("DELETE FROM "+table+" WHERE "+firstColumn+"="+s));
    }
    boolean validateUniqueFeild(JTextField tf, JLabel l, String table, String columnName, int edit) throws SQLException{
            if(edit!=1){
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM "+table+" WHERE "+columnName+" ='"+tf.getText()+"'");
                if(rs.next()){
                    //l.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
                    l.setText(tf.getText()+" already exist !");
                    //JOptionPane.showMessageDialog (null, "style exist", "error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else{
                    return true;
                }
            }else return true;
    }
    ImageIcon ResizeImage(String imgPath, JLabel label){
        ImageIcon image = new ImageIcon(imgPath);
        Image img = image.getImage();
        Image newImage=img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(newImage);
        return i;
    }
    void loadImage(String primaryKey, JLabel label){
        try {
            ResultSet rs = st.executeQuery("select image from employee where empNo ='"+primaryKey+"'");
            if(rs.next()){
                byte[] img = rs.getBytes("image");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image s = im.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(s);
                label.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void getFileName(JLabel label, JLabel label1){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg; *.jpeg)","jpg","jpeg"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)","png"));
        int result=chooser.showSaveDialog(null);
        if(result== JFileChooser.APPROVE_OPTION){ // check System.out.println(chooser.getSelectedFile().length());
            File selectedFile=chooser.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
            label.setIcon(ResizeImage(fileName, label));
            
        }
        label1.setText(fileName);
    }
    void saveImage(String primaryKey){
        if(fileName!=null){
            try {
                String sql = "update employee set image=? where epfNo='"+primaryKey+"'";
                PreparedStatement pst = con.prepareStatement(sql);
                InputStream is = new FileInputStream(new File(fileName));
                pst.setBlob(1, is);
                pst.executeUpdate();
            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void getImage(JLabel label, String primaryKey){
        try {
            System.out.println(primaryKey);
            String sql = "select image from employee where empNo='"+primaryKey+"'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);           
            if(rs.next()){
                byte[] img = rs.getBytes(1);
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image s = im.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(s);
                label.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*boolean validateJoinedDate(JXDatePicker D){
    Date ed= (Date) D.getDate();
    Format df=new SimpleDateFormat("yyyy");
    Format df1=new SimpleDateFormat("MM");
    
    String sd=df.format(ed);
    int Syear=Integer.parseInt(sd);
    int sMonth=Integer.parseInt(df1.format(ed));
    
    
    Date d = new Date();
    DateFormat f= new SimpleDateFormat("yyyy");
    DateFormat f1=new SimpleDateFormat("MM");
    
    String cDate=f.format(d);
    int cYear=Integer.parseInt(cDate);
    int cMonth=Integer.parseInt(f1.format(d));
    /*
    System.out.println(Syear);
    System.out.println(sMonth);
    System.out.println("current");
    System.out.println(cYear);
    System.out.println(cMonth);
    System.out.println(Syear==cYear);
    System.out.println(cMonth==sMonth);
    System.out.println("function---");
    if(Syear==cYear){
    if((cMonth-sMonth>=0) &&(cMonth-sMonth<=1)){
        return true;
    }
    else{
        return false;
    }
}
    return false;
}*/
    /*public static void main(String[] args) {
    methods m = new methods();
    System.out.println(m.validateEPF("V00000"));
    }*/
    
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
    public static void main(String[] args) {
        methods m = new methods();
        String val="11-10-1965";
        //JLabel label = null;
        //System.out.println(m.validateBirthday(val));
    }
 
}

