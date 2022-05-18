package actions.views;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// タイムカード情報について画面の入力値・出力値を扱うViewモデル

@Getter // 全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter // 全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor // 引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor // 全てのクラスフィールドを引数に持つ引数ありコンストラクタを自動生成する(Lombok)
public class TimecardView {

    // id
    private Integer id;

    //日報を作成した従業員のid
    private EmployeeView employee;

    // 出勤時間
    private LocalDateTime attendance_at;

    // 退勤時間
    private LocalDateTime leaving_at;

    // 休憩開始時間
    private LocalDateTime rest_start_at;

    // 休憩終了時間
    private LocalDateTime rest_end_at;

    // 労働時間
    private LocalTime work_at;

    // 休憩時間
    private LocalTime rest_at;

    // 登録日時
    private LocalDateTime created_at;

    // 更新日時
    private LocalDateTime updated_at;

    // 削除日時
    private LocalDateTime deleted_at;
}
