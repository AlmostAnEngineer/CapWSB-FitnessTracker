package com.capgemini.wsb.fitnesstracker.mail.api;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import java.util.List;

public interface EmailProvider {
    EmailDto sendMail(String from, String to, String subject, String body, List<Training> trainingList);
}
