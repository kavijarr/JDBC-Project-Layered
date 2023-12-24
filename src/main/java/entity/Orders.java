package entity;

import dto.OrderDetailsDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Orders {
    @Id
    private String orderId;
    private String date;

    @ManyToOne
    @JoinColumn(name = "customer_Id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetailList = new ArrayList<>();

    public Orders(String orderId, String date) {
        this.orderId = orderId;
        this.date = date;
    }
}
