package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssafy.trip.model.service.TripService;
import com.ssafy.trip.model.vo.GugunVO;
import com.ssafy.trip.model.vo.SidoVO;
import com.ssafy.trip.model.vo.TripTypeVO;
import com.ssafy.trip.model.vo.TripVO;

@RestController
@RequestMapping("/trip")
public class TripProcessController {
	
	@Autowired
	private TripService tripService;
	
	@PostMapping("get-region")
	public Map<String, List<?>> getRegion() {
		Map<String, List<?>> regions = new HashMap<>();
		
		List<SidoVO> sidos = tripService.getSido();
		regions.put("sido", sidos);
		
		List<GugunVO> guguns = tripService.getGugunBySidoCode(sidos.get(0).getSidoCode());
		regions.put("gugun", guguns);
		
		List<TripTypeVO> tripTypes = tripService.getTripType();
		regions.put("tripType", tripTypes);
		
		return regions;
	}
	
	@PostMapping("get-gugun")
	public List<GugunVO> getGugun(int sidoCode) {
		List<GugunVO> guguns = tripService.getGugunBySidoCode(sidoCode);
		
		return guguns;
	}
	
	@PostMapping("list")
	public PageInfo<TripVO> getTrip(TripVO trip, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TripVO> trips = tripService.getTripByRegion(trip);
		
		return PageInfo.of(trips);
	}
	
}
