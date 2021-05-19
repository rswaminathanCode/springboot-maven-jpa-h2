/**
 * 
 */
package com.project.hr.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hr.usermgmt.model.Employee;

/**
 * @author swami
 *
 */
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

}
