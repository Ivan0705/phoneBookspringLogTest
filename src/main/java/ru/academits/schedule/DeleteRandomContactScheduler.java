package ru.academits.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.dao.ContactDao;

@Component
public class DeleteRandomContactScheduler {

    private final static Logger logger =
            LoggerFactory.getLogger(DeleteRandomContactScheduler.class.getName());

    @Autowired
    private ContactDao contactDao;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void sendEmailWithContactList() {
        Integer randomId = contactDao.getRandomId();

        if (randomId != null) {
            contactDao.remove(randomId);
            logger.info("Deleted contact with id {}", randomId);
        }
    }
}
