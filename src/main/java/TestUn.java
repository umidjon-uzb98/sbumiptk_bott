import org.glassfish.grizzly.http.server.util.RequestUtils;
import org.glassfish.grizzly.streams.Input;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class TestUn extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String passNumber = message.getText();
        GetFile getFile = new GetFile(message.getDocument().getFileId());

        try {
            SendDocument sendDoc = new SendDocument();
            sendDoc.setChatId(message.getChatId());

            File tgFile = execute(getFile);
            String fileUrl = tgFile.getFileUrl(getBotToken());
//            System.out.println(fileUrl);

            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();
            InputFile inputFile = new InputFile();
            inputFile.setMedia(inputStream,"passNumber.pdf");


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

//    private void sendFile(Long chatId, InputStream is, String name) {
//        SendDocument outMess = new SendDocument();
//        outMess.setChatId(chatId);
//        outMess.setNewDocument(name, is);
//        try{
//            sendDocument(outMess);
//        } catch (TelegramApiException e){
//            e.printStackTrace();
//        }
//    }
}
