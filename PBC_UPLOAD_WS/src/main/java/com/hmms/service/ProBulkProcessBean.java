package com.hmms.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
// import java.io.FileInpupStr;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.hmms.entity.Result;
import com.hmms.entity.ResultBuilder;
import com.hmms.util.ConnectionUtil;
import com.hmms.util.DataUtil;
import com.hmms.util.DateParser;
import com.hmms.util.Log;
import com.hmms.util.UnicodeReader;
import com.hmms.vo.MasterCodeVO;
import com.hmms.vo.PropertyBulkListVO;

// import org.apache.commons.csvvCSVParsVrr;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

// import net.sf.json.JSON;
// import net.sf.json.JSONArray;
// import net.sf.json.JSONObject;
// import net.sf.json.JsonConfig;
import oracle.jdbc.OracleTypes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class ProBulkProcessBean {

    public static final String SCREEN_ID = "txUpload";
    public static final String REF_ID = "txUpload";

    private String uploadFolder;
    private String downloadFolder;

    public ProBulkProcessBean(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private DataSource dataSource;

    private Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet rs = null;

    // private static final Logger logger =
    private final String dateErrMssage = "New property bulk start date must larger than old property bulk start date";

    private final static SimpleDateFormat RESTRICT_DATA_FORMATTER = new SimpleDateFormat("dd/mm/yyyy");

    private final String SQL_GET_ESTATE_LIST = "SELECT AUN_CODE, AUN_NAME  FROM  fsc.ADMIN_UNITS WHERE   AUN_AUY_CODE = 'OFF' ORDER BY 1";

    private final String SQL_GET_BLOCK_LIST = "" + /** ~ { */
            ""
            + "SELECT  distinct AUN_CODE , AUN_NAME  "
            + "\r\n	   FROM admin_properties, admin_groupings,admin_units"
            + "\r\n		  WHERE agr_aun_code_parent=?  AND"
            + "\r\n		  agr_aun_code_child = apr_aun_code"
            + "\r\n		  AND agr_auy_code_parent = 'OFF'"
            + "\r\n		  AND   apr_aun_code = aun_code"
            + "\r\n		  order by 1"
            + "\r\n"/** } */
    ;

    private final String SQL_GET_PROPERTY_ELEMENT_LIST = "" + /** ~ { */
            ""
            + "select ELE_CODE, ELE_DESCRIPTION,ELE_VALUE_TYPE"
            + "\r\n    from elements"
            + "\r\n    where  ELE_CODE NOT IN (SELECT RER_ELE_CODE FROM RENT_ELEMRATES) and ELE_CURRENT_IND='Y'"
            + "\r\n    order by ele_code "
            + "\r\n "/** } */
    ;

    private final String SQL_GET_CHARGE_ELEMENT_LIST = "" + /** ~ { */
            ""
            + "SELECT DISTINCT RER_ELE_CODE, ELE_DESCRIPTION FROM rent_elemrates, PROPERTY_ELEMENTS, ELEMENTS"
            + "\r\n		 WHERE rer_status = 'A' AND RER_ELE_CODE=PEL_ELE_CODE AND PEL_ELE_CODE = ELE_CODE"
            + "\r\n		 ORDER BY RER_ELE_CODE"
            + "\r\n"/** } */
    ;

    // private final String SQL_GET_ELEMENT_CODE_LIST = "select  ELE_CODE, ELE_DESCRIPTION, ele_kind"
    //         + "  from ("
    //         + " select  ELE_CODE, ELE_DESCRIPTION,'CL' as ele_kind from elements"
    //         + " where  ELE_CODE IN (SELECT RER_ELE_CODE FROM RENT_ELEMRATES)  AND ELE_CURRENT_IND = 'Y'"
    //         + " union all"
    //         + " select  ELE_CODE, ELE_DESCRIPTION,'PR' as ele_kind from elements"
    //         + " where  ELE_TYPE = 'PR' AND ELE_CURRENT_IND = 'Y'  AND"
    //         + " ELE_CODE IN ('UNDIVSHARE', 'MANSHARE', 'CAPFUND', 'CONFUND', 'MFD', 'SPFUND', 'SHOPNO', 'RATESNO', 'AREATYPE',"
    //         + "                 'ESTATE', 'FLATTYPE', 'FLATSUBTPE', 'ELDERLY', 'GRND', 'TOP', 'BALCONY', 'FLOOR', 'LIFT', 'LIFTLAND', 'FACING',"
    //         + "                 'IFA_RPT', 'IFA', 'SFA', 'LFA', 'GFA', 'MINPX', 'MINALLOC', 'MAXALLOC', 'GROUP' ,'UNITTYPE', 'FLAT_NO')"
    //         + " ) order by ele_code ";
    // 20231110
    private final String SQL_GET_ELEMENT_CODE_LIST = "SELECT ELE_CODE, ELE_CODE ||' - '||ELE_DESCRIPTION, ELE_TYPE  FROM ELEMENTS WHERE ELE_TYPE = 'PR' AND ELE_CURRENT_IND = 'Y' ORDER BY 1 ";

    private final String SQL_INSERT_PROPERTY_ELEMENT = "" + /** ~ { */
            ""
            + "INSERT INTO PROPERTY_ELEMENTS ( "
            + "\r\n  PEL_ELE_CODE        ,"
            + "\r\n  PEL_START_DATE      ,"
            + "\r\n  PEL_ATT_CODE        ,"
            + "\r\n  PEL_PRO_REFNO       ,"
            + "\r\n  PEL_HRV_ELO_CODE    ,"
            + "\r\n  PEL_FAT_CODE        ,"
            + "\r\n  PEL_CREATED_BY      ,"
            + "\r\n  PEL_CREATED_DATE    , "
            + "\r\n  PEL_MODIFIED_BY     ,"
            + "\r\n  PEL_MODIFIED_DATE   ,"
            + "\r\n  PEL_HRV_RCO_CODE    ,"
            + "\r\n  PEL_HRV_RCA_CODE    ,"
            + "\r\n  PEL_DATE_VALUE      ,"
            + "\r\n  PEL_NUMERIC_VALUE   ,"
            + "\r\n  PEL_QUANTITY        ,"
            + "\r\n  PEL_END_DATE        ,"
            + "\r\n  PEL_SOURCE          ,"
            + "\r\n  PEL_SOURCE_REFNO    ,"
            + "\r\n  PEL_COMMENT         ,"
            + "\r\n  PEL_CHARGEABLE_IND  ,"
            + "\r\n  PEL_SCS_REFNO       ,"
            + "\r\n  PEL_CNT_REFERENCE   ,"
            + "\r\n  PEL_TYPE_IND        ,"
            + "\r\n  PEL_JHE_REFNO ,"
            + "\r\n  PEL_REFNO)"
            + "\r\n  VALUES ( ?, trunc(SYSDATE), ?,?,'PRO', 'NUL',?,SYSDATE, NULL,NULL,NULL,NULL,?,?,NULL,?,NULL,NULL,NULL,'Y',NULL, NULL, NULL,  NULL, PEL_REFNO_SEQ.NEXTVAL)"
            + "\r\n"/** } */
    ;

    private final String SQL_INSERT_ATTACHMENT_DETAIL_bak = "" + /** ~ { */
            ""
            + "INSERT INTO HST_HMMS_ATTACHMENT_DETAIL ( "
            + "\r\n  ATD_ID        ,"
            + "\r\n  ATD_ATC_ID      ,"
            + "\r\n  ATD_REF_CODE        ,"
            + "\r\n  ATD_FILE_DISPLAY_NAME       ,"
            + "\r\n  ATD_FILE_SYS_NAME    ,"
            + "\r\n  ATD_FILE_DOWNLOAD_LINK        ,"
            + "\r\n  ATD_REMARK      ,"
            + "\r\n  ATD_IS_DELETED_BY_USER    , "
            + "\r\n  ATD_DELETED_BY_USER     ,"
            + "\r\n  ATD_DELETED_BY_DATE   ,"
            + "\r\n  ATD_DELETED_BY_SYS_DATE    ,"
            + "\r\n  ATD_CREATED_BY    ,"
            + "\r\n  ATD_CREATED_DATE      ,"
            + "\r\n  ATD_MODIFIED_BY   ,"
            + "\r\n  ATD_MODIFIED_DATE)"
            + "\r\n  VALUES ( ATD_SEQ.NEXTVAL, trunc(SYSDATE), ?,?,'PRO', 'NUL',?,SYSDATE, NULL,NULL,NULL,NULL,NULL,NULL,NULL)"
            + "\r\n"/** } */
    ;

    private final String SQL_INSERT_ATTACHMENT_DETAIL = "" + /** ~ { */
        ""
        + "INSERT INTO HST_HMMS_ATTACHMENT_DETAIL ( "
        + "\r\n  ATD_ID        ,"
        + "\r\n  ATD_ATC_ID      ,"
        + "\r\n  ATD_REF_CODE        ,"
        + "\r\n  ATD_FILE_DISPLAY_NAME       ,"
        + "\r\n  ATD_FILE_SYS_NAME    ,"
        + "\r\n  ATD_FILE_DOWNLOAD_LINK        ,"
        + "\r\n  ATD_REMARK      ,"
        + "\r\n  ATD_IS_DELETED_BY_USER    , "
        + "\r\n  ATD_DELETED_BY_USER     ,"
        + "\r\n  ATD_DELETED_BY_DATE   ,"
        + "\r\n  ATD_DELETED_BY_SYS_DATE    ,"
        + "\r\n  ATD_CREATED_BY    ,"
        + "\r\n  ATD_CREATED_DATE      ,"
        + "\r\n  ATD_MODIFIED_BY   ,"
        + "\r\n  ATD_MODIFIED_DATE)"
        + "\r\n  VALUES ( ?, ?,?,?,?,?,NULL,?, NULL,NULL,NULL,?,SYSDATE,NULL,NULL)"
        + "\r\n"/** } */
    ;


    private final String SQL_INSERT_PE_UPLOAD_DETAIL = "" + /** ~ { */
        ""
        + "INSERT INTO HST_PE_UPLOAD_DETAIL ( "
        + "\r\n  PUD_ID        ,"
        + "\r\n  PUD_ATD_ID      ,"
        + "\r\n  pud_line_no        ,"
        + "\r\n  pud_estate       ,"
        + "\r\n  pud_block    ,"
        + "\r\n  pud_ele_code        ,"
        + "\r\n  pud_start_date      ,"
        + "\r\n  pud_end_date    , "
        + "\r\n  pud_value     ,"
        + "\r\n  pud_status   ,"
        + "\r\n  pud_message)"
        // + "\r\n  VALUES ( seq_pe_upload_id.NEXTVAL, HS_HMMS.ATD_SEQ.CURRVAL,?,?,?,?,?,?,?,?,?)"
        + "\r\n  VALUES ( seq_pe_upload_id.NEXTVAL,?,?,?,?,?,?,?,?,?,?)"
        + "\r\n"/** } */
    ;

    // 20230912 zgl
    private final String SQL_GET_MANAGE_FEE_LIST1 = "" + /** ~ { */
            ""
            + "SELECT PEL_NUMERIC_VALUE from PROPERTY_ELEMENTS "
            + "\r\n		 WHERE pel_ele_code = ? and pel_start_date = to_date(?, 'dd/MM/yyyy') and pel_end_date = to_date(?, 'dd/MM/yyyy') "
            + "\r\n		 ORDER BY PEL_PRO_REFNO"
            + "\r\n"/** } */
    ;
    private final String SQL_GET_MANAGE_FEE_LIST = "" + /** ~ { */
            ""
            + "SELECT PEL_NUMERIC_VALUE from PROPERTY_ELEMENTS "
            + "\r\n		 WHERE pel_ele_code = ? and pel_start_date = to_date(?, 'dd/MM/yyyy') "
            // + "\r\n		 ORDER BY PEL_PRO_REFNO"
            + "\r\n"/** } */
    ;

    private final String SQL_INSERT_PE_DBR_RATE_MAP = "" + /** ~ { */
        ""
        + "INSERT INTO HST_BULK_UPLOAD_PEL_MFEE ( "
        + "\r\n  PUD_ID        ,"
        + "\r\n  pud_estate       ,"
        + "\r\n  pud_message)"
        // + "\r\n  VALUES ( SEQ_PUDMF_GROUP_NO.NEXTVAL,?,?)"
        + "\r\n  VALUES ( ?,?,?)"
        + "\r\n"/** } */
    ;

    private final String SQL_GET_MAX_STARTDATE_BY_ELECODE = "select max(PEL_Start_date) from property_elements where pel_ele_code =?";


    // --------20211227-------------
    public Result getEstateList(String user) throws SQLException {
        String sql = this.SQL_GET_ESTATE_LIST;
        List<MasterCodeVO> mcList = new ArrayList<MasterCodeVO>();
        System.out.println(sql);
        try {
            conn = dataSource.getConnection();
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs != null && rs.next()) {
                MasterCodeVO mc = new MasterCodeVO();
                mc.setCode(rs.getString(1));
                mc.setDescription(DataUtil.nvl(rs.getString(2), ""));
                mcList.add(mc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
        }
        return ResultBuilder.buildSuccessResult(mcList, mcList.size());
    }

    public Result getBlockList(String user, String estate) throws SQLException {
        List<MasterCodeVO> mcList = new ArrayList<MasterCodeVO>();
        if (StringUtils.isEmpty(estate))
            return ResultBuilder.buildSuccessResult(mcList, mcList.size());

        String sql = this.SQL_GET_BLOCK_LIST;
        System.out.println(sql);
        try {
            conn = dataSource.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, estate);
            rs = psmt.executeQuery();
            while (rs != null && rs.next()) {
                MasterCodeVO mc = new MasterCodeVO();
                mc.setCode(rs.getString(1));
                mc.setDescription(DataUtil.nvl(rs.getString(2), ""));

                mcList.add(mc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
        }
        return ResultBuilder.buildSuccessResult(mcList, mcList.size());
    }

    public Result getElementCodeList(String user) throws SQLException {
        String sql = this.SQL_GET_ELEMENT_CODE_LIST;
        List<MasterCodeVO> mcList = new ArrayList<MasterCodeVO>();
        System.out.println(sql);
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs != null && rs.next()) {
                MasterCodeVO mc = new MasterCodeVO();
                mc.setCode(rs.getString(1));
                mc.setDescription(DataUtil.nvl(rs.getString(2), ""));
                mc.setValueType(rs.getString(3));
                mcList.add(mc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
        }
        return ResultBuilder.buildSuccessResult(mcList, mcList.size());
    }

    public Result getChargeElementList(String user) throws SQLException {
        String sql = this.SQL_GET_CHARGE_ELEMENT_LIST;
        List<MasterCodeVO> mcList = new ArrayList<MasterCodeVO>();
        System.out.println(sql);
        try {
            conn = dataSource.getConnection();
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs != null && rs.next()) {
                MasterCodeVO mc = new MasterCodeVO();
                mc.setCode(rs.getString(1));
                mc.setDescription(DataUtil.nvl(rs.getString(2), ""));
                mc.setValueType("N");
                mcList.add(mc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
        }
        return ResultBuilder.buildSuccessResult(mcList, mcList.size());
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }


    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }


    public Result downloadTemplate(String user) throws IOException, SQLException {

        // String sql = "select * from table(hsk_pe_upload.gen_template(?,?,?,?,?))";
        String sql = "select * from table(hsk_prop_main.gen_probulk_template)";
        try {
            // 1 Get download path from upload.properties
            // String path="/u01/spool/HOUDEV/pdf_output/"; //VM path
            // Configuration Config = new Configuration("/upload.properties");
            // path = Config.getValue("report.path");

            // 2 Set server storage path
            // String savePath = ResourceUtils.getURL("D:/zgl").getPath();
            String savePath = downloadFolder;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(time);

            String filename = "PBC_" + date + ".CSV";
            String localFileName = file + "/" + filename;

            File newFile = new File(localFileName);
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));

            conn = dataSource.getConnection();
            psmt = conn.prepareStatement(sql);
            // psmt.setString(1, estate);
            // psmt.setString(2, block);
            // psmt.setString(3, eleCode);
            // psmt.setString(4, startDate);
            // psmt.setString(5, endDate);
            rs = psmt.executeQuery();
            while (rs != null && rs.next()) {
                out.write(rs.getString(1) + "\r\n");
                out.flush();
            }
            out.close();
            return ResultBuilder.buildSuccessResult(filename); // vm server
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
        }
    }

    //20220713
    public Result selectAndUpload(MultipartFile file, String user) throws Exception {
        
        String encoding = "UTF-8";
        String TYPE_CSV = "CSV";

        BufferedReader bufferedReader = null;
        InputStreamReader read = null;
        // --
        String orgUploadFilePath = "";
        String orgFileName = "";
        String sysFileName = "";
        String upload_result = "OK";
        int  atdID = 0;
        String message = "";
        int failLineNo = 0;
        String new_upload_result ="OK";
        // String[] currRow = null;
        String currRow = null;  //20240123

        if (StringUtils.isEmpty(file.getName())) {
            return ResultBuilder.buildServerFailResult("File not found");
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!(TYPE_CSV.equals(suffix.toUpperCase()) || TYPE_CSV.equals(suffix.toLowerCase())
        // || TYPE_EXCEL.equals(suffix.toUpperCase()) ||
        // TYPE_EXCEL.equals(suffix.toLowerCase())
        )) {
            return ResultBuilder.buildServerFailResult("File type error,Please uplaod .csv file");
        }
        if (file.isEmpty()) {
            return ResultBuilder.buildServerFailResult("Empty File");
        }
        
            // 1. copy upload file to upload Folder
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
            orgUploadFilePath = uploadFolder;
            orgFileName = file.getOriginalFilename();
            int dot = orgFileName.lastIndexOf(".");
            sysFileName = orgFileName.substring(0, dot) + "_" + date + ".CSV";
            // save a copy to uploadPath+"//"+sysFileName;
            File sysFile = new File(new File(orgUploadFilePath).getAbsolutePath() + "/" + sysFileName);
            // File sysFile = new File(orgUploadFilePath + "/" + sysFileName);
            if (!sysFile.getParentFile().exists()) {
                sysFile.getParentFile().mkdirs();
            }
            // file.transferTo(sysFile);
            FileUtils.copyInputStreamToFile(file.getInputStream(), sysFile);

            // Keep Log:Insert a record into table
            // hst_hmms_attachment_detail----------------------------

//----------------------------
            //-- get current  HST_HMMS_ATTACHMENT_DETAIL.PUD_ID
            try {
                // String SQL_GET_PUD_ID = "Select max(ATD_ID) from HST_HMMS_ATTACHMENT_DETAIL"; 
                conn = dataSource.getConnection();
                String SQL_GET_ATD_ID = "SELECT ATD_SEQ.NEXTVAL from dual";
                psmt = conn.prepareStatement(SQL_GET_ATD_ID);
                rs = psmt.executeQuery();
                if(rs!=null && rs.next()){
                    atdID = rs.getInt(1);  
                }
                rs.close();
                ConnectionUtil.closePreparedStatement(psmt);
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtil.rollback(conn);
                return ResultBuilder.buildServerFailResult(e.getMessage());
            } finally {
                ConnectionUtil.closePreparedStatement(psmt);
                // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
            }
    //-----------------------
            //-------20221024
            user = adjustUser(user);
            setFscUser(conn, user, "UPDELNOTE");
            //-------20221024    

            String sql_insert_atd_detail = this.SQL_INSERT_ATTACHMENT_DETAIL;
            try {
                // conn = dataSource.getConnection();
                ConnectionUtil.beginTransaction(conn);
                psmt = conn.prepareStatement(sql_insert_atd_detail);
                psmt.setInt(1,atdID);
                psmt.setInt(2,5);
                psmt.setString(3, "txUpload");
                psmt.setString(4, ("x_Upload_File_ITS " + orgFileName));
                psmt.setString(5, sysFileName);
                psmt.setString(6,"iHousing_Tx_Upload_File_ITS " + orgFileName);
                psmt.setInt(7, 0);
                psmt.setString(8, user);
                psmt.executeUpdate();
                ConnectionUtil.closePreparedStatement(psmt);
                // ConnectionUtil.commit(conn);
                
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtil.rollback(conn);
                return ResultBuilder.buildServerFailResult(e.getMessage());
            } finally {
                ConnectionUtil.closePreparedStatement(psmt);
                // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
            }


            read = new InputStreamReader(file.getInputStream(), encoding);
            bufferedReader = new BufferedReader(read);

            // ---------------2.Get List Str From CSV-----------------
            // List<String[]> dataList = new ArrayList<String[]>();
            List<String> dataList = new ArrayList<String>();
            String line = " ";
            while (StringUtils.isNotEmpty(line)) {
                line = bufferedReader.readLine();
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                line = line + ", , ,";  // protect: the last value is blank and cause split miss the value 
                // 20240409 zgl
                line = line + ", , , , , , , , , , , , , , , , , , , , , , , , , , , , ,";
                //String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // String[] columns = line.split(",");
                // dataList.add(columns);
                dataList.add(line);
            }

            try {
                // conn = dataSource.getConnection();
                ConnectionUtil.beginTransaction(conn);
                int lineNo = 0;
                for (int i = 0; i < dataList.size(); i++) {
                   currRow = dataList.get(i);
                    // if (currRow.length > 0 && !currRow[0].toUpperCase().contains("PROPERTY REFERENCE") ) { // ignore first row
                    if (currRow.length() > 0 && !currRow.toUpperCase().contains("PROPERTY REFERENCE") ) { // ignore first row
                        // String estate = StringUtils.trim(currRow[0]);
                        // String block = StringUtils.trim(currRow[1]);
                        // String propRef = StringUtils.trim(currRow[2]);
                        // String eleCode = StringUtils.trim(currRow[3]);
                        // String startDate = StringUtils.trim(currRow[4]);
                        // String endDate = StringUtils.trim(currRow[5]);
                        // String value = StringUtils.trim(currRow[6]);
                        lineNo++;
                     
                        Log.show("PROPERTY_BULK_CREATION", "call hsk_prop_main.bulk_upload_import_line");
                        CallableStatement cstmt1 = conn
                                .prepareCall("{call hsk_prop_main.bulk_upload_import_line(?,?,?,?)}");
                        cstmt1.setInt(1, atdID);
                        cstmt1.setInt(2, lineNo);
                        cstmt1.setString(3, currRow);
                        cstmt1.registerOutParameter(4, OracleTypes.VARCHAR);
                        cstmt1.execute();
                        upload_result = cstmt1.getString(4);
                        cstmt1.close();
                        
                        if (upload_result.contains("FAIL")) {
                            
                            message = upload_result.split("`")[0];
                            failLineNo = Integer.parseInt(upload_result.split("`")[1]);
                            //  --  20220720   add ------------------------------------------------------------------------------------
                            Log.show("PROPERTY BULK CREATION Upload","call hsk_upload.new_upload with status UPLOAD_FAIL");
                            // /*------------1 create new upload log  ---------------*/
                            CallableStatement cstmt2 = conn.prepareCall("{call hsk_upload.new_upload(?,?,?,?,?,?,?,?,?,?)}");

                            cstmt2.setString(1,user); //user
                            cstmt2.setString(2,"PBC");      //sys_code
                            cstmt2.setInt(3,atdID);   //run_id    atdID
                            cstmt2.setString(4,orgFileName); // org_file_name
                            cstmt2.setString(5,sysFileName);  //sys_file_name
                            cstmt2.setString(6,orgUploadFilePath);  //sys_file_path
                            cstmt2.setString(7,null);  //remark
                            cstmt2.setString(8, "UPLOAD_FAIL");  //ststus
                            cstmt2.setString(9,message); //message
                            cstmt2.registerOutParameter(10, OracleTypes.VARCHAR);
                            cstmt2.execute();
                            new_upload_result = cstmt2.getString(10);
                            cstmt2.close();

                            ConnectionUtil.commit(conn);
                            return ResultBuilder.buildServerFailResult(atdID, message, failLineNo, dataList.size()-1); 
                        } 
                    }
                }

                Log.show("PROPERTY_BULK_CREATION", "call hsk_prop_main.UPLOAD_LINE SUCCESS!");
                
                ConnectionUtil.commit(conn);
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtil.rollback(conn);
                return ResultBuilder.buildServerFailResult(e.getMessage());
            } finally {
                ConnectionUtil.closeResultSet(rs);
                ConnectionUtil.closePreparedStatement(psmt);
                ConnectionUtil.closeConnection(conn);
                // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (Exception ee1) {
                }
                try {
                    if (read != null)
                        read.close();
                } catch (Exception ee2) {
                }
            }

                //  --  20220720   add ------------------------------------------------------------------------------------
                Log.show("PROPERTY BULK CREATION Uplaod","call hsk_upload.new_upload with status UPLOADED");
                // /*------------1 create new upload log  ---------------*/
                conn = dataSource.getConnection();
                CallableStatement cstmt2 = conn.prepareCall("{call hsk_upload.new_upload(?,?,?,?,?,?,?,?,?,?)}");
                try {
                    ConnectionUtil.beginTransaction(conn);
                    cstmt2.setString(1,user); //user
                    cstmt2.setString(2,"PBC");      //sys_code
                    cstmt2.setInt(3,atdID);   //run_id    atdID
                    cstmt2.setString(4,orgFileName); // org_file_name
                    cstmt2.setString(5,sysFileName);  //sys_file_name
                    cstmt2.setString(6,orgUploadFilePath);  //sys_file_path
                    cstmt2.setString(7,null);  //remark
                    cstmt2.setString(8, "UPLOADED");  //ststus
                    cstmt2.setString(9,null); //message
                    cstmt2.registerOutParameter(10, OracleTypes.VARCHAR);
                    cstmt2.execute();
                    new_upload_result = cstmt2.getString(10);
                    cstmt2.close();
                    ConnectionUtil.commit(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    ConnectionUtil.rollback(conn);
                    return ResultBuilder.buildServerFailResult(e.getMessage());
                } finally {
                    ConnectionUtil.closeConnection(conn);
                }
             
                //  --  20220720       --------------------------------------------------------
                if (new_upload_result.contains("FAIL")) {
                    // return ResultBuilder.buildServerFailResult(new_upload_result);
                    return ResultBuilder.buildServerFail1600Result(new_upload_result); 
                } 

            return ResultBuilder.buildSuccessResult(atdID, upload_result, dataList.size()-1);
    }


    //20220713
    public Result validate(String atdID, String user) throws Exception {
        String validate_result = "OK";
        String update_validate_status = "OK";
        String message = "";
        int failLineNo = 0;
        int totalLineNum = 0;
        try {
            conn = dataSource.getConnection();
            //-------20221024
            user = adjustUser(user);
            setFscUser(conn, user, "UPDELNOTE");
            //-------20221024 

            ConnectionUtil.beginTransaction(conn);

            Log.show("PROPERTY_BULK_CREATION", "call hsk_prop_main.bulk_upload_validate");
            CallableStatement cstmt1 = conn.prepareCall("{call hsk_prop_main.bulk_upload_validate(?,?,?)}");
            cstmt1.setInt(1, Integer.parseInt(atdID));
            cstmt1.setString(2, user);
            cstmt1.registerOutParameter(3, OracleTypes.VARCHAR);
            cstmt1.execute();
            validate_result = cstmt1.getString(3);
            cstmt1.close();

            message = validate_result.split("`")[0];
            totalLineNum = Integer.parseInt(validate_result.split("`")[1]);

            //------------------------20220721
            //update upload log set  status  =  -------------------------------------------------
            Log.show("update PBC Validate","call hsk_upload.update_upload_status");
            CallableStatement cstmt = conn.prepareCall("{call hsk_upload.update_upload_status(?,?,?,?,?,?)}");
            cstmt.setString(1,user); //user
            cstmt.setString(2,"PBC");      //sys_code
            cstmt.setInt(3,Integer.parseInt(atdID));  //run_id
            cstmt.setString(4,validate_result.contains("FAIL")? "VALIDATE_FAIL":"VALIDATED");  //ststus
            cstmt.setString(5,validate_result.contains("FAIL")?  message:"Validate Success");   //message
            cstmt.registerOutParameter(6, OracleTypes.VARCHAR);
            cstmt.execute();
            update_validate_status = cstmt.getString(6);
            cstmt.close();
            Log.show("update PBC Validate","call hsk_upload.update_upload_status SUCCESS!");

            //--------------------------20220721
            ConnectionUtil.commit(conn);

            if (update_validate_status.contains("FAIL")) {
                // return ResultBuilder.buildServerFailResult(update_validate_status);
                return ResultBuilder.buildServerFail1600Result(update_validate_status); 
            }


            if (validate_result.contains("FAIL")) {
                 //ConnectionUtil.rollback(conn);
                ConnectionUtil.commit(conn);
                failLineNo = Integer.parseInt(validate_result.split("`")[2]);
                //TO DO: update status to atd
                return ResultBuilder.buildServerFailResult(Integer.parseInt(atdID), message, failLineNo, totalLineNum);
            } 
                
            Log.show("PROPERTY_BULK_CREATION", "Validated Sucessful!");
            // ConnectionUtil.commit(conn);
            //TO DO: update status to atd

        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollback(conn);
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
            // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
        }
        return ResultBuilder.buildSuccessResult(Integer.parseInt(atdID), message ,totalLineNum);
    }


     //20240123
     public Result uploadData(String atdID, String user) throws Exception {
      
        String result = "OK";
        String update_process_status = "OK";
        String message = "";
        int failLineNo = 0;
        int totalLineNum = 0;
        try {
            conn = dataSource.getConnection();
            //-------20221024
            user = adjustUser(user);
            setFscUser(conn, user, "UPDELNOTE");
            //-------20221024  

            ConnectionUtil.beginTransaction(conn);

            Log.show("PROPERTY_BULK_CREATION", "call  hsk_prop_main.bulk_upload_property");
            CallableStatement cstmt1 = conn.prepareCall("{call hsk_prop_main.bulk_upload_property(?,?,?)}");
            cstmt1.setInt(1, Integer.parseInt(atdID));
            cstmt1.setString(2, user);
            cstmt1.registerOutParameter(3, OracleTypes.VARCHAR  );
            cstmt1.execute();
            result = cstmt1.getString(3);
            cstmt1.close();
                                 
            //FAIL:' || SQLERRM || ' ` '; 
            if (result.toUpperCase().startsWith("FAIL")) {
                message = result;
            } else {
                String []resultArr = result.split("`");  // 'OK`Total '|| v_total_line || ' lines  processed/uploaded '||'`'|| v_total_line;
                message = resultArr[1];
            }
            //------------------------20220721
            //update upload log set  status  =  -------------------------------------------------
            CallableStatement cstmt = conn.prepareCall("{call hsk_upload.update_upload_status(?,?,?,?,?,?)}");
            cstmt.setString(1,user); //user
            cstmt.setString(2,"PBC");      //sys_code
            cstmt.setInt(3,Integer.parseInt(atdID));  //run_id
            cstmt.setString(4,result.contains("FAIL")? "PROCESSED_FAIL":"PROCESSED");  //ststus
            cstmt.setString(5,result.contains("FAIL")?  message:"PROCESSED Success");   //message
            cstmt.registerOutParameter(6, OracleTypes.VARCHAR);
            cstmt.execute();
            update_process_status = cstmt.getString(6);
            cstmt.close();

            //--------------------------20220721
            if (update_process_status.contains("FAIL")) {
                ConnectionUtil.commit(conn);
                // return ResultBuilder.buildServerFailResult(update_process_status);
                return ResultBuilder.buildServerFail1600Result( update_process_status);//20220721
            }

            if (result.contains("FAIL")) {
                // ConnectionUtil.rollback(conn);
                ConnectionUtil.rollback(conn);
                //TO DO: update status to atd
                // return ResultBuilder.buildServerFailResult(Integer.parseInt(atdID), message, failLineNo, totalLineNum);
                // return ResultBuilder.buildServerFailResult( message);
                return ResultBuilder.buildServerFail1600Result( result);
            } 
            totalLineNum = Integer.parseInt(result.split("`")[2]);
            Log.show("PROPERTY_ELEMENT", "call hsk_pe_upload.upload_data SUCCESS!");
            ConnectionUtil.commit(conn);

            //TO DO: update status to atd
               
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollback(conn);
            return ResultBuilder.buildServerFailResult(e.getMessage());
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(psmt);
            ConnectionUtil.closeConnection(conn);
            // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
        }
        return ResultBuilder.buildSuccessResult(Integer.parseInt(atdID), message ,totalLineNum);
    }


    //-----------20221024---------------------------------------
    private String adjustUser(String user) {
		user = user.toUpperCase();
		if (user.equals("ADMIN"))
			user = "HWLEE";
		return user;
	}

	private void setFscUser(Connection conn, String user, String role) throws SQLException {
		// insert job user, if job_user exists then igore

		if (role != null && role.equals("UPDELNOTE")) {

			try {
				psmt = conn.prepareStatement(
						"insert into job_role_users values ('UPDELNOTE', ?, to_date('01/01/2021','dd/mm/yyyy', null,'DUMMY FOR HAA2/IHOUSING-Add Once', 'Y')");
				psmt.setString(1, user);
				psmt.executeUpdate();
			} catch (Exception ee) {
				;
			} finally {
				// DBConnection.closePreparedStatement(psmt);
                ConnectionUtil.closePreparedStatement(psmt);
			}
		}

		CallableStatement cstmt = null;
		cstmt = conn.prepareCall("{call fsc_variables.set_g_username(?)}");
		cstmt.setString(1, user);
		cstmt.execute();
		cstmt.close();
		cstmt = null;
	}
    //-----------20221024------------------------


    public String getUploadFileType (String atdID) throws SQLException {
        String fileType = "PE";
        try {
                // String SQL_GET_PUD_ID = "Select max(ATD_ID) from HST_HMMS_ATTACHMENT_DETAIL"; 
                conn = dataSource.getConnection();
                String SQL_GET_FILE_TYPE = "select UPL_SYS_CODE from hst_hmms_upload" + //
                        " where upl_run_id=" + atdID;
                psmt = conn.prepareStatement(SQL_GET_FILE_TYPE);
                rs = psmt.executeQuery();
                if(rs!=null && rs.next()){
                    fileType = rs.getString(1);  
                }
                rs.close();
                ConnectionUtil.closePreparedStatement(psmt);
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL:"+e.getMessage();
            } finally {
                ConnectionUtil.closePreparedStatement(psmt);
                // try { if (conn!=null) conn.close(); } catch(Exception ee) {}
            }
            return fileType;    
    } 

}




