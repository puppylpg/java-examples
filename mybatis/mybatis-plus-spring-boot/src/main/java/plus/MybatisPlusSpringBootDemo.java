package plus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import plus.mapper.UseruserMapper;

import java.util.List;

/**
 *
 * @author puppylpg on 2022/05/31
 */
@SpringBootApplication
//@MapperScan("plus.mapper")
public class MybatisPlusSpringBootDemo implements CommandLineRunner {

    private final UseruserMapper mapper;

    public MybatisPlusSpringBootDemo(UseruserMapper mapper) {
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusSpringBootDemo.class, args);
    }

    /**
     * insert es: 1
     * insert mybatis: 1
     * Useruser(id=1, name=Jone, age=18, email=test1@baomidou.com)
     * Useruser(id=2, name=Jack, age=20, email=test2@baomidou.com)
     * Useruser(id=3, name=Tom, age=28, email=test3@baomidou.com)
     * Useruser(id=4, name=Sandy, age=21, email=test4@baomidou.com)
     * Useruser(id=5, name=Billie, age=24, email=test5@baomidou.com)
     * Useruser(id=100, name=elasticsearch, age=null, email=null)
     * Useruser(id=200, name=mybatis, age=null, email=null)
     */
    @Override
    public void run(String... args) {
        // 通过mapper操作
        Useruser elasticsearch = new Useruser(100, "elasticsearch");
        Useruser mybatis = new Useruser(200, "mybatis");

        int result = mapper.insert(elasticsearch);
        System.out.println("insert es: " + result);
        int result2 = mapper.insert(mybatis);
        System.out.println("insert mybatis: " + result2);

        List<Useruser> userusers = mapper.selectList(null);
        userusers.forEach(System.out::println);
    }
}
