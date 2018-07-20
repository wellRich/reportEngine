package cn.guoyka.simplermybatis.dao.sqlProvider;

import cn.guoyka.simplermybatis.dao.BaseSql;
import cn.guoyka.simplermybatis.dao.EntityDao;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.util.search.SeekFilter;
import cn.guoyka.simplermybatis.util.search.SeekReq;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
@Component
public class ReportSql implements BaseSql<Report> {

    public final static EntityDao<Report> entityDao = new EntityDao<Report>() {
        @Override
        public Class init() {
            return Report.class;
        }
    };


    public static EntityDao<Report> getEntityDao() {
        return entityDao;
    }

    /**
     * 批量插入
     * @param entities 实例集合
     * @param hasPrimary 是否需要插入主键
     * @return sql
     */
    public String batchInsert(Collection<Report> entities, boolean hasPrimary){
        return entityDao.batchInsert(entities, hasPrimary);
    }

    @Override
    public String pageSeek(SeekReq req, int pageIndex, int pageSize) {
        return entityDao.pageSeek(req, pageIndex, pageSize);
    }

    @Override
    public String countBy(String field, SeekFilter... filters) {
        return entityDao.countBy(field, filters);
    }

    @Override
    public String findByIds(String ids) {
        return entityDao.findByIds(ids);
    }

    @Override
    public String insert(Object o) {
        return entityDao.insert(o);
    }

    @Override
    public String add(Object o) {
        return entityDao.add(0);
    }

    @Override
    public String update(Object o) {
        return entityDao.update(o);
    }

    @Override
    public String delete(Object o) {
        return entityDao.delete(o);
    }

    @Override
    public String findAll() {
        return entityDao.findAll();
    }

    @Override
    public String seek(SeekReq req) {
        return entityDao.seek(req);
    }

    @Override
    public String get(Object o) {
        return entityDao.get(o);
    }

    //@Override
    public String batchDelete(String keys) {
        return entityDao.batchDelete(keys);
    }
}
