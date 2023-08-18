package test;

import API.EmployeeService;
import DB.CompanyRepository;
import DB.EmployeeRepository;
import Model.api.CreateEmployeeRequest;
import Model.api.EmployeeFromAPI;
import Model.api.UpdateEmployeeRequest;
import Model.db.EmployeeFromDB;
import Model.db.InsertEmployee;
import extantion.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({EmployeeRepositoryResolver.class, CompanyRepositoryProvider.class, EmployeeServiceResolver.class, })
public class EmployeeServiceTest {


    @Test
    @DisplayName("Создание сотрудника авторизованным пользователем(admin)")
    public void adminCreateEmployee(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                               @AdminAuthorized EmployeeService service) throws SQLException, IOException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через API
        CreateEmployeeRequest createEmployee=new CreateEmployeeRequest("Bob","Nal","Alex",
                idNewCompany,"folowme@mail.com","http://123.org", "635587",
                "1990-05-06 00:00:00",true);
        service.createEmployee(createEmployee);
        List<EmployeeFromDB> list=employeeRepository.getAll(idNewCompany);
        assertEquals(1,list.size());
        EmployeeFromDB employeeBack=employeeRepository.getById(list.get(0).getId());
        assertEquals(createEmployee.getFirstName(),employeeBack.getFirst_name());
        assertEquals(createEmployee.getLastName(),employeeBack.getLast_name());
        assertEquals(createEmployee.getMiddleName(),employeeBack.getMiddle_name());
        assertEquals(createEmployee.getPhone(),employeeBack.getPhone());
        assertEquals(createEmployee.getEmail(),employeeBack.getEmail());
        assertEquals(createEmployee.getUrl(),employeeBack.getAvatar_url());
        assertEquals(createEmployee.getCompanyId(),employeeBack.getCompany_id());
        assertEquals(createEmployee.getBirthdate(),employeeBack.getBirthdate());
    }
    @Test
    @DisplayName("Создание сотрудника авторизованным пользователем(client)")
    public void clientShouldNotCreateEmployee(CompanyRepository companyRepository,
                                              EmployeeRepository employeeRepository,
                                              @ClientAuthorized EmployeeService service) throws IOException, SQLException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
        List<EmployeeFromDB> listBefore=employeeRepository.getAll(idNewCompany);
        assertEquals(0,listBefore.size());
//        Создаем сотрудника через API
        CreateEmployeeRequest createEmployee=new CreateEmployeeRequest("Bob","Nal","Alex",
                idNewCompany,"folowme@mail.com","http://123.org", "635587",
                "1990-05-06 00:00:00",true);
        service.createEmployee(createEmployee);
        List<EmployeeFromDB> listAfter=employeeRepository.getAll(idNewCompany);
        assertEquals(1,listAfter.size());
    }
    @Test
    @DisplayName("Получение списка сотрудников компании")
    public void listOfEmployees(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                    EmployeeService service) throws SQLException, IOException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через БД
        InsertEmployee employee=new InsertEmployee("Bob","Nal","Alex","635587",
                "folowme@mail.com","http://123.org",idNewCompany, Timestamp.valueOf("1990-12-01 00:00:00"),true);
        employeeRepository.addEmployee(employee);

        List<EmployeeFromDB> list1=employeeRepository.getAll(idNewCompany);
        List<EmployeeFromAPI> list2=service.getEmployees(idNewCompany);
        assertEquals(list1.size(),list2.size());
    }
    @Test
    @DisplayName("Получение сотрудника по id")
    public void employeeById(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                 EmployeeService service) throws IOException, SQLException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через БД
        InsertEmployee employee=new InsertEmployee("Bob","Nal","Alex","635587",
                "folowme@mail.com","http://123.org",idNewCompany, Timestamp.valueOf("1990-12-01 00:00:00"),true);
        employeeRepository.addEmployee(employee);
        List<EmployeeFromDB> list=employeeRepository.getAll(idNewCompany);
        assertEquals(1,list.size());
//        Получаем сотрудника через API
        EmployeeFromAPI employeeBack=service.getEmployeeById(list.get(0).getId());
        assertEquals(employee.getFirst_name(),employeeBack.getFirstName());
        assertEquals(employee.getLast_name(),employeeBack.getLastName());
        assertEquals(employee.getMiddle_name(),employeeBack.getMiddleName());
        assertEquals(employee.getPhone(),employeeBack.getPhone());
        assertEquals(employee.getEmail(),employeeBack.getEmail());
        assertEquals(employee.getAvatar_url(),employeeBack.getUrl());
        assertEquals(employee.getCompany_id(),employeeBack.getCompanyId());
        assertEquals("1990-12-01",employeeBack.getBirthdate());
    }
    @Test
    @DisplayName("Обновление информации о сотруднике (last_name,email,url,isActive) авторизованным пользователем(admin)")
    public void adminUpdateEmployee(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                   @AdminAuthorized EmployeeService service) throws SQLException, IOException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через БД
        InsertEmployee employee=new InsertEmployee("Bob","Nal","Alex","635587",
                "folowme@mail.com","http://123.org",idNewCompany, Timestamp.valueOf("1990-12-01 00:00:00"),true);
        employeeRepository.addEmployee(employee);
