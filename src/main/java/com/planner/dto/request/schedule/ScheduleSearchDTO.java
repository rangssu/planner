package com.planner.dto.request.schedule;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSearchDTO {
	private Long team_id;
	private String so;
	private String cal_title;
	private LocalDate cal_date;
}
