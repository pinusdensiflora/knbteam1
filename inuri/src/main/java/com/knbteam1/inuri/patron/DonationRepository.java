/*
 * 생성자 : 김근환 
 * 생성일 : 9.15 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.24
 */
package com.knbteam1.inuri.patron;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

}
