package vn.iotstar.ltdd_giuaky.api;

import vn.iotstar.ltdd_giuaky.dto.Category;
import vn.iotstar.ltdd_giuaky.dto.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    //22110360_PhanDuyKiet
    @GET("categories") // Endpoint của API
    Call<List<Category>> getCategories();

    @GET("productsByCategory") // Endpoint mới
    Call<List<Product>> getProductsByCategory(@Query("categoryName") String categoryName); // Trả về danh sách sản phẩm
}