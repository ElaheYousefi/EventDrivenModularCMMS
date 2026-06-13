package ir.dadeandish.event;

import java.time.LocalDate;

public class WorkOrderAssignedEvent {
    private String priority;
    private int id;
    private LocalDate dueDate;
    private String employeeName;
    private String mobile;
    private String email;
    private String equipmentName;

    public WorkOrderAssignedEvent(String priority, int id, LocalDate dueDate,
                                 String employeeName, String email,
                                 String mobile, String equipmentName) {
        this.priority = priority;
        this.id = id;
        this.dueDate = dueDate;
        this.employeeName = employeeName;
        this.mobile = mobile;
        this.email = email;
        this.equipmentName = equipmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
