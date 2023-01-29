package dhis2.d2.d_tree.user;



import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

interface UserService {
    @GET("group-1")
    Observable<List<User>> getUsers(@Header("x-apikey") String authToken);
}
