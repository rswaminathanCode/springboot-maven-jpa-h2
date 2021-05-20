package com.project.hr.usermgmt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.hr.usermgmt.model.Employee;

/**
 * @author swami
 *
 */
@Component
public class CSVUtils {

	public static String TYPE = "text/csv";
	static String[] HEADERs = { "ID", "LOGIN", "NAME", "SALARY", "STARTDATE" };

	public static boolean hasCSVFormat(MultipartFile file) {
		//Can be enabled to restrict file from specific content type:
		//TYPE.equals(file.getContentType())
		if ( Optional.of(file).isPresent() && file.getSize() > 0) {
			return true;
		}
		return false;
	}
    
	private static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
    
    public static boolean isAlphanumeric(final String input) {
        return input.matches(ALPHANUMERIC_PATTERN);
    }
    
	public static List<Employee> csvToEmployees(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Employee> employees = new ArrayList<Employee>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
                //TODO:commented data from the feed file to be ignored.
				Employee emp = new Employee(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2),
						Double.parseDouble(csvRecord.get(3)), csvRecord.get(4));
				employees.add(emp);
			}
			return employees;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	/**
	 * Below utility is to check duplicated data within a same multipart file
	 * 
	 * @param empList
	 * @return
	 */
	public static List<Employee> checkDuplicateUsers(List<Employee> empList) {
		return empList.stream().distinct().collect(Collectors.toList());
	}
}
