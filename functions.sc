// Anonymous Function
val isEven = (i: Int) => { i % 2 == 0 }
val double = (i: Int) => { i * 2 }

// Instead of val, you can use def too. The difference is val is initialized at declaration 
// while def is initialized every time it's called.

// Explicitly declaring the return type of an Anonymous Function:
val isEven: (Int) => Boolean = i => { i % 2 == 0 }

// Anonymous function with two Parameters:

// implicit approach
val add = (x: Int, y: Int) => { x + y } 
val add = (x: Int, y: Int) => x + y

// explicit approach
val add: (Int, Int) => Int = (x,y) => { x + y } 
val add: (Int, Int) => Int = (x,y) => x + y

// the curly braces around the body of the function in these simple examples are optional, 
// but they are required when the function body grows to more than one expression

// Using functions as variables:
val list = List.range(1, 10) 
list.filter(isEven)


// Defining a Method That Accepts a Simple Function Parameter:

// To define a function that takes a String and returns an Int, use one of these two signatures:
executeFunction(f:String => Int) 
executeFunction(f:(String) => Int)
// parentheses are optional when the function has only one parameter

// To define a function that takes two Ints and returns a Boolean, use this signature:
executeFunction(f:(Int, Int) => Boolean)


def executeAndPrint(f:(Int, Int) => Int, x: Int, y: Int) { 
    val result = f(x, y)
    println(result)
}

val sum = (x: Int, y: Int) => x + y
val multiply=(x:Int,y:Int)=> x*y

executeAndPrint(sum, 2, 9) // prints 11
executeAndPrint(multiply, 3, 9) // prints 27


// Using Closures

// To demonstrate a closure in Scala, use the following simple (but complete) example:

package otherscope {
    class Foo {
// a method that takes a function and a string, and passes the string into 
// the function, and then executes the function
        def exec(f:(String) => Unit, name: String) {
            f(name) 
        }
    }       
}

object ClosureExample extends App {
    var hello = "Hello"
    def sayHello(name: String) { 
        println(s"$hello, $name") 
    }
    // execute sayHello from the exec method foo 
    val foo = new otherscope.Foo 
    foo.exec(sayHello, "Al")
    // change the local variable 'hello', then execute sayHello from // the exec method of foo, and see what happens
    hello = "Hola"
    foo.exec(sayHello, "Lorenzo")
}

Hello, Al 
Hola, Lorenzo

// a closure begins with a function and a variable defined in the same scope, which are then 
// separated from each other. When the function is executed at some other point in space (scope) 
// and time, it is magically still aware of the variable it referenced in their earlier time together, 
// and even picks up any changes to that variable.


// Using Partially Applied Functions:

val sum = (a: Int, b: Int, c: Int) => a + b + c

// There’s nothing special about this sum function, it’s just a normal function. 
// But things get interesting when you supply two of the parameters when calling the function, 
// but don’t provide the third parameter:

val f = sum(1, 2, _: Int)

// Because you haven’t provided a value for the third parameter, the resulting variable f is
// a partially applied function.
scala> f(3) 
res0: Int = 6

// Creating a Function That Returns a Function:
def saySomething(prefix: String) = (s: String) => { 
    prefix + " " + s
}
// Because saySomething returns a function, you can assign that resulting function to a variable. 
// The saySomething function requires a String argument, so give it one as you create the resulting 
// function sayHello:

val sayHello = saySomething("Hello")
sayHello("Al")

or

saySomething("Hello")("Deepak")


// Creating Partial Functionsl:
// You want to define a function that will only work for a subset of possible input values.

// A partial function is a function that does not provide an answer for every possible input value it can be given. 
// It provides an answer only for a subset of possible data, and defines the data it can handle. 
// In Scala, a partial function can also be queried to determine if it can handle a particular value.

val divide = (x: Int) => 42 / x

// As defined, this function blows up when the input parameter is zero:
// Although you can handle this particular situation by catching and throwing an exception, 
// Scala lets you define the divide function as a PartialFunction. When doing so, you also 
// explicitly state that the function is defined when the input parameter is not zero:

val divide = new PartialFunction[Int, Int] { 
    def apply(x: Int) = 42 / x
    def isDefinedAt(x: Int) = x != 0 
}

// With this approach, you can do several nice things. 
// One thing you can do is test the function before you attempt to use it:
if (divide.isDefinedAt(1)) divide(1)


// Whereas that divide function is explicit about what data it handles, partial functions are often 
// written using case statements:
val divide2: PartialFunction[Int, Int] = { 
    case d: Int if d != 0 => 42 / d

}

