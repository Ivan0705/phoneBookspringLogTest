package ru.academits;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PhoneBookSpringApplicationTest {

    @Autowired
    private ContactService contactService;

    @Test
    public void addContact() {
        List<Contact> contactList = contactService.getAllContacts();

        assertEquals(contactList.size(), 0);

        Contact contact = new Contact();
        contact.setFirstName("First name");
        contact.setLastName("Last name");
        contact.setPhone("0 000 000 000 000");

        contactService.addContact(contact);

        contactList = contactService.getAllContacts();

        assertEquals(contactList.size(), 1);
    }

    @Test
    public void contactValidation() {
        Contact contact = new Contact();
        assertFalse(contactService.addContact(contact).isValid());

        contact.setFirstName("First name");
        assertFalse(contactService.addContact(contact).isValid());

        contact.setLastName("Last name");
        assertFalse(contactService.addContact(contact).isValid());

        contact.setPhone("0 000 000 000");
        assertTrue(contactService.addContact(contact).isValid());

        Contact contact2 = new Contact();
        contact2.setFirstName("First name");
        contact2.setLastName("Last name");
        contact2.setPhone("0 000 000 000");
        assertFalse(contactService.addContact(contact2).isValid());

        contact2.setPhone("7 000 000 000");
        assertTrue(contactService.addContact(contact2).isValid());
    }

    @Test
    public void deleteContact() {
        List<Contact> contactList = contactService.getAllContacts();

        assertEquals(contactList.size(), 0);

        Contact contact = new Contact();
        contact.setFirstName("First name");
        contact.setLastName("Last name");
        contact.setPhone("0 000 000 000 000");

        ContactValidation contactValidation = contactService.addContact(contact);

        contactService.deleteContact(contactValidation.getId());

        contactList = contactService.getAllContacts();

        assertEquals(contactList.size(), 0);
    }
}
