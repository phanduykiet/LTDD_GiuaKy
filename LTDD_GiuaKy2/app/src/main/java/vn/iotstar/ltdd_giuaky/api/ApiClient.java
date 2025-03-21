package vn.iotstar.ltdd_giuaky.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // Sử dụng 10.0.2.2 cho localhost trên trình giả lập

    public static synchronized Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Đường dẫn API cơ sở
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON sang Object
                    .build();
        }
        return retrofit;
    }
}

