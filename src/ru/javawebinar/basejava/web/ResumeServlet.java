package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    private static final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println(request.getServletPath());
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        /*String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');*/
        String param = request.getParameter("uuid");
        StringBuilder stringBuilder = new StringBuilder();
        if (param == null) {
            stringBuilder.append("" +
                    "<html>" +
                    "<body>" +
                    "   <table border=\"1\" width=\"50%\">" +
                    "       <th>UUID</th>" +
                    "       <th>ФИО</th>");
            for (Resume resume : storage.getAllSorted()) {
                stringBuilder.append("" +
                        "<tr>" +
                        "   <td>" +
                        resume.getUuid() +
                        "   </td>" +
                        "   <td>" +
                        resume.getFullName() +
                        "   </td>" +
                        "</tr>");
            }
            stringBuilder.append("" +
                    "   </table>" +
                    "</body>" +
                    "</html>");
        } else {
            Resume resume = storage.get(param);
            stringBuilder.append("" +
                    "<html>" +
                    "<body>" +
                    "<h1>" +
                    resume.getFullName() +
                    "</h1>" +
                    "<p>");
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                stringBuilder.append("" +
                        entry.getKey().getTitle() + ": " +
                        entry.getValue() +
                        "<br/>");
            }
            stringBuilder.append("<p><hr><table cellpadding=\"2\">" +
                    "        <tr>" +
                    "            <td colspan=\"2\"><h2>Позиция</h2></td>" +
                    "        </tr>" +
                    "        <tr>" +
                    "            <td colspan=\"2\"><h3>");
            TextSection textSection = (TextSection) resume.getSection(SectionType.OBJECTIVE);
            if (textSection != null) {
                stringBuilder.append(textSection)
                        .append("</h3>" +
                                "            </td>" +
                                "        </tr>" +
                                "        <tr>" +
                                "            <td colspan=\"2\"><h2>Личные качества</h2></td>" +
                                "        </tr>" +
                                "        <tr>" +
                                "            <td colspan=\"2\">");
            }
            textSection = (TextSection) resume.getSection(SectionType.PERSONAL);
            if (textSection != null) {
                stringBuilder.append(textSection)
                        .append("</td>" +
                                "        </tr>" +
                                "<tr>" +
                                "            <td colspan=\"2\"><h2>Достижения</h2></td>" +
                                "        </tr>" +
                                "        <tr>" +
                                "            <td colspan=\"2\">" +
                                "                <ul>");
            }
            ListSection listSection = (ListSection) resume.getSection(SectionType.ACHIEVEMENT);
            if (listSection != null) {
                for (String str : listSection.getItems()) {
                    stringBuilder.append("<li>").append(str).append("</li>");
                }
                stringBuilder.append("</ul>" +
                        "            </td>" +
                        "        </tr>" +
                        "        <tr>" +
                        "           <td colspan=\"2\"><h2>Квалификация</h2></td>" +
                        "        </tr>" +
                        "        <tr>" +
                        "            <td colspan=\"2\">" +
                        "                <ul>\n");
            }
            listSection = (ListSection) resume.getSection(SectionType.QUALIFICATIONS);
            if (listSection != null) {
                for (String str : listSection.getItems()) {
                    stringBuilder.append("<li>").append(str).append("</li>");
                }
                stringBuilder.append("</ul>" +
                        "            </td>" +
                        "        </tr>");
            }
            stringBuilder.append("</body></html>");
        }
        response.getWriter().write(stringBuilder.toString());
    }
}
