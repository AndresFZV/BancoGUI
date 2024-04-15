package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

/**
 * Clase que representa el controlador de la vista de login
 * @author caflorezvi
 */
public class LoginControlador {

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    Usuario usuario = sesion.getUsuario();


    @FXML
    private TextField cedulaTextField;
    @FXML
    private PasswordField passwordField;

    public void irPanelCliente(ActionEvent actionEvent){
        if (!cedulaTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            try {
                Usuario usuario = banco.validarUsuario(cedulaTextField.getText(), passwordField.getText() );
                Sesion sesion = Sesion.getInstancia();
                sesion.setUsuario(usuario);
                navegarVentana("/panelCliente.fxml", "Banco - Panel de administración");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al iniciar sesión");
                alert.setContentText("Sus datos de acceso son incorrectos.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar sesión");
            alert.setContentText("Todos los campos son obligatorios para continuar.");
            alert.showAndWait();
        }
    }

    public void irInicio(ActionEvent actionEvent){
        navegarInicio("/inicio.fxml", "Banco");
    }
    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void navegarInicio(String nombreArchivoFxml, String tituloVentana){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}