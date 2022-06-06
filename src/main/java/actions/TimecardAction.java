package actions;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TimecardView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.TimecardValidator;
import services.TimecardService;

public class TimecardAction extends ActionBase {
    private TimecardService service;

    // メソッドを実行する
    @Override
    public void process() throws ServletException, IOException {
        service = new TimecardService();

        // メソッドを実行
        invoke();

        service.close();
    }

    // 一覧画面を表示する
    // @throws ServletException
    // @throws IOException
    public void index() throws ServletException, IOException{

        // 現在時刻を取得(yyyy-MM-dd)
        String today = getToday("yyyy-MM-dd");

        List<TimecardView> timecards = service.getTodayTimecard(today);

        // 当日のタイムカードデータの件数を取得
        long timecardCount = service.countToday(today);


        putRequestScope(AttributeConst.TIMECARDS,timecards); // 取得したタイムカードデータ
        putRequestScope(AttributeConst.TIM_COUNT,timecardCount); // 全てのタイムカードデータの件数

        // セッションにフラッシュメッセージを設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);

        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        // 一覧画面を表示
        forward(ForwardConst.FW_TIM_INDEX);

    }

    // タイムカード登録画面を表示する
    // @throws ServletException
    // @throws IOException
    public void edit() throws ServletException,IOException{

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // ログインしている従業員のidを条件にタイムカードデータを取得する
        TimecardView tv = service.getTodayTimecard(ev.getId());


        // 現在時刻を生成
        LocalDateTime now = LocalDateTime.now();

        String now_date_ymd = getToday("yyyy年MM月dd日");
        String now_date_hms = getToday("HH:mm:ss");


        putRequestScope(AttributeConst.TOKEN, getTokenId()); // CSRF対策用トークン
        putRequestScope(AttributeConst.TIMECARD, tv); // 取得したタイムカードデータ
        putRequestScope(AttributeConst.NOW_DATE,now); // 現在時刻
        putRequestScope(AttributeConst.NOW_DATE_YMD,now_date_ymd); // 現在時刻(yyyy年MM月dd日)
        putRequestScope(AttributeConst.NOW_DATE_HMS,now_date_hms); // 現在時刻(HH:mm:ss)


        //登録画面を表示
        forward(ForwardConst.FW_TIM_EDIT);

    }

