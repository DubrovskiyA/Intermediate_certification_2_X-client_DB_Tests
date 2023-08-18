package API;

import Model.api.CreateEmployeeRequest;
import Model.api.EmployeeFromAPI;
import Model.api.UpdateEmployeeRequest;

import java.io.IOException;
import java.util.List;

public interface EmployeeService extends Authorizable {
    List<EmployeeFromAPI> getEmployees(int id) throws IOException;
    int createEmployee(CreateEmployeeRequest createEmployeeRequest) throws IOException;

    int createEmployeeUnauthorized(CreateEmployeeRequest createEmployeeRequest) throws IOException;

    EmployeeFromAPI getEmployeeById(int id) throws IOException;
    void updateEmployeeInfo(int id, UpdateEmployeeRequest updateEmployeeRequest) throws IOException;

    void updateEmployeeInfoUnauthorized(int id, UpdateEmployeeRequest updateEmployeeRequest) throws IOException;
}
