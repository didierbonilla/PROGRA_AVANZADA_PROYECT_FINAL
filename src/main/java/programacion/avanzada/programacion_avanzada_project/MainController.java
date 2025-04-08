package programacion.avanzada.programacion_avanzada_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private String Username;
    private String Role;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUsuariosMenu;

    @FXML
    private AnchorPane frm_main_content;

    @FXML
    private Label lblUserName;

    public void logout() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deseas cerrar sesion?");
        alert.setHeaderText("Cerrar sesion");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get().equals(ButtonType.OK)){
            this.redirectToLogin();
        }
    }

    public void redirectToLogin() throws IOException {
        btnLogout.getScene().getWindow().hide();
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader);

        stage.setScene(scene);
        stage.show();
    }

    public void redirecToUsuarios(){cargarVista("/fxml/Usuarios.fxml");}

    public void redirecToReservaciones() {
        cargarVista("/fxml/Reservaciones.fxml");
    }

    public void redirecToCategorias() {
        cargarVista("/fxml/Categorias.fxml");
    }

    public void redirectToHabitaciones(String categoria) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Habitaciones.fxml"));
            AnchorPane vista = loader.load();

            HabitacionesController controller = loader.getController();
            controller.setCategoria(categoria);
            controller.setMainController(this);

            frm_main_content.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToGuardarReserva(boolean esNuevo, int reservaId, String HabitacionId){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GuardarReserva.fxml"));
            AnchorPane vista = loader.load();

            GuardarReservaController controller = loader.getController();
            controller.setForm(esNuevo, reservaId, HabitacionId);
            controller.setMainController(this);

            frm_main_content.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            AnchorPane vista = loader.load();

            Object controller = loader.getController();
            if (controller instanceof LayoutScene) {
                ((LayoutScene) controller).setMainController(this);
            }

            frm_main_content.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarVista("/fxml/Categorias.fxml");
    }

    public void setUsername(String Username) {
        this.Username = Username;
        lblUserName.setText(this.Username);
    }

    public void setRole(String rolename) {
        this.Role = rolename;
        btnUsuariosMenu.setVisible(this.isAdmin());
    }

    public boolean isAdmin() {
        return this.Role.equalsIgnoreCase("ADMIN");
    }
}
