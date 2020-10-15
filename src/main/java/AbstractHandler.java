import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class AbstractHandler {
    Bot bot;

    AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
