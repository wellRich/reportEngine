package cn.guoyka.simplermybatis.service;

import cn.guoyka.simplermybatis.api.ReportApi;
import cn.guoyka.simplermybatis.dao.mapper.ReportMapper;
import cn.guoyka.simplermybatis.dao.mapper.ReportTplMapper;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.entity.ReportTpl;
import cn.guoyka.simplermybatis.util.KeyGreaterUtil;
import cn.guoyka.simplermybatis.util.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Transactional
public class ReportApiImpl implements ReportApi {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportTplMapper reportTplMapper;

    @Autowired
    private RedisClient redisClient;




    public String key = "100";

    @Override
    @Cacheable(value="findAll", key = "#root.targetClass.simpleName")
    public List<Report> findAll() {
        System.out.println("未使用缓存时---》   ");
        return reportMapper.findAll();
    }


    @Override
    @Cacheable(value = "rpt-findById", key="#id")
    public Report findById(Object id){
        return reportMapper.get(id);
    }


    public void addReportTpl(){
        ReportTpl report = new ReportTpl(){{
            setTitle("测试");
            setId(KeyGreaterUtil.greater('T'));
        }};
        reportTplMapper.insert(report);
    }
}
