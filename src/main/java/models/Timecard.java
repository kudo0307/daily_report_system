package models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// タイムカードデータのDTOモデル
@Table(name = JpaConst.TABLE_TIM)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TIM_GET_ALL,
            query = JpaConst.Q_TIM_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_TIM_GET_TODAY,
            query = JpaConst.Q_TIM_GET_TODAY_DEF),
    @NamedQuery(
            name = JpaConst.Q_TIM_TODAY_COUNT,
            query =   JpaConst.Q_TIM_TODAY_COUNT_DEF)
})

@Getter // 全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter // 全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor // 引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor // 全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Timecard {

    // id
    @Id
    @Column(name = JpaConst.TIM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 日報を作成した従業員のid
    @Column(name = JpaConst.TIM_COL_EMP,nullable = false)
    private Integer employee_id;

    // 出勤時間
    @Column(name = JpaConst.TIM_COL_ATD_AT,nullable = false)
    private LocalDateTime attendance_at;

    // 退勤時間
    @Column(name = JpaConst.TIM_COL_LEV_AT, nullable = false)
    private LocalDateTime leaving_at;

    // 休憩開始時間
    @Column(name = JpaConst.TIM_COL_REST_START_AT)
    private LocalDateTime rest_start_at;

    // 休憩終了時間
    @Column(name = JpaConst.TIM_COL_REST_END_AT)
    private LocalDateTime rest_end_at;

    // 労働時間
    @Column(name = JpaConst.TIM_COL_WORK_AT, nullable = false)
    private LocalDateTime work_at;

    // 休憩時間
    @Column(name = JpaConst.TIM_COL_REST_AT)
    private LocalTime rest_at;

    // 登録日時
    @Column(name = JpaConst.TIM_COL_CREATED_AT, nullable = false)
    private LocalDateTime created_at;

    // 更新日時
    @Column(name = JpaConst.TIM_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updated_at;

    // 削除日時
    @Column(name = JpaConst.TIM_COL_DELETED_AT)
    private LocalDateTime deleted_at;

}
