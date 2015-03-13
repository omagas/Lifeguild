/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.utility;

import com.zurich.sds.batch.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ??DBSetting.properties 設�??? ??? Connection, Batch 使�?
 * @author jason.huang
 */
public class ConnectionFactory4Properties extends ConnectionFactory {

    private Log log = LogFactory.getLog(this.getClass());
    private String driver = null;
    private String url = null;
    private String user = null;
    private String passwd = null;

    private ConnectionFactory4Properties() {
        init();
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory4Properties();
        }
        return instance;
    }

    private void init() {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("DBSettingLG");
            driver = resource.getString("db.driver.class");
            url = resource.getString("db.url");
            user = resource.getString("db.user");
            passwd = resource.getString("db.passwd");
            Class.forName(driver).newInstance();
            log.info("initial 36  JDBC driver OK");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new RuntimeException("Get database connection failed, caused by: " + e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, passwd);
    }
}
