package programacion.avanzada.programacion_avanzada_project;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import programacion.avanzada.programacion_avanzada_project.models.UsuarioModel;
import programacion.avanzada.programacion_avanzada_project.repositories.UsuariosRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Connection conn;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public void login(){

        Alert alert;
        UsuariosRepository resource = new UsuariosRepository(this.conn);

        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            UsuarioModel user = resource.filterByUserName(username);
            if(user == null){
                Alertas.error("Inicio de sesion fallido...","Usuario no existe");
                return;
            }

            if(!user.getClave().equals(password)){
                Alertas.error("Inicio de sesion fallido...","Usuario o Contraseña incorrecta");
                return;
            }

            this.redirectToMain(user);
        }
        catch (Exception e){
            Alertas.error("Error Interno",e.getMessage());
        }
    }

    public void redirectToMain(UsuarioModel userData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setUser(userData);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            Alertas.error("Error Interno","No se pudo redirigir a el escenario principal");
        }
    }

    public void redirecToRecuperarUsuario() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RecuperarUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            Alertas.error("Error Interno","No se pudo redirigir a el escenario [Recuperar Cuenta]");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();

        this.conn = db.getConnection();
    }
}
