package programacion.avanzada.programacion_avanzada_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import programacion.avanzada.programacion_avanzada_project.models.HabitacionModel;
import programacion.avanzada.programacion_avanzada_project.repositories.CategoriasHabitacionesRepository;
import programacion.avanzada.programacion_avanzada_project.repositories.HabitacionesRepository;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class HabitacionesController implements Initializable, LayoutScene {

    private Connection conn;
    private MainController mainController;
    private String Categoria;

    private HabitacionesRepository habitacionesRep;
    private CategoriasHabitacionesRepository CategoriasRep;

    @FXML
    private Label lblCategoria;

    @FXML
    private TableColumn<HabitacionModel, String> colHabitacion;

    @FXML
    private TableColumn<HabitacionModel, String> colCapacidad;

    @FXML
    private TableColumn<HabitacionModel, Double> colPrecio;

    @FXML
    private TableColumn<HabitacionModel, String> colDisponible;

    @FXML
    private TableView<HabitacionModel> tblHabitaciones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();

        this.habitacionesRep = new HabitacionesRepository(conn);
        this.CategoriasRep = new CategoriasHabitacionesRepository(conn);

        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("HABITACION_CODIGO"));
        colCapacidad.setCellValueFactory(cellData -> {
            int capacidad = cellData.getValue().getCAPACIDAD();
            return new javafx.beans.property.SimpleStringProperty(capacidad + " Persona(s)");
        });
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("PRECIO"));

        colDisponible.setCellValueFactory(cellData -> {
            boolean reservada = cellData.getValue().isReserved();
            return new javafx.beans.property.SimpleStringProperty(reservada ? "NO" : "SI");
        });

        agregarColumnaBoton();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setCategoria(String categoria) {
        this.Categoria = categoria;

        lblCategoria.setText("CATEGORIA SELECCIONADA: " + CategoriasRep.getCategoryName(categoria));
        rellenarGridHabitaciones();
    }

    public void rellenarGridHabitaciones() {
        List<HabitacionModel> lista = habitacionesRep.listByCategory(this.Categoria);
        ObservableList<HabitacionModel> data = FXCollections.observableArrayList(lista);
        tblHabitaciones.setItems(data);
    }

    private void agregarColumnaBoton() {
        TableColumn<HabitacionModel, Void> colBtn = new TableColumn<>("ACCION");

        Callback<TableColumn<HabitacionModel, Void>, TableCell<HabitacionModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Reservar");

            {
                btn.setOnAction(e -> {
                    HabitacionModel habitacion = getTableView().getItems().get(getIndex());
                    System.out.println("Reservando habitación: " + habitacion.getHABITACION_CODIGO());
                    mainController.redirectToGuardarReserva(true,0,habitacion.getHABITACION_CODIGO());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(btn));
                }
            }
        };

        colBtn.setCellFactory(cellFactory);
        tblHabitaciones.getColumns().add(colBtn);

        tblHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void volver(){
        this.mainController.redirecToCategorias();
    }
}
