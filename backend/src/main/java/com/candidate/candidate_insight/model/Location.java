package com.candidate.candidate_insight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String locationcode;
    private String locationname;
    private String locationaddress;
}
