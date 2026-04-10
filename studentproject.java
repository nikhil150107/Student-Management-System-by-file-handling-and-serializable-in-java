package GoodBye;

import java.io.*;
import java.util.*;

class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	int marks;
	
	public Student(int id,String name,int marks) {
		this.id=id;
		this.name=name;
		this.marks=marks;
	}
	void display()
	{
			System.out.println(id+" "+name+" "+marks);
	}
}

class project{
	
	//call kar raha hu without static then object main me banna hoga
	//agar object main me nahi banna then static use karna function bannane se pehle
	static final String FILE_NAME = "students.ser";
	
	@SuppressWarnings("unchecked")
	static ArrayList<Student> loadStudents(){
		ArrayList<Student> list = new ArrayList<>();
		File f=new File(FILE_NAME);
		if(!f.exists())return list;
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))){
			list=(ArrayList<Student>)ois.readObject();
		}catch(EOFException e) {
			//file empty hai
		}catch(Exception e) {
			System.out.println("Error loading data: " + e.getMessage());
		}
		
		return list;
	}
	static void saveStudent(ArrayList<Student> list)throws Exception {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
			oos.writeObject(list);
			System.out.println("Data saved successfully.");
		}catch(Exception e) {
			System.out.println("Error saving data: " + e.getMessage());
		}
	}
	static void addStudent(Scanner sc) throws Exception {
		ArrayList<Student> list=loadStudents();
		
		int id;
		System.out.println("Enter the id: ");
		id=sc.nextInt();
		sc.nextLine();
		for(var s : list) {
			if(s.id==id) {
				System.out.println("This id " + id + " is already exist");
				return;
			}
		}
		
		
		String name;

		System.out.println("Enter the name:- ");
		name = sc.nextLine().trim();
		if(name.isEmpty()) {
			System.out.println("Name of a Student cannot be empty!");
			return;
		}
		System.out.println("Enter the marks between (0-100):- ");
		int marks;
		marks=sc.nextInt();
		if(marks<0 || marks>100) {
			System.out.println("Marks range is invaild ");
			return;
		}
		
		list.add(new Student(id,name,marks));
		saveStudent(list);
		System.out.println("Student Succesfully Added! ");
	}
	
	static void viewAllStudents() {
		ArrayList<Student> list=loadStudents();
		
		if(list.isEmpty()) {
			System.out.println("No student record found.");
			return;
		}
		
		 System.out.println("\n===== All Students =====");
	        for (Student s : list) {
	            s.display();
	        }
	        System.out.println("Total Students: " + list.size());
	}
	
	static void searchStudent(Scanner sc) {
		System.out.println("Enter Student ID to search.");
		int id=sc.nextInt();
		
		ArrayList<Student> list = loadStudents();
		
		 for (Student s : list) {
	            if (s.id == id) {
	                System.out.println("Student Found:");
	                s.display();
	                return;
	            }
	        }
	   System.out.println("Student with ID " + id + " not found.");
		
	}
	
	static void updateStudent(Scanner sc) throws Exception {
		 System.out.println("Enter Student ID to update: ");
		 int id=sc.nextInt();
		 sc.nextLine();
		 ArrayList<Student> list=loadStudents();
		 boolean found=false;
		 for(var s: list) {
			 if(s.id==id) {
				 found=true;
				 System.out.print("Enter New Name (press Enter to keep '" + s.name + "'): ");
	                String newName = sc.nextLine().trim();
	                if (!newName.isEmpty()) {
	                    s.name = newName;
	                }
	 
	                System.out.print("Enter New Marks (-1 to keep current " + s.marks + "): ");
	                int newMarks = sc.nextInt();
	                if (newMarks != -1) {
	                    if (newMarks < 0 || newMarks > 100) {
	                        System.out.println("Invalid marks! Update cancelled.");
	                        return;
	                    }
	                    s.marks = newMarks;
	                }
	 
	                break;
			 }
		 }
		 if (!found) {
	            System.out.println("Student with ID " + id + " not found.");
	            return;
	        }
	 
		saveStudent(list);
	        System.out.println("Student updated successfully!");
	}
	
	static void deleteStudent(Scanner sc) throws Exception {
		System.out.println("Enter Student ID to delete: ");
		int id=sc.nextInt();
		
		ArrayList<Student> list=loadStudents();
		boolean removed=false;
		
		Iterator<Student> iter=list.iterator();
		
		while(iter.hasNext()) {
			Student s=iter.next();
			if(s.id==id) {
				iter.remove();
				removed=true;
				break;
			}
		}
		
		if(!removed) {
			 System.out.println("Student with ID " + id + " not found.");
	            return;
		}
		
		saveStudent(list);
		System.out.println("Student Deleted Successfully!");
	}
	public static void main(String[] args) throws Exception{
		Scanner sc=new Scanner(System.in);
		int choice;
		
		System.out.println("====================================");
	    System.out.println("  Welcome to Student Management System");
	    System.out.println("====================================");
	    
	    while(true) {
	    	 System.out.println("\n========== MENU ==========");
	     System.out.println("1. Add Student");
	     System.out.println("2. View All Students");
	     System.out.println("3. Search Student");
	     System.out.println("4. Update Student");
	     System.out.println("5. Delete Student");
	     System.out.println("6. Exit");
	     System.out.print("Enter your choice: ");
	     
	    	choice=sc.nextInt();
	    	
	    		switch(choice) {
	    			case 1:
	    				addStudent(sc);
	    				break;
	    			case 2:
	    				viewAllStudents();
	    				break;
	    			case 3:
	    				searchStudent(sc);
	    				break;
	    			case 4:
	    				updateStudent(sc);
	    				break;
	    			case 5:
	    				deleteStudent(sc);
	    				break;
	    			case 6:
	    				System.out.println("GOod BYe! SeE yOU neXT TiMe");
	    				sc.close();
	    				System.exit(0);
	    			default :
	    				System.out.println("You had enter invalid number pls,enter in (0-6).");
	    				
	    		}
	    }
		
	}
}

