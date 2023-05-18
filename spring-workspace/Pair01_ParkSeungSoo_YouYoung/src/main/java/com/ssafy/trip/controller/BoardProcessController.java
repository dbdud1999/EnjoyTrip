package com.ssafy.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssafy.trip.model.service.BoardService;
import com.ssafy.trip.model.vo.BoardVO;

@RestController
@RequestMapping("/board")
@CrossOrigin
public class BoardProcessController {
	
	@Autowired
	BoardService boardService;
	
	@PostMapping("list")
	public PageInfo<BoardVO> getList(String type, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BoardVO> boards = boardService.selectAll(type);
		
		return PageInfo.of(boards);
	}
	
	@PostMapping("view")
	public BoardVO getBoard(BoardVO boardVO) {
		return boardService.selectOne(boardVO);
	}
	
	@PostMapping("update")
	public int updateBoard (BoardVO boardVO) {
		return boardService.update(boardVO);
	}
	
}
