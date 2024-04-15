package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DatosControlador implements Initializable {


    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label idEstatico;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    Usuario usuario = sesion.getUsuario();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarUsuario();
        idEstatica();
    }

    private void inicializarUsuario() {
        usuario = Sesion.getInstancia().getUsuario();
    }

    public void idEstatica() {
        if (usuario != null) {
            idEstatico.setText(usuario.getNumeroIdentificacion());
        }
    }

    @FXML
    public void actualizar() {
        try {
            banco.actualizarUsuario(txtNombre.getText(), txtDireccion.getText(), txtIdentificacion.getText(), txtCorreo.getText(), txtPassword.getText());
            mostrarAlerta("Datos del usuario actualizados correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }
}