// The example method PartialFunction[Int, Int] transformed an input Int into an output Int, but if it returned a String instead, 
// it would be declared like this:
PartialFunction[Int, String]

// Although this code doesn’t explicitly implement the isDefinedAt method, 
// it works exactly the same as the previous divide function definition:

scala> divide2.isDefinedAt(0) 
res0: Boolean = false


// orElse and andThen:
// A terrific feature of partial functions is that you can chain them together. For instance, 
// one method may only work with even numbers, and another method may only work with odd numbers. 
// Together they can solve all integer problems.

val convert1to5 = new PartialFunction[Int, String] {
    val nums = Array("one", "two", "three", "four", "five") 
    def apply(i: Int) = nums(i-1)
    def isDefinedAt(i: Int) = i > 0 && i < 6
}
// converts 6 to "six", etc., up to 10
val convert6to10 = new PartialFunction[Int, String] {
    val nums = Array("six", "seven", "eight", "nine", "ten") 
    def apply(i: Int) = nums(i-6)
    def isDefinedAt(i: Int) = i > 5 && i < 11
}

val handle1to10 = convert1to5 orElse convert6to10

scala> handle1to10(3) 
res0: String = three

scala> handle1to10(8) 
res1: String = eight

// collect:
// collect builds a new collection by applying a partial function to all elements of 
// this list on which the function is defined.

val divide: PartialFunction[Int, Int] = { 
    case d: Int if d != 0 => 42 / d
}

// If you attempt to use this function with the map method, it will explode with a MatchError:
scala> List(0,1,2) map { divide } 
scala.MatchError: 0 (of class java.lang.Integer) stack trace continues ...

// However, if you use the same function with the collect method, it works fine: 
scala> List(0,1,2) collect { divide }
res0: List[Int] = List(42, 21)

// This is because the collect method is written to test the isDefinedAt method for each element it’s given. 
// As a result, it doesn’t run the divide algorithm when the input value is 0 (but does run it for every 
// other element).
// You can see the collect method work in other situations, such as passing it a List that contains 
// a mix of data types, with a function that works only with Int values:
scala> List(42, "cat") collect { case i: Int => i + 1 } 
res0: List[Int] = List(43)


// Understanding Mutable Variables with Immutable Collections:
// You may have seen that mixing a mutable variable (var) with an immutable collection causes surprising behavior. 
// For instance, when you create an immutable Vector as a var, it appears you can somehow add new elements to it:

scala> var sisters = Vector("Melinda")
sisters: collection.immutable.Vector[String] = Vector(Melinda)

scala> sisters = sisters :+ "Melissa"
sisters: collection.immutable.Vector[String] = Vector(Melinda, Melissa)

scala> sisters = sisters :+ "Marisa"
sisters: collection.immutable.Vector[String] = Vector(Melinda, Melissa, Marisa)

// Though it looks like you’re mutating an immutable collection, what’s really happening is that the sisters 
// variable points to a new collection each time you use the :+ method. The sisters variable is mutable—like 
// a non-final field in Java—so it’s actually being reassigned to a new collection during each step. 
// The end result is similar to these lines of code:

var sisters = Vector("Melinda")
sisters = Vector("Melinda", "Melissa")
sisters = Vector("Melinda", "Melissa", "Marisa")

// In the second and third lines of code, the sisters reference has been changed to point to a new collection.
// Attempting to mutate one of its elements—which doesn’t involve reassigning the variable—results in an error:

scala> sisters(0) = "Molly"
<console>: error: value update is not a member of scala.collection.immutable.Vector[String]
sisters(0) = "Molly"

// A mutable variable (var) can be reassigned to point at new data.


// Splitting Sequences into Subsets (groupBy, partition, etc.)
scala> val x = List(15, 10, 5, 8, 20, 12) 
x: List[Int] = List(15, 10, 5, 8, 20, 12)

// The groupBy method partitions the collection into a Map of subcollections based on your function. 
scala> val y = x.groupBy(_ > 10) 
y: Map[Boolean,List[Int]] = Map(false -> List(10, 5, 8), true -> List(15, 20, 12))

// The partition method creates two lists, one containing values for which your predicate returned true, 
// and the other containing the elements that returned false.
scala> val y = x.partition(_ > 10)
y: (List[Int], List[Int]) = (List(15, 20, 12), List(10, 5, 8))

