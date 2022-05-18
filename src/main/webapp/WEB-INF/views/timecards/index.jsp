<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
                    <th>出勤時間</th>
                    <th>退勤時間</th>
                    <th>休憩開始</th>
                    <th>休憩終了</th>
                    <th>労働時間</th>
                    <th>休憩時間</th>
                </tr>
                <c:forEach var="timecard" items="${timecards }" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>${timecard.employee.name}</td>
                        <fmt:parseDate  value="${timecard.attendance_at}" pattern="yyyy-MM-dd'T'HH:mm:ss"  var="attendanceAt" type="TIME"/>
                        <td><fmt:formatDate value="${attendanceAt}" pattern="MM/dd HH:mm:ss" /></td>
                        <fmt:parseDate  value="${timecard.leaving_at}" pattern="yyyy-MM-dd'T'HH:mm:ss"  var="leavingAt" type="TIME"/>
                        <td><fmt:formatDate value="${leavingAt}" pattern="MM/dd HH:mm:ss" /></td>
                        <fmt:parseDate  value="${timecard.rest_start_at}" pattern="yyyy-MM-dd'T'HH:mm:ss"  var="restStartAt" type="TIME"/>
                        <td><fmt:formatDate value="${restStartAt}" pattern="HH:mm:ss" /></td>
                        <fmt:parseDate  value="${timecard.rest_end_at}" pattern="yyyy-MM-dd'T'HH:mm:ss"  var="restEndAt" type="TIME"/>
                        <td><fmt:formatDate value="${restEndAt}" pattern="HH:mm:ss" /></td>
                        <td>${timecard.work_at }</td>
                        <td>${timecard.rest_at }</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p><a href="<c:url value='?action=${actTim}&command=${commEdit}' />">タイムカード</a></p>
    </c:param>
</c:import>