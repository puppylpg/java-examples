package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承了mp的base mapper，就不用写一大堆基础的方法了。
 *
 * @author puppylpg on 2022/05/31
 */
//@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

//    @Select("select * from BLOG")
//    List<Blog> getAll();
//
//    @Insert("insert into BLOG (id, title) values (#{id}, #{title})")
//    int insert(Blog blog);
//
//    @Select("SELECT * FROM BLOG WHERE id = #{blogId}")
//    Blog getUser(@Param("blogId") String blogId);
}
