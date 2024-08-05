package com.fred.projectapi.model.entity;

import com.fred.projectapi.model.enums.Risk;
import com.fred.projectapi.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date estimatedEndDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	@Column(nullable = false)
	private Double budget;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Risk risk;

	@ManyToOne
	@JoinColumn(name = "manager_id", nullable = false)
	private Employee manager;

	@OneToMany(mappedBy = "project")
	private List<EmployeeProject> members;

}
