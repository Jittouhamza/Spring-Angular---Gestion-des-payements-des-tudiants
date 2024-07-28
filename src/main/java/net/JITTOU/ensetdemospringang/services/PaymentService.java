package net.JITTOU.ensetdemospringang.services;

import jakarta.transaction.Transactional;
import net.JITTOU.ensetdemospringang.repository.PaymentRepository;
import net.JITTOU.ensetdemospringang.repository.StudentRepository;
import net.JITTOU.ensetdemospringang.dtaos.NewPaymentDTO;
import net.JITTOU.ensetdemospringang.entities.Payment;
import net.JITTOU.ensetdemospringang.entities.PaymentStatus;
import net.JITTOU.ensetdemospringang.entities.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;


    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(newPaymentDTO.getStudentCode());
        Payment payment = Payment.builder()
                .date(newPaymentDTO.getDate()).type(newPaymentDTO.getType()).student(student).amount(newPaymentDTO.getAmount())
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
