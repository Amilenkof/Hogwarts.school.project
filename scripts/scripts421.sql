/***Возраст студента не может быть меньше 16 лет.***/

ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 0);

/***Имена студентов должны быть уникальными и не равны нулю.***/
ALTER TABLE student
    ALTER COLUMN name SET  NOT NULL;
ALTER TABLE student
    ADD CONSTRAINT name_unique_constraint UNIQUE (name);

/***Пара “значение названия” - “цвет факультета” должна быть уникальной.***/

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique_constraint UNIQUE (name,color);

/***При создании студента без возраста ему автоматически должно присваиваться 20 лет.***/
ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;






