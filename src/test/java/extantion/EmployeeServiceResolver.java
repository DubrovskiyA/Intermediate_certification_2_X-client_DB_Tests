package extantion;

import API.*;
import Model.api.UserInfo;
import extantion.props.PropertyProvider;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;

public class EmployeeServiceResolver implements ParameterResolver {
    private final static String URL = PropertyProvider.getInstance().getProps().getProperty("test.url");
    private final static String ADMIN_USER = PropertyProvider.getInstance().getProps().getProperty("test.admin.user");
    private final static String ADMIN_PASS = PropertyProvider.getInstance().getProps().getProperty("test.admin.pass");
    private final static String CLIENT_USER = PropertyProvider.getInstance().getProps().getProperty("test.client.user");
    private final static String CLIENT_PASS = PropertyProvider.getInstance().getProps().getProperty("test.client.pass");



    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(EmployeeService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        OkHttpClient client= new OkHttpClient.Builder().addNetworkInterceptor(new LogInterceptor()).build();
        EmployeeService service=new EmployeeServiceImpl_OkHttp(client,URL);

        if (parameterContext.isAnnotated(AdminAuthorized.class)){
            AuthorizeService authorizeService = new AuthorizeServiceImpOkHttp(client, URL);
            UserInfo userInfo;
            try {
                userInfo = authorizeService.auth(ADMIN_USER, ADMIN_PASS);
                } catch (IOException e) {
                throw new RuntimeException(e);
            }
            service.setUserToken(userInfo.getUserToken());
        }
        if (parameterContext.isAnnotated(ClientAuthorized.class)){
            AuthorizeService authorizeService = new AuthorizeServiceImpOkHttp(client, URL);
            UserInfo userInfo;
            try {
                userInfo = authorizeService.auth(CLIENT_USER, CLIENT_PASS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            service.setUserToken(userInfo.getUserToken());
        }

        return service;
        }
    }

