package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.CuentaAhorros;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import co.edu.uniquindio.banco.modelo.enums.CategoriaTransaccion;
import co.edu.uniquindio.banco.controlador.observador.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TransferenciaControlador {

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private Observable observable;

    @FXML
    private ComboBox<String> categoriaCB;
    @FXML
    private TextField nCuentaTextField;
    @FXML
    private TextField montoTextField;

    private final ObservableList<String> categoriaList = FXCollections.observableArrayList("Viajes", "Facturas", "Gasolina", "Ropa", "Pago", "Otros");

    public void initialize() {
        categoriaCB.setItems(categoriaList);
    }

    public void transferir(ActionEvent event) {
        try {
            Usuario usuario = sesion.getUsuario();
            List<CuentaAhorros> cuentas = banco.consultarCuentasUsario(usuario.getNumeroIdentificacion(), usuario.getContrasena());
            CategoriaTransaccion categoria = CategoriaTransaccion.valueOf(categoriaCB.getValue().toUpperCase());
            banco.realizarTransferencia(cuentas.get(0).getNumeroCuenta(), nCuentaTextField.getText(), Float.parseFloat(montoTextField.getText()), categoria);
            observable.notificar();
            limpiarCampos();
            mostrarAlerta("La transferencia se realizó correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void limpiarCampos() {
        nCuentaTextField.clear();
        montoTextField.clear();
        categoriaCB.setValue(null);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) nCuentaTextField.getScene().getWindow();
        stage.close();
    }

    public void inicializarObservable(Observable observable) {
        this.observable = observable;
    }
}




