import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Student {
    String id;
    double average;
    String name;
    String[] modules;
    int[] marks;

    public Student(String name){
        //complete the constructor
        this.name = name;
        String [] modules = {"OOP", "SWW1"};
        int [] marks = {0, 0};
        average = 0;
        id = getId(name);
    }

    public Student(String name, String[] modules, int[] marks){
        //complete the constructor
        this.name = name;
        this.modules = modules;
        this.marks = marks;
        this.id = getId(name);
        average = calculateAverage(marks);
    }

    public String getId (String name){
        //complete this method
        String [] splitName;
// CHANGE TO FOR LOOP WHERE INDEX IS I AND LOOPS AROUND LENGTH OF SPLIT NAME
        splitName = name.split(" ");
        if (splitName.length == 3)
        {
            String firstName = splitName[0];
            String middleName = splitName[1];
            String lastName = splitName[2];
            String upper1 = firstName.toUpperCase();
            String upper2 = middleName.toUpperCase();
            String upper3 = lastName.toUpperCase();
            char c1 = upper1.charAt(0);
            char c2 = upper2.charAt(0);
            char c3 = upper3.charAt(0);
            String char1 = Character.toString(c1);
            String char2 = Character.toString(c2);
            String char3 = Character.toString(c3);
            id = char1 + char2 + char3 + name.length();
        }
        if (splitName.length == 2)
        {
            String firstName = splitName[0];
            String lastName = splitName[1];
            String upper1 = firstName.toUpperCase();
            String upper2 = lastName.toUpperCase();
            char c1 = upper1.charAt(0);
            char c2 = upper2.charAt(0);
            String char1 = Character.toString(c1);
            String char2 = Character.toString(c2);

            id = char1 + char2 + name.length();
        }
        return id;
    }

    public double calculateAverage(int[] marks){
        //complete this method
        double addedMarks = 0;
        for (int mark : marks) {
            addedMarks += mark;
        }
        double average = addedMarks / marks.length;
        return (double)Math.round(average * 100)/100;
    }

    public static double getAverage(Student[] students, String id){
        //complete this method
        // search for object in array using attribute of object??
        double average = 0;
        for (Student student : students)
        {
            if (id.equals(student.id))
            {
                average = student.average;
                return average;
            }
        }
        return 0;
    }

    public static int getMark(Student[] students, String id, String module){
        //complete this method
        int mark;
        for (Student student : students)
        {
            if (id.equals(student.id))
            {
                if (student.modules == null)
                {
                    return 0;
                }
                for (int x = 0; x < student.modules.length; x++)
                {
                    if (module.equals(student.modules[x]))
                    {
                        mark = student.marks[x];
                        return mark;
                    }
                }
            }
        }
        return 0;
    }
}
