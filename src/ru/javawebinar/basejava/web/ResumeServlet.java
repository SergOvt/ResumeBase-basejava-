package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> params = request.getParameterMap();
        Resume r = new Resume("");
        String uuid = params.get("uuid")[0];
        if (uuid != null && uuid.trim().length() != 0) r = storage.get(uuid);
        r.setFullName(params.get("fullName")[0]);

        for (ContactType type : ContactType.values()) {
            String[] values = params.get(type.name());
            if (values != null && values[0].trim().length() != 0) {
                r.addContact(type, values[0]);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String[] values = params.get(type.name());
            if (values != null && values[0].trim().length() != 0) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(values[0]));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(values));
                        break;
                    case EXPERIENCE:
                        r.addSection(type, new OrganizationSection(getOrganizationList(values, params.get("expPos"))));
                        break;
                    case EDUCATION:
                        r.addSection(type, new OrganizationSection(getOrganizationList(values, params.get("eduPos"))));
                        break;
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (uuid == null || uuid.trim().length() == 0) storage.save(r);
        else storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "create":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private List<Organization> getOrganizationList(String[] organizations, String[] positions) {
        List<Organization> resultList = new ArrayList<>();
        for (int i = 0; i < organizations.length - 1; i += 2) {
            List<Organization.Position> positionList = new ArrayList<>();
            int posLength = positions == null ? 0 : positions.length;
            for (int j = 0; j < posLength - 4; j += 5) {
                if (positions[j].equals(organizations[i])) {
                    positionList.add(new Organization.Position(LocalDate.parse(positions[j + 2]),
                            positions[j + 3].equals("") ? DateUtil.NOW : LocalDate.parse(positions[j + 3]),
                            positions[j + 1], positions[j + 4]));
                }
            }
            resultList.add(new Organization(new Link(organizations[i], organizations[i + 1]), positionList));
        }
        return resultList;
    }
}
