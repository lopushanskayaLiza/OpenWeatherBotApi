import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather_daily extends AbstractHandler{

    private static final Logger log = Logger.getLogger(Weather_daily.class);
    private String WRONG_INPUT_MESSAGE = "Wrong input. " +
            "You must enter the coordinates of the place where you want to know the daily weather forecast. Like this:\n" +
            "/weather_hourly 33.441792 -94.037689";

    private String DAILY_WEATHER_RESULT = "";

    public Weather_daily(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String text = parsedCommand.getText();

        String result = WeatherModel.parseCoordinates(text);

        if ("".equals(text) || result.equals("Error"))
            return WRONG_INPUT_MESSAGE;

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?"
                    + result + "&units=metric&exclude=current,minutely,hourly,alerts&appid=ee6cf5b158f6fa54261142cd9ef4a729");

            Scanner in = new Scanner((InputStream) url.getContent());

            String res = "";
            while (in.hasNext()) {
                res += in.nextLine();
            }

            WeatherModel model = new WeatherModel();

            JSONObject object = new JSONObject(res);
            model.setLat(object.getDouble("lat"));
            model.setLon(object.getDouble("lon"));

            JSONArray getDailyData = object.getJSONArray("daily");

            JSONObject daily = getDailyData.getJSONObject(3);

            JSONObject temp = daily.getJSONObject("temp");
            model.setDay_temp(temp.getDouble("day"));
            model.setMax_temp(temp.getDouble("max"));
            model.setMin_temp(temp.getDouble("min"));
            model.setEve_temp(temp.getDouble("eve"));
            model.setMorn_temp(temp.getDouble("morn"));
            model.setNight_temp(temp.getDouble("night"));

            JSONArray weather = daily.getJSONArray("weather");
            for (int i = 0; i < weather.length(); i++) {
                JSONObject obj = weather.getJSONObject(i);
                model.setIcon((String) obj.get("icon"));
                model.setWeather((String) obj.get("description"));
            }

            return "lat: " + model.getLat() +
                    "\nlon: " + model.getLon() +
                    "\ndaily temp: " + model.getDay_temp() +
                    "\nmorning temp: " + model.getMorn_temp() +
                    "\nevening temp: " + model.getEve_temp() +
                    "\nnight temp: " + model.getNight_temp() +
                    "\nmax temp: " + model.getMax_temp() +
                    "\nmin temp: " + model.getMin_temp() +
                    "\ndescription: " + model.getWeather() +
                    "\nhttp://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png";

        } catch (MalformedURLException e) {
            return "MalformedURLException";
        } catch (IOException e) {
            return "IOException";
        }
    }
}
