package com.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JoinMailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private String code;

    // 이메일 메세지 생성
    @Override
    public MimeMessage CreateMessage(String to) throws MessagingException, UnsupportedEncodingException {
        // 객체를 생성하여 이메일 메세지를 만듦
        MimeMessage message = mailSender.createMimeMassage();

        message.addRecipients(RecipientType.TO, to); // 이메일 받을 사람(to) 설정
        message.setSubject("회원가입 인증번호"); // 이메일 제목

        String msgg="";
        msgg += "<div style = 'margin:100px'>";
        msgg += "<h1> 안녕하세요.</h1>";
        msgg += "<h1> 책방 입니다.</h1>";
        msgg += "<p> 아래의 코드를 입력 란에다가 작성해주시길 바랍니다.</p>";
        msgg += "<h3 style = 'color : red;'> 회원가입 인증 코드입니다.</h3>";
        msgg += "<div style = 'font-size : 130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/>";
        msgg += "</div>";

        message.setText(msgg, "utf-8", "html");
        // 이메일 보낼 발신자 정보 설정
        message.setFrom(new InternetAddress("네이버 이메일주소", "보내는 사용자이름"));

        return message;
    }

    // 이메일 전송
    @Override
    public String sendSimpleMessage(String to) throws Exception {
        // 랜덤한 인증번호 생성
        code = createKey();
        // 이메일 메세지 생성
        MimeMessage message = CreateMessage(to);

        try{
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        // 이메일 전송 실행
        return code;
    }

    // 인증번호 생성
    @Override
    public String createKey() {
        Random random = new Random();
        // 100000 ~ 900000 랜덤숫자 생성
        int key = 100000+ random.nextInt(900000);
        // String으로 변환하여 반환
        return String.valueOf(key);
    }
}
