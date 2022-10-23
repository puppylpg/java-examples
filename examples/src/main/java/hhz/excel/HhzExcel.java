package hhz.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

/**
 * @author puppylpg on 2022/10/23
 */
@Data
@Builder
public class HhzExcel {

    @ExcelProperty("主题id")
    private Integer topicId;
    @ColumnWidth(30)
    @ExcelProperty("主题标题")
    private String topicTitle;
    @ColumnWidth(50)
    @ExcelProperty("标题")
    private String title;
    @ColumnWidth(100)
    @ExcelProperty("内容")
    private String content;
    @ColumnWidth(20)
    @ExcelProperty("发布时间")
    private String publishTime;
    @ColumnWidth(20)
    @ExcelProperty("用户名")
    private String nickname;

    @ExcelProperty("评论数")
    private int comment;
    @ExcelProperty("点赞数")
    private int like;
    @ExcelProperty("收藏数")
    private int favorite;
    @ExcelProperty("分享数")
    private int share;
}