    // タイムカード更新
    // @throws ServletException
    // @throws IOException
    public void update() throws ServletException,IOException{

        // CSRF対策 tokenチェック
        if(checkToken()) {
            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            // ログインしている従業員のタイムカードデータを取得
            TimecardView tv = service.getTodayTimecard(ev.getId());

            // 押されたボタンごとに処理をする
            switch(getRequestParam(AttributeConst.TIMECARD_TYPE)) {

                case JpaConst.ATTENDANCE_BTN: // 出勤
                    if(tv == null) {

                        String attendanceAt = getRequestParam(AttributeConst.TIM_ATTENDANCE_AT); // 出勤時間

                        // 出勤時間が正しい値か判定
                        if(!TimecardValidator.checkDateTime(attendanceAt)) {

                            // 正しくなければ
                            // エラー画面を表示
                            forward(ForwardConst.FW_ERR_UNKNOWN);
                            return;
                        }

                        // 出勤時間をStringからLocalDatetimeに変換
                        LocalDateTime castAttendanceAt = LocalDateTime.parse(attendanceAt);

                        // タイムカードのインスタンス作成
                        TimecardView saveTv = new TimecardView();

                        saveTv.setEmployee(ev); // 従業員idをセット
                        saveTv.setAttendance_at(castAttendanceAt);// 出勤時間をセット

                        service.create(saveTv); // データ登録

                        // セッションに登録完了のフラッシュメッセージを設定
                        putSessionScope(AttributeConst.FLUSH,MessageConst.T_ATTENDANCE.getMessage());

                        // 一覧画面にリダイレクト
                        redirect(ForwardConst.ACT_TIM,ForwardConst.CMD_INDEX);

                    }else {
                        // エラー画面を表示
                        forward(ForwardConst.FW_ERR_UNKNOWN);
                    }

                    break;
                case JpaConst.LEAVING_BTN: // 退勤
                        if(tv != null && tv.getLeaving_at()==null) {
                            LocalDateTime attendanceAt = tv.getAttendance_at(); // 出勤時間
                            String leavingAt = getRequestParam(AttributeConst.TIM_LEAVING_AT); // 退勤時間
                            LocalDateTime restStartAt = tv.getRest_start_at(); // 休憩開始時間
                            LocalDateTime restEndAt = tv.getRest_end_at(); // 休憩終了時間
                            LocalTime workAt = null; // 労働時間
                            LocalTime restAt = tv.getRest_at(); // 休憩時間


                            // 退勤時間が正しい値か判定
                            if(!TimecardValidator.checkDateTime(leavingAt)) {

                                // 正しくなければ
                                // エラー画面を表示
                                forward(ForwardConst.FW_ERR_UNKNOWN);
                                return;
                            }

                            // 退勤時間をStringからLocalDatetimeに変換
                            LocalDateTime castLeavingAt = LocalDateTime.parse(leavingAt);


                            if(castLeavingAt.isBefore(attendanceAt)) {
                                // 退勤時間が出勤時間より前を入力していたら

                                // エラー画面を表示
                                forward(ForwardConst.FW_ERR_UNKNOWN);
                                return;
                            }

                            tv.setLeaving_at(castLeavingAt); // 退勤時間をセット

                            if(restStartAt !=null && restEndAt == null) {
                                // 休憩開始時間が登録されていて、休憩終了時間が登録されていない場合
                                // 休憩開始時間のJpaConst.ADD_REST_END_AT 時間後を休憩終了時間として登録する

                                restEndAt = tv.getRest_start_at().plusHours(JpaConst.ADD_REST_END_AT);
                                tv.setRest_end_at(restEndAt); // 休憩終了時間をセット
                            }

                            // 休憩時間
                            if(restAt == null &&restStartAt!=null && restEndAt !=null) {

                                restAt = getDiffLocalDateTime(restStartAt,restEndAt);

                                tv.setRest_at(restAt); // 休憩時間をセット
                            }

                            // 労働時間

                            workAt = getDiffLocalDateTime(attendanceAt, castLeavingAt); // 労働時間

                            if(restAt != null) {
                                // 休憩時間があったら

                                if(restAt.compareTo(workAt) > 0) {
                                    // 休憩時間が労働時間より多かったら

                                    // 労働時間を0に設定
                                    workAt = LocalTime.of(0, 0, 0);
                                }else {
                                    workAt = getDiffLocalTime(restAt,workAt); // 労働時間から休憩時間を引く
                                }

                            }

                            tv.setWork_at(workAt); // 労働時間をセット

                            service.update(tv); // データ更新

                            // セッションに登録完了のフラッシュメッセージを設定
                            putSessionScope(AttributeConst.FLUSH,MessageConst.T_LEAVING.getMessage());

                            // 一覧画面にリダイレクト
                            redirect(ForwardConst.ACT_TIM,ForwardConst.CMD_INDEX);

                        }else {
                            // エラー画面を表示
                            forward(ForwardConst.FW_ERR_UNKNOWN);
                        }
                    break;
                case JpaConst.REST_START_BTN: // 休憩開始
                    if(tv!=null && tv.getRest_start_at() == null && tv.getRest_end_at() == null) {

                        String restStartAt = getRequestParam(AttributeConst.TIM_REST_START_AT); // 休憩開始時間

                        // 休憩開始時間が正しい値か判定
                        if(!TimecardValidator.checkDateTime(restStartAt)) {

                            // 正しくなければ
                            // エラー画面を表示
                            forward(ForwardConst.FW_ERR_UNKNOWN);
                            return;
                        }

                        // 休憩開始時間をStringからLocalDatetimeに変換
                        LocalDateTime castRestStartAt = LocalDateTime.parse(restStartAt);

                        tv.setRest_start_at(castRestStartAt);

                        service.update(tv); // データ更新

                        // セッションに登録完了のフラッシュメッセージを設定
                        putSessionScope(AttributeConst.FLUSH,MessageConst.T_REST_START.getMessage());

                        // 一覧画面にリダイレクト
                        redirect(ForwardConst.ACT_TIM,ForwardConst.CMD_INDEX);


                    }else {
                        // エラー画面を表示
                        forward(ForwardConst.FW_ERR_UNKNOWN);
                    }
                    break;
                case JpaConst.REST_END_BTN: // 休憩終了
                    if(tv != null && tv.getRest_start_at() != null && tv.getRest_end_at() ==null && tv.getLeaving_at() ==null) {

                        String restEndAt = getRequestParam(AttributeConst.TIM_REST_END_AT); // 休憩終了時間

                        // 休憩開始時間が正しい値か判定
                        if(!TimecardValidator.checkDateTime(restEndAt)) {

                            // 正しくなければ
                            // エラー画面を表示
                            forward(ForwardConst.FW_ERR_UNKNOWN);
                            return;
                        }

                        // 休憩終了時間をStringからLocalDateTimeに変換
                        LocalDateTime castRestEndAt = LocalDateTime.parse(restEndAt);


                        LocalDateTime restStartAt = tv.getRest_start_at();// 休憩開始時間

                        if(castRestEndAt.isBefore(restStartAt)) {
                            // 退勤時間が出勤時間より前を入力していたら

                            // エラー画面を表示
                            forward(ForwardConst.FW_ERR_UNKNOWN);
                            return;
                        }

                        LocalTime restAt = getDiffLocalDateTime(restStartAt,castRestEndAt);

                        tv.setRest_end_at(castRestEndAt); // 休憩終了時間をセット
                        tv.setRest_at(restAt); // 休憩時間をセット


                        service.update(tv); // データ更新

                        // セッションに登録完了のフラッシュメッセージを設定
                        putSessionScope(AttributeConst.FLUSH,MessageConst.T_REST_END.getMessage());

                        // 一覧画面にリダイレクト
                        redirect(ForwardConst.ACT_TIM,ForwardConst.CMD_INDEX);

                    }else {
                        // エラー画面を表示
                        forward(ForwardConst.FW_ERR_UNKNOWN);
                    }
                    break;
                default :
                    // エラー画面を表示
                    forward(ForwardConst.FW_ERR_UNKNOWN);
                    break;
            }
        }
    }

