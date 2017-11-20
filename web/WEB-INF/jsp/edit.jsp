<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>${SectionType.OBJECTIVE.title}:</h3>
        <input type="text" name="objective" size=60
               value="${resume.getSection(SectionType.OBJECTIVE).getContent()}"><br/>

        <h3>${SectionType.PERSONAL.title}:</h3>
        <input type="text" name="personal" size=60 value="${resume.getSection(SectionType.PERSONAL).getContent()}"><br/>

        <h3>${SectionType.ACHIEVEMENT.title}:</h3>
        <c:forEach var="achieve" items="${resume.getSection(SectionType.ACHIEVEMENT).getItems()}">
            <input type="text" name="achievement" size=60 value="${achieve}"><br/><br/>
        </c:forEach>
        <input type="text" name="achievement" size=60><br/><br/>

        <h3>${SectionType.QUALIFICATIONS.title}:</h3>
        <c:forEach var="qualif" items="${resume.getSection(SectionType.QUALIFICATIONS).getItems()}">
            <input type="text" name="qualifications" size=60 value="${qualif}"><br/><br/>
        </c:forEach>
        <input type="text" name="qualifications" size=60><br/><br/>

        <h3>${SectionType.EXPERIENCE.title}:</h3>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EXPERIENCE).getOrganizations()}">
            <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
            Название организации:
            <input type="text" name="qualifications" size=40 value="${organization.homePage.name}">
            &nbsp;URL организации:
            <input type="text" name="qualifications" size=40 value="${organization.homePage.url}"><br/><br/>
            <c:forEach var="position" items="${organization.positions}">
                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                <table>
                    <tr>
                        <td width="19%">
                            Позиция:<br/>
                            <input type="text" size="20" name="qualifications" value="${position.title}"><br/><br/>
                            Дата начала:<br/>
                            <input type="date" name="qualifications" value="${position.startDate}"><br/><br/>
                            Дата окончания:<br/>
                            <input type="date" name="qualifications" value="${position.endDate}">
                        </td>
                        <td>
                            Деятельность:<br/>
                            <textarea name="qualifications" rows="10" cols="103">${position.description}</textarea><br/>
                        </td>
                    </tr>
                </table>
            </c:forEach>
            <br/><br/>
        </c:forEach>

        <h3>${SectionType.EDUCATION.title}:</h3>
        <c:forEach var="organization" items="${resume.getSection(SectionType.EDUCATION).getOrganizations()}">
            Название организации:
            <input type="text" name="qualifications" size=40 value="${organization.homePage.name}">
            &nbsp;URL организации:
            <input type="text" name="qualifications" size=40 value="${organization.homePage.url}"><br/><br/>
            <c:forEach var="position" items="${organization.positions}">
                <table>
                    <tr>
                        <td width="19%">
                            Позиция:<br/>
                            <input type="text" size="20" name="qualifications" value="${position.title}"><br/><br/>
                            Дата начала:<br/>
                            <input type="date" name="qualifications" value="${position.startDate}"><br/><br/>
                            Дата окончания:<br/>
                            <input type="date" name="qualifications" value="${position.endDate}">
                        </td>
                        <td>
                            Деятельность:<br/>
                            <textarea name="qualifications" rows="10" cols="103">${position.description}</textarea><br/>
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

