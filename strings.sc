// String Interpolation

val name = "Deepak"
val age = 26
println("$name is $age years old.")

// You can also evaluate values

case class Student(name: String, score: Int)
val hannah = Student("Hannah", 93)
println(s"${hannah.name} has scored ${hannah.score}")

// The s that’s placed before each string literal is actually a method.

//The f string interpolator (printf style formatting)
val weight = 200
println(f"$name is $age years old, and weighs $weight%.2f pounds.")

// In addition to the s and f string interpolators, Scala includes another interpolator named raw. 
// The raw interpolator “performs no escaping of literals within the string.
s"foo\nbar" 
res0: String = 
foo
bar

raw"foo\nbar" 
res1: String = foo\nbar


// Prior to version 2.10, Scala didn’t include the string interpolation functionality just described. 
// If you need to use a release prior to Scala 2.10 for some reason, 
// the solution is to call the format method on a string, as shown in the following examples:

val s = "%s is %d years old".format(name, age)


// ********* Read about implicit classes  *************** //

implicit class StringImprovements(val s: String) { 
    def increment = s.map(c => (c + 1).toChar)
    def decrement = s.map(c => (c − 1).toChar)
}

scala> "HAL".increment 
res0: String = IBM