/**
 * Created by 100588642 on 1/24/2017.
 */
public class Student {
    private int sid;
    private String firstName;
    private String lastName;
    private double gpa;

    public Student(int sid, String firstName, String lastName, double gpa){
        setSid(sid);
        setFirstName(firstName);
        setLastName(lastName);
        setGpa(gpa);
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
