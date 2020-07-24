package employeedemo.employee.dao.impl;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import employeedemo.employee.dao.IEmployeeDao;
import employeedemo.employee.vo.Employee;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDao implements IEmployeeDao {
    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

    @Override
    public void createEmployee(Employee employee) throws Exception {
        try {
            mongoTemplate.save(employee,"employee");
        }catch (Exception e){
            logger.debug(e.getMessage());
        }
    }

    @Override
    public List<Employee> getEmployeeByAgeGender(String gender, double minage, double maxage)throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        MongoCollection<Document> mongoCollection = mongoTemplate.getCollection("employee");
        //BasicDBList fields = new BasicDBList();
        BasicDBObject queryCondition = new BasicDBObject();
        if(gender != null){
            queryCondition.put("gender",gender);
        }
        if(minage > 0 && maxage > 0 && maxage > minage){
            queryCondition.put("age",new BasicDBObject("$gte", minage).append("$lte", maxage));
        }
        FindIterable<Document> findIterable =mongoCollection.find(queryCondition);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            while (mongoCursor.hasNext()) {
                Document document = mongoCursor.next();
                if (document != null) {
                    Employee employee = new Employee();
                    employee.setEmpId((String) document.get("empId"));
                    employee.setName((String) document.get("name"));
                    employee.setAge((double) document.get("age"));
                    employee.setGender((String) document.get("gender"));
                    employees.add(employee);
                }
            }
        }catch(Exception e){
            logger.debug(e.getMessage());
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
