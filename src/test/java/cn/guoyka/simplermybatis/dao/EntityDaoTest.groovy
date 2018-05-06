package cn.guoyka.simplermybatis.dao

import cn.guoyka.simplermybatis.entity.Report

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 1.0 , 2018/4/30
 */
class EntityDaoTest extends GroovyTestCase {
    void testBatchInsert() {
        EntityDao dao = new EntityDao() {
            @Override
            Class init() {
                return Report.class
            }
        }

        dao.add(new Report());
    }
}