// The span method that splits the list in two. It scans all elements in order, storing them into a list 
// while the given predicate (a function) is true. When the predicate is false span() stops and returns 
// the two resulting lists
scala> val y = x.span(_ < 20)
y: (List[Int], List[Int]) = (List(15, 10, 5, 8), List(20, 12))

// The splitAt method splits the original list according to the element index value you supplied.
scala> val y = x.splitAt(2)
y: (List[Int], List[Int]) = (List(15, 10), List(5, 8, 20, 12))


// The sliding(size, step) method is an interesting creature that can be used to break a sequence into many groups. 
// It can be called with just a size, or both a size and step:

scala> val nums = (1 to 5).toArray 
nums: Array[Int] = Array(1, 2, 3, 4, 5)
// size = 2
scala> nums.sliding(2).toList
res0: List[Array[Int]] = List(Array(1, 2), Array(2, 3), Array(3, 4), Array(4, 5))
// size = 2, step = 2
scala> nums.sliding(2,2).toList
res1: List[Array[Int]] = List(Array(1, 2), Array(3, 4), Array(5))
// size = 2, step = 3
scala> nums.sliding(2,3).toList
res2: List[Array[Int]] = List(Array(1, 2), Array(4, 5))


// The unzip method is also interesting. It can be used to take a sequence of Tuple2 values and create 
// two resulting lists: one that contains the first element of each tuple, and another that contains 
// the second element from each tuple:

scala> val listOfTuple2s = List((1,2), ('a', 'b')) 
listOfTuple2s: List[(AnyVal, AnyVal)] = List((1,2), (a,b))

scala> val x = listOfTuple2s.unzip
x: (List[AnyVal], List[AnyVal]) = (List(1, a),List(2, b))


scala> val couples = List(("Kim", "Al"), ("Julia", "Terry")) 
couples: List[(String, String)] = List((Kim,Al), (Julia,Terry))

scala> val (women, men) = couples.unzip 
women: List[String] = List(Kim, Julia) 
men: List[String] = List(Al, Terry)


// Walking Through a Collection with the reduce and fold Methods
val a = Array(12, 6, 15, 2, 20, 9)
scala> a.reduceLeft(_ + _) 
res0: Int = 64

val findMax = (x: Int, y: Int) => {
    val winner = x max y
    println(s"compared $x to $y, $winner was larger") 
    winner
}

scala> a.reduceLeft(findMax) 
compared 12 to 6, 12 was larger 
compared 12 to 15, 15 was larger 
compared 15 to 2, 15 was larger
compared 15 to 20, 20 was larger 
compared 20 to 9, 20 was larger 
res0: Int = 20

val peeps = Vector("al", "hannah", "emily", "christina", "aleka")
scala> peeps.reduceLeft((x,y) => if (x.length > y.length) x else y) 
res0: String = christina


// foldLeft, reduceRight, and foldRight
// The foldLeft method works just like reduceLeft, but it lets you set a seed value to be used for the first element. 
// The following examples demonstrate a “sum” algorithm, first with reduceLeft and then with foldLeft, 
// to demonstrate the difference:
scala> val a = Array(1, 2, 3) 
a: Array[Int] = Array(1, 2, 3)

scala> a.reduceLeft(_ + _) 
res0: Int = 6

scala> a.foldLeft(20)(_ + _) 
res1: Int = 26

scala> a.foldLeft(100)(_ + _) 
res2: Int = 106

// The difference between reduceLeft and reduceRight
// In many algorithms, it may not matter if you call reduceLeft or reduceRight. 
// In that case, you can call reduce instead. The reduce Scaladoc states, 
// “The order in which operations are performed on elements is unspecified and may be nondeterministic.”
// But some algorithms will yield a big difference. For example, the divide or concat function.


// Extracting Unique Elements from a Sequence
val x = Vector(1, 1, 2, 3, 3, 4)

scala> val y = x.distinct
y: scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, 4)

scala> val s = x.toSet
s: scala.collection.immutable.Set[Int] = Set(1, 2, 3, 4)

// Using distinct with your own classes
// To use distinct with your own class, you’ll need to implement the equals and hashCode methods.

class Person(firstName: String, lastName: String) { 
    override def toString = s"$firstName $lastName" 
    
    def canEqual(a: Any) = a.isInstanceOf[Person]
    
    override def equals(that: Any): Boolean = that match {
        case that: Person => that.canEqual(this) && this.hashCode == that.hashCode
        case _ => false
    }
    
    override def hashCode: Int = {
        val prime = 31
        var result = 1
        result = prime * result + lastName.hashCode;
        result = prime * result + (if (firstName == null) 0 else firstName.hashCode) return result
    } 
}

