# simpler-mybatis

## 这个项目解决了什么问题？
1. crud的默认实现
2. 只需要与实体属性打交道，不需要过多关注数据表，更加专注于业务逻辑
3. 使用脚本生成实体类的Mapper.xml文件
4. 实现后台分页


![Mapper.xml](https://github.com/wellRich/simpler-mybatis/raw/master/doc/mapper_xml.png)

## 搭建这个项目的起因：
1. 最近的项目使用Mybatis
2. 增删改查代码逻辑雷同，感觉是在做体力活，没激情
3. 不愿意写太多xml配置文件
4. 实体类对应的数据表中的字段与实体的属性名不一致，也不愿记忆


## 实现思路/技术

1. 充分利用`org.apache.ibatis.jdbc.SQL`这个类，使用它来将我们封装好的对象解析为原生sql或者是带有表达式的sql
2. 在实体类上添加注解`cn.guoyka.simplermybatis.Table`、`cn.guoyka.simplermybatis.Column`，使用EntityDao封装、储存实体类的属性、get方法、字段
3. 将查询语句使用QueryReq与QueryFilter封装，使用默认实现的pageSeek与countBy进行分页查询


## 这种设计带来的问题？
1. 程序中的sql减少了，了解业务逻辑会有一些不便

