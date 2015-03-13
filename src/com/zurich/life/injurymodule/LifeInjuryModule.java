/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;

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


import com.zurich.life.utility.*;


/**
 *
 * @author louie.zheng
 */
public class LifeInjuryModule {
//private static final String QUERY_SQL ="select DataID from GTL_TO_PDF_MODEL where DataID = ? ";
private static final String QUERY_SQL = "select Serial_No from CASNT_SerialNo_Def_Tb where YY = ?";      
        //"select Serial_No from [CASNT_PA_AccuAmt_Tb] where YY = ? and MM = ? and DD = ?";
    /**
     * @param args the command line arguments
     */
    private static Logger logger = Logger.getLogger(LifeInjuryModule.class);
    
    
   private void Process400(){
       FileIO ioDealer=new FileIO();
       String oldFileDest ="D:/Life/400/";
       String newFileDest ="D:/Life/400/RESP/";
       logger.info("From:"+oldFileDest+"  To:"+newFileDest);
       
        try {
            isExistsOrder("14", "03", "13");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LifeInjuryModule.class.getName()).log(Level.SEVERE, null, ex);
        }
       ioDealer.cpyAllFile(oldFileDest,newFileDest);
       
       
   } 
 
       private boolean isExistsOrder(String YY,String MM, String DD) throws Exception {//"select DataID from GTL_TO_PDF_MODEL where DataID = ? "
            Map result = QueryUtils36.getInstance().querySingle(QUERY_SQL, new Object[]{YY});
            if (result != null) {// && !StringUtils.isEmpty((String) result.get("DataID"))
                System.out.println("...true");
                return true;
                
            } else {
                System.out.println("....false");
                return false;
                
            }
        }
       

    public static void main(String[] args) {
        // TODO code application logic here
        //logger.info("TEST log");
        
        //ioDealer.copyFile(null, null);
                //資料夾來源路徑
 
        
        LifeInjuryModule InjuryModuleI=new LifeInjuryModule();
        InjuryModuleI.Process400();
        
        
        
    }
}
