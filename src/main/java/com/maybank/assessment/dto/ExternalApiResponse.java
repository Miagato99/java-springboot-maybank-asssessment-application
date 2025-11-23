package com.maybank.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiResponse {

    private Long userId;
    private Long id;
    private String title;
    private String body;
}
