/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.school;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.brainChallenge.School;
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

    public SchoolFacade(){
        super(School.class);
    }
    
   public List<School> fetchJoinSchools(){
       return getCriteria().setFetchMode("studentRecords", FetchMode.JOIN).list();
   }
   
   public School fetchJoinSchoolRecord(School school){
       return (School) getCriteria().setFetchMode("studentRecords", FetchMode.JOIN)
               .add(Restrictions.eq("id", school.getId())).uniqueResult();
   }
   
   public void saveSchool(School schoolObj){
       globalRegistry = GlobalRegistry.getInstance();
       String identificationNo = "SC"+ StringUtils.leftPad(String.valueOf(globalRegistry.getSchoolCount()), 5, "0");
       schoolObj.setIdentificationNo(identificationNo);
       persist(schoolObj);
       globalRegistry.updateSchoolCount();
   }
}
