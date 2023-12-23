package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class MainSceneController {
    @FXML
    private Label totalPriceLabel; 
    public double totalPrice = 0;
    private static MainSceneController instance;
    private String ticket;
    @FXML
    private TextArea ticketPrint;
    
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
    
    // With this method we create the Ticket
    public void createTicket() {
    	if(totalPrice >= 0) {
    		ticket = "";
    		ticket += "                                KJ BAR\n";
    		for (Product product : observableProductsList) {
    			if (product.getQuantity() > 0) {
    				ticket += "\n" + product.getName() + " " + product.getPrice() + "€   " + product.getQuantity() + " UNIDADES   " + product.getTotal() + "€\n";
    			}
    		}
	    
    	ticket += "\n\n                       PRECIO SIN IVA: " + String.format("%.2f", Math.abs((totalPrice-(totalPrice * 0.21)))) + "\n                    +IVA 21%: " + String.format("%.2f", Math.abs(totalPrice * 0.21));
	    ticket += "\n\n                       TOTAL: " + String.format("%.2f", Math.abs(totalPrice));
	    
	    ticketPrint.setText(ticket);
    	} else 
    		ticketPrint.setText("");
    }
    
    // With this method we create a PDF of the ticket and we get the PDF in the out directory
    public void toPDF() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            File fontFile = new File("src/application/font/times.ttf");
            PDType0Font font = PDType0Font.load(document, fontFile);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            float y = 700; 
            float leading = 14;

            String[] lines = ticket.split("\\n"); // Divides the string by lines

            for (String line : lines) {
                contentStream.beginText();
                contentStream.newLineAtOffset(25, y);
                contentStream.showText(line);
                contentStream.endText();
                y -= leading; // Moves down to the next line
            }

            contentStream.close();

            document.save("src/application/out/output.pdf"); // Save the document like output.pdf
            System.out.println("PDF creado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}