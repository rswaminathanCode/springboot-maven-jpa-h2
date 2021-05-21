package com.project.hr.usermgmt.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.hr.usermgmt.exception.ResourceNotFoundException;
import com.project.hr.usermgmt.model.Employee;
import com.project.hr.usermgmt.service.EmployeeService;
import com.project.hr.usermgmt.utils.CSVUtils;

/**
 * @author swami
 *
 */
@CrossOrigin("http://localhost:8081")
@Controller
// @RequestMapping("/users")
// @Slf4j
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	/**
	 * Below functions is for uploading users into HR system
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/users/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (CSVUtils.hasCSVFormat(file)) {
			try {
				List<Employee> receivedList = CSVUtils.csvToEmployees(file.getInputStream());
				// Below functions can remove duplicated record entries in the file
				List<Employee> removedDupList = CSVUtils.checkDuplicateUsers(receivedList);
				
				if (receivedList.size() == removedDupList.size()) {
					// Process to upload users
					if (empService.save(removedDupList).size() <= 0) {
						message = "Success but no data updated: ";
						return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, ""));
					} else {
						message = "Data created or uploaded: ";
						return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message, ""));
					}
				} else {
					message = "Duplicate entries in the upload file: ";
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
				}

			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
			}
		}
		message = "Please upload a valid csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
	}

	/**
	 * Below functions is to getAll users
	 * 
	 * @param emp
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<ResponseMessage> getAllUsers() throws ResourceNotFoundException {
		List<Employee> emp = empService.getAllUsers();
		if (emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("No data found.", emp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("success", emp));
	}

	/**
	 * Below functions is to getAll users by given 
	 * salary field filter and order by id 
	 * 
	 * @param emp
	 * @return
	 */
	@GetMapping("/users/{minSalary}/{maxSalary}")
	public ResponseEntity<ResponseMessage> getAllUsersByFilter(@PathVariable String minSalary,
			@PathVariable String maxSalary) throws ResourceNotFoundException {
		double fromSal = Double.parseDouble(Optional.of(minSalary).isPresent()?minSalary:"0.0");
		double toSal = Double.parseDouble(Optional.of(maxSalary).isPresent()?maxSalary:"0.0");
		if (toSal > fromSal) {
			List<Employee> emp = empService.getUsersBySalaryRangeSorted(minSalary, maxSalary);
			// emp.stream().forEach(s ->System.out.println("====getAllUsersByFilter::"+s));
			if (!emp.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Success", emp));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Bad input.", ""));
	}

	/**
	 * Below functions is to create user
	 * 
	 * @param emp
	 * @return
	 */
	@PostMapping("/users")
	public ResponseEntity<ResponseMessage> createUser(@Valid @RequestBody Employee emp)
			throws ResourceNotFoundException {
		Optional<Employee> user = empService.getUsersById(emp.getId());
		if (user.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Employee ID already exists", ""));
		} else {
			empService.createUser(emp);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully created.", emp));
		}
	}

	/**
	 * Below functions is used to get user by ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<ResponseMessage> getUsersById(@Valid @PathVariable String id)
			throws ResourceNotFoundException {
		Optional<Employee> user = empService.getUsersById(id);
		if (user.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Success", user));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Bad input", ""));
		}
	}

	/**
	 * Below functions is used to update a user.
	 * 
	 * @param id
	 * @param emp
	 * @return
	 */
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<ResponseMessage> updateUser(@Valid @PathVariable String id, @RequestBody Employee emp)
			throws ResourceNotFoundException {
		
		if (Objects.equals(id,emp.getId()) && empService.getUsersById(emp.getId()).isPresent()) {
			empService.updateUser(emp);
			// return new ResponseEntity<>(empService.updateUser(emp),HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully updated", ""));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("No such employee.", ""));
	}

	/**
	 * Below functions is use to delete a user by ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<ResponseMessage> deleteUser(@Valid @PathVariable String id) throws ResourceNotFoundException {
		Optional<Employee> users = empService.getUsersById(id);
		if (users.isPresent()) {
			empService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully deleted", ""));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("No such employee.", ""));
		}

	}

}