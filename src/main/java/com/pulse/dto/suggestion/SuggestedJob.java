package com.pulse.dto.suggestion;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @Builder
public class SuggestedJob {

    private Long id;
    private String title;
    private String contractType;
    private String jobType;
    private String shortDescription;
}
