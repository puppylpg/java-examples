package mapper;

import entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * https://mybatis.org/spring/zh/mappers.html，这个mapper可以被{@link org.mybatis.spring.annotation.MapperScan}自动扫描并添加进factory
 *
 * @author puppylpg on 2022/05/31
 */
//@Mapper
public interface BlogMapper {

    @Select("select * from BLOG")
    List<Blog> getAll();

    @Insert("insert into BLOG (id, title) values (#{id}, #{title})")
    int insert(Blog blog);

    @Select("SELECT * FROM BLOG WHERE id = #{blogId}")
    Blog getUser(@Param("blogId") String blogId);

    /**
     * 使用xml定义mapper映射
     */
    List<Blog> getAllByXml();
}
