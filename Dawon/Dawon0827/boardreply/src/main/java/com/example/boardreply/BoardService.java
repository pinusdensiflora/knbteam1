package com.example.boardreply;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	//readlist
	List<Board> readlist() {
		return boardRepository.findAll();
	}
	
	//readdetail
	Board readdetail(Integer id) {
		Optional<Board> ob = boardRepository.findById(id);
		return ob.get();
	}
	
	
}