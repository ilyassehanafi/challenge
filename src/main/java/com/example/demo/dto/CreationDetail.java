package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreationDetail {
    int totalRecordsCount;
    int totalRecordsSaved;
    int totalRecordsNotSaved;
}
