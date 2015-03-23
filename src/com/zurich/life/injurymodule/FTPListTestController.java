/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;

import com.zurich.life.utility.PropertiesTool;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author louie.zheng
 */
public class FTPListTestController {
protected static FTPListTestController instance = null;
    


    public static synchronized FTPListTestController getInstance() {
        if (instance == null) {
            instance = new FTPListTestController();
        }
        return instance;
    }    
    
    public void WaitTilFileReady() throws FileNotFoundException, IOException, InterruptedException{
                Properties pt=new PropertiesTool().getProperties("ftpconfig.properties");
                String aa=pt.getProperty("ftp.server").toString();                                           
		String server = pt.getProperty("ftp.server").toString();
		int port = Integer.parseInt(pt.getProperty("ftp.port"));
		String user = pt.getProperty("ftp.user").toString();
		String pass = pt.getProperty("ftp.pass").toString();

                //System.out.println(server+":"+port+":"+user+":"+pass);
                
		FTPClient ftpClient = new FTPClient();
        
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
                
                int count=0;
                
            mainLoop:    
            while(true){ 
                Thread.sleep(2000); //間隔兩秒鐘跑一次
                        System.out.println(">>>Start Search");
                        FTPFile[] subFiles = ftpClient.listFiles("/batN06");

                        if (subFiles != null && subFiles.length > 0) {
                            for (FTPFile aFile : subFiles) {      
                                if(aFile.getName().indexOf("MA")!=-1){
//                                    System.out.println("IA...../batN06/"+aFile.getName());
//                                }else{
                                    System.out.println(">>>MA.QA../batN06/"+aFile.getName());
                                    break mainLoop;//直接離開巢狀迴圈
                                }
                            }
                        }else{
                            count++;
                            System.out.println(count+" Try:"+" No file");
                        }
                   
            }
            ftpClient.logout();
            ftpClient.disconnect();
            System.out.println("Disconnected");
            System.out.println("Master, I'v finished the job");
    }
    
}
