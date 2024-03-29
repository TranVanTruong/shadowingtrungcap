package shadowing.systemtrust.shadowing2;

import java.io.Serializable;

public class Product implements Serializable {
    private    int    id;
    private    String name;
    private    int    quantity;
    private String detail;
    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public Product() {
    }
    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    public Product(int id, String name, int quantity, String detail) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

