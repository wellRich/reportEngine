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
    }`


3. 将查询语句使用QueryReq与QueryFilter封装，使用默认实现的pageSeek与countBy进行分页查询


