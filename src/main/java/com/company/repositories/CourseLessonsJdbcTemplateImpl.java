package com.company.repositories;

import com.company.models.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseLessonsJdbcTemplateImpl implements CourseLessonsRepository {
    private JdbcTemplate jdbcTemplate;
    //language=SQL
    private final String SQL_ADD_LESSON = "INSERT INTO course_lessons (course_id, lesson_number, video_url) VALUES (?,?,?)";
    //language=SQL
    private final String SQL_LESSONS_BY_COURSE_ID = "SELECT * FROM course_lessons WHERE course_id=?";
    //language=SQL
    private static final String SQL_LESSON_WATCHED = "INSERT INTO users_lessons (user_id, course_id, watched_lesson) VALUES (?,?,?)";
    //language=SQL
    private static final String SQL_WATCHED_LESSONS = "SELECT * FROM users_lessons WHERE course_id=? AND user_id=?";

    public CourseLessonsJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Lesson> lessonsRowMapper = (row, rowNumber) -> {
        Long courseId = row.getLong("course_id");
        Integer lessonNumber = row.getInt("lesson_number");
        String videoUrl = row.getString("video_url");

        return Lesson.builder().courseId(courseId).lessonNumber(lessonNumber).videoUrl(videoUrl).build();
    };

    private RowMapper<Integer> watchedLessonsRowMapper = (row, rowNumber) -> row.getInt("watched_lesson");

    public void addLessonsOfCourse(long courseId, int lessonsNumber) {
        String courseDirectory = "/coursesFolder/" + courseId + "/course/";
        for (int i = 1; i < lessonsNumber + 1; i++) {
            try (Stream<Path> walk = Files.walk(Paths.get(courseDirectory + "lesson_" + i))) {
                List<String> files = walk.filter(Files::isRegularFile)
                        .map(x -> x.toString()).collect(Collectors.toList());

                int finalI = i;
                int updRows = jdbcTemplate.update(connection -> {
                    PreparedStatement statement = connection
                            .prepareStatement(SQL_ADD_LESSON);
                    statement.setLong(1, courseId);
                    statement.setInt(2, finalI);
                    statement.setString(3, files.get(0));
                    return statement;
                });

                if (updRows == 0) {
                    throw new IllegalArgumentException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Lesson> getLessonsByCourseId(long courseId) {
        return jdbcTemplate.query(SQL_LESSONS_BY_COURSE_ID, new Object[]{courseId}, lessonsRowMapper);
    }


    @Override
    public void lessonWatched(long userId, long courseId, int lessonNumber) {
        int updRows = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_LESSON_WATCHED);
            statement.setLong(1, userId);
            statement.setLong(2, courseId);
            statement.setInt(3, lessonNumber);
            return statement;
        });

        if (updRows == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Lesson> usersPersonalisedLessons(long userId, long courseId) {
        List<Lesson> lessons = getLessonsByCourseId(courseId);
        List<Integer> watchedLessons = jdbcTemplate.query(SQL_WATCHED_LESSONS, new Object[]{courseId, userId}, watchedLessonsRowMapper);
        for (Lesson lesson: lessons) {
            if (watchedLessons.contains(lesson.getLessonNumber())) {
                lesson.setIsWatched(true);
            } else {
                lesson.setIsWatched(false);
            }
        }
        return lessons;
    }
}
