package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            // TODO implements sections

            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();

                switch (entry.getKey().name()) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        TextSection textSection = (TextSection) section;
                        dos.writeUTF(textSection.getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        writeListSection(dos, (ListSection)section);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        writeOrganizationSection(dos, (OrganizationSection)section);
                }
            }
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections

            while (dis.available() > 0) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.addSection(SectionType.valueOf(sectionName), new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        int countSections = dis.readInt();
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(readListSection(dis, countSections)));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        int countOrgSections = dis.readInt();
                        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(readOrganizationSection(dis, countOrgSections)));
                }
            }
            return resume;
        }
    }

    private List<Organization> readOrganizationSection(DataInputStream dis, int countSections) throws IOException{
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < countSections; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            Link link = new Link(name, url.equals("") ? null : url);
            int countPositions = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < countPositions; j++) {
                LocalDate start = LocalDate.parse(dis.readUTF());
                LocalDate end = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                positions.add(new Organization.Position(start, end, title, description.equals("") ? null : description));
            }
            organizations.add(new Organization(link, positions));
        }
        return organizations;
    }

    private List<String> readListSection(DataInputStream dis, int countSections) throws IOException{
        List<String> items = new ArrayList<>();
        for (int i = 0; i < countSections; i++) {
            items.add(dis.readUTF());
        }
        return items;
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) throws IOException{
        dos.writeInt(organizationSection.getOrganizations().size());
        for (Organization organization : organizationSection.getOrganizations()) {
            dos.writeUTF(organization.getHomePage().getName());
            String url = organization.getHomePage().getUrl();
            dos.writeUTF(url == null ? "" : url);
            dos.writeInt(organization.getPositions().size());
            for (Organization.Position pos : organization.getPositions()) {
                dos.writeUTF(pos.getStartDate().toString());
                dos.writeUTF(pos.getEndDate().toString());
                dos.writeUTF(pos.getTitle());
                String desc = pos.getDescription();
                dos.writeUTF(desc == null ? "" : desc);
            }
        }
    }

    private void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException{
        dos.writeInt(listSection.getItems().size());
        for (String item : listSection.getItems()) {
            dos.writeUTF(item);
        }
    }
}
