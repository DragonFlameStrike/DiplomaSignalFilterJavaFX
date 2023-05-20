module com.example.signalfilterjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.psambit9791.jdsp;


    opens com.example.signalfilterjavafx to javafx.fxml;
    exports com.example.signalfilterjavafx;
    exports com.example.signalfilterjavafx.DataPrep;
    opens com.example.signalfilterjavafx.DataPrep to javafx.fxml;
    exports com.example.signalfilterjavafx.Filters;
    opens com.example.signalfilterjavafx.Filters to javafx.fxml;
    exports com.example.signalfilterjavafx.Data;
    opens com.example.signalfilterjavafx.Data to javafx.fxml;
    exports com.example.signalfilterjavafx.GUI;
    opens com.example.signalfilterjavafx.GUI to javafx.fxml;
}