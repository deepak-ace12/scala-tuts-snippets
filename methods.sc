// Scala lets you control method visibility in a more granular and powerful way than Java. 
// In order from “most restrictive” to “most open,” Scala provides these scope options:
// 1. Object-private scope
// 2. Private
// 3. Package
// 4. Package-specific
// 5. Public


// Object-private scope:

// The most restrictive access is to mark a method as object-private. When you do this, 
// the method is available only to the current instance of the current object. 
// Other instances of the same class cannot access the method.

private[this] def isFoo = true


// Private scope:

// A slightly less restrictive access is to mark a method private, which makes the method 
// available to (a) the current class and (b) other instances of the current class. 

private def isFoo = true


// Protected scope:

// Marking a method protected makes the method available to subclasses. In Java, protected methods 
// can be accessed by other classes in the same package, but this isn’t true in Scala.

protected def isFoo = true


// Package scope:
// To make a method available to all members of the current package—what would be called “package scope” 
// in Java—mark the method as being private to the current package with the private[packageName] syntax.

package com.acme.coolapp.model {

    class Foo {
        private[model] def doX {}
    }
}


// More package-level control:

// Beyond making a method available to classes in the current package, Scala gives you more control 
// and lets you make a method available at different levels in a class hierarchy. 
// The following example demonstrates how you can make the methods doX, doY, and doZ available to different package levels:

package com.acme.coolapp.model { 
    class Foo {
        private[model] def doX {}
        private[coolapp] def doY {}
        private[acme] def doZ {} 
    }
}


// Calling a Method on a Superclass: Use super to refer to the parent class, and then provide the method name.

// Controlling which trait you call a method from:

trait Human {
    def hello = "the Human trait" 
}

trait Mother extends Human { 
    override def hello = "Mother"
}

trait Father extends Human { 
    override def hello = "Father"
}


class Child extends Human with Mother with Father { 
    def printSuper = super.hello
    def printMother = super[Mother].hello
    def printFather = super[Father].hello
    def printHuman = super[Human].hello 
}


// Note that when using this technique, you can’t continue to reach up through the parent class hierarchy 
// unless you directly extend the target class or trait using the extends or with keywords. 
// For instance, the following code won’t compile because Dog doesn’t directly extend the Animal trait:

trait Animal {
    def walk { println("Animal is walking") }
}

class FourLeggedAnimal extends Animal {
    override def walk { println("I'm walking on all fours") }
}

class Dog extends FourLeggedAnimal { 
    def walkThenRun {
        super.walk // works 
        uper[FourLeggedAnimal].walk // works 
        super[Animal].walk // error: won't compile
    } 
}


// Creating Methods That Take Variable-Argument Fields
def printAll(strings: String*) {
    strings.foreach(println) 
}

// these all work
printAll()
printAll("foo") 
printAll("foo", "bar") 
printAll("foo", "bar", "baz")
val fruits = List("apple", "banana", "cherry")
printAll(fruits: _*)

// When declaring that a method has a field that can contain a variable number of arguments, 
// the varargs field must be the last field in the method signature.


// Declaring That a Method Can Throw an Exception

@throws(classOf[IOException]) 
@throws(classOf[LineUnavailableException]) 
@throws(classOf[UnsupportedAudioFileException]) 

def playSoundFileWithJavaAudio {
    // exception throwing code here ...
}


