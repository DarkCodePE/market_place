package com.example.multisecurityspring.application.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePackDTO {
    private String name;
    @Builder.Default
    private List<Long> products = new ArrayList<>();
}
