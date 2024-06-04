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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "15 48 14 ? * 2") //report scheduled for every sunday at 18:00:00
    public void generateReportAndSendMail() {
        System.out.println("Cron scheduling report generation");
        List<User> allUsers = userProvider.findAllUsers();
        LocalDateTime now = LocalDateTime.now();
        for (User user : allUsers) {
            final EmailDto emailDto = emailProvider.sendMail(user.getEmail(),
                    reportString,
                    trainingProvider.getTrainingsByUserId(user.getId()).stream().filter(
                            training -> training.getStartTime().toInstant().isAfter(Instant.from(now))
                    ).collect(Collectors.toList()));
            emailSender.send(emailDto);
            System.out.println("sending email");
        }
    }
}
