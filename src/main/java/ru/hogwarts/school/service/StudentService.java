package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Scope("singleton")
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Optional<Student> read(Long id) {
        logger.info("Was invoked method for read student");
        return studentRepository.findById(id);
    }

    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }


    public Collection<Student> findForAge(int age) {
        logger.info("Was invoked method for find student with age={}", age);
        return getAll().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find all students with age between{} and {}", min, max);
        logger.warn("This method can return empty result");
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> findAllStudensByFaculty(Long id) {
        logger.info("Was invoked method for find all students on faculty with id={}", id);
        return studentRepository.findByFaculty_Id(id);
    }

    public int countStudents() {
        logger.info("Was invoked method for counting all students");
        return studentRepository.getCountStudents();
    }

    public double getAgeAverage() {
        logger.info("Was invoked method for calculating average age about all students");

        return studentRepository.getAgeAverage();
    }

    public List<Student> getFiveLastStudents() {
        logger.info("Was invoked method for find 5 last students");

        return studentRepository.getFiveLastStudents();
    }

    public List<String> getAllStudentsWithNameStartsOnA() {
        logger.info("Was invoked method getAllStudentsWithNameStartsOnA");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .toList();
    }

    public double getAgeAverageStream() {
        logger.info("Was invoked method getAgeAverageStream");
        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            logger.error("studentList is empty");
        }
        return studentList.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(() -> new StudentNotFoundException("List of students is empty"));
    }

    //**Шаг 1**
//
//Создать эндпоинт для студентов (с любым url), который запускает метод сервиса (с любым названием).
// В методе сервиса необходимо получить список всех студентов и вывести их имена в консоль используя команду
// System.out.println(). При этом первые два имени вывести в основном потоке, второе и третье в параллельном потоке.
// А пятое и шестое во втором параллельном потоке. В итоге в консоли должен появиться список из шести имен в порядке,
// отличном от порядка в коллекции.
    public void printStudentsMultyThread() {
        List<String> names = getAll().stream().map(Student::getName).toList();
        System.out.println("names = " + names);
        printNextStudentName(names.get(0), names.get(1));
        Thread threadOne = new Thread(() -> {
            printNextStudentName(names.get(2));
            printNextStudentName(names.get(3));
        });
        Thread threadTwo = new Thread(() -> {
            printNextStudentName(names.get(4));
            printNextStudentName(names.get(5));
        });
        threadOne.start();
        threadTwo.start();
    }

    private void printNextStudentName(String... strings) {
        for (String string : strings) {
            System.out.println(Thread.currentThread().getName() + " " + string);
        }
    }
//**Шаг 2**
//
//Создать еще один эндпоинт и метод в сервисе. Но теперь вывод имени в консоль вынести в отдельный синхронизированный метод.
// И так же запустить печать в консоль первых двух имен в основном потоке, третьего и четвертого в параллельном потоке,
// четвертого и пятого во втором параллельном потоке.
//В итоге в консоли должен находиться список из имен в том же порядке, что и в коллекции.
    private synchronized void printNextStudentsNameSinchronized(String ... strings) {
        printNextStudentName(strings);
    }


    public void printStudentsMultyThreadSinhronized() {
        List<String> names = getAll().stream().map(Student::getName).toList();
        System.out.println("names = " + names);
        printNextStudentsNameSinchronized(names.get(0),names.get(1));
        Thread threadOne = new Thread(() -> {
            printNextStudentsNameSinchronized(names.get(2),names.get(3));
        });
        Thread threadTwo = new Thread(() -> {
            printNextStudentsNameSinchronized(names.get(4),names.get(5));
        });
        threadOne.start();
        threadTwo.start();
    }
}


