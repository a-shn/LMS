package com.company.repositories;

import com.company.models.Lesson;

import java.util.List;

public interface CourseLessonsRepository {
    public void addLessonsOfCourse(long courseId, int lessonsNumber);

    List<Lesson> getLessonsByCourseId(long courseId);

    void lessonWatched(long userId, long courseId, int lessonNumber);

    List<Lesson> usersPersonalisedLessons(long userId, long courseId);
}
