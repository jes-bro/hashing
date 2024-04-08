/**
 * Implement a hashmap using an associative array
 * as an underlying data structure. The hashmap is a class
 * that inherits from the associative array interface.
 */
import kotlin.math.floor

/**
 * Represents a mapping of keys to values.
 * @param K the type of the keys
 * @param V the type of the values
 */
interface AssociativeArray<K, V> {
    val keys: MutableList<K>
    data class Pair<K, V> (val key: K, val value: V)
    val buckets: MutableList<DoublyLinkedList<Pair<K, V?>>>
    var bucket_size: Int
    /**
     * Insert the mapping from the key, [k], to the value, [v].
     * If the key already maps to a value, replace the mapping.
     */
    operator fun set(k: K, v: V) {

    }

    /**
     * @return true if [k] is a key in the associative array
     */
    operator fun contains(k: K): Boolean {
        //println("In contains")
        for (key in keys) {
            if (key == k) {
                //println("Contains key $k")
                return true
            }
        }
        //println("Does not contain key $k")
        return false
    }

    /**
     * @return the value associated with the key [k] or null if it doesn't exist
     */
    operator fun get(k: K, anyBuckets: MutableList<DoublyLinkedList<Pair<K, V?>>>): V? {
        //println("IN get")
        if (!this.contains(k)) {
            return null
        }
        val bucket: Int = getBucket(k)

        var bucketNum: Int = 1
        //println("PRINTING ANY BUCKETS")
        for (bucket in anyBuckets) {
            //println("Bucket number $bucketNum:")
            bucketNum+=1
            var currVal = bucket.head
            while (currVal?.next != null) {
                //println(kotlin.Pair(currVal.data.key, currVal.data.value))
                currVal = currVal.next
            }
            //println(kotlin.Pair(currVal?.data?.key, currVal?.data?.value))
        }

        var currKey = anyBuckets[bucket].head
        var theVal: V? = null
        //println("looking for key $k")
        while (currKey?.next != null) {
            if (currKey.data.key == k) {
                //println("found key $k")
                theVal = currKey.data.value
                //println("the val $theVal")
            }
            currKey = currKey.next
        }
        if (currKey?.data?.key == k) {
            if (currKey != null) {
                //println("found key $k")
                theVal = currKey.data.value
                //println("the val: $theVal")
            }
        }
        return theVal

    }

    /**
     * Remove the key, [k], from the associative array
     * @param k the key to remove
     * @return true if the item was successfully removed and false if the element was not found
     */
    fun remove(k: K): Boolean {
        if (!this.contains(k)) {
            //println("DOESNT CONTAIN KEYYYYYYYYY")
            return false
        }
        val kBucket = getBucket(k)
        var currPair = buckets[kBucket].head
        //println("CUR PAIR: ${currPair?.data}")
        if (currPair != null) {
            //println("GOT HEREEEEEEE")
            while (currPair?.next != null) {
                //println("Testing key ${currPair.data.key} against $k")
                if (currPair.data.key == k) {
                    //println("________________REMOVING KEY_________________")
                    if (buckets[kBucket].head == currPair) {
                        buckets[kBucket].head = buckets[kBucket].head?.next
                    } else {
                        currPair.prev?.next = currPair.next
                    }
                }
                currPair = currPair.next
            }
            if (buckets[kBucket].tail?.data?.key == k) {
                buckets[kBucket].popBack()
            }
        }
        keys.remove(k)
        return true
    }


    /**
     * @return the number of elements stored in the hash table
     */
    fun size(): Int {
        //println("In size")
        var size: Int = 0
        for (bucket in buckets) {
            var currVal = bucket.head
            if (currVal != null) {
                while (currVal?.next != null) {
                    //println("IN while")
                    size++
                    currVal = currVal.next
                }
                size++
            }
        }
        //println("size is: $size")
        return size
    }

    /**
     * @return the full list of key value pairs for the associative array
     */
    fun keyValuePairs(): List<AssociativeArray.Pair<K, V?>> {
        val fullList: MutableList<Pair<K, V?>> = mutableListOf()
        //println("In key value pairs")
        var bucketNum = 0
        for (bucket in buckets) {
            //println("in bucket number $bucketNum")
            bucketNum++
            var currVal = bucket.head
            //println(currVal)
            while (currVal?.next != null) {
                fullList.add(Pair(currVal.data.key, currVal.data.value))
                currVal = currVal.next
            }
            if (currVal != null) {
                fullList.add(Pair(currVal.data.key, currVal.data.value))
            }
        }
        return fullList.toList()
    }

