/**
 * Use a Lempel Ziv compression class to illustrate how much a binary string of text is
 * compressed based on its length and redundancy.
 */

/**
 * A class to conduct Lempel Ziv compression on an input string.
 */
class LempelZiv(private val input: String, originalKeys: MutableList<String>) {
    var map = HashMap<String, String>(originalKeys)
    private var substringCharToInt: MutableMap<String, Pair<String, Int>> = mutableMapOf("0" to Pair("1", 1), "1" to Pair("2",2))
    private var substringNum = 3
    var outputList: MutableList<String> = mutableListOf("0", "1")

    /**
     * Compress the input string.
     */
    fun compress() {
        var startIndex: Int = 2 // LHS of the first character
        var endIndex: Int = 3
        while (endIndex <= input.length) {
            //println("Current start index: $startIndex, current end index: $endIndex")
            var substring = input.substring(startIndex, endIndex)
            //println("Current substring: $substring")
            if (!map.contains(substring)) {
                //println("Does not contain current substring: $substring")
                //println("Substring $substring not in keys")
                var miniStartIndex: Int = 0 // LHS of the first character
                var miniEndIndex: Int = 1
                while (map.contains(substring.substring(miniStartIndex, miniEndIndex))) {
                    //println("Map contains ${substring.substring(miniStartIndex, miniEndIndex)}")
                    miniEndIndex++
                    }
                val encoding = makeEncoding(substring, miniStartIndex, miniEndIndex)
                map.set(substring, encoding)
                //println("Current buckets")
                //printBuckets()
                startIndex = startIndex + (substring.length)
                //println("New start index is: $startIndex")
                endIndex = startIndex + 1
                //println("New start index is: $endIndex")
            } else {
                //println("Contains current substring: $substring")
                endIndex++
            }
        }
    }

    /**
     * Make an encoding for a new substring in the input string.
     * @param substring The substring of the original string to create an encoding for.
     * @param miniStartIndex The starting index of the substring in the input string
     * @param miniEndIndex The ending index of the substring in the input string.
     * @return the encoding for the substring.
     */
    fun makeEncoding(substring: String, miniStartIndex: Int, miniEndIndex: Int): String {
        // Add encoding for new substring
        //printSubstringChar()
        val alreadyEncoded = substring.substring(miniStartIndex, miniEndIndex - 1)
        //println("alreadyEncoded: $alreadyEncoded")
        var firstEncoding = ""
        firstEncoding = substringCharToInt[alreadyEncoded]?.second.toString()
        //println("First encoding is: $firstEncoding")
        //println("Second encoding is: ${substring.substring(miniEndIndex - 1, miniEndIndex)}")
        val secondEncoding = substringCharToInt[substring.substring(miniEndIndex - 1, miniEndIndex)]?.first.toString()
        //println("Second encoding is: $secondEncoding")
        substringCharToInt.set(substring, Pair(firstEncoding.plus(secondEncoding),substringNum))
        val secondBinPart = substring.substring(substring.lastIndex, substring.length)
        val firstBinPart = substringCharToInt[substring]?.first?.substring(0,1)
            ?.let { Integer.toBinaryString(it.toInt()) }
        outputList.add(firstBinPart.plus(secondBinPart))
        substringNum++
        return firstEncoding.plus(secondEncoding)

    }

    /**
     * Print the contents of the Lempel Ziv buckets.
     * (I forget why I made this and one in Associative Array
     * but I think there was a reason).
     */
    fun printBuckets() {
        for (bucket in map.buckets) {
            var currVal = bucket.head
            while (currVal?.next != null) {
                //println(AssociativeArray.Pair(currVal.data.key, currVal.data.value))
                currVal = currVal.next
            }
            println(AssociativeArray.Pair(currVal?.data?.key, currVal?.data?.value))
        }
    }

    /**
     * Print substringCharToInt for debugging purposes.
     */
    fun printSubstringChar() {
        for (key in substringCharToInt.keys) {
            //println("key $key, value: ${substringCharToInt[key]}")
        }
    }
}

/**
 * Calculate how much [lempelString] got compressed using the Lempel Ziv algorithm.
 * @param lempelString The input string to compress
 */
fun getCompressionStats(lempelString: String) {
    val originalKeys: MutableList<String> = mutableListOf()
    val compressor = LempelZiv(lempelString, originalKeys)
    compressor.map.set("0", "1")
    compressor.map.set("1", "2")
    compressor.compress()
    //compressor.printBuckets()
    //compressor.printSubstringChar()
    println(compressor.outputList)
    val compressedOutput = compressor.outputList.joinToString("")

    val originalLength = lempelString.length
    val compressedLength = compressedOutput.length
    val compressionRatio = originalLength.toDouble() / compressedLength

    println("Original String: $lempelString")
    println("Original Length: $originalLength")
    println("Compressed Length: $compressedLength")
    println("Compression Ratio: $compressionRatio")
    println("Compressed Output: ${compressor.outputList}")
}

/**
 * Test the amount of compression for input strings of different lengths.
 */
fun main() {
    var lempelString = "01000101110010100101"
    getCompressionStats(lempelString)
    lempelString = "0100010111001010010101000101110010100101"
    getCompressionStats(lempelString)
    lempelString = "01000101110010100101010001011100101001010100010111001010010101000101110010100101"
    getCompressionStats(lempelString)
}