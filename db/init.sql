CREATE TABLE students (
student_id INT AUTO_INCREMENT,
name varchar(255) NOT NULL,
email varchar(255) NOT NULL UNIQUE,
PRIMARY KEY (student_id)
);

CREATE TABLE courses (
course_id INT AUTO_INCREMENT,
student_id INT NOT NULL,
course_name varchar(255) NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL, 
PRIMARY KEY (course_id),
FOREIGN KEY (student_id) REFERENCES students(student_id)
);

CREATE TABLE assignments (
assignment_id INT AUTO_INCREMENT,
student_id INT NOT NULL,
course_id INT NULL,
title varchar(255) NOT NULL,
description TEXT,
deadline DATETIME NOT NULL,
status ENUM('pending','in-progress', 'completed','overdue') DEFAULT 'pending',
PRIMARY KEY (assignment_id),
FOREIGN KEY (course_id) REFERENCES courses (course_id),
FOREIGN KEY (student_id) REFERENCES students(student_id)
);

CREATE TABLE schedule (
schedule_id INT AUTO_INCREMENT,
course_id int NOT NULL,
weekday ENUM('monday','tuesday','wednesday','thursday','friday','saturday','sunday'),
start_time TIME NOT NULL,
end_time TIME NOT NULL,
PRIMARY KEY (schedule_id),
FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE reminders (
    reminder_id INT AUTO_INCREMENT,
    student_id INT NOT NULL,
    assignment_id INT NULL,
    message VARCHAR(255),
    reminder_time DATETIME NOT NULL,
    PRIMARY KEY (reminder_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id)
);