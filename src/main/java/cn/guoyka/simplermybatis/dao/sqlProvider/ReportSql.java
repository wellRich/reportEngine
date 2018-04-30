package cn.guoyka.simplermybatis.dao.sqlProvider;

import cn.guoyka.simplermybatis.dao.BaseSql;
import cn.guoyka.simplermybatis.dao.EntityDao;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.util.search.QueryFilter;
import cn.guoyka.simplermybatis.util.search.QueryReq;

import java.util.Collection;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
public class ReportSql implements BaseSql<Report> {

    public static EntityDao entityDao = new EntityDao() {
        @Override
        public Class init() {
            return Report.class;
        }
    };


    @Override
    public String pageSeek(QueryReq req, int pageIndex, int pageSize) {
        return entityDao.pageSeek(req, pageIndex, pageSize);
    }

    @Override
    public String countBy(String field, QueryFilter... filters) {
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
    public String seek(QueryReq req) {
        return entityDao.seek(req);
    }

    @Override
    public String get(Object o) {
        return entityDao.get(o);
    }

    @Override
    public String batchDelete(Collection<?> keys) {
        return entityDao.batchDelete(keys);
    }
}
