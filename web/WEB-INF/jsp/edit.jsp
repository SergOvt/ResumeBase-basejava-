<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    function saveName() {
        document.getElementById('expPosId').value = document.getElementById('exp').value;
        document.getElementById('eduPosId').value = document.getElementById('edu').value;
    }
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
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
        <input type="text" name="PERSONAL" size=60 value="${resume.getSection(SectionType.PERSONAL).getContent()}"><br/>

        <h3>${SectionType.ACHIEVEMENT.title}:</h3>
        <div id="achievId"></div>
        <c:forEach var="achieve" items="${resume.getSection(SectionType.ACHIEVEMENT).getItems()}">
            <input type="text" name="ACHIEVEMENT" size=60 value="${achieve}" required>
            <br/><br/>
        </c:forEach>
        </div>

        <h3>${SectionType.QUALIFICATIONS.title}:</h3>
        <c:forEach var="qualif" items="${resume.getSection(SectionType.QUALIFICATIONS).getItems()}">
            <input type="text" name="QUALIFICATIONS" size=60 value="${qualif}" required><br/><br/>
        </c:forEach>

        <h3>${SectionType.EXPERIENCE.title}:</h3>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EXPERIENCE).getOrganizations()}">
            <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
            Название организации:
            <input id="exp" type="text" name="EXPERIENCE" size=40 value="${organization.homePage.name}" required onkeyup="saveName()">
            &nbsp;URL организации:
            <input type="text" name="EXPERIENCE" size=40 value="${organization.homePage.url}"><br/><br/>
            <c:forEach var="position" items="${organization.positions}">
                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                <input type="hidden" id="expPosId" name="expPos" value="${organization.homePage.name}">
                <table>
                    <tr>
                        <td width="19%">
                            Позиция:<br/>
                            <input type="text" size="20" name="expPos" value="${position.title}" required><br/><br/>
                            Дата начала:<br/>
                            <input type="date" name="expPos" value="${position.startDate}" required><br/><br/>
                            Дата окончания:<br/>
                            <input type="date" name="expPos" value="${position.endDate.isEqual(DateUtil.NOW) ? null : position.endDate}">
                        </td>
                        <td>
                            Деятельность:<br/>
                            <textarea name="expPos" rows="10" cols="103" style="resize: none" required>${position.description}</textarea><br/>
                        </td>
                    </tr>
                </table>
            </c:forEach>
            <br/><br/>
        </c:forEach>

        <h3>${SectionType.EDUCATION.title}:</h3>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EDUCATION).getOrganizations()}">
            Название организации:
            <input id="edu" type="text" name="EDUCATION" size=40 value="${organization.homePage.name}" required onkeyup="saveName()">
            &nbsp;URL организации:
            <input type="text" name="EDUCATION" size=40 value="${organization.homePage.url}"><br/><br/>
            <c:forEach var="position" items="${organization.positions}">
                <input type="hidden" id="eduPosId" name="eduPos" value="${organization.homePage.name}">
                <table>
                    <tr>
                        <td width="19%">
                            Позиция:<br/>
                            <input type="text" size="20" name="eduPos" value="${position.title}" required><br/><br/>
                            Дата начала:<br/>
                            <input type="date" name="eduPos" value="${position.startDate}" required><br/><br/>
                            Дата окончания:<br/>
                            <input type="date" name="eduPos" value="${position.endDate.isEqual(DateUtil.NOW) ? null : position.endDate}">
                        </td>
                        <td>
                            Деятельность:<br/>
                            <textarea name="eduPos" rows="10" cols="103" style="resize: none" required>${position.description}</textarea><br/>
                        </td>
                    </tr>
                </table>
            </c:forEach>
            <br/><br/>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

