package com.gyk.utils;

public class SqlParser {

    /*** 对数据表的操作类型 ***/
    public static final String AT_FILTER = "filter" ;  //过滤，数据集的行数减少
    public static final String AT_JOIN = "join" ;      //关联，两个异构数据表的关联组合
    public static final String AT_UNION = "union" ;    //合并，两个同构数据表的数据集叠加
    public static final String AT_SLIM = "slim" ;      //精简，选择数据表的部分字段
    public static final String AT_SORT = "sort" ;      //排序，将数据表按指定字段排序
    public static final String AT_GROUP = "group" ;      //排序，将数据表按指定字段排序

    public static final String DT_VARCHAR = "VARCHAR";
    public static final String DT_NUMBER = "NUMBER";
    public static final String DT_INTEGER = "INTEGER";
    public static final String DT_DATE = "DATE";
    public static final String CO_NE = "<>";
    public static final String CO_GE = ">=";
    public static final String CO_LE = "<=";
    public static final String CO_BETWEEN = "BETWEEN";
    public static final String CO_IN = "IN";
    public static final String CO_NOT_IN = "NOT IN";
    public static final String LO_AND = "AND";
    public static final String LO_OR = "OR";
    public static final String FU_COUNT = "CNT";
    public static final String FU_CNTDIST = "CND";
    public static final String FU_SUM = "SUM";
    public static final String FU_AVG = "AVG";
    public static final String FU_MAX = "MAX";
    public static final String FU_MIN = "MIN";
    public static final String CO_LIKE = "LIKE";
    public static final String CO_NOT_LIKE = "NOT LIKE";

    /**
     * 解析各类操作类型的表达形式。总体规范：JSON格式。第一层为JSONObject数组，数组元素为以下之一：
     * JOIN: {act:"join", CO:"like", joinType:"left", leftTable:{name:"product",title:"产品销售流水表",on:"maker_id"},
     * rightTable:{name:"manufactory", title:"生产商信息表", on:"id"}}
     *
     * FILTERS: {act:"filter", logic:"AND", filters:[{N:'id',L:'标识',DT:'number',CO:'>',V:'123'},
     * {T:'B',N:'birthday',L:'出生日期',DT:'date',CO:'>',V:'19830101'}]}
     *
     * UNION: {act:"union", leftTable:{name:"po_2012", columns:["orderNumber as 订单号","amount as 数量","payment"]},
     * rightTable:{name:"po_2013"}, columns:["orderNumber as 订单号","amount as 数量","payment"]}
     *
     * slim对象columns属性说明：T:所属表别名;N:字段名;L:显示名;DT:数据类型;S:字段长度
     * SLIM: {act:"slim", columns:[{N:'id',L:'标识',DT:'number',S:'5,2'},{T:'B',N:'birthday',L:'出生日期',DT:'date',S:''},{T:'B',N:'duty',L:'职务',DT:'varchar',S:'1000'}, {T:'B',N:'birthYear',L:'出生年份',DT:'varchar',S:'',EP:"substr(to_char(birthday,'yyyymmdd'),1,4)"}]}
     *
     * SORT: {act:"sort", by:[{N:'id',L:'标识',DR:'ASC'}, {N:'id',L:'标识',DT:'number',DR:'DESC'}]}
     *
     * GROUP: {act:"group", tId:"890", funcs:["COUNT", "MAX"], by:[{N:'id',L:'标识'}, {N:'brithday',L:'出生日期'}],
     * columns:[{N:'id',L:'标识',DT:'number'},{T:'B',N:'birthday',L:'出生日期',DT:'date'},{T:'B',N:'duty',L:'职务',DT:'varchar'}]}
     */

}
