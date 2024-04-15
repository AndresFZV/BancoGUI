package co.edu.uniquindio.banco.app;

import co.edu.uniquindio.banco.modelo.Banco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Clase que representa la aplicación de un banco y que se encarga
 * de cargar la ventana principal de la aplicación.
 * @author caflorezvi
 */



public class BancoApp extends Application {

    private Banco banco;
    public BancoApp() {
    }

    @Override
    public void start(Stage stage) throws Exception {

        banco = Banco.getInstancia();

        FXMLLoader loader = new FXMLLoader(BancoApp.class.getResource("/inicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Banco");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(BancoApp.class, args);
    }
}
