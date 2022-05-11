package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.TimecardView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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

        // 指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        // 現在時刻を取得(Y-m-d)
        String today = getToday();

        List<TimecardView> timecards = service.getTodayPerPage(today,page);

        // 当日のタイムカードデータの件数を取得
        long timecardCount = service.countToday(today);

        putRequestScope(AttributeConst.TIMECARDS,timecards); // 取得したタイムカードデータ
        putRequestScope(AttributeConst.TIM_COUNT,timecardCount); // 全てのタイムカードデータの件数
        putRequestScope(AttributeConst.PAGE,page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW,JpaConst.ROW_PER_PAGE); // 1ページに表示する

        // セッションにフラッシュメッセージを設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        // 一覧画面を表示
        forward(ForwardConst.FW_TIM_INDEX);

    }

    // 現在時刻を取得する(Y-m-d)
    // @return 現在時刻(Y-m-d)
    public String getToday() {
        // 現在の日付を生成
        LocalDateTime localDateTime = LocalDateTime.now();
        // 書式を指定
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        // 指定の書式に日付データを渡す
        String datetimeformatted = datetimeformatter.format(localDateTime);

        return datetimeformatted;
    }

}
