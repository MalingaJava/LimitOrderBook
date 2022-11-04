package limitorderbook;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class OrderBook {
    // Descending
    private ConcurrentNavigableMap<Double, List<Order>> bid;
    // Ascending
    private ConcurrentNavigableMap<Double, List<Order>> offer;

    // TODO: Add default constructor
    public OrderBook(final int orderBookSize) {
        // TODO: Validate orderBookSize
        // TODO: ConcurrentSkipListMap vs SynchronizedSortedMap vs TreeMap
        // TODO: Use pollFirstEntry method to limit the map
        bid = new ConcurrentSkipListMap<>((k1, k2) -> Double.compare(k2, k1));
        offer = new ConcurrentSkipListMap<>();
    }

    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook(1);
        orderBook.bid.put(10.0, new LinkedList<>(List.of(new Order().id(1).price(10).size(10))));
        orderBook.bid.put(9.0, new LinkedList<>(List.of(new Order().id(2).price(9))));
        orderBook.bid.put(15.0, new LinkedList<>(List.of(new Order().id(4).price(15).size(10))));
        System.out.println("Initial Map : " + orderBook.bid);

        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()).size(40));
        System.out.println("After Add Map : " + orderBook.bid);
        Long size = orderBook.getTotalSizeBySideAndLevel(OrderSide.BID, 1);
        System.out.println("Total Size: " + size);
        orderBook.removeOrderById(orderBook.bid, 4L);
        System.out.println("After Remove Map : " + orderBook.bid);
        orderBook.modSizeByOrderId(orderBook.bid, 100L, 1000L);
        System.out.println("After Size Change Map : " + orderBook.bid);
        Double price = orderBook.getPriceBySideAndLevel(OrderSide.BID, 2);
        System.out.println("Price: " + price);
        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()).size(41));
        orderBook.printMap(OrderSide.BID);
    }

    // TODO: Add javadoc
    public void putOrder(final Order order) {
        if (order.side() == OrderSide.BID.asChar()) {
            processAdd(bid, order);
        } else if (order.side() == OrderSide.ASK.asChar()) {
            processAdd(offer, order);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    public void removeOrderById(final Map<Double, List<Order>> map, final Long orderId) {
        map.forEach((key, value) -> {
            value.removeIf(s -> s.id() == orderId);
        });
    }

    // TODO: long instead of Long
    public void modSizeByOrderId(final Map<Double, List<Order>> map, final Long orderId,
                                 final Long newSize) {
        map.forEach((key, value) -> value.stream().filter(order -> order.id() ==
                orderId).forEachOrdered(order -> order.size(newSize)));
    }

    public Double getPriceBySideAndLevel(final OrderSide side, int level) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            return getSize(bid, level);
        } else if (side.asChar() == OrderSide.ASK.asChar()) {
            return getSize(offer, level);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    private Double getSize(final Map<Double, List<Order>> map, int level){
        Double[] myArray = map.keySet().toArray(new Double[0]);
        return myArray[--level];
    }

    public Long getTotalSizeBySideAndLevel(final OrderSide side, int level) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            return getTotalSize(bid, level);
        } else if (side.asChar() == OrderSide.ASK.asChar()) {
            return getTotalSize(offer, level);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    private Long getTotalSize(final Map<Double, List<Order>> map, int level){
        Double[] myArray = bid.keySet().toArray(new Double[0]);
        Double price = myArray[--level];
        return bid.get(price).stream().mapToLong(Order::size).sum();
    }

    public void printMap(final OrderSide side) {
        if (side.asChar() == OrderSide.BID.asChar()) {
            printOrderBook(bid);
        } else if (side.asChar() == OrderSide.ASK.asChar()) {
            printOrderBook(offer);
        } else {
            // TODO: Log this.
            throw new RuntimeException("Invalid side exception");
        }
    }

    private void printOrderBook(final Map<Double, List<Order>> map){
        map.forEach((key, value) -> {
            value.forEach(System.out::println);
        });
    }

    private void processAdd(final Map<Double, List<Order>> map, final Order order) {
        List<Order> orderList = map.computeIfAbsent(order.price(), k -> new LinkedList<>());
        orderList.add(order);
    }
}
