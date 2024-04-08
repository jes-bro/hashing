/**
 * A doubly linked list interface.
 *
 * This interface specifies a doubly linked list.
 *
 * @param T the type of the data in each linked list node.
 *
 */


interface DoublyLinkedListUniversal<T> {
    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T)

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T)

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists.
     */
    fun popFront(): T?

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists.
     */
    fun popBack(): T?

    /**
     * @return the value at the front of the list or nil if none exists.
     */
    fun peekFront(): T?

    /**
     * @return the value at the back of the list or nil if none exists.
     */
    fun peekBack(): T?

    /**
     * @return true if the list is empty and false otherwise.
     */
    fun isEmpty(): Boolean

}

/**
 * A doubly linked list class.
 *
 * This class implements a doubly linked list with
 * pushing, popping and peaking functionalities for
 * both the head and the tail.
 *
 * @param T the type of the data in each linked list node.
 *
 */
class DoublyLinkedList<T>: DoublyLinkedListUniversal<T> {
    // The head of the doubly linked list
    internal var head: LinkedListNode<T>?=null
    // The tail of the doubly linked list
    internal var tail: LinkedListNode<T>?=null
    // The linked list node class
    internal class LinkedListNode<T>(val data: T, var next: LinkedListNode<T>?, var prev: LinkedListNode<T>?)

    /**
     * Adds the element [data] to the front of the linked list.
     * @param data The information being stored in the new head
     * linked list node.
     */
    override fun pushFront(data: T) {
        val newNode = LinkedListNode(data = data, next = this.head, prev = null)
        if (this.head == null) {
            this.tail = newNode
        } else {
            this.head?.prev = newNode
        }
        this.head = newNode
    }

    /**
     * Adds the element [data] to the back of the linked list.
     * @param data The information being stored in the new tail
     * linked list node.
     */
    override fun pushBack(data: T) {
        val newNode = LinkedListNode(data = data, next = null, prev = this.tail)
        if (this.head == null) {
            head = newNode
        } else {
            this.tail?.next = newNode
        }
        this.tail = newNode
    }

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists.
     */
    override fun popFront(): T? {
        if (this.isEmpty()) {
            return null
        } else {
            val value = this.head?.data
            if (this.head?.next == null) {
                this.tail = null
            }
            this.head = head?.next
            this.head?.prev = null
            return value
        }
    }

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists.
     */
    override fun popBack(): T? {
        if (this.isEmpty()) {
            return null
        } else {
            val newLast = this.tail?.prev
            if (this.tail?.prev == null) {
                this.head = null
            }
            val elemAtBack = this.tail?.data
            newLast?.next = null
            this.tail = newLast
            return elemAtBack
        }

    }

    /**
     * Shows the data stored at the front of the list.
     * @return the value at the front of the list or nil if none exists.
     */
    override fun peekFront(): T? {
        return this.head?.data
    }

    /**
     * Shows the data stored at the front of the list.
     * @return the value at the back of the list or nil if none exists.
     */
    override fun peekBack(): T? {
        return this.tail?.data
    }

    /**
     * Checks if the linked list is empty.
     * @return true if the list is empty and false otherwise.
     */
    override fun isEmpty(): Boolean {
        return this.head == null
    }

}