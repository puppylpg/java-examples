package boot.entity;

/**
 * @author puppylpg on 2022/05/31
 */
public class Blog {

    int id;
    String title;

    public Blog(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", title: " + title;
    }
}
