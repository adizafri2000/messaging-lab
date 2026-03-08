package org.adi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This is our "Contract".
 * Both the Producer and Consumer will refer to this same class.
 */
public record OrderEvent(
        String orderId,
        String productDescription,
        double amount,
        LocalDateTime createdAt
) implements Serializable {}