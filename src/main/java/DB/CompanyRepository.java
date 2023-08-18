package DB;

import java.sql.SQLException;

public interface CompanyRepository {
    int createCompany() throws SQLException;
}
