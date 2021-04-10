// in Scala you could write two for loops as follows to read every line in a file 
// and then operate on each character in each line:
for (line <- source.getLines) { 
  for {
    char <- line
    if char.isLetter
  } // char algorithm here ...
}

// But with Scala’s for loop mojo, you can write this code even more concisely:

for {
  line <- source.getLines 
  char <- line
  if char.isLetter
} // char algorithm here ...


// Scala Comprehensions
val nieces = List("emily", "hannah", "mercedes", "porsche")
for (n <- nieces) yield n.capitalize


// Scala collections also offer a zipWithIndex method that you can use to create a loop counter:
for ((e, count) <- a.zipWithIndex) { 
  println(s"$count is $e")
}

// How for loops are translated
// As you work with Scala, it’s helpful to understand how for loops are translated by the compiler. 
// The Scala Language Specification provides details on precisely how a for loop is translated under 
// various conditions. I encourage you to read the Specification for details on the rules, but a 
// simplification of those rules can be stated as follows:
// 1. A simple for loop that iterates over a collection is translated to a foreach method call on the collection.
// 2. A for loop with a guard (see Recipe 3.3) is translated to a sequence of a withFilter method call on the collection followed by a foreach call.
// 3. A for loop with a yield expression is translated to a map method call on the col‐ lection.
// 4. A for loop with a yield expression and a guard is translated to a withFilter method call on the collection, followed by a map method call.


// filter will take the original collection and produce a new collection, but withFilter will non-strictly 
// (i.e. lazily) pass unfiltered values through to later map/flatMap/withFilter calls, saving a second 
// pass through the (filtered) collection. Hence it will be more efficient when passing through to these 
// subsequent method calls.


// It’s true that Scala doesn’t have break and continue keywords, 
// but it does offer similar functionality through scala.util.control.Breaks.

import util.control.Breaks._

// To implement a break, this Scala:

breakable {
  for (x <- xs) {
      if (cond) break
  } 
}

// To implement continue functionality, this Scala:
for (x <- xs) { 
  breakable { 
    if (cond) 
    break
  } 
}


// Note that break and breakable aren’t actually keywords; they’re methods in scala.util.control.Breaks. 
// In Scala the break method is declared as follows to throw an instance of a BreakControl exception when it’s called:

private val breakException = new BreakControl def break(): Nothing = { throw breakException }


// Nested loops and labeled breaks

import scala.util.control._ 

val Inner = new Breaks
val Outer = new Breaks

Outer.breakable {
  for (i <- 1 to 5) { 
    Inner.breakable {
      for (j <- 'a' to 'e') {
        if (i == 1 && j == 'c') Inner.break else println(s"i: $i, j: $j")
        if (i == 2 && j == 'b') Outer.break 
      }
    }   
  }
}


// The @switch annotation
// When writing simple match expressions, it’s recommend to use the @switch annotation. 
// This annotation provides a warning at compile time if the switch can’t be compiled to a tableswitch or lookupswitch.
// Compiling your match expression to a tableswitch or lookupswitch is better for per‐ formance,
// because it results in a branch table rather than a decision tree. When a value is given to the expression,
// it can jump directly to the result rather than working through the decision tree.


class SwitchDemo {
  val i = 1
  val x = (i: @switch) match {
    case 1 => "One" 
    case 2 => "Two" 
    case _ => "Other"
  }
}


Matching Multiple Conditions with One Case Statement

val i = 5 
i match {
  case 1 | 3 | 5 | 7 | 9 => println("odd")
  case 2 | 4 | 6 | 8 | 10 => println("even") 
}


// Define a case statement for each pattern you want to match. 
// The following method shows examples of many different types of patterns you can use in match expressions:

def echoWhatYouGaveMe(x: Any): String = x match {
  
  // constant patterns
  case 0 => "zero"
  case true => "true"
  case "hello" => "you said 'hello'" case Nil => "an empty List"
  
  // sequence patterns
  case List(0, _, _) => "a three-element list with 0 as the first element"
  case List(1, _*) => "a list beginning with 1, having any number of elements" case Vector(1, _*) => "a vector starting with 1, having any number of elements"
  
  // tuples
  case (a, b) => s"got $a and $b"
  case (a, b, c) => s"got $a, $b, and $c"
  
  // constructor patterns
  case Person(first, "Alexander") => s"found an Alexander, first name = $first" case Dog("Suka") => "found a dog named Suka"
  
  // typed patterns
  case s: String => s"you gave me this string: $s"
  case i: Int => s"thanks for the int: $i"
  case f: Float => s"thanks for the float: $f"
  case a: Array[Int] => s"an array of int: ${a.mkString(",")}"
  case as: Array[String] => s"an array of strings: ${as.mkString(",")}"
  case d: Dog => s"dog: ${d.name}"
  case list: List[_] => s"thanks for the List: $list" case m: Map[_, _] => m.toString
  
  // the default wildcard pattern
  case _ => "Unknown" 
}


// Adding if Expressions (Guards) to Case Statements

// Add an if guard to your case statement. Use it to match a range of numbers:
i match {
  case a if 0 to 9 contains a => println("0-9 range: " + a) 
  case b if 10 to 19 contains b => println("10-19 range: " + b) 
  case c if 20 to 29 contains c => println("20-29 range: " + c) 
  case _ => println("Hmmm...")
}

// Use it to match different values of an object:
num match {
  case x if x == 1 => println("one, a lonely number") 
  case x if (x == 2 || x == 3) => println(x)
  case _ => println("some other value")
}

stock match {
  case x if (x.symbol == "XYZ" && x.price < 20) => buy(x) 
  case x if (x.symbol == "XYZ" && x.price > 50) => sell(x) 
  case _ => // do nothing
}

try { 
  openAndReadAFile(filename)
} catch {
  case e: FileNotFoundException => println("Couldn't find that file.")
  case e: IOException => println("Had an IOException trying to read that file")
}
