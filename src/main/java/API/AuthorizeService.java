package API;

import Model.api.UserInfo;

import java.io.IOException;

public interface AuthorizeService {
    UserInfo auth(String username, String password) throws IOException;
}
