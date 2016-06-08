package morse;


import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
Class to create animation of English to Morse encoding
*/
public class MorseAnimation 
{
    private final Stage stage;
    private final double stageWidth = 500;
    private final double stageHeight = 300;
    private final ArrayList<MorseSymbol> alSymbols;
         
    private int counter = 0; //counter variable for animation
    
    //Single arg constructor takes ArrayList of MorseSymbols as argument
    public MorseAnimation(ArrayList<MorseSymbol> alSymbols)
    {
        this.alSymbols = alSymbols;
        
        BorderPane root = new BorderPane();
        root.setCenter(createFlowPane());
        root.setBottom(createButtonPane());        
        
       
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        stage = new Stage();
        stage.setX(screenBounds.getMinX() + screenBounds.getWidth() - stageWidth);
        stage.setY(screenBounds.getMinY() + screenBounds.getHeight() - stageHeight);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);        
        
        stage.setTitle("Morse Code Animation");
        stage.setScene(new Scene(root));
    }
    
    private FlowPane createFlowPane()
    {
        //Flow pane to display morse symbols
        FlowPane fpSymbol = new FlowPane(10, 10);
        fpSymbol.setAlignment(Pos.CENTER);
        fpSymbol.setPrefWrapLength(stageWidth - 100);
        
        //Adds every symbol in ArrayList to ObservableList property in FlowPane
        for (MorseSymbol symbol: alSymbols)
        {           
            fpSymbol.getChildren().add(symbol);
        }
        return fpSymbol;
    }
    
    private HBox createButtonPane()
    {
        Button btnPlay = new Button("Play");
        /*
        When variable counter equals variable i, then that symbol "lights up" 
        and its playSound method is called.  Otherwise symbol fill is set to BLACK
        */
        btnPlay.setOnAction(e ->
        {
            counter = 0;
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), ev ->
            {
                for (int i = 0; i < alSymbols.size(); i++)
                {
                    MorseSymbol symbol = alSymbols.get(i);
                    if (counter == i)
                    {
                        symbol.setSymbolFill(Color.BLUE);
                        symbol.playSound();
                    }
                    else
                    {
                        symbol.setSymbolFill(Color.BLACK);
                    }                        
                }             
                counter++;                
            }));
            timeline.setCycleCount(alSymbols.size());
            timeline.play();            
        });
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.CENTER);
        hbButton.setPadding(new Insets(0, 0, 25, 0));
        hbButton.getChildren().add(btnPlay);        
        return hbButton;
    }
    
    public void showStage()
    {
        stage.show();
    }
}
