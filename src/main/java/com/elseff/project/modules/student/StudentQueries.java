package com.elseff.project.modules.student;

public final class StudentQueries {
    public static final String CREATE_TABLE_STUDENT = """
            DROP TABLE IF EXISTS student;
                      
            CREATE TABLE student(
                id BIGSERIAL NOT NULL,
                name VARCHAR(255) NOT NULL,
                CONSTRAINT pk_user PRIMARY KEY (id)
            );
                        
            ALTER TABLE student
            ALTER COLUMN id SET DEFAULT nextval('student_id_seq');
            """;

    public static final String INSERT_INTO_STUDENT = """
            INSERT INTO student(name) VALUES ('%s');
            """;

    public static final String SELECT_FROM_STUDENT = """
            SELECT * FROM student;
            """;

    public static final String SELECT_FROM_STUDENT_BY_ID = """
            SELECT * FROM student
            WHERE id = %d
            """;

    public static final String UPDATE_STUDENT_NAME = """
            UPDATE student SET name = '%s'
            WHERE id = %d;
            """;

    public static final String DELETE_FROM_STUDENT = """
            DELETE FROM student WHERE id=%d
            """;
}
