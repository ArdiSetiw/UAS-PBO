/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package program;


import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ardi Setyiawan
 */
public class AdminPageController implements Initializable {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Label lblUsername;
    @FXML
    private ImageView imgProfile;
    @FXML
    private ImageView btnClose;
    private Button btnHome;
    @FXML
    private Button btnDataObat;
    private AnchorPane panelHome;
    @FXML
    private AnchorPane panelData;
    private GridPane grid;
    @FXML
    private VBox panelFormTambah;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtHarga;
    @FXML
    private RadioButton rbKering;
    @FXML
    private ToggleGroup jenisObat;
    @FXML
    private RadioButton rbBerdahak;
    @FXML
    private TextField txtStok;
    @FXML
    private ComboBox<String> cbBahan;
    @FXML
    private ImageView imgItem;
    @FXML
    private Button btnPilihGambar;
    @FXML
    private Button btnAdd;
    @FXML
    private AnchorPane btnBack;
    @FXML
    private AnchorPane panelTambah;
    
    private ObservableList<Obat> obatList;
    @FXML
    private TableView<Obat> tvObat;
    @FXML
    private TableColumn<Obat, String> cNama;
    @FXML
    private TableColumn<Obat, String> cHarga;
    @FXML
    private TableColumn<Obat, String> cJenis;
    @FXML
    private TableColumn<Obat, String> cStok;
    @FXML
    private TableColumn<Obat, String> cBahan;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnHapus;
    @FXML
    private TextField txtSearch;
    
    String search = "";
    @FXML
    private AnchorPane panelEdit;
    @FXML
    private VBox panelFormTambah1;
    @FXML
    private TextField txtNamaEdit;
    @FXML
    private TextField txtHargaEdit;
    @FXML
    private RadioButton rbKeringEdit;
    @FXML
    private ToggleGroup jenisObatEdit;
    @FXML
    private RadioButton rbBerdahakEdit;
    @FXML
    private TextField txtStokEdit;
    @FXML
    private ComboBox<String> cbBahanEdit;
    @FXML
    private ImageView imgItemEdit;
    @FXML
    private Button btnPilihGambarEdit;
    @FXML
    private Button btnUpdate;
    @FXML
    private AnchorPane btnBack1;
    @FXML
    private Button btnTambahObat;
    @FXML
    private AnchorPane btnLogout;
    @FXML
    private Button btnRiwayat;
    @FXML
    private AnchorPane panelRiwayat;
    @FXML
    private TableView<riwayat> tvRiwayat;
    @FXML
    private TableColumn<riwayat, String> cNamaRiwayat;
    @FXML
    private TableColumn<riwayat, Integer> cTotalRiwayat;
    @FXML
    private TableColumn<riwayat, String> cTanggalRiwayat;
    
    public void loadList() throws SQLException{
        if (obatList != null){
            obatList.clear();
        }
        
        connect = Database.connectDB();
        String SQL = "SELECT * FROM tbobat where nama like '%" + search + "%' or jenis like '%" + search + "%' or bahan like '%" + search + "%'";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        this.obatList = FXCollections.observableArrayList();
        while(result.next()){
            Obat obat = new Obat(result.getString("nama"),
                                result.getDouble("harga"),
                                result.getString("jenis"),
                                result.getInt("stok"),
                                result.getString("bahan"),
                                result.getString("obatPic"));
            
            obatList.add(obat);
        }
        
        cNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cHarga.setCellValueFactory(new PropertyValueFactory<> ("harga"));
        cJenis.setCellValueFactory(new PropertyValueFactory<> ("jenis"));
        cStok.setCellValueFactory(new PropertyValueFactory<> ("stok"));
        cBahan.setCellValueFactory(new PropertyValueFactory<> ("bahan"));

        tvObat.setItems(obatList);
    }
    
    public void setUsername(String user) {
        lblUsername.setText(user);
    }
    
