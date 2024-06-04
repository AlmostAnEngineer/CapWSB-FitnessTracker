package com.capgemini.wsb.fitnesstracker.notification.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailProvider;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.internal.EmailService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@Slf4j
@EnableScheduling
public class NotificationService {
    private final EmailSender emailSender;
    private final EmailProvider emailProvider;
    private final TrainingProvider trainingProvider;
    private final UserProvider userProvider;
    private final String reportString="WEEKLY REPORT";

    @Scheduled(cron = "15 36 14 ? * 2") //report scheduled for every sunday at 18:00:00
    public void generateReportAndSendMail() {
        System.out.println("Cron scheduling report generation");
        List<User> allUsers = userProvider.findAllUsers();
        for (User user : allUsers) {
            final EmailDto emailDto = emailProvider.sendMail(user.getEmail(),
                    reportString,
                    trainingProvider.getTrainingsByUserId(user.getId()));
            emailSender.send(emailDto);
            System.out.println("sending email");
        }
    }
}
