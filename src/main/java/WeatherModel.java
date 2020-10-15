import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

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
    private double temp_min;
    @Setter
    @Getter
    private double temp_max;
    @Setter
    @Getter
    private double temp_day;
    @Setter
    @Getter
    private double temp_night;
    @Setter
    @Getter
    private double temp_morning;
    @Setter
    @Getter
    private double temp_evening;
    @Setter
    @Getter
    private double temp_now;

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
}
