package plus;

import lombok.Data;

/**
 * user是h2的关键词……
 *
 * - https://stackoverflow.com/a/46737637/7676237
 * - https://h2database.com/html/advanced.html#compatibility
 *
 * @author puppylpg on 2022/05/31
 */
@Data
public class Useruser {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    Useruser(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
