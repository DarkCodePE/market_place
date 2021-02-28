package com.example.multisecurityspring.infrastructure.exception;

import com.example.multisecurityspring.infrastructure.utils.ErrorTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class ApiErrorResponse <T>{
    private ErrorTypes errorTypes;
    private T message;
}
