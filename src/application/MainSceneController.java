package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.Map;

public class MainSceneController {
    @FXML
    private Label totalPriceLabel; 
    public double totalPrice = 0;
    private static MainSceneController instance;
    
    @FXML 
    private ListView<Product> productsListView;
    private ObservableList<Product> observableProductsList = FXCollections.observableArrayList();
    private Map<String, Product> products = new HashMap<>();
    
    public void initialize() {
        // Add products to the hashmap;
        products.put("BOTELLA AGUA 1€", new Product("AGUA", 1.0));
        products.put("CAFE 1.5€", new Product("CAFE", 1.5));
        products.put("ZUMO NARANJA 2€", new Product("ZUMO NARANJA", 2));
        products.put("COLACAO 1€", new Product("COLACAO", 1));
        products.put("SANDWICH 3.2€", new Product("SANDWICH", 3.2));
        products.put("BOCADILLO ATUN 5€", new Product("BOCADILLO ATUN", 5));
        products.put("BOCADILLO LOMO 7€", new Product("BOCADILLO LOMO", 7));
        products.put("BOCADILLO YORK 4€", new Product("BOCADILLO YORK", 4));
        products.put("BOCADILLO JAMON 7€", new Product("BOCADILLO JAMON", 7));
        products.put("CROISSANT 3€", new Product("CROISSANT", 3));
        products.put("NAP JAMON QUESO 4€", new Product("NAPOLITANA", 4));
        products.put("DONUT 1.5€", new Product("DONUT", 1.5));

        // Iterates in the hashmap and puts in the list that the user will view the products that have at least 1 unit
        for (Product product : products.values()) {
            if (product.getQuantity() > 0) {
            	observableProductsList.add(product);
            }
        }

        // Set the ListView items
        productsListView.setItems(observableProductsList);
        productsListView.setCellFactory(param -> new ProductCell());
    }
    
    // Did a singleton so i can access the totalPrice value
    public MainSceneController() {
        instance = this;
    }
    
    public static MainSceneController getInstance() {
        return instance;
    }
    
	// With this method we can handle the product we choose by pressing the button, with the text we have in the button 
    // this method search his index(text of button) in the hashmap and get his values;
    @FXML
    public void handleProductButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String productName = button.getText();

        Product product = products.get(productName);
            String name = product.getName();
            double price = product.getPrice();
            int quantity = product.getQuantity();
            
            // If the product doesnt at first this condition will be adding this product at list
            if (product.getQuantity() == 0) {
            	observableProductsList.add(product);
            }
                        
            // Increase in 1 the quantity of the product
            product.increaseQuantity();
            
            // Add the total price of the product to the total
            updateTotalPrice(price);
           
            productsListView.refresh();

    }
    
    // Update the total price of the order
    public void updateTotalPrice(double price) {
        this.totalPrice += price;
        totalPriceLabel.setText("TOTAL: " + String.format("%.2f", Math.abs(this.totalPrice)));
    }

}


