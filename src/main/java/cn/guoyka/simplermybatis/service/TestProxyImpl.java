package cn.guoyka.simplermybatis.service;

import cn.guoyka.simplermybatis.api.TestProxy;
import cn.guoyka.simplermybatis.dao.mapper.ReportTplMapper;
import cn.guoyka.simplermybatis.entity.ReportTpl;
import cn.guoyka.simplermybatis.util.KeyGreaterUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Aspect
public class TestProxyImpl implements TestProxy {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public String hi() {
        System.out.println("hello中文");
        return "hello";
    }

    @Autowired
    private ReportApiImpl reportApi;

    @Autowired
    private ReportTplMapper reportTplMapper;

    @Pointcut("execution(public java.util.List<cn.guoyka.simplermybatis.entity.Report> *findAll())")
    public void middle(){

    }


    @Before("middle()")
    public void testAop(JoinPoint joinPoint){
        System.out.println("Aop--------------> " + joinPoint.getTarget());

        System.out.println("Aop--------------> " + joinPoint.getSignature());
    }

    private void testAdd(){
        ReportTpl report = new ReportTpl(){{
            setTitle("测试");
            setId(KeyGreaterUtil.greater('T'));
        }};
        reportTplMapper.insert(report);
    }

    @Autowired
    private TestProxyImpl testProxy;

    public void test(){
        testProxy.testAdd();
        throw new NullPointerException("抛个异常");
    }
}
