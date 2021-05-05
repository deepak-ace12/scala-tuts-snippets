// List: A singly linked list. Suited for recursive algorithms that work by splitting the head from the remainder of the list.
// Queue: A first-in, first-out data structure.
// Range: A range of integer values.
// Stack: A last-in, first-out data structure.
// Stream: Similar to List, but it’s lazy and persistent. Good for a large or infinite sequence, similar to a Haskell List.
// String: Can be treated as an immutable, indexed sequence of characters.
// Vector: The “go to” immutable, indexed sequence. The Scaladoc describes it as, “Implemented as a set of nested arrays that’s efficient at splitting and joining.”
// Array: Backed by a Java array, its elements are mutable, but it can’t change in size.
// ArrayBuffer: The “go to” class for a mutable, sequential collection. The amortized cost for appending elements is constant.
// ArrayStack: A last-in, first-out data structure. Prefer over Stack when performance is important.
// DoubleLinkedList: Like a singly linked list, but with a prev method as well. The documentation states, “The additional links make element removal very fast.”
// LinkedList: A mutable, singly linked list.
// ListBuffer: Like an ArrayBuffer, but backed by a list. The documentation states, “If you plan to convert the buffer to a list, use ListBuffer instead of ArrayBuffer.” Offers constant-time prepend and append; most other operations are linear.
// MutableList: A mutable, singly linked list with constant-time append.
// Queue: A first-in, first-out data structure.
// Stack: A last-in, first-out data structure. (The documentation suggests that an ArrayStack is slightly more efficient.)
// StringBuilder: Used to build strings, as in a loop. Like the Java StringBuilder.


// Mutable Sequences:
// 1. Array
// 2. ArrayBuffer
// 3. ArrayStack
// 4. DoubleLinkedList
// 5. LinkedList
// 6. ListBuffer
// 7. MutableList
// 8. Queue
// 9. Stack
// 10. StringBuilder


// Immutable Sequences:
// 1. List
// 2. Queue
// 3. Range
// 4. Stack
// 5. Stream
// 6. String
// 7. Vector


// Indexed Sequences:
// 1. Range
// 2. String
// 3. Vector
// 4. Array
// 5. ArrayBuffer
// 6. ArrayStack
// 7. StringBuilder


// Linear Sequences:
// 1. List
// 2. Queue
// 3. Stack
// 4. Stream
// 5. DoubleLinkedList
// 6. LinkedList
// 7. ListBuffer
// 8. MutableList
// 9. Queue
// 10. Stack


// LIST:

// Different Ways to Create and Populate a List:

// 1
scala> val list = 1 :: 2 :: 3 :: Nil list: List[Int] = List(1, 2, 3)
// 2
scala> val list = List(1, 2, 3) x: List[Int] = List(1, 2, 3)
// 3a
scala> val x = List(1, 2.0, 33D, 4000L)
x: List[Double] = List(1.0, 2.0, 33.0, 4000.0)

// 3b
scala> val x = List[Number](1, 2.0, 33D, 4000L)
x: List[java.lang.Number] = List(1, 2.0, 33.0, 4000)
// 4
scala> val x = List.range(1, 10)
x: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
scala> val x = List.range(0, 10, 2) x: List[Int] = List(0, 2, 4, 6, 8)
// 5
scala> val x = List.fill(3)("foo")
x: List[String] = List(foo, foo, foo)
// 6
scala> val x = List.tabulate(5)(n => n * n) x: List[Int] = List(0, 1, 4, 9, 16)
// 7
scala> val x = collection.mutable.ListBuffer(1, 2, 3).toList x: List[Int] = List(1, 2, 3)
// 8
scala> "foo".toList
res0: List[Char] = List(f, o, o)


// Creating a Mutable List
import scala.collection.mutable.ListBuffer
var fruits = new ListBuffer[String]()
// add one element at a time to the ListBuffer
fruits += "Apple" 
fruits += "Banana" 
fruits += "Orange"

// add multiple elements
fruits += ("Strawberry", "Kiwi", "Pineapple") // remove one element
fruits -= "Apple"
// remove multiple elements
fruits -= ("Banana", "Orange")
// remove multiple elements specified by another sequence
fruits --= Seq("Kiwi", "Pineapple")

You can delete elements by position:
scala> x.remove(0) 
res2: Int = 1


// Merging (Concatenating) Lists
val c = a ++ b
val c = a ::: b
val c = List.concat(a, b)


// Using Stream, a Lazy Version of a List
scala> val stream = 1 #:: 2 #:: 3 #:: Stream.empty
stream: scala.collection.immutable.Stream[Int] = Stream(1, ?)

// The REPL output shows that the stream begins with the number 1 but uses a ? to denote the end of the stream. 
// This is because the end of the stream hasn’t been evaluated yet.
val a = Stream from 1 // Infinite stream

scala> val stream = (1 to 100000000).toStream
stream: scala.collection.immutable.Stream[Int] = Stream(1, ?)

// Calls to the following strict methods are evaluated immediately and can easily cause java.lang.OutOfMemoryError errors:
stream.max 
stream.size 
stream.sum


// Sorting Arrays
scala.util.Sorting.quickSort(arr)
// Notice that quickSort sorts the Array in place; there’s no need to assign the result to a new variable.

// If the type an Array is holding doesn’t have an implicit Ordering, you can either modify it to mix in the 
// Ordered trait (which gives it an implicit Ordering), or sort it using the sorted, sortWith, or sortBy methods. 

class Person (var name: String) extends Ordered [Person] {
    override def toString = name
    // return 0 if the same, negative if this < that, positive if this > that
    def compare (that: Person) = { 
        if (this.name == that.name) {
            0
        }
        else if (this.name > that.name) {
            1
        }
        else {
            −1
        } 
    }
}

// Creating Multidimensional Arrays
val a = Array.ofDim[String](rows, cols)