package boot;

import boot.entity.Blog;
import boot.mapper.AnotherBlogMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import boot.outer.BlogMapper;

import java.util.List;

/**
 * @author puppylpg on 2022/06/07
 */
@MybatisTest
@RunWith(SpringRunner.class)
public class AnotherBlogMapperTest {

    /**
     * 用{@link org.apache.ibatis.annotations.Mapper}的mapper
     * IDEA倒是可以找到。
     */
    @Autowired
    private AnotherBlogMapper anotherBlogMapper;

    /**
     * 用{@link org.mybatis.spring.annotation.MapperScan}的mapper
     * IDEA好像不太理解，找不到candidate。
     */
    @Autowired
    private BlogMapper blogMapper;

    @Test
    public void testAnotherBlogMapper() {
        List<Blog> blogList = anotherBlogMapper.getAll();
        Assert.assertEquals(2, blogList.size());
    }

    @Test
    public void testBlogMapper() {
        List<Blog> blogList = blogMapper.getAllByXml();
        Assert.assertEquals(2, blogList.size());
    }
}
