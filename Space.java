
import javafx.scene.paint.Color;

/*
Class to represent a space for animation purposes
*/
public class Space extends MorseSymbol
{
    /*No arg constructor that passes width, height, 
    and color to super constructor*/
    public Space()
    {
        super(20, 10, Color.WHITE, null);
    }
    
    @Override
    public void setSymbolFill(Color color)
    {
        super.setSymbolFill(Color.WHITE);
    }
}
