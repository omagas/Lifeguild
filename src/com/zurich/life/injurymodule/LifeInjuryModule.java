/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zurich.life.utility.*;
import java.sql.SQLException;
import org.omg.CORBA.TIMEOUT;


/**
 *
 * @author louie.zheng
 */
public class LifeInjuryModule {
//private static final String QUERY_SQL ="select DataID from GTL_TO_PDF_MODEL where DataID = ? ";
    private static final String QUERY_SQL = "select Serial_No from CASNT_SerialNo_Def_Tb where YY = ? and MM=? and DD=? and File_Typ=?";      
        //"select Serial_No from [CASNT_PA_AccuAmt_Tb] where YY = ? and MM = ? and DD = ?";
    /**
     * @param args the command line arguments
     */
    private static Logger logger = Logger.getLogger(LifeInjuryModule.class);
    private String UpfileName;
    
    
   private void Process400(){
       //int OldSerialNum=0;
       int NewSerialNum=0;
       String[] UpNameArray=new String[100];
       String oldFileDest ="D:/Life/400/";
       String newFileDest ="D:/Life/400/RESP/";
       logger.info("From:"+oldFileDest+"  To:"+newFileDest);
       

  
       String NewfileNm_endfix= String.format("%04d", NewSerialNum);  //數字補零
       
       UpNameArray=toIA(oldFileDest,newFileDest,NewSerialNum);
       System.out.println("UpNameArray:"+UpNameArray[0]);
       System.out.println("UpNameArray:"+UpNameArray[1]);
       System.out.println("UpNameArray:"+UpNameArray[2]);
       
       toMA(oldFileDest,newFileDest,NewfileNm_endfix);
       
 
   } 
   
   
   
   

   private String[] toIA(String oldFileDest,String newFileDest,int New_Serial_Num){//取號number跟PA相同
       int file_count=0;
       FileIO ioDealer=new FileIO();
       String[] UpNameArray=new String[100];

       //String NewfileNm="IA"+newfileNm_endfix; 
       //System.out.println("toPA..."+NewfileNm);
       //ioDealer.cpyAllFile(oldFileDest,newFileDest);    
       File oldDir = new File(oldFileDest);//可以寫相對路徑。
       File[] files = oldDir.listFiles();//列出檔案 
       
       SNumberGenerator sn=new SNumberGenerator();//new file name Generator
        for (File f : files) {//列舉一種新的陣列寫法 意同 for(int i=0;i<files.lengthi++){}
            System.out.println("files name:"+f.getName());
            String fname=f.getName();
           
   
            if(fname.indexOf(".txt")!=-1){  
                file_count++;
                sn.Gen_NEW_File_Name("IA");
                System.out.println("....getSerial_Num....:"+sn.getNEW_File_Name());//get new file name by SNumberGenerator
                
//                File sourceDemo = new File(oldDir.getAbsolutePath() + "/"
//                        + f.getName());
//                File destDemo = new File(distDir.getAbsolutePath() + "/"
//                        + f.getName());
                  UpNameArray[file_count]=sn.getNEW_File_Name();
                ioDealer.moveFile(oldFileDest+ "/"+f.getName(),newFileDest+"/"+sn.getNEW_File_Name());//move files
            } 
            
        }        
       return UpNameArray;
   }     
   
   private void toMA(String oldFileDest,String newFileDest,String newfileNm_endfix){//取號number跟PA相同
       FileIO ioDealer=new FileIO();
       String NewfileNm="MA"+newfileNm_endfix; 
       System.out.println("toMA..."+NewfileNm);
       ioDealer.cpyAllFile(oldFileDest,newFileDest);       
   }   
   
 

       

    public static void main(String[] args) {
        // TODO code application logic here
        //logger.info("TEST log");
        //ioDealer.copyFile(null, null);
        //資料夾來源路徑
        PropertiesTool propertiesTool = new PropertiesTool();
        LifeInjuryModule lifeInjuryModule = new LifeInjuryModule();
        //********
        //Step1.Module Process move file then mofify file name.
        //*******
        lifeInjuryModule.Process400();
        //********
        //Step2.Upload files to ftp
        //*******
        //FTPUploadController fTPUploadController = new FTPUploadController();
        
        
        //********
        //Step3.Sleep for a moment.
        //*******
        //        try {
        //            System.out.println("I'm going to bed");
        //            Thread.sleep(10000);
        //            System.out.println("I wake up");
        //        } catch (InterruptedException ex) {
        //            java.util.logging.Logger.getLogger(LifeInjuryModule.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        //********
        //Step4.Download feedback files from ftp Location D:/Life/400/RESP
        //*******
        //FTPDownloadController fTPDownloadController = new FTPDownloadController();

        
    }
}
