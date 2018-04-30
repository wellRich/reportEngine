package cn.guoyka.simplermybatis.dao.mapper;

import cn.guoyka.simplermybatis.dao.BaseMapper;
import cn.guoyka.simplermybatis.dao.sqlProvider.ReportSql;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.util.search.QueryFilter;
import cn.guoyka.simplermybatis.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * Report实体的mapper
 * 这个接口完全可以使用脚本来生成，脚本会在后续完成
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report>{
    String RESULT_MAP = "cn.guoyka.simplermybatis.entity.allOfReport";

    @Override
    @InsertProvider(type = ReportSql.class, method = BaseMapper.INSERT)
    int insert(Object o);

    @Override
    @InsertProvider(type = ReportSql.class, method = BaseMapper.ADD)
    int add(Object o);

    @Override
    @UpdateProvider(type = ReportSql.class, method = BaseMapper.UPDATE)
    int update(Object o);

    @Override
    @DeleteProvider(type = ReportSql.class, method = BaseMapper.DELETE)
    int delete(Object obj);

    //@Override
    @DeleteProvider(type = ReportSql.class, method = BaseMapper.BATCH_DELETE)
    int batchDelete(String keys);

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.GET)
    @ResultMap(RESULT_MAP)
    Report get(Object key);

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.FIND_BY_IDS)
    @ResultMap(RESULT_MAP)
    List<Report> findByIds(String ids);

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.FIND_ALL)
    @ResultMap(RESULT_MAP)
    List<Report> findAll();

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.PAGE_SEEK)
    @ResultMap(RESULT_MAP)
    List<Report> pageSeek(QueryReq req, int pageNum, int pageSize);

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.SEEK)
    @ResultMap(RESULT_MAP)
    List<Report> seek(QueryReq req);

    @Override
    @SelectProvider(type = ReportSql.class, method = BaseMapper.COUNT_BY)
    int countBy(String field, QueryFilter... filters);

    @SelectProvider(type = ReportSql.class, method = "batchInsert")
    void batchInsert(Collection<Report> entities, boolean hasPrimary);
}
