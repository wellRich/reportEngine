package cn.guoyka.simplermybatis.util.search;

import cn.guoyka.simplermybatis.dao.BaseMapper;

import java.util.List;
import java.util.function.Supplier;

/**
 * 用于封装分页查询
 *
 * @author guoyka
 * @version 2018/3/21
 */
public final class SeekResp<T>{

    /*
    数据总数
     */
    private int totalRecord;

    /*
    每页大小
     */
    private int pageSize;


    private int offset;


    /*
    当前页码
     */
    private int pageIndex;

    /*
    总页数
     */
    private int totalPage;

    /*
    实体数据
     */
    private List<T> dataList;

    public SeekResp() {
    }


    /**
     * 使用分页查询
     * @param pageIndex 当前页码
     * @param pageSize 每页数量
     * @param total 总数
     * @param req 查询封装
     * @param mapper 实现或者继承BaseMapper的mybatis Mapper
     * @param <T> 泛型
     * @return 分页查询结果的封装对象
     */
    public static <T> SeekResp<T> buildQueryResp(int pageIndex, int pageSize, int total, SeekReq req, BaseMapper<T> mapper){
        SeekResp<T> resp = new SeekResp<T>(pageIndex, pageSize);
        resp.query(() -> mapper.pageSeek(req, pageIndex, pageSize));
        if (total == 0){
            resp.count(() -> mapper.countBy(null, (SeekFilter[]) req.search.toArray()));
        }else {
            resp.setTotalRecord(total);
        }
        return resp;
    }

    public SeekResp(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.offset = (pageIndex - 1) * pageSize;
    }

    public void query(Supplier<List<T>> supplier){
        setDataList(supplier.get());
    }

    public void count(Supplier<Integer> count){
        setTotalRecord(count.get());
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return this.totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        setTotalPage(getPageCount());
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    protected int getPageCount() {
        return getPageSize() == 0 ? 0 : (getTotalRecord() + getPageSize() - 1) / getPageSize();
    }
}
