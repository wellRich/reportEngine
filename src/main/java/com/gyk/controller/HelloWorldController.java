package com.gyk.controller;

import javax.inject.Inject;
import com.gyk.dao.ReportTplDao;
import com.gyk.domain.ReportTpl;
import com.gyk.services.SqlParserService;
import com.gyk.utils.SqlParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;


@Controller
@RequestMapping("/test")
public class HelloWorldController {
    private static Logger logger = Logger.getLogger(HelloWorldController.class);

    @Inject
    ReportTplDao tplDao;
    @Inject
    SqlParserService sqlParserService;
    
    @RequestMapping(value = "/hello")
    public String HelloWorld(Model model) {
        logger.info("hello world" + "\n");
        model.addAttribute("message", "Hello World!!!");
        try {
            StringBuffer json = new StringBuffer();
            String lineText = null;
            File file = new File("C:/rules.json");
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader buf = new BufferedReader(reader);
            while ((lineText = buf.readLine()) != null){
                json.append(lineText);
            }
            buf.close();
            reader.close();
            inputStream.close();
            logger.info("json-------------> " + json);
            sqlParserService.parserSelectSql(json.toString(), "");
            //sqlParserService.parserSelectSql("{\"rules\":{ \"join\":{\"mainTable\":{\"name\": \"peop\", \"alias\": \"p\", \"on\": \"id\", \"CO\": \"=\", \"joinType\": \"LEFT JOIN\", \"joinTable\": {\"name\": \"test\", \"alias\":\"B\", \"on\":\"id\"}}},\"slim\": {\"act\":\"slim\", \"mainTable\": [{\"name\": \"peop\", \"alias\": \"p\"}, {\"name\": \"student\", \"alias\": \"学生\"}], \"columns\":[{\"N\":\"id\", \"L\": \"标识\", \"DT\": \"number\", \"S\": \"5,2\"}, {\"N\":\"name\", \"L\": \"铝管\", \"DT\": \"varchar\", \"S\": \"5,2\"}]}}}", "");
            System.out.println("-----------------------");
            //sqlParserService.parserSelectSql(json, "");
           /* ReportTpl tpl = new ReportTpl();
            tpl.setName("test2");
            tpl.setType("2");
            System.out.println("tpl-------------> " + tplDao.findAll().size());*/
            //tplDao.save(tpl);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }finally {
        }
        return "more/hello";
    }
}

