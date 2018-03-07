package com.gyk.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDao<T extends Serializable, PK extends Serializable> implements Dao<T, PK> {

    @Autowired
    private SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(BaseDao.class);


    public static final int LIMIT_MAX_VALUE = 50000;
    public static final int LIMIT_PAGE_MAX = 10000;
    private String tableName;
    protected Class<T> modelClass;


    protected BaseDao() {
        /*Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.modelClass = (Class<T>) p[0];
        }
        logger.info("c-------------> " + c.getSimpleName());
        logger.info("t----------> " + t.getTypeName());
        logger.info("modelClass-----> " + modelType().getSimpleName());
        logger.info("come in default");*/
        create(this);
    }

    /**
     * 工厂方法，用于创建dao实例
     *
     * @param baseDao
     */
    protected abstract void create(BaseDao<T, PK> baseDao);

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setModelClass(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public Class modelType() {
        return this.modelClass;
    }

    public String tableName() {
        return this.tableName;
    }

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    public T get(PK id) {
        return (T) getCurrentSession().get(modelType().getName(), id);
    }

    public List<T> findAll() {
        return getCurrentSession().createSQLQuery("SELECT * FROM " + tableName() + " where DELETED != 1").list();
    }

    public T findOneBy(String field, Object... values) throws IllegalArgumentException {
        return (T)getCurrentSession();
    }

    public T save(Object t) {
        return (T) getCurrentSession().save(t);
    }


    public void delete(T entity){
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteByKey(PK id) {
        delete(get(id));
    }
}
