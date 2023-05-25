package trillion9.studyarcade_be.roomMember;

import org.springframework.data.jpa.repository.JpaRepository;
import trillion9.studyarcade_be.member.Member;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByMemberIdAndRoomMaster(Member member, boolean roomMaster);
}