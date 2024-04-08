/**
 * Test the LempelZiv class.
 */
import org.junit.Test
import org.junit.Assert.*

/**
 * A class to test the functions in the LempelZiv class.
 */
class TestLempelZiv {

    /**
     * Test that compress creates the correct binary encoding according to the LempelZiv algorithm.
     */
    @Test
    fun testCompress() {
        val lempelString = "01000101110010100101"
        val originalKeys: MutableList<String> = mutableListOf()
        val compressor = LempelZiv(lempelString, originalKeys)
        compressor.map.set("0", "1")
        compressor.map.set("1", "2")
        compressor.compress()
        val expectedOutput = mutableListOf("0", "1", "10", "11", "1001", "100", "1000", "1100", "1101")
        assertEquals("Make sure compress produces the same output stream as the YouTube video reference.",expectedOutput, compressor.outputList)
    }

    /**
     * Test that makeEncoding produces the correct encoding for a substring of the string
     * that's being compressed.
     */
    @Test
    fun testMakeEncoding() {
        val lempelString = "01000101110010100101"
        val originalKeys: MutableList<String> = mutableListOf()
        val compressor = LempelZiv(lempelString, originalKeys)
        val encoding = compressor.makeEncoding("0100",2,4)
        assertEquals("Make encoding produces codes as expected. In this case, it codes 00 as 11 because 0 is the first code.", "11",encoding)
    }
}