package cn.guoyka.simplermybatis.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;


    static {
        Configuration configuration = new Configuration();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/rpt?useUnicode=true&characterEncoding=UTF-8&useSSL=false&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true");
        dataSource.setPassword("root");
        dataSource.setUser("root");
        Environment et = new Environment("100", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(et);
        EntityHelper.getPackageClassByAnnotation("cn.guoyka.simplermybatis.dao", Mapper.class).forEach(configuration::addMapper);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    }

    public static void pr(Object o){
        System.out.println(o);
    }
    private static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession() {
        return getSqlSessionFactory().openSession();
    }

    public static <T> T getMapper(Class<T> tClass) {
        return getSqlSessionFactory().openSession().getMapper(tClass);
    }

    private MybatisUtils() {}
}
