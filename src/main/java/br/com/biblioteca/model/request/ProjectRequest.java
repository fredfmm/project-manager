package com.fred.projectapi.model.request;


import com.fred.projectapi.model.enums.Risk;
import com.fred.projectapi.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class ProjectRequest {

    @NotNull(message = "message.name.mandatory")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "message.startDate.mandatory")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "message.estimatedEndDate.mandatory")
    private Date estimatedEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "message.endDate.mandatory")
    private Date endDate;

    @NotNull(message = "message.description.mandatory")
    private String description;

    @NotNull(message = "message.status.mandatory")
    private Status status;

    @NotNull(message = "message.risk.mandatory")
    private Risk risk;

    @NotNull(message = "message.budget.mandatory")
    private Double budget;

    @NotNull(message = "message.managerId.mandatory")
    private Long managerId;

}

