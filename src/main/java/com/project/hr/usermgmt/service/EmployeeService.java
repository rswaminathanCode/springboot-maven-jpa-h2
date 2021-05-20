/**
 * 
 */
package com.project.hr.usermgmt.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.hr.usermgmt.model.Employee;
import com.project.hr.usermgmt.repository.EmployeeRepo;

/**
 * @author swami
 *
 */
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo usersRepo;

	@Transactional
	public List<Employee> save(List<Employee> users) {
		List<Employee> response = null;
		response = usersRepo.saveAll(users);
		return response;
	}

	public List<Employee> getAllUsers() {
		return usersRepo.findAll();
	}

	@Transactional
	public Optional<Employee> getUsersById(String id) {
		// usersRepo.findAll().stream().filter(s ->
		// s.getId().contentEquals(Id)).collect(Collectors.toList())
		return usersRepo.findById(id);
	}

	@Transactional
	public Employee createUser(Employee emp) {
		return usersRepo.save(emp);
	}

	@Transactional
	public Employee updateUser(Employee emp) {
		return usersRepo.save(emp);
	}

	@Transactional
	public void deleteUser(String id) {
		usersRepo.deleteById(id);
	}

	@Transactional
	public List<Employee> getUsersBySalaryRangeSorted(String fromSalary, String toSalary) {
		double fromSal = Double.parseDouble(fromSalary);
		double toSal = Double.parseDouble(toSalary);
		List<Employee> dbList = usersRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
		return dbList.stream().filter(s -> (s.getSalary() >= fromSal && s.getSalary() <= toSal))
				.limit((long) (toSal - fromSal)).collect(Collectors.toList());
	}

}