object Person {
    def apply(firstName: String, lastName: String) = new Person(firstName, lastName)
}

val dale1 = new Person("Dale", "Cooper") 
val dale2 = new Person("Dale", "Cooper") 
val ed = new Person("Ed", "Hurley")
val list = List(dale1, dale2, ed)
val uniques = list.distinct


// Merging Sequential Collections
// • Use the ++= method to merge a sequence into a mutable sequence.
// • Use the ++ method to merge two mutable or immutable sequences.
// • Use collection methods like union, diff, and intersect.


// Creating a Lazy View on a Collection:
// Except for the Stream class, whenever you create an instance of a Scala collection class, 
// you’re creating a strict version of the collection. This means that if you create a collection 
// that contains one million elements, memory is allocated for all of those elements im‐ mediately. 
// This is the way things normally work in a language like Java.
val a = Stream from 1
// In Scala you can optionally create a view on a collection. A view makes the result non‐ strict, or lazy. 
// This changes the resulting collection, so when it’s used with a transformer method, the elements will 
// only be calculated as they are accessed, and not “eagerly,” as they normally would be.

scala> 1 to 100
res0: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, ... 98, 99, 100)

scala> (1 to 100).view 
res0: java.lang.Object with
scala.collection.SeqView[Int,scala.collection.immutable.IndexedSeq[Int]] = SeqView(...)

// The Scala documentation states that a view “constructs only a proxy for the result collection, 
// and its elements get constructed only as one demands them.

// Forcing view to normal collection:

scala> val a = (1 to 100).view 
a: java.lang.Object with
scala.collection.SeqView[Int,scala.collection.immutable.IndexedSeq[Int]] = SeqView(...)

scala> val x = a.force
x: scala.collection.immutable.IndexedSeq[Int] =Vector(1, 2, 3, ... 98, 99, 100)


// Populating a Collection with a Range:
// Call the range method on sequences that support it, or create a Range and convert it to the desired sequence.

scala> Array.range(1, 5)
res0: Array[Int] = Array(1, 2, 3, 4)

scala> List.range(0, 10)
res1: List[Int] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

scala> Vector.range(0, 10, 2)
res2: collection.immutable.Vector[Int] = Vector(0, 2, 4, 6, 8)


For some of the collections, such as List and Array, you can also create a Range and convert it to the desired sequence:
scala> val a = (0 until 10).toArray
a: Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
scala> val list = 1 to 10 by 2 toList 
list: List[Int] = List(1, 3, 5, 7, 9)

scala> val list = (1 to 10).by(2).toList 
list: List[Int] = List(1, 3, 5, 7, 9)

// The REPL shows the collections that can be created directly from a Range:
// toArray
// toList
// toString
// toBuffer        
// toIndexedSeq   
// toIterable   
// toIterator
// toMap           
// toSeq          
// toSet        
// toStream
// toTraversable


// Sorting a Collection

scala> val a = List(10, 5, 8, 1, 7).sorted 
a: List[Int] = List(1, 5, 7, 8, 10)

scala> List(10, 5, 8, 1, 7).sortWith(_ < _) 
res1: List[Int] = List(1, 5, 7, 8, 10)

scala> List(10, 5, 8, 1, 7).sortWith(_ > _) 
res2: List[Int] = List(10, 8, 7, 5, 1)


// Your sorting function can be as complicated as it needs to be. For example, you can access methods on 
// the elements during the sort, such as the following example, which sorts a list of strings by the string length:

scala> List("banana", "pear", "apple", "orange").sortWith(_.length < _.length) 
res5: List[java.lang.String] = List(pear, apple, banana, orange)

scala> List("banana", "pear", "apple", "orange").sortWith(_.length > _.length) 
res6: List[java.lang.String] = List(banana, orange, apple, pear)

// In the same way the length method is called on a String, you can call a method on any class you want to sort. 
// If your sorting method gets longer, first declare it as a method:
def sortByLength(s1: String, s2: String) = { 
    println("comparing %s and %s".format(s1, s2)) 
    s1.length > s2.length
}

// Then use it by passing it into the sortWith method:
scala> List("banana", "pear", "apple").sortWith(sortByLength) 
comparing banana and pear
comparing pear and apple
comparing apple and pear
comparing banana and apple
res0: List[String] = List(banana, apple, pear)

// If the type a sequence is holding doesn’t have an implicit Ordering, you won’t be able
// to sort it with sorted. For instance, given this basic class: