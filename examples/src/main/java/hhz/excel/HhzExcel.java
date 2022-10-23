package hhz.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

/**
 * @author puppylpg on 2022/10/23
 */
@Data
@Builder(toBuilder = true)
public class HhzExcel {

    @CustomMerge(needMerge = true)
    @ExcelProperty("主题id")
    private Integer topicId;

    @CustomMerge(needMerge = true)
    @ColumnWidth(30)
    @ExcelProperty("主题标题")
    private String topicTitle;

    @CustomMerge(needMerge = true, isPk = true)
    @ExcelProperty("id")
    private String id;

    @CustomMerge(needMerge = true)
    @ColumnWidth(50)
    @ExcelProperty("标题")
    private String title;

    @CustomMerge(needMerge = true)
    @ColumnWidth(100)
    @ExcelProperty("内容")
    private String content;

    @CustomMerge(needMerge = true)
    @ColumnWidth(20)
    @ExcelProperty("发布时间")
    private String publishTime;

    @CustomMerge(needMerge = true)
    @ExcelProperty("用户id")
    private String userId;

    @CustomMerge(needMerge = true)
    @ColumnWidth(20)
    @ExcelProperty("用户名")
    private String nickname;

    @CustomMerge(needMerge = true)
    @ExcelProperty("评论数")
    private int comment;

    @CustomMerge(needMerge = true)
    @ExcelProperty("点赞数")
    private int like;

    @CustomMerge(needMerge = true)
    @ExcelProperty("收藏数")
    private int favorite;

    @CustomMerge(needMerge = true)
    @ExcelProperty("分享数")
    private int share;

    @ExcelProperty("图片url")
    @ColumnWidth(100)
    private String url;
}
