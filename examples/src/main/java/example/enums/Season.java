package example.enums;

import lombok.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liuhaibo on 2018/01/11
 */
public enum Season {

    SPRING(-1, "spring"),

    SUMMER(0, "summer"),

    AUTUMN(1, "autumn"),

    WINTER(2, "winter"),

    UNKNOWN(3, "unknown");

    private int index;
    private String text;

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    Season(int index, String text) {
        this.index = index;
        this.text = text;
    }

    private static final Map<String, Season> REVERSE_LOOKUP = Arrays.stream(Season.values()).collect(Collectors.toMap(e -> e.getText().toLowerCase(), Function.identity()));

    private static final Map<Integer, Season> indexToSeasons = new HashMap<>();

    static {
        for (Season season : Season.values()) {
            indexToSeasons.put(season.getIndex(), season);
        }
    }

    /**
     * get season by index
     *
     * @param index index
     * @return a Season
     */
    public static Season parseByIndex(int index) {
        return indexToSeasons.getOrDefault(index, UNKNOWN);
    }

    /**
     * get the season by a string, string should be UPPER case
     * <p>
     * (because enum value is UPPER case)
     *
     * @param text
     * @return
     */
    public static Season parseByText(String text) {
        return Season.valueOf(text);
    }

    public static Season parseByText2(@NonNull String value) {
        return REVERSE_LOOKUP.getOrDefault(value.toLowerCase(), UNKNOWN);
    }

    public static Season parseByText3(@NonNull String value) {
        try {
            return Season.valueOf(value);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    public static void main(String[] args) {
        System.out.println("Print all the seasons:");
        indexToSeasons.forEach((x, y) -> System.out.println(x + y.getText()));

        System.out.println("Print season 2:");
        System.out.println(parseByIndex(2));
        System.out.println(parseByText("WINTER"));
        System.out.println(parseByText2("WINTER"));
        System.out.println(parseByText3("WINTER"));
        try {
            // No enum constant example.enums.Season.nono
            System.out.println(parseByText("nono"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // UNKNOWN
        System.out.println(parseByText2("nono"));
        // UNKNOWN
        System.out.println(parseByText3("nono"));
    }
}