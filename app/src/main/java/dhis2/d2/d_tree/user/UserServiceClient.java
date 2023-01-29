package dhis2.d2.d_tree.user;


import dhis2.d2.d_tree.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceClient {

    private static UserServiceClient instance = null;
    private UserService userService;

    private UserServiceClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
        userService = retrofit.create(UserService.class);
    }

    public static synchronized UserServiceClient getInstance() {
        if (instance == null) {
            instance = new UserServiceClient();
        }
        return instance;
    }

    public UserService getApi() {
        return userService;
    }
}
