/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.injurymodule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.zurich.life.utility.*;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author louie.zheng
 */
public class FTPUploadController {
    private String LocalFile;
    private boolean completed;

    public String getLocalFile() {
        return LocalFile;
    }

    public void setLocalFile(String LocalFile) {
        this.LocalFile = LocalFile;
    }

    public FTPUploadController() {

    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    
    protected void controller() throws FileNotFoundException, IOException{
        
        System.out.println(this.getClass().getName());
        Properties pt=new PropertiesTool().getProperties("ftpconfig.properties");
        String server = pt.getProperty("ftp.server").toString();
        int port = Integer.parseInt(pt.getProperty("ftp.port"));
        String user = pt.getProperty("ftp.user").toString();
        String pass = pt.getProperty("ftp.pass").toString();
        String preaperUpDirPath=pt.getProperty("ftp.preaperUpDirPath").toString();
        String remoteDirPath_dir=pt.getProperty("ftp.remoteDirPath").toString();
        
        
        
        
		FTPClient ftpClient = new FTPClient();
		try {

                        ftpClient.connect(server, port);
                        ftpClient.login(user, pass);
                        ftpClient.enterLocalPassiveMode();

                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

//			// APPROACH #1: uploads first file using an InputStream
//			File firstLocalFile = new File("D:/Life/400/LNPA20150309_IA.txt");
//
//			String firstRemoteFile = "IA0001.zip";
//			InputStream inputStream = new FileInputStream(firstLocalFile);
//
//			System.out.println("Start uploading first file");
//			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
//			inputStream.close();
//			if (done) {
//				System.out.println("The first file is uploaded successfully.");
//			}

                        // APPROACH #2: uploads second file using an OutputStream
                        File secondLocalFile = new File(preaperUpDirPath+this.LocalFile);
                        System.out.println("..........LocalFile..prepare up load loc:"+secondLocalFile);
                        String secondRemoteFile = remoteDirPath_dir+"/"+this.LocalFile;
                        InputStream inputStream = new FileInputStream(secondLocalFile);

                        System.out.println("Start uploading  file");
                        OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);

                            //ftpClient.deleteFile("MA0001.txt");
                        byte[] bytesIn = new byte[4096];
                        int read = 0;

                        while ((read = inputStream.read(bytesIn)) != -1) {
                                outputStream.write(bytesIn, 0, read);
                        }
                        inputStream.close();
                        outputStream.close();

                        boolean completed = ftpClient.completePendingCommand();
                        if (completed) {
                                System.out.println("The  file is uploaded successfully.");
                                setCompleted(completed);
                        }

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
                        } catch (IOException ex) {
                                ex.printStackTrace();
                        }
                        
		}    
               
    }
    
}
