/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospital.bed.management.frames;

import com.raven.chart.ModelChart;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.ScrollBar;

/**
 *
 * @author bonbo
 */
public class Hospital_5 extends javax.swing.JFrame {

    ArrayList<Double> bedsPublic = new ArrayList<Double>();
    ArrayList<Double> bedsPrivate = new ArrayList<Double>();
    
    public Hospital_5() {
        initComponents();
        panelBorder2.initMoving(Hospital_5.this);
        
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane1.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane1.setHorizontalScrollBar(new ScrollBar());
        jScrollPane1.getHorizontalScrollBar().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane3.setHorizontalScrollBar(new ScrollBar());
        jScrollPane3.getHorizontalScrollBar().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        
        setRegionBedsToVariablesLoop();
        setHospitalInfoToTable12();
        setHospitalInfoToTable11();
        
        chart1.addLegend("Private", new Color(16, 69, 71));
        chart1.addLegend("Public", new Color(108, 197, 81));
        
        
        chart1.addData(new ModelChart("I", new double[]{bedsPrivate.get(0), bedsPublic.get(0)}));
        chart1.addData(new ModelChart("II", new double[]{bedsPrivate.get(1), bedsPublic.get(1)}));
        chart1.addData(new ModelChart("III", new double[]{bedsPrivate.get(2), bedsPublic.get(2)}));
        chart1.addData(new ModelChart("IV-A", new double[]{bedsPrivate.get(3), bedsPublic.get(3)}));
        chart1.addData(new ModelChart("MIMAROPA", new double[]{bedsPrivate.get(4), bedsPublic.get(4)}));
        chart1.addData(new ModelChart("V", new double[]{bedsPrivate.get(5), bedsPublic.get(5)}));
        chart1.addData(new ModelChart("VI", new double[]{bedsPrivate.get(6), bedsPublic.get(6)}));
        chart1.addData(new ModelChart("VII", new double[]{bedsPrivate.get(7), bedsPublic.get(7)}));
        chart1.addData(new ModelChart("VIII", new double[]{bedsPrivate.get(8), bedsPublic.get(8)}));
        chart1.addData(new ModelChart("IX", new double[]{bedsPrivate.get(9), bedsPublic.get(9)}));
        chart1.addData(new ModelChart("X", new double[]{bedsPrivate.get(10), bedsPublic.get(10)}));
        chart1.addData(new ModelChart("XI", new double[]{bedsPrivate.get(11), bedsPublic.get(11)}));
        chart1.addData(new ModelChart("XII", new double[]{bedsPrivate.get(12), bedsPublic.get(12)}));
        chart1.addData(new ModelChart("XIII", new double[]{bedsPrivate.get(13), bedsPublic.get(13)}));
        chart1.addData(new ModelChart("NCR", new double[]{bedsPrivate.get(14), bedsPublic.get(14)}));
        chart1.addData(new ModelChart("CAR", new double[]{bedsPrivate.get(15), bedsPublic.get(15)}));
        chart1.addData(new ModelChart("ARMM", new double[]{bedsPrivate.get(16), bedsPublic.get(16)}));
    }

    DefaultTableModel model;
    
    public void setHospitalInfoToTable11() {
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT h.hospName, r.regionName,h.hospType, h.covidBeds, h.nonCovidBeds, h.covidPatients, h.nonCovidPatients\n" +
                    "FROM hospital_bed.region r, \n" +
                    "	hospital_bed.hospital_information h\n" +
                    "WHERE h.hospRegion = r.regionName \n" +
                    "GROUP BY r.regionName, h.hospType\n" +
                    "ORDER BY h.hospID;");
         
