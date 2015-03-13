/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.life.utility;

/**
 *
 * @author louie.zheng
 */

import com.zurich.sds.batch.utils.BatchSQL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jason.huang
 */
public class QueryUtils36 {

    private static QueryUtils36 instance = new QueryUtils36();
    private Log log = LogFactory.getLog(this.getClass());
    private Connection conn4Query = null;

    private QueryUtils36() {
    }

    public static QueryUtils36 getInstance() {
        return instance;
    }

    /**
     * ?�詢
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List query(String sql, Object[] params) throws SQLException {
        return query(sql, new MapListHandler(), params);
    }

    /**
     * ?�詢
     * @param sql
     * @param handler
     * @param params
     * @return
     * @throws SQLException
     */
    public List query(String sql, ResultSetHandler handler, Object[] params) throws SQLException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            this.processDateParam(params);
            return (List) new QueryRunner().query(conn, sql, handler, params);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
            throw e;
        } finally {
            this.closeConnection(conn);
        }
    }

    /**
     * ?�詢-�???��?�?     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Map querySingle(String sql, Object[] params) throws SQLException {
        List result = query(sql, new MapListHandler(), params);
        return CollectionUtils.isEmpty(result) ? null : (Map) result.get(0);
    }

    /**
     * insert / update / delete / call Stored Procedure 使�?
     * @param sql
     * @param params
     * @throws SQLException
     */
    public void update(String sql, Object[] params) throws SQLException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            this.processDateParam(params);
            log.info("updateSQL=" + StringUtils.trim(sql) + ", params=" + this.getParamStr(params));
            new QueryRunner().update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
            throw e;
        } finally {
            this.closeConnection(conn);
        }
    }

    /**
     * �?��??insert / update / delete / call Stored Procedure, ????��???Transaction
     * @param batchSQL
     * @throws SQLException
     */
    public void transactionUpdate(BatchSQL batchSQL) throws SQLException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            for (int i = 0; i < batchSQL.size(); i++) {
                String sqlCmd = batchSQL.getSQL(i);
                Object[] params = batchSQL.getParams(i);
                this.processDateParam(params);
                log.info("updateSQL=" + StringUtils.trim(sqlCmd) + ", params=" + this.getParamStr(params));
                new QueryRunner().update(conn, sqlCmd, params);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
            try {
                conn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
                log.error("rollback error : " + e2);
            }
            throw e;
        } finally {
            this.closeConnection(conn);
        }
    }

    /**
     * ??? seqNo, 並�? table ?��???+ 1
     * @param selectCmd
     * @param updateCmd
     * @param key
     * @return
     * @throws SQLException
     */
    public synchronized int getSequence(String selectCmd, String updateCmd, String key) throws SQLException {
        Map map = this.querySingle(selectCmd, new Object[]{key});
        int nextVal = NumberUtils.toInt(map.values().iterator().next().toString()) + 1;
        this.update(updateCmd, new Object[]{nextVal, key});
        return nextVal;
    }

    /**
     * �?java.util.Date �?? java.sql.Date, ?��??��? DBUtils ??????
     * @param params
     */
    private void processDateParam(Object[] params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null && !(params[i] instanceof Timestamp) && params[i] instanceof Date) {
                    params[i] = new java.sql.Date(((Date) params[i]).getTime());
                }
            }
        }
    }

    /**
     * params �?? String, �?log ???
     * @param params
     * @return
     */
    private String getParamStr(Object[] params) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i]);
                if (i < params.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * ??? Connection,
     * TODO : �?? batch ?��?, ????�寫??keep ?��? Connection 就好, �????? get/closeConnection
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
//        if (conn4Query == null || !conn4Query.isValid(10)) {
//            conn4Query = ConnectionFactory4Properties36.getInstance().getConnection();
//        }
//        return conn4Query;
        return ConnectionFactory4Properties.getInstance().getConnection();
    }

    /**
     * ??? Connection
     * TODO : �?? batch ?��?, ????�寫??keep ?��? Connection 就好, �????? get/closeConnection
     * @param conn
     */
    private void closeConnection(Connection conn) {
        ConnectionFactory4Properties.getInstance().releaseConnection(conn);
    }
}

