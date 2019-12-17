package ru.academits.dao;

import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ContactDao {
    private List<Contact> contactList = new ArrayList<>();
    private AtomicInteger idSequence = new AtomicInteger(0);

    private Random random = new Random();

    public ContactDao() {
        Contact contact = new Contact();
        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");
        contactList.add(contact);
    }

    public Integer getRandomId() {
        if (contactList.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(contactList.size());
        return contactList.get(randomIndex).getId();
    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAll() {
        return new ArrayList<>(this.contactList);
    }

    public List<Contact> getAllContacts(String filterQuery) {
        if (filterQuery != null && !filterQuery.equals("")) {
            filterQuery = filterQuery.toLowerCase();
            List<Contact> filteredResults = new ArrayList<>();

            for (Contact contact : contactList) {
                if (Integer.toString(contact.getId()).equals(filterQuery)) {
                    filteredResults.add(contact);
                } else if (contact.getPhone().equals(filterQuery)) {
                    filteredResults.add(contact);
                } else if (contact.getFirstName().toLowerCase().equals(filterQuery)) {
                    filteredResults.add(contact);
                } else if (contact.getLastName().toLowerCase().equals(filterQuery)) {
                    filteredResults.add(contact);
                }
            }
            return filteredResults;
        } else {
            return contactList;
        }
    }

    public Contact add(Contact contact) {
        contact.setId(getNewId());
        contactList.add(contact);
        return contact;
    }

    public boolean remove(int id) {
        boolean result = false;
        Iterator<Contact> contactIterator = contactList.iterator();
        while (contactIterator.hasNext()) {
            Contact contact = contactIterator.next();
            if (contact.getId() == id) {
                contactIterator.remove();
                result = true;
            }
        }
        return result;
    }
}
