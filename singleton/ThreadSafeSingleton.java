package singleton;


/**
 * Remember that the previous implementation was not thread safe
 * This getInstance() method uses something called as synchronized keyword
 * When multple threads try to access the instance at the same time. synchronized
 * ensures that only one thread can create the object, while the others wait
 * ThreadSafeSingleton
 * This would actually use the Locking/Mutex 
 * 
 * 
 * Performance Consideration :
 * This approach is correct but has a performance cost. Every call to getInstance()
 * acquires a lock, even after the instance has been created. Once the instance exists,
 * there is no reason to synchronize. The next approach fixes this issue
 */
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    private ThreadSafeSingleton(){}
    public static synchronized ThreadSafeSingleton getInstance(){
        if(instance == null){
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
