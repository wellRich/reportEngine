package cn.guoyka.simplermybatis.util.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 查询封装
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public class SeekReq<T> implements Serializable{

    //需要查询的属性
    //考虑使用表达式{},把属性框起来，解析也许更快
    public String selectFields;

   //排序
    public String sort;


    //查询条件
    public List<SeekFilter> search = new ArrayList();

    //默认查询条件，未用上
    public List<SeekFilter> restrictions = new ArrayList();

    public SeekReq() {
    }




    public SeekReq(String selectFields) {
        this.selectFields = selectFields;
    }


    public SeekReq(String sort, String selectFields) {
        this.sort = sort;
        this.selectFields = selectFields;
    }

    public String getSelectFields() {
        return this.selectFields;
    }

    public SeekReq setSelectFields(String selectFields) {
        this.selectFields = selectFields;
        return this;
    }



    public String getSort() {
        return this.sort;
    }

    public SeekReq setSort(String sort) {
        this.sort = sort;
        return this;
    }


    public SeekReq addFilter(String field, Object value, String operator, String logic) {
        return this.addFilter(new SeekFilter(field, value, operator, logic));
    }

    public SeekReq addFilter(String field, Object value, String operator) {
        return this.addFilter(new SeekFilter(field, value, operator));
    }

    public SeekReq addFilter(String field, Object value) {
        return this.addFilter(field, value, SeekFilter.OPR_IS);
    }

    public SeekReq addFilter(SeekFilter filter) {
        this.search.add(filter);
        return this;
    }

    public SeekReq addRestriction(String field, Object value, String operator) {
        return this.addRestriction(new SeekFilter(field, value, operator));
    }

    public SeekReq addRestriction(String field, Object value) {
        return this.addRestriction(field, value, SeekFilter.OPR_IS);
    }

    public SeekReq addRestriction(SeekFilter filter) {
        if (!this.exists(this.restrictions, filter.getField())) {
            this.restrictions.add(filter);
        }

        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("selectFields=>").append(this.selectFields);
        sb.append(", search=>").append(this.search);
        sb.append(", restrictions=>").append(this.restrictions);
        return sb.toString();
    }

    private boolean exists(List<SeekFilter> bucket, String field) {
        Iterator queryFilterIterator = bucket.iterator();
        SeekFilter seekFilter;
        do {
            if (!queryFilterIterator.hasNext()) {
                return false;
            }

            seekFilter = (SeekFilter)queryFilterIterator.next();
        } while(!seekFilter.getField().equals(field));

        return true;
    }
}
