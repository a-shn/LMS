package com.company.repositories;

import com.company.models.Course;
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
    private static final String SQL_INSERT = "INSERT INTO users_courses (user_id, course_id, last_lesson) VALUES (?,?,?)";

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


//    @Override
    public void save(User user) {
        int updRows = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
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
}
