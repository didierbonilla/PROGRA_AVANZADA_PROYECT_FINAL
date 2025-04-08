package programacion.avanzada.programacion_avanzada_project;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import programacion.avanzada.programacion_avanzada_project.models.CategoriaHabitacionModel;
import programacion.avanzada.programacion_avanzada_project.repositories.CategoriasHabitacionesRepository;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class CategoriasController implements Initializable, LayoutScene {
    private Connection conn;
    private MainController mainController;
    private CategoriasHabitacionesRepository CategoriasRep;

    @FXML
    private TableView<CategoriaHabitacionModel> tbl_categorias;

    @FXML
    private TableColumn<CategoriaHabitacionModel, String> tbl_categorias_col_categoria;

    @FXML
    private TableColumn<CategoriaHabitacionModel, Integer> tbl_categorias_col_disponible;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();
        this.CategoriasRep = new CategoriasHabitacionesRepository(this.conn);

        agregarColumnaBoton();

        tbl_categorias_col_categoria.setCellValueFactory(new PropertyValueFactory<>("CATEGORIA_NOMBRE"));
        tbl_categorias_col_disponible.setCellValueFactory(new PropertyValueFactory<>("HABITACIONES"));

        tbl_categorias.setItems(
            FXCollections.observableArrayList(CategoriasRep.list())
        );

        tbl_categorias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void agregarColumnaBoton() {
        TableColumn<CategoriaHabitacionModel, Void> colBtn = new TableColumn<>("ACCIÓN");

        Callback<TableColumn<CategoriaHabitacionModel, Void>, TableCell<CategoriaHabitacionModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Ver habitaciones");
            {
                btn.setOnAction(e -> {
                    CategoriaHabitacionModel data = getTableView().getItems().get(getIndex());
                    System.out.println("Categoría seleccionada: " + data.CATEGORIA_CODIGO);
                    mainController.redirectToHabitaciones(data.CATEGORIA_CODIGO);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };

        colBtn.setCellFactory(cellFactory);
        tbl_categorias.getColumns().add(colBtn);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
