package API;

import Model.api.CreateEmployeeRequest;
import Model.api.EmployeeFromAPI;
import Model.api.UpdateEmployeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class EmployeeServiceImpl_OkHttp implements EmployeeService{
    private final OkHttpClient client;
    private static String BASE_URL;
    private String PATH="employee";
    private MediaType APPLICATION_JSON=MediaType.parse("application/json; charset=utf-8");
    private ObjectMapper mapper;

    private String userToken;


    public EmployeeServiceImpl_OkHttp(OkHttpClient client,String url) {
        this.client = client;
        BASE_URL=url;
        mapper=new ObjectMapper();
    }


    @Override
    public List<EmployeeFromAPI> getEmployees(int id) throws IOException {
        HttpUrl url= HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegments(PATH)
                .addQueryParameter("company",Integer.toString(id))
                .build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.newCall(request).execute();
        return mapper.readValue(response.body().string(), new TypeReference<List<EmployeeFromAPI>>() {});
    }

    @Override
    public int createEmployee(CreateEmployeeRequest createEmployeeRequest) throws IOException {
        RequestBody body=RequestBody.create(mapper.writeValueAsString(createEmployeeRequest),APPLICATION_JSON);
        HttpUrl url=HttpUrl.parse(BASE_URL).newBuilder().addPathSegment(PATH).build();
        Request request=new Request.Builder().post(body).url(url).addHeader("x-client-token",userToken).build();
        Response response=client.newCall(request).execute();
        EmployeeFromAPI employee=mapper.readValue(response.body().string(),EmployeeFromAPI.class);
        return employee.getId();
    }
    @Override
    public int createEmployeeUnauthorized(CreateEmployeeRequest createEmployeeRequest) throws IOException {
        RequestBody body=RequestBody.create(mapper.writeValueAsString(createEmployeeRequest),APPLICATION_JSON);
        HttpUrl url=HttpUrl.parse(BASE_URL).newBuilder().addPathSegment(PATH).build();
        Request request=new Request.Builder().post(body).url(url).build();
        Response response=client.newCall(request).execute();
        EmployeeFromAPI employee=mapper.readValue(response.body().string(),EmployeeFromAPI.class);
        return employee.getId();
    }

    @Override
    public EmployeeFromAPI getEmployeeById(int id) throws IOException {
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegments(PATH)
                .addPathSegment(Integer.toString(id))
                .build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.newCall(request).execute();
        return mapper.readValue(response.body().string(), EmployeeFromAPI.class);
    }

    @Override
    public void updateEmployeeInfo(int id, UpdateEmployeeRequest updateEmployeeRequest) throws IOException {
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegments(PATH)
                .addPathSegment(Integer.toString(id))
                .build();
        RequestBody body=RequestBody.create(mapper.writeValueAsString(updateEmployeeRequest),APPLICATION_JSON);
        Request request=new Request.Builder().patch(body).url(url).addHeader("x-client-token",userToken).build();
        client.newCall(request).execute();
    }
    @Override
    public void updateEmployeeInfoUnauthorized(int id, UpdateEmployeeRequest updateEmployeeRequest) throws IOException {
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegments(PATH)
                .addPathSegment(Integer.toString(id))
                .build();
        RequestBody body=RequestBody.create(mapper.writeValueAsString(updateEmployeeRequest),APPLICATION_JSON);
        Request request=new Request.Builder().patch(body).url(url).build();
        client.newCall(request).execute();
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
