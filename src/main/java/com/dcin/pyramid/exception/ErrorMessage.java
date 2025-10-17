package com.dcin.pyramid.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;
}
