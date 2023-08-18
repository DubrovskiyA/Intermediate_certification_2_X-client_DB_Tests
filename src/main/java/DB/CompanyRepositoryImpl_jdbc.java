package DB;

import Model.api.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRepositoryImpl_jdbc implements CompanyRepository{
    private final Connection connection;
    private int companyId;
    private static final String INSERT_company="insert into company (name,description) values ('Test','For test')";
    private static final String SELECT_lAST="select*from company where \"deleted_at\" is null order by \"id\" desc limit 1";
    private static final String DELETE_COMPANY="delete from company where \"id\"=?";


    public CompanyRepositoryImpl_jdbc(Connection connection) {
        this.connection = connection;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public int createCompany() throws SQLException {
        int resultSet=connection.createStatement().executeUpdate(INSERT_company);
        ResultSet resultSet1=connection.createStatement().executeQuery(SELECT_lAST);
        Company company=new Company();
        resultSet1.next();
        company.setId(resultSet1.getInt("id"));
        return companyId=company.getId();
    }
    void deleteCompany(int companyId) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(DELETE_COMPANY);
        preparedStatement.setInt(1,companyId);
        preparedStatement.executeUpdate();
    }


}
