package comp3350.sonicmatic.exceptions;

public class PersistentTypeMismatchException extends Exception
{
    public PersistentTypeMismatchException()
    {
        super("PersistentTypeMismatchException - Not the correct type for this persistence object to operate on");
    }
}
