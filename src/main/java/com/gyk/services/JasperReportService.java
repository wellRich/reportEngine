package com.gyk.services;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import com.fasterxml.jackson.databind.JsonNode;
import com.gyk.utils.CommonUtils;
import com.gyk.utils.JsonHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

@Service
public class JasperReportService {
    private static final Logger log = Logger.getLogger(JasperReportService.class);

    @Inject
    SqlParserService sqlParserService;

    public DynamicReport createDynamicReport(String reportStudio, int screenWidth){
        log.info("createDynamicReport---screenWidth= ${screenWidth}");
        // 1 读取JSON数据结构

        try {
            JsonNode jsonData = JsonHelper.parseJSON(reportStudio);
            // 1.1 取得列
            JsonNode columns = jsonData.get("columns");

            //1.2
            JsonNode parameters = jsonData.get("parameters");
            log.info("createDynamicReport.parameters----------> " + parameters)

            // 1.3 报表标题
            String title = (String) CommonUtils.binaryOperation(jsonData.get("title").textValue(), "报表标题");

            // 1.4 解析sql
            String sql = sqlParserService.parserSelectSql(jsonData.get("result"), "MySQL");

            log.info("createDynamicReport.sql----------> " + sql);

            //2 设置字体与样式
            Font mineFont = new Font();
            mineFont.setFontName("hwst");
            Style titleStyle = new StyleBuilder(false)
                    .setFont(mineFont).build();

            Style headerStyle = new Style("header");
            headerStyle.setTextColor(Color.BLUE);
            headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            headerStyle.setVerticalAlign(VerticalAlign.BOTTOM);
            headerStyle.setBorder(Border.PEN_1_POINT());
            headerStyle.setBackgroundColor(new Color(199,235,205));
            headerStyle.setFont(mineFont);

            Style fieldStyle = new Style("field");
            fieldStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            fieldStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            fieldStyle.setBorder(Border.THIN());
            fieldStyle.setBorderBottom(Border.THIN());
            fieldStyle.setFont(mineFont);

            //3 拼装报表
            FastReportBuilder reportBuilder = new FastReportBuilder();

            //3.3 设置参数
            for(JsonNode param: parameters){
                reportBuilder.addParameter(param.textValue(), String.class.getName());
            }

            //3.1 设置标题、样式、数据源及查询语句
            reportBuilder.setQuery(sql, DJConstants.QUERY_LANGUAGE_SQL)
                    .setTitle(title)
                    .setDefaultStyles(titleStyle, titleStyle, headerStyle, fieldStyle)
                    .setReportName("My Excel Report")
                    .setPageSizeAndOrientation(Page.Page_Letter_Landscape())
                    .setPrintColumnNames(true)
                    .setUseFullPageWidth(true)
                    .setSubtitle("创建日期：" + new Date().format("yyyy-MM-dd"));

            //3.2 设置字段


            for(col in columns){
                //字段对应的列宽，在ReportDef中定义了最大值与最小值
                int width = assignWidth(screenWidth, col.get("width"))
                def label = col.get("title")
                log.info("字段：${label}-的宽度= " + width);
                String dataType = col.getAt("className")
                String className = translateDataType(dataType)
                //reportBuilder.addField(col.get("property"), className)
                String exp = (String)col.getAt("EP")
                if(exp != null && exp != ""){
                    //reportBuilder.addColumn()
                }
                reportBuilder.addColumn(label, col.get("property"), className, width, fieldStyle);
            }

            reportBuilder.setReportLocale(Locale.CHINESE);
            return reportBuilder.build();
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }
}
