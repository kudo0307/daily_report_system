package models.validators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import actions.views.TimecardView;
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

    // LocalDateTimeにparseできるか判定
    // @param at 出勤時間
    // @return true : 正しい値 , false : 不正な値
    public static Boolean checkDateTime(String at) {

        try {
            LocalDateTime castAt = LocalDateTime.parse(at);
        }catch (Exception e){
            return false;
        }


        return true;
    }



}
