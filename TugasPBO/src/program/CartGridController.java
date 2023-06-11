/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package program;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Ardi Setyiawan
 */
public class CartGridController implements Initializable {

    @FXML
    private ImageView imgItem;
    @FXML
    private Label lblNameItem;
    @FXML
    private ImageView btnKurangJumlah;
    @FXML
    private Label lblStokCart;
    @FXML
    private ImageView btnTambahJumlah;
    @FXML
    private Label lblHargaItem;
    @FXML
    private ImageView btnHapus;
    
    private Keranjang cart;
    private cartListener listener;
    @FXML
    private AnchorPane panel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panel.setEffect(new DropShadow(7,Color.BLACK));
    }    
    
    
    public void setData(Keranjang cart, cartListener listener) {
        int stok = cart.getJumlah();
        
        Double harga = Double.valueOf(stok) * cart.getHarga();
        
        this.cart = cart;
        this.listener = listener;
        
        lblNameItem.setText(cart.getNama());
        lblStokCart.setText(String.valueOf(stok));
        lblHargaItem.setText(String.valueOf(harga));
        Image img = new Image(System.getProperty("user.dir")+"\\src\\gambar\\obat\\"+cart.getNama()+".png");
        imgItem.setImage(img);
    }
    
    @FXML
    private void btnKurangJumlah_Clicked(MouseEvent event) {
        listener.cartKurang(cart);
    }

    @FXML
    private void btnTambahJumlah_Clicked(MouseEvent event) {
        listener.cartTambah(cart);
    }

    @FXML
    private void btnHapus_Clicked(MouseEvent event) {
        listener.cartHapus(cart);
    }
    
}
