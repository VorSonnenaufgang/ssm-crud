package ink.vor.crud.service;

import ink.vor.crud.pojo.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muquanrui
 * @date 16/02/2022 17:45
 */

@Service
public interface EmployeeService {
    List<Employee> getAll();

    void saveEmp(Employee employee);

    boolean checkUser(String empName);

    Employee getEmp(int id);

    void updateEmp(Employee employee);

    void deleteEmp(int id);

    void deleteBatch(List<Integer> ids);
}
