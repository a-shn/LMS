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

    public CourseLessonsJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Lesson> lessonsRowMapper = (row, rowNumber) -> {
        Long courseId = row.getLong("course_id");
        Integer lessonNumber = row.getInt("lesson_number");
        String videoUrl = row.getString("video_url");

        return Lesson.builder().courseId(courseId).lessonNumber(lessonNumber).videoUrl(videoUrl).build();
    };

    public void addLessonsOfCourse(long courseId, int lessonsNumber) {
        String courseDirectory = "/home/xiu-xiu/Desktop/SpringServiceFileFolder/coursesFolder/" + courseId + "/course/";
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
}
