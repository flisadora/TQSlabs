package stack;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack stackWithElements;
    private TqsStack stackEmpty;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        stackWithElements = new TqsStack();
        stackWithElements.push("John Lennon");
        stackWithElements.push("Paul McCartney");
        stackWithElements.push("Ring Star");
        stackWithElements.push("George Harrison");

        stackEmpty = new TqsStack();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(stackEmpty.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void push() {
        stackWithElements.push("Isadora Loredo");
        assertEquals("Isadora Loredo",stackWithElements.peek());
    }

    @org.junit.jupiter.api.Test
    void pop() {
        stackWithElements.pop();
        assertEquals("Ring Star", stackWithElements.peek());

        assertThrows(NoSuchElementException.class, () -> {
            stackEmpty.pop();
        });
    }

    @org.junit.jupiter.api.Test
    void popOnEmptyStack() {
       assertThrows(NoSuchElementException.class, () -> {
            stackEmpty.pop();
        });
    }

    @org.junit.jupiter.api.Test
    void peek() {
        assertEquals("George Harrison", stackWithElements.peek());

        assertThrows(NoSuchElementException.class, () -> {
            stackEmpty.peek();
        });
    }

    @org.junit.jupiter.api.Test
    void peekOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stackEmpty.peek();
        });
    }

    @org.junit.jupiter.api.Test
    void stackSize() {
        assertEquals(4, stackWithElements.stackSize());
    }
}