package programacion.avanzada.programacion_avanzada_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import programacion.avanzada.programacion_avanzada_project.models.HabitacionModel;
import programacion.avanzada.programacion_avanzada_project.models.ReservaModel;
import programacion.avanzada.programacion_avanzada_project.repositories.HabitacionesRepository;

import java.net.URL;
import java.sql.Connection;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import programacion.avanzada.programacion_avanzada_project.repositories.ReservacionesRepository;

public class GuardarReservaController implements Initializable, LayoutScene {
    private Connection conn;
    private MainController mainController;
    private int ReservaId;
    private String HabitacionId;
    private boolean EsNuevo;

    ReservacionesRepository ReservacionesRep;
    HabitacionesRepository HabitacionesRep;
    HabitacionModel DatosHabitacion;

    @FXML
    private Button btnAnularReserva;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblSubTitulo;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalDias;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtDNI;

    @FXML
    private DatePicker txtDesde;

    @FXML
    private TextField txtHabitacion;

    @FXML
    private DatePicker txtHasta;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        DBConnection db = new DBConnection();
        db.connect();
        this.conn = db.getConnection();

        this.ReservacionesRep = new ReservacionesRepository(conn);
        this.HabitacionesRep = new HabitacionesRepository(conn);

        txtDesde.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        txtDesde.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                // Bloquear fechas en "hasta" menores que "desde"
                txtHasta.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty || date.isBefore(newDate));
                    }
                });

                if (txtHasta.getValue() != null && txtHasta.getValue().isBefore(newDate)) {
                    txtHasta.setValue(null);
                }
            }

            long dias = calcularDias();
            lblTotalDias.setText(dias + " Dia(s)");

            double total = calcularTotal();
            lblTotal.setText("L "+total);
        });

        txtHasta.valueProperty().addListener((obs, oldDate, newDate) -> {
            long dias = calcularDias();
            lblTotalDias.setText(dias + " Dia(s)");

            double total = calcularTotal();
            lblTotal.setText("L "+total);
        });

        txtDesde.getEditor().setDisable(true);
        txtDesde.getEditor().setOpacity(1);

        txtHasta.getEditor().setDisable(true);
        txtHasta.getEditor().setOpacity(1);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setForm(boolean esNuevo, int reservaId, String habitacionId) {
        this.EsNuevo = esNuevo;
        this.ReservaId = reservaId;

        if(this.EsNuevo){
            btnAnularReserva.setVisible(false);
            lblSubTitulo.setText("Creando nueva Reservacion");
            rellenarDatosHabitacion(habitacionId);
        }else{
            btnAnularReserva.setVisible(true);
            lblSubTitulo.setText("Editando Reservacion # " + this.ReservaId);
            rellenarDatosReserva();
        }
    }

    public void rellenarDatosHabitacion(String habitacionId){

        this.HabitacionId = habitacionId;
        //HabitacionModel item = HabitacionesRep.find(habitacionId);
        this.DatosHabitacion = HabitacionesRep.find(habitacionId);

        txtHabitacion.setText(this.DatosHabitacion.getHABITACION_CODIGO());
        txtCategoria.setText(this.DatosHabitacion.getCATEGORIA_NOMBRE());
        txtPrecio.setText("L. "+this.DatosHabitacion.getPRECIO());
        txtCapacidad.setText(this.DatosHabitacion.getCAPACIDAD()+" Persona(s)");
        lblPrecio.setText("L. "+this.DatosHabitacion.getPRECIO());
    }

    public void rellenarDatosReserva(){

        ReservaModel item = this.ReservacionesRep.find(this.ReservaId);
        this.rellenarDatosHabitacion(item.getHABITACION_CODIGO());

        txtDesde.setValue(item.getFECHA_ENTRADA());
        txtHasta.setValue(item.getFECHA_SALIDA());
        txtDNI.setText(item.getDNI());
        txtNombre.setText(item.getNOMBRE_CLIENTE());
        lblPrecio.setText("L "+item.getPRECIO());
        lblTotalDias.setText(item.getDIAS() + " Dia(s)");
        lblTotal.setText("L "+item.getTOTAL());
    }

    public void guardarDatosReserva(){

        if(this.DatosHabitacion == null){
            Alertas.error("Campo Requerido","Ah ocurrido un error cargando los datos de la habitacion...");
        } else if(txtDNI.getText().isEmpty()){
            Alertas.error("Campo Requerido","Campo [DNI Cliente] es requerido");
        } else if(txtNombre.getText().isEmpty()){
            Alertas.error("Campo Requerido","Campo [Nombre Cliente] es requerido");
        } else if(txtDesde.getValue() == null){
            Alertas.error("Campo Requerido","Campo [Reservar Desde] es requerido");
        } else if(txtHasta.getValue() == null){
            Alertas.error("Campo Requerido","Campo [Reservar Hasta] es requerido");
        } else if(this.calcularDias() <= 0){
            Alertas.error("Campo Requerido","Campo [EL lapso de dias debe ser mayor/igual a 1] es requerido");
        } else {

            ReservaModel data = new ReservaModel(
                this.HabitacionId,
                txtDNI.getText(),
                txtNombre.getText(),
                txtDesde.getValue(),
                txtHasta.getValue(),
                DatosHabitacion.getPRECIO(),
                (int) this.calcularDias(),
                this.calcularTotal(),
                "CONTADO"
            );

            boolean status;
            if (this.EsNuevo) {
                status = ReservacionesRep.create(data);
            } else {
                data.setRESERVA_ID(this.ReservaId);
                status = ReservacionesRep.update(data);
            }

            if(status){
                Alertas.success("Exito!","Reservacion guardada con exito");
                this.mainController.redirecToReservaciones();
            }
        }
    }

    public void anularReserva(){
        boolean status = this.ReservacionesRep.cancel(this.ReservaId);
        if(status){
            Alertas.error("Exito!","Reservacion cancelada con exito");
            mainController.redirecToReservaciones();
        }
    }
    public void volver(){
        if(this.EsNuevo) {
            mainController.redirectToHabitaciones(this.DatosHabitacion.getCATEGORIA_CODIGO());
        } else{
            mainController.redirecToReservaciones();
        }
    }

    public long calcularDias() {
        if (txtDesde.getValue() != null && txtHasta.getValue() != null) {
            return ChronoUnit.DAYS.between(txtDesde.getValue(), txtHasta.getValue());
        }
        return 0;
    }

    public double calcularTotal(){
        long dias = calcularDias();
        double precio = DatosHabitacion.getPRECIO();

        return dias * precio;
    }
}
