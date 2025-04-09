package programacion.avanzada.programacion_avanzada_project.repositories;

import programacion.avanzada.programacion_avanzada_project.Alertas;
import programacion.avanzada.programacion_avanzada_project.models.ReservaModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservacionesRepository {
    private final Connection conn;

    public ReservacionesRepository(Connection conn) {
        this.conn = conn;
    }

    public List<ReservaModel> list() {
        List<ReservaModel> reservas = new ArrayList<>();
        String sql = "SELECT * FROM VW_RESERVACIONES";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ReservaModel r = new ReservaModel();
                r.setRESERVA_ID(rs.getInt("RESERVA_ID"));
                r.setFECHA_CREACION(rs.getDate("FECHA_CREACION").toLocalDate());
                r.setHABITACION_CODIGO(rs.getString("HABITACION_CODIGO"));
                r.setCATEGORIA_CODIGO(rs.getString("CATEGORIA_CODIGO"));
                r.setCATEGORIA_NOMBRE(rs.getString("CATEGORIA_NOMBRE"));
                r.setPISO(rs.getInt("PISO"));
                r.setNUMERO(rs.getInt("NUMERO"));
                r.setCAPACIDAD(rs.getInt("CAPACIDAD"));
                r.setDNI(rs.getString("DNI"));
                r.setNOMBRE_CLIENTE(rs.getString("NOMBRE_CLIENTE"));
                r.setFECHA_ENTRADA(rs.getDate("FECHA_ENTRADA").toLocalDate());
                r.setFECHA_SALIDA(rs.getDate("FECHA_SALIDA").toLocalDate());
                r.setPRECIO(rs.getDouble("PRECIO"));
                r.setDIAS(rs.getInt("DIAS"));
                r.setTOTAL(rs.getDouble("TOTAL"));
                r.setTIPO_PAGO(rs.getString("TIPO_PAGO"));
                r.setESTADO(rs.getString("ESTADO"));
                reservas.add(r);
            }
        } catch (SQLException e) {
            Alertas.error("Error Interno SQL",e.getMessage());
        }

        return reservas;
    }

    public boolean create(ReservaModel reserva) {
        String sql =
            "EXEC SP_TBL_RESERVAS_INSERT\n" +
            "    @HABITACION_CODIGO = ?,\n" +
            "    @FECHA_ENTRADA = ?,\n" +
            "    @FECHA_SALIDA = ?,\n" +
            "    @DIAS = ?,\n" +
            "    @DNI = ?,\n" +
            "    @NOMBRE_CLIENTE = ?,\n" +
            "    @TIPO_PAGO = ?,\n" +
            "    @PRECIO = ?,\n" +
            "    @TOTAL = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reserva.getHABITACION_CODIGO());
            stmt.setDate(2, Date.valueOf(reserva.getFECHA_ENTRADA()));
            stmt.setDate(3, Date.valueOf(reserva.getFECHA_SALIDA()));
            stmt.setInt(4, reserva.getDIAS());
            stmt.setString(5, reserva.getDNI());
            stmt.setString(6, reserva.getNOMBRE_CLIENTE());
            stmt.setString(7, reserva.getTIPO_PAGO());
            stmt.setDouble(8, reserva.getPRECIO());
            stmt.setDouble(9, reserva.getTOTAL());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Alertas.error("Ah ocurrido un error inesperado...",e.getMessage());
            return false;
        }
    }

    public boolean update(ReservaModel reserva) {
        String sql =
                "EXEC SP_TBL_RESERVAS_UPDATE\n" +
                "    @RESERVA_ID = ?,\n" +
                "    @FECHA_ENTRADA = ?,\n" +
                "    @FECHA_SALIDA = ?,\n" +
                "    @DIAS = ?,\n" +
                "    @DNI = ?),\n" +
                "    @NOMBRE_CLIENTE = ?,\n" +
                "    @TIPO_PAGO = ?,\n" +
                "    @TOTAL = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getRESERVA_ID());
            stmt.setDate(2, Date.valueOf(reserva.getFECHA_ENTRADA()));
            stmt.setDate(3, Date.valueOf(reserva.getFECHA_SALIDA()));
            stmt.setInt(4, reserva.getDIAS());
            stmt.setString(5, reserva.getDNI());
            stmt.setString(6, reserva.getNOMBRE_CLIENTE());
            stmt.setString(7, reserva.getTIPO_PAGO());
            stmt.setDouble(8, reserva.getTOTAL());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Alertas.error("Ah ocurrido un error inesperado...",e.getMessage());
            return false;
        }
    }

    public ReservaModel find(int id) {
        String sql = "SELECT * FROM VW_RESERVACIONES WHERE RESERVA_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ReservaModel r = new ReservaModel();
                r.setRESERVA_ID(rs.getInt("RESERVA_ID"));
                r.setFECHA_CREACION(rs.getDate("FECHA_CREACION").toLocalDate());
                r.setHABITACION_CODIGO(rs.getString("HABITACION_CODIGO"));
                r.setCATEGORIA_CODIGO(rs.getString("CATEGORIA_CODIGO"));
                r.setCATEGORIA_NOMBRE(rs.getString("CATEGORIA_NOMBRE"));
                r.setPISO(rs.getInt("PISO"));
                r.setNUMERO(rs.getInt("NUMERO"));
                r.setCAPACIDAD(rs.getInt("CAPACIDAD"));
                r.setDNI(rs.getString("DNI"));
                r.setNOMBRE_CLIENTE(rs.getString("NOMBRE_CLIENTE"));
                r.setFECHA_ENTRADA(rs.getDate("FECHA_ENTRADA").toLocalDate());
                r.setFECHA_SALIDA(rs.getDate("FECHA_SALIDA").toLocalDate());
                r.setPRECIO(rs.getDouble("PRECIO"));
                r.setDIAS(rs.getInt("DIAS"));
                r.setTOTAL(rs.getDouble("TOTAL"));
                r.setTIPO_PAGO(rs.getString("TIPO_PAGO"));
                r.setESTADO(rs.getString("ESTADO"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cancel(int id) {
        String sql = "UPDATE TBL_RESERVAS SET ANULADA = 'Y' WHERE RESERVA_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Alertas.error("Ah ocurrido un error inesperado...",e.getMessage());
            return false;
        }
    }
}