    // 現在時刻を指定されたフォーマットに変換する
    // @param dateFormat フォーマットパターン(例 : yyyy-MM-dd)
    // @return 現在時刻
    public String getToday(String dateFormat) {
        // 現在時刻を生成
        LocalDateTime localDateTime = LocalDateTime.now();
        // 書式を指定
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern(dateFormat);
        // 指定の書式に日付データを渡す
        String datetimeformatted = datetimeformatter.format(localDateTime);

        return datetimeformatted;
    }

    // 2つのLocalDateTimeの差分をLocalDateTimeで返却する
    // @param localDateTime1 1つ目の日付
    // @param localDateTime2 2つ目の日付
    // @return LocalTime 差分の時間
    public LocalTime getDiffLocalDateTime(LocalDateTime localDateTime1,LocalDateTime localDateTime2) {
        // 時間、分、秒
        int hour,min,sec;


        int diffAt = (int)ChronoUnit.SECONDS.between(localDateTime1, localDateTime2); // 休憩開始時間と終了時間の差分

        hour = getHour(diffAt);
        min = getMinute(diffAt);
        sec = getSecond(diffAt);

        LocalTime diffLocalTime = LocalTime.of(hour, min, sec);

        return diffLocalTime;
    }

    // 2つのLocalTimeの差分を返却する
    // @param localTime1 1つ目の日付
    // @param localTime2 2つ目の日付
    // @return LocalTime 差分の時間
    public LocalTime getDiffLocalTime(LocalTime localDateTime1,LocalTime localDateTime2) {

        // 時間、分、秒
        int hour,min,sec;

        Duration durationTime = Duration.between(localDateTime1, localDateTime2); // 休憩開始時間と終了時間の差分

        hour = getHour((int)durationTime.getSeconds());
        min = getMinute((int)durationTime.getSeconds());
        sec = getSecond((int)durationTime.getSeconds());

        LocalTime diffLocalTime = LocalTime.of(hour, min, sec);

        return diffLocalTime;
    }

    // 秒を時間に変換
    // @param s 秒数
    // @return 時間
    public int getHour(int s) {
        return s/3600;
    }

    // 秒を分に変換
    // @param s秒数
    // @return 分
    public int getMinute(int s) {
        return (s/60) % 60;
    }

    // 秒を返す
    // @param s 秒数
    // @return 秒
    public int getSecond(int s) {
        return s % 60;
    }

}
