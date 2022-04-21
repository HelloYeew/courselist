package courselist.persistence;

import courselist.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A data access object for Course entities.
 * Requires a datanbase connection be supplied.
 */
public class CourseDao {
    // a connection to the database
    private Connection connection;

    /**
     * Initialize a new CourseDao with a JDBC connection to the database.
     * @param connection a connection to the database
     */
    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieve a course from the database given its id.
     * @param id
     * @return a Cource with the requested id
     */
    public Course get(int id) {
        // create the course to return
        Course course = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM course WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            // create a Course object from the first row of the ResultSet
            if (resultSet.next()) {
                course = makeCourse(id, resultSet);
            }
        } catch (SQLException ex) {
            // rethrow the exception with a descriptive message
            throw new RuntimeException("Problem getting course from database " + ex);
        }

        return course;
    }

    private Course makeCourse(int id, ResultSet resultSet) throws SQLException {
        Course course;
        String courseNumber = resultSet.getString("course_number");
        String title = resultSet.getString("title");
        int credits = resultSet.getInt("credits");
        double difficulty = resultSet.getDouble("difficulty");
        course = new Course(courseNumber, title, credits);
        course.setId(id);
        return course;
    }
}
