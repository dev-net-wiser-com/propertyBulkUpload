package com.hmms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.hmms.service.ProBulkProcessBean;
import com.hmms.entity.Result;
import com.hmms.entity.ResultBuilder;
import org.springframework.web.bind.annotation.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {

    //private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ProBulkProcessBean proBulkProcessBean;


    @Autowired
    private HttpServletResponse response;


    //----------------------------------- Property bulk creation ----------------------------------------------

    @RequestMapping(value = { "/getEstateList" })
    public ResponseEntity<Result> getEstateList(@RequestParam("user") String user) {
        Result result;
        try {
            result = proBulkProcessBean.getEstateList(user); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(result);
    }


    @RequestMapping(value = { "/getBlockList" })
    public ResponseEntity<Result> getBlockList(@RequestParam("user") String user, @RequestParam("estate") String estate) {
        Result result;
        try {
            result = proBulkProcessBean.getBlockList(user,estate); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = { "/getElementList" })
    public ResponseEntity<Result> getElementList(@RequestParam("user") String user) {
        Result result;
        try {
            result = proBulkProcessBean.getElementCodeList(user);   
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(result);
    }


    // @RequestMapping(value = { "/downloadTemplate" })
    // public ResponseEntity<Result> downloadTemplate(@RequestParam("user") String user, @RequestParam("estate") String estate,
    //                                 @RequestParam("block") String block, @RequestParam("elementCode") String elementCode,
    //                                 @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
    //     Result result;
    //     try {
    //         result = proBulkProcessBean.downloadTemplate(estate, block, elementCode, startDate, endDate, user);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.internalServerError().build();
    //     }
    //     return ResponseEntity.ok().body(result);
    // }
    @RequestMapping(value = { "/downloadTemplate" })
    public ResponseEntity<Result> downloadTemplate(@RequestParam("user") String user) {
        Result result;
        try {
            result = proBulkProcessBean.downloadTemplate(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(result);
    }



    //--20220714
    @PostMapping("uploadFileModel")
    public ResponseEntity<Result> uploadFileModel(@RequestParam("files") MultipartFile file, @RequestParam("user") String user) {
        Result result = null;
        try {
            result = proBulkProcessBean.selectAndUpload(file,user);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultBuilder.buildServerFailResult(e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok().body(result); 
    }
    
    @RequestMapping("validationModel")
    public ResponseEntity<Result> validationModel(@RequestParam("atdID") String atdID, @RequestParam("user") String user) {
        Result result = null;
        try {
            result = proBulkProcessBean.validate(atdID,user);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultBuilder.buildServerFailResult(e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok().body(result); 
    }

    @RequestMapping("uploadDataModel")
    public ResponseEntity<Result> uploadDataModel(@RequestParam("atdID") String atdID, @RequestParam("user") String user) {
        Result result = null;
        try {
            result = proBulkProcessBean.uploadData(atdID,user);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultBuilder.buildServerFailResult(e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok().body(result); 
    }







   


 
    







    
}
