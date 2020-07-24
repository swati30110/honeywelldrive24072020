package employeedemo.employee.service.impl;

import employeedemo.employee.dao.IEmployeeDao;
import employeedemo.employee.service.IEmployeeService;
import employeedemo.employee.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    IEmployeeDao iEmployeeDao;

    @Override
    public void createEmployee(Employee employee) throws Exception {
        iEmployeeDao.createEmployee(employee);
    }

    @Override
    public List<Employee> getEmployeeByAgeGender(String gender, double minage, double maxage) throws Exception {
        List<Employee> employees = iEmployeeDao.getEmployeeByAgeGender(gender,minage,maxage);
        return employees;
    }
}
