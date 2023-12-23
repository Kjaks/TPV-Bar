package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class ProductCell extends ListCell<Product> {
	// Here did a singleton so i can access to the totalPrice of the instance
    MainSceneController controller = MainSceneController.getInstance();
    
    // Adding buttons to the cell where we see each product
    private HBox hbox = new HBox();
    private Button addButton = new Button("+");
    private Button removeButton = new Button("-");
    
    public ProductCell() {
    	
    	// Setting styles
    	addButton.setStyle("-fx-background-color: transparent; -fx-font-size: 30px; -fx-font-weight: bold; -fx-cursor: hand;");
        removeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 30px; -fx-font-weight: bold; -fx-cursor: hand;");

        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(addButton, removeButton);
        
        // Add the addButton and the logic, increase in 1 unit the product and updates price
        addButton.setOnAction(event -> {
            Product product = getItem();
            if (product != null) {
                product.increaseQuantity();
                
                controller.updateTotalPrice(product.getPrice());
                
                updateItem(product, isEmpty());
            }
        });

        // Add the removeButton and the logic, decrease in 1 unit and updates price
        removeButton.setOnAction(event -> {
            Product product = getItem();
            if (product != null) {
                product.decreaseQuantity();

                if (product.getQuantity() == 0) {
                    getListView().getItems().remove(product);
                    controller.updateTotalPrice(-(product.getPrice()));
                } else {
                    controller.updateTotalPrice(-(product.getPrice()));
                    
                    updateItem(product, isEmpty());
                }
            }
        });

    }

    // This updates the item and we can see his values;
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            Label productInfo = new Label(product.getName() + "   " + product.getQuantity() + "   " + String.format("%.2f", Math.abs(product.getTotal())) + "€");
            productInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            hbox.getChildren().setAll(productInfo, addButton, removeButton);
            setGraphic(hbox);
        }
   }
}


