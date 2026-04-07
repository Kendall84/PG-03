package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controlador principal de la interfaz de usuario JavaFX.
 * Maneja la lógica de interacción para las conversiones de moneda, distancia y temperatura.
 */
public class MainController {

    @FXML
    private TextField txtDolares;
    @FXML
    private Label lblResultadoDolares;

    @FXML
    private TextField txtKilometros;
    @FXML
    private Label lblResultadoKilometros;

    @FXML
    private TextField txtCelsius;
    @FXML
    private Label lblResultadoTemperatura;

    /**
     * Acción del botón de conversión para dólares.
     */
    @FXML
    protected void onBtnDolaresClick() {
        procesarConversion(txtDolares, lblResultadoDolares, new ConversorDolares(), " Colones");
    }

    /**
     * Acción del botón de conversión para kilómetros.
     */
    @FXML
    protected void onBtnKilometrosClick() {
        procesarConversion(txtKilometros, lblResultadoKilometros, new ConversorKilometros(), " Metros");
    }

    /**
     * Acción del botón de conversión para temperatura.
     */
    @FXML
    protected void onBtnTemperaturaClick() {
        procesarConversion(txtCelsius, lblResultadoTemperatura, new ConversorTemperatura(), " °F");
    }

    /**
     * Método genérico para procesar cualquier conversión.
     * Utiliza polimorfismo a través de la clase base Conversor.
     *
     * @param textField Campo de texto con el valor de entrada.
     * @param label Etiqueta donde se mostrará el resultado.
     * @param conversor Instancia específica del conversor a utilizar.
     * @param unidad Sufijo de la unidad de medida resultante.
     */
    private void procesarConversion(TextField textField, Label label, Conversor conversor, String unidad) {
        try {
            double valorEntrada = Double.parseDouble(textField.getText());
            conversor.setValorDeEntrada(valorEntrada);
            double resultado = conversor.convertir();

            label.setText(String.format("%.2f", resultado) + unidad);
            label.setStyle("-fx-text-fill: black;");

        } catch (NumberFormatException e) {
            label.setText("Error: Ingrese un número válido.");
            label.setStyle("-fx-text-fill: red;");
        }
    }
}
