HashMap: The immutable version “implements maps using a hash trie”; the mutable version “implements maps using a hashtable.
LinkedHashMap: “Implements mutable maps using a hashtable.” Returns elements by the order in which they were inserted.
ListMap: A map implemented using a list data structure. Returns elements in the opposite order by which they were inserted, as though each element is inserted at the head of the map.
Map: The base map, with both mutable and immutable implementations.
SortedMap: A base trait that stores its keys in sorted order. (Creating a variable as a SortedMap currently returns a TreeMap.)
TreeMap: An immutable, sorted map, implemented as a red-black tree.
WeakHashMap: A hash map with weak references, it’s a wrapper around sjava.util.WeakHashMap.

Mutable Maps:
1. HashMap
2. LinkedHashMap
3. ListMap
4. Map
5. WeakHashMap

Immutable Maps:
1. HashMap
2. ListMap
3. Map
4. SortedMap
5. TreeMap


// Traversing a Map
for ((k,v) <- ratings) println(s"key: $k, value: $v")

ratings.foreach {
case(movie, rating) => println(s"key: $movie, value: $rating") }

ratings.foreach(x => println(s"key: ${x._1}, value: ${x._2}"))
ratings.keys.foreach((movie) => println(movie))


// Operating on map values

scala> var x = collection.mutable.Map(1 -> "a", 2 -> "b")
x: scala.collection.mutable.Map[Int,String] = Map(2 -> b, 1 -> a)

scala> val y = x.mapValues(_.toUpperCase)
y: scala.collection.Map[Int,String] = Map(2 -> B, 1 -> A)

scala> val map = Map(1 -> 10, 2 -> 20, 3 -> 30)
map: scala.collection.mutable.Map[Int,Int] = Map(2 -> 20, 1 -> 10, 3 -> 30)

scala> val newMap = map.transform((k,v) => k + v) 
newMap: map.type = Map(2 -> 22, 1 -> 11, 3 -> 33)


// To test whether a value exists in a map, use the valuesIterator method to search for the value using exists 
// and contains:
scala> states.valuesIterator.exists(_.contains("ucky")) 
res0: Boolean = true

scala> states.valuesIterator.exists(_.contains("yucky")) 
res1: Boolean = false


// Filtering a Map
// Mutable maps
// You can filter the elements in a mutable map using the retain method to specify which elements should be retained:
scala> var x = collection.mutable.Map(1 -> "a", 2 -> "b", 3 -> "c")
x: scala.collection.mutable.Map[Int,String] = Map(2 -> b, 1 -> a, 3 -> c)

scala> x.retain((k,v) => k > 1)
res0: scala.collection.mutable.Map[Int,String] = Map(2 -> b, 3 -> c)

scala> x
res1: scala.collection.mutable.Map[Int,String] = Map(2 -> b, 3 -> c)

// Mutable and immutable maps
// When working with a mutable or immutable map, you can use a predicate with the filterKeys methods to define which map elements to retain. When using this method, remember to assign the filtered result to a new variable:
scala> val x = Map(1 -> "a", 2 -> "b", 3 -> "c")
x: scala.collection.mutable.Map[Int,String] = Map(2 -> b, 1 -> a, 3 -> c)

scala> val y = x.filterKeys(_ > 2)
y: scala.collection.Map[Int,String] = Map(3 -> c)


// You can also use all of the filtering methods. For instance, the map version of the filter method lets you 
// filter the map elements by either key, value, or both. The filter method provides your predicate a Tuple2, 
// so you can access the key and value as shown in these examples:

scala> var m = Map(1 -> "a", 2 -> "b", 3 -> "c")
m: scala.collection.immutable.Map[Int,String] = Map(1 -> a, 2 -> b, 3 -> c)
// access the key
scala> m.filter((t) => t._1 > 1)
res0: scala.collection.immutable.Map[Int,String] = Map(2 -> b, 3 -> c)
// access the value
scala> m.filter((t) => t._2 == "c")
res1: scala.collection.immutable.Map[Int,String] = Map(3 -> c)


// Sorting an Existing Map by Key or Value

// sorting by key using sortBy
ListMap(grades.toSeq.sortBy(_._1):_*)
// sorting using value using sortBy
ListMap(grades.toSeq.sortBy(_._2):_*)

// the _* shows that the method accepts varags


// You can also sort the keys in ascending or descending order using sortWith:
// low to high by key
scala> ListMap(grades.toSeq.sortWith(_._1 < _._1):_*) 
res0: scala.collection.immutable.ListMap[String,Int] =
Map(Al -> 85, Emily -> 91, Hannah -> 92, Kim -> 90, Melissa -> 95)
// high to low by key
scala> ListMap(grades.toSeq.sortWith(_._1 > _._1):_*) 
res1: scala.collection.immutable.ListMap[String,Int] =
Map(Melissa -> 95, Kim -> 90, Hannah -> 92, Emily -> 91, Al -> 85)


// low to high by value
scala> ListMap(grades.toSeq.sortWith(_._2 < _._2):_*) 
res0: scala.collection.immutable.ListMap[String,Int] =
Map(Al -> 85, Emily -> 91, Hannah -> 92, Kim -> 90, Melissa -> 95)
// high to low by value
scala> ListMap(grades.toSeq.sortWith(_._2 > _._2):_*) 
res1: scala.collection.immutable.ListMap[String,Int] =
Map(Melissa -> 95, Kim -> 90, Hannah -> 92, Emily -> 91, Al -> 85)


// The _* portion of the code takes a little getting used to. It’s used to convert the data so it will be 
// passed as multiple parameters to the ListMap or LinkedHashMap.


// Finding the Largest Key or Value in a Map
val grades = Map("Al" -> 80, "Kim" -> 95, "Teri" -> 85, "Julia" -> 90)


scala> grades.max
res0: (String, Int) = (Teri,85)
// Because the “T” in “Teri” is farthest down the alphabet in the names, it is returned.

// You can also call keysIterator to get an iterator over the map keys, and call its max method:
scala> grades.keysIterator.max 
res1: String = Teri
// You can find the same maximum by getting the keysIterator and using reduceLeft: 
scala> grades.keysIterator.reduceLeft((x,y) => if (x > y) x else y)
res2: String = Teri


// Because the values in the map are of type Int in this example, you can use this simple approach to get the largest value:
scala> grades.valuesIterator.max 
res4: Int = 95
// You can also use the reduceLeft approach, if you prefer: 
scala> grades.valuesIterator.reduceLeft(_ max _)
res5: Int = 95