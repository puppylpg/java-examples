package boot.mapper;

import boot.entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * springboot里，{@link Mapper}终于有用了……
 * https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
 *
 * 如果用了{@link org.mybatis.spring.annotation.MapperScan}，{@link  Mapper}又没用了。
 *
 * @author puppylpg on 2022/05/31
 */
@Mapper
public interface AnotherBlogMapper {

    @Select("select * from BLOG")
    List<Blog> getAll();

    @Insert("insert into BLOG (id, title) values (#{id}, #{title})")
    int insert(Blog blog);

    @Select("SELECT * FROM BLOG WHERE id = #{blogId}")
    Blog getUser(@Param("blogId") String blogId);
}
