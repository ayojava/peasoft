/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
@Slf4j
@WebListener
public class ServletLifecycleEvents implements ServletContextListener{
    
    private GlobalRegistry globalRegistry;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        log.info("Context Path ::: {} ",realPath);
        int position = StringUtils.indexOf(realPath, "tmp");
        globalRegistry = GlobalRegistry.getInstance();
        globalRegistry.setInitFilePath(StringUtils.substring(realPath, 0,position));
                
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
    }
    
   
            
            
            
    
}
