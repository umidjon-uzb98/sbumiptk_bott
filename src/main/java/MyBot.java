import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//SBUMIPTK //sbumiptk_info_bot

public class MyBot extends TelegramLongPollingBot {

    List<TelegramUser> users = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        System.out.println(message.getText());
//******************************

/*//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            Document document = message.getDocument();
//            try {
//                saveFileToFolder(document.getFileId(),document.getFileName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//    }
// **********************************************/

/*//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            List<PhotoSize> photo = message.getPhoto();
//            for (PhotoSize photoSize : photo) {
//                System.out.println(photo);
//                try {
//                    saveFileToFolder(photoSize.getFileId(), "rasm1.jpg");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//    }
//**********************************************/

/*//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            Voice voice = message.getVoice();
//            try {
//                saveFileToFolder(voice.getFileId(), "sound.mp3");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else if (update.hasCallbackQuery()){}*/

/*        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()){
                System.out.println("text");
            } else if (message.hasVoice()) {
                System.out.println("voice");
            } else if (message.hasPhoto()) {
                System.out.println("photo");
            } else if (message.hasDocument()) {
                System.out.println("document");
            }
        } */

        /*if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            TelegramUser user = saveUser(chatId);
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/list")) {
                    System.out.println(users);
                }
                if (text.equals("/start")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Assalomu aleykum! \n" +
                            "Botga hush kelibsiz!\n" +
                            "Tilni tanlang!\n" +
                            "Пожалуйста виберите язык!\n" +
                            "1- uz\n" +
                            "2- ru\n");
                    sendMessage.setChatId(chatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    user.setStep(BotConstant.SELECT_LANG);
                } else if (user.getStep().equals(BotConstant.SELECT_LANG)) {
                    if (text.equals("1")) {
                        sendText(chatId, "Xabaringizni qoldiring");
                    } else if (text.equals("2")) {
                        sendText(chatId, "Оставте своё сообщение");
                    }
                    user.setStep(BotConstant.WRITE_MSG);
                } else if (user.getStep().equals(BotConstant.WRITE_MSG)) {
                    user.setMsg(text);
                }

            }
        }*/

        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            TelegramUser user = saveUser(chatId);
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/list")) {
                    System.out.println(users);
                }
                if (text.equals("/start")) {
                    if (user.getFullName() != null) {
                        try {
                            setLang(chatId,user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Assalomu aleykum! \n" +
                                "Botga hush kelibsiz!\n" +
                                "Iltimos ism familiyangizni kiriting!\n"
                        );
                        sendMessage.setChatId(chatId);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        user.setStep(BotConstant.ENTER_NAME);
                    }

                } else if (user.getStep().equals(BotConstant.ENTER_NAME)) {
                    try {
                        user.setFullName(text);
                        setLang(chatId,user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.setStep(BotConstant.SELECT_LANG);
                } else if (user.getStep().equals(BotConstant.WRITE_MSG)) {
                    user.setMsg(text);
                    sendText(chatId, user.getSelectedLang().equals(BotQuery.UZ_SELECT)
                            ? "Tez orada siz bilan bog'lanishadi"
                            : "Скоро свами свяжетесь");
                }

            }
        } else if (update.hasCallbackQuery()) {
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            String data = update.getCallbackQuery().getData();
            TelegramUser user = saveUser(chatId);
            System.out.println(user.getStep());
            if (user.getStep().equals(BotConstant.SELECT_LANG)) {
                System.out.println("sel lang");
                if (data.equals(BotQuery.UZ_SELECT)) {
                    user.setSelectedLang(BotQuery.UZ_SELECT);
                    sendText(chatId, "Xabaringizni qoldiring");
                } else if (data.equals(BotQuery.RU_SELECT)) {
                    user.setSelectedLang(BotQuery.RU_SELECT);
                    sendText(chatId, "Оставте своё сообщение");
                }
                user.setStep(BotConstant.WRITE_MSG);
            }
        }
    }

    private TelegramUser saveUser(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }
        TelegramUser user = new TelegramUser();
        user.setChatId(chatId);
        users.add(user);
        return user;
    }

    private void saveFileToFolder(String fileId, String fileName) throws Exception {
        GetFile getFile = new GetFile(fileId);

        File tgFile = execute(getFile);
        String fileUrl = tgFile.getFileUrl(getBotToken());
//                System.out.println(fileUrl);

        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
//                FileUtils.copyInputStreamToFile(inputStream,new java.io.File("D:/"+document.getFileName()));
        FileUtils.copyInputStreamToFile(inputStream, new java.io.File(fileName));
    }

    private void setLang(String chatId, TelegramUser user) throws Exception {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(user.getFullName() +", iltimos tilni tanlang!\n" +
                "Пожалуйста виберите язык!\n");
        sendMessage.setChatId(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> td = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonUz = new InlineKeyboardButton();
        inlineKeyboardButtonUz.setText("\uD83C\uDDFA\uD83C\uDDFF Uz");
        inlineKeyboardButtonUz.setCallbackData(BotQuery.UZ_SELECT);

        InlineKeyboardButton inlineKeyboardButtonRu = new InlineKeyboardButton();
        inlineKeyboardButtonRu.setText("\uD83C\uDDF7\uD83C\uDDFA Ru");
        inlineKeyboardButtonRu.setCallbackData(BotQuery.RU_SELECT);

        td.add(inlineKeyboardButtonUz);
        td.add(inlineKeyboardButtonRu);

        List<List<InlineKeyboardButton>> tr = new ArrayList<>();
        tr.add(td);

        inlineKeyboardMarkup.setKeyboard(tr);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        execute(sendMessage);
        user.setStep(BotConstant.SELECT_LANG);
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

    @Override
    public String getBotUsername() {
        return "sbumiptk_info_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}
