// Object Casting:

// Use the asInstanceOf method to cast an instance to the desired type.
val recognizer = cm.lookup("recognizer").asInstanceOf[Recognizer]

// This Scala code is equivalent to the following Java code:
Recognizer recognizer = (Recognizer)cm.lookup("recognizer");


// The Scala Equivalent of Java’s .class:

// Use the Scala classOf method instead of Java’s .class. The following example shows how to pass a 
// class of type TargetDataLine to a method named DataLine.Info:

val info = new DataLine.Info(classOf[TargetDataLine], null)


// Determining the Class of an Object:

// When you want to learn about the types Scala is automatically assigning on your behalf, 
// call the getClass method on the object.

numbers.getClass

// Give the class you’re importing a new name when you import it with this syntax:
import java.util.{ArrayList => JavaList}