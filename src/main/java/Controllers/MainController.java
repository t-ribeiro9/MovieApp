package Controllers;

import Models.MainStorage;
import Models.Movie;
import Utils.FileManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class MainController implements Initializable {

    @FXML
    private ListView<Movie> moviesListView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FileManager.updateFile();
            moviesListView.setItems(FXCollections.observableList(new ArrayList<>(MainStorage.getMovies())));
        } catch (IOException ex) {
        }
        if (!FileManager.checkFilesExist()){
            //Appear nothing
        } else {
            //Appear movies
        }
    }    
}
