package cn.guoyka.simplermybatis.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import cn.guoyka.simplermybatis.util.search.SeekFilter;
import cn.guoyka.simplermybatis.util.search.SeekReq;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public interface BaseSql<T extends Serializable> {


    default  String rename(String json, Map<String, String> renameMap) {
        if (renameMap == null) {
            return json;
        } else {
            String key;
            for(Iterator iterator = renameMap.keySet().iterator(); iterator.hasNext(); json = json.replace(key, renameMap.get(key))) {
                key = (String)iterator.next();
            }
            return json;
        }
    }

    String insert(Object o);
    String add(Object o);
    String batchInsert(Collection<T> entities, boolean hasPrimary);
    String update(Object o);
    String delete(Object o);
    String get(Object o);
    String seek(SeekReq req);
    String pageSeek(SeekReq req, int pageIndex, int pageSize);
    String countBy(String field, SeekFilter... filters);
    String findByIds(String ids);
    String findAll();
    String batchDelete(String keys);


}
