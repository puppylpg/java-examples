import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * https://mybatis.org/spring/zh/mappers.html，这个mapper并不能被自动扫描并添加进factory……
 *
 * @author puppylpg on 2022/05/31
 */
//@Mapper
public interface BlogMapper {

    @Select("select * from BLOG")
    List<Blog> getAll();

    @Insert("insert into BLOG (id, title) values (#{id}, #{title})")
    int insert(Blog blog);
}
