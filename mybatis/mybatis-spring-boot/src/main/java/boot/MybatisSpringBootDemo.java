package boot;

import boot.entity.Blog;
import boot.outer.BlogMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 启动spring boot一定要在一个package内：
 * ** WARNING ** : Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package.
 *
 * - https://stackoverflow.com/a/41729933/7676237
 * - https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code
 *
 * @author puppylpg on 2022/05/31
 */
@SpringBootApplication
//@MapperScan(basePackages = {"outer", "boot"})
public class MybatisSpringBootDemo implements CommandLineRunner {

    private final BlogMapper mapper;

    public MybatisSpringBootDemo(BlogMapper mapper) {
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisSpringBootDemo.class, args);
    }

    /**
     * insert es: 1
     * insert mybatis: 1
     * id: 1, title: elasticsearch
     * id: 2, title: mybatis
     * find by xml---
     * id: 1, title: elasticsearch
     * id: 2, title: mybatis
     */
    @Override
    public void run(String... args) {
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
    }
}
