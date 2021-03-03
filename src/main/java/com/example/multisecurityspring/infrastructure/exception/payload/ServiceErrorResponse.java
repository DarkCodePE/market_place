package com.example.multisecurityspring.infrastructure.exception.payload;

import com.example.multisecurityspring.infrastructure.utils.ErrorTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceErrorResponse<T>{
    private ErrorTypes errorTypes;
    private T message;
}
