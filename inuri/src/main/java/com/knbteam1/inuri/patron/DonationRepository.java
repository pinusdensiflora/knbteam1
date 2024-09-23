package com.knbteam1.inuri.patron;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query("SELECT SUM(d.donationAmount) FROM Donation d WHERE d.ddate = :currentDate")
    Integer findTotalDonationsByDate(@Param("currentDate") LocalDateTime currentDate);
}
