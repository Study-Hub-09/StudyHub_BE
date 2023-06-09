package trillion9.studyarcade_be.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomCreateResponseDto {
	private String sessionId;
	private String roomName;
	private String roomContent;
	private String imageUrl;
	private boolean secret;
	private int userCount;
	private String category;
	private LocalDateTime createdAt;

	@Builder
	private RoomCreateResponseDto(String sessionId, String roomName, String roomContent, String imageUrl, boolean secret, int userCount, String category, LocalDateTime createdAt) {
		this.sessionId = sessionId;
		this.roomName = roomName;
		this.roomContent = roomContent;
		this.imageUrl = imageUrl;
		this.secret = secret;
		this.userCount = userCount;
		this.category = category;
		this.createdAt = createdAt;
	}
}