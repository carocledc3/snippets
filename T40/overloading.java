public class overloading {
    
    public static void main(String[] args) {
        TestStudent tester = new TestStudent();
        Student zairelle = new Student();
        
        tester.printStudent(zairelle);

        zairelle.setNameOfStudent("Zairelle De Leon");
        zairelle.addOneSubject();
        tester.changeCourseAndYear(zairelle, "BSHM-PCA", 2);
        tester.printStudent(zairelle);

        Student angel = new Student();
        angel.setNameOfStudent("Angel Mae Grueso");
        angel.addOneSubject();
        tester.changeCourseAndYear(angel, "BSCS", 2);
        tester.printStudent(angel);

        Student carlos = new Student();
        carlos.setNameOfStudent("Carlos Romeo Del Castillo");
        carlos.addOneSubject();
        tester.changeCourseAndYear(carlos, "BSIT-WebTech", 2);
        tester.printStudent(carlos);

        Student emilio = new Student();
        emilio.setNameOfStudent("Emilio Aguinaldo");
        emilio.addOneSubject();
        tester.changeCourseAndYear(emilio, "BSCrim", 3);
        tester.printStudent(emilio);

        Student[] sectionTest = {zairelle, angel, carlos, emilio};
        tester.printAllStudents(sectionTest);
        tester.printAllStudents(sectionTest, 3);
    }
}

class Student {
    static int numberOfStudents = 0;
    int getNumberOfStudents() { return numberOfStudents; }

    String nameOfStudent;
    String getNameOfStudent() { return this.nameOfStudent; }
    void setNameOfStudent(String newname) { this.nameOfStudent = newname; }

    String courseOfStudent;
    String getCourseOfStudent() { return this.courseOfStudent; }
    void setCourseOfStudent(String newcourse) { this.courseOfStudent = newcourse; }

    int yearOfStudent;
    int getYearOfStudent() { return this.yearOfStudent; }
    void setYearOfStudent(int newyear) { this.yearOfStudent = newyear; }

    int numberOfSubjectsEnrolled;
    int getNumberOfEnrolledCourses() { return this.numberOfSubjectsEnrolled; }
    void setNumberOfEnrolledCourses(int newnum) { this.numberOfSubjectsEnrolled = newnum; }

    public Student() {
        numberOfStudents++;
        nameOfStudent = "not set";
        courseOfStudent = "not set";
        yearOfStudent = 1;
        numberOfStudents = 0;
    }

    void addOneSubject() { numberOfSubjectsEnrolled++; }
    void removeOneSubject() { numberOfSubjectsEnrolled--; }
}

class TestStudent {

    static void printStudent(Student st) {
        System.out.printf("""
            ===[STUDENT INFORMATION]===
            Name: %s
            Course: %s
            Year: %s
            Enrolled Subjects: %s
        """, st.getNameOfStudent(), st.getCourseOfStudent(), st.getYearOfStudent(), st.getNumberOfEnrolledCourses());
    }

    static void changeCourseAndYear(Student st, String newcourse, int newYear) {
        st.setYearOfStudent(newYear);
        st.setCourseOfStudent(newcourse);
    }

    static void changeCourseAndYear(Student st, int newYear, String newcourse) {
        st.setYearOfStudent(newYear);
        st.setCourseOfStudent(newcourse);
    }

    static void printAllStudents(Student[] section) {
        System.out.println("====[STUDENTS IN SECTION]====");
        for (Student st : section) {
            System.out.printf("%s -- %s -- Year %s%n", st.getNameOfStudent(), st.getCourseOfStudent(), st.getYearOfStudent());
        }
    }

    static void printAllStudents(Student[] section, int specifiedYear) {
        System.out.printf("====[YEAR %s STUDENTS IN SECTION]====%n", specifiedYear);
        for (Student st : section) {
            if( st.getYearOfStudent() == specifiedYear ) {
                System.out.printf("%s -- %s -- Year %s%n", st.getNameOfStudent(), st.getCourseOfStudent(), st.getYearOfStudent());
            }
        }
    }

}

