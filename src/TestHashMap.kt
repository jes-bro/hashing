/**
 * Test the HashMap class and AssociativeArray interface.
 */
import org.junit.Test
import org.junit.Assert.*

/**
 * A class to test the HashMap class and the underlying AssociativeArray interface.
 */
class TestHashMap {

    /**
     * Test whether contains returns true when a key is in the hashmap and
     * false when the key is not in the hashmap.
     */
    @Test
    fun testContains() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, String>(keys)
        assertEquals("Contains returns true for a key in the keys list.", true,  map.contains("A"))
        assertEquals("Contains returns false for a key not in the keys list.", false, map.contains("D"))
    }

    /**
     * Test whether set correctly adds a key value pair to the hashmap.
     */
    @Test
    fun testSet() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, String>(keys)
        map.set("D", "M")
        assertEquals("The key value pair is added to the hashmap.", "M", map.get("D", map.buckets))
        map.set("D", "V")
        assertEquals("Value is overwritten when setting the val of an existing key.", "V", map.get("D", map.buckets))
    }

    /**
     * Test whether get retrieves the proper key value pair in the hashmap.
     */
    @Test
    fun testGet() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, Int>(keys)
        assertEquals("Get returns null when the key has no value mapped to it.", null, map.get("A", map.buckets))
        map.set("C", 1)
        assertEquals("Get returns the value associated with an existing key.", 1, map.get("C", map.buckets))
    }

    /**
     * Test whether remove removes the designated key value pair (the input to the function) from the hashmap.
     */
    @Test
    fun testRemove() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, Int>(keys)
        map.set("A", 1)
        map.remove("A")
        assertEquals("Remove removes the key from the keys mutable list.",false, map.contains("A"))
        assertEquals("Remove removes the key value pair from the map.",null, map.get("A",map.buckets))
    }

    /**
     * Test that size returns the number of key value pairs in the hashmap.
     */
    @Test
    fun testSize() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, Int>(keys)
        map.set("A", 1)
        assertEquals("Size returns 1 when there is 1 element in the hashmap.", 1, map.size())
        map.remove("A")
        assertEquals("Size returns 0 when the hashmap is empty.", 0, map.size())
    }

    /**
     * Test that keyValuePairs returns all the key value pairs in the hashmap.
     */
    @Test
    fun keyValuePairs() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, Int>(keys)
        map.set("A", 1)
        var expected = mutableListOf(AssociativeArray.Pair("A",1))
        assertEquals("Key value pairs returns a list of all key value pairs when there is a single pair.", expected, map.keyValuePairs())
        map.set("B", 4)
        expected = mutableListOf(AssociativeArray.Pair("A",1), AssociativeArray.Pair("B",4))
        assertEquals("Key value pairs returns a list of all key value pairs when there are multiple pairs.", expected, map.keyValuePairs())
    }

    /**
     * Ensure that getBucket designates diverse buckets for key value pairs.
     */
    @Test
    fun getBucket() {
        val keys = mutableListOf("A", "B", "C")
        val map = HashMap<String, Int>(keys)
        map.set("A", 1)
        assertEquals("Get bucket returns the 0th bucket index if there is one bucket.", 0,map.getBucket("A"))
        map.set("B", 2)
        map.set("C", 3)
        map.set("D", 4)
        map.set("E", 5)
        // Doesn't have to be the case that A and D are in different buckets, but they are in this case.
        assertEquals("Get bucket returns indices that correspond to other buckets when there are more than 3 key value pairs in the map.", false,map.getBucket("D") == map.getBucket("A"))
    }

    /**
     * Test that increase bucket size doubles the number of buckets when the number of key value pairs stored
     * in any bucket is too large.
     */
    @Test
    fun testIncreaseBucketSize() {
        val keys = mutableListOf("A", "B", "C", "D","E")
        val map = HashMap<String, Int>(keys)
        map.set("B", 2)
        map.set("C", 3)
        map.set("D", 4)
        map.set("E", 5)
        map.set("A",1)
        assertEquals("Increase bucket size doubles the bucket size when there are too many elements in one bucket.",true,map.buckets.size > 1)
        assertEquals("The bucket size parameter is updated properly to match the actual number of buckets.", true, map.bucket_size==map.buckets.size)

    }

    /**
     * Test that redelegate buckets evenly distributes the key value pairs across buckets.
     */
    @Test
    fun testRedelegateBuckets() {
        val keys = mutableListOf("A", "B", "C", "D","E")
        val map = HashMap<String, Int>(keys)
        map.set("B", 2)
        map.set("C", 3)
        map.set("D", 4)
        map.set("E", 5)
        var currPair = map.buckets[0].head
        var size1 = 0
        while (currPair?.next != null) {
            size1++
            currPair = currPair.next
        }
        size1++

        currPair = map.buckets[1].head
        var size2 = 0
        while (currPair?.next != null) {
            size2++
            currPair = currPair.next
        }
        size2++

        assertEquals("Redelegate buckets generally evenly distributes key value pairs across buckets.",true, size1-1<=size2 && size2<=size1+1)
    }
    /**
     * *NOTE* I'm not testing the printBuckets function because it doesn't actually perform any computation. It's
     * just used for printing out the contents of the buckets in a formatted way that's easy to read.
     */
}