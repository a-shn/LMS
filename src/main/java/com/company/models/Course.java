package com.company.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Course {
    private Long courseId;
    private String sourceGeneralName;
    private String sourceCourseUrl;
    private String courseName;
}
