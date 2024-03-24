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
    int totalRecordsCount=0;
    int totalRecordsSaved=0;
    int totalRecordsNotSaved=0;

	public void incrementRecordsSaved() {
		this.totalRecordsSaved++;
	}

	public void incrementRecordsNotSaved() {
		this.totalRecordsNotSaved++;
	}
}
