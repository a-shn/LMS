package com.company.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Lesson {
    private Long courseId;
    private Integer lessonNumber;
    private String videoUrl;
    private Boolean isWatched;
}
