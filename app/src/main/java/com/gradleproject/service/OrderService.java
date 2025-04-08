package com.gradleproject.service;

import com.gradleproject.model.AuditEntry;
import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    /**
     * Processes an order. This method runs within a transactional context.
     * The propagation is REQUIRED (default), meaning it will join an existing transaction or create a new one if none exists.
     * The isolation level is set to READ_COMMITTED to prevent dirty reads.
     *
     * @param order the order to process.
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor = Exception.class)
    public void processOrder(Order order) {
        // Perform operations such as:
        // - Validating the order
        // - Updating inventory
        // - Charging the customer's account
        // These operations are executed within the same transaction.
    }

    /**
     * Logs order activity without modifying the database.
     * This method executes within an existing transaction if one exists, otherwise, it runs non-transactionally.
     *
     * @param orderId the ID of the order.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void logOrderActivity(Long orderId) {
        // Log order activity.
    }

    /**
     * Processes a separate audit log entry in a new, independent transaction.
     * This method suspends any existing transaction and starts a new one.
     *
     * @param auditEntry the audit entry to record.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordAuditEntry(AuditEntry auditEntry) {
        // Write audit entry to the audit log.
    }

}
