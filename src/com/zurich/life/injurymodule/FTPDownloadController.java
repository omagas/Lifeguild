/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;

import com.zurich.life.utility.FTPUtil;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;


/**
 *
 * @author louie.zheng
 */
public class FTPDownloadController {



    
    
    // call the utility method
    public static void Controller(){
        String server = "10.8.1.253";
        int port = 21;
        String user = "batn06";
        String pass = "ZITVPNnjxug";        
        FTPClient ftpClient = new FTPClient();

        try{
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
                        // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();
 
            System.out.println("Connected");
            // log out and disconnect from the server
            
            
            

 

    // directory on the server to be downloaded
        String remoteDirPath = "/batN06";

    // directory where the files will be saved
        String saveDirPath = "D:/Life/400/RESP";//D:\Life\400\RESP
        FTPUtil ftpu=new FTPUtil();

        ftpu.downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath);
        
        
        ftpClient.logout();
        ftpClient.disconnect();
        System.out.println("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FTP下載發生錯誤");
        }
    }
    
    
    
}
