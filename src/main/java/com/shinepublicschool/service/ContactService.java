package com.shinepublicschool.service;

import com.shinepublicschool.Constants.ShinePublicSchoolConstants;
import com.shinepublicschool.model.Contact;
import com.shinepublicschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

//    private static Logger log= LoggerFactory.getLogger(ContactController.class);
//above statement can be replaced by @Slf4j annotation, it will provide the log object

    /*
    save contact details into DB
    @param contact
    @return boolean
     */

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(ShinePublicSchoolConstants.OPEN);
        contact.setCreatedBy(ShinePublicSchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int result = contactRepository.saveContactDetails(contact);
        if (result > 0) {
            isSaved = true;
        }
        return isSaved;
    }


    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findMsgsWithStatus(ShinePublicSchoolConstants.OPEN);
    }

    public boolean updateMsgStatus(int id, String updatedBy) {
        boolean isUpdated = false;
        int result = contactRepository.updateMsgStatus(id, ShinePublicSchoolConstants.CLOSE, updatedBy);
        if (result > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
