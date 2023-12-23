package entity;

import dto.OrderDetailsDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Orders {
    private String orderId;
    private String date;
    private String customerId;
    private List<OrderDetail> list;
}
