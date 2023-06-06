package trillion9.studyarcade_be.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trillion9.studyarcade_be.room.Room;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDto {
	private String sessionId;
	private String roomName;
	private String roomContent;
	private String imageUrl;
	private Long userCount;
	private LocalDateTime createdAt;

	public RoomResponseDto(Room room) {
		this.sessionId = room.getSessionId();
		this.roomName = room.getRoomName();
		this.roomContent = room.getRoomContent();
		this.imageUrl = room.getImageUrl();
		this.userCount = room.getUserCount();
		this.createdAt = room.getCreatedAt();
	}
}