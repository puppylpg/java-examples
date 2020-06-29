package example.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author puppylpg on 2020/06/30
 */
public class GenerateDate {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(String... args) {

        String start = "2019/12/02";
        String end = "2020/03/02";

        LocalDate startDate = LocalDate.of(2019, 12, 2);
        LocalDate endDate = LocalDate.of(2020, 3, 2);

        while (startDate.isBefore(endDate)) {
            System.out.printf("%s%n", startDate.format(FORMATTER));
            startDate = startDate.plusDays(1);
        }
    }
}
