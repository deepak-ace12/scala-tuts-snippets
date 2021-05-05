// A predicate is simply a method, function, or anonymous function that takes one or more 
// parameters and returns a Boolean value.

def isEven (i: Int) = if (i % 2 == 0) true else false

// At a high level, Scala’s collection classes begin with the Traversable and Iterable traits, 
// and extend into the three main categories of sequences (Seq), sets (Set), and maps (Map). 
// Sequences further branch off into indexed and linear sequences.

// sequences branch off into two main categories: indexed sequences and linear sequences (linked lists). 
// An IndexedSeq indicates that random access of elements is efficient, such as accessing an Array element 
// as arr(5000). By default, specifying that you want an IndexedSeq creates a Vector
scala> val x = IndexedSeq(1,2,3)
x: IndexedSeq[Int] = Vector(1, 2, 3)

// A LinearSeq implies that the collection can be efficiently split into head and tail components,
// and it’s common to work with them using the head, tail, and isEmpty meth‐ ods. Note that creating 
// a LinearSeq creates a List, which is a singly linked list:
scala> val seq = scala.collection.immutable.LinearSeq(1,2,3) 
seq: scala.collection.immutable.LinearSeq[Int] = List(1, 2, 3)


Strict and lazy collections:

// Given that definition, collections can also be thought of in terms of being strict or lazy. 
// In a strict collection, memory for the elements is allocated immediately, and all of its 
// elements are immediately evaluated when a transformer method is invoked. In a lazy collection, 
// memory for the elements is not allocated immediately, and transformer methods do not construct 
// new elements until they are demanded.

// All of the collection classes except Stream are strict, but the other collection classes can be 
// converted to a lazy collection by creating a view on the collection.


// Methods organized by category
1. Filtering methods: Methods that can be used to filter a collection include 
collect, diff, distinct, drop, dropWhile, filter, filterNot, find, foldLeft, foldRight, head, headOption, 
init, intersect, last, lastOption, reduceLeft, reduceRight, remove, slice, tail, take, takeWhile, and union.

2. Transformer methods: Transformer methods take at least one input collection to create a new output collection, 
typically using an algorithm you provide. They include +, ++, −, −−, diff, distinct, collect, flatMap, map,
reverse, sortWith, takeWhile, zip, and zipWithIndex.

3. Grouping methods: These methods let you take an existing collection and create multiple groups from that one collection. 
These methods include groupBy, partition, sliding, span, splitAt, and unzip.

4. Informational and mathematical methods: These methods provide information about a collection, and include 
canEqual, contains, containsSlice, count, endsWith, exists, find, forAll, hasDefiniteSize, indexOf, indexOfSlice,
indexWhere, isDefinedAt, isEmpty, lastIndexOf, lastIndexOfSlice, lastIndexWhere, max, min, nonEmpty, product, segmentLength,
size, startsWith, sum. The methods foldLeft, foldRight, reduceLeft, and reduceRight can also be used with a function you 
supply to obtain information about a collection.

5. Others: A few other methods are hard to categorize, including par, view, flatten, foreach, and mkString. 
par creates a parallel collection from an existing collection; view creates a lazy view on a collection 
flatten converts a list of lists down to one list; foreach is like a for loop, letting you iterate over the 
elements in a collection; mkString lets you build a String from a collection.


Common collection methods
The following are the common methods to all collections via Traversable. 
The following symbols are used in the first column of the table:

• c refers to a collection 
• f refers to a function
• p refers to a predicate 
• n refers to a number
• op refers to a simple operation (usually a simple function)
