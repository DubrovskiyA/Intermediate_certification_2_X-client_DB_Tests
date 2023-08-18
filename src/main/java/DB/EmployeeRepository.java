package DB;

import Model.db.EmployeeFromDB;
import Model.db.InsertEmployee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeRepository {
    List<EmployeeFromDB> getAll(int id) throws SQLException;
    void addEmployee(InsertEmployee employee) throws SQLException;
    EmployeeFromDB getById(int id) throws SQLException;
}