    public void setImgProfile(String lokasi) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(lokasi));
        imgProfile.setImage(image);
    }
    
    public void setBgBtnHome(){
        btnTambahObat.setStyle("-fx-background-color: #019786; ");
        btnDataObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnRiwayat.setStyle("-fx-background-color: #1FAF9F; ");
    }
    
    public void setBgBtnDataObat(){
        btnTambahObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnDataObat.setStyle("-fx-background-color: #019786; ");
        btnRiwayat.setStyle("-fx-background-color: #1FAF9F; ");
    }
    
    public void setBgBtnRiwayat(){
        btnTambahObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnDataObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnRiwayat.setStyle("-fx-background-color: #019786; ");
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            final ObservableList<String> bahan = FXCollections.observableArrayList("Antitusif", "Ekspektoran", "Antihistamin", "Dekongestan", "Kombinasi");
            cbBahan.setItems(bahan);
            cbBahanEdit.setItems(bahan);
            loadList();

          
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnClose_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void btnTambahObat_Clicked(MouseEvent event) {
        panelData.setVisible(false);
        panelTambah.setVisible(true);
        panelEdit.setVisible(false);
        panelRiwayat.setVisible(false);
        setBgBtnHome();
        clearTambah();
    }

    @FXML
    private void btnDataObat_Clicked(MouseEvent event) {
        panelData.setVisible(true);
        panelTambah.setVisible(false);
        panelEdit.setVisible(false);
        panelRiwayat.setVisible(false);
        setBgBtnDataObat();
    }
    
    @FXML
    private void btnRiwayat_Clicked(MouseEvent event) throws SQLException {
        panelData.setVisible(false);
        panelTambah.setVisible(false);
        panelEdit.setVisible(false);
        panelRiwayat.setVisible(true);
        setBgBtnRiwayat();
        loadRiwayat();
    }
    
    private File fileTerpilih;
    private File fileTerpilihEdit;
    @FXML
    private void btnPilihGambar_Clicked(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Pilih Gambar");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        
        fileTerpilih = fc.showOpenDialog(stage);
        
        if (fileTerpilih != null){
            String path = fileTerpilih.getAbsolutePath();
            Image image = new Image(path);
            imgItem.setImage(image);
            imgItem.setFitHeight(90.0);
            imgItem.setFitWidth(90.0);
        }
    }
    
    private boolean cekInput(){
        RadioButton rb = (RadioButton)jenisObat.getSelectedToggle();
        if ("".equals(txtNama.getText())){
            return false;
        }
        if ("".equals(txtHarga.getText())){
            return false;
        }
        if ("".equals(rb.getText())){
            return false;
        }
        if (cbBahan.getValue() == ""){
            return false;
        }
        if (fileTerpilih == null){
            return false;
        }
        return true;
    }
    
    private void clearTambah(){
        txtNama.setText("");
        txtHarga.setText("");
        rbKering.setSelected(false);
        rbBerdahak.setSelected(false);
        txtStok.setText("");
        cbBahan.valueProperty().set(null);
        imgItem.setImage(null);
      
    }

    @FXML
    private void btnAdd_Clicked(MouseEvent event) throws IOException, SQLException {
        if(cekInput() == false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Gagal Menambah Data!");
            alert.setContentText("Mohon Isi Seluruh Form");
            alert.showAndWait();
            return;
        }
        
        Double harga = Double.valueOf(txtHarga.getText());
        RadioButton rb = (RadioButton)jenisObat.getSelectedToggle();
        int stok = Integer.parseInt(txtStok.getText());
        
        Obat obat = new Obat(txtNama.getText(),harga,rb.getText(),stok,cbBahan.getValue(),txtNama.getText()+".png");
        
        File dest = new File("src/gambar/obat/"+txtNama.getText()+".png");
            
        Files.copy(fileTerpilih.toPath(),dest.toPath());
        
        obat.addtodb();
        clearTambah();
        loadList();
        fileTerpilih = null;
    }

    @FXML
    private void btnBack_Clicked(MouseEvent event) {
        panelTambah.setVisible(false);
        panelEdit.setVisible(false);
        panelData.setVisible(true);
        clearTambah();
    }

    @FXML
    private void btnEdit_Clicked(MouseEvent event) {
        panelData.setVisible(false);
        panelEdit.setVisible(true);

        Obat obat = tvObat.getSelectionModel().getSelectedItem();

        txtNamaEdit.setPromptText("Nama : "+obat.getNama());

        String harga = obat.getHarga().toString();
        txtHargaEdit.setPromptText("Harga : " + harga);

        if ("Kering".equals(obat.getJenis())){
            rbKeringEdit.setSelected(true);
        }else{
            rbBerdahakEdit.setSelected(true);
        }
        
        int stok = obat.getStok();
        
        txtStokEdit.setPromptText("Stok : " + String.valueOf(stok));
        
        cbBahanEdit.valueProperty().set(obat.getBahan());
        
        Image image = new Image(System.getProperty("user.dir")+"\\src\\gambar\\obat\\" + obat.getGambar());
        imgItemEdit.setImage(image);
        imgItemEdit.setFitHeight(90.0);
        imgItemEdit.setFitWidth(90.0);
    }

    @FXML
    private void btnHapus_Clicked(MouseEvent event) throws SQLException {
        Obat obat = tvObat.getSelectionModel().getSelectedItem();
        
        obat.deleteFromDb(obat.getNama());
        
        loadList();
    }

    @FXML
    private void txtSearch_Pressed(KeyEvent event) throws SQLException {
        search = txtSearch.getText();
        loadList();
    }

    @FXML
    private void btnPilihGambarEdit_Clicked(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Pilih Gambar");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        
        fileTerpilihEdit = fc.showOpenDialog(stage);
        
        if (fileTerpilihEdit != null){
            String path = fileTerpilihEdit.getAbsolutePath();
            Image image = new Image(path);
            imgItemEdit.setImage(image);
            imgItemEdit.setFitHeight(90.0);
            imgItemEdit.setFitWidth(90.0);
        }
    }

    @FXML
    private void btnUpdate_Clicked(MouseEvent event) throws SQLException, IOException {
        int id_produk = 0;
        Obat obat = tvObat.getSelectionModel().getSelectedItem();
        
        String nama = txtNamaEdit.getText();
        Double harga = 0.0;
        int stok = 0;
        String gambar;
        
        if(nama.isEmpty()){
            nama = obat.getNama();
        }
        if("".equals(txtHargaEdit.getText())){
            harga = obat.getHarga();
        }else{
            harga = Double.valueOf(txtHargaEdit.getText());
        }
        if("".equals(txtStokEdit.getText())){
            stok = obat.getStok();
        }else{
            stok = Integer.parseInt(txtStokEdit.getText());
        }
        
        RadioButton rb = (RadioButton)jenisObatEdit.getSelectedToggle();
        
        if (fileTerpilihEdit == null){
            gambar = obat.getGambar();
        }else {
            Path imgPath = Paths.get(System.getProperty("user.dir")+"\\src\\gambar\\obat\\" + obat.getGambar());
            
            Files.delete(imgPath);

            
            File dest = new File("src/gambar/obat/"+nama+".png");

            Files.copy(fileTerpilihEdit.toPath(),dest.toPath());
            
            gambar = nama+".png";
        }
        
        String ambil_id = "SELECT id_obat FROM tbobat WHERE nama = ?";
        prepare = connect.prepareStatement(ambil_id);
        prepare.setString(1, obat.getNama());
        result = prepare.executeQuery();
        if(result.next()){
            id_produk = result.getInt("id_obat");
        }
        
        obat.updateToDb(nama, harga, rb.getText(), stok, cbBahanEdit.getValue(), gambar, id_produk);
        loadList();
        panelEdit.setVisible(false);
        panelData.setVisible(true);
    }

    @FXML
    private void btnLogout_Clicked(MouseEvent event) {
        try {
            btnLogout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root, 814, 508));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private ObservableList<riwayat> riwayatList;
    public void loadRiwayat() throws SQLException{
        if (riwayatList != null){
            riwayatList.clear();
        }
        
        connect = Database.connectDB();
        String SQL = "SELECT tbakun.nama,tbcheckout.totalHarga,tbcheckout.tanggal from tbcheckout join tbakun on tbakun.id_akun = tbcheckout.id_akun";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        this.riwayatList = FXCollections.observableArrayList();
        while(result.next()){
            riwayat rwyt = new riwayat(result.getString("nama"),
                                result.getInt("totalHarga"),
                                result.getString("tanggal"));
            
            riwayatList.add(rwyt);
        }
        
        cNamaRiwayat.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cTotalRiwayat.setCellValueFactory(new PropertyValueFactory<> ("total"));
        cTanggalRiwayat.setCellValueFactory(new PropertyValueFactory<> ("tanggal"));

        tvRiwayat.setItems(riwayatList);
    }
    

    
    
}
