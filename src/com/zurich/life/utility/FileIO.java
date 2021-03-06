/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.utility;
import java.io.*;
/**
 *
 * @author louie.zheng
 */
public class FileIO {
private static FileIO instance = new FileIO();
    
  private static FileIO getFileIO(){
      return instance;
  }
    
  public  void cpyAllFile(String SrcDir,String DistDir){//Move all file from origin.
        int file_count=0;
        System.out.println("From:"+SrcDir+"  To:"+DistDir);
        File oldDir = new File(SrcDir);//可以寫相對路徑。
        //File distDir = new File(DistDir);//可以寫相對路徑。
        File[] files = oldDir.listFiles();//列出檔案        
        System.out.println("total of files:"+files.length);
        
        
        for (File f : files) {//列舉一種新的陣列寫法 意同 for(int i=0;i<files.lengthi++){}
            System.out.println("files name:"+f.getName());
            String fname=f.getName();
            if(fname.indexOf(".txt")!=-1){  
                file_count++;
//                File sourceDemo = new File(oldDir.getAbsolutePath() + "/"
//                        + f.getName());
//                File destDemo = new File(distDir.getAbsolutePath() + "/"
//                        + f.getName());
                moveFile(SrcDir+ "/"+f.getName(),DistDir+"/"+f.getName());//move files
            } 
            
        } 
        System.out.println("Valid files:"+file_count);
  }   
  
  
      // copy file 複製單獨檔案
    public static void copy(File src, File dst) throws IOException {
        
        
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    

/**
* 新建目錄
* @param folderPath String 如c:/fqf
* @return boolean
*/
    public void newFolder(String folderPath) {
            try {
                    String filePath = folderPath;
                    filePath = filePath.toString();
                    java.io.File myFilePath = new java.io.File(filePath);
                    if (!myFilePath.exists()) {
                            myFilePath.mkdir();
                    }
            }	
            catch(Exception e) {
                    System.out.println("新建目錄操作出錯");
                    e.printStackTrace();
            }
    }


    /**
    * 新建檔
    * @param filePathAndNameString 檔路徑及名稱 如c:/fqf.txt
    * @param fileContent String檔內容
    * @return boolean
    */
    public void newFile(String filePathAndName,String fileContent) {


            try{
                    String filePath = filePathAndName;
                    filePath = filePath.toString();
                    File myFilePath = new File(filePath);
                    if (!myFilePath.exists()) {
                            myFilePath.createNewFile();
                    }
                    FileWriter resultFile = new FileWriter(myFilePath);
                    PrintWriter myFile = new PrintWriter(resultFile);
                    String strContent = fileContent;
                    myFile.println(strContent);
                    resultFile.close();

            }
            catch(Exception e) {
                    System.out.println("新建目錄操作出錯");
                    e.printStackTrace();


            }


    }


    /**
    * 刪除檔
    * @param filePathAndNameString 檔路徑及名稱 如c:/fqf.txt
    * @param fileContentString
    * @return boolean
    */
    public void delFile(String filePathAndName){
            try {
                    String filePath = filePathAndName;
                    filePath = filePath.toString();
                    java.io.File myDelFile = new java.io.File(filePath);
                    myDelFile.delete();


            }
            catch(Exception e) {
                    System.out.println("刪除檔操作出錯");
                    e.printStackTrace();


            }


    }


    /**
    * 刪除資料夾
    * @param filePathAndNameString 資料夾路徑及名稱 如c:/fqf
    * @param fileContentString
    * @return boolean
    */
    public void delFolder(String folderPath) {
            try {
                    delAllFile(folderPath); //刪除完裡面所有內容
                    String filePath = folderPath;
                    filePath = filePath.toString();
                    java.io.File myFilePath = new java.io.File(filePath);
                    myFilePath.delete(); //刪除空資料夾


            }
            catch(Exception e) {
                    System.out.println("刪除資料夾操作出錯");
                    e.printStackTrace();


            }


    }


    /**
    *刪除資料夾裡面的所有檔
    * @param path String資料夾路徑 如 c:/fqf
    */
    public void delAllFile(String path) {
            File file =new File(path);
            if(!file.exists()) {
                    return;
            }
            if(!file.isDirectory()) {
                    return;
            }
            String[]tempList = file.list();
            File temp =null;
            for (int i =0; i < tempList.length; i++) {
                    if (path.endsWith(File.separator)) {
                            temp = new File(path + tempList[i]);
                    }else {
                            temp = new File(path + File.separator + tempList[i]);
                    }
                    if (temp.isFile()) {
                            temp.delete();
                    }
                    if (temp.isDirectory()) {
                            delAllFile(path+"/"+ tempList[i]);//先刪除資料夾裡面的檔
                            delFolder(path+"/"+ tempList[i]);//再刪除空資料夾
                    }
            }
    }


    /**
    * 複製單個檔
    * @param oldPath String原檔路徑 如：c:/fqf.txt
    * @param newPath String複製後路徑 如：f:/fqf.txt
    * @return boolean
    */
    public void copyFile(String oldPath, String newPath) {
            try {
                    int bytesum = 0;
                    int byteread = 0;
                    File oldfile = new File(oldPath);
                    if (oldfile.exists()) { //檔存在時
                            InputStream inStream = new FileInputStream(oldPath);//讀入原檔
                            FileOutputStream fs = new FileOutputStream(newPath);
                            byte[] buffer = new byte[1444];
                            int length;
                            while ( (byteread = inStream.read(buffer)) != -1) {
                                    bytesum += byteread; //位元組數 檔案大小
                                    //System.out.println(bytesum);
                                    fs.write(buffer, 0, byteread);
                            }
                            inStream.close();
                    }
            }
            catch(Exception e) {
                    System.out.println("複製單個檔操作出錯");
                    e.printStackTrace();


            }


    }


    /**
    * 複製整個資料夾內容
    * @param oldPath String原檔路徑 如：c:/fqf
    * @param newPath String複製後路徑 如：f:/fqf/ff
    * @return boolean
    */
    public void copyFolder(String oldPath, String newPath) {


            try{
                    (new File(newPath)).mkdirs(); //如果資料夾不存在則建立新資料夾
                    File a=new File(oldPath);
                    String[] file=a.list();
                    File temp=null;
                    for (int i = 0; i < file.length; i++) {
                            if(oldPath.endsWith(File.separator)){
                                    temp=new File(oldPath+file[i]);
                            }
                            else{
                                    temp=new File(oldPath+File.separator+file[i]);
                            }


                            if(temp.isFile()){
                                    FileInputStream input = new FileInputStream(temp);
                                    FileOutputStream output = new FileOutputStream(newPath + "/"+
                                    (temp.getName()).toString());
                                    byte[] b = new byte[1024 * 5];
                                    int len;
                                    while ( (len = input.read(b)) != -1) {
                                            output.write(b, 0, len);
                                    }
                                    output.flush();
                                    output.close();
                                    input.close();
                            }
                            if(temp.isDirectory()){//如果是子資料夾
                                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                            }
                    }
            }
            catch(Exception e) {
                    System.out.println("複製整個資料夾內容操作出錯");
                    e.printStackTrace();

            }

    }

    /**
    * 移動檔到指定目錄
    * @param oldPath String如：c:/fqf.txt
    * @param newPath String如：d:/fqf.txt
    */
    public void moveFile(String oldPath, String newPath) {
            copyFile(oldPath, newPath);
            delFile(oldPath);

    }

    /**
    * 移動檔到指定目錄
    * @param oldPath String如：c:/fqf.txt
    * @param newPath String如：d:/fqf.txt
    */
    public void moveFolder(String oldPath, String newPath) {
            copyFolder(oldPath, newPath);
            delFolder(oldPath);

    }    
}
