package morse;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/*
Main form to input either Morse or English.  
Displays output if translation was successful.  Otherwise displays error message.
*/
public class MainForm extends Application 
{
    //MainForm control objects
    private TextField txtInput;
    private TextField tfOutput;
    private MorseTree morseTree;
    private final Background BACK_GROUND = new Background(new BackgroundFill
        (Color.WHITE, null, null));
    private final Font DEFAULT_FONT = Font.font(STYLESHEET_MODENA, FontWeight.NORMAL, 16);
    
    @Override
    public void start(Stage primaryStage) 
    {
        morseTree = new MorseTree();
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 200);
        
        BorderPane bPane = new BorderPane();
        bPane.setTop(createInputPane(scene));
        bPane.setCenter(createButtonPane());
        bPane.setBottom(createOutputPane(scene));
        root.setTop(createHelpPane());
        
        root.setCenter(bPane);
        
        primaryStage.setTitle("Morse Code");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    
    private Node createInputPane(Scene scene)
    {
        Label lblInput = new Label("Input: ");
        lblInput.setFont(DEFAULT_FONT);
        txtInput = new TextField();
        txtInput.setMinWidth(scene.getWidth() * .75);
        txtInput.setFont(DEFAULT_FONT);
        HBox hbInput = new HBox(10);
        hbInput.setAlignment(Pos.CENTER);
        hbInput.setPadding(new Insets(10, 0, 10, 0));
        hbInput.getChildren().addAll(lblInput, txtInput);
        return hbInput;
    }
    
    private Node createButtonPane()
    {
        Button btnEncode = new Button("Encode");
        btnEncode.setFont(DEFAULT_FONT);     
        btnEncode.setOnAction(e ->
        {
            try
            {
                tfOutput.setText("");
                tfOutput.setStyle(null);
                tfOutput.setText(morseTree.encode(txtInput.getText()));
            }
            catch (CodeableException ex) //catches exception thrown in encode
                        {
                tfOutput.setStyle("-fx-text-fill: red;");
                tfOutput.setText("Unencodeable symbols: " + ex.getMessage());
            }            
        });
        
        Button btnDecode = new Button("Decode");
        btnDecode.setFont(DEFAULT_FONT);        
        btnDecode.setOnAction(e ->
        {
            try
            {
                tfOutput.setText("");
                tfOutput.setStyle(null);
                tfOutput.setText(morseTree.decode(txtInput.getText()));               
            }
            catch (CodeableException ex)  //catches exception thrown in decode
                        {
                tfOutput.setStyle("-fx-text-fill: red;");
                tfOutput.setText("Undecodeable symbols: " + ex.getMessage());
            }            
        });
        HBox hbButton = new HBox(5);
        hbButton.setAlignment(Pos.CENTER);
        hbButton.getChildren().addAll(btnEncode, btnDecode);
        return hbButton;
    }
    
    private Node createOutputPane(Scene scene)
    {         
        Label output = new Label("Output: ");
        output.setFont(DEFAULT_FONT);
        tfOutput = new TextField();
        tfOutput.setMinWidth(scene.getWidth() * .75);
        tfOutput.setEditable(false);
        tfOutput.setFont(DEFAULT_FONT);
        tfOutput.setBackground(BACK_GROUND);
        HBox hbOutput = new HBox(10);
        hbOutput.setAlignment(Pos.CENTER);
        hbOutput.setPadding(new Insets(10, 10, 25, 10));
        hbOutput.getChildren().addAll(output, tfOutput);
        return hbOutput;
    }
    
    private Node createHelpPane()
    {
        Button btnHelp = new Button("Help");
        btnHelp.setFont(DEFAULT_FONT);
        btnHelp.setOnAction(new InstructionHandler());
        HBox hbHelp = new HBox(10);
        hbHelp.setAlignment(Pos.CENTER_RIGHT);
        hbHelp.setPadding(new Insets(10, 10, 0, 10));
        hbHelp.getChildren().add(btnHelp);
        return hbHelp;
    }
    
    //Inner class to handle button click event
    private class InstructionHandler implements EventHandler<ActionEvent>
    {
        //class variables to hold instructions
        private final String strEtoM = "English to Morse: Type phrase in plain English";
        private final String strMtoE = "Morse to English: Enter Morse code using . "
                                    + "(period) for dot and - (hyphen) for dash.\n"+
                                      "Seperate each letter with a space"+
                                       "Seperate each word with three spaces"; 
        
        @Override
        public void handle(ActionEvent e)
        {
            Stage stage = new Stage();
            BorderPane root = new BorderPane();
            
            VBox instructionPane = new VBox();
            instructionPane.setSpacing(20);
            instructionPane.setAlignment(Pos.CENTER_LEFT);
            
            Label lblEtoM = new Label(strEtoM);
            lblEtoM.setPadding(new Insets(0, 10, 10, 10));
            lblEtoM.setWrapText(true);
            Label lblMtoE = new Label(strMtoE);
            lblMtoE.setPadding(new Insets(0, 10, 10, 10));
            lblMtoE.setWrapText(true);
            instructionPane.getChildren().addAll(lblEtoM, lblMtoE);
            
            HBox buttonPane = new HBox();
            buttonPane.setAlignment(Pos.CENTER);
            buttonPane.setPadding(new Insets(0, 0, 10, 0));
            Button btnClose = new Button("Got it");
            buttonPane.getChildren().add(btnClose);

            root.setCenter(instructionPane);
            root.setBottom(buttonPane);
            
            btnClose.setOnAction(ev -> 
            {
                stage.close();                
            }); //registers button to event
            
            stage.setTitle("Instructions");
            stage.setScene(new Scene(root, 300, 200));
            stage.show();
        }
    }
    
}
