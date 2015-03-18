/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;

import java.util.logging.Level;
import org.apache.log4j.Logger;

import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.SQLException;
import com.zurich.life.utility.*;
/**
 *
 * @author louie.zheng
 */
public class SNumberGenerator {
       private int serial_Num;
       private String NEW_File_Name;
       private String yy;
       private String mm;
       private String dd;
       private static Logger logger = Logger.getLogger(LifeInjuryModule.class);

    public SNumberGenerator() {
        
        
       SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
       String inputTime = sdf.format(new Date());
       System.out.printf("Your Date is = %s\n", inputTime);
       
       String dateString[]=inputTime.split("-");
       this.yy=dateString[0];
       this.mm=dateString[1];
       this.dd=dateString[2];


    }
       
    public void Gen_NEW_File_Name(String type){
        
        try {
            this.serial_Num=isExistsOrder(this.yy, this.mm, this.dd,type);
            NEW_File_Name=type.trim()+String.format("%04d", serial_Num)+".txt";
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SNumberGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
       
       
    
       private int isExistsOrder(String YY,String MM, String DD,String Type) throws Exception {//"select DataID from GTL_TO_PDF_MODEL where DataID = ? "
            
           Map result = QueryUtils36.getInstance().querySingle("select Serial_No from CASNT_SerialNo_Def_Tb where YY = ? and MM=? and DD=? and File_Typ=?", new Object[]{YY,MM,DD,Type});
            int Num=0;
            if (result != null) {// && !StringUtils.isEmpty((String) result.get("DataID"))
                System.out.println("...true...is not empty");
                logger.info("...true...is not empty");
                System.out.println("...Serial_No:"+result.get("Serial_No"));
                logger.info("...Serial_No:"+result.get("Serial_No"));
                
                Num=Integer.parseInt(result.get("Serial_No").toString());//get sql colunm Serial_No
                Num=Num+1;

                    QueryUtils36.getInstance().update("UPDATE [dbo].[CASNT_SerialNo_Def_Tb] SET [Serial_No] = ? WHERE  YY = ? and MM=? and DD=? and File_Typ=?", new Object[]{Num,YY,MM,DD,Type});                        

             
                return Num;
                
            } else {
                System.out.println("....false...is empty");
                logger.info("....false...is empty");
                Num=0;
                Num=Num+1;

                    QueryUtils36.getInstance().update("INSERT INTO [dbo].[CASNT_SerialNo_Def_Tb]([YY],[MM],[DD],[File_Typ],[Serial_No])VALUES(?,?,?,?,?)", new Object[]{YY,MM,DD,Type,Num});              


                
                return Num;
                
            }
        }

    public String getNEW_File_Name() {
        return NEW_File_Name;
    }

    public void setNEW_File_Name(String NEW_File_Name) {
        this.NEW_File_Name = NEW_File_Name;
    }
       


    public String getDd() {
        return dd;
    }

    public String getMm() {
        return mm;
    }

    public String getYy() {
        return yy;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }
    
    
    
}
