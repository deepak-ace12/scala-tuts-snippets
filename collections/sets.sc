BitSet: A set of “non-negative integers represented as variable-size arrays of bits packed into 64-bit words.” Used to save memory when you have a set of integers.
HashSet: The immutable version “implements sets using a hash trie”; the mutable version “implements sets using a hashtable.”
LinkedHashSet: A mutable set implemented using a hashtable. Returns elements in the order in which they were inserted.
ListSet: A set implemented using a list structure.
TreeSet: The immutable version “implements immutable sets using a tree.” The mutable version is a mutable SortedSet with “an immutable AVL Tree as underlying data structure.”
Set: Generic base traits, with both mutable and immutable implementations.
SortedSet: A base trait. (Creating a variable as a SortedSet returns a TreeSet.)


Mutable Sets:
All sets are mutable except LinkedHashSet

Immutable Sets:
All sets are immutable except ListSet