import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class FindWithPassportNumberBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String passNumber = message.getText();
        String chatId = message.getChatId().toString();

        if (passNumber.equals("/start")) {
            sendText(chatId, "Passport raqamini kiriting!");
        } else {
            try {
                sendFile(passNumber, chatId);
            } catch (FileNotFoundException e) {
                sendText(chatId, "Mavjud bo'lmagan passport raqami kiritildi!");
                e.printStackTrace();
            }
        }
    }

    private void sendText(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(String pdfName, String chatId) throws FileNotFoundException {
        SendDocument sendDoc = new SendDocument();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new FileInputStream("C:\\Users\\Sabr\\Desktop\\student_info_files\\" + pdfName + ".pdf"), pdfName + ".pdf");
        sendDoc.setChatId(chatId);
        sendDoc.setDocument(inputFile);
        try {
            execute(sendDoc);
        } catch (TelegramApiException e) {
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
}
