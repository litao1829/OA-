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
public class ProcessFlow {
    private long processId;
    private long formId;
    private long operatorID;
    private String action;
    private String result;
    private String reason;
    private Date createTime;
    private Date auditTime;
    private int  orderNo;
    private String state;
    private int isLast;
}
