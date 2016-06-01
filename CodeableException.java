/*
Exception to handle plain text that cannot be encoded and encoded text that
cannot be unlocked
*/
public class CodeableException extends Exception
{
    public CodeableException(String message)
    {
        super(message);
    }
}
