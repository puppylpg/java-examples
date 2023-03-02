package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;

/**
 * 自动扫描注册mapper
 * 关于@MapperScan的处理逻辑，参考{@link org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)}
 *
 * @author puppylpg on 2022/05/31
 */
@Configuration
@MapperScan(basePackages = MybatisConfig.MAPPER_PACKAGE)
public class MybatisConfig {

    public static final String MAPPER_PACKAGE = "mapper";

    /**
     * Java不编译xml，所以target里没有这些文件。需要maven里设置resources，include xml文件才行。
     * 而且必须使用maven compile才行。所以这是一个纯maven的trick，和java无关
     */
    public static final String MAPPER_XML_PATH = "classpath:mapper/xml/*.xml";

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        // 显式设置xml mapper的位置，如果和mapper class位置结构一致，也可以不显式设置
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
        return factoryBean.getObject();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        // 启动h2时初始化sql脚本
        config.setJdbcUrl("jdbc:h2:mem:pokemon;DB_CLOSE_DELAY=-1;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:scripts/init.sql'");
        config.setUsername("sa");
        config.setPassword("password");
        config.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(config);
    }
}
