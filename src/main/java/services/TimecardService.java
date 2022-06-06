package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.TimecardConverter;
import actions.views.TimecardView;
import constants.JpaConst;
import models.Timecard;

// タイムカードテーブルにかかわる処理を行うクラス
public class TimecardService extends ServiceBase {
    // 指定されたページ数の一覧画面に表示する出勤日が当日のデータを取得し、TimecardViewのリストで返却する
    // @param today 当日の日付
    // @return 表示するデータのリスト
    public List<TimecardView> getTodayTimecard(String today){


        List<Timecard> t = null;

        try {


            t = em.createNamedQuery(JpaConst.Q_TIM_GET_TODAY,Timecard.class)
                    .setParameter(JpaConst.NOW_DATE_YMD,today )
                    .getResultList();
        }catch(NoResultException ex) {

        }

        return TimecardConverter.toViewList(t);

    }

    // タイムカードテーブルのデータの件数を取得し、返却する
    // @return タイムカードテーブルのデータの件数
    public long countToday(String today) {
        long timCount = (long) em.createNamedQuery(JpaConst.Q_TIM_TODAY_COUNT,Long.class)
                .setParameter(JpaConst.NOW_DATE_YMD,today )
                .getSingleResult();

        return timCount;
    }


    // 従業員idを条件に取得したデータをTimecardViewのインスタンスで返却する
    // @param id タイムカードのid
    // @return 取得データのインスタンス
    public TimecardView findOne(int id) {
        Timecard t = findOneInternal(id);
        return TimecardConverter.toView(t);
    }

    // 従業員idを条件に取得したデータをTimecardViewのインスタンスで返却する
    // @param empId 従業員id
    // @return 取得データのインスタンス
    public TimecardView getTodayTimecard(int empId) {
        Timecard t = null;
        try {
            t = em.createNamedQuery(JpaConst.Q_TIM_TODAY_TIMECARD,Timecard.class)
                    .setParameter(JpaConst.TIM_COL_EMP, empId)
                    .getSingleResult();
        }catch(NoResultException ex) {

        }
        return TimecardConverter.toView(t);
    }

    // タイムカードの登録内容を元にデータを一軒作成し、タイムカードテーブルに登録する
    // @param tv 画面から入力された従業員の登録内容
    public void create(TimecardView tv){

        // 登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        tv.setCreated_at(now);
        tv.setUpdated_at(now);

        // データを登録する
        em.getTransaction().begin();
        em.persist(TimecardConverter.toModel(tv));
        em.getTransaction().commit();

    }

    // タイムカードの更新内容を元にデータを1件作成し、タイムカードテーブルを更新する
    // @param tv 画面から入力されたタイムカードの登録内容
    public void update(TimecardView tv){
        // idを条件に登録済みのタイムカード情報を取得する
        TimecardView savedTim = findOne(tv.getId());

        savedTim.setLeaving_at(tv.getLeaving_at()); // 退勤時間
        savedTim.setRest_start_at(tv.getRest_start_at()); // 休憩開始時間
        savedTim.setRest_end_at(tv.getRest_end_at()); // 休憩終了時間
        savedTim.setWork_at(tv.getWork_at()); // 労働時間
        savedTim.setRest_at(tv.getRest_at()); // 休憩時間

        // 更新日時に現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        savedTim.setUpdated_at(now);


        // データを更新する
        em.getTransaction().begin();
        Timecard t = findOneInternal(tv.getId());
        TimecardConverter.copyViewToModel(t, tv);
        em.getTransaction().commit();


    }



    // idを条件にデータを1件取得し、Timecardのインスタンスで返却する
    // @param id タイムカードのid
    // @return 取得データのインスタンス
    private Timecard findOneInternal(int id) {
        Timecard t = em.find(Timecard.class, id);

        return t;
    }



}
