package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class ProductCell extends ListCell<Product> {
	MainSceneController controller = new MainSceneController();
	
    private HBox hbox = new HBox();
    private Button addButton = new Button("+");
    private Button removeButton = new Button("-");

    public ProductCell() {
        addButton.setOnAction(event -> {
            Product product = getItem();
            if (product != null) {
                product.increaseQuantity();
                
                controller.updateTotalPrice(product.getPrice());
                
                updateItem(product, isEmpty());
            }
        });

        removeButton.setOnAction(event -> {
            Product product = getItem();
            if (product != null) {
                product.decreaseQuantity();

                if (product.getQuantity() == 0) {
                    getListView().getItems().remove(product);
                } else {
                    controller.updateTotalPrice(2.0);
                    
                    updateItem(product, isEmpty());
                }
            }
        });


        hbox.getChildren().addAll(addButton, removeButton);
    }

    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            Label productInfo = new Label(product.getName() + " - Cantidad: " + product.getQuantity());
            hbox.getChildren().setAll(productInfo, addButton, removeButton);
            setGraphic(hbox);
        }
   }
}


