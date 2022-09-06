import org.glassfish.grizzly.http.server.util.RequestUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.xml.bind.DatatypeConverter;
import java.io.File;

public class FindWithPassportNumberBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String passNumber = message.getText();

        File file = new File("\u202AC:\\Users\\Sabr\\Desktop\\pdf_files\\" + passNumber + ".pdf");



        SendDocument sendDocument = new SendDocument();
//        sendDocument.setChatId(message.getChatId());
//        sendDocument.setDocument(file);
//
//        sendDocument.



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
