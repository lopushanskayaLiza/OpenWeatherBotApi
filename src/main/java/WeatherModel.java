import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.Double.parseDouble;

public class WeatherModel {

    @Setter
    @Getter
    private String city; // name
    @Setter
    @Getter
    private String weather; // main
    @Setter
    @Getter
    private String icon; // sticker

    @Setter
    @Getter
    private double lat;
    @Setter
    @Getter
    private double lon;

    @Setter
    @Getter
    private double temp;

    @Setter
    @Getter
    private double humidity;

    //daily

    @Setter
    @Getter
    private double day_temp;

    @Setter
    @Getter
    private double min_temp;

    @Setter
    @Getter
    private double max_temp;

    @Setter
    @Getter
    private double night_temp;

    @Setter
    @Getter
    private double eve_temp;

    @Setter
    @Getter
    private double morn_temp;

    public static String parseCoordinates(String text) {
        int index = text.indexOf(" ");

        try {
            double lat = parseDouble(text.substring(0, index));
            double lon = parseDouble(text.substring(index + 1));
            return ("lat=" + lat + "&lon=" + lon);
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String unixToDate(long unixTime) {
        Date date = new Date(unixTime * 1000L);

        SimpleDateFormat dateFormat = new SimpleDateFormat("e;EEE, d MMM yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return dateFormat.format(date);
    }
}
