package morse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
/*
Tree data structure to hold nodes that represent a letter and its corresponding
symbol in the Morse coding system
*/
public class MorseTree 
{
    private final Node root;
    private static final char DOT = '.';
    private static final char DASH = '-';
    
    public MorseTree()
    {
        root = new Node(' ', "");
        build();   
    }
    
    /*
    Each line of file represents a character in the morse encoding system
    and its corresponding symbol.  The character and symbol form a Node.
    */
    private void build()
    {
        try
        {
            String resource = "resources/BuildTree.txt";
            InputStream inStream = this.getClass().getResourceAsStream(resource);
            Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inStream)));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                Character letter = parts[0].charAt(0);
                String symbol = parts[1];
                Node node = new Node(letter, symbol);
                add(node);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /*
    Public method
    */
    public void add(Node nodeToAdd)
    {
        add(nodeToAdd, root);
    }
    
    /*
    Adds Node to correct position in tree
    */
    private void add(Node nodeToAdd, Node localNode)
    {
        int requiredDepth = nodeToAdd.getDepthLevel(); //length of symbol
        int currentDepth = localNode.getDepthLevel(); //length of symbol are current level
        char symbol = nodeToAdd.getSymbol().charAt(currentDepth); //dot or dash
        boolean correctPos = false;
        if (requiredDepth - currentDepth == 1) //cursor at correct position in tree
        {
            correctPos = true;
        }
        
        Node nextNode;
        if (correctPos && symbol == DOT) //adds node to left position
        {
            localNode.setLeftNode(nodeToAdd);
        }
        else if (correctPos && symbol == DASH) //adds node to right position
        {
            localNode.setRightNode(nodeToAdd);
        }
        else if (!correctPos && symbol == DOT) //moves to left in tree
        {
            nextNode = localNode.getLeftNode();
            add(nodeToAdd, nextNode);
        }
        else //moves to right in tree
        {
            nextNode = localNode.getRightNode();
            add(nodeToAdd, nextNode);
        }
    }
    
    /*
    Encodes plain text
    */
    public String encode(String plainTextMessage) throws CodeableException
    {
        StringBuilder codeable = new StringBuilder();
        StringBuilder uncodeable = new StringBuilder();
        String[] parts = plainTextMessage.split(" ");
        for (String part: parts)
        {
            part = part.toLowerCase();
            char[] letters = part.toCharArray();
            for (char letter: letters)
            {
                if (Character.isAlphabetic(letter))
                {
                    retrieveSymbol(letter, root, codeable);                    
                }
                else //Non-alpha chars not in tree
                {
                    uncodeable.append(letter);
                    uncodeable.append(" ");
                }
            }
            codeable.append("   ");//Space between words
        }
        if (uncodeable.length() > 0) //Unencodeable chars in plain text
        {
            throw new CodeableException(uncodeable.toString());
        }
        createAnimSymbols(codeable); //Passes list of encoded letter
        return codeable.toString();
    }
    
    /*
    Fetches symbol corresponding to char from tree
    */
    private void retrieveSymbol(Character targetLetter, Node localRoot, 
            StringBuilder codeable)
    { 
        if (localRoot != null) //Current node is not null
        {
            if (localRoot.getLetter().equals(targetLetter)) //At correct location
            {
                codeable.append(localRoot.getSymbol());
                codeable.append(" ");
            }
            else //Not correct location in tree
            {
                retrieveSymbol(targetLetter, localRoot.getLeftNode(), codeable);
                retrieveSymbol(targetLetter, localRoot.getRightNode(), codeable);
            }            
        }
    }
    
    /*
    Creates a list of MorseSymbols for use in animation
    */
    private void createAnimSymbols(StringBuilder codeable)
    {
        ArrayList<MorseSymbol> symbols = new ArrayList<>();
        for (int i = 0; i < codeable.length(); i++)
        {
            char symbol = codeable.charAt(i);
            if (symbol == DASH)
            {
                symbols.add(new Dash("resources/dash.mp3"));
            }
            else if (symbol == DOT)
            {
                symbols.add(new Dot("resources/dot.mp3"));
            }
            else
            {
                symbols.add(new Space());
            }
        }
        MorseAnimation codeDisplay = new MorseAnimation(symbols); //constructs animation
        codeDisplay.showStage();   
    }
    
    /*
    Decodes message
    */
    public String decode(String codedMessage) throws CodeableException
    {
        StringBuilder decodeable = new StringBuilder();
        StringBuilder unDecodeable = new StringBuilder();
        String[] symbols = codedMessage.split(" ");
        for (String symbol: symbols)
        {
            symbol = symbol.trim();
            if (formatCorrect(symbol, unDecodeable))
            {
                retrieveLetter(symbol, root, decodeable);                    
            }   
        }
        if (unDecodeable.length() > 0) //Symbols that cannot be decoded
        {
            throw new CodeableException(unDecodeable.toString());
        }
        return decodeable.toString();
    }
    
    /*
    Returns true if all chars in String are either DOT or DASH, otherwise
    returns false
    */
    private boolean formatCorrect(String symbol, StringBuilder unDecodeable)
    {
        boolean correctFormat = true;
        char[] symbols = symbol.toCharArray();
        for (char s: symbols)
        {
            if (s != DOT && s != DASH)
            {
                correctFormat = false;
                unDecodeable.append(s);
                unDecodeable.append(" ");
            }
        }
        return correctFormat;
    }
    
    /*
    Retrieves letter associated with symbol from tree
    */
    private void retrieveLetter(String symbol, Node localRoot, StringBuilder sb)
    {
        int symbolDepth = symbol.length();
        if (localRoot != null) //current Node is not null
        {
            int currentDepth = localRoot.getDepthLevel(); //Length of symbol

            if (symbolDepth == currentDepth) //At current position
            {
                sb.append(localRoot.getLetter());
            }
            else if (symbol.charAt(currentDepth) == DOT) //Search left
            {
                retrieveLetter(symbol, localRoot.getLeftNode(), sb);
            }
            else //Search right
            {
                retrieveLetter(symbol, localRoot.getRightNode(), sb);
            }              
        } 
    }
}
