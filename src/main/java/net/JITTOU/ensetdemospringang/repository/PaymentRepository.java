package net.JITTOU.ensetdemospringang.repository;

import net.JITTOU.ensetdemospringang.entities.Payment;
import net.JITTOU.ensetdemospringang.entities.PaymentStatus;
import net.JITTOU.ensetdemospringang.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
