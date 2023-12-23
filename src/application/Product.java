package application;

public class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0; // The default quantity of product is 0
    }

    // Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
    public void increaseQuantity() {
        this.quantity += 1;
    }
    
    public void decreaseQuantity() {
        if(this.quantity >= 1) this.quantity -= 1;
    }

}

