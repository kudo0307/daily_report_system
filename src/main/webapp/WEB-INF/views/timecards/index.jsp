<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTim" value="${ForwardConst.ACT_TIM.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null }">
            <div id="flush_success">
                <c:out value="${flush}"/>
            </div>
        </c:if>
        <h2>タイムカード 一覧</h2>
        <table id="timecard_list">
            <tbody>
                <tr>
                    <th>氏名</th>
                    <th>日付</th>
                    <th>出勤時間</th>
                    <th>退勤時間</th>
                    <th>休憩開始</th>
                    <th>休憩終了</th>
                    <th>労働時間</th>
                    <th>休憩時間</th>
                </tr>
                <c:forEach var="timecard" items="${timecards }" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>仮氏名</td>
                        <td>ここに日付</td>
                        <td><c:out value="${timecard.attendance_at}"></c:out></td>
                        <td><c:out value="${timecard.leaving_at}"></c:out></td>
                        <td><c:out value="${timecard.rest_start_at}"></c:out></td>
                        <td><c:out value="${timecard.rest_end_at}"></c:out></td>
                        <td><c:out value="${timecard.work_at}"></c:out></td>
                        <td><c:out value="${timecard.rest_at}"></c:out></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全${timecards_count}件)<br />
            <c:forEach var="i" begin="1" end="${((timecards_count - 1) / maxRow)+1 }" step="1">
                <c:choose>
                    <c:when test="${i == page }">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTim}&command=${commIdx}&page=${i}' />"><c:out value="${i}"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <p><a href="<c:url value='?action=${actTim}&command=${commEdit}' />">タイムカード</a></p>
    </c:param>
</c:import>