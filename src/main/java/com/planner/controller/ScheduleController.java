package com.planner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planner.dto.request.schedule.CalendarDTO;
import com.planner.dto.request.schedule.CalendarPrintDTO;
import com.planner.dto.request.schedule.MapDTO;
import com.planner.dto.request.schedule.MapLikeDTO;
import com.planner.dto.request.schedule.ScheduleDTO;
import com.planner.dto.response.member.ResMemberDetail;
import com.planner.service.MapLikeService;
import com.planner.service.ScheduleService;
import com.planner.util.UserData;

import lombok.RequiredArgsConstructor;

@RequestMapping("/planner/")
@Controller
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;
	private final MapLikeService mapLikeService;
	
	// 달력 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("calendar")
	public String calendar() {
		return "calendar";
	}

	// calendar에서 일정 제목 표시
	@GetMapping("cal-sche")
	@ResponseBody
	public List<CalendarPrintDTO> calendarAndSchedule(CalendarDTO dto, @UserData ResMemberDetail detail) {
		// 그룹캘린더 아니면 dto.getTeam_id() == null
		if(dto.getTeam_id() == null) {
			dto.setMember_id(detail.getMember_id());
		}
		List<CalendarPrintDTO> list = scheduleService.calendarPrint(dto);
		return list;
	}

	//일정 보기
	@GetMapping("schedule")
	public String right(ScheduleDTO scheduleDTO, Model model, @RequestParam("date") String date,
		   				MapLikeDTO MapLikedto,@UserData ResMemberDetail detail) {
		List<ScheduleDTO> list = null;
		Long id = detail.getMember_id();
		list = scheduleService.schedule_select(id, date, -1L);
		MapLikedto.setMember_id(detail.getMember_id());
		ArrayList<MapLikeDTO> mapLikeList = mapLikeService.MapLikeSelect(id);
		model.addAttribute("mapLikeList", mapLikeList);
		model.addAttribute("date", date);
		model.addAttribute("list", list);
		return "schedule";
	}

	// 글 쓰기
	@PostMapping("schedule")
	public String schedulePro(ScheduleDTO scheduleDTO, @RequestParam("date") String date, Model model ,
													@UserData ResMemberDetail detail, MapDTO mapDTO) {
		model.addAttribute("date", date);
		scheduleService.schedule_insert(scheduleDTO, mapDTO, detail);
		return "redirect:/planner/calendar";
	}

	// 글삭제
	@ResponseBody
	@DeleteMapping("schedule/del")
	public void  scheduleDel(@RequestParam("schedule_id") Long schedule_id) {
		scheduleService.schedule_delete(schedule_id);
	}

	// 글수정
	@PostMapping("schedule/edt")
	public String schedulePro(ScheduleDTO scheduleDTO, MapDTO mapDTO ,Model model) {
		scheduleService.schedule_update(scheduleDTO, mapDTO);
		return "redirect:/planner/calendar";
	}

}
