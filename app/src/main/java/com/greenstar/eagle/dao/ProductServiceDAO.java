package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.ProductService;

import java.util.List;

@Dao
public interface ProductServiceDAO {
    @Insert
    void insertMultiple (List<ProductService> productServices);

    @Insert
    void insert (ProductService productService);

    @Query("SELECT * FROM ProductService where status=0")
    List<ProductService> getAllPending();

    @Query("SELECT * FROM ProductService where type=1 and status=1")
    List<ProductService> getAllProducts();


    @Query("SELECT * FROM ProductService where type=2 and status=1")
    List<ProductService> getAllServices();

    @Query("SELECT count(*) FROM ProductService")
    int getCount();

    @Query("DELETE FROM ProductService")
    public void nukeTable();
}
