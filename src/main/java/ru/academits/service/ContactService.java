package ru.academits.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;

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
                logger.info("The telephone numbers are identical{}", contact.getPhone());
                return true;
            }
        }
        logger.info("The telephone numbers are different");
        return false;
    }

    private ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (StringUtils.isEmpty(contact.getFirstName())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Имя должно быть заполнено.");
            logger.info("Validated contact with id{}", contact.getFirstName());
            return contactValidation;
        }

        if (StringUtils.isEmpty(contact.getLastName())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
            logger.info("Validated contact with id{}", contact.getLastName());
            return contactValidation;
        }

        if (StringUtils.isEmpty(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Телефон должно быть заполнено.");
            logger.info("Validated contact with id{}", contact.getPhone());
            return contactValidation;
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
            logger.info("Checked contact with id{}", contact.getPhone());
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
        logger.info("Get list of contacts");
        return contactDao.getAll();
    }

    public List<Contact> getAllContacts(String filterQuery) {
        logger.info("Get list of contact by query {}", filterQuery);
        return contactDao.getAllContacts(filterQuery);
    }
}
