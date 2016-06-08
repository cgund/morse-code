package morse;


import java.net.URL;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
Class to represent a symbol in the Morse code system and sound associated 
with the symbol
*/
public abstract class MorseSymbol extends Rectangle
{
    private AudioClip audio;

    public MorseSymbol(double width, double height, Color color, String resourceAudio)
    {
        super(width, height);
        super.setFill(color);
        try
        {
            if (resourceAudio != null)
            {
                URL resource = this.getClass().getResource(resourceAudio);
                audio = new AudioClip(resource.toExternalForm());                
            }
        }
        catch(NullPointerException | IllegalArgumentException | MediaException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public void playSound()
    {
        if (audio != null)
        {
            audio.play();            
        }
    }
    
    public void setSymbolFill(Color color)
    {
        super.setFill(color);
    }
}
