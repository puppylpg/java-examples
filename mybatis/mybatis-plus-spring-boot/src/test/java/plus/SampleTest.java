package plus;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import plus.mapper.UseruserMapper;
import plus.mapper.UseruserService;
import plus.mapper.UseruserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puppylpg on 2022/05/31
 */
@MybatisPlusTest
@RunWith(SpringRunner.class)
//@Import(UseruserServiceImpl.class)
@Sql("classpath:scripts/schema.sql")
public class SampleTest {

    @Autowired
    private UseruserMapper useruserMapper;

    @Autowired
    private UseruserService useruserService;

    @Test
    public void oneByOne1k() {
        for(int i = 0; i < 1000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            useruserMapper.insert(elasticsearch);
        }
    }

    @Test
    public void oneByOne1w() {
        for(int i = 0; i < 10000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            useruserMapper.insert(elasticsearch);
        }
    }

    @Test
    public void oneByOne10w() {
        for(int i = 0; i < 100000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            useruserMapper.insert(elasticsearch);
        }
    }

    @Test
    public void batch1k() {
        List<Useruser> list = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            list.add(elasticsearch);
        }
        useruserService.saveBatch(list);
    }

    @Test
    public void batch1w() {
        List<Useruser> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            list.add(elasticsearch);
        }
        useruserService.saveBatch(list);
    }

    @Test
    public void batch10w() {
        List<Useruser> list = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            Useruser elasticsearch = new Useruser( "elasticsearch");
            list.add(elasticsearch);
        }
        useruserService.saveBatch(list);
    }

}
