package net.JITTOU.ensetdemospringang.dtaos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.JITTOU.ensetdemospringang.entities.PaymentType;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NewPaymentDTO {
    private double amount;
    private PaymentType type;
    private LocalDate date;
    private String studentCode;
}
