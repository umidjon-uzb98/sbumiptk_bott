import org.glassfish.grizzly.http.server.util.RequestUtils;
import org.glassfish.grizzly.streams.Input;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FindWithPassportNumberBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String passNumber = message.getText();

        URL url = null;
        try {
//            url = new URL("\u202AC:\\Users\\Sabr\\Desktop\\pdf_files\\" + passNumber + ".pdf");
            url = new URL("C:\\Users\\Sabr\\Desktop\\pdf_files\\pdf2.pdf");
            InputStream inputStream = url.openStream();


            SendDocument sendDoc = new SendDocument();
            sendDoc.setChatId(message.getChatId());
            InputFile inputFile = new InputFile(String.valueOf(inputStream));
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
        return "";
    }
    private void sendFile(Long chatId, InputStream is, String name){
        SendDocument outMess = new SendDocument();
        outMess.setChatId(chatId);
//        outMess.setNewDocument(name, is);
//        try{
//            sendDocument(outMess);
//        } catch (TelegramApiException e){
//            e.printStackTrace();
//        }
    }
}
