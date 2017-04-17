/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.school;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.excel.school.SchoolAndStudentRecordsListExcelReport;
import org.javasoft.peasoft.excel.school.SchoolListExcelReport;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
@Asynchronous
public class AsyncSchoolFacade extends GenericDAO<School, Long>{
    
    @EJB
    private SchoolFacade schoolFacade;
    
    public AsyncSchoolFacade(){
        super(School.class);
    }
    
    public void asyncSchoolListExcelDocument(String fileName){
        SchoolListExcelReport schoolListExcelReport = new SchoolListExcelReport();
        schoolListExcelReport.populateExcelSheet("schoolList", schoolFacade.fetchJoinSchools(),fileName);
        schoolListExcelReport.destroy();
    }
    
    public void asyncSchoolAndStudentsRecordsExcelDocument(String fileName, School schoolObj){
        SchoolAndStudentRecordsListExcelReport report = new SchoolAndStudentRecordsListExcelReport();
        report.populateExcelSheet("SchoolRecordsList", fileName, schoolObj);
        report.destroy();
    }
}
