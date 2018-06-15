/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mainternance;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author pandu
 */
public class validation {
     Connection con;
    Statement st;
    
    
     boolean validateEmail(String val){ //www.stackoverflow.com
        String pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
        Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(val);
        if(matcher.find()){
           return true; 
        }
        else return false;
    }
    boolean validatePhone(String val){ //www.stackoverflow.com
        String ph_pattern="^[0-9]{10}$";
        if(val.matches(ph_pattern)){
            return true;
        }
        else return false;
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
    boolean vehiclenum(String Number){
        
          String pattern = "^[a-zA-z]{2}-[0-9]{4}$";
         if (Number.matches(pattern)){
            return true;
         }
         else {
            return false;
        }
    }
    
    boolean checkSubmit(){
        int no=JOptionPane.showConfirmDialog(null, "Do you want to save this data? ", "Check before Submitting", 1);
        if(no==JOptionPane.YES_OPTION){
            return true;
        }
        else return false;
    }
    void deleterow(String s, String table, String firstColumn) throws SQLException{
        st.executeUpdate("DELETE FROM "+table+" WHERE "+firstColumn+"="+s);
    }
    boolean validateUniqueFeild(JTextField tf, JLabel l, String table, String columnName) throws SQLException{
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM "+table+" WHERE "+columnName+" ='"+tf.getText()+"'");
        if(rs.next()){
            l.setBorder(BorderFactory.createLineBorder(Color.decode("#ff0000")));
            l.setText(tf.getText()+" already exist !");
            //JOptionPane.showMessageDialog (null, "style exist", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }
    boolean validateDateForward(String val, JLabel label){
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
                System.out.println(arr[0]);
                System.out.println(dayArr[0]);
                if((check>=0 && check<=29) && (arr[1] == dayArr[1]) && (arr[2] == dayArr[2])){
                    return true;
                }
                else{
                    label.setText("Invalid past date !");
                    return false;
                }
            }else{
                    label.setText("Date field cannot be empty !");
                    return false;
            }
        }
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
                if((check<=0) && (arr[2] == dayArr[2])){
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
}
