package com.capgemini.wsb.fitnesstracker.notification.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailProvider;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.internal.EmailService;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

    @Scheduled(cron = "15 2 15 ? * 2") //report scheduled for every sunday at 18:00:00
    public void generateReportAndSendMail() {
        System.out.println("Cron scheduling report generation");
        List<User> allUsers = userProvider.findAllUsers();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minus(1, ChronoUnit.WEEKS);
        for (User user : allUsers) {
            List<Training> recentTrainings = trainingProvider.getTrainingsByUserId(user.getId()).stream()
                    .filter(training -> toLocalDateTime(training.getStartTime()).isAfter(oneWeekAgo))
                    .collect(Collectors.toList());
            if (!recentTrainings.isEmpty()) {
                final EmailDto emailDto = emailProvider.sendMail(user.getEmail(),
                        reportString,
                        recentTrainings);
                emailSender.send(emailDto);
                System.out.println("sending email");
            }
        }
    }
    private LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
