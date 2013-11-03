package junit.org.rapidpm.demo.jaxenter.blog0006.tableviewdemo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by Sven Ruppert on 02.11.13.
 */
public class TableViewSerialDemo extends Application {


    private static final int MAX_ELEMENTS = 1000000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(600);
        stage.setHeight(550);

        final Label label = new Label("EUR/Dollar/percentage");
        label.setFont(new Font("Arial", 20));

        final TableView<CurrencyValue> table = new TableView<CurrencyValue>();
        table.setEditable(true);

        TableColumn<CurrencyValue, Double> euroCol = new TableColumn<CurrencyValue, Double>("Euro");
        euroCol.setMinWidth(200);
        euroCol.setCellValueFactory(new PropertyValueFactory<CurrencyValue, Double>("euro"));

        TableColumn<CurrencyValue, Double> dollarCol = new TableColumn<CurrencyValue, Double>("Dollar");
        dollarCol.setMinWidth(200);
        dollarCol.setCellValueFactory(new PropertyValueFactory<CurrencyValue, Double>("dollar"));

        TableColumn<CurrencyValue, Double> percentageCol = new TableColumn<CurrencyValue, Double>("Percentage");
        percentageCol.setMinWidth(200);
        percentageCol.setCellValueFactory(new PropertyValueFactory<CurrencyValue, Double>("percentage"));

        table.getColumns().addAll(Arrays.asList(euroCol, dollarCol, percentageCol));


        final TextField tfUmrechnungsfaktor = new TextField("1.34");
        tfUmrechnungsfaktor.setMaxWidth(dollarCol.getPrefWidth());
        tfUmrechnungsfaktor.setPromptText("Umrechnungsfaktor");

        final Button addButton = new Button("Add");
        final HBox hb = new HBox(3);
        hb.getChildren().addAll(tfUmrechnungsfaktor, addButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        final Random random = new Random();

        final List<CurrencyValue> list = Stream
                .generate(() -> {
                    final Double aDouble = Double.valueOf(tfUmrechnungsfaktor.getText());
                    return new CurrencyValue().changeUmrechnungsFaktor(aDouble).addEUR((double) random.nextInt(99999));
                })
                .limit(MAX_ELEMENTS)
                .collect(Collectors.toList());

        table.getItems().addAll(list);






        //G*p=P*100 -> p=P*100/G
        final Consumer<? super CurrencyValue> action = v -> {
            final Double aDouble = Double.valueOf(tfUmrechnungsfaktor.getText());
            v.changeUmrechnungsFaktor(aDouble);
            v.addUSD(v.getEuro() * v.getUmrechnungsfaktor());
            v.addPerc(v.getDollar() * 100 / v.getEuro());
        };
        table.getItems()
                .parallelStream()
                .forEach(action);



        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                final ObservableList<CurrencyValue> tableItems = table.getItems();
                for (final CurrencyValue v : tableItems) {
                    final Double aDouble = Double.valueOf(tfUmrechnungsfaktor.getText());
                    v.changeUmrechnungsFaktor(aDouble);
                    v.addUSD(v.getEuro() * v.getUmrechnungsfaktor());
                    v.addPerc(v.getDollar() * 100 / v.getEuro());
                }


                System.out.println("table.getItems().size() = " + tableItems.size());
            }
        });

        stage.show();
    }
};
