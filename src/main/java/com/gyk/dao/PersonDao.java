package com.gyk.dao;

import com.google.inject.ImplementedBy;
import com.gyk.domain.Person;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import javax.persistence.Table;

@ImplementedBy(PersonDao.DefaultImpl.class)
public interface PersonDao extends Dao<Person, Long>  {
    @Repository
    @Singleton
    final class DefaultImpl extends BaseDao<Person, Long> implements PersonDao{
        @Override
        protected void create(BaseDao<Person, Long> baseDao) {
            Class<Person> clazz = Person.class;
            baseDao.setModelClass(clazz);
            baseDao.setTableName(clazz.getAnnotation(Table.class).name());
        }
    }
}
