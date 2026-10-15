package singleton;

public class LazyInitialization{
    private static LazyInitialization instance;

    // Private constructore prevents creating objects from outside the class
    private LazyInitialization(){}

    public static LazyInitialization getInstance(){
        // if the instance is not created then create it else return the instance
        if(instance == null){
            instance = new LazyInitialization();
        }
        return instance;
    }
}

/**
 * How it basically works?
 * getInstance() method checks if an instance already exists
 * if not create the new instance
 * if an instance already exists then creation step is skipped
 * This thing is not thread safe. If multiple threads call the thing when instance is null then there is a possibility of creating multiple instances which vialoates the rule.
 */