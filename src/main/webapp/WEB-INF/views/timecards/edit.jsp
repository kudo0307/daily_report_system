<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTim" value="${ForwardConst.ACT_TIM.getValue() }" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="timDisplayTop">
            <p>タイムカード</p>
        </div>
        <div class="nowDateYmd">
            <p><c:out value="${now_date_ymd}"/></p>
        </div>
        <div class="nowDateHms">
            <p><c:out value="${now_date_hms}"/></p>
        </div>
        <div id="timBtnBox">
            <div <c:if test="${timecard.attendance_at != null }">class="btnDisable"</c:if> >
                <form method="POST" action="<c:url value='?action=${actTim}&command=${commUpd}' />">
                    <input type="hidden" name="${AttributeConst.TIM_ATTENDANCE_AT.getValue() }" value="${now_date }" />
                    <input type="hidden" name="${AttributeConst.TIMECARD_TYPE.getValue() }" value="${AttributeConst.ATTENDANCE_BTN.getValue()}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value = "出勤" class="timecardBtn"  <c:if test="${timecard.attendance_at != null }">disabled</c:if>>
                </form>
            </div>
            <div <c:if test="${timecard.attendance_at == null }">class="btnDisable"</c:if> >
                <form method="POST" action="<c:url value='?action=${actTim}&command=${commUpd}' />">
                    <input type="hidden" name="${AttributeConst.TIM_LEAVING_AT.getValue() }" value="${now_date }" />
                    <input type="hidden" name="${AttributeConst.TIMECARD_TYPE.getValue() }" value="${AttributeConst.LEAVING_BTN.getValue()}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value = "退勤" class="timecardBtn"  <c:if test="${timecard.attendance_at ==null}">disabled</c:if>>
                </form>
            </div>
            <div <c:if test="${timecard.attendance_at ==null ||timecard.rest_start_at != null }">class="btnDisable"</c:if>>
                <form method="POST" action="<c:url value='?action=${actTim}&command=${commUpd}' />">
                    <input type="hidden" name="${AttributeConst.TIM_REST_START_AT.getValue() }" value="${now_date }" />
                    <input type="hidden" name="${AttributeConst.TIMECARD_TYPE.getValue() }" value="${AttributeConst.REST_START_BTN.getValue()}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value = "休憩開始" class="timecardBtn" <c:if test="${timecard.attendance_at ==null ||timecard.rest_start_at != null }">disabled</c:if>>
                </form>
            </div>
            <div <c:if test="${timecard.attendance_at ==null || timecard.rest_start_at == null || timecard.rest_end_at !=null }">class="btnDisable"</c:if>>
                <form method="POST" action="<c:url value='?action=${actTim}&command=${commUpd}' />">
                    <input type="hidden" name="${AttributeConst.TIM_REST_END_AT.getValue() }" value="${now_date }" />
                    <input type="hidden" name="${AttributeConst.TIMECARD_TYPE.getValue() }" value="${AttributeConst.REST_END_BTN.getValue()}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value = "休憩終了" class="timecardBtn" <c:if test="${timecard.attendance_at ==null || timecard.rest_start_at == null || timecard.rest_end_at !=null }">disabled</c:if>>
                </form>
            </div>
        </div>
    </c:param>
</c:import>