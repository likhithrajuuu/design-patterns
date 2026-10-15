# Builder Design Pattern
It is a creational design pattern that let's you construct complex objects step-by-step, separating the creational logic from the final representation.

Without this pattern, developers often end up with overloaded constructors or a wide set of setters.

For example, a User object might include the field names like name, email, address and preferences. As the number of fields grows, the API becomes harder to use correctly, easier to misuse and more difficult to maintain.

Builder pattern addresses this by introducing a dedicated builder that owns the creation logic. Clients configure the builder step by step and then build the final object, which can remain immutable, validated, and consistenly constructed.

Now let's first define the problem. Please check : [Problem](./Problem.java)

### What's wrong with this approach?
While this functionality works, this design becomes more error-prone as the object becomes more complex.

1. **Hard to Read and Write**
Multiple parameters of the same type make it easy to accidentally swap arguments. Code is difficult to understand at a glance, especially when most parameters are null.

2. **Error-prone**
Clients must pass null for optional parameters they do not want to set, increasing the risk of bugs. One wrong position and you silently assign a value to wrong field

3. **Inflexible and Fragile**
If you want to set parameter 5 but not 3 and 4, you are forced to pass null for 3 and 4. You must follow the exact parameter order, which hurts both readability and usability.

4. **Poor Scalability**
Adding a new optional parameter requires adding or changing constructors, which may break existing code. Testing and documentation become increasingly difficult to maintain.

## What is the builder pattern?
The builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different configurations.

Two ideas define the pattern:
1. Step-by-Step construction: Instead of passing everything to a constructor at once, you set each field through individual method calls. You only call the methods for the fields you need.

2. Fluent Interface : Each setter method returns the builder itself, allowing you to chain calls into a single readable expression that ends with build().

Before : Telescoping Constructor
```java
HttpRequest req = new HttpRequest(
    url,                 // url
    method,              // method
    headers,             // headers
    null,                // body
    null,                // queryParams
    30000                // timeoutMs
);
```


After : Builder Pattern
```java
HttpRequest req = new HttpRequest.Builder(url)
    .method("POST")
    .addHeader("key", "val")
    .build();
```


Think of a building a custom burger:
1. Builder - the instructions on how to make it. It defines the steps such as adding a bun, patty, cheese or sauce.
2. ConcreteBuilder - the specific worker or recipe that carries out those steps. One concrete builder might make a veggie burger; another might make a beef burger
3. Product - the thing you want to make. Here, it's the finished burger
4. Director(Optional) - the person who give the steps in the order