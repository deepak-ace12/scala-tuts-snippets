// Controlling the Visibility of Constructor Fields:

// 1. If a field is declared as a var, Scala generates both getter and setter methods for that field.
// 2. If the field is a val, Scala generates only a getter method for it.
// 3. If a field doesn’t have a var or val modifier, Scala gets conservative, and doesn’t generate a getter or setter method for the field.
// 4. Additionally, var and val fields can be modified with the private keyword, which prevents getters and setters from being generated.


// Case classes:

// Parameters in the constructor of a case class differ from these rules in one way. 
// Case class constructor parameters are val by default but can be made var explicitly.


// Auxiliary Constructors:

// 1. Auxiliary constructors are defined by creating methods named this.
// 2. Each auxiliary constructor must begin with a call to a previously defined constructor.
// 3. Each constructor must have a different signature.
// 4. One constructor calls another constructor with the name this.
// 5. Scala generate the accessor and mutator methods for primary constructor's parameters.


// If you decide that you want to add auxiliary constructors to let you create new Person instances (a) 
// without specifying any parameters, and (b) by only specifying their name, the solution is to add apply 
// methods to the companion object of the Person case class in the Person.scala file

case class Person (var name: String, var age: Int) 

// the companion object
object Person {
    def apply() = new Person("<no name>", 0)
    def apply(name: String) = new Person(name, 0)
}

// Case classes:

// 1. An apply method is generated, so you don’t need to use the new keyword to create a new instance of the class.
// 2. Accessor methods are generated for the constructor parameters because case class constructor parameters are val by default. Mutator methods are also generated for parameters declared as var.
// 3. A good, default toString method is generated.
// 4. An unapply method is generated, making it easy to use case classes in match expressions.
// 5. equals and hashCode methods are generated.
// 6. A copy method is generated.

// A case class even creates a copy method that is helpful when you need to clone an object, and change some of the fields during the process:
case class Employee(name: String, loc: String, role: String)
val fred = Employee("Fred", "Anchorage", "Salesman")
val joe = fred.copy(name="Joe", role="Mechanic")

// Overriding Default Accessors and Mutators:

// Notice the constructor parameter is declared private and var. The private keyword keeps Scala from 
// exposing that field to other classes, and the var lets the value of the field be changed.

class Person(private var _name: String) {
    def name = _name // accessor
    def name_=(aName: String) { _name = aName } // mutator 
}


// Preventing Getter and Setter Methods from Being Generated:

// Defining a field as private limits the field so it’s only available to instances of the same class, 
// in this case instances of the Stock class. To be clear, any instance of a Stock class 
// can access a private field of any other Stock instance.
class Stock {
    private var currentPrice: Double = _ 
}

// As an example, the following code yields true when the Driver object is run, 
// because the isHigher method in the Stock class can access the price field both (a) 
// in its object, and (b) in the other Stock object it’s being compared to:
class Stock {
// a private field can be seen by any Stock instance
    private var price: Double = _
    def setPrice(p: Double) { price = p }
    def isHigher(that: Stock): Boolean = this.price > that.price
}

object Driver extends App {
    val s1 = new Stock s1.setPrice(20)
    val s2 = new Stock s2.setPrice(100)
    println(s2.isHigher(s1))
}

// Object-private fields:
// Defining a field as private[this] takes this privacy a step further, and makes the field object-private, 
// which means that it can only be accessed from the object that contains it. Unlike private, the field can’t 
// also be accessed by other instances of the same type, making it more private than the plain private setting.

class Stock {
    // a private[this] var is object-private, and can only be seen // by the current instance
    private[this] var price: Double = _
    def setPrice(p: Double) { price = p }
    // error: this method won't compile because price is now object-private
    def isHigher(that: Stock): Boolean = this.price > that.price 
}


// When to Use an Abstract Class:
// There are two main reasons to use an abstract class in Scala:
// 1. You want to create a base class that requires constructor arguments.
// 2. The code will be called from Java code.

// this won't compile
trait Animal(name: String)

// So, use an abstract class whenever a base behavior must have constructor parameters:
abstract class Animal(name: String)

// Use an abstract class instead of a trait when the base functionality must take constructor parameters. 
// However, be aware that a class can extend only one abstract class.

def speak // no body makes the method abstract

// When you define an abstract field in an abstract class or trait, the Scala compiler does not create 
// a field in the resulting code; it only generates the methods that correspond to the val or var field.

abstract class BaseController(db: Database) {
    // abstract val and var fields
    val greeting: String
    var age: Int

    def save { db.save }
    def update { db.update } def delete { db.delete }
    // abstract
    def connect
    // an abstract method that returns a String
    def getStatus: String
        // an abstract method that takes a parameter
    def setServerName(serverName: String) 
}

// Concrete val fields in abstract classes:
// When defining a concrete val field in an abstract class, you can provide an initial value, 
// and then override that value in concrete subclasses.

abstract class Animal {
    val greeting = "Hello"  // provide an initial value
    def sayHello { println(greeting) } def run
}

class Dog extends Animal {
    override val greeting = "Woof"  // override the value
    def run { println("Dog is running") }
}

// To prevent a concrete val field in an abstract base class from being overridden in a
// subclass, declare the field as a final val:

abstract class Animal {
    final val greeting = "Hello" 
}

class Dog extends Animal {
    val greeting = "Woof"   // won't compile
}


// Concrete var fields in abstract classes:

// You can also give var fields an initial value in your trait or abstract class, and then
// refer to them in your concrete subclasses, like this.

abstract class Animal {
    var greeting = "Hello"
    var age = 0
    override def toString = s"I say $greeting, and I'm $age years old."
}

class Dog extends Animal { 
    greeting = "Woof"
    age = 2
}