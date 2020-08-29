<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${survey.title}预览</title>
    <link rel="stylesheet" href="../../static/lib/layui-src/css/layui.css" media="all">
    <link rel="stylesheet" href="../../static/css/question.css" media="all">
    <style>
        .bg{
           background-image: url("../../upload/${survey.bgimg}");
           background-size: 100% 100%;
        }
    </style>
</head>
<body>
<div style="margin: 5px;float: right;margin-right: 300px;">
    <form action="../upload" enctype="multipart/form-data" method="post">
        <input type="file" name="file">
        <input type="hidden" name="id" value="${survey.id}">
        <input type="submit" style="background-color: #009688;width: 100px;color: #ffffff;border:1px solid #cccccc;" value="上传背景图">
    </form>
</div>
<div class="layuimini-container bg" style="position: absolute;bottom: 0px;top: 40px;left: 0px;right:0px;margin: 10px;">
    <div class="preview" id="preview" style="overflow: auto;">
        <ul class="content">
            <c:forEach items="${survey.questions}" var="question" varStatus="status">
                <c:if test="${question.type == 1  or question.type == 2 }">
                    <div class="radioTemplate spliter">
                        <li class="using radio">
                            <input type="hidden">
                            <div class="title">
                                <div class="no_edit"><strong>${status.index+1}、${question.title}</strong></div>
                                <c:if test="${question.remark != null && question.remark != ''}">
                                    <div class="no_edit">${question.remark}</div>
                                </c:if>
                            </div>
                            <div class="options">
                                <ul>
                                    <c:forEach items="${question.options}" var="option">
                                        <li>
                                           <table>
                                               <tr>
                                                   <td>
                                                       <c:if test="${question.type == 1}"><input type="radio" name="r${question.id}"></c:if>
                                                       <c:if test="${question.type == 2}"><input type="checkbox" name="r${question.id}"></c:if>
                                                   </td>
                                                   <td>${option.opt}</td>
                                               </tr>
                                           </table>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </div>
                </c:if>
                <c:if test="${question.type == 3  or question.type == 4 }">
                    <div class="inputTemplate spliter">
                        <li class="using inputx">
                            <input type="hidden">
                            <div class="title">
                                <div class="no_edit"><strong>${status.index+1}、${question.title}</strong></div>
                                <c:if test="${question.remark != null && question.remark != ''}">
                                    <div class="no_edit">${question.remark}</div>
                                </c:if>
                            </div>
                            <div>
                            <c:if test="${question.type == 3 }">
                                <input type="text" class="inputx">
                            </c:if>
                               <c:if test="${question.type == 4 }">
                                    <textarea class="mytext"></textarea>
                               </c:if>
                            </div>
                        </li>
                    </div>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

<script src="../../static/lib/layui-src/layui.js" charset="utf-8"></script>
<script src="../../static/js/lay-config.js" charset="utf-8"></script>

</body>
</html>