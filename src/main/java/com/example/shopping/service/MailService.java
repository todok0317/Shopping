package com.example.shopping.service;

public interface MailService {

    // 메일에 해당하는 내용을 작성하는 메소드
    MimeMessage CreateMessage(String to) throws Exception;

    // 메일을 보내기위한 메소드
    String sendSimpleMessage(String to) throws Exception;

    // 인증번호를 생성하기 위한 메소드
    public String createKey();
}
