/*
 * 생성자 : 김근환 
 * 생성일 : 9.15 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.24
 */
package com.knbteam1.inuri.patron;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knbteam1.inuri.auth.Customer;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
	private MailService mailService;

    public Donation createDonation(String method, Integer amount, Integer period, Child child, Customer customer) {
        Donation donation = new Donation();
        donation.setDdate(LocalDateTime.now());  // 현재 시간 저장
        donation.setDonationMethod(method);  // 후원 방법 (정기/일시)
        donation.setDonationAmount(amount);  // 후원 금액
        donation.setDonationPeriod(period);  // 정기 후원 기간
        donation.setChild(child);  // 후원 아동 정보
        donation.setCustomer(customer);  // 후원자 정보

        Donation savedDonation = donationRepository.save(donation);  // DB에 저장
        
        // 이메일 전송
        String emailSubject = "후원이 완료되었습니다.";
        String emailBody = customer.getName() + "님, " + child.getChname()+ " 어린이에게 " + amount + "원을 후원해주셔서 감사합니다.";  // 고객 이름 사용
        mailService.create(customer.getUsername(), emailSubject, emailBody);

        return savedDonation;
    }

    public Optional<Donation> findByChild_ChidAndCustomer_Cid(Integer childId, Integer customerId) {
        return donationRepository.findFirstByChild_ChidAndCustomer_Cid(childId, customerId);
    }
}
