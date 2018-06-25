package cn.guoyka.simplermybatis.api;

import cn.guoyka.simplermybatis.entity.Report;

import java.util.List;

public interface ReportApi {

    List<Report> findAll();

    Report findById(Object id);
}
