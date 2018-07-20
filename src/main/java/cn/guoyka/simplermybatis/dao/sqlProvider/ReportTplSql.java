package cn.guoyka.simplermybatis.dao.sqlProvider;

import cn.guoyka.simplermybatis.dao.BaseSql;
import cn.guoyka.simplermybatis.dao.EntityDao;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.entity.ReportTpl;
import cn.guoyka.simplermybatis.util.search.SeekFilter;
import cn.guoyka.simplermybatis.util.search.SeekReq;
import com.google.inject.ImplementedBy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class ReportTplSql implements BaseSql<ReportTpl> {
    public final static EntityDao<ReportTpl> entityDao = new EntityDao<ReportTpl>() {
        @Override
        public Class init() {
            return ReportTpl.class;
        }
    };

    public static EntityDao<ReportTpl> getEntityDao() {
        return entityDao;
    }

    @Override
    public String insert(Object o) {
        return null;
    }

    @Override
    public String add(Object o) {
        return null;
    }

    @Override
    public String batchInsert(Collection<ReportTpl> entities, boolean hasPrimary) {
        return null;
    }

    @Override
    public String update(Object o) {
        return null;
    }

    @Override
    public String delete(Object o) {
        return null;
    }

    @Override
    public String get(Object o) {
        return null;
    }

    @Override
    public String seek(SeekReq req) {
        return null;
    }

    @Override
    public String pageSeek(SeekReq req, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public String countBy(String field, SeekFilter... filters) {
        return null;
    }

    @Override
    public String findByIds(String ids) {
        return null;
    }

    @Override
    public String findAll() {
        return null;
    }

    @Override
    public String batchDelete(String keys) {
        return null;
    }
}
