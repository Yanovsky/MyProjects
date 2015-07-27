package ru.alex.phonebook.fx;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ezvcard.VCard;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ru.alex.phonebook.visual.PhoneBookModel;
import ru.logging.LoggingUtils;

public class PhoneBookMain {

    @FXML
    private TableView<VCard> table;
    @FXML
    private TextField edtPhoneBookFile;

    private PhoneBookModel model = new PhoneBookModel();

    @FXML
    private void actOpenBook() {
        System.out.println(LoggingUtils.logMethodInvoke(this.getClass(), "actOpenBook"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть книгу");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Все файлы", "*.*"), new ExtensionFilter("Файлы записных книг (*.vcf)", "*.vcf"));
        fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(1));
        File file = fileChooser.showOpenDialog(PhoneBookLauncher.primaryStage);
        if (file != null)
        try {
            model.setPhoneBookFile(file);
            edtPhoneBookFile.setText(file.getAbsolutePath());
            table.setItems(FXCollections.observableArrayList(model.getPhoneBook()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMouseClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Optional.ofNullable(table.getSelectionModel().getSelectedItem()).ifPresent(card -> {
                System.out.println(model.getAbonentName(card));
            });
        }
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {
        System.out.println(LoggingUtils.logMethodInvoke(this.getClass(), "initialize"));

        TableColumn<VCard, String> columnName = new TableColumn<VCard, String>(model.getColumnName(0));
        columnName.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(model.getAbonentName(cellData.getValue())));

        TableColumn<VCard, String> columnPhone = new TableColumn<VCard, String>(model.getColumnName(1));
        columnPhone.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(model.getFxPhoneNumbers(cellData.getValue())));
        
        TableColumn<VCard, Labeled> columnImage = new TableColumn<VCard, Labeled>(model.getColumnName(2));
        columnImage.setMaxWidth(100);
        columnImage.setMinWidth(100);
        columnImage.setCellValueFactory(cellData -> new SimpleObjectProperty<Labeled>(this.getImageCell(cellData)));

        table.getColumns().addAll(columnName, columnPhone, columnImage);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private Labeled getImageCell(CellDataFeatures<VCard, Labeled> cellData) {
        ImageView image = new ImageView(model.getFxPhoto(cellData.getValue()));
        Labeled result = new Label();
        result.setMaxHeight(Double.MAX_VALUE);
        result.setGraphic(image);
        result.setAlignment(Pos.CENTER);
        result.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return result;
    }

}
