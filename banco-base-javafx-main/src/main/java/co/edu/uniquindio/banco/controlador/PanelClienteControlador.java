package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import co.edu.uniquindio.banco.modelo.Transaccion;
import co.edu.uniquindio.banco.modelo.CuentaAhorros;
import co.edu.uniquindio.banco.controlador.observador.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PanelClienteControlador implements Initializable, Observable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblCuenta;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn<Transaccion, String> tipoTr;
    @FXML
    private TableColumn<Transaccion, String> fechaTr;
    @FXML
    private TableColumn<Transaccion, String> valorTr;
    @FXML
    private TableColumn<Transaccion, String> usuarioTr;
    @FXML
    private TableColumn<Transaccion, String> categoriaTr;

    private Usuario usuario;
    private final Banco banco = Banco.getInstancia();

    public void irActualizar(ActionEvent actionEvent) {
        navegarDatos("/Datos.fxml", "Banco - Actualizar Datos");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarUsuario();
        mostrarDatosUsuario();

        tipoTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        fechaTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        valorTr.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getMonto()));
        usuarioTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        categoriaTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));
    }

    private void inicializarUsuario() {
        usuario = Sesion.getInstancia().getUsuario();
    }

    private void mostrarDatosUsuario() {
        if (usuario != null) {
            lblNombre.setText(usuario.getNombre());
            consultarCuentas();
        }
    }

    private void consultarCuentas() {
        try {
            List<CuentaAhorros> cuentas = Banco.getInstancia().consultarCuentasUsario(usuario.getNumeroIdentificacion(), usuario.getContrasena());
            if (!cuentas.isEmpty()) {
                lblCuenta.setText(cuentas.get(0).getNumeroCuenta());
                consultarTransacciones(cuentas.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consultarTransacciones(CuentaAhorros cuenta) {
        tablaTransacciones.setItems(FXCollections.observableArrayList(cuenta.getTransacciones()));
    }

    @FXML
    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        tablaTransacciones.getScene().getWindow().hide();
    }

    @FXML
    public void irTransferencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transferencia.fxml"));
            Parent root = loader.load();
            TransferenciaControlador controlador = loader.getController();
            controlador.inicializarObservable(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Banco - Realizar Transferencia");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void navegarDatos(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void consultar() {
        try {
            if (usuario != null) {
                List<CuentaAhorros> cuentas = Banco.getInstancia().consultarCuentasUsario(usuario.getNumeroIdentificacion(), usuario.getContrasena());
                if (!cuentas.isEmpty()) {
                    float saldo = cuentas.get(0).getSaldo();
                    String mensaje = "Nombre del cliente: " + usuario.getNombre() + "\n" +
                            "Número de identificación: " + usuario.getNumeroIdentificacion() + "\n" +
                            "Correo electrónico: " + usuario.getCorreoElectronico() + "\n" +
                            "Dirección: " + usuario.getDireccion() + "\n" +
                            "Saldo disponible en su cuenta: " + saldo;

                    mostrarAlerta(Alert.AlertType.INFORMATION, "Información del usuario", null, mensaje);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay cuentas de ahorros", "El usuario no tiene cuentas de ahorros");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario no válido", "No se proporcionó un usuario válido");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al mostrar información del usuario", e.getMessage());
        }
    }


    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String mensaje) {
        mostrarAlerta(tipoAlerta, titulo, null, mensaje);
    }

    @Override
    public void notificar() {
        consultarCuentas();
    }
}


