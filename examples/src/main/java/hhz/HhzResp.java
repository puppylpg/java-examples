package hhz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import hhz.excel.HhzExcel;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author puppylpg on 2022/10/23
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HhzResp {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @JsonProperty("is_over")
    public int isOver;

//    @JsonProperty("list")
    public List<Post> list;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Post {
        public int type;

        public Photo photo;

        @JsonProperty("comment_list")
        List<Comment> comments;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Photo {

            @JsonProperty("user_info")
            UserInfo userInfo;

            @JsonProperty("photo_info")
            PhotoInfo photoInfo;
            Counter counter;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class UserInfo {
                String uid;
                String nick;
                String gender;
                String area;
                String area_code;
                String area_name;
                int type;
                String sub_type;
                String status;
                String profile;
                String avatar;
                String bid_avatar;
                String avatar_version;
                String addtime;
                String reply_time;
                int is_follow;
                int is_follow_new;
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class PhotoInfo {
                String id;
                String pic_url;
                String new_pic_url;
                String thumb_pic_url;
                String ori_pic_url;
                String o_500_url;
                String remark;
                String title;
                @JsonProperty("relate_goods_list")
                RelateGoodsList relateGoodsList;
                @JsonProperty("mention_list")
                List<MentionList> mentionLists;
                @JsonProperty("image_list")
                List<ImageList> imageLists;

                int is_liked;
                int is_favorited;
                String addtime;
                String user_update_time;
                List<RemarkTag> remark_tags;
                // 不知道是哪个，没找着
                String admin_tag;
                Topic topic;

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class RelateGoodsList {
                    List<Goods> list;

                    @Data
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Goods {
                        String id;
                        String basic_id;
                        String basic_title;
                        String basic_state;
                        int type;
                        String title;
                        String ori_min_price;
                        String ori_max_price;
                        String actual_min_price;
                        String actual_max_price;
                        String shop_id;
                        String category_id;
                        String cover_img;
                        String brand_id;
                        String channel_jump_params;
                        int fav_count;
                        int buy_count;
                        @JsonProperty("brand_info")
                        BrandInfo brandInfo;

                        @Data
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class BrandInfo {
                            String id;
                            String name;
                            String nick;
                            String avatar;
                            String logo;
                            String description;
                            String brand_type;
                        }
                    }
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                // 艾特
                public static class MentionList {
                    String uid;
                    int type;
                    String nick;
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class ImageList {
                    String pic_id;
                    String pic_org_id;
                    String pic_url;
                    String pic_jpg_url;
                    String ori_pic_url;
                    String thumb_pic_url;
                    String water_pic_url;
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                // #日式
                public static class RemarkTag {
                    String title;
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Topic {
                    int id;
                    String title;
                    String sq_thumb;
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class VideoInfo {
                    String video_id;
                    String cover_pic_id;
                    String pic_url;
                    String ori_pic_url;
                    String gif_url;
                    String water_play_url;
                    String duration;
                }
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Counter {
                int comment;
                int like;
                int favorite;
                int share;
                int read;
                int wiki;
                int comments;
            }
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Comment {
            CommentUserInfo user_info;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class CommentUserInfo {
                int uid;
                String nick;
                String avatar;
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class CommentInfo {
                int id;
                int uid;
                int type;
                int parentid;
                int addtime;
                String content;
            }
        }

        // 如果有多个图片，返回多个对象
        public List<HhzExcel> convertToExcel() {
            Instant instant = Instant.ofEpochSecond(Long.parseLong(photo.photoInfo.addtime));
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());

            HhzExcel hhzExcel = HhzExcel.builder()
                    .topicId(photo.photoInfo.topic.id)
                    .topicTitle(photo.photoInfo.topic.title)
                    .id(photo.photoInfo.id)
                    .title(photo.photoInfo.title)
                    .content(photo.photoInfo.remark)
                    .publishTime(FORMATTER.format(zonedDateTime))
                    .nickname(photo.userInfo.nick)
                    .comment(photo.counter.comment)
                    .like(photo.counter.like)
                    .favorite(photo.counter.favorite)
                    .share(photo.counter.share)
                    .build();

            List<Photo.PhotoInfo.ImageList> images = photo.photoInfo.imageLists;
            if (CollectionUtils.isNotEmpty(images)) {
                return images.stream().map(
                        image -> hhzExcel.toBuilder().url(image.ori_pic_url).build()
                ).collect(Collectors.toList());
            } else {
                return Collections.singletonList(hhzExcel);
            }
        }

    }

}
