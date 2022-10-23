package hhz;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author puppylpg on 2020/06/30
 */
public class GetTopicIdList {

    public static Pattern p = Pattern.compile("\"topic_info\":\\{\"id\":(\\d+)");

    public static List<Integer> hot = Arrays.asList(499, 193, 660, 698, 519, 263, 414, 261, 663, 354, 670, 586, 516, 528, 661, 507, 366, 683, 743, 345);

    public static void main(String... args) throws IOException {
        List<Integer> ids = new ArrayList<>();

        File folder = new File("hhz/topics");
        for (File f : folder.listFiles()) {
            System.out.println(f.getAbsolutePath());
            String context = FileUtils.readFileToString(f, StandardCharsets.UTF_8);

            Matcher m = p.matcher(context);
            while (m.find()) {
                ids.add(Integer.valueOf(m.group(1)));
            }

            System.out.println(ids);
            System.out.println(ids.size());
        }
        Collections.reverse(ids);
        System.out.println(ids);
        System.out.println(ids.size());

        hot.forEach(id -> {
            if (!ids.contains(id)) {
                ids.add(id);
            }
        });
        System.out.println(ids);
        System.out.println(ids.size());
    }
}
