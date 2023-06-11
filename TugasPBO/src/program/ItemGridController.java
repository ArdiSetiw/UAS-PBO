/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package program;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
public class ItemGridController implements Initializable {

    @FXML
    private Label gridLblNama;
    @FXML
    private Label gridLblHarga;
    @FXML
    private ImageView gridImg;
    @FXML
    private AnchorPane panel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panel.setEffect(new DropShadow(10,Color.BLACK));
    }    
    
    private Obat obat;
    private MyListener myListener;
    public void setData(Obat obat,MyListener myListener) {
        
        this.obat = obat;
        this.myListener = myListener;
        double harga = obat.getHarga();
        String hargaString = Double.toString(harga);
        
        gridLblNama.setText(obat.getNama());
        gridLblHarga.setText("Rp."+hargaString);
        Image image = new Image(System.getProperty("user.dir")+"\\src\\gambar\\obat\\"+obat.getGambar());
        gridImg.setImage(image);
    }

    @FXML
    private void panel_Clicked(MouseEvent event) throws IOException {
        myListener.onClickListener(obat);
    }


    
}
