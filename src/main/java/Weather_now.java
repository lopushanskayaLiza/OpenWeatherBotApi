import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class Weather_now extends AbstractHandler{
    private static final Logger log = Logger.getLogger(Weather_now.class);
    private String WRONG_INPUT_MESSAGE = "Wrong input. " +
            "You must enter the coordinates of the place where you want to know the weather forecast. Like this:\n" +
            "/weather_now 33.441792 -94.037689";


    public Weather_now (Bot bot) {super(bot);}

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String text = parsedCommand.getText();

        String result = WeatherModel.parseCoordinates(text);

        if ("".equals(text) || result.equals("Error"))
            return WRONG_INPUT_MESSAGE;

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?"
            + result + "&units=metric&exclude=hourly,minutely,daily,alerts&appid=ee6cf5b158f6fa54261142cd9ef4a729");

            Scanner in = new Scanner((InputStream) url.getContent());

            String res = "";
            while (in.hasNext()) {
                res += in.nextLine();
            }

            WeatherModel model = new WeatherModel();

            JSONObject object = new JSONObject(res);
            model.setLat(object.getDouble("lat"));
            model.setLon(object.getDouble("lon"));

            JSONObject current = object.getJSONObject("current");
            model.setTemp(current.getDouble("temp"));
            model.setHumidity(current.getDouble("humidity"));

            JSONArray getWeatherAndIcon = current.getJSONArray("weather");
            for (int i = 0; i < getWeatherAndIcon.length(); i++) {
                JSONObject obj = getWeatherAndIcon.getJSONObject(i);
                model.setIcon((String) obj.get("icon"));
                model.setWeather((String) obj.get("description"));
            }

            return "lat: " + model.getLat() +
                    "\nlon: " + model.getLon() +
                    "\ntemp: " + model.getTemp() +
                    "\nhumidity: " + model.getHumidity() +
                    "\ndescription: " + model.getWeather() +
                    "\nhttp://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png";
        } catch (MalformedURLException e) {
            return "MalformedURLException";
        } catch (IOException e) {
            return "IOException";
        }

    }

}
