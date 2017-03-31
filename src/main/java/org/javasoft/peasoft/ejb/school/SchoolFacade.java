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
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.brainChallenge.School;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class SchoolFacade extends GenericDAO<School, Long>{

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
}