//        Обновляем информацию о сотруднике через API
        UpdateEmployeeRequest updateEmployeeRequest=new UpdateEmployeeRequest("Rob","forup@cinm.ju",
                "http://test.123",false);
        List<EmployeeFromDB> list=employeeRepository.getAll(idNewCompany);
        assertEquals(1,list.size());
        service.updateEmployeeInfo(list.get(0).getId(),updateEmployeeRequest);
        EmployeeFromDB employeeBack=employeeRepository.getById(list.get(0).getId());
        assertEquals(updateEmployeeRequest.getLastName(),employeeBack.getLast_name());
        assertEquals(updateEmployeeRequest.getEmail(),employeeBack.getEmail());
        assertEquals(updateEmployeeRequest.getUrl(),employeeBack.getAvatar_url());
        assertEquals(updateEmployeeRequest.getIsActive(),employeeBack.getIs_active());
    }
    @Test
    @DisplayName("Обновление информации о сотруднике (last_name,email,url,isActive) авторизованным пользователем(client)")
    public void clientUpdateEmployee(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                    @ClientAuthorized EmployeeService service) throws SQLException, IOException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через БД
        InsertEmployee employee=new InsertEmployee("Bob","Nal","Alex","635587",
                "folowme@mail.com","http://123.org",idNewCompany, Timestamp.valueOf("1990-12-01 00:00:00"),true);
        employeeRepository.addEmployee(employee);
//        Обновляем информацию о сотруднике через API
        UpdateEmployeeRequest updateEmployeeRequest=new UpdateEmployeeRequest("Rob","forup@cinm.ju",
                "http://test.123",false);
        List<EmployeeFromDB> list=employeeRepository.getAll(idNewCompany);
        assertEquals(1,list.size());
        service.updateEmployeeInfo(list.get(0).getId(),updateEmployeeRequest);
        EmployeeFromDB employeeBack=employeeRepository.getById(list.get(0).getId());
        assertEquals(updateEmployeeRequest.getLastName(),employeeBack.getLast_name());
        assertEquals(updateEmployeeRequest.getEmail(),employeeBack.getEmail());
        assertEquals(updateEmployeeRequest.getUrl(),employeeBack.getAvatar_url());
        assertEquals(updateEmployeeRequest.getIsActive(),employeeBack.getIs_active());
    }
    @Test
    @DisplayName("Неавторизованный пользователь не может создать сотрудника")
    public void unauthorizedClientShouldNotCreateEmployee(CompanyRepository companyRepository,
                                                          EmployeeRepository employeeRepository,
                                                          EmployeeService service) throws IOException, SQLException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
        List<EmployeeFromDB> listBefore=employeeRepository.getAll(idNewCompany);
        assertEquals(0,listBefore.size());
//        Создаем сотрудника через API
        CreateEmployeeRequest createEmployee=new CreateEmployeeRequest("Bob","Nal","Alex",
                idNewCompany,"folowme@mail.com","http://123.org", "635587",
                "1990-05-06",true);
        service.createEmployeeUnauthorized(createEmployee);
        List<EmployeeFromDB> listAfter=employeeRepository.getAll(idNewCompany);
        assertEquals(0,listAfter.size());
    }
    @Test
    @DisplayName("Неавторизованный пользователь не может обновить информации о сотруднике (last_name,email,url,isActive)")
    public void unauthorizedClientShouldNotUpdateEmployee(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                                           EmployeeService service) throws SQLException, IOException {
//        Создаем компанию через БД
        int idNewCompany=companyRepository.createCompany();
//        Создаем сотрудника через БД
        InsertEmployee employee=new InsertEmployee("Bob","Nal","Alex","635587",
                "folowme@mail.com","http://123.org",idNewCompany, Timestamp.valueOf("1990-12-01 00:00:00"),true);
        employeeRepository.addEmployee(employee);
//        Обновляем информацию о сотруднике через API
        UpdateEmployeeRequest updateEmployeeRequest=new UpdateEmployeeRequest("Rob","forup@cinm.ju",
                "http://test.123",false);
        List<EmployeeFromDB> list=employeeRepository.getAll(idNewCompany);
        assertEquals(1,list.size());
        service.updateEmployeeInfoUnauthorized(list.get(0).getId(),updateEmployeeRequest);
        EmployeeFromDB employeeBack=employeeRepository.getById(list.get(0).getId());
        assertEquals(employee.getLast_name(),employeeBack.getLast_name());
        assertEquals(employee.getEmail(),employeeBack.getEmail());
        assertEquals(employee.getAvatar_url(),employeeBack.getAvatar_url());
        assertEquals(employee.getIsActive(),employeeBack.getIs_active());
    }


}
