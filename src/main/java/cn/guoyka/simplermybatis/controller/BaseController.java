package cn.guoyka.simplermybatis.controller;

import cn.guoyka.simplermybatis.dao.EntityDao;
import cn.guoyka.simplermybatis.dao.mapper.ReportMapper;
import cn.guoyka.simplermybatis.entity.Report;
import cn.guoyka.simplermybatis.entity.ReportTpl;
import com.google.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
@Controller
@RequestMapping({"/redis/home", "/"})
public class BaseController {




    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String home(){
        EntityDao<Report> reportEntityDao = EntityDao.getDao(Report.class);
        EntityDao<ReportTpl> reportTplEntityDao = EntityDao.getDao(ReportTpl.class);
        System.out.println("come in reportTplEntityDao" + reportTplEntityDao.getFieldsAndCols());

        System.out.println("come in reportEntityDao" + reportEntityDao.getFieldsAndCols());
        return "views/index";
    }


}
