import java.util.*;
class Student  {
    private String studentname;
    private int rollno;
    private int studentage;

    public Student(int rollno, String studentname, int studentage) {
         this.rollno = rollno;
         this.studentname = studentname;
         this.studentage = studentage;
    }

    public String getStudentname() {
         return studentname;
    }
    public void setStudentname(String studentname) {
	this.studentname = studentname;
    }
    public int getRollno() {
	return rollno;
    }
    public void setRollno(int rollno) {
	this.rollno = rollno;
    }
    public int getStudentage() {
	return studentage;
    }
    public void setStudentage(int studentage) {
 	this.studentage = studentage;
    }	
}


public class ArrayListSorting  {

     public static void main(String args[]){
	   ArrayList<Student> arraylist = new ArrayList<Student>();
	   arraylist.add(new Student(223, "Chaitanya", 26));
	   arraylist.add(new Student(245, "Rahul", 24));
	   arraylist.add(new Student(209, "Ajeet", 32));

//	   Collections.sort(arraylist);

	   for(Student.getStudentage str: arraylist){
			System.out.println(str);
	   }
     }
}
