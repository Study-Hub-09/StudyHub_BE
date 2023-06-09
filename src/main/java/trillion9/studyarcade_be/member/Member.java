package trillion9.studyarcade_be.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trillion9.studyarcade_be.member.dto.MemberRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long kakaoId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Long dailyStudyTime;
    private Long totalStudyTime;
    private String title;
    private String imageUrl;
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Member(Long id, Long kakaoId, String nickname, String email, String password, Long dailyStudyTime, Long totalStudyTime, String title, String imageUrl) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.dailyStudyTime = dailyStudyTime;
        this.totalStudyTime = totalStudyTime;
        this.title = title != null ? title : "씨앗";
        this.imageUrl = imageUrl;
    }

    public void setDailyStudyTime(Long dailyStudyTime) {
        this.dailyStudyTime = dailyStudyTime;
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateStudyTime(Long roomStudyTime) {
        this.dailyStudyTime += roomStudyTime;
        this.totalStudyTime += roomStudyTime;
        updateTitle();
    }

    public void updateTitle() {
        if (this.totalStudyTime >= 1501 * 60 * 60) {
            this.title = "세계수";
        } else if (this.totalStudyTime >= 1001 * 60 * 60) {
            this.title = "거목";
        } else if (this.totalStudyTime >= 651 * 60 * 60) {
            this.title = "나무";
        } else if (this.totalStudyTime >= 401 * 60 * 60) {
            this.title = "묘목";
        } else if (this.totalStudyTime >= 201 * 60 * 60) {
            this.title = "잎사귀";
        } else if (this.totalStudyTime >= 51 * 60 * 60) {
            this.title = "새싹";
        } else {
            this.title = "씨앗";
        }
    }

    public void updateMember(MemberRequestDto memberRequestDto, String imageUrl, String password) {
        this.nickname = memberRequestDto.getNickname();
        this.imageUrl = imageUrl;
        this.password = password;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

