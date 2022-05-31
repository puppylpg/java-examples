import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.List;

/**
 * plain mybatis
 *
 * @author puppylpg on 2022/05/30
 */
public class PlainMybatisDemo {

    /**
     * insert es: 1
     * insert mybatis: 1
     * id: 1, title: elasticsearch
     * id: 2, title: mybatis
     */
    public static void main(String... args) {

        // 配置session factory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 使用factory创建时session
        try (SqlSession session = sqlSessionFactory.openSession()) {

            // 从session获取mapper
            BlogMapper mapper = session.getMapper(BlogMapper.class);

            // 通过mapper操作
            Blog elasticsearch = new Blog(1, "elasticsearch");
            Blog mybatis = new Blog(2, "mybatis");

            int result = mapper.insert(elasticsearch);
            System.out.println("insert es: " + result);
            int result2 = mapper.insert(mybatis);
            System.out.println("insert mybatis: " + result2);

            List<Blog> blogs = mapper.getAll();
            blogs.forEach(System.out::println);
        }
    }

    private static SqlSessionFactory getSqlSessionFactory() {
        DataSource dataSource = getDataSource();

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        // 必须通过这种方式添加mapper，除非通过mybatis-spring自动发现并注入mapper
        configuration.addMapper(BlogMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        // 启动h2时初始化sql脚本
        config.setJdbcUrl("jdbc:h2:mem:pokemon;DB_CLOSE_DELAY=-1;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:scripts/init.sql'");
        config.setUsername("sa");
        config.setPassword("password");
        config.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(config);
    }
}
