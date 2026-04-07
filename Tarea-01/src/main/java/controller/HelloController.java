package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador para la vista inicial, si se desea.
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("¡Bienvenido al Multi-Conversor!");
    }
}
