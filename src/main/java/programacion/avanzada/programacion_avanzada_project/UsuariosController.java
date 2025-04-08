package programacion.avanzada.programacion_avanzada_project;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import programacion.avanzada.programacion_avanzada_project.models.UsuarioModel;
import programacion.avanzada.programacion_avanzada_project.repositories.UsuariosRepository;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable, LayoutScene {

    private Connection conn;
    private MainController mainController;
    private UsuariosRepository repo;
    private UsuarioModel selectedUser = null;

    @FXML
    private TableView<UsuarioModel> tblUsuarios;

    @FXML
    private TableColumn<UsuarioModel, String> colNombreUsuario;

    @FXML
    private TableColumn<UsuarioModel, String> colRolUsuario;

    @FXML
    private TableColumn<UsuarioModel, String> colUsuario;

    @FXML
    private TableColumn<UsuarioModel, String> colEmail;

    @FXML
    private ComboBox<String> cbbRol;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();
        this.repo = new UsuariosRepository(conn);

        btnEliminar.setDisable(true);
        cbbRol.setItems(FXCollections.observableArrayList("ADMIN", "STANDARD"));

        colUsuario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsuario()));
        colNombreUsuario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colRolUsuario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRol()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        tblUsuarios.setItems(FXCollections.observableArrayList(repo.list()));

        tblUsuarios.setOnMouseClicked((MouseEvent event) -> {
            if (tblUsuarios.getSelectionModel().getSelectedItem() != null) {
                btnEliminar.setDisable(false);
                selectedUser = tblUsuarios.getSelectionModel().getSelectedItem();
                rellenarFormulario(selectedUser);
            }else{
                btnEliminar.setDisable(true);
            }
        });

        tblUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void rellenarFormulario(UsuarioModel user) {
        txtUsuario.setText(user.getUsuario());
        txtNombre.setText(user.getNombre());
        txtPassword.setText(user.getClave());
        txtEmail.setText(user.getEmail());
        cbbRol.setValue(user.getRol());
    }

    @FXML
    public void nuevo() {
        selectedUser = null;

        txtUsuario.clear();
        txtNombre.clear();
        txtPassword.clear();
        txtEmail.clear();
        cbbRol.getSelectionModel().clearSelection();

        tblUsuarios.getSelectionModel().clearSelection();
        btnEliminar.setDisable(true);
    }

    @FXML
    public void guardar() {
        String usuario = txtUsuario.getText();
        String nombre = txtNombre.getText();
        String clave = txtPassword.getText();
        String email = txtEmail.getText();
        String rol = cbbRol.getValue();

        if (usuario.isEmpty() || nombre.isEmpty() || clave.isEmpty() || email.isEmpty() || rol == null) {
            Alertas.error("Validación", "Por favor completa todos los campos.");
            return;
        }

        UsuarioModel user = new UsuarioModel(usuario, nombre, clave);
        user.setRol(rol);
        user.setEmail(email);

        boolean result;
        if (selectedUser == null) {
            result = repo.create(user);
            if (result) Alertas.success("Éxito", "Usuario creado exitosamente");
        } else {
            user.setId(selectedUser.getId());
            result = repo.update(user);
            if (result) Alertas.success("Éxito", "Usuario actualizado exitosamente");
        }

        if (result) {
            nuevo();
            tblUsuarios.setItems(FXCollections.observableArrayList(repo.list()));
        }
    }

    @FXML
    public void eliminar() {
        if (selectedUser == null) {
            Alertas.error("Error", "Debe seleccionar un usuario para eliminar.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Usuario");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de eliminar al usuario seleccionado?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (repo.delete(selectedUser.getId())) {
                Alertas.success("Éxito", "Usuario eliminado exitosamente.");
                nuevo();
                tblUsuarios.setItems(FXCollections.observableArrayList(repo.list()));
            }
        }
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
