package cn.guoyka.simplermybatis;

import cn.guoyka.simplermybatis.dao.mapper.ReportMapper;
import cn.guoyka.simplermybatis.entity.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimplerMybatisApplicationTests {

	@Autowired
	ReportMapper reportMapper;

	@Test
	public void contextLoads() {
		reportMapper.insert(new Report(){{
			setName("测试");
			setNotes("说明！");
			setTemplate("tpl");
			setTitle("标题");
			setType("标准报告");
		}});
	}

}
