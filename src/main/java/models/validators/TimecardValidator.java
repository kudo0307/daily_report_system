package models.validators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import actions.views.TimecardView;
import constants.JpaConst;
import services.TimecardService;

public class TimecardValidator {

    // タイムカードインスタンスの各項目についてバリデーションを行う
    // @param service 呼び出し元Serviceクラスのインスタンス
    // @param tv TimecardViewのインスタンス
    // @return エラーのリスト

    public static List<String> validate(TimecardService service, TimecardView tv){
        List<String> errors = new ArrayList<String>();

        return errors;
    }

    // 入力された時間が正しい値かチェックする
    // @param at 対象の時間の文字列
    // @return true : 正しい値 , false : 不正な値
    public static Boolean checkDateTime(String at) {
        Boolean checkFlag = true;

        // LocalDateTimeにparseできるか判定
        if(!checkDateTimeParse(at)) {
            // parseできなかった場合

            checkFlag = false;
        }else {
            // parseできた場合

            // 登録可能な時間か判定
            if(!checkPossibleDateTime(LocalDateTime.parse(at))) {

                // 登録不可能な時間なら
                checkFlag = false;
            }
        }

        return checkFlag;
    }

    // LocalDateTimeにparseできるか判定
    // @param at 対象の時間の文字列
    // @return true : 正しい値 , false : 不正な値
    public static Boolean checkDateTimeParse(String at) {

        try {
            LocalDateTime castAt = LocalDateTime.parse(at);
        }catch (Exception e){
            return false;
        }


        return true;
    }

    // 登録可能な日時か判定
    // @param at チェックする日時
    // @return true:登録可能 , false:登録不可能
    public static Boolean checkPossibleDateTime(LocalDateTime at) {

        boolean checkFlag = false;

        LocalDateTime now =  LocalDateTime.now(); // 現在時刻を取得
        LocalDateTime minDateTime =  now.minusMinutes(JpaConst.POSSIBLE_MIN_MINUTE); // 登録可能範囲

        // 時間を比較する
        if(at.isAfter(minDateTime)&&at.isBefore(now)) {
            checkFlag = true;
        }

        return checkFlag;
    }

    // 現在時刻を指定されたフォーマットに変換する
    // @param dateFormat フォーマットパターン(例 : yyyy-MM-dd)
    // @return 現在時刻
    public static String getToday(String dateFormat) {
        // 現在時刻を生成
        LocalDateTime localDateTime = LocalDateTime.now();
        // 書式を指定
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern(dateFormat);
        // 指定の書式に日付データを渡す
        String datetimeformatted = datetimeformatter.format(localDateTime);

        return datetimeformatted;
    }



}
