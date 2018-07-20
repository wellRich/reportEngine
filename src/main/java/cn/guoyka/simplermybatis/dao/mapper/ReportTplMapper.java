package cn.guoyka.simplermybatis.dao.mapper;

import cn.guoyka.simplermybatis.dao.BaseMapper;
import cn.guoyka.simplermybatis.dao.sqlProvider.ReportTplSql;
import cn.guoyka.simplermybatis.entity.ReportTpl;
import cn.guoyka.simplermybatis.util.search.SeekFilter;
import cn.guoyka.simplermybatis.util.search.SeekReq;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ReportTplMapper extends BaseMapper<ReportTpl> {
    @Override
    @InsertProvider(type = ReportTplSql.class, method = BaseMapper.INSERT)
    int insert(Object o);

    @Override
    @InsertProvider(type = ReportTplSql.class, method = BaseMapper.ADD)
    int add(Object o);

    @Override
    @UpdateProvider(type = ReportTplSql.class, method = BaseMapper.UPDATE)
    int update(Object o);

    @Override
    @DeleteProvider(type = ReportTplSql.class, method = BaseMapper.DELETE)
    int delete(Object obj);

    //@Override
    @DeleteProvider(type = ReportTplSql.class, method = BaseMapper.BATCH_DELETE)
    int batchDelete(String keys);

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.GET)
    ReportTpl get(Object key);

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.FIND_BY_IDS)
    List<ReportTpl> findByIds(String ids);

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.FIND_ALL)
    List<ReportTpl> findAll();

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.PAGE_SEEK)
    List<ReportTpl> pageSeek(SeekReq req, int pageNum, int pageSize);

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.SEEK)
    List<ReportTpl> seek(SeekReq req);

    @Override
    @SelectProvider(type = ReportTplSql.class, method = BaseMapper.COUNT_BY)
    int countBy(String field, SeekFilter... filters);

    @SelectProvider(type = ReportTplSql.class, method = "batchInsert")
    void batchInsert(Collection<ReportTpl> entities, boolean hasPrimary);
    
}
