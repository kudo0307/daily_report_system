package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Timecard;

// タイムカードデータのDTOモデル⇔Viewモデルの変換を行うクラス

public class TimecardConverter {
    // ViewモデルのインスタンスからDTOモデルのインスタンスを生成する
    // @param tv TimecardViewのインスタンス
    // @return Timecardのインスタンス

    public static Timecard toModel(TimecardView tv) {

        return new Timecard(
                tv.getId(),
                EmployeeConverter.toModel(tv.getEmployee()),
                tv.getAttendance_at(),
                tv.getLeaving_at(),
                tv.getRest_start_at(),
                tv.getRest_end_at(),
                tv.getWork_at(),
                tv.getRest_at(),
                tv.getCreated_at(),
                tv.getUpdated_at(),
                tv.getDeleted_at()

                );

    }

    // DTOモデルのインスタンスからViewモデルのインスタンスを生成する
    public static TimecardView toView(Timecard t) {

        if(t == null) {
            return null;
        }



        return new TimecardView(
                t.getId(),
                EmployeeConverter.toView(t.getEmployee()),
                t.getAttendance_at(),
                t.getLeaving_at(),
                t.getRest_start_at(),
                t.getRest_end_at(),
                t.getWork_at(),
                t.getRest_at(),
                t.getCreated_at(),
                t.getUpdated_at(),
                t.getDeleted_at()

                );
    }

    // DTOモデルのリストからViewモデルのリストを作成する
    // @param list DTOモデルのリスト
    // @return Viewモデルのリスト
    public static List<TimecardView> toViewList(List<Timecard> list){

        List<TimecardView> tvs = new ArrayList<>();

        for(Timecard t : list) {
            tvs.add(toView(t));
        }

        return tvs;
    }

    // Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
    // @param t DTOモデル(コピー先)
    // @param tv Viewモデル(コピー先)
    public static void copyViewToModel(Timecard t,TimecardView tv) {
        t.setId(tv.getId());
        t.setEmployee(EmployeeConverter.toModel(tv.getEmployee()));
        t.setAttendance_at(tv.getAttendance_at());
        t.setLeaving_at(tv.getLeaving_at());
        t.setRest_start_at(tv.getRest_start_at());
        t.setRest_end_at(tv.getRest_end_at());
        t.setWork_at(tv.getWork_at());
        t.setRest_at(tv.getRest_at());
        t.setCreated_at(tv.getCreated_at());
        t.setUpdated_at(tv.getUpdated_at());
        t.setDeleted_at(tv.getDeleted_at());
    }

}
