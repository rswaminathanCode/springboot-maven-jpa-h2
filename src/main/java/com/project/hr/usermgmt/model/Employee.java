package com.project.hr.usermgmt.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "ID", nullable = false)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@NotEmpty
	private String id;

	@Column(name = "LOGIN", nullable = false)
	@NotNull
	@NotEmpty
	private String login;

	@Column(name = "NAME", nullable = false)
	@NotNull
	@NotEmpty
	private String name;

	@Column(name = "SALARY", nullable = false)
	@DecimalMin(value = "0.1", inclusive = true)
	@NotNull
	private double salary;

	@Column(name = "STARTDATE", nullable = false)
	@NotNull
	private LocalDate startdate;

	private static final DateTimeFormatter DATE_FORMATTER_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter DATE_FORMATTER_2 = DateTimeFormatter.ofPattern("dd-MMM-yy");

	public Employee(String id, String login, String name, double salary, String startdate) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.salary = salary;
		this.startdate = formatDate(startdate);
	}

	public static final LocalDate formatDate(String startdate) {
		LocalDate formattedDate = null;
		try {
			if (startdate.length() == 9) {
				formattedDate = LocalDate.parse(startdate, DATE_FORMATTER_2);
			} else {
				formattedDate = LocalDate.parse(startdate, DATE_FORMATTER_1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return formattedDate;
	}

	public String getId() {
		return id;
	}

	public double getSalary() {
		return salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + ", startdate="
				+ startdate + "]";
	}

}