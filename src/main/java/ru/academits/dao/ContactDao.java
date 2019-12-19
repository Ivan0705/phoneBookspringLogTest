package ru.academits.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ContactDao {
    private static final Logger logger = LoggerFactory.getLogger(ContactDao.class);
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
        logger.info("Get list of contact");
    }

    public Integer getRandomId() {
        if (contactList.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(contactList.size());
        logger.info("Get random id");
        return contactList.get(randomIndex).getId();
    }

    private int getNewId() {
        logger.info("Get new id");
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAll() {
        logger.info("Get list of contacts");
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
            logger.info("Get list of contact by query");
            return filteredResults;
        } else {
            logger.info("List of contacts");
            return contactList;
        }
    }

    public Contact add(Contact contact) {
        contact.setId(getNewId());
        contactList.add(contact);
        logger.info("Added contact");
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
            logger.info("Removed contact with id");
        }
        logger.info("Don't removed contact with id");
        return result;
    }
}
