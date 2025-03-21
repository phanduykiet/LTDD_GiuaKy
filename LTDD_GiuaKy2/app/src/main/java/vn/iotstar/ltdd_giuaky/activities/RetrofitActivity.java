package vn.iotstar.ltdd_giuaky.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.iotstar.ltdd_giuaky.OnCategoryClickListener;
import vn.iotstar.ltdd_giuaky.R;
import vn.iotstar.ltdd_giuaky.adapter.CategoryAdapter;
import vn.iotstar.ltdd_giuaky.adapter.ProductAdapter;
import vn.iotstar.ltdd_giuaky.api.APIService;
import vn.iotstar.ltdd_giuaky.api.ApiClient;
import vn.iotstar.ltdd_giuaky.dto.Category;
import vn.iotstar.ltdd_giuaky.dto.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity implements OnCategoryClickListener {
    private RecyclerView rcCate, rcProduct;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private APIService apiService;
    private List<Category> categoryList;
    private List<Product> productList; // Lưu trữ sản phẩm trả về từ API
    private TextView tvProductsTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView
        rcCate = findViewById(R.id.rc_category);
        rcProduct = findViewById(R.id.rc_product);
        tvProductsTitle = findViewById(R.id.textView8);
        // Khởi tạo APIService
        apiService = ApiClient.getRetrofitInstance().create(APIService.class);

        // Gọi API để lấy danh mục
        getCategories();
    }

    private void getCategories() {
        Log.d("RetrofitActivity", "Fetching categories...");
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RetrofitActivity", "Categories fetched: " + response.body().size());
                    categoryList = response.body();

                    categoryAdapter = new CategoryAdapter(RetrofitActivity.this, categoryList, RetrofitActivity.this);
                    rcCate.setLayoutManager(new LinearLayoutManager(
                            RetrofitActivity.this,
                            LinearLayoutManager.HORIZONTAL,
                            false
                    ));
                    rcCate.setAdapter(categoryAdapter);
                } else {
                    Log.e("RetrofitActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Log.e("RetrofitActivity", "API call failed: " + t.getMessage());
            }
        });
    }

    private void getProductsByCategory(String categoryName) {
        Log.d("RetrofitActivity", "Fetching products for category: " + categoryName);
        apiService.getProductsByCategory(categoryName).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RetrofitActivity", "Products fetched: " + response.body().size());
                    productList = response.body();

                    productAdapter = new ProductAdapter(RetrofitActivity.this, productList);
                    rcProduct.setLayoutManager(new GridLayoutManager(
                            RetrofitActivity.this,
                            3
                    ));
                    rcProduct.setAdapter(productAdapter);
                } else {
                    Log.e("RetrofitActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e("RetrofitActivity", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onCategoryClick(String categoryName) {
        Log.d("RetrofitActivity", "Category clicked: " + categoryName); // Log để kiểm tra tên danh mục
        tvProductsTitle.setText(categoryName);
        getProductsByCategory(categoryName); // Chỉ gọi khi click
    }
}