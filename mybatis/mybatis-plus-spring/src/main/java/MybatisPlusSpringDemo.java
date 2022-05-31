import config.MybatisPlusConfig;
import entity.Blog;
import mapper.BlogMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * mybatis-plus demo
 *
 * @author puppylpg on 2022/05/30
 */
public class MybatisPlusSpringDemo {

    /**
     *  _ _   |_  _ _|_. ___ _ |    _
     * | | |\/|_)(_| | |_\  |_)||_|_\
     *      /               |
     *                         3.5.1
     * insert es: 1
     * insert mybatis: 1
     * id: 1, title: elasticsearch
     * id: 2, title: mybatis
     */
    public static void main(String... args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(MybatisPlusConfig.class);
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

        List<Blog> blogs = mapper.selectList(null);
        blogs.forEach(System.out::println);

        applicationContext.close();
    }
}
