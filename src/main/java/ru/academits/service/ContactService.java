package ru.academits.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.schedule.DeleteRandomContactScheduler;

import java.util.List;

@Service
public class ContactService {

    private final static Logger logger =
            LoggerFactory.getLogger(ContactService.class.getName());

    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts(null);
        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (StringUtils.isEmpty(contact.getFirstName())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Имя должно быть заполнено.");
            return contactValidation;
        }

        if (StringUtils.isEmpty(contact.getLastName())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
            return contactValidation;
        }

        if (StringUtils.isEmpty(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Телефон должно быть заполнено.");
            return contactValidation;
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
            return contactValidation;
        }
        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);
        if (contactValidation.isValid()) {
            contact = contactDao.add(contact);
            contactValidation.setId(contact.getId());

            logger.info("Added contact with id {}", contact.getId());
        }
        return contactValidation;
    }

    public void deleteContact(int id) {
        if (contactDao.remove(id)) {
            logger.info("Removed contact with id {}", id);
        }
    }

    public List<Contact> getAllContacts() {
        logger.info("Get list of contact");
        return contactDao.getAll();
    }

    public List<Contact> getAllContacts(String filterQuery) {
        logger.info("Get list of contact by query {}", filterQuery);
        return contactDao.getAllContacts(filterQuery);
    }
}
