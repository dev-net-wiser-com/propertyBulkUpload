package com.hmms.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

import com.hmms.service.ProBulkProcessBean;

@Configuration
@ComponentScan("com.hmms")
@PropertySource({"classpath:upload.properties"})
public class ProcessBeanConfig implements ApplicationContextAware{
    @Value("${upload.local.path}")
    private String uploadLocalPath;

    @Value("${upload.server.path}")
    private String uploadServerPath;
    
    @Value("${download.server.path}")
    private String downloadServerPath;
    
 
    private ApplicationContext applicationContext;


//-------------------------------
@Bean
public ProBulkProcessBean proBulkProcessBean(@Qualifier("dataSource")DataSource dataSource){
    ProBulkProcessBean proBulkProcessBean = new ProBulkProcessBean(dataSource);
    boolean isPrd = false;
    String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
    for(int i=0;i<activeProfiles.length;i++){
        if(StringUtils.equalsAnyIgnoreCase("prd", activeProfiles[i])){
            isPrd = true;
            break;
        }
    }
    if(isPrd){
        proBulkProcessBean.setUploadFolder(uploadServerPath);
        proBulkProcessBean.setDownloadFolder(downloadServerPath);
    }else{
        proBulkProcessBean.setUploadFolder(uploadLocalPath); //in local upload/download use same path
        proBulkProcessBean.setDownloadFolder(uploadLocalPath);
    }
    return proBulkProcessBean;
}



//-------------------------------


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    
}
