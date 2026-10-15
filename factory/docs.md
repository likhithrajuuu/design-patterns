# Factory Pattern
Factory Design Pattern is a creational-pattern that provides an interface for creating the objects in a superclass, but allows sub-classes to alter the type of objects that will be created

It's particularly useful in situations where:
    - The exact type of object to be created isn't known until runtime.
    - Object creation logic is complex, repetitive, or needs encapsulation.
    - You want to follow the Open/Closed Principle, open for the extension, closed for modification

When you have multiple objects of similar type, you might start with the basic conditional logic(like `if-else` or `switch` statements) to decide which object to create.

But as your application grows, this approach becomes rigid, harder to test, and tightly couples your code to specific classes, violating key design principles.

Factory method let's you create different objects without tightly coupling your code to specific classes.

Let's walk through a real-world example to see how we can apply the Factory Method pattern to build a more scalable and maintainable object creation workflow.

## The Problem : Sending Notifications
Imagine you are building a web application that sends notifications to users. At first, it's simple. You are sending email notifications. 
A single class takes care of that.
```java
class EmailNotification{
    public void send(){
        System.out.println("Sending an email notification ...");
    }
}
```

to use it in our service, we create the email notification object and call the send() method
```java
class NotificationService{
    public void sendNotification(String message){
        EmailNotification email = new EmailNotification();
        email.send(message);
    }
}
```

All good. But then comes a new requirement: support SMS notifications

So, you add a new class and update the NotificationService class by adding a new `if` block to create a SMS notification object, and send that too.

```java
class NotificationService{
    public void sendNotification(String type, String message){
        if(type.equals("EMAIL")){
            EmailNotificantion email = new EmailNotification();
            email.send(message);
        } else if(type.equals("SMS")){
            SMSNotification sms = new SMSNotification();
            sms.send(message);
        }
    }
}
```

Slightly more complex, but still manageable. A few weeks later, product wants to send push notifications to the mobile devices. Then marketing wants Slack alerts and then whatsapp

Each one adds another branch:
```java
class NotificationService{
    public void sendNotification(String type, String message){
        if(type.equals("EMAIL")){
            EmailNotificantion email = new EmailNotification();
            email.send(message);
        } else if(type.equals("SMS")){
            SMSNotification sms = new SMSNotification();
            sms.send(message);
        } else if(type.equals("Push")){
            PushNotification push = new PushNotification();
            push.send(message);
        } else if(type.equals("Slack")){
            SlackNotification slack = new SlackNotifiaction();
            slack.send(message);
        } else if(type.equals("Whatsapp")){
            WhatsappNotification whatsapp = new WhatsappNotification();
            whatsapp.send(message);
        }
    }
}
```

Now, your notification code is starting to look a giant control tower. It's responsible for creating every kind of notification, knowing how each one works, and deciding which to send based on the type.

This becomes a nightmare to maintain:
    - Every time you add a new notification channel, you must modify the same core logic
    - Testing becomes cumbersome because the logic is intertwined with object creation.
    - It violates the Open/Closed Principle the class is not open for extension without modification.

## First Attempt : Simple Factory
Before jumping to the full Factory Method pattern, there is a common intermediate step: extract  the creation logic into a separate class.

This is called Simple Factory. It is not a formal Gang of Four(GoF) pattern, but it is one of the most practical refactoring techniques in the real-world codebases.

The idea is straightforward: create a separate class whose only job is to centralize and encapsulate object creation. The notification service no longer needs to know which concrete class to instantiate. It asks the factory.

```java
class SimpleNotificationFactory{
    public static Notification createNotification(String type){
        return switch(type){
            case "EMAIL" -> new EmailNotification();
            case "SMS" -> new SMSNotification();
            case "PUSH" -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown type");
        }
    }
}
```

All the creation logic is now in one place. Now `NotificationService` is cleaner:
```java
class NotificationService{
    public void sendNotification(String type, String message){
        Notification notification = SimpleNotificationFactory.createNotification(type);
        notification.send(message);
    }
}
```

This is better. The service only uses the notification, it does not construct it. Adding new types is easier since you only modify the factory, not every service that uses notifications.

But as your product grows and you keep adding new notification types, something starts to feel off again. Your `SimpleNotificationFactory` is beginning to look eerily similar to th bloated code you just refactored away from.

Every time you introduce a new type, you are right back to modifying the factory's switch or `if-else` statements.

That is not very Open/Closed, is it?

Your system is better, but it is still not open to extension without modification. You are still hardcoding the decision logic and centralizing creation in one place. You need to give each type of notification its own responsibility for knowing how to create itself.

And that's exactly the type of problems ***Factory Method Design Pattern*** solves.

## What is the Factory Method Design Pattern?
The factory method pattern takes the idea of object creation and hands it off to the subclasses. Instead of one central factory deciding what to create, you delegate the responsibility to specialized classes that know exactly what they need to produce.

In simple terms:
 - Each subclass defines its own way of instantiating the object.
 - The base class defines a common interface for creating that object, but doesn't know what the object is.
 - The base class also often defines the common behaviour, using the created object in some way.

So now instead of having:
```java
if type == "EMAIL" -> return new EmailNotification();
if type == "SMS" -> return new SMSNotification();
```

Now the creation logic is decentralized.


Real World Analogy:
Think of a food delivery platform. you place an order. If the system is designed like a Simple Factory, there's one centralized kitchen deciding whether to cook pizza, sushi, or burgers.

But with th Factory Method, each restuarant (Pizza Place, Sushi Bar, Burger Joint) has its own kitchen and knows how to prepare its food. The platform just asks the appropriate kitchen to handle it.
