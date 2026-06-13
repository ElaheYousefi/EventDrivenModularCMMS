package ir.dadeandish.api;

public interface EmployeeAPI {

    public boolean validateEmployee(int employeeId);
    EmployeeDto getEmployee(int employeeId);

}
