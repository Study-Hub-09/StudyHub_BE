package trillion9.studyarcade_be.global.config;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trillion9.studyarcade_be.chat.ChatRequestDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

	private final ObjectMapper objectMapper;
	private final SimpMessageSendingOperations messagingTemplate;

	/**
	 * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
	 */
	// 리스너가 대기하고 있다가 메세지 오면 RedisSubscriber.sendMessage가 수행됨

	public void sendMessage(String publishMessage) {
		try {
			// ChatRequestDto 객채로 맵핑
			ChatRequestDto chatMessage = objectMapper.readValue(publishMessage, ChatRequestDto.class);
			// 스터디룸을 구독한 클라이언트에게 메시지 발송
			messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getSessionId(), chatMessage);
		} catch (Exception e) {
			log.error("Exception {}", e);
		}
	}
}