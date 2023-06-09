package trillion9.studyarcade_be.studytime;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class StudyTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long memberId;
    private LocalDate dailyDate;
    private Long dailyStudyTime;

    public StudyTime(Long memberId, LocalDate dailyDate, Long dailyStudyTime) {
        this.memberId = memberId;
        this.dailyDate = dailyDate;
        this.dailyStudyTime = dailyStudyTime;
    }
}

