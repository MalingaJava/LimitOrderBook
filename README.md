# LimitOrderBook
Limit order book POC

Please read the OrderBook.java class Java doc comments for more information.

1. Use case 1: Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently).\
   Implemented in <code>public void putOrder(final Order order)</code> method.
2. Use case 2: Given an order id, remove an Order from the OrderBook (order deletions are expected to occur\
   at ap- proximately 60% of the rate of order additions).\
   Implemented in <code>public void removeOrderById(final Map<Double, List<Order>> map, final Long orderId)</code> method.
3. Use case 3: Given an order id and a new size, modify an existing order in the book to use the new size\
   (size modifications do not effect time priority).\
   Implemented in <code>public void modSizeByOrderId(final Map<Double, List<Order>> map, final Long orderId,
   final Long newSize)</code> method.
4. Use case 4: Given a side and a level (an integer value >0) return the price for that level\
   (where level 1 represents the best price for a given side). For example, given side=B\
   and level=2 return the second best bid price.\
   Implemented in <code>public Double getPriceBySideAndLevel(final OrderSide side, int level)</code> method.
5. Use case 5: Given a side and a level return the total size available for that level.\
   Implemented in <code>public Long getTotalSizeBySideAndLevel(final OrderSide side, final int level)</code> method.
6. Use case 6: Given a side return all the orders from that side of the book, in level- and time-order.\
   Implemented in <code>public void printMap(final OrderSide side)</code> method.

Note: Data structures justification and more technical details we will discuss later during the interview.

# How to run
There is a simple main method to demonstrate the use cases. 
<code>./gradlew run</code>
Or if you have gradle 7.5.1
<code>gradle run</code>

**Note: Please note that before compilation gradle will run Checkstyle, SpotBug and PMD to make sure\
code is in good quality.**

# Answer to the Part B of the assignment
Question:
Please suggest (but do not implement) modifications or additions to the Order and/or OrderBook classes 
to make them better suited to support real-life, latency-sensitive trading operations.

Answer:


# TODO
1. Unit tests for all use cases: Due to time constraints didn't had time to write the unit tests. Sorry for that.
2. Logging framework: Add logging framework to log the messages instead of System.out.println