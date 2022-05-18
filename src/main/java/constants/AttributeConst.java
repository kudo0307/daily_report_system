package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の従業員
    LOGIN_EMP("login_employee"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //従業員管理
    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_ADMIN_FLG("admin_flag"),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    // タイムカード
    TIMECARD("timecard"),
    TIMECARDS("timecards"),
    TIM_COUNT("timecards_count"),
    TIM_ID("id"),
    TIM_EMPLOYEE_ID("employee_id"),
    TIM_ATTENDANCE_AT("attendance_at"),
    TIM_LEAVING_AT("leaving_at"),
    TIM_REST_START_AT("rest_start_at"),
    TIM_REST_END_AT("rest_end_at"),
    TIM_WORK_AT("work_at"),
    TIM_REST_AT("rest_at"),

    NOW_DATE("now_date"), // 現在時刻
    NOW_DATE_YMD("now_date_ymd"), // 現在時刻(yyyy年MM月dd日)
    NOW_DATE_HMS("now_date_hms"), // 現在時刻(HH時mm分ss秒)
    TIMECARD_TYPE("timecard_type"), // タイムカードの処理の種類
    ATTENDANCE_BTN("attendance_btn"), // 出勤ボタン
    LEAVING_BTN("leaving_btn"), // 退勤ボタン
    REST_START_BTN("rest_start_btn"), // 休憩開始ボタン
    REST_END_BTN("rest_end_btn"), // 休憩終了ボタン


    //日報管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}