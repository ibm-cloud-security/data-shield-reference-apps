package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Scanner;

import util.TestObject;

public class InputOutput {

	public static boolean fileOutputStream(String path, String fileName, String content) {
		
		System.out.println("InputOutput.fileOutputStream() Start");

		boolean result = false;

		try (FileOutputStream out = new FileOutputStream(path + fileName)) {

			out.write(content.getBytes());
			out.flush();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileOutputStream() End");
		return result;

	}

	public static String fileInputStream(String path, String fileName) {
		
		System.out.println("InputOutput.fileInputStream() Start");

		final StringBuilder result = new StringBuilder();

		try (FileInputStream in = new FileInputStream(path + fileName)) {

			int c;
			while ((c = in.read()) != -1) {
				result.append((char) c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("InputOutput.fileInputStream() End");
		return result.toString();

	}

	public static boolean fileWriter(String path, String fileName, String content) {
		
		System.out.println("InputOutput.fileWriter() Start");

		boolean result = false;

		try (FileWriter fileWriter = new FileWriter(path + fileName)) {
			fileWriter.write(content);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("InputOutput.fileWriter() End");
		return result;
	}

	public static String fileReader(String path, String fileName) {
		
		System.out.println("InputOutput.fileReader() Start");

		final StringBuilder result = new StringBuilder();

		try (FileReader reader = new FileReader(path + fileName)) {

			int c;
			while ((c = reader.read()) != -1) {
				result.append((char) c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("InputOutput.fileReader() End");
		return result.toString();

	}

	public static boolean filePrintWriter(String path, String fileName, String content) {
		
		System.out.println("InputOutput.filePrintWriter() Start");

		boolean result = false;

		try (PrintWriter outputStream = new PrintWriter(new FileWriter(path + fileName))) {

			outputStream.println(content);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.filePrintWriter() End");
		return result;

	}

	public static String fileBufferedReader(String path, String fileName) {
		
		System.out.println("InputOutput.fileBufferedReader() Start");

		final StringBuilder result = new StringBuilder();

		try (BufferedReader inputStream = new BufferedReader(new FileReader(path + fileName))) {

			String l;
			while ((l = inputStream.readLine()) != null) {
				result.append(l);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("InputOutput.fileBufferedReader() End");
		return result.toString();

	}

	public static boolean fileBufferedWriter(String path, String fileName, String content) {
		
		System.out.println("InputOutput.fileBufferedWriter() Start");

		boolean result = false;

		try (BufferedWriter outputStream = new BufferedWriter(new FileWriter(path + fileName))) {

			outputStream.write(content);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileBufferedWriter() End");
		return result;

	}

	public static String fileDataInputStream(String path, String fileName) {
		
		System.out.println("InputOutput.fileDataInputStream() Start");

		final StringBuilder result = new StringBuilder();

		try (DataInputStream inputStream = new DataInputStream(
				new BufferedInputStream(new FileInputStream(path + fileName)))) {

			int c;
			while ((c = inputStream.read()) != -1) {
				result.append((char) c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("InputOutput.fileDataInputStream() End");
		return result.toString();

	}

	public static boolean fileDataOutputStream(String path, String fileName, String content) {
		
		System.out.println("InputOutput.fileDataOutputStream() Start");

		boolean result = false;

		try (DataOutputStream outputStream = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(path + fileName)))) {

			outputStream.write(content.getBytes());
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileDataOutputStream() End");
		return result;

	}

	public static String fileObjectInputStream(String path, String fileName) {
		
		System.out.println("InputOutput.fileObjectInputStream() Start");

		TestObject object = null;

		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path + fileName))) {
			object = (TestObject) objectInputStream.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileObjectInputStream() End");
		return object.toString();

	}

	public static boolean fileObjectOutputStream(String path, String fileName, String content) {

		System.out.println("InputOutput.fileObjectOutputStream() Start");
		boolean result = false;

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path + fileName))) {

			TestObject object = new TestObject(content);
			objectOutputStream.writeObject(object);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileObjectOutputStream() End");
		return result;

	}

	public static String fileScanner(String path, String fileName) {

		System.out.println("InputOutput.fileScanner() Start");
		final StringBuilder result = new StringBuilder();

		try (Scanner in = new Scanner(new File(path + fileName))) {

			while (in.hasNext()) {
				result.append(in.next());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileScanner() End");
		return result.toString();

	}

	public static boolean fileFormatter(String path, String fileName, String content) {

		System.out.println("InputOutput.fileFormatter() Start");
		boolean result = false;

		try (Formatter out = new Formatter(path + fileName)) {

			out.format("%s", content);
			out.flush();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("InputOutput.fileFormatter() End");
		return result;

	}

	public static void main(String... args) {

//		fileWriter("","test.txt","Hello World");
//		System.out.println(fileReader("","test.txt"));
//		fileOutputStream("","test.txt","Hello World");
//		System.out.println(fileInputStream("","test.txt"));
//		filePrintWriter("","test.txt","Hello World");
//		System.out.println(fileBufferedReader("","test.txt"));
//		fileBufferedWriter("","test.txt","Hello World");
//		System.out.println(fileBufferedReader("","test.txt"));
//		fileDataOutputStream("","test.txt","Hello World");
//		System.out.println(fileDataInputStream("","test.txt"));
//		fileObjectOutputStream("","test.txt","Hello World");
//		System.out.println(fileObjectInputStream("","test.txt"));
//		fileFormatter("","test.txt","Hello World");
//		System.out.println(fileScanner("","test.txt"));

	}

}
