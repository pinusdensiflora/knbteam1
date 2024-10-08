/*
 * 생성자 : 김근환 
 * 생성일 : 9.15 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.24
 */
package com.knbteam1.inuri.patron;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knbteam1.inuri.auth.Customer;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
	Optional<Donation> findFirstByChild_ChidAndCustomer_Cid(Integer chid, Integer customerId);
	
	// 특정 고객의 전체 후원 조회
    List<Donation> findByCustomer(Customer customer);
    
    // 특정 아동에 대한 후원 내역 조회
    List<Donation> findByChild_ChidAndCustomer_Cid(Integer chid, Integer customerId);
}
