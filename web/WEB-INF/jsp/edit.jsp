<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <script src="js/edit.js" type="text/javascript"></script>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <c:if test="${type.name().equals(\"MAIL\")}">
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}" required></dd>
                </c:if>
                <c:if test="${!type.name().equals(\"MAIL\")}">
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                </c:if>
            </dl>
        </c:forEach>

        <h3>${SectionType.OBJECTIVE.title}:</h3>
        <input type="text" name="OBJECTIVE" size=60
               value="${resume.getSection(SectionType.OBJECTIVE).getContent()}" required><br/>

        <h3>${SectionType.PERSONAL.title}:</h3>
        <textarea name="PERSONAL" rows="5" cols="58" style="resize: none" >${resume.getSection(SectionType.PERSONAL).getContent()}</textarea>
        <br/>

        <h3>${SectionType.ACHIEVEMENT.title}: <a onclick="return addField('acievId', 'ACHIEVEMENT')" href="#"><img src="img/add.png"></a></h3>
        <div id="acievId">
        </div>
        <c:forEach var="achieve" items="${resume.getSection(SectionType.ACHIEVEMENT).getItems()}">
            <div>
                <table>
                    <tr>
                        <td>
                            <textarea name="ACHIEVEMENT" rows="5" cols="58" style="resize: none"
                                      required>${achieve}</textarea>
                        </td>
                        <td>
                            <a onclick="return deleteBlock(this)" href="#"><img src="img/delete.png"></a>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>

        <h3>${SectionType.QUALIFICATIONS.title}: <a onclick="return addField('qualId', 'QUALIFICATIONS')" href="#"><img src="img/add.png"></a></h3>
        <div id="qualId">
        </div>
        <c:forEach var="qualif" items="${resume.getSection(SectionType.QUALIFICATIONS).getItems()}">
            <div>
                <table>
                    <tr>
                        <td>
                            <textarea name="QUALIFICATIONS" rows="5" cols="58" style="resize: none"
                                      required>${qualif}</textarea>
                        </td>
                        <td>
                            <a onclick="return deleteBlock(this)" href="#"><img src="img/delete.png"></a>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>

        <h3>${SectionType.EXPERIENCE.title}: <a onclick="return addSectionBlock('expId', 'EXPERIENCE', 'expPos')" href="#"><img src="img/add.png"></a></h3>
        <div id="expId">
        </div>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EXPERIENCE).getOrganizations()}"
                   varStatus="loop">
            <div>
                <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                <b>Название организации:</b>
                <input id="org${loop.index}" type="text" name="EXPERIENCE" size=40 value="${organization.homePage.name}" required
                       onkeyup="saveName('org${loop.index}', 'orgPos${loop.index}')">
                &nbsp;<b>URL организации:</b>
                <input type="text" name="EXPERIENCE" size=40 value="${organization.homePage.url}">
                <a onclick="return deleteField(this)" href="#"><img src="img/delete.png"></a>
                <a onclick="return addPositionBlock('posId${loop.index}', 'expPos')" href="#"><img src="img/add.png"></a>
                <br/><br/>
                <div id="posId${loop.index}">
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <table style="padding: 0 0 2ch 0">
                            <tr>
                                <td width="19%">
                                    Позиция:<br/>
                                    <input type="hidden" id="orgPos${loop.index}" name="expPos"
                                           value="${organization.homePage.name}">
                                    <input type="text" size="20" name="expPos" value="${position.title}" required><br/><br/>
                                    Дата начала:<br/>
                                    <input type="date" name="expPos" value="${position.startDate}" required><br/><br/>
                                    Дата окончания:<br/>
                                    <input type="date" name="expPos"
                                           value="${position.endDate.isEqual(DateUtil.NOW) ? null : position.endDate}">
                                </td>
                                <td>
                                    Деятельность:<br/>
                                    <textarea name="expPos" rows="10" cols="103" style="resize: none">${position.description}</textarea>
                                </td>
                                <td>
                                    <a onclick="return deleteBlock(this)" href="#"><img src="img/delete.png"></a>
                                </td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

        <h3>${SectionType.EDUCATION.title}: <a onclick="return addSectionBlock('eduId', 'EDUCATION', 'eduPos')" href="#"><img src="img/add.png"></a></h3>
        <div id="eduId">
        </div>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EDUCATION).getOrganizations()}"
                   varStatus="loop">
            <div>
                <b>Название организации:</b>
                <input id="org${loop.index + 50}" type="text" name="EDUCATION" size=40 value="${organization.homePage.name}" required
                       onkeyup="saveName('org${loop.index + 50}', 'orgPos${loop.index + 50}')">
                &nbsp;<b>URL организации:</b>
                <input type="text" name="EDUCATION" size=40 value="${organization.homePage.url}">
                <a onclick="return deleteField(this)" href="#"><img src="img/delete.png"></a>
                <a onclick="return addPositionBlock('posId${loop.index + 50}', 'eduPos')" href="#"><img src="img/add.png"></a>
                <br/><br/>
                <div id="posId${loop.index + 50}">
                    <c:forEach var="position" items="${organization.positions}">
                        <table style="padding: 0 0 2ch 0">
                            <tr>
                                <td width="19%">
                                    Позиция:<br/>
                                    <input type="hidden" id="orgPos${loop.index + 50}" name="eduPos"
                                           value="${organization.homePage.name}">
                                    <input type="text" size="20" name="eduPos" value="${position.title}" required><br/><br/>
                                    Дата начала:<br/>
                                    <input type="date" name="eduPos" value="${position.startDate}" required><br/><br/>
                                    Дата окончания:<br/>
                                    <input type="date" name="eduPos"
                                           value="${position.endDate.isEqual(DateUtil.NOW) ? null : position.endDate}">
                                </td>
                                <td>
                                    Деятельность:<br/>
                                    <textarea name="eduPos" rows="10" cols="103" style="resize: none">${position.description}</textarea>
                                </td>
                                <td>
                                    <a onclick="return deleteBlock(this)" href="#"><img src="img/delete.png"></a>
                                </td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

