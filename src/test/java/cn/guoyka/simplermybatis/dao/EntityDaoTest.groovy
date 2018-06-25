package cn.guoyka.simplermybatis.dao

import cn.guoyka.simplermybatis.entity.Report
import javafx.scene.input.DataFormat

import javax.swing.text.DateFormatter
import java.time.format.DateTimeFormatter
import java.util.stream.Stream

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
        DateFormatter formatter = DataFormat.newInstance()


    }



}
