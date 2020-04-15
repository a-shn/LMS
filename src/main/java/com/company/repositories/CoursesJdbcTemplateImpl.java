package com.company.repositories;

import com.company.models.Course;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class CoursesJdbcTemplateImpl implements CoursesRepository {
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM courses WHERE course_id = (?)";
    //language=SQL
    private static final String SQL_INSERT = "INSERT INTO courses (source_general_name, source_course_url, course_name) VALUES (?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public CoursesJdbcTemplateImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    private RowMapper<Course> coursesRowMapper = (row, rowNumber) -> {
        Long courseId = row.getLong("course_id");
        String sourceGeneralName = row.getString("source_general_name");
        String sourceCourseUrl = row.getString("source_course_url");
        String courseName = row.getString("course_name");

        return new Course(courseId, sourceGeneralName, sourceCourseUrl, courseName);
    };


    @Override
    public Course save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updRows = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getSourceGeneralName());
            statement.setString(2, course.getSourceCourseUrl());
            statement.setString(3, course.getCourseName());
            return statement;
        }, keyHolder);

        if (updRows == 0) {
            throw new IllegalArgumentException();
        }
        return new Course((Long) Objects.requireNonNull(keyHolder.getKeys().get("course_id")),
                course.getSourceGeneralName(), course.getSourceCourseUrl(), course.getCourseName());
    }

    @Override
    public Optional<Course> findById(Long id) {
        try {
            Course course = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, coursesRowMapper);
            return Optional.ofNullable(course);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
