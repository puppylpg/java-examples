package plus;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import plus.mapper.UseruserMapper;

import java.util.List;

/**
 * @author puppylpg on 2022/05/31
 */
@MybatisPlusTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Autowired
    private UseruserMapper useruserMapper;

    @Test
    public void testSelect() {
        List<Useruser> userList = useruserMapper.selectList(null);
        Assert.assertEquals(7, userList.size());
    }

}
