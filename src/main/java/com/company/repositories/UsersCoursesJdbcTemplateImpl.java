package com.company.repositories;

import com.company.models.Course;
import com.company.models.Lesson;
import com.company.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsersCoursesJdbcTemplateImpl implements UsersCoursesRepository {
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users_courses WHERE user_id = (?)";
    //language=SQL
    private static final String SQL_INSERT = "INSERT INTO users_courses (user_id, course_id) VALUES (?,?)";

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CoursesRepository coursesRepository;

    public UsersCoursesJdbcTemplateImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    private RowMapper<Course> usersCoursesRowMapper = (row, rowNumber) -> {
        Long courseId = row.getLong("course_id");
        Optional<Course> optionalCourse = coursesRepository.findById(courseId);
        return optionalCourse.orElse(null);
    };

    @Override
    public void save(User user, Course course) {
        int updRows = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setLong(1, user.getUserId());
            statement.setLong(2, course.getCourseId());
            return statement;
        });

        if (updRows == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void save(User user, long courseId) {
        int updRows = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setLong(1, user.getUserId());
            statement.setLong(2, courseId);
            return statement;
        });

        if (updRows == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Course> getCoursesById(Long id) {
        return jdbcTemplate.query(SQL_SELECT_BY_ID, new Object[]{id}, usersCoursesRowMapper);
    }

    @Override
    public Boolean isUserInCourse(User user, long courseId) {
        List<Course> usersCourses = getCoursesById(user.getUserId());
        for (Course course: usersCourses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }
}
