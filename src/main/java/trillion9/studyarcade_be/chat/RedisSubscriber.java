package trillion9.studyarcade_be.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import trillion9.studyarcade_be.member.Member;
import trillion9.studyarcade_be.member.MemberRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

	private final ObjectMapper objectMapper;
	private final SimpMessageSendingOperations messagingTemplate;
	private final MemberRepository memberRepository;

	/* Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아서 처리.
	리스너가 대기하고 있다가 메세지 오면 RedisSubscriber.sendMessage 수행 */
	public void sendMessage(String publishMessage) {
		try {
			// ChatMessageDto 객채로 맵핑
			ChatMessageDto chatMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);
			String now = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
			chatMessage.setCreatedAt(now);

			// 프로필 이미지 설정
			String profileImage = retrieveProfileImage(chatMessage.getNickname());
			chatMessage.setProfileImage(profileImage);

			// 스터디룸을 구독한 클라이언트에게 메시지 발송
			messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getSessionId(), chatMessage);
		} catch (Exception e) {
			log.error("Exception {}", e);
		}
	}

	private String retrieveProfileImage(String nickname) {
		Optional<Member> member = memberRepository.findByNickname(nickname);

		if (member.isPresent() && member.get().getImageUrl() != null) {
			return member.get().getImageUrl();
		}

		return "대표 프로필 이미지 URL";
	}
}