package com.example.case_study_module_4.service.scheduler;

import com.example.case_study_module_4.repository.IUserRepository;
import com.example.case_study_module_4.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PromotionScheduler {

    private final EmailService emailService;
    private final IUserRepository userRepository;

//    private final String[] customers = {
//            "khach1@gmail.com",
//            "khach2@gmail.com"
//    };

    /**
     * Gửi mail mỗi thứ Hai lúc 08:00 sáng.
     * Cron format: giây phút giờ ngày-tháng tháng thứ-ngày
     * "0 0 8 ? * MON" = 08:00 sáng thứ Hai hàng tuần
     */
    @Scheduled(cron = "0 0 8 ? * MON", zone = "Asia/Ho_Chi_Minh")
    public void sendWeeklyPromotion() {
        String subject = "🎉 Khuyến mãi đặc biệt tuần này!";
        String html = """
                <h2>Xin chào quý khách!</h2>
                <p>Tuần này, chúng tôi dành tặng bạn <b>mã giảm giá 20%</b> cho tất cả đơn hàng online.</p>
                <p>Hãy nhanh tay đặt món tại OsaHaNeat nhé 🍜</p>
                <br>
                <small>Thân mến,<br>Đội ngũ OsaHaNeat</small>
                """;

//        for (String email : customers) {
//            emailService.sendMail(email, subject, html);
//        }
//
//        System.out.println("✅ Đã gửi email khuyến mãi tự động đến " + customers.length + " khách hàng!");

        //Lay Email o DB
        List<String> emails = userRepository.findAllEmailsActiveUsers();

        for (String email : emails) {
            emailService.sendMail(email, subject, html);
        }
        System.out.println("✅ Đã gửi khuyến mãi đến " + emails.size() + " khách hàng.");
    }
}
