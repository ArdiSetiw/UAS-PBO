/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package program;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author Ardi Setyiawan
 */
public class LoginController implements Initializable {


    @FXML
    private AnchorPane panelLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtNamaReg;
    @FXML
    private TextField txtUsernameReg;
    @FXML
    private PasswordField txtPasswordReg;
    @FXML
    private PasswordField txtKPasswordReg;
    @FXML
    private Button btnReg;
    @FXML
    private ImageView btnClose;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnOpenReg;
    @FXML
    private AnchorPane panelReg;
    @FXML
    private AnchorPane btnBack;
    @FXML
    private Button btnPilihGambar;
    @FXML
    private ImageView imgProfile;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panelLogin.setEffect(new DropShadow(10,Color.BLACK));
        panelReg.setEffect(new DropShadow(10,Color.BLACK));
    }    
    
    private void clear(){
        txtNamaReg.clear();
        txtUsernameReg.clear();
        txtPasswordReg.clear();
        txtKPasswordReg.clear();
    }
    
    @FXML
    private void btnClose_Clicked(MouseEvent event) {
        System.exit(0);
    }
    
    public FXMLLoader loader;
    
    @FXML
    private void btnLogin_Clicked(MouseEvent event) throws SQLException {
        String query = "SELECT * FROM tbakun WHERE username = ? AND password = ?";
        connect = Database.connectDB();
        
        prepare = connect.prepareStatement(query);
        prepare.setString(1, txtUsername.getText());
        prepare.setString(2, txtPassword.getText());
        result = prepare.executeQuery();
        
        if (result.next()) {
            Parent root;
            if ("Admin".equals(result.getString(2))){
                try {
                    loader = new FXMLLoader(getClass().getResource("adminPage.fxml"));
                    root = loader.load();
                    AdminPageController controller = loader.getController();
                    
                    controller.setBgBtnDataObat();
                    controller.setUsername(result.getString(2));
                    controller.setImgProfile(System.getProperty("user.dir")+"\\src\\gambar\\profile\\"+result.getString(5));
                    
                    btnLogin.getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(new Scene(root,814,508));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    loader = new FXMLLoader(getClass().getResource("userPage.fxml"));
                    root = loader.load();
                    UserPageController controller = loader.getController();
                    
                    controller.setBgBtnDataObat();
                    controller.id_akun = result.getInt("id_akun");
                    controller.setUsername(result.getString(2));
                    controller.setImgProfile(System.getProperty("user.dir")+"\\src\\gambar\\profile\\"+result.getString(5));
                    
                    btnLogin.getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(new Scene(root,814,508));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Username atau Password Salah");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnOpenReg_Clicked(MouseEvent event) {
        panelLogin.setVisible(false);
        panelReg.setVisible(true);
    }

    @FXML
    private void btnBack_Clicked(MouseEvent event) {
        panelLogin.setVisible(true);
        panelReg.setVisible(false);
    }
    
    private File fileTerpilih;
    @FXML
    private void btnPilihGambar_Clicked(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Pilih Gambar");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        
        fileTerpilih = fc.showOpenDialog(stage);
        
        if (fileTerpilih != null){
            String path = fileTerpilih.getAbsolutePath();
            Image image = new Image(path);
            imgProfile.setImage(image);
            imgProfile.setFitHeight(90.0);
            imgProfile.setFitWidth(90.0);
        }
    }
    
    private boolean cekInput(){
        if ("".equals(txtNamaReg.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Mohon Isi Seluruh Data");
            alert.showAndWait();
            txtNamaReg.requestFocus();
            return false;
        }
        if("".equals(txtUsernameReg.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Mohon Isi Seluruh Data");
            alert.showAndWait();
            txtUsernameReg.requestFocus();
            return false;
        }
        if("".equals(txtPasswordReg.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Mohon Isi Seluruh Data");
            alert.showAndWait();
            txtPasswordReg.requestFocus();
            return false;
        }
        if("".equals(txtKPasswordReg.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Mohon Isi Seluruh Data");
            alert.showAndWait();
            txtKPasswordReg.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    private void btnReg_Clicked(MouseEvent event) throws SQLException, IOException {
        if(cekInput() == false){
            return;
        }
        
        String selectQuery = "Select * from tbakun where username=?";
        connect = Database.connectDB();

        prepare = connect.prepareStatement(selectQuery);
        prepare.setString(1,txtUsernameReg.getText());
        result = prepare.executeQuery();
        
        if(result.next()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Username Telah Ada");
            alert.showAndWait();
            txtUsernameReg.requestFocus();

            return;
        }

        if (txtPasswordReg.getText().equals(txtKPasswordReg.getText())) {
            System.out.println("tes");
            String nama = txtNamaReg.getText();
            String username = txtUsernameReg.getText();
            String password = txtPasswordReg.getText();
            
            if (fileTerpilih == null) {
                fileTerpilih = new File("src/gambar/profile/default.png");
            }
            
            String extension = "";
            
            int i = fileTerpilih.getName().lastIndexOf(".");
            if (i > 0) {
                extension = fileTerpilih.getName().substring(i+1);
            }
            
            File dest = new File("src/gambar/profile/"+txtUsernameReg.getText()+"."+extension);
            
            Files.copy(fileTerpilih.toPath(),dest.toPath());
            
            String insertQuery = "Insert into tbakun(nama, username, password, profilePic) values (?,?,?,?)";
            connect = Database.connectDB();
            
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1,txtNamaReg.getText());
            prepare.setString(2,txtUsernameReg.getText());
            prepare.setString(3,txtPasswordReg.getText());
            prepare.setString(4,txtUsernameReg.getText()+"."+extension);
            
            prepare.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Registrasi Akun Berhasil");
            alert.showAndWait();
            
            panelLogin.setVisible(true);
            panelReg.setVisible(false);
            
            clear();
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Password dan Konfirmasi Password Salah");
            alert.showAndWait();
        }
    }

    
    
}
