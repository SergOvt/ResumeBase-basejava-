<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.util.ContactsUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            ${ContactsUtil.toHtml(contactEntry.key, contactEntry.value)}<br/>
        </c:forEach>
    <p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="section" items="${resume.sections}">
            <jsp:useBean id="section"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <tr>
                <td colspan="2"><h2>${section.key.title}</h2></td>
            </tr>
            <c:choose>
                <c:when test="${section.key.name().equals(\"OBJECTIVE\") || section.key.name().equals(\"PERSONAL\")}">
                    <tr>
                        <td colspan="2">
                            ${section.value.getContent()}
                        </td>
                    </tr>
                </c:when>
                <c:when test="${section.key.name().equals(\"ACHIEVEMENT\") || section.key.name().equals(\"QUALIFICATIONS\")}">
                    <tr>
                        <td colspan="2">
                            <ul>
                            <c:forEach var="piceListSection" items="${section.value.getItems()}">
                                <li>
                                    ${piceListSection}
                                </li>
                            </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${section.key.name().equals(\"EXPERIENCE\") || section.key.name().equals(\"EDUCATION\")}">
                    <c:forEach var="organization" items="${section.value.getOrganizations()}">
                        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                        <tr>
                            <td colspan="2">
                                <c:if test="${!organization.homePage.url.equals(\"\")}">
                                    <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
                                </c:if>
                                <c:if test="${organization.homePage.url.equals(\"\")}">
                                    <h3>${organization.homePage.name}</h3>
                                </c:if>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <tr>
                                <td width="20%" style="vertical-align: top">
                                        ${DateUtil.printDate(position.startDate)} - ${DateUtil.printDate(position.endDate)}
                                </td>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

