package ucr.algoritmos.tarea01;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private ComboBox<String> progressionComboBox;

    @FXML
    private TextField countTextField;

    @FXML
    private TextArea resultTextArea;

    @FXML
    public void initialize() {
        progressionComboBox.setItems(FXCollections.observableArrayList(
                "Fibonacci (Iterativa)",
                "Fibonacci (Recursiva)",
                "Fibonacci (Memoización)",
                "Aritmética (Prueba)"
        ));
        progressionComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onGenerateButtonClick() {
        resultTextArea.clear();
        try {
            int n = Integer.parseInt(countTextField.getText());
            if (n < 1) {
                resultTextArea.setText("Por favor ingrese un número mayor a 0.");
                return;
            }

            String selection = progressionComboBox.getValue();
            StringBuilder sb = new StringBuilder();
            AtomicInteger counter = new AtomicInteger(0);

            if (selection.equals("Fibonacci (Iterativa)")) {
                FibonacciProgression fib = new FibonacciProgression();
                sb.append("Serie Fibonacci (Iterativa):\n");
                for (int i = 0; i < n; i++) {
                    sb.append(fib.nextValue()).append(i == n - 1 ? "" : ", ");
                }
            } else if (selection.equals("Fibonacci (Recursiva)")) {
                sb.append("Serie Fibonacci (Recursiva):\n");
                for (int i = 0; i < n; i++) {
                    sb.append(fibonacci(i, counter)).append(i == n - 1 ? "" : ", ");
                }
                sb.append("\n\nLlamadas recursivas: ").append(counter.get());
            } else if (selection.equals("Fibonacci (Memoización)")) {
                sb.append("Serie Fibonacci (Memoización):\n");
                Map<Integer, Long> memo = new HashMap<>();
                for (int i = 0; i < n; i++) {
                    sb.append(fibMemo(i, memo, counter)).append(i == n - 1 ? "" : ", ");
                }
                sb.append("\n\nLlamadas con memoización: ").append(counter.get());
            } else {
                ArithmeticProgression ap = new ArithmeticProgression(1, 1);
                sb.append("Progresión Aritmética:\n");
                for (int i = 0; i < n; i++) {
                    sb.append(ap.nextValue()).append(i == n - 1 ? "" : ", ");
                }
            }

            resultTextArea.setText(sb.toString());

        } catch (NumberFormatException e) {
            resultTextArea.setText("Error: La cantidad debe ser un número entero válido.");
        }
    }

    // Código proporcionado por el usuario
    public static long fibonacci(int n, AtomicInteger counter) {
        counter.incrementAndGet();
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1, counter) + fibonacci(n - 2, counter);
        }
    }

    public static long fibMemo(int n, Map<Integer, Long> memo, AtomicInteger counter) {
        counter.incrementAndGet();
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        long result = fibMemo(n - 1, memo, counter) + fibMemo(n - 2, memo, counter);
        memo.put(n, result);
        return result;
    }
}
