package com.gradleproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The Order entity represents a customer's order in the system.
 * It is mapped to the "orders" table in the database.
 *
 * <p>Key fields:
 * <ul>
 *   <li>{@code id} - The primary key for the order, automatically generated.</li>
 *   <li>{@code orderDate} - The date and time when the order was placed.</li>
 *   <li>{@code customerId} - The identifier of the customer who placed the order.</li>
 *   <li>{@code totalAmount} - The total monetary value of the order.</li>
 *   <li>{@code status} - The status of the order (e.g., "PENDING", "COMPLETED").</li>
 * </ul>
 *
 * <p>This entity uses JPA annotations to define its mapping, and Hibernate will manage its lifecycle.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_UNIQUE_ID")
    @SequenceGenerator(name = "GP_UNIQUE_ID", sequenceName = "GP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    /**
     * Default no-argument constructor required by JPA.
     */
    public Order() {
    }

    /**
     * Constructs an Order with the given details.
     *
     * @param orderDate   the date and time when the order was placed.
     * @param customerId  the identifier of the customer.
     * @param totalAmount the total monetary value of the order.
     * @param status      the status of the order.
     */
    public Order(LocalDateTime orderDate, Long customerId, BigDecimal totalAmount, String status) {
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

}
