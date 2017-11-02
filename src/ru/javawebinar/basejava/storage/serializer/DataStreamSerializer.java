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

            for (SectionType sectionType : SectionType.values()) {
                dos.writeUTF(sectionType.name());
                if (r.getSections().containsKey(sectionType)) {
                    Section section = r.getSection(sectionType);
                    if (section instanceof TextSection) {
                        dos.writeInt(1);
                        TextSection textSection = (TextSection)section;
                        dos.writeUTF(textSection.getContent());
                    }
                    if (section instanceof ListSection) {
                        ListSection listSection = (ListSection)section;
                        dos.writeInt(listSection.getItems().size());
                        for (String item : listSection.getItems()) {
                            dos.writeUTF(item);
                        }
                    }
                    if (section instanceof OrganizationSection) {
                        OrganizationSection organizationSection = (OrganizationSection)section;
                        dos.writeInt(organizationSection.getOrganizations().size());
                        for (Organization organization : organizationSection.getOrganizations()) {
                            dos.writeUTF(organization.getHomePage().getName());
                            String url = organization.getHomePage().getUrl();
                            dos.writeUTF(url == null ? "NULL" : url);
                            dos.writeInt(organization.getPositions().size());
                            for (Organization.Position pos : organization.getPositions()) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                String desc = pos.getDescription();
                                dos.writeUTF(desc == null ? "NULL" : desc);
                            }
                        }
                    }
                }
                else dos.writeInt(0);
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
                int countSections = dis.readInt();

                if (countSections != 0) {
                    if (sectionName.equals("PERSONAL") || sectionName.equals("OBJECTIVE"))
                        resume.addSection(SectionType.valueOf(sectionName), new TextSection(dis.readUTF()));
                    if (sectionName.equals("ACHIEVEMENT") || sectionName.equals("QUALIFICATIONS"))
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(readListSection(dis, countSections)));
                    if (sectionName.equals("EXPERIENCE") || sectionName.equals("EDUCATION"))
                        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(readOrganizationSection(dis, countSections)));
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
            Link link = new Link(name, url.equals("NULL") ? null : url);
            int countPositions = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < countPositions; j++) {
                LocalDate start = LocalDate.parse(dis.readUTF());
                LocalDate end = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                positions.add(new Organization.Position(start, end, title, description.equals("NULL") ? null : description));
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
}
