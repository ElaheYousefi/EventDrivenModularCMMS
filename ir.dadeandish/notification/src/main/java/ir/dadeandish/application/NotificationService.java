package ir.dadeandish.application;

import ir.dadeandish.event.WorkOrderAssignedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class NotificationService {

    private EmailService emailService;
    private SmsService smsService;

    @Autowired
    public NotificationService(SmsService smsService, EmailService emailService) {
        this.emailService = emailService;
        this.smsService= smsService;
    }



    @EventListener
    public void handle(WorkOrderAssignedEvent e){
        sendEmail(e.getId(), e.getEmail(), e.getDueDate(), e.getEmployeeName(), e.getEquipmentName(), e.getPriority());
        sendSMS(e.getId(), e.getMobile(), e.getDueDate(), e.getEmployeeName(), e.getEquipmentName(), e.getPriority());
    }

    private void sendEmail(Integer workOrderId, String email, LocalDate dueDate, String employeeName, String equipmentName, String priority) {

        String subject = "New Work Order Assigned";

        String body = String.format(
                "Hello %s,\n\n" +
                        "A new work order has been assigned to you.\n\n" +
                        "Work Order ID: %d\n" +
                        "Equipment: %s\n" +
                        "Priority: %s\n" +
                        "Due Date: %s\n\n" +
                        "Please check your dashboard.",
//                employeeName,
                workOrderId,
                equipmentName,
                priority,
                dueDate
        );

        emailService.sendEmail(email, subject, body);
    }

    private void sendSMS(Integer workOrderId, String mobile, LocalDate dueDate, String employeeName, String equipmentName, String priority) {

        String message = String.format(
                "New Work Order Assigned:\nID: %d\nEquipment: %s\nPriority: %s",
                workOrderId,
                equipmentName,
                priority
        );

        smsService.sendSms(mobile, message);
    }
}
