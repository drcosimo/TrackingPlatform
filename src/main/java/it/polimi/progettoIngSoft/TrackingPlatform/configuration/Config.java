//package it.polimi.progettoIngSoft.TrackingPlatform.configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class Config {
//
//    @Value("${spring.mail.host}")
//    private String springMailHost;
//
//    @Value("${spring.mail.port}")
//    private Integer springMailPort;
//
//    @Value("${spring.mail.username}")
//    private String springMailUsername;
//
//    @Value("${spring.mail.password}")
//    private String springMailPassword;
//
//    private JavaMailSender javaMailSender;
//
//    @Bean
//    public Config init(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(springMailHost);
//        mailSender.setPort(springMailPort);
//
//        mailSender.setUsername(springMailUsername);
//        mailSender.setPassword(springMailPassword);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        this.javaMailSender = mailSender;
//        return this;
//    }
//
//    public JavaMailSender getJavaMailSender() {
//        return javaMailSender;
//    }
//
//}