           while(rs.next()) {
               String hospName = rs.getString("h.hospName");
               String regionName = rs.getString("r.regionName");
               String covidBeds = rs.getString("h.covidBeds");
               String nonCovidBeds = rs.getString("h.nonCovidBeds");
               String covidPatients = rs.getString("h.covidPatients");
               String nonCovidPatients = rs.getString("h.nonCovidPatients");
               
               
               Object[] obj = {hospName, regionName, covidBeds, nonCovidBeds, covidPatients, nonCovidPatients};
               model = (DefaultTableModel) tableDark1.getModel();
               model.addRow(obj);
               
               System.out.println("I am at model.addRow " );
           }
         
        } catch (Exception e) {
             e.printStackTrace();    
        }
    }
    
    public void setHospitalInfoToTable12() {
        
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT r.regionName,h.hospType, sum(h.covidBeds) as \"totalCovidBeds\",sum(h.nonCovidBeds) as \"totalNonCovidBeds\",sum(h.covidBeds+h.nonCovidBeds) as \"totalBed\",sum(h.covidBeds-h.covidPatients) as \"totalVacantCovidBeds\", sum(h.nonCovidBeds-h.nonCovidPatients) as \"totalVacantNonCovidBeds\"\n" +
                    "FROM hospital_bed.region r, \n" +
                    "	hospital_bed.hospital_information h\n" +
                    "WHERE h.hospRegion = r.regionName \n" +
                    "GROUP BY r.regionName, h.hospType\n" +
                    "ORDER BY sum(h.covidBeds) desc;");
         
           while(rs.next()) {
               String regionName = rs.getString("r.regionName");
               String hospType = rs.getString("h.hospType");
               String covidBeds = rs.getString("totalCovidBeds");
               String nonCovidBeds = rs.getString("totalNonCovidBeds");
               String vacantCovidBeds = rs.getString("totalVacantCovidBeds");
               String vacantNonCovidBeds = rs.getString("totalVacantNonCovidBeds");
               
               Object[] obj = {regionName, hospType, covidBeds, nonCovidBeds, vacantCovidBeds, vacantNonCovidBeds};
               model = (DefaultTableModel) tableDark2.getModel();
               model.addRow(obj);
               
               System.out.println("I am at model.addRow " );
           }
         
        } catch (Exception e) {
             e.printStackTrace();    
        }
    }
    
    public void setRegionBedsToVariablesLoop() {
        /*
        bedsPrivate.add(Reg1Priv);
        bedsPublic.add(Reg1Pub);
        bedsPrivate.add(Reg2Priv);
        bedsPublic.add(Reg2Pub);
        bedsPrivate.add(Reg3Priv);
        bedsPublic.add(Reg3Pub);
        
        bedsPrivate.add(Reg4aPriv);
        bedsPublic.add(Reg4aPub);
        bedsPrivate.add(RegMIMPriv);
        bedsPublic.add(RegMIMPub);
        bedsPrivate.add(Reg5Priv);
        bedsPublic.add(Reg5Pub);
        
        bedsPrivate.add(Reg6Priv);
        bedsPublic.add(Reg6Pub);
        bedsPrivate.add(Reg7Priv);
        bedsPublic.add(Reg7Pub);
        bedsPrivate.add(Reg8Priv);
        bedsPublic.add(Reg8Pub);
        
        bedsPrivate.add(Reg9Priv);
        bedsPublic.add(Reg9Pub);
        bedsPrivate.add(Reg10Priv);
        bedsPublic.add(Reg10Pub);
        bedsPrivate.add(Reg11Priv);
        bedsPublic.add(Reg11Pub);
        
        bedsPrivate.add(Reg12Priv);
        bedsPublic.add(Reg12Pub);
        bedsPrivate.add(Reg13Priv);
        bedsPublic.add(Reg13Pub);
        bedsPrivate.add(RegNCRPriv);
        bedsPublic.add(RegNCRPub);
        
        bedsPrivate.add(Reg9Priv);
        bedsPublic.add(Reg9Pub);
        bedsPrivate.add(Reg10Priv);
        bedsPublic.add(Reg10Pub);
        bedsPrivate.add(Reg11Priv);
        bedsPublic.add(Reg11Pub);
        
        bedsPrivate.add(RegCARPriv);
        bedsPublic.add(RegCARPub);
        bedsPrivate.add(RegBARMMPriv);
        bedsPublic.add(RegBARMMPub);
        */
        
        
        
        
        
        /*
        =================================================== GUMAGANA ===================================
        ArrayList < String > lists1 = new ArrayList < String > ();
        ArrayList < String > lists2 = new ArrayList < String > ();
        String str1 = "";
        double dou1 ;
        String str2 = "";
        double dou2 ;
        
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT sum(h.covidBeds+h.nonCovidPatients) as \"totalBeds\"\n" +
                    "FROM hospital_bed.region r, \n" +
                    "	hospital_bed.hospital_information h\n" +
                    "WHERE h.hospRegion = r.regionName AND (r.regionId=\"1\" AND h.hospType=\"public\")\n" +
                    "GROUP BY r.regionName;");
            
             while(rs.next()) {
                 lists1.add(rs.getString("totalBeds"));
             }
             System.out.println("lists1 public " +lists1.get(0));
             
             for (String list : lists1) {
                 str1 += list;
             }
             System.out.println("str1 " +str1);
             
             dou1 = Double.parseDouble(str1);
             System.out.println("dou1 " +dou1);
             
             bedsPublic.add(dou1);
             System.out.println("bedspublic " +bedsPublic.get(0));
             
            } catch (Exception e) {
             e.printStackTrace();    
         }
        
        
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT sum(h.covidBeds+h.nonCovidPatients) as \"totalBeds\"\n" +
                    "FROM hospital_bed.region r, \n" +
                    "	hospital_bed.hospital_information h\n" +
                    "WHERE h.hospRegion = r.regionName AND (r.regionId=\"1\" AND h.hospType=\"private\")\n" +
                    "GROUP BY r.regionName;");
            
             while(rs.next()) {
                 lists2.add(rs.getString("totalBeds"));
             }
             System.out.println("lists2 public " +lists2.get(0));
             
             for (String list : lists2) {
                 str2 += list;
             }
             System.out.println("str2 " +str2);
             
             dou2 = Double.parseDouble(str2);
             System.out.println("dou2 " +dou2);
             
             bedsPrivate.add(dou2);
             System.out.println("bedsprivate " +bedsPrivate.get(0));
             
            } catch (Exception e) {
             e.printStackTrace();    
         }
        =================================================== GUMAGANA ===================================
        */
        
        
        
        
        
        
        
        System.out.println("entered function ");
        for (int i=1; i <= 17; i++){
            
            ArrayList < String > lists1 = new ArrayList < String > ();
            String str1 = "";
            double dou1 ;
            System.out.println("entered for loop public "+ i );
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT sum((h.covidBeds-h.covidPatients)+(h.nonCovidBeds-h.nonCovidPatients)) as \"totalBeds\"\n" +
                       "FROM hospital_bed.region r, \n" +
                       "	hospital_bed.hospital_information h\n" +
                       "WHERE h.hospRegion = r.regionName AND (r.regionId=\""+ i +"\" AND h.hospType=\"public\")\n" +
                       "GROUP BY r.regionName;");

                while(rs.next()) {
                    lists1.add(rs.getString("totalBeds"));
                }
                //System.out.println("lists1 public " +lists1.get(i));

                for (String list : lists1) {
                    str1 += list;
                }
                System.out.println("str1 " +str1);

                dou1 = Double.parseDouble(str1);
                System.out.println("dou1 " +dou1);

                bedsPublic.add(dou1);
                //System.out.println("bedspublic " +bedsPublic.get(i));      
            } catch (Exception e) {
                e.printStackTrace();    
            }
            
        }
        
        for (int i=1; i <= 17; i++){
            ArrayList < String > lists2 = new ArrayList < String > ();
            String str2 = "";
            double dou2;
            System.out.println("entered for loop private "+ i );
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT sum(h.covidBeds+h.nonCovidBeds) as \"totalBeds\"\n" +
                       "FROM hospital_bed.region r, \n" +
                       "	hospital_bed.hospital_information h\n" +
                       "WHERE h.hospRegion = r.regionName AND (r.regionId=\""+ i +"\" AND h.hospType=\"private\")\n" +
                       "GROUP BY r.regionName;");

                while(rs.next()) {
                    lists2.add(rs.getString("totalBeds"));
                }
                //System.out.println("lists2 public " +lists2.get(i));

                for (String list : lists2) {
                    str2 += list;
                }
                System.out.println("str2 " +str2);

                dou2 = Double.parseDouble(str2);
                System.out.println("dou2 " +dou2);

                bedsPrivate.add(dou2);
                //System.out.println("bedsprivate " +bedsPrivate.get(i));
             
            } catch (Exception e) {
             e.printStackTrace();    
            }
            
        }
        
        /*
        System.out.println("Private1");
        for (double i : bedsPrivate) {
            System.out.println(i);
            System.out.println("Public1");
        }
        
        System.out.println("Public1");
        for (double i : bedsPublic) {
            System.out.println(i);
            System.out.println("Public1");
        }
        */
    }
    
    // Clear the table
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
        model.setRowCount(0);
    }
    
    // to fetch the patient details from the database and display it to panel
    // search
    public void getHospitalRecords() {
        
        String hospName_search =jTextField5.getText();
        //String studentNumber = jTextField2.getText();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_bed", "root", "@Lacorte2001");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM hospital_bed.hospital_information WHERE hospName REGEXP (\""+ hospName_search +"\");");
            
            if (rs.next()) {
               do {
                    String hospName = rs.getString("hospName");
                    String hospRegion = rs.getString("hospRegion");
                    String covidPatients = rs.getString("covidPatients");
                    String nonCovidPatients = rs.getString("nonCovidPatients");
                    String covidBeds = rs.getString("covidBeds");
                    String nonCovidBeds = rs.getString("nonCovidBeds");
               
               Object[] obj = {hospName, hospRegion, covidPatients, nonCovidPatients, covidBeds, nonCovidBeds};
               model = (DefaultTableModel) tableDark1.getModel();
               model.addRow(obj);
               } while (rs.next());
               
               
               //System.out.println("I am at model.addRow at search function " +userID);
            } else {
                JOptionPane.showMessageDialog(this, "No Hospital name " + hospName_search + " found");
                setHospitalInfoToTable11();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder2 = new swing.PanelBorder();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panelBorder3 = new swing.PanelBorder();
        chart1 = new com.raven.chart.Chart();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDark1 = new tabledark.TableDark();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDark2 = new tabledark.TableDark();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("STATISTICS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText(" -");
        jLabel15.setName(""); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(19, 19));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText(" x");
        jLabel16.setName(""); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(19, 19));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 153, 153));
        jLabel18.setText("HOSPITAL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1500, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelBorder3.setBackground(new java.awt.Color(199, 228, 224));

        jButton3.setBackground(new java.awt.Color(153, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Find Hospital:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 153, 153));
        jButton7.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("SEARCH");
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jTextField5)
                .addGap(0, 0, 0)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hosp Name", "Region", "Covid Bed", "NonCovid Bed", "Covid Pat", "NonCovid Pat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableDark1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Table of hospital bed per region:");

        tableDark2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Region", "Hospital Type", "Covid Beds", "NonCovid Beds", "Vacant CB", "Vacant NCB"
            }
        ));
        jScrollPane1.setViewportView(tableDark2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 301, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel20.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("PHILIPPINES: AVAILABLE BED OCCUPANCY IN HOSPITALS PER REGION");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBorder3Layout = new javax.swing.GroupLayout(panelBorder3);
        panelBorder3.setLayout(panelBorder3Layout);
        panelBorder3Layout.setHorizontalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chart1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(163, 163, 163))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBorder3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(panelBorder3, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked

        int answer = JOptionPane.showConfirmDialog(null, "Exit to Windows?", "Exit",JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            System.exit(0);
        }
        /*
        Ask_exit ae = new Ask_exit();
        ae.setVisible(true);
        ae.pack();
        ae.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
        //this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        Main_hospital_dash mah = new Main_hospital_dash();
        mah.setVisible(true);
        mah.pack();
        mah.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        String hospName_search =jTextField5.getText();
        
        if (hospName_search.isEmpty()) {
            clearTable();
            setHospitalInfoToTable11();
        } else {
            System.out.println("Prompted");
            clearTable();
            getHospitalRecords();
        }
        
        //clearTable();
        //getHospitalRecords();
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(Hospital_5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hospital_5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hospital_5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hospital_5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hospital_5().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField5;
    private swing.PanelBorder panelBorder2;
    private swing.PanelBorder panelBorder3;
    private tabledark.TableDark tableDark1;
    private tabledark.TableDark tableDark2;
    // End of variables declaration//GEN-END:variables
}
