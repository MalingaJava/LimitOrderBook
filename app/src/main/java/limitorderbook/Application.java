package limitorderbook;

import java.util.LinkedList;
import java.util.List;

/**
 * Main application.
 *
 * @author Malinga.
 */
public class Application {

    /**
     * Main method of the program.
     *
     * @param args JVM arguments.
     */
    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook(1);
        orderBook.getBid().put(10.0, new LinkedList<>(List.of(new Order().id(1).price(10).size(10))));
        orderBook.getBid().put(9.0, new LinkedList<>(List.of(new Order().id(2).price(9))));
        orderBook.getBid().put(15.0, new LinkedList<>(List.of(new Order().id(4).price(15).size(10))));
        System.out.println("Initial Map : " + orderBook.getBid());

        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()).size(40));
        System.out.println("After Add Map : " + orderBook.getBid());
        Long size = orderBook.getTotalSizeBySideAndLevel(OrderSide.BID, 1);
        System.out.println("Total Size: " + size);
        orderBook.removeOrderById(orderBook.getBid(), 4L);
        System.out.println("After Remove Map : " + orderBook.getBid());
        orderBook.modSizeByOrderId(orderBook.getBid(), 100L, 1000L);
        System.out.println("After Size Change Map : " + orderBook.getBid());
        Double price = orderBook.getPriceBySideAndLevel(OrderSide.BID, 2);
        System.out.println("Price: " + price);
        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()).size(41));
        orderBook.printMap(OrderSide.BID);
    }
}
