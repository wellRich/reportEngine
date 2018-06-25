package cn.guoyka.simplermybatis;

import cn.guoyka.simplermybatis.dao.mapper.ReportMapper;
import cn.guoyka.simplermybatis.entity.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimplerMybatisApplicationTests {

	@Autowired
	ReportMapper reportMapper;

	@Test
	public void contextLoads() {
		Set<Report> reportSet = new HashSet<>();
		for(int i = 0; i < 10000; i ++){
			reportSet.add(new Report(){{
				setName("name_" + Math.random() * 10);
			}});
		}

		long start = System.currentTimeMillis();

		reportMapper.batchInsert( reportSet, false);


		System.out.println("time--------> " + (System.currentTimeMillis() -start));



	}

	@Test
	public void testBatchDelete(){
		reportMapper.batchDelete("10024, 10025");
	}

}
