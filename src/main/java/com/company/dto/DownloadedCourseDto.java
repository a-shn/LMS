package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DownloadedCourseDto {
    private String courseDirectory;
    private Long courseId;
}
