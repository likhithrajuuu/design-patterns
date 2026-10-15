package singleton;

/**
 * DoubleCheckedLocking
 * Private constructor prevents external instantiation
 * Fast Path : first check without locking
 * Lock only when the instance might need to be created 
 * Second check inside the lock (prevents the double creation)
 */
public class DoubleCheckedLocking {
    private static volatile DoubleCheckedLocking instance;
    private DoubleCheckedLocking(){}
    private static  DoubleCheckedLocking getInstance(){
        if(instance == null){
            synchronized (DoubleCheckedLocking.class){
                if(instance == null){
                    instance = new DoubleCheckedLocking();
                }
            }
        }
        return instance;
    }
}
