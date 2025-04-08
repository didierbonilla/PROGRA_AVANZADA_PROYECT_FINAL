package programacion.avanzada.programacion_avanzada_project;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import programacion.avanzada.programacion_avanzada_project.models.ReservaModel;
import programacion.avanzada.programacion_avanzada_project.repositories.ReservacionesRepository;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ReservacionesController implements Initializable, LayoutScene {

    private Connection conn;
    private MainController mainController;
    private ReservacionesRepository reservacionesRep;

    @FXML
    private TableColumn<ReservaModel, String> colCliente;

    @FXML
    private TableColumn<ReservaModel, String> colCreacion;

    @FXML
    private TableColumn<ReservaModel, Integer> colDocumento;

    @FXML
    private TableColumn<ReservaModel, String> colEstado;

    @FXML
    private TableColumn<ReservaModel, String> colHabitacion;

    @FXML
    private TableView<ReservaModel> tblReservaciones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();
        this.reservacionesRep = new ReservacionesRepository(conn);

        // Mapeo de columnas
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("RESERVA_ID"));
        colCreacion.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFECHA_CREACION() != null) {
                return new SimpleStringProperty(cellData.getValue().getFECHA_CREACION().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("HABITACION_CODIGO"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("ESTADO"));

        colCliente.setCellValueFactory(cellData -> {
            ReservaModel r = cellData.getValue();
            String cliente = r.getDNI() + "\n" + r.getNOMBRE_CLIENTE();
            return new SimpleStringProperty(cliente);
        });

        agregarColumnaEditar();
        rellenarReservaciones();
    }

    public void agregarColumnaEditar() {
        TableColumn<ReservaModel, Void> colBtn = new TableColumn<>("");
        Callback<TableColumn<ReservaModel, Void>, TableCell<ReservaModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Editar");
            {
                btn.setOnAction(e -> {
                    ReservaModel data = getTableView().getItems().get(getIndex());
                    mainController.redirectToGuardarReserva(false,data.getRESERVA_ID(),null);
                });
                btn.setStyle("-fx-font-size: 10px;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };

        colBtn.setCellFactory(cellFactory);
        tblReservaciones.getColumns().add(colBtn);

        tblReservaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void rellenarReservaciones() {
        tblReservaciones.setItems(FXCollections.observableArrayList(reservacionesRep.list()));
    }

    public void volver() {
        this.mainController.redirecToCategorias();
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
