package controller;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import model.*;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    // ================= MONEDAS =================
    @FXML private HBox hboxCoins;
    @FXML private TableView<Moneda> tableCoins;
    @FXML private TableColumn<Moneda, String> colMoneda;
    @FXML private TableColumn<Moneda, Integer> colCantidad;
    @FXML private TableColumn<Moneda, Double> colMonto;
    @FXML private TableColumn<Moneda, Double> colRestante;
    @FXML private Slider sliderCoinAmount;
    @FXML private TextField txtCointValue;
    @FXML private Button btnCalcularMonedas;
    @FXML private Button btnLimpiarMonedas;
    @FXML private ListView<String> coinList;

    // ================= BINARIA =================
    @FXML private Button btnBinAnimate;
    @FXML private ListView<String> listBinSteps;
    @FXML private Button btnBinGen;
    @FXML private Slider sliderBinSize;
    @FXML private Button btnBinSearch;
    @FXML private TextField txtBinValue;
    @FXML private Canvas canvasBin;
    @FXML private Label lblBinTime;
    @FXML private Label lblBinSize;
    @FXML private Label lblBinArray;
    @FXML private Button btnBinReset;
    @FXML private Label lblBinComplexity;
    @FXML private Label lblBinResult;
    @FXML private ProgressBar progressBarBin;
    @FXML private Label lblBinComps;

    // ================= N-REINAS =================
    @FXML private Canvas canvasQueens;
    @FXML private ListView<String> listQueensSteps;
    @FXML private Slider sliderQueens;
    @FXML private Button btn4x4;
    @FXML private Button btn8x8;
    @FXML private Slider sliderSpeed;
    @FXML private Button btnResolver;
    @FXML private Button btnAnimar;
    @FXML private Button btnDetener;
    @FXML private Button btnLimpiar;
    @FXML private Label lblQueenSolutions;
    @FXML private Label lblQueenRecursive;
    @FXML private Label lblQueenConflicts;
    @FXML private Label lblQueenBacktracks;
    @FXML private Label lblQueenTime;

    // ================= MOCHILA =================
    @FXML private Canvas canvasKnapsack;
    @FXML private Label lblCapacidad;
    @FXML private Label lblPeso;
    @FXML private Label lblValor;
    @FXML private Label lblCapacidadValor;
    @FXML private Slider sliderCapacidad;
    @FXML private Button btnResolverMochila;
    @FXML private Label lblKnapsackValue;
    @FXML private ListView<String> listPasos;
    @FXML private ComboBox<String> comboPackages;
    @FXML private Button btnLimpiarMochila;

    private final SearchEngine searchEngine = new SearchEngine();
    private final ArrayPainter arrayPainter = new ArrayPainter();
    private Timeline animation;
    private int[] binArray;
    private SearchResult binResult;

    private int queenSize = 4;
    private int[] queenBoard;
    private Timeline queenTimeline;
    private long queenTime;
    private int queenCalls = 0;
    private int queenConflicts = 0;
    private int queenBacktracks = 0;
    private int queenSolutions = 0;

    private Item[] currentItems = Item.Package1();
    private Greedy.KnapsackResult knapsackResult;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCoinTab();
        setupBinTab();
        setupQueens();
        setupMochila();
    }

    // =====================================================
    // 🟡 MONEDAS
    // =====================================================
    private void setupCoinTab() {
        colMoneda.setCellValueFactory(new PropertyValueFactory<>("denominacion"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colRestante.setCellValueFactory(new PropertyValueFactory<>("restante"));

        sliderCoinAmount.setMin(1);
        sliderCoinAmount.setMax(10000);
        sliderCoinAmount.setValue(787);
        sliderCoinAmount.valueProperty().addListener((obs, oldVal, newVal) -> {
            txtCointValue.setText(String.valueOf(newVal.intValue()));
        });
        txtCointValue.setText(String.valueOf((int) sliderCoinAmount.getValue()));
        btnCalcularMonedas.setOnAction(e -> generateCoinChange());
        btnLimpiarMonedas.setOnAction(e -> {
            coinList.getItems().clear(); tableCoins.getItems().clear(); hboxCoins.getChildren().clear();
        });
    }

    private void generateCoinChange() {
        int monto;
        try { monto = Integer.parseInt(txtCointValue.getText().trim()); } catch (Exception e) { return; }
        tableCoins.getItems().clear(); coinList.getItems().clear(); hboxCoins.getChildren().clear();
        List<Moneda> resultList = Greedy.coinChange(monto);
        tableCoins.setItems(FXCollections.observableArrayList(resultList));
        List<Node> coinNodes = new ArrayList<>();
        ObservableList<String> steps = FXCollections.observableArrayList();
        for (Moneda m : resultList) {
            if (m.getCantidad() > 0) {
                VBox coin = drawCoinVisual(m);
                coinNodes.add(coin);
                hboxCoins.getChildren().add(coin);
                steps.add(String.format("%s * %d = %.0f (remaining %.0f)", m.getDenominacion(), m.getCantidad(), m.getMonto(), m.getRestante()));
            }
        }
        coinList.setItems(steps);
        animateCoins(coinNodes);
    }

    private VBox drawCoinVisual(Moneda m) {
        VBox container = new VBox(); container.setAlignment(Pos.CENTER); container.setSpacing(5);
        StackPane stack = new StackPane();
        Circle circle = new Circle(40);
        String colorHex;
        switch (m.getDenominacion()) {
            case "500": colorHex = "#FFD700"; break;
            case "100": colorHex = "#C0C0C0"; break;
            case "50":  colorHex = "#CD7F32"; break;
            case "25":  colorHex = "#A9A9A9"; break;
            case "10":  colorHex = "#8B4513"; break;
            case "5":   colorHex = "#BDB76B"; break;
            case "1":   colorHex = "#F5DEB3"; break;
            default:    colorHex = "#95A5A6";
        }
        circle.setStyle("-fx-fill: " + colorHex + "; -fx-stroke: #2C3E50; -fx-stroke-width: 2;");
        Label lblValue = new Label("₡" + m.getDenominacion());
        lblValue.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
        Circle badgeCircle = new Circle(12, Color.RED);
        Label lblQty = new Label("x" + m.getCantidad());
        lblQty.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
        StackPane badge = new StackPane(badgeCircle, lblQty);
        badge.setTranslateX(30); badge.setTranslateY(-30);
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        stack.getChildren().addAll(circle, lblValue, badge);
        container.getChildren().add(stack);
        return container;
    }

    private void animateCoins(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.setScaleX(0); node.setScaleY(0); node.setOpacity(0);
            ScaleTransition st = new ScaleTransition(Duration.millis(500), node);
            st.setToX(1.0); st.setToY(1.0); st.setDelay(Duration.millis(i * 200));
            FadeTransition ft = new FadeTransition(Duration.millis(500), node);
            ft.setToValue(1.0); ft.setDelay(Duration.millis(i * 200));
            st.play(); ft.play();
        }
    }

    // =====================================================
    // 🔵 BINARIA
    // =====================================================
    private void setupBinTab() {
        configSlider(sliderBinSize, 10, 50, 20, lblBinSize);
        btnBinGen.setOnAction(e -> generateBin());
        btnBinSearch.setOnAction(e -> runSearch(false));
        btnBinAnimate.setOnAction(e -> runSearch(true));
        btnBinReset.setOnAction(e -> resetBin());
        btnBinSearch.setDisable(true); btnBinAnimate.setDisable(true);
    }

    private void generateBin() {
        int size = (int) sliderBinSize.getValue();
        binArray = SearchEngine.generateSorted(size, 100);
        updateArrayLabel(lblBinArray, binArray);
        canvasBin.getGraphicsContext2D().clearRect(0, 0, canvasBin.getWidth(), canvasBin.getHeight());
        listBinSteps.getItems().clear(); progressBarBin.setProgress(0); lblBinResult.setText("");
        btnBinSearch.setDisable(false); btnBinAnimate.setDisable(false);
    }

    private void runSearch(boolean animate) {
        if (binArray == null) return;
        int value;
        try { value = Integer.parseInt(txtBinValue.getText()); } catch (Exception e) {
            value = binArray[new Random().nextInt(binArray.length)]; txtBinValue.setText(String.valueOf(value));
        }
        binArray = SearchEngine.ensureContains(binArray, value);
        updateArrayLabel(lblBinArray, binArray);
        binResult = searchEngine.binary(binArray, value);
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < binResult.steps.size(); i++) items.add(String.format("[%02d] %s", i + 1, binResult.steps.get(i).description));
        listBinSteps.setItems(items); updateBinStats(binResult);
        if (animate) animateSearch(binResult, binArray, canvasBin, progressBarBin, listBinSteps);
        else {
            boolean[] vis = buildVisited(binResult.steps, binArray.length, binResult.steps.size());
            arrayPainter.paint(canvasBin, binArray, binResult.steps.isEmpty() ? null : binResult.steps.getLast(), vis, binResult.foundIndex);
            progressBarBin.setProgress(1.0);
        }
    }

    private void resetBin() {
        stopAnimation(); binArray = null; binResult = null; lblBinArray.setText("[ Arreglo Binario ]");
        canvasBin.getGraphicsContext2D().clearRect(0, 0, canvasBin.getWidth(), canvasBin.getHeight());
        listBinSteps.getItems().clear(); progressBarBin.setProgress(0); lblBinResult.setText(""); txtBinValue.clear();
        btnBinSearch.setDisable(true); btnBinAnimate.setDisable(true);
    }

    private void animateSearch(SearchResult res, int[] arr, Canvas canvas, ProgressBar pb, ListView<String> lv) {
        stopAnimation(); if (res.steps.isEmpty()) return;
        int total = res.steps.size();
        int delay = Math.max(180, Math.min(800, 3000 / total));
        animation = new Timeline();
        for (int i = 1; i <= total; i++) {
            final int step = i;
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(step * delay), e -> {
                SearchResult.Step s = res.steps.get(step - 1);
                boolean[] vis = buildVisited(res.steps, arr.length, step - 1);
                int found = s.isHit ? s.index : (step == total ? res.foundIndex : -1);
                paintBinCanvas(canvas, arr, s, vis, found);
                pb.setProgress((double) step / total);
                lv.scrollTo(step - 1); lv.getSelectionModel().select(step - 1);
            }));
        }
        animation.setOnFinished(e -> pb.setProgress(1.0)); animation.play();
    }

    private void paintBinCanvas(Canvas canvas, int[] arr, SearchResult.Step step, boolean[] vis, int foundIdx) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double w = canvas.getWidth(), h = canvas.getHeight();
        gc.clearRect(0, 0, w, h);
        double barW = (w - 40) / arr.length;
        int max = Arrays.stream(arr).max().orElse(1);
        for (int i = 0; i < arr.length; i++) {
            double barH = (arr[i] / (double) max) * (h - 60);
            gc.setFill(i == foundIdx ? Color.DARKGREEN : (step != null && i == step.mid ? Color.ORANGE : Color.STEELBLUE));
            if (vis != null && vis[i] && i != foundIdx && (step == null || i != step.mid)) gc.setGlobalAlpha(0.4);
            gc.fillRect(20 + i * barW, h - barH - 20, barW - 2, barH);
            gc.setGlobalAlpha(1.0);
        }
    }

    private void stopAnimation() { if (animation != null) { animation.stop(); animation = null; } }

    private void updateBinStats(SearchResult res) {
        lblBinResult.setText(res.foundIndex >= 0 ? "Encontrado en índice " + res.foundIndex : "No encontrado");
        lblBinComps.setText(res.comparisons + ""); lblBinTime.setText(String.format("%.4f ms", res.nanoTime / 1_000_000.0));
        lblBinComplexity.setText("O(log n)");
    }

    // =====================================================
    // ♟️ N-REINAS
    // =====================================================
    private void setupQueens() {
        sliderQueens.setMin(4); sliderQueens.setMax(10); sliderQueens.setValue(4);
        sliderQueens.valueProperty().addListener((obs, o, n) -> { queenSize = n.intValue(); drawQueensBoard(new int[queenSize]); });
        btn4x4.setOnAction(e -> { queenSize = 4; sliderQueens.setValue(4); drawQueensBoard(new int[4]); });
        btn8x8.setOnAction(e -> { queenSize = 8; sliderQueens.setValue(8); drawQueensBoard(new int[8]); });
        btnResolver.setOnAction(e -> solveQueens());
        btnAnimar.setOnAction(e -> animateQueens());
        btnDetener.setOnAction(e -> stopQueenAnimation());
        btnLimpiar.setOnAction(e -> clearQueens());
        drawQueensBoard(new int[queenSize]);
    }

    private void solveQueens() {
        stopQueenAnimation(); queenBoard = new int[queenSize]; Arrays.fill(queenBoard, -1);
        queenStepList.clear(); queenCalls = 0; queenConflicts = 0; queenBacktracks = 0; queenSolutions = 0;
        long start = System.currentTimeMillis(); solveQueensBacktracking(0, queenStepList);
        queenTime = System.currentTimeMillis() - start;
        drawQueensBoard(queenBoard); updateQueenStats(); updateQueenLog();
    }

    private void animateQueens() {
        stopQueenAnimation(); if (queenStepList.isEmpty()) solveQueens();
        int delay = (int) sliderSpeed.getValue(); queenTimeline = new Timeline();
        for (int i = 0; i < queenStepList.size(); i++) {
            final int step = i;
            queenTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(step * delay), e -> {
                QueenStep s = queenStepList.get(step); drawQueensBoard(s.board); highlightQueenCell(s);
                updateQueenLogAtStep(step);
            }));
        }
        queenTimeline.play();
    }

    private void stopQueenAnimation() { if (queenTimeline != null) { queenTimeline.stop(); queenTimeline = null; } }

    private static class QueenStep {
        int[] board; String description; int row, col;
        QueenStep(int[] board, String description, int row, int col) { this.board = board.clone(); this.description = description; this.row = row; this.col = col; }
    }
    private List<QueenStep> queenStepList = new ArrayList<>();

    private boolean solveQueensBacktracking(int row, List<QueenStep> steps) {
        queenCalls++; if (row == queenSize) { queenSolutions++; return true; }
        for (int col = 0; col < queenSize; col++) {
            if (isSafe(row, col)) {
                queenBoard[row] = col;
                steps.add(new QueenStep(queenBoard.clone(), "♛ Colocar reina en fila " + row + ", col " + col, row, col));
                if (solveQueensBacktracking(row + 1, steps)) return true;
                queenBacktracks++; queenBoard[row] = -1;
                steps.add(new QueenStep(queenBoard.clone(), "✘ Backtrack en fila " + row, row, col));
            } else {
                queenConflicts++; steps.add(new QueenStep(queenBoard.clone(), "✘ Conflicto en (" + row + "," + col + ")", row, col));
            }
        }
        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queenBoard[i] == col || Math.abs(queenBoard[i] - col) == Math.abs(i - row)) return false;
        }
        return true;
    }

    private void drawQueensBoard(int[] board) {
        GraphicsContext gc = canvasQueens.getGraphicsContext2D(); double cell = canvasQueens.getWidth() / queenSize;
        gc.clearRect(0, 0, canvasQueens.getWidth(), canvasQueens.getHeight());
        for (int r = 0; r < queenSize; r++) {
            for (int c = 0; c < queenSize; c++) {
                gc.setFill((r + c) % 2 == 0 ? Color.BEIGE : Color.LIGHTGRAY); gc.fillRect(c * cell, r * cell, cell, cell);
                if (board[r] == c) {
                    gc.setFill(Color.TEAL); gc.fillOval(c * cell + cell * 0.1, r * cell + cell * 0.1, cell * 0.8, cell * 0.8);
                    gc.setStroke(Color.BLACK); gc.setLineWidth(1.5); gc.strokeOval(c * cell + cell * 0.1, r * cell + cell * 0.1, cell * 0.8, cell * 0.8);
                    gc.setFill(Color.BLACK); gc.fillOval(c * cell + cell * 0.4, r * cell + cell * 0.4, cell * 0.2, cell * 0.2);
                }
            }
        }
    }

    private void highlightQueenCell(QueenStep s) {
        GraphicsContext gc = canvasQueens.getGraphicsContext2D(); double cell = canvasQueens.getWidth() / queenSize;
        gc.setFill(s.description.contains("♛") ? Color.web("#90EE90", 0.5) : Color.web("#FFB6C1", 0.5));
        gc.fillRect(s.col * cell, s.row * cell, cell, cell);
    }

    private void clearQueens() {
        stopQueenAnimation(); queenBoard = new int[queenSize]; Arrays.fill(queenBoard, -1);
        drawQueensBoard(queenBoard); listQueensSteps.getItems().clear(); queenStepList.clear();
        queenCalls = 0; queenConflicts = 0; queenBacktracks = 0; queenSolutions = 0; queenTime = 0; updateQueenStats();
    }

    private void updateQueenStats() {
        lblQueenSolutions.setText("Soluciones: " + queenSolutions); lblQueenRecursive.setText("Llamadas: " + queenCalls);
        lblQueenConflicts.setText("Conflictos: " + queenConflicts); lblQueenBacktracks.setText("Backtracks: " + queenBacktracks);
        lblQueenTime.setText("Tiempo: " + queenTime + " ms");
    }

    private void updateQueenLog() {
        listQueensSteps.getItems().clear();
        for (int i = 0; i < queenStepList.size(); i++) listQueensSteps.getItems().add(String.format("[%04d] %s", i + 1, queenStepList.get(i).description));
    }

    private void updateQueenLogAtStep(int step) {
        listQueensSteps.getItems().clear();
        for (int i = 0; i <= step; i++) listQueensSteps.getItems().add(String.format("[%04d] %s", i + 1, queenStepList.get(i).description));
        listQueensSteps.scrollTo(step); listQueensSteps.getSelectionModel().select(step);
    }

    // =====================================================
    // 🎒 MOCHILA
    // =====================================================
    private void setupMochila() {
        comboPackages.setItems(FXCollections.observableArrayList("Package 1", "Package 2", "Package 3", "Package 4"));
        comboPackages.setValue("Package 1");
        sliderCapacidad.setMin(10); sliderCapacidad.setMax(100); sliderCapacidad.setValue(50);
        sliderCapacidad.valueProperty().addListener((obs, o, n) -> lblCapacidadValor.setText(String.valueOf(n.intValue())));
        lblCapacidadValor.setText(String.valueOf((int) sliderCapacidad.getValue()));
        btnResolverMochila.setOnAction(e -> solveKnapsack());
        btnLimpiarMochila.setOnAction(e -> clearKnapsack());
        drawKnapsackSilhouette();
    }

    private void solveKnapsack() {
        int cap = (int) sliderCapacidad.getValue();
        Item[] items = getSelectedPackage();
        knapsackResult = Greedy.knapsackResult(items, cap);
        lblCapacidad.setText("Capacidad: " + cap); lblPeso.setText("Peso total: " + knapsackResult.maxWeight);
        lblValor.setText("Valor total: " + knapsackResult.maxValue);
        lblKnapsackValue.setText(String.format("Valor óptimo: %.2f", knapsackResult.maxValue));
        animateKnapsackFilling();
        listPasos.getItems().clear(); listPasos.getItems().add("Estrategia: Mayor Ratio");
        for (Item item : knapsackResult.selected) listPasos.getItems().add("✓ " + item.getName() + " (v:" + item.getValue() + ")");
    }

    private void drawKnapsackSilhouette() {
        GraphicsContext gc = canvasKnapsack.getGraphicsContext2D(); gc.clearRect(0, 0, 400, 300);
        gc.setStroke(Color.SADDLEBROWN); gc.setLineWidth(5); gc.strokeRoundRect(50, 50, 300, 200, 30, 30);
        gc.setFill(Color.web("#8B4513", 0.1)); gc.fillRoundRect(50, 50, 300, 200, 30, 30);
    }

    private void animateKnapsackFilling() {
        drawKnapsackSilhouette(); GraphicsContext gc = canvasKnapsack.getGraphicsContext2D();
        Color[] colors = {Color.RED, Color.PINK, Color.YELLOW, Color.GREEN, Color.CYAN, Color.GRAY};
        int delay = (int) sliderSpeed.getValue(); Timeline tl = new Timeline();
        for (int i = 0; i < knapsackResult.selected.size(); i++) {
            final int idx = i; Item item = knapsackResult.selected.get(i);
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(i * delay), e -> {
                gc.setFill(colors[idx % colors.length]);
                gc.fillRect(70 + idx * 40, 230 - idx * 25, 35, 20);
                gc.setFill(Color.BLACK); gc.setFont(javafx.scene.text.Font.font(9));
                gc.fillText(item.getName().substring(0, Math.min(5, item.getName().length())), 70 + idx * 40, 225 - idx * 25);
            }));
        }
        tl.play();
    }

    private Item[] getSelectedPackage() {
        String pkg = comboPackages.getValue();
        if (pkg.equals("Package 2")) return Item.Package2();
        if (pkg.equals("Package 3")) return Item.Package3();
        if (pkg.equals("Package 4")) return Item.Package4();
        return Item.Package1();
    }

    private void clearKnapsack() {
        drawKnapsackSilhouette(); lblCapacidad.setText("Capacidad: --"); lblPeso.setText("Peso total: --");
        lblValor.setText("Valor total: --"); lblKnapsackValue.setText("Valor óptimo: 0,00"); listPasos.getItems().clear();
    }

    private void configSlider(Slider s, int min, int max, int val, Label lbl) {
        s.setMin(min); s.setMax(max); s.setValue(val);
        s.valueProperty().addListener((o, ov, nv) -> lbl.setText(String.valueOf(nv.intValue())));
        lbl.setText(String.valueOf(val));
    }

    private boolean[] buildVisited(List<SearchResult.Step> steps, int n, int upTo) {
        boolean[] vis = new boolean[n];
        for (int i = 0; i < Math.min(upTo, steps.size()); i++) {
            int idx = steps.get(i).index; if (idx >= 0 && idx < n) vis[idx] = true;
        }
        return vis;
    }

    private void updateArrayLabel(Label lbl, int[] arr) {
        if (arr == null) return; StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < arr.length; i++) sb.append(arr[i]).append(i < arr.length - 1 ? ", " : " ]");
        lbl.setText(sb.toString());
    }
}