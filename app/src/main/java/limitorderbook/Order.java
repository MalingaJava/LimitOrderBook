package limitorderbook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is the class to hold the order information.
 *
 * @author Malinga.
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
@ToString
public class Order implements Serializable {
    // id of order
    private long id;
    // price of order
    private double price;
    // B "Bid" or O "Offer"
    private char side;
    // size of order
    private long size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.price, price) == 0 &&
                side == order.side && size == order.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, side, size);
    }
}
