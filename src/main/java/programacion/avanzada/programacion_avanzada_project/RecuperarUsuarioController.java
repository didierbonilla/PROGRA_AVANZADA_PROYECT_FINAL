package programacion.avanzada.programacion_avanzada_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import programacion.avanzada.programacion_avanzada_project.models.UsuarioModel;
import programacion.avanzada.programacion_avanzada_project.repositories.UsuariosRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class RecuperarUsuarioController implements Initializable, LayoutScene{

    private Connection conn;
    private MainController mainController;
    private UsuariosRepository UsuariosRepo;
    private int codigoVerificacion;
    private UsuarioModel UserData;

    @FXML
    private AnchorPane frmCambiarClave;

    @FXML
    private PasswordField txtClave;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblNombre;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Button btnVerificarCodigo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();
        this.UsuariosRepo = new UsuariosRepository(this.conn);

        frmCambiarClave.setVisible(false);
        btnVerificarCodigo.setVisible(false);
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void enviarCorreo(){
        if(txtEmail.getText().isEmpty()){
            Alertas.error("Campo requerido","Ingresa un correo valido...");
            return;
        }

        this.UserData = this.UsuariosRepo.filterByEmail(txtEmail.getText());
        if(UserData == null){
            Alertas.error("Error de Autenticacion","no existe una cuenta vinculada al correo");
            return;
        }
        lblUsuario.setText("Usuario: "+UserData.getUsuario());
        lblNombre.setText("Nombre: "+UserData.getNombre());

        this.generarCodigo();

        String title = "VERIFICACION DE CUENTA";
        String body = "<h3>Programacion Avanzada - Proyecto Final</h3>";
        body += "<center>Tu codigo de verificacion es: "+this.codigoVerificacion+"</center>";
        boolean status = EmailServer.sendEmail(title, body, txtEmail.getText());
        if(status){
            btnVerificarCodigo.setVisible(true);
        }
    }

    public void generarCodigo() {
        this.codigoVerificacion = (int)(Math.random() * 900000) + 100000;
    }

    public void verificarCodigo() {
        boolean valido = String.valueOf(this.codigoVerificacion).equalsIgnoreCase(txtCodigo.getText());
        if(valido){
            frmCambiarClave.setVisible(true);
        }else{
            Alertas.error("Error de Autenticacion","Codigo incorrecto...");
        }
    }

    public void guardarClave() {
        this.UserData.setClave(txtClave.getText());
        boolean status = this.UsuariosRepo.update(this.UserData);
        if(status){
            Alertas.success("Autenticacion Completa!","Clave reseteada y guardada con exito!");
            this.redirecToLogin();
        }
    }

    public void redirecToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVerificarCodigo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            Alertas.error("Error Interno","No se pudo redirigir a el escenario [Login]");
        }
    }

}
