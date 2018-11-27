package Controllers;

import Models.MainStorage;
import Models.Movie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MovieController implements Initializable {
    
    //Current movie
    private Movie movie;
    
    //Buttons
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRandom;
    
    //Labels
    @FXML
    private Label lblMovieTitle;
    @FXML
    private Label lblCategories;
    
    //Checkbox
    @FXML
    private CheckBox checkBoxSeen;
    
    //Cover
    @FXML
    private ImageView imgViewCover;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image cover = new Image(movie.getCover().toURI().toString());
        imgViewCover.setImage(cover);
        checkBoxSeen.setSelected(movie.getSeen());
        lblMovieTitle.setText(movie.getTitle());
        lblCategories.setText(movie.getAllCategories());
        
    }    

    @FXML
    private void handleBackBtn(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Movie App");
            stage.setScene(new Scene(root1));
            stage.show();
          } catch (Exception e){
              
          }
    }

    @FXML
    private void handleRandomBtn(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MovieScene.fxml"));
            Movie randomMovie = MainStorage.getRandomMovie();
            ((MovieController) fxmlLoader.getController()).setMovie(randomMovie);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(randomMovie.getTitle());
            stage.setScene(new Scene(root1));
            stage.show();
          } catch (Exception e){
              
          }
    }

    @FXML
    private void handleSeen(ActionEvent event) {
        movie.setSeen(checkBoxSeen.isSelected());
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
}
