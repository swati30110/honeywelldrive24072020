package employeedemo.employee.service;

import employeedemo.employee.vo.Employee;

import java.util.List;

public interface IEmployeeService {
    void createEmployee(Employee employee) throws Exception;
    List<Employee> getEmployeeByAgeGender(String gender , double minage , double maxage) throws Exception;
}
