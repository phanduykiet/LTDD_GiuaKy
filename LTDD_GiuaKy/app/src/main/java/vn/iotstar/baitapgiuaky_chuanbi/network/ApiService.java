package vn.iotstar.baitapgiuaky_chuanbi.network;

import vn.iotstar.baitapgiuaky_chuanbi.models.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("l") // Đường dẫn API
    Call<List<Product>> getLastProducts();
}
