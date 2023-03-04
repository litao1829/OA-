package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveForm {
    private long formId;
    private long employeeId;
    private int formType;
    private Date startTime;
    private Date endTime;
    private String reason;
    private Date createTime;
    private String state;

}
