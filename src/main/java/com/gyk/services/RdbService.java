package com.gyk.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关系型数据库服务
 */
@Service
public class RdbService {
    private final static Logger log = Logger.getLogger(RdbService.class);

    //获取目标数据库的表
    /**
     *  列出指定数据源中所有可见的表名。
     *  @param conn  数据库连接
     *  @param catalogPattern    catalog名匹配
     *  @param schemaPattern     schema名匹配模式; 支持通配符%
     *  @param tableNamePattern  表名匹配模式; 支持通配符%
     */
    public List<Map> getTables(Connection conn, String catalogPattern, String schemaPattern, String tableNamePattern) {
        List ret = new ArrayList();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            rs = dbMetaData.getTables(
                    catalogPattern != null ? catalogPattern.toUpperCase() : null,      // catalog
                    schemaPattern != null ? schemaPattern.toUpperCase() : null,       // schemaPattern
                    tableNamePattern != null ? tableNamePattern.toUpperCase() : null,
                    null);                              // tableTypes
            while (rs.next()) {
                Map cell = new HashMap();
                cell.put("name", rs.getString(3));
                cell.put("remarks", rs.getString(5));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            closeConnection(conn, null, rs);
        }
        return ret;
    }

    private void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try{
            if (null != rs) rs.close();
            if (null != stmt) stmt.close();
            if (null != conn) conn.close();
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
    }
}
