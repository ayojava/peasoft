/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.school;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.javasoft.peasoft.batch.dto.EmailBatchDTO;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.ejb.settings.BatchSettingsFacade;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import org.javasoft.peasoft.excel.school.SchoolAndStudentRecordsListExcelReport;
import org.javasoft.peasoft.excel.school.SchoolAndStudentRecordsResultListExcelReport;
import org.javasoft.peasoft.excel.school.SchoolListExcelReport;
import org.javasoft.peasoft.utils.GlobalRegistry;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class SchoolFacade extends GenericDAO<School, Long>{
    
    private GlobalRegistry globalRegistry;
    
    @EJB
    private BatchSettingsFacade batchSettingsFacade;

    public SchoolFacade(){
        super(School.class);
    }
    
   public List<School> fetchJoinSchools(){
       return getCriteria().setFetchMode("studentRecords", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
   }
   
   public School fetchJoinSchoolRecord(School school){
       return (School) getCriteria().setFetchMode("studentRecords", FetchMode.JOIN)
               .add(Restrictions.eq("id", school.getId())).uniqueResult();
   }
   
   public void saveSchool(School schoolObj){
       globalRegistry = GlobalRegistry.getInstance();
       //log.info("Before School Update -> [ {} ]" , globalRegistry.getSchoolCount());
       globalRegistry.updateSchoolCount();
       //log.info("Ater School Update -> [ {} ]" , globalRegistry.getSchoolCount());
       String identificationNo = "SC"+ StringUtils.leftPad(String.valueOf(globalRegistry.getSchoolCount()), 5, "0");
       schoolObj.setIdentificationNo(identificationNo);
       persist(schoolObj);
       
   }
   
   public void asyncSchoolListExcelDocument(String fileName){
        SchoolListExcelReport schoolListExcelReport = new SchoolListExcelReport();
        schoolListExcelReport.populateExcelSheet("schoolList", fetchJoinSchools(),fileName);
        schoolListExcelReport.destroy();
    }
    
    public void asyncSchoolAndStudentsRecordsExcelDocument(String fileName, School schoolObj,List<StudentRecord> studentRecord){
        SchoolAndStudentRecordsResultListExcelReport report = new SchoolAndStudentRecordsResultListExcelReport();
        report.populateExcelSheet("SchoolRecordsList", fileName, schoolObj,studentRecord);
        report.destroy();
    }
    
    public void generateStudentsRecordsExcelDocumentBySchool(String fileName, School schoolObj,List<StudentRecord> studentRecord){
        BatchSettings batchSettings = batchSettingsFacade.findOne();
        SchoolAndStudentRecordsListExcelReport report = new SchoolAndStudentRecordsListExcelReport();
        report.populateExcelSheet("StudentsRecordsList", fileName, schoolObj,studentRecord,batchSettings);
        report.destroy();
    }
    
    public boolean schoolAlreadyExists(School schoolObj){
        List<School> allSchools = findAll();
        return allSchools.stream().anyMatch(s-> StringUtils.equalsIgnoreCase(s.getName(), schoolObj.getName()));
    }
    
    public List<EmailBatchDTO> findEmailBatchDTO(){
        Criteria criteria = getCriteria();
        //criteria.createAlias("addressTemplate", "pa");
        ProjectionList projectionsList =  Projections.projectionList();
        projectionsList.add(Projections.property("id"),"recipientID");
        projectionsList.add(Projections.property("addressTemplate.contactEmail1"),"email1");
        projectionsList.add(Projections.property("addressTemplate.contactEmail1"),"email2");
        criteria.setProjection(projectionsList).setResultTransformer(Transformers.aliasToBean(EmailBatchDTO.class));
        return criteria.list();
    }
}
