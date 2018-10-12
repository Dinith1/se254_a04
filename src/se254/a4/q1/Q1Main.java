package se254.a4.q1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * SE254 Assignment 4 Question 1 Main. This class should be implemented to contain the functionality described in the
 * assignment handout. you may implement additional classes if you wish, but this class should be the main program entry
 * point.
 */
public class Q1Main {

	public static void main(String[] args) {
		System.out.print("Enter a class name: ");
		String usrIn = getUserInput();

		Class<?> cl = getSpecifiedClass(usrIn);
		checkValidClass(cl);
		Object obj = getSpecifiedObject(cl);
		System.out.println("You entered: " + usrIn + "\nAn object has been created for this class\n");

		while (true) {
			printPublicFields(cl, obj);
			printPublicMethods(cl);
			
			int errCode = 1;
			while (errCode != 0) {
				System.out.println("Enter the name of a method to execute on the object (without the brackets): ");
				String methodName = getUserInput();
				errCode = performSpecifiedMethod(cl, obj, methodName);
			}

		}


	}


	private static String getUserInput() {
		Scanner scan = new Scanner(System.in);
		String s = scan.next();
		return s;
	}


	private static Class<?> getSpecifiedClass(String className) {
		try {
			Class<?> cl = Class.forName(className);
			return cl;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}


	private static void checkValidClass(Class<?> cl) {
		if (cl == null) {
			System.err.println("ERROR: Invalid class name... Exiting program...\n\n");
			System.exit(-1);
		}
		return;
	}


	private static Object getSpecifiedObject(Class<?> cl) {
		try {
			Object obj = cl.newInstance();
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static void printPublicFields(Class<?> cl, Object obj) {
		System.out.println("Public fields:");
		for (Field f : cl.getFields()) {
			try {
				System.out.println("    " + f.getName() + ": " + f.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}


	private static void printPublicMethods(Class<?> cl) {
		System.out.println("Public methods without input parameters:");
		for (Method m : cl.getMethods()) {
			if (m.getParameterTypes().length == 0) {
				System.out.println("    " + m.getName() + "()");
			}
		}
		System.out.println();
	}


	private static int performSpecifiedMethod(Class<?> cl, Object obj, String methodName) {
		try {
			Method m = cl.getMethod(methodName, null);
			m.invoke(obj, null);
			System.out.println("\nTHE " + methodName + "() METHOD HAS BEEN INVOKED");
			System.out.println("=======================================\n");
			return 0;
		} catch (Exception e) {
			System.err.println("ERROR: Invalid method name...");
			return 1;
		}
	}

} 
