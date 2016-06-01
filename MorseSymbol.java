
import java.io.File;
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
    private File file;
    private String filePath;
    private AudioClip audio;

    public MorseSymbol(double width, double height, Color color, File fileAudio)
    {
        super(width, height);
        super.setFill(color);
        try
        {
            if (fileAudio != null)
            {
                this.file = fileAudio;
                filePath = this.file.toURI().toString();
                audio = new AudioClip(filePath);                
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
