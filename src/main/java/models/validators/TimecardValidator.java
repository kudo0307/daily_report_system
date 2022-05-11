package models.validators;

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

        //

        return errors;
    }



}
