import config.MybatisConfig;
import entity.Blog;
import mapper.BlogMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * mybatis-spring demo
 *
 * @author puppylpg on 2022/05/30
 */
public class MybatisSpringDemo {

    /**
     * insert es: 1
     * insert mybatis: 1
     * id: 1, title: elasticsearch
     * id: 2, title: mybatis
     */
    public static void main(String... args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(MybatisConfig.class);
        applicationContext.refresh();

        // 从spring容器获取mapper
        BlogMapper mapper = applicationContext.getBean(BlogMapper.class);

        // 通过mapper操作
        Blog elasticsearch = new Blog(1, "elasticsearch");
        Blog mybatis = new Blog(2, "mybatis");

        int result = mapper.insert(elasticsearch);
        System.out.println("insert es: " + result);
        int result2 = mapper.insert(mybatis);
        System.out.println("insert mybatis: " + result2);

        List<Blog> blogs = mapper.getAll();
        blogs.forEach(System.out::println);

        System.out.println("find by xml---");
        List<Blog> blogsByXml = mapper.getAllByXml();
        blogsByXml.forEach(System.out::println);

        applicationContext.close();
    }
}
