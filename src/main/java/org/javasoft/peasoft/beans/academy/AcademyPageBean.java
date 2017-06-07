/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.academy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.VIEW_HOME_PAGE;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.NotificationFacade;
import org.javasoft.peasoft.ejb.settings.BatchSettingsFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.Notification;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import org.javasoft.peasoft.service.AcademyService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("academyPageBean")
@ViewScoped
public class AcademyPageBean extends AbstractBean implements Serializable {

    @Getter
    private List<StudentRecord> studentRecords;

    @EJB
    private StudentRecordFacade studentRecordFacade;
    
    @EJB
    private BatchSettingsFacade batchSettingsFacade;

    @EJB
    private NotificationFacade notificationFacade;
    
    @EJB
    private EmailDataFacade emailDataFacade;
    
    @Inject
    private EmailUtilBean emailUtilBean;
    
    private BatchSettings batchSetting;

    private String status;
    
    private AcademyService academyService;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        academyService = new AcademyService();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_ACADEMY_RECORDS, pageResource)) {
            List<StudentRecord> orderedMarks = studentRecordFacade.orderByMarks();
            log.info("Total Student Record List ==== {}", orderedMarks.size());
            status = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("status");
            if (StringUtils.equalsIgnoreCase(status, SELECTED)) {
                studentRecords = orderedMarks.stream().filter(StudentRecord::isActive).limit(500).collect(toList());
            } else {
                studentRecords = orderedMarks.stream().filter(StudentRecord::isActive).skip(500).collect(toList());
            }
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void scheduleNotification() {
        try {
            List<Notification> notificationList = notificationFacade.getPendingAcademyNotificationOrderByMarksAndStatus();
            log.info("Total Notification List ==== {}", notificationList.size());
            batchSetting = batchSettingsFacade.findOne();
            if (StringUtils.equalsIgnoreCase(status, SELECTED)) {
                pendingSelectedNotifications(notificationList);
            } else {
                pendingNotSelectedNotifications(notificationList);
            }
            Messages.addGlobalInfo("Notifications Scheduled Successfully");
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    private void pendingNotSelectedNotifications(List<Notification> notificationList) {
        List<Notification> notSelectedNotificationList = notificationList.stream().skip(500).collect(toList());

        List<Notification> pendingNotSelectedNotification = notSelectedNotificationList.stream().filter(n -> !n.isAcademyNotification()).collect(toList());
        log.info("Pending NotSelected  Notification List ==== {}", pendingNotSelectedNotification.size());
        int cntSize = (pendingNotSelectedNotification.size() > BATCH_SIZE) ? BATCH_SIZE : pendingNotSelectedNotification.size();
        for (int i = 0; i <= cntSize; i++) {
            Notification aNotification = pendingNotSelectedNotification.get(i);
            StudentRecord aRecord = aNotification.getStudentRecord();
            EmailData emailData =academyService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting, false);
            emailDataFacade.persist(emailData);
            
            Student studentObj = aRecord.getStudent();
            HashSet<String> phoneNos = new HashSet<>();
            phoneNos.add(studentObj.getPhoneNo());
            phoneNos.add(studentObj.getOtherPhoneNo());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());
            
            

            aNotification.setBatchNotification(true);
            notificationFacade.edit(aNotification);

        }
    }
    
    private void pendingSelectedNotifications(List<Notification> notificationList) {
        List<Notification> selectedNotificationList = notificationList.stream().limit(500).collect(toList());

        List<Notification> pendingSelectedNotification = selectedNotificationList.stream().filter(n -> !n.isAcademyNotification()).collect(toList());
        log.info("Pending Selected  Notification List ==== {}", pendingSelectedNotification.size());
        int cntSize = (pendingSelectedNotification.size() > BATCH_SIZE) ? BATCH_SIZE : pendingSelectedNotification.size();
        for (int i = 0; i <= cntSize; i++) {
            Notification aNotification = pendingSelectedNotification.get(i);
            StudentRecord aRecord = aNotification.getStudentRecord();

            Student studentObj = aRecord.getStudent();

            HashSet<String> phoneNos = new HashSet<>();
            phoneNos.add(studentObj.getPhoneNo());
            phoneNos.add(studentObj.getOtherPhoneNo());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());

            aNotification.setBatchNotification(true);
            notificationFacade.edit(aNotification);

        }
    }

    private void cleanup() {

    }
}
