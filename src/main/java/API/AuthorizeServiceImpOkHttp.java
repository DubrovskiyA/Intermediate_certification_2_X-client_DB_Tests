package API;

import Model.api.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class AuthorizeServiceImpOkHttp implements AuthorizeService{
    private String BASE_URL;
    private String PATH="auth/login";
    private OkHttpClient client;
    private ObjectMapper mapper;
    private MediaType APPLICATION_JSON=MediaType.parse("application/json; charset=utf-8");

    public AuthorizeServiceImpOkHttp(OkHttpClient client, String url) {
        this.BASE_URL = url;
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    @Override
    public UserInfo auth(String username, String password) throws IOException {
        RequestBody bodyAuth=RequestBody.create("{\"username\":\""+username+"\",\"password\":\""+password+"\"}",APPLICATION_JSON);
        HttpUrl urlAuth=HttpUrl.parse(BASE_URL).newBuilder().addPathSegments(PATH).build();
        Request request=new Request.Builder().post(bodyAuth).url(urlAuth).build();
        Response response=client.newCall(request).execute();
        UserInfo userInfo=mapper.readValue(response.body().string(), UserInfo.class);
        return userInfo;
    }
}
