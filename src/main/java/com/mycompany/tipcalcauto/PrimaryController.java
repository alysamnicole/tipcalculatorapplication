package com.mycompany.tipcalcauto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    @FXML private TextField amountTextField;
    @FXML private TextField tipTextField;
    @FXML private TextField totalTextField;
    @FXML private Label tipPercentageLabel;
    @FXML private Slider tipPercentageSlider;

    private final ObjectProperty<BigDecimal> tipPercentage = new SimpleObjectProperty<>(BigDecimal.valueOf(0.15));
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private final NumberFormat percent = NumberFormat.getPercentInstance();

    public void initialize() {
        currency.setRoundingMode(RoundingMode.HALF_UP);

        // Bind tip percentage label to slider
        tipPercentageLabel.textProperty().bind(
            Bindings.createStringBinding(() ->
                "Custom Tip: " + percent.format(tipPercentageSlider.getValue() / 100.0),
                tipPercentageSlider.valueProperty()
            )
        );

        // Update tipPercentage when slider changes
        tipPercentageSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            tipPercentage.set(BigDecimal.valueOf(newVal.doubleValue() / 100.0));
            calculate(); // recalculate
        });

        // Listener for amount text field changes
        amountTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            calculate(); // recalculate
        });
    }

    private void calculate() {
        try {
            BigDecimal amount = new BigDecimal(amountTextField.getText());
            BigDecimal tip = amount.multiply(tipPercentage.get());
            BigDecimal total = amount.add(tip);

            tipTextField.setText(currency.format(tip));
            totalTextField.setText(currency.format(total));
        } catch (NumberFormatException ex) {
            tipTextField.setText("");
            totalTextField.setText("");
        }
    }

    @FXML
    private void handleCalculate() {
        calculate();
    }
}

