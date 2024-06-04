package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailProvider;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class EmailService implements EmailProvider {

    @Override
    public EmailDto sendMail(String to, String subject, List<Training> trainingList) {
        System.out.println("Creating mail to:" + to);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder reportString = new StringBuilder();
        reportString.append("WEEKLY REPORT \n");
        reportString.append("FOR ").append(to).append("\n");
        reportString.append("GENERATED FROM: ").append(lastWeek.format(formatter))
                .append(" TO: ").append(now.format(formatter));
        reportString.append("AMOUNT OF TRAININGS:").append(trainingList.size()).append("\n");
        reportString.append("TRAINING REPORT:");
        for (Training training : trainingList) {
            reportString.append("TRAINING ID: ").append(training.getId()).append("\n");
            reportString.append("TRAINING START TIME").append(training.getStartTime()).append("\n");
            reportString.append("TRAINING END TIME").append(training.getEndTime()).append("\n");
            reportString.append("ACTIVITY TYPE: ").append(training.getActivityType()).append("\n");
            reportString.append("DISTANCE: ").append(training.getDistance()).append("\n");
            reportString.append("AVERAGE SPEED: ").append(training.getAverageSpeed()).append("\n");
        }
        reportString.append("END OF REPORT\n");
        EmailDto mail = new EmailDto(to, subject, reportString.toString());
        System.out.println("mail created");
        return mail;
    }
}
