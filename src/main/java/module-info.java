module com.mycompany.tipcalcauto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.tipcalcauto to javafx.fxml;
    exports com.mycompany.tipcalcauto;
}
