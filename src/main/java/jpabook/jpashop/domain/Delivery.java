package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {
    @Id@GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;
    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 꼭 스트링으로 쓸 것.
    private DeliveryStatus status; //READY , COMP
}