    /**
     * Use division hash function to figure out which bucket [k] should map to
     * @param k the key to remove
     * @return the bucket to store the key value pair that corresponds to [k]
     */
    fun getBucket(k: K): Int {
        //println("In get bucket")
        //println(k)
        //println(bucket_size)
        //println(k.hashCode())
        //println("the num: $num")
        return k.hashCode() % bucket_size
        //println("Done")
    }

    /**
     * Use multiplication hash function to figure out which bucket [k] should map to
     * @param k the key to remove
     * @return the bucket to store the key value pair that corresponds to [k]
     */
    fun getBucketMultiplicationMethod(k: K): Int {
        val A = 0.618
        val m = 2
        val fractionalPart = (k.hashCode() * A) - floor(k.hashCode() * A)
        return Math.floor(m * fractionalPart).toInt()
    }
}

/**
 * Represents a mapping of keys to values in the form of a hashmap.
 * @param K the type of the keys
 * @param V the type of the values
 */
class HashMap<K, V>(override val keys: MutableList<K>) : AssociativeArray<K, V>  {
    override var bucket_size: Int = 1
    private val mapKeys = keys
    override public val buckets: MutableList<DoublyLinkedList<AssociativeArray.Pair<K, V?>>> = mutableListOf(DoublyLinkedList())


    /**
     * Use division hash function to figure out which bucket [k] should map to
     * @param k the key to remove
     * @return the bucket to store the key value pair that corresponds to [k]
     */
    fun increaseBucketSize() {
        //println("In increase bucket size")
        val oldSize = bucket_size
        bucket_size = bucket_size * 2
        //println("Bucket size has been incremented to $bucket_size")
        var sanityCheck = 1
        for (index in oldSize until bucket_size) {
            //println("$sanityCheck buckets added")
            sanityCheck++
            buckets.add(DoublyLinkedList())
        }
        redelegateBuckets()
    }

    /**
     * Rehash when the number of buckets is increased.
     */
    fun redelegateBuckets() {
        //println("In redelegate")
        // Save vals in buckets
        val allKeyValuePairs: List<AssociativeArray.Pair<K, V?>> = this.keyValuePairs()
        //println("allKeyValuePairs: $allKeyValuePairs")
        // Empty buckets
        for (bucket in buckets) {
            while (!bucket.isEmpty()) {
                bucket.popFront()
            }
        }
        //Add the values back to the buckets via rehashing
        for (pair in allKeyValuePairs) {
            //#println("redelegating pair $pair")
            val bucket: Int = getBucket(pair.key)
            //println("the bucket val is: $bucket")
            buckets[bucket].pushBack(pair)
        }
    }

    /**
     * Add key value pair to the hashmap. If [k] is already in the map, replace the existing value.
     * @param k the key in the pair.
     * @param v the value in the pair.
     */
    override fun set(k: K, v: V) {
        //println("In set")
        if (this.contains(k)) {
            this.remove(k)
        }
            keys.add(k)
        val bucketNum = getBucket(k)
        //println("list head: ${buckets[bucketNum].head?.data}")
        buckets[bucketNum].pushBack((AssociativeArray.Pair(k, v)))
        //println("list next: ${buckets[bucketNum].head?.next?.data}")
        //println("list next: ${buckets[bucketNum].head?.next?.next?.data}")


        if (this.size() / bucket_size > 3) {
            increaseBucketSize()
        }
        //printBuckets()
    }

    /**
     * Print out the contents of the buckets in a readable format.
     */
    fun printBuckets() {
        var bucketNum: Int = 1
        for (bucket in buckets) {
            println("Bucket number $bucketNum:")
            bucketNum+=1
            var currVal = bucket.head
            while (currVal?.next != null) {
                //println(Pair(currVal.data.key, currVal.data.value))
                currVal = currVal.next
            }
            println(Pair(currVal?.data?.key, currVal?.data?.value))
        }
    }

}

/**
 * Create a hashmap and print the buckets and contents.
 */
fun main() {
    val keys = mutableListOf("One", "Two", "Three")
    val myMap = HashMap<String, String>(keys)
    myMap.remove("Two")
    myMap.printBuckets()
    myMap.set("One", "1")
    myMap.set("Two", "2")
    myMap.set("Three", "3")
    println(myMap.bucket_size)
    println(myMap.keys)
}
