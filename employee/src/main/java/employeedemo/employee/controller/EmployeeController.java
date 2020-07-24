package employeedemo.employee.controller;

import employeedemo.employee.dao.impl.EmployeeDao;
import employeedemo.employee.service.IEmployeeService;
import employeedemo.employee.vo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("employee")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) throws Exception{
        try{
            if(employee.getEmpId() != null && employee !=null){
                iEmployeeService.createEmployee(employee);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }catch(Exception e){
            logger.debug("Error from api employees "+e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("employees")
    public ResponseEntity<List<Employee>> getEmployeesByGenderAge(@RequestParam String gender ,
                                                                  @RequestParam double minage,
                                                                  @RequestParam double maxage)throws Exception{
        if( (gender.equals("") || gender == null)
                && (minage > 0 && maxage > 0)
                && (maxage >= minage )){
            logger.debug("BAD REQUEST");
            return new ResponseEntity<List<Employee>>(HttpStatus.BAD_REQUEST);
        }
        List<Employee> employees = iEmployeeService.getEmployeeByAgeGender(gender,minage,maxage);
        if (employees.size() == 0) {
            logger.debug("No Employees with the given condition is not in the database");
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
        }
    }
}
