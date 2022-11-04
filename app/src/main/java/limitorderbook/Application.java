package limitorderbook;

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
        OrderBook orderBook = new OrderBook(10);
        System.out.println("Populating the Order Book");
        /*
        | Market: NYSE - New York Stock Exchange
        | Symbol: MFG - Mizuho Financial Group Inc. is this correct symbol ? :)
        --------------------------------------------------------------------
        | BID                               | OFFER
        --------------------------------------------------------------------
        | id: 1, price: 15.0, size: 10      | id: 6, price: 16.0, size: 10 |
        | id: 2, price: 14.9, size: 11      | id: 7, price: 17.0, size: 11 |
        | id: 3, price: 14.85, size: 12     | id: 8, price: 18.0, size: 12 |
        | id: 4, price: 14.85, size: 13     | id: 9, price: 18.0, size: 13 |
        | id: 5, price: 12.0, size: 14      | id: 10, price: 19.0, size: 14 |
        --------------------------------------------------------------------
         */
        // Please note that we have two different orders in bids and offer for same price
        orderBook.putOrder(new Order().id(1).price(15.0).side(OrderSide.BID.asChar()).size(10));
        orderBook.putOrder(new Order().id(2).price(14.9).side(OrderSide.BID.asChar()).size(11));
        orderBook.putOrder(new Order().id(3).price(14.85).side(OrderSide.BID.asChar()).size(12));
        orderBook.putOrder(new Order().id(4).price(14.85).side(OrderSide.BID.asChar()).size(13));
        orderBook.putOrder(new Order().id(5).price(12.0).side(OrderSide.BID.asChar()).size(14));

        orderBook.putOrder(new Order().id(6).price(16.0).side(OrderSide.OFFER.asChar()).size(10));
        orderBook.putOrder(new Order().id(7).price(17.0).side(OrderSide.OFFER.asChar()).size(11));
        orderBook.putOrder(new Order().id(8).price(18.0).side(OrderSide.OFFER.asChar()).size(12));
        orderBook.putOrder(new Order().id(9).price(18.0).side(OrderSide.OFFER.asChar()).size(13));
        orderBook.putOrder(new Order().id(10).price(19.0).side(OrderSide.OFFER.asChar()).size(14));

        System.out.println("---------------------- Initial Order Book --------------------------");
        System.out.println("Initial Order Book BID: ");
        orderBook.printMap(OrderSide.BID);
        System.out.println("Initial Order Book OFFER: ");
        orderBook.printMap(OrderSide.OFFER);
        System.out.println("---------------------------------------------------------------------");

        orderBook.putOrder(new Order().id(11).price(11.0).side(OrderSide.BID.asChar()).size(40));
        System.out.println("----------------- Order Book after adding new order -----------------");
        System.out.println("Order Book BID: ");
        orderBook.printMap(OrderSide.BID);
        System.out.println("Order Book OFFER: ");
        orderBook.printMap(OrderSide.OFFER);
        System.out.println("---------------------------------------------------------------------");

        System.out.println("-------------- Get the total size by side and level -----------------");
        // This should return 25 as size, because we have two orders for the same price in level 3,
        // Order 3 and order 4. total size 12 + 13 = 25.
        Long size = orderBook.getTotalSizeBySideAndLevel(OrderSide.BID, 3);
        System.out.println("Total size level 3 BID side: " + size);
        System.out.println("---------------------------------------------------------------------");

        System.out.println("------------------ Remove order by order id -------------------------");
        // In here removing the order id 4.
        orderBook.removeOrderById(4);
        System.out.println("After remove order id 3 from Order Book: ");
        orderBook.printMap(OrderSide.BID);
        System.out.println("---------------------------------------------------------------------");

        System.out.println("------------------ Modify the size by order id ----------------------");
        System.out.println("Before size change Order Book");
        orderBook.printMap(OrderSide.BID);
        orderBook.modSizeByOrderId(2, 1000);
        System.out.println("After size change Order Book order id = 2, new size = 1000");
        orderBook.printMap(OrderSide.BID);
        System.out.println("---------------------------------------------------------------------");

        System.out.println("--------------- Get the price by side and level ---------------------");
        Double price = orderBook.getPriceBySideAndLevel(OrderSide.BID, 2);
        System.out.println("Price: " + price);
        System.out.println("---------------------------------------------------------------------");

        System.out.println("---------------- Print the Order Book by side------------------------");
        orderBook.putOrder(new Order().id(3).price(15).side(OrderSide.BID.asChar()).size(41));
        System.out.println("Printing the Order Book Side BID: " + OrderSide.BID.asChar());
        orderBook.printMap(OrderSide.BID);
        System.out.println("---------------------------------------------------------------------");
    }
}
