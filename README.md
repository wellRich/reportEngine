# simpler-mybatis

## 这个项目解决了什么问题？
1. crud的默认实现
2. 使用脚本生成实体类的Mapper.xml文件
3. 实现后台分页


![Mapper.xml](https://github.com/wellRich/simpler-mybatis/raw/master/doc/mapper_xml.png)

## 搭建这个项目的起因：
1. 最近的项目使用Mybatis
2. 增删改查代码逻辑雷同，感觉是在做体力活，没激情
3. 不愿意写太多xml配置文件
4. 实体类对应的数据表中的字段与实体的属性名不一致，也不愿记忆



## 实现思路/技术

1. 充分利用`org.apache.ibatis.jdbc.SQL`这个类，使用它来将我们封装好的对象解析为原生sql或者是带有表达式的sql
2. 使用EntityDao封装、储存实体类的属性、get方法、字段
	`package cn.guoyka.simplermybatis.dao;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import cn.guoyka.simplermybatis.annotation.Column;
import cn.guoyka.simplermybatis.annotation.Table;
import cn.guoyka.simplermybatis.util.search.QueryFilter;
import cn.guoyka.simplermybatis.util.search.QueryReq;


/**
 * CRUD的默认实现SqlProvider
 *
 * @author guoyka
 * @version 2018/3/17
 */
public abstract class EntityDao<T extends Serializable> implements BaseSql<T> {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(EntityDao.class);

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表格主键对应的实体属性名称
     */
    private String primaryField;

    /**
     * 众列名称，以逗号分割的字符串
     */
    private String columns;

    private List<String> fields = new ArrayList<>();

    private List<String> fieldsExcPrimary = new ArrayList<>();

    /**
     * 不包括主键的众列名称，同样是以逗号分割的字符串
     */
    private String columnsExcPrimary;

    /**
     * 结构[属性：字段]
     */
    private Map<String, String> fieldsAndCols = new HashMap<>();

    /**
     * [属性：字段]，不包括主键
     */
    private Map<String, String> fiesAndColsExcPrimary = new HashMap<>();

    private Map<String, Method> fieldAndMethod = new HashMap<>();

    private Class<T> clazz;


    public abstract Class<T> init();


    protected EntityDao() {
        log.info("entitySql无参构造器被调用------------> " + this);
        clazz = init();
        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table.name();
        this.primaryField = table.primaryKey();
        Field[] fields = clazz.getDeclaredFields();
        int size = fields.length;
        Field field;
        String colName;
        String fieldName;
        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (int i = 0; i < size; i++) {
                field = fields[i];
                fieldName = field.getName();

                //只针对做了com.digital.util.search.Column注解的字段
                if (fields[i].isAnnotationPresent(Column.class)) {
                    this.fields.add(fieldName);
                    fieldAndMethod.put(fieldName, clazz.getMethod("get" + upperCaseFirst(fieldName)));
                    colName = field.getAnnotation(Column.class).name();
                    if (!this.primaryField.equals(field.getName())) {//过滤有问题
                        this.fiesAndColsExcPrimary.put(fieldName, colName);
                        stringBuilder.append(colName).append(i < size - 1 ? "," : "");
                        this.fieldsExcPrimary.add(fieldName);
                    }
                    this.fieldsAndCols.put(fieldName, colName);
                    sb.append(colName).append(i < size - 1 ? "," : "");
                }
            }
        } catch (NoSuchMethodException ex) {
            log.error("entityDao.constructor.error-------> " + ex.getMessage());
        }

        this.columns = sb.toString();
        this.columnsExcPrimary = stringBuilder.toString();
    }

    private String upperCaseFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    //会插入主键
    public String insert(final Object entity) {
        String sql = new SQL() {{
            INSERT_INTO(tableName);
            getFieldsAndCols().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
        }}.toString();
        log.info("entityDao.insert.sql----> " + sql);
        return sql;
    }

    //不会插入主键
    public String add(final Object entity) {
        String sql = new SQL() {{
            INSERT_INTO(tableName);
            getFiesAndColsExcPrimary().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
        }}.toString();
        log.info("entityDao.insert.sql----> " + sql);
        return sql;
    }


    private String batchInsert(Collection<T> entities, List<String> fields) {
        StringBuilder re = new StringBuilder(" VALUES ");
        try {
            for (T entity : entities) {
                StringBuilder sb = new StringBuilder("(");

                for (String key : fieldsExcPrimary) {
                    Method m = fieldAndMethod.get(key);
                    Object val = m.invoke(entity);
                    sb.append("'").append(val).append("',");
                }
                sb.append("),");
                re.append(sb);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("entityDao.batchInsert.error----> " + ex.getMessage());
        }


        System.out.println("----" + re.toString().replaceAll("\\),$", ")"));
        String sql = new SQL() {{
            INSERT_INTO(tableName);
            INTO_COLUMNS(columnsExcPrimary);
        }}.toString() + re.toString()
                .replaceAll(",\\)", ")")
                .replaceAll("'null'", "null")
                .replaceAll("\\),$", ")");
        log.info("entityDao.batchInsert.sql----> " + sql);
        return sql;
    }


    /**
     * 批量插入
     *
     * @param entities   实体集合
     * @param hasPrimary 插入的属性是否包括主键
     * @return
     */
    public String batchInsert(Collection<T> entities, boolean hasPrimary) {
        return batchInsert(entities, hasPrimary ? fields : fieldsExcPrimary);
    }

    /**
     * 删除实例对象
     *
     * @param obj 可以是主键、实例对象
     * @return
     */
    public String delete(Object obj) {
        String sql;
        if (obj == null) {
            log.error("操作的对象为空！");
            throw new RuntimeException("对象为空！");
        } else {
            if (isEntity(obj)) {
                sql = new SQL() {{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=#{" + getPrimaryField() + "}");
                }}.toString();
            } else {
                sql = new SQL() {{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=" + obj);
                }}.toString();
            }
            log.info("entityDao.delete.sql--------------> " + sql);
            return sql;
        }
    }

    /**
     * @param keys 以“,”分隔的主键值
     * @return sql
     */
    public String batchDelete(String keys) {
        String sql = new SQL() {{
            DELETE_FROM(getTableName());
            WHERE(getColumnByField(getPrimaryField()) + " IN (" + keys + ")");
        }}.toString();
        log.info("entityDao.batchDelete.sql----------> " + sql);
        return sql;
    }

    /**
     * 更新实例对象
     * 如果传入map，则据键值对修改对象，
     * 如果传入是一个已在的实例，则会将此实例的所有属性更新至数据库，
     * 建议使用map作为参数
     *
     * @param group 包含主键的map：[keyName: value] 或者 实例对象
     * @return sql
     */
    public String update(final Object group) {
        String sql;
        if (group == null) {
            throw new RuntimeException("传入对象不存在！");
        } else {
            if (isEntity(group)) {
                sql = new SQL() {{
                    UPDATE(tableName);
                    getFiesAndColsExcPrimary().forEach((k, v) -> SET(v + "=#{" + k + "}"));
                    WHERE(getColumnByField(primaryField) + "=#{" + primaryField + "}");
                }}.toString();
            } else if (group instanceof Map) {
                Map temp = (Map) group;
                if (temp.containsKey(primaryField)) {
                    sql = new SQL() {{
                        UPDATE(tableName);
                        temp.forEach((k, v) -> {
                            SET(getColumnByField(k.toString()) + "=#{" + k + "}");
                        });
                        WHERE(getColumnByField(primaryField) + "=#{" + primaryField + "}");
                    }}.toString();
                } else {
                    throw new RuntimeException("未传入主键值！");
                }
            } else {
                throw new IllegalArgumentException("只接收实体对象或者[属性-属性值]结构的map对象");
            }
            log.info("entityDao.update.sql-------> " + sql);
            return sql;
        }
    }

    /**
     * 批量更新实例
     *
     * @param params       需要更新的字段与值组成的键值对
     * @param queryFilters 过滤条件
     * @return sql
     */
    public String batchUpdate(Map<String, Object> params, QueryFilter... queryFilters) {
        String sql = new SQL() {{
            UPDATE(getTableName());
            if (params instanceof Map) {
                params.forEach((k, v) -> {
                    SET(getColumnByField(k.toString()) + "=#{" + k + "}");
                });
                parseFilters(this, queryFilters);
            } else {
                throw new IllegalArgumentException("只接收[属性:值]结构的map对象");
            }
        }}.toString();
        return sql;
    }


    //通过主键获取对象
    public String get(Object primaryKey) {
        String sql = new SQL() {{
            FROM(tableName);
            SELECT(columns);
            WHERE(getColumnByField(getPrimaryField()) + "=" + primaryKey);
        }}.toString();
        log.info("entityDao.get.sql------------> " + sql);
        return sql;
    }


    /**
     * 通过若干主键获取对象集合
     *
     * @param ids 以“,”分隔的id
     * @return sql
     */
    public String findByIds(String ids) {
        String sql = new SQL() {{
            FROM(tableName);
            SELECT(getColumns());
            WHERE(getColumnByField(getPrimaryField()) + "  IN (" + ids + ")");
        }}.toString();
        log.info("entityDao.findByIds.sql ---> " + sql);
        return sql;
    }

    /**
     * 统计查询
     *
     * @param field   统计的字段，默认是主键
     * @param filters 过滤条件
     * @return sql
     */
    public String countBy(String field, QueryFilter... filters) {
        String sql = new SQL() {{
            FROM(tableName);
            if (field != null && "".equals(field)) {
                if (getColumnByField(field) == null) {
                    SELECT("count(" + primaryField + ")");
                } else {
                    SELECT("count(" + field + ")");
                }
            } else {
                SELECT("count(" + primaryField + ")");
            }
            parseFilters(this, filters);
        }}.toString();
        sql = rename(sql, getFieldsAndCols());
        log.info("entityDao.countBy.sql------> " + sql);
        return sql;
    }

    /**
     * 分页查询
     *
     * @param req       查询封装对象
     * @param pageIndex 当前在页码
     * @param pageSize  每页大小
     * @return sql
     */
    public String pageSeek(QueryReq req, int pageIndex, int pageSize) {
        List<QueryFilter> filters = req.search;
        int offset = (pageIndex - 1) * pageSize;
        String sql = new SQL() {{
            FROM(getTableName());
            if (req.selectFields == null || req.selectFields.equals("")) {
                SELECT(getColumns());
            } else {
                SELECT(req.selectFields);
            }
            parseFilters(this, filters.toArray(new QueryFilter[]{}));
            if (req.sort != null) {
                ORDER_BY(req.sort);
            }
        }}.toString() + " LIMIT " + pageSize + " OFFSET " + offset;
        sql = rename(sql, getFieldsAndCols());
        log.info("entityDao.pageSeek.sql----------> " + sql);
        return sql;
    }

    /**
     * 根据指定的若干条件查询若干字段查询
     *
     * @param req 查询封装对象
     * @return sql
     */
    public String seek(QueryReq req) {
        List<QueryFilter> filters = req.search;
        String sql = new SQL() {{
            FROM(getTableName());
            if (req.selectFields == null || req.selectFields.equals("")) {
                SELECT(getColumns());
            } else {
                SELECT(req.selectFields);
            }
            parseFilters(this, filters.toArray(new QueryFilter[]{}));
            if (req.sort != null) {
                ORDER_BY(req.sort);
            }
        }}.toString();
        sql = rename(sql, fieldsAndCols);
        log.info("entityDao.seek.sql-----> " + sql);
        return sql;
    }

    private void parseFilters(SQL sql, QueryFilter... queryFilters) {
        sql.WHERE("1 = 1");
        for (QueryFilter filter : queryFilters) {
            if (filter.getLogic().equals(QueryFilter.LOGIC_AND)) {
                sql.AND();
                sql.WHERE(filter.toSqlPart());
            } else {
                sql.OR();
                sql.WHERE(filter.toSqlPart());
            }
        }
    }

    public String findAll() {
        String sql = new SQL() {{
            SELECT(columns);
            FROM(tableName);
        }}.toString();
        log.info("entityDao.findAll.sql----------> " + sql);
        return sql;
    }


    //通过字段名获取数据列名
    protected String getColumnByField(String fieldName) {
        return fieldsAndCols.get(fieldName);
    }

    private boolean isEntity(Object obj) {
        return obj.getClass().equals(clazz);
    }

    public String getTableName() {
        return tableName;
    }


    public Map<String, String> getFieldsAndCols() {
        return fieldsAndCols;
    }

    public String getColumns() {
        return columns;
    }

    public String getColumnsExcPrimary() {
        return columnsExcPrimary;
    }

    public Map<String, String> getFiesAndColsExcPrimary() {
        return fiesAndColsExcPrimary;
    }

    public String getPrimaryField() {
        return primaryField;
    }

    public Class<T> getClazz() {
        return clazz;
    }

}
`
3. 将查询语句使用QueryReq与QueryFilter封装，使用默认实现的pageSeek与countBy进行分页查询


