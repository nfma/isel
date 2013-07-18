package isel.exam;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static isel.exam.FunctionalExam.Student;
import static isel.exam.TestExam.Console.REDIRECT_CONSOLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 18/07/13
 * Time: 00:13
 * To change this template use File | Settings | File Templates.
 */
public class TestExam {
    private static ByteArrayOutputStream baos;

    // roll out my own poor man's tests
    public static void main(String[] args) {
        runTests();
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintEmptyContainerWhenNoAlbumsGiven() {
        final Container container = new Container(new Album[0]);
        container.show();
        assertEquals("Albums", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintASetWithOnePhotoInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L)})});
        container.show();
        assertEquals("Albums\n    Photos\n        img1", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintASetWithOnePhotoWithANullNameInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image(null, 10L)})});
        container.show();
        assertEquals("Albums\n    Photos\n        null", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintASetWithANullNameWithOnePhotoInAContainer() {
        final Container container = new Container(new Album[]{new Set(null, new Image[]{new Image("img1", 10L)})});
        container.show();
        assertEquals("Albums\n    null\n        img1", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintASetWithTwoPhotosInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L),
                new Image("img2", 20L)})});
        container.show();
        assertEquals("Albums\n    Photos\n        img1\n        img2", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintTwoSetsWithOnePhotoEachInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L)}),
                new Set("New Photos", new Image[]{new Image("img2", 20L)})});
        container.show();
        assertEquals("Albums\n    Photos\n        img1\n    New Photos\n        img2", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintACollectionWithOneSetEachWithOnePhotoEachInAContainer() {
        final Collection awesome = new Collection("Awesome", 2);
        awesome.add(new Set("Photos", new Image[]{new Image("img1", 10L)}));
        awesome.add(new Set("New Photos", new Image[]{new Image("img2", 10L)}));
        final Container container = new Container(new Album[]{awesome});
        container.show();
        assertEquals("Albums\n    Awesome\n        Photos\n            img1\n        New Photos\n            img2", readResultFromConsoleOutput());
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintACollectionWithANullNameWithOneSetEachWithOnePhotoEachInAContainer() {
        final Collection awesome = new Collection(null, 2);
        awesome.add(new Set("Photos", new Image[]{new Image("img1", 10L)}));
        awesome.add(new Set("New Photos", new Image[]{new Image("img2", 10L)}));
        final Container container = new Container(new Album[]{awesome});
        container.show();
        assertEquals("Albums\n    null\n        Photos\n            img1\n        New Photos\n            img2", readResultFromConsoleOutput());
    }

    @Test
    private void shouldReturnNullPathOnAnEmptyContainer() {
        final Container container = new Container(new Album[0]);
        assertEquals(container.getPath("img1"), null);
    }

    @Test
    private void shouldReturnNullPathOnAnEmptyContainerWhenSearchingForNull() {
        final Container container = new Container(new Album[0]);
        assertEquals(container.getPath(null), null);
    }

    @Test
    private void shouldReturnThePathOfASetWithOnePhotoInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L)})});
        assertEquals("Albums/Photos/img1", container.getPath("img1"));
    }

    @Test
    private void shouldReturnNullWhenSearchingForAPhotoNameThatDoesntExist() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L)})});
        assertEquals(null, container.getPath("img2"));
    }

    @Test
    private void shouldReturnThePathOfASetWithOnePhotoWithANullNameInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image(null, 10L)})});
        assertEquals("Albums/Photos/null", container.getPath(null));
    }

    @Test
    private void shouldReturnThePathOfTheSecondImageInsideASetWithTwoPhotosInAContainer() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L),
                new Image("img2", 20L)})});
        assertEquals("Albums/Photos/img2", container.getPath("img2"));
    }

    @Test
    private void shouldReturnThePathOfTheFirstImageInsideASetWithAPhotosInAContainerWithTwoSets() {
        final Container container = new Container(new Album[]{new Set("Photos", new Image[]{new Image("img1", 10L)}),
                new Set("New Photos", new Image[]{new Image("img1", 20L)})});
        assertEquals("Albums/Photos/img1", container.getPath("img1"));
    }

    @Test
    private void shouldReturnThePathOfTheFirstImageInsideASetWithAPhotosInAContainerWithACollection() {
        final Collection awesome = new Collection("Awesome", 2);
        awesome.add(new Set("Photos", new Image[]{new Image("img1", 10L)}));
        awesome.add(new Set("New Photos", new Image[]{new Image("img2", 10L)}));
        final Container container = new Container(new Album[]{awesome});
        assertEquals("Albums/Awesome/Photos/img1", container.getPath("img1"));
    }

    @Test(console = REDIRECT_CONSOLE)
    private void shouldPrintTheResultOfPointFourOfTheExercise() {
    final Collection anonymous = new Collection(2);
        anonymous.add(new Set("Birthdays", new Image[]{new Image("img1", 10L), new Image("img2", 10L), new Image("img3", 10L)}));
        anonymous.add(new Set("Landscapes", new Image[]{new Image("img4", 10L), new Image("img5", 10L)}));
        final Container container = new Container(new Album[]{anonymous});
        container.show();
        assertEquals("Albums\n    Collection 2\n        Birthdays\n            img1\n            img2\n            img3\n" +
                "        Landscapes\n            img4\n            img5", readResultFromConsoleOutput());
        // The result in the exam is botched cause Collection has 2 albums inside
    }

    @Test
    public void shouldKeepEmptyIterator() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        exam.filter(students.iterator(), makeFunc(false));
        assertEquals(students.size(), 0);
    }

    @Test
    public void shouldKeepOneElementWhenItsExecutionIsFalse() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(0));
        exam.filter(students.iterator(), makeFunc(false));
        assertEquals(students.size(), 1);
    }

    @Test
    public void shouldRemoveTheElementWhenItsExecutionIsTrue() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(0));
        exam.filter(students.iterator(), makeFunc(true));
        assertEquals(students.size(), 0);
    }

    @Test
    public void shouldReturnZeroWhenNoStudents() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        assertEquals(exam.average(students), 0f);
    }

    @Test
    public void shouldReturnZeroWhenAllStudentsFailed() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(0));
        assertEquals(exam.average(students), 0f);
        assertEquals(students.size(), 1);
    }

    @Test
    public void shouldReturnFifteenWhenOnlyOneStudentPassedWithFifteen() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(15));
        assertEquals(exam.average(students), 15f);
        assertEquals(students.size(), 0);
    }

    @Test
    public void shouldReturnFifteenWhenOneStudentPassedWithFifteenAndAnotherFailed() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(15));
        students.add(anonymousStudent(0));
        assertEquals(exam.average(students), 15f);
        assertEquals(students.size(), 1);
    }

    @Test
    public void shouldReturnFifteenWhenTwoStudentPassedWithAverageFifteenAndAnotherFailed() {
        final FunctionalExam exam = new FunctionalExam();
        List<Student> students = new ArrayList<Student>();
        students.add(anonymousStudent(10));
        students.add(anonymousStudent(20));
        students.add(anonymousStudent(0));
        assertEquals(exam.average(students), 15f);
        assertEquals(students.size(), 1);
    }

    private FunctionalExam.Func<Student, Integer> makeFunc(final boolean result) {
        return new FunctionalExam.Func<Student, Integer>() {
            public boolean exec(Student student) {
                return result;
            }

            public Integer getExecutionsResult() {
                return null;
            }
        };
    }

    private Student anonymousStudent(final int grade) {
        return new Student() {
            public int getGrade() {
                return grade;
            }

            public String getName() {
                return null;
            }
        };
    }

    private static <T> void assertEquals(T expected, T actual) {
        if ((expected != null && !expected.equals(actual)) || (expected == null && actual != null))
            throw new AssertionError("\nexpected: " + expected + "\n but got: " + actual);
    }

    private static void runTests() {
        int numTests = 0, passed = 0, failed = 0;
        PrintStream console = System.out;  // this is plagued with side effects to the console, a nightmare to test...
        long startTime = System.nanoTime();
        for (Method method : TestExam.class.getDeclaredMethods()) {
            if (isTest(method)) {
                numTests++;
                try {
                    if(shouldRedirectConsole(method)) {
                        redirectConsole();
                    }
                    method.invoke(new TestExam()); //run each test in a new instance to clean up everything
                    passed++;
                } catch (Exception e) {
                    printFailedTest(console, method, e); // i actually need to print to the console here
                    failed++;
                } finally {
                    resetConsole(console);
                }
            }
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("Result: " + numTests + " test(s). " + passed + " test(s) passed and " + failed + " test(s) failed, in " + (duration / 1000000) + "ms");
    }

    private static void resetConsole(PrintStream console) {
        System.setOut(console);
    }

    private static void redirectConsole() {
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    private String readResultFromConsoleOutput() {
        final byte[] output = baos.toByteArray();
        final BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(output));
        final byte[] buffer = new byte[output.length];
        try {
            bis.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e); // this shouldn't really happen but in case it does it will just fail tests...
        }
        return new String(buffer).trim(); //the byte array might have extra empty spaces caused by the growth of the output stream
    }

    private static void printFailedTest(PrintStream console, Method method, Exception e) {
        console.println(method.getName() + " failed");
        e.printStackTrace(console);
        console.println("\n");
    }

    private static boolean isTest(Method method) {
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (Test.class.isInstance(annotation))
                return true;
        }
        return false;
    }

    private static boolean shouldRedirectConsole(Method method) {
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (Test.class.isInstance(annotation)) {
                Test test = (Test) annotation;
                return test.console().equals(REDIRECT_CONSOLE);
            }
        }
        return false;
    }

    @Retention(RUNTIME)
    @Target(METHOD)
    private @interface Test {
        Console console() default Console.NO_CONSOLE_REDIRECTION;
    }

    enum Console {
        REDIRECT_CONSOLE,
        NO_CONSOLE_REDIRECTION
    }
}