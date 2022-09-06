import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.InputStream;
import java.net.URL;

public class FindWithPassportNumberBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String passNumber = message.getText();

        try {
            SendDocument sendDoc = new SendDocument();
            sendDoc.setChatId(message.getChatId());
            sendDoc.setDocument(inputFile);
            execute(sendDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "sbumiptk_info_bot";
    }

    @Override
    public String getBotToken() {
        return "5685358111:AAGE7B9_RX-SuYkCO85V3NM9uCgyPjcMXhw";
    }

}
