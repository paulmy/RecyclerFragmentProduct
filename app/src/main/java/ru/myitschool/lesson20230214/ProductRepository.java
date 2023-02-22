package ru.myitschool.lesson20230214;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class ProductRepository implements AutoCloseable {
    private static ProductRepository instance = null;

    //Context context;
    public static DataBaseHelper dataBaseHelper;

    public static ProductRepository getInstance(Context context) {
        if (instance == null) instance = new ProductRepository(context);
        return instance;
    }

    private ArrayList<ProductData> products = new ArrayList<>();

    public ProductRepository(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        Log.d("DB","OPEN DB REPO  ProductRepository(Context context) ");
        products.addAll(dataBaseHelper.getAll() );

        // products.add(new ProductData("Хлеб", "бородинский", 45));
        // products.add(new ProductData("Молоко", "жирность 3,5", 60));
        //products.add(new ProductData("Сыр", "Российский", 50));

    }


    public ArrayList<ProductData> getProducts() {
        return dataBaseHelper.getAll();
        // return (ArrayList<ProductData>) products;
    }

    public int getPos(ProductData data) {
        return products.indexOf(data);
    }

    public void addProduct(ProductData productData) {
        dataBaseHelper.add(productData);
        //products.add(productData);
    }

    public void removeByPosition(int position) {
        dataBaseHelper.removeProduct(position);
        //products.remove(position);

    }

    public void replaceProduct(ProductData temp, int position) {

        products.set(position, temp);
    }


    @Override
    public void close() {
        dataBaseHelper.close();
        Log.d("DB","CLOSE DB PRODUCT_REPO dataBaseHelper.close()");
    }
}
