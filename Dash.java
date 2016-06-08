package morse;

import javafx.scene.paint.Color;
/*
Represents a dash in the Morse code system
*/
public class Dash extends MorseSymbol
{    
    /*No arg constructor that passes width, height, color and a reference to a 
    file with sound corresponding to symbol to super constructor*/
    public Dash(String resource)
    {
        super(20, 10, Color.BLACK, resource);
    }
}
