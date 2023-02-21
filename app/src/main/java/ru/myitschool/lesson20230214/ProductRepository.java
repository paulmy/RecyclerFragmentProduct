package ru.myitschool.lesson20230214;

import java.util.ArrayList;

public class ProductRepository {
    private static ProductRepository instance = null;

    public static ProductRepository getInstance() {
        if (instance == null) instance = new ProductRepository();
        return instance;
    }

    private final ArrayList<ProductData> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new ProductData("Хлеб", "бородинский", 45));
        products.add(new ProductData("Молоко", "жирность 3,5", 60));
        products.add(new ProductData("Сыр", "Российский", 50));

    }


    public ArrayList<ProductData> getProducts() {

        return (ArrayList<ProductData>) products;
    }

    public int getPos(ProductData data) {
        return products.indexOf(data);
    }

    public void addProduct(ProductData productData) {

        products.add(productData);
    }

    public void removeByPosition(int position) {
        products.remove(position);

    }

    public void replaceProduct(ProductData temp, int position) {

        products.set(position,temp);
    }
}
