package limitorderbook;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

/**
 * This is the implementation of a limit order book stores customer orders on
 * a price time priority basis. for more information please read
 * JavaTechnicalTest-OrderBook.pdf.
 *
 * @author Malinga.
 */
public class OrderBook implements Serializable {
    // This is ConcurrentNavigableMap why I choose this, we may discuss during the interview.
    private final ConcurrentNavigableMap<Double, List<Order>> bid;
    // This is ConcurrentNavigableMap why I choose this, we may discuss during the interview.
    private final ConcurrentNavigableMap<Double, List<Order>> offer;

    private int MAX_ORDER_BOOK_SIZE;

    /**
     * This constructor will instantiate bid/offer maps as ConcurrentSkipListMap.
     * Bid order book is sorted Descending order from price and.
     * Offer order book is sorted Ascending order from the price.
     * Also note that it will keep the same price orders in a LinkedList on time priority basis.
     */
    public OrderBook(){
        this.MAX_ORDER_BOOK_SIZE = 10;
        bid = new ConcurrentSkipListMap<>((k1, k2) -> Double.compare(k2, k1));
        offer = new ConcurrentSkipListMap<>();
    }

    /**
     * This constructor will instantiate bid/offer maps as ConcurrentSkipListMap.
     * Bid order book is sorted Descending order from price and.
     * Offer order book is sorted Ascending order from the price.
     * Also note that it will keep the same price orders in a LinkedList on time priority basis.
     *
     * @param orderBookSize order book size.
     */
    public OrderBook(final int orderBookSize) {
        this.MAX_ORDER_BOOK_SIZE = orderBookSize;
        bid = new ConcurrentSkipListMap<>((k1, k2) -> Double.compare(k2, k1));
        offer = new ConcurrentSkipListMap<>();
    }

    /**
     * This is the first use case of the assignment.
     * Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently).
     *
     * @param order order.
     */
    public void putOrder(final Order order) {
        if (order.side() == OrderSide.BID.asChar()) {
            processAdd(bid, order);
        } else if (order.side() == OrderSide.OFFER.asChar()) {
            processAdd(offer, order);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    /**
     * This is the second use case of the assignment.
     * Given an order id, remove an Order from the OrderBook (order deletions are expected to occur
     * at ap- proximately 60% of the rate of order additions).
     *
     * @param map     // TODO: need to remove this.
     * @param orderId order id.
     */
    public void removeOrderById(final Map<Double, List<Order>> map, final long orderId) {
        map.forEach((key, value) -> {
            value.removeIf(s -> s.id() == orderId);
        });
    }

    /**
     * This is the third use case of the assignment.
     * Given an order id and a new size, modify an existing order in the book to use the new size
     * (size modifications do not effect time priority).
     *
     * @param map     // TODO: Remove this
     * @param orderId order id.
     * @param newSize new size.
     */
    public void modSizeByOrderId(final Map<Double, List<Order>> map, final long orderId,
                                 final long newSize) {
        map.forEach((key, value) -> value.stream().filter(order -> order.id() ==
                orderId).forEachOrdered(order -> order.size(newSize)));
    }

    /**
     * This is the fourth use case of the assignment.
     * Given a side and a level (an integer value >0) return the price for that level
     * (where level 1 represents the best price for a given side). For example, given side=B
     * and level=2 return the second best bid price.
     *
     * @param side  side.
     * @param level level.
     * @return total size.
     */
    public Double getPriceBySideAndLevel(final OrderSide side, int level) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            return getPrice(bid, level);
        } else if (side.asChar() == OrderSide.OFFER.asChar()) {
            return getPrice(offer, level);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    /**
     * This is the fifth use case of the assignment.
     * Given a side and a level return the total size available for that level.
     *
     * @param side  side.
     * @param level level.
     * @return total size.
     */
    public Long getTotalSizeBySideAndLevel(final OrderSide side, final int level) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            return getTotalSize(bid, level);
        } else if (side.asChar() == OrderSide.OFFER.asChar()) {
            return getTotalSize(offer, level);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    /**
     * This is the sixth use case of the assignment.
     * Given a side return all the orders from that side of the book, in level- and time-order.
     *
     * @param side side.
     */
    public void printMap(final OrderSide side) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            printOrderBook(bid);
        } else if (side.asChar() == OrderSide.OFFER.asChar()) {
            printOrderBook(offer);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    /**
     * Helper method to get the price of any given level of an order book.
     *
     * @param map   order book.
     * @param level level.
     * @return price.
     */
    private Double getPrice(final Map<Double, List<Order>> map, int level) {
        Double[] myArray = map.keySet().toArray(new Double[0]);
        return myArray[--level];
    }

    /**
     * Helper method to get the total size of any given level of an order book.
     *
     * @param map   order book.
     * @param level level.
     * @return total size.
     */
    private Long getTotalSize(final Map<Double, List<Order>> map, int level) {
        Double[] myArray = map.keySet().toArray(new Double[0]);
        Double price = myArray[--level];
        return map.get(price).stream().mapToLong(Order::size).sum();
    }

    /**
     * Helper method to print any given order book sorted order.
     *
     * @param map order book.
     */
    private void printOrderBook(final Map<Double, List<Order>> map) {
        Stream.of(map.entrySet())
                .forEach(System.out::println);
    }

    /**
     * Helper method to process the adding of an order to the order book.
     *
     * @param map   order book.
     * @param order oder to be added.
     */
    private void processAdd(final Map<Double, List<Order>> map, final Order order) {
        List<Order> orderList = map.computeIfAbsent(order.price(), k -> new LinkedList<>());
        orderList.add(order);
    }

    /**
     * Get the bid order book.
     *
     * @return bid order book.
     */
    public ConcurrentNavigableMap<Double, List<Order>> getBid() {
        return bid;
    }

    /**
     * Get offer order book.
     *
     * @return offer order book.
     */
    public ConcurrentNavigableMap<Double, List<Order>> getOffer() {
        return offer;
    }
}
