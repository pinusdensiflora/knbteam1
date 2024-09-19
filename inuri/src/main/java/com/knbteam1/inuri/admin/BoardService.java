/*
 * 생산자: 배다원
 * 생산일: 9/13
 * 연락처 : dawnzeze@gmail.com
 * */
package com.knbteam1.inuri.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	public List<Board> readlist() {
		
		
		return boardRepository.findAll();
	}
	
	public List<Board> findListBcate(Integer bcate) {
		
		
		return boardRepository.findByBcate(bcate);
	}
	
	
	
	
	public Board findBoard(Integer bid) {
		//bid 값으로 어디의 보드정보 가져오기
		
		Optional<Board> ob =  boardRepository.findById(bid);
		return ob.get();
	}
	
	public String getBname(Integer bid) {
		Optional<Board> ob =  boardRepository.findById(bid);

		return ob.get().getBname();
	}
	
	public Integer getBcate(Integer bid) {
		Optional<Board> ob =  boardRepository.findById(bid);

		return ob.get().getBcate();
	}
	
}
