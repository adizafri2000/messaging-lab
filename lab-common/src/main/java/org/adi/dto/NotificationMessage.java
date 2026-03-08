package org.adi.dto;

import java.time.LocalDateTime;

public record NotificationMessage(
        String id,
        String content,
        LocalDateTime timestamp
) {}
