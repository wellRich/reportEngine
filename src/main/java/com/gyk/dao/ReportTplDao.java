package com.gyk.dao;

import com.google.inject.ImplementedBy;
import com.gyk.domain.ReportTpl;
import org.jboss.logging.annotations.Property;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import javax.persistence.Table;

@ImplementedBy(ReportTplDao.DefaultImpl.class)
public interface ReportTplDao extends Dao<ReportTpl, Long> {
    @Singleton
    @Repository
    final class DefaultImpl extends BaseDao<ReportTpl, Long> implements ReportTplDao {
        @Override
        protected void create(BaseDao<ReportTpl, Long> baseDao) {
            Class<ReportTpl> clazz = ReportTpl.class;
            baseDao.setModelClass(clazz);
            baseDao.setTableName(clazz.getAnnotation(Table.class).name());
        }
    }

}
