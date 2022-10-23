package hhz;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhz.excel.CustomMergeStrategy;
import hhz.excel.HhzExcel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 根据hhz/topics下获取到的topic id，处理hhz/posts下的数据，生成excel
 *
 * @author puppylpg on 2022/10/23
 */
public class ProcessResponse {

    public static Pattern p = Pattern.compile("\"topic_info\":\\{\"id\":(\\d+)");

    public static List<Integer> hot = Arrays.asList(499, 193, 660, 698, 519, 263, 414, 261, 663, 354, 670, 586, 516, 528, 661, 507, 366, 683, 743, 345);

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String... args) throws IOException {
        List<Integer> topics = getTopicIds();
        File hhz = new File("hhz/posts");
        for (Integer topic : topics) {
            List<HhzResp.Post> postsInTopic = new ArrayList<>();

            System.out.println("for topic=" + topic);
            List<File> topicFiles = Arrays.stream(hhz.listFiles()).filter(file -> file.getName().startsWith(topic + "_"))
                    .collect(Collectors.toList());
            for (File topicFile : topicFiles) {
                System.out.println(topicFile.getName());
                HhzResp resp = objectMapper.readValue(topicFile, HhzResp.class);

                List<HhzResp.Post> posts = resp.getList();
                if (CollectionUtils.isNotEmpty(posts)) {
                    postsInTopic.addAll(posts);
                }
            }

            if (CollectionUtils.isNotEmpty(postsInTopic)) {
                // 不要image了
                List<HhzExcel> hhzExcels = postsInTopic.stream().flatMap(post -> post.convertToExcel(false).stream()).collect(Collectors.toList());
                String topicTitle = hhzExcels.get(0).getTopicTitle();
                topicTitle = StringUtils.replace(topicTitle, " ", "_");

                File result = new File(String.format("hhz/excel/%d-%s.xlsx", topic, topicTitle));

                EasyExcel.write(result, HhzExcel.class)
                        // 有自己的行合并策略
                        .registerWriteHandler(new CustomMergeStrategy(HhzExcel.class))
                        .sheet("sheet1")
                        .doWrite(hhzExcels);
            }

        }
    }

    private static List<Integer> getTopicIds() throws IOException {
        List<Integer> ids = new ArrayList<>();

        File folder = new File("hhz/topics");
        for (File f : folder.listFiles()) {
            System.out.println(f.getAbsolutePath());
            String context = FileUtils.readFileToString(f, StandardCharsets.UTF_8);

            Matcher m = p.matcher(context);
            while (m.find()) {
                ids.add(Integer.valueOf(m.group(1)));
            }
        }
        Collections.reverse(ids);

        hot.forEach(id -> {
            if (!ids.contains(id)) {
                ids.add(id);
            }
        });
        return ids;
    }
}
