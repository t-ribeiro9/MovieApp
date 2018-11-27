package Controllers;

import Utils.FileManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainController implements Initializable {
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FileManager.updateFile();
        } catch (IOException ex) {
        }
        if (!FileManager.checkFilesExist()){
            //Appear nothing
        } else {
            //Appear movies
        }
    }    
}
