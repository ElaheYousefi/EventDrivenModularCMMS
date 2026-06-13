package ir.dadeandish.application;

import ir.dadeandish.api.EmployeeAPI;
import ir.dadeandish.api.EmployeeDto;
import ir.dadeandish.domain.EmployeeRepository;

public class EmployeeService implements EmployeeAPI {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean validateEmployee(int employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    public EmployeeDto getEmployee(int employeeId){
        return new EmployeeDto();
    }
}
