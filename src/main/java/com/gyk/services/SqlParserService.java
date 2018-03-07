package com.gyk.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyk.utils.CommonUtils;
import com.gyk.utils.JsonHelper;
import com.gyk.utils.SqlParser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class SqlParserService {
    private static Logger log = Logger.getLogger(SqlParserService.class);

    //取得表连接信息
    public String getJoinInfo(JsonNode joinInfo){
        StringBuilder sb = new StringBuilder();
        if(joinInfo.hasNonNull("mainTable")){
            JsonNode mainTable = joinInfo.get("mainTable");
            if(mainTable.hasNonNull("joinTables")){
                mainTable.get("joinTables").forEach(joinTable ->{
                    String alias = joinTable.get("alias").textValue();
                    String operator = mainTable.get("CO").textValue();
                    sb.append(" ").append(mainTable.get("joinType").textValue())
                            .append(" ")
                            .append(joinTable.get("name").textValue())
                            .append(" ")
                            .append(alias)
                            .append(" ON ")
                            .append(mainTable.get("alias").textValue())
                            .append(".")
                            .append(mainTable.get("on").textValue())
                            .append(" ")
                            .append(operator)
                            .append(" ");
                    if(operator == SqlParser.CO_LIKE || operator == SqlParser.CO_NOT_LIKE){
                        sb.append(" '%'||").append(alias).append(".").append(joinTable.get("on").textValue()).append("||'%'");
                    }else {
                        sb.append(alias)
                                .append(".")
                                .append(joinTable.get("on").textValue());
                    }
                });
            }
        }
        return sb.toString();
    }

    //根据数据类型获取过滤值 
    public Object getValue(JsonNode node, String dbms){
        String dataType = node.get("dataType").textValue();
        JsonNode valueNode = node.get("value");
        Object value = new Object();
        switch (dataType){
            case "string":
                value = valueNode.textValue();
                break;
            case "number":
                value = valueNode.numberValue();
                break;
            case "date":
                value = getValue(valueNode.textValue(), dbms);
        }
        return value;
    }

    public Object getValue(String data, String dbms){
        StringBuffer sb = new StringBuffer();
        switch (dbms){
            case "Oracle":
                sb.append(" to_date(").append(" '").append(data).append("','mm/dd/yyyy')");
            break;
            default:
                sb.append(" str_to_date(").append(" '").append(data).append("','%m/%d/%Y')");
            break;
        }
        return sb.toString();
    }

    public String parserSelectSql(JsonNode rules, String dbms){
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        for (JsonNode rule : rules) {
            //查询字段
            if (rule.hasNonNull(SqlParser.AT_SLIM)) {
                JsonNode slimNode = rule.get(SqlParser.AT_SLIM);
                if (slimNode.hasNonNull("columns")) {
                    slimNode.get("columns").forEach(n -> {
                        if (n.hasNonNull("expression")) {//具有表达式
                            String expression = n.hasNonNull("expression") ? n.get("expression").textValue() : "";
                            if (n.hasNonNull("tableAlias")) {//列有别名
                                String aliasOfTable = n.get("T").textValue();
                                sqlBuilder.append(expression).append(" AS ").append(n.get("label").textValue()).append("_").append(aliasOfTable).append(",");
                            } else {
                                sqlBuilder.append(expression).append(" AS ").append(n.get("label").textValue()).append(",");
                            }
                        } else {
                            Object columnName = CommonUtils.binaryOperation(n.get("name").textValue(), n.get("label").textValue());
                            if (n.hasNonNull("tableAlias")) {
                                String aliasOfTable = n.get("tableAlias").textValue();
                                sqlBuilder.append(aliasOfTable).append(".").append(columnName).append(" AS ").append(columnName).append("_").append(aliasOfTable).append(",");
                            } else {
                                sqlBuilder.append(columnName).append(" AS ").append(columnName).append(",");
                            }
                        }
                    });
                    sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                    sqlBuilder.append(" FROM ");
                    slimNode.get("mainTable").forEach(n->{
                        sqlBuilder.append(n.get("name").textValue()).append(" ").append(n.get("alias").textValue()).append(",");
                    });
                    sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                }
            }

            //表的连接
            if(rule.hasNonNull(SqlParser.AT_JOIN)){
                sqlBuilder.append(getJoinInfo(rule.get(SqlParser.AT_JOIN)));
            }

            //分组条件
            if(rule.hasNonNull(SqlParser.AT_GROUP)){

            }

            //过滤条件
            if(rule.hasNonNull(SqlParser.AT_FILTER)){
                sqlBuilder.append(" WHERE 1=1 ");
                JsonNode filterNode = rule.get(SqlParser.AT_FILTER);
                String logic = filterNode.get("logic").textValue();
                if(filterNode.hasNonNull("filters")){
                    filterNode.get("filters").forEach(node -> {
                        if(node.hasNonNull("expression")){
                            sqlBuilder.append(logic);
                            sqlBuilder.append(node.get("expression").textValue())
                                    .append(" ")
                                    .append(node.get("CO").textValue())
                                    .append(" ")
                                    .append(getValue(node, dbms));
                        }else {
                            sqlBuilder.append(logic).append(" ");
                            sqlBuilder.append(node.get("tableAlias").textValue())
                                    .append(".")
                                    .append(node.get("name").textValue())
                                    .append(" ")
                                    .append(node.get("CO").textValue())
                                    .append(" ")
                                    .append(getValue(node, dbms));
                        }
                    });
                }
            }

            //排序条件
            if(rule.hasNonNull(SqlParser.AT_SORT)){
                sqlBuilder.append(" ORDER BY ");
                rule.get(SqlParser.AT_SORT).forEach(node -> {
                    sqlBuilder.append(node.get("tableAlias").textValue()).append(".").append(node.get("name").textValue()).append(" ").append(node.get("type").textValue()).append(",");
                });
                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
            }

        }
        return sqlBuilder.toString();
    }

    /**
     * 将分析构建的JSON数据解析为SQL查询语句
     */
    public String parserSelectSql(String rulesJson, String dbms){
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        try {
            JsonNode rules = JsonHelper.parseJSON(rulesJson);
            System.out.println("result is -----------> " + sqlBuilder.toString());
            return parserSelectSql(rules, dbms);
        }catch (IOException ex){
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * //一个方法用于处理日期转换
     * @param filter 字段信息
     * @param colName 列名
     * @param dbms 数据库类型
     * @return
     */
    String processFilter(HashMap<String, String> filter, String colName, String dbms){
        String value = filter.get("V");
        StringBuffer sb = new StringBuffer();
        switch (dbms){
            case "Oracle":
                sb.append(colName);
                if(filter.get("CO") != null){
                    sb.append(" ").append(filter.get("CO")).append(" to_date(").append(" '").append(value).append("','mm/dd/yyyy')");
                }
            break;
            default:
                sb.append(colName);
                if(filter.get("CO") != null){
                    sb.append(" ").append(filter.get("CO")).append(" str_to_date(").append(" '").append(value).append("','%m/%d/%Y')");
                }
            break;
        }
        return sb.toString();
    }
}
