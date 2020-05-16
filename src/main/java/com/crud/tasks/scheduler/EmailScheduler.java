package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private final static String SUBJECT = "TASKS: Once a day email";
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    SimpleEmailService simpleEmailService;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String task = size == 1 ? "task" : "tasks";
        simpleEmailService.send(new Mail(adminConfig.getEmail(),
                SUBJECT,
                "Currently in database you got: " + size + " " + task,
                null));
    }
}
