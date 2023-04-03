package workit.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class PushAlarmService {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    // 메시징만 권한 설정
    @Value("${fcm.key.scope}")
    private String FIREBASE_SCOPE;

    @Value("${fcm.key.url}")
    private String API_URL;

    private FirebaseMessaging instance;

    @PostConstruct
    public void firebaseSetting() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                .createScoped((Arrays.asList(FIREBASE_SCOPE)));
        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);
    }

    @Scheduled(cron = "0 0 09 * * ?")
    public void pushMorningDietAlarm() throws FirebaseMessagingException {
        log.info("아침 식사 알림");
        pushAlarm("워킷 메시지", "오늘도 화이팅!");
    }

    private void pushAlarm(String title, String body) throws FirebaseMessagingException {
        Message message = getMessage(title, body);
        sendMessage(message);
    }

    private Message getMessage(String title, String body) {
        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();
        Message.Builder builder = Message.builder();
        Message message = builder
                .setTopic(API_URL)
                .setNotification(notification)
                .build();
        return message;
    }

    public String sendMessage(Message message) throws FirebaseMessagingException {
        return this.instance.send(message);
    }
}
