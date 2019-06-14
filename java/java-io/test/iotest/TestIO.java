package iotest;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.InputOutput;

class TestIO {

	public static final String PATH = "";
	public static final String TEST_FILE_NAME = "text.txt";
	public static final String RAW_CONTENT = "IBM Cloud Data Shield enables users to run containerized applications in a secure enclave on an IBM Cloud Kubernetes host, providing data-in-use protection. "
			+ "Data Shield allows user-level code to allocate private regions of memory, called enclaves, that are protected from processes running at higher privilege levels. "
			+ "It extends SGX language support from C and C++ to Python and also provides pre-converted SGX applications for MySQL, NGINX and Vault, with little to no code change. "
			+ "Powered by the Fortanix Runtime Encryption platform and Intel SGX technology, these tools enable organizations with sensitive data to leverage cloud computing with more confidence. "
			+ "Enables organizations with sensitive data to leverage cloud computing."
			+ "Runs containerized applications in secure enclaves on the IBM Cloud Kubernetes Service."
			+ "Provides visibility into node security attributes."
			+ "Integrates with DevOps pipelines. Uses the IBM Cloud Kubernetes Service to bring scalability and high availability to SGX workloads.";
	public static String CONTENT = "";
	public static int KB_SIZE = Integer.parseInt(System.getenv("KB_SIZE"));

	@BeforeEach
	void setUp() throws Exception {
		System.out.println();
		System.out.println("*Starting Test*");
		System.out.println("*Setup*");
		final StringBuilder contentBuilder = new StringBuilder();
		for(int i=0; i < KB_SIZE; i++) {
			contentBuilder.append(RAW_CONTENT);
		}
		CONTENT = contentBuilder.toString();
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("*Tear Down*");
		Files.deleteIfExists(Paths.get(PATH + TEST_FILE_NAME));
	}

	@Test
	void testFileWriterAndReader() {
		
		System.out.println("*testFileWriterAndReader*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileWriter(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileReader(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);
		
	}

	@Test
	void testOutputStreamWriterAndReader() {
		
		System.out.println("*testOutputStreamWriterAndReader*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileOutputStream(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileInputStream(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);

	}

	@Test
	void testPrintWriterAndBufferedReader() {
		
		System.out.println("*testPrintWriterAndBufferedReader*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.filePrintWriter(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileBufferedReader(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);

	}

	@Test
	void testBufferedWriterAndReader() {
		
		System.out.println("*testBufferedWriterAndReader*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileBufferedWriter(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileBufferedReader(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);

	}

	@Test
	void testDataInputAndOutputStream() {
		
		System.out.println("*testDataInputAndOutputStream*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileDataOutputStream(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileDataInputStream(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);

	}

	@Test
	void testObjectInputAndOutputStream() {
		
		System.out.println("*testObjectInputAndOutputStream*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileObjectOutputStream(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileObjectInputStream(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT, fileContent);

	}

	@Test
	void testFormatterAndScanner() {
		
		System.out.println("*testFormatterAndScanner*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);
		
		boolean result = InputOutput.fileFormatter(PATH, TEST_FILE_NAME, CONTENT);
		assertEquals(true, result);

		final String fileContent = InputOutput.fileScanner(PATH, TEST_FILE_NAME);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		assertEquals(CONTENT.replaceAll("\\s", ""), fileContent);

	}

}