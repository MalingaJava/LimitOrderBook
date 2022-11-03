package limitorderbook;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class OrderBook {
    // Descending
    private static ConcurrentNavigableMap<Double, List<Order>> bid;
    // Ascending
    private static ConcurrentNavigableMap<Double, List<Order>> offer;

    // TODO: Add default constructor
    public OrderBook(final int orderBookSize) {
        // TODO: Validate orderBookSize
        // TODO: ConcurrentSkipListMap vs SynchronizedSortedMap vs TreeMap
        // TODO: Use pollFirstEntry method to limit the map
        bid = new ConcurrentSkipListMap<>((k1, k2) -> Double.compare(k2, k1));
        offer = new ConcurrentSkipListMap<>();
    }

    // TODO: Add javadoc
    public void putOrder(final Order order) {
        if (order.side() == OrderSide.BID.asChar()) {
            processAdd(bid, order);
        } else if (order.side() == OrderSide.ASK.asChar()) {
            processAdd(offer, order);
        } else {
            // TODO: Log this.
        }
    }

    public void removeOrderById(final Map<Double, List<Order>> map, final Long orderId) {
        map.forEach((key, value) -> {
            boolean b = value.removeIf(s -> s.id() == orderId);
            System.out.println("Removed: " + b);
        });
    }

    // TODO: long instead of Long
    public void modSizeByOrderId(final Map<Double, List<Order>> map, final Long orderId,
                                 final Long newSize) {
        map.forEach((key, value) -> {
            value.stream().filter(order -> order.id() ==
                    orderId).forEachOrdered(order -> order.size(newSize));
        });
    }

    public Double getOrderBySideAndLevel(final OrderSide side, final int level) {

        if (side.asChar() == OrderSide.BID.asChar()) {
            Iterator<ConcurrentNavigableMap
                    .Entry<Double, List<Order>>> itr = bid.entrySet().iterator();
            int i = 1;
            while (itr.hasNext()) {
                ConcurrentNavigableMap
                        .Entry<Double, List<Order>> entry
                        = itr.next();
                if(i == level){
                    return entry.getKey();
                }
                i++;
            }
        } else if (side.asChar() == OrderSide.ASK.asChar()) {

        } else {
            // TODO: Log this.
        }
        return 0d;
    }

    private void processAdd(final Map<Double, List<Order>> map, final Order order) {
        List<Order> orderList = map.computeIfAbsent(order.price(), k -> new LinkedList<>());
        orderList.add(order);
    }

    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook(1);
        bid.put(10.0, new LinkedList<>(List.of(new Order().id(1).price(10).size(10))));
        bid.put(9.0, new LinkedList<>(List.of(new Order().id(2).price(9))));
//        bid.put(15.0, new LinkedList<>(List.of(new Order().id(3).price(15))));
        bid.put(15.0, new LinkedList<>(List.of(new Order().id(4).price(15))));
        System.out.println("Initial Map : " + bid);

        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()));
        System.out.println("After Add Map : " + bid);
        orderBook.removeOrderById(bid, 4L);
        System.out.println("After Remove Map : " + bid);
        orderBook.modSizeByOrderId(bid, 100L, 1000L);
        System.out.println("After Size Change Map : " + bid);
        Double price = orderBook.getOrderBySideAndLevel(OrderSide.BID, 2);
        System.out.println("Price: " + price);


//        offer.put(1, new LinkedList<>(List.of("Geeks1")));
//        offer.put(4, new LinkedList<>(List.of("Geeks4")));
//        offer.put(2, new LinkedList<>(List.of("Geeks2")));
//        offer.put(9, new LinkedList<>(List.of("Geeks9")));
//        System.out.println("Initial Map : " + offer);
    }

}
