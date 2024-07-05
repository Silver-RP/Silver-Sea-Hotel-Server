//package com.booking.app.Booking.App.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // Allow all origins
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedOrigins("http://localhost:5173")// Allowed HTTP methods
//                .allowedHeaders("*")  // Allowed headers
//                .allowCredentials(true)  // Allow credentials like cookies
//                .maxAge(3600);  // Cache preflight requests for 1 hour
//    }
//}
//
