package com.knbteam1.inuri.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	public List<Board> readlist() {
		
		
		return boardRepository.findAll();
	}
}
