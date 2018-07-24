package cn.guoyka.simplermybatis.controller;

import cn.guoyka.simplermybatis.dao.sqlProvider.ReportSql;
import cn.guoyka.simplermybatis.service.ReportApiImpl;
import cn.guoyka.simplermybatis.service.TestProxyImpl;
import cn.guoyka.simplermybatis.util.JSONHelper;
import cn.guoyka.simplermybatis.util.redis.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping({"/redis"})
public class TestRedisController {

    @Autowired
    private RedisClient redisClinet;


    @Autowired
    private ReportApiImpl reportApi;

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAllReport(){
        return JSONHelper.toJSON(reportApi.findAll());
    }

    @ApiIgnore
    @RequestMapping("/findById")
    @ResponseBody
    public Object findById(@RequestParam("id") String id){
        return JSONHelper.toJSON(reportApi.findById(id));
    }

    /**
     * 设置key和value到redis
     */
    @RequestMapping("/set")
    @ResponseBody
    public String set(String key, String value) throws Exception {
        redisClinet.set(key, value, 10);
        return "success";
    }

    /**
     * 根据key获取value
     */
    @RequestMapping("/get")
    @ResponseBody
    public String get(String key) throws Exception {
        return redisClinet.get(key);
    }

    /**
     * 设置object对象到redis
     */
    @ApiKeyAuthDefinition(key = "Authorization", name = "Authorization", in = ApiKeyAuthDefinition.ApiKeyLocation.QUERY)
    @RequestMapping("/setObj")
    @ResponseBody
    public String setObj(ReportSql reportSql) throws Exception {
        redisClinet.setObj(reportSql);
        return "success";
    }

    /**
     * 根据key获取Object对象
     */
    @RequestMapping("/getObj")
    @ResponseBody
    public String getObj(ReportSql reportSql) throws Exception {
        return JSONHelper.toJSON(redisClinet.getObj(reportSql, ReportSql.class));
    }

    @Autowired
    private TestProxyImpl testProxy;

    @RequestMapping("/testT")
    @ResponseBody
    public String testT(){
        testProxy.hi();
        testProxy.test();
        return "success: fa";
    }


}
