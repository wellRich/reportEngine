package cn.guoyka.simplermybatis.dao;

import cn.guoyka.simplermybatis.util.search.SeekFilter;
import cn.guoyka.simplermybatis.util.search.SeekReq;

import java.util.Collection;
import java.util.List;

/**
 * 查询接口
 * 分页查询、通用查询、统计查询
 *
 * @author guoyka
 * @version 1.0, 2018/4/18
 */
public interface BaseMapper<T> {

    public static final String SEEK = "seek";

    public static final String PAGE_SEEK = "pageSeek";

    public static final String COUNT_BY = "countBy";

    public static final String GET = "get";

    public static final String UPDATE = "update";

    public static final String INSERT = "insert";

    public static final String ADD = "add";

    public static final String DELETE = "delete";


    public static final String BATCH_DELETE = "batchDelete";


    public static final String FIND_BY_IDS = "findByIds";

    public static final String FIND_ALL = "findAll";



    //插入, 会将主键一块插入
    int insert(Object o);


    //插入，不会插入主键，适合主键自动生成的情景下使用
    int add(Object o);

    /**
     * 批量插入实例
     * @param entities 实例集合
     * @param hasPrimary 是否需要插入主键值
     */
    void batchInsert(Collection<T> entities, boolean hasPrimary);

    //修改
    int update(Object o);

    /**
     * 删除实例对象
     * @param obj 可以是主键、实例对象
     * @return 被删除的数量
     */
    int delete(Object obj);


    /**
     * 通过主键批量删除实例对象
     * @param keys 主键集合
     * @return 被删除的数量
     */
    int batchDelete(Collection<?> keys);


    /**
     * 通过主键获取实例对象
     * @param key 主键值
     * @return 实例对象
     */
    T get(Object key);


    /**
     * 通过若干主键获取实例对象list
     * @param ids 若干主键值，以“,”分隔
     * @return
     */
    List<T> findByIds(String ids);

    //查找所有的实体对象
    List<T> findAll();


    /**
     * 分页查询
     * @param req 查询封装对象
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return list
     */
    List<T> pageSeek(SeekReq req, int pageNum, int pageSize);

    /**
     * 通用查询
     * @param req 查询封装对象
     * @return list
     */
    List<T> seek(SeekReq req);


    /**
     * 统计查询
     * @param field 统计字段，可以为空
     * @param filters 若干过滤条件
     * @return 数目
     */
    int countBy(String field, SeekFilter... filters);
}
