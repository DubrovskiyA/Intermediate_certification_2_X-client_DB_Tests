package extantion;

import DB.EmployeeRepository;
import DB.EmployeeRepositoryImpl_jdbc;
import extantion.props.PropertyProvider;
import org.junit.jupiter.api.extension.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class EmployeeRepositoryResolver implements ParameterResolver, BeforeAllCallback, AfterAllCallback {
    private Connection connection=null;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(EmployeeRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new EmployeeRepositoryImpl_jdbc(connection);

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("=====connecting=====");
        String connectionString= PropertyProvider.getInstance().getProps().getProperty("testDB.connectionString");
        String user=PropertyProvider.getInstance().getProps().getProperty("testDB.user");
        String pass=PropertyProvider.getInstance().getProps().getProperty("testDB.pass");
        connection=DriverManager.getConnection(connectionString,user,pass);
    }
    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("=====disconnecting=====");
        if (connection!=null&&!connection.isClosed()) {
            connection.close();
        }
    }

}
