package com.company.controllers;

import com.company.dto.MessageDto;
import com.company.models.Message;
import com.company.repositories.ChatRepository;
import com.company.security.details.UserDetailsImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessagesController {
    @Autowired
    private ChatRepository chatRepository;
    private static final Boolean newMessage = false;

    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(chatRepository.getLastMessages(10));
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MessageDto messageDto) {
        chatRepository.save(new Message(userDetails.getUsername(), messageDto.getText(), LocalDateTime.now()));
        synchronized (newMessage) {
            newMessage.notifyAll();
        }
    }

    @SneakyThrows
    @GetMapping("/getNewMessage")
    public ResponseEntity<List<Message>> getNewMessage() {
        synchronized (newMessage) {
            if (!newMessage) {
                newMessage.wait();
            }
        }
        return ResponseEntity.ok(chatRepository.getLastMessages(1));
    }
}
