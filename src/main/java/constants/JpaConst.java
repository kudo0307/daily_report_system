package constants;

public interface JpaConst {
  //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)

    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

    // タイムカードテーブル
    String TABLE_TIM = "timecard"; // テーブル名
    // タイムカードテーブルカラム
    String TIM_COL_ID = "id"; // id
    String TIM_COL_EMP = "employee_id"; // タイムカードを作成した従業員のid
    String TIM_COL_ATD_AT = "attendance_at"; // 出勤時間
    String TIM_COL_LEV_AT = "leaving_at"; // 退勤時間
    String TIM_COL_REST_START_AT = "rest_start_at"; // 休憩開始時間
    String TIM_COL_REST_END_AT = "rest_end_at"; // 休憩終了時間
    String TIM_COL_WORK_AT = "work_at"; // 労働時間
    String TIM_COL_REST_AT = "rest_at"; // 休憩時間
    String TIM_COL_CREATED_AT = "created_at"; // 登録日時
    String TIM_COL_UPDATED_AT = "updated_at"; // 更新日時
    String TIM_COL_DELETED_AT = "deleted_at"; // 削除日時

    String NOW_DATE = "now_date"; // 現在時刻
    String NOW_DATE_YMD = "now_date_ymd"; // 現在時刻(yyyy-MM-dd)

    String TIMECARD_TYPE = "timecard_type"; // タイムカードの処理の種類
    String ATTENDANCE_BTN = "attendance_btn"; // 出勤ボタン
    String LEAVING_BTN = "leaving_btn"; // 退勤ボタン
    String REST_START_BTN = "rest_start_btn"; // 休憩開始ボタン
    String REST_END_BTN = "rest_end_btn"; // 休憩終了ボタン

    int ADD_REST_END_AT = 1; // 休憩終了時間を自動で計算するときの時間(hour)
    int POSSIBLE_MIN_MINUTE = 10; // 登録可能な時間のずれ(10分)


    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_TIM = "timecard"; // タイムカード

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員

    //NamedQueryの nameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_RESISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_RESISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;
    // 全てのタイムカードをidの降順に取得する
    String Q_TIM_GET_ALL = ENTITY_TIM + ".getAll";
    String Q_TIM_GET_ALL_DEF = "SELECT t FROM Timecard AS t ORDER BY t.id DESC";
    // 全てのタイムカードの件数を取得する
    String Q_TIM_COUNT = ENTITY_TIM + ".count";
    String Q_TIM_COUNT_DEF = "SELECT COUNT(t) FROM Timecard AS t";
    // 当日のタイムカードをidの降順に取得する
    String Q_TIM_GET_TODAY = ENTITY_TIM + ".getByAttendance";
    String Q_TIM_GET_TODAY_DEF = "SELECT t FROM Timecard AS t WHERE DATE_FORMAT(t.attendance_at,'%Y-%m-%d') = : " + NOW_DATE_YMD + " ORDER BY t.id DESC";
    // 当日のタイムカードの件数を取得する
    String Q_TIM_TODAY_COUNT = ENTITY_TIM + ".count";
    String Q_TIM_TODAY_COUNT_DEF = "SELECT COUNT(t) FROM Timecard AS t WHERE DATE_FORMAT(t.attendance_at,'%Y-%m-%d') = :" + NOW_DATE_YMD;
    // 指定した従業員の退勤時間が登録されていないタイムカードデータを取得する
    String Q_TIM_TODAY_TIMECARD = ENTITY_TIM + ".getMine";
    String Q_TIM_TODAY_TIMECARD_DEF = "SELECT t FROM Timecard AS t WHERE t.leaving_at = NULL AND t.employee.id =:" + TIM_COL_EMP;

}

