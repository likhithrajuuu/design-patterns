# Singleton Design Pattern
It is a creational design pattern with two goals:
1. Class has only one instance
2. Code has a shared way to access that instance

Imagine a print spooler : applications send jobs to the same spooler so they're co-ordinated. In software, the shared loggers, caches or the configuration objects are common examples that we can see

Please read the solution in the same order that is mentioned here:
1. [Lazy Initialization](./LazyInitialization.java)
2. [Thread-Safe Singleton Implementation](./ThreadSafeSingleton.java)
3. [Double-Checked Locking](./DoubleCheckedLocking.java)