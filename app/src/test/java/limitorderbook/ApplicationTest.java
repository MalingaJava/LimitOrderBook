package limitorderbook;

import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationTest {
    @Test public void appHasAGreeting() {
        Application classUnderTest = new Application();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());

        /*
        public static void main(String[] args) {
        OrderBook orderBook = new OrderBook(1);
        bid.put(1, new LinkedList<>(List.of("Geeks1")));
        bid.put(4, new LinkedList<>(List.of("Geeks4")));
        bid.put(2, new LinkedList<>(List.of("Geeks2")));
        bid.put(9, new LinkedList<>(List.of("Geeks9")));
        System.out.println("Initial Map : " + bid);
        offer.put(1, new LinkedList<>(List.of("Geeks1")));
        offer.put(4, new LinkedList<>(List.of("Geeks4")));
        offer.put(2, new LinkedList<>(List.of("Geeks2")));
        offer.put(9, new LinkedList<>(List.of("Geeks9")));
        System.out.println("Initial Map : " + offer);
        }
         */
    }
}
