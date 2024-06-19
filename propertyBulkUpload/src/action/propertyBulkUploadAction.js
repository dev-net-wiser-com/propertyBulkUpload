import {formPostService,postServiceTools,getServiceTools,GET_SERVICE_URL} from '../service/service';
import { om, isEmpty } from '../service/util';
import { confirmAlert } from 'react-confirm-alert';

let timer1, timer2;
let actions = {
    getEstateList: function(){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        dispatch(actions.changeValue("estateList",[]))
        getServiceTools("getEstateList?user=" + admin + "&ts="+Math.random(),(str)=>{
          if(str.errorCode==1200){
            let arr = str.result;
            arr.splice(0,0,{code:'',description:''});
            dispatch(actions.changeValue("estateList",arr));
          }
        })
      }
    },


    getBlockList: function(estate){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        // let param = "estate=" + (estate || '' ) + "&ts="+Math.random();
        let param = "user=" + admin + "&" + "estate=" + (estate || '' ) + "&ts="+Math.random();
        dispatch(actions.changeValue("block",""));
        dispatch(actions.changeValue("blockList",[]));
        dispatch(actions.changeValues({
          lSelectBlockList:[],
          valueBlockList:[],
          rSelectBlockList:[]
        }));
        getServiceTools("getBlockList?" + param,(str)=>{
          if(str.errorCode==1200){
            let arr = str.result;
            arr.splice(0,0,{code:'',description:''});
            dispatch(actions.changeValue("blockList",arr));
          }
        })
      }
    },




    getElementList: function(){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        dispatch(actions.changeValue("elementList",[]))
        getServiceTools("getElementList?user=" + admin + "&ts="+Math.random(),(str)=>{
          if(str.errorCode==1200){
            let arr = str.result;
            arr.splice(0,0,{code:'',description:''});
            dispatch(actions.changeValue("elementList",arr));
          }
        })
      }
    },

    // downloadTemplate: function(){
    //   return function(dispatch,getState){
    //     let { estate ,block ,elementCode ,startDate,endDate ,admin} = getState().propertyBulkUploadInfo;
    //     if(isEmpty(estate)){
    //       alert("Please select Estate");
    //       return;
    //     }
    //     if(isEmpty(elementCode)){
    //       alert("Please select Element Code");
    //       return;
    //     }
    //     if(isEmpty(startDate)){
    //       alert("Please select Element Start Date");
    //       return;
    //     }
    //     let param = "estate="+estate+"&block="+block+"&elementCode="+elementCode+"&startDate="+startDate+"&endDate="+endDate+"&ts="+Math.random();
    //     dispatch(actions.changeValue("btnDownload",false));
    //     dispatch(actions.closeTimer());
    //     getServiceTools("downloadTemplate?user=" + admin + "&" + param,(str)=>{
    //       if(str.errorCode==1200){
    //         // window.open(str.result);
    //         //20220526
    //         window.open (GET_SERVICE_URL.uploadUrl+"download.jsp?filedesc="+ str.result);
    //       }else{
            
    //         alert(str.msg);
    //       }
    //       dispatch(actions.changeValue("btnDownload",true));
    //     })
    //   }
    // },
    downloadTemplate: function(){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        dispatch(actions.changeValue("btnDownloadDisable",true));
        dispatch(actions.closeTimer());
        getServiceTools("downloadTemplate?user=" + admin,(str)=>{
          if(str.errorCode==1200){
            // window.open(str.result);
            //20220526
            window.open (GET_SERVICE_URL.uploadUrl+"download.jsp?filedesc="+ str.result);
          }else{
            alert(str.msg);
          }
          dispatch(actions.changeValue("btnDownloadDisable",false));
        })
      }
    },


    uploadFile: function(fileList,_this){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        dispatch(actions.changeValue("disableValidate",false));  //20240222
        let formData = new FormData();
        formData.append("files", fileList.fileContent ,fileList.fileName );
        formData.append("user",admin);
        dispatch(actions.changeValue("FLoad",true));
        dispatch(actions.closeTimer());
        formPostService("uploadFileModel",formData,(str)=>{
          if(str.errorCode==1200){
            alert("File is uploaded successfully!");
            //fileList.UStatus = "S";
            fileList.UStatus = "UPLOADED";
            // fileList.seqno = str.result;
            fileList.seqno = str.atdID;
            fileList.remark = "Total "+str.total+" Line Uploaded";
            dispatch(actions.changeValue("UploadDTag",true));
          }else{
            // alert(str.msg);
            alert("file upload fail");
            fileList.UStatus = "UPLOAD_FAIL";
            // fileList.remark = str.msg;
            fileList.remark ="file upload fail";
          }
          dispatch(actions.changeValue("FLoad",false));
          _this.setState({
            dataList:[fileList],
          });
        },()=>{
          dispatch(actions.changeValue("FLoad",false));
          fileList.UStatus = "E";
          _this.setState({
            dataList:[fileList],
          });
        }
        )
      }
    },
    Validation: function(fileList,_this){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        dispatch(actions.changeValue("VLoad",true));
        dispatch(actions.changeValue("UploadVTag",false));  
        dispatch(actions.changeValue("disableValidate",true));  //20240220
        getServiceTools("validationModel?user="+admin+"&atdID="+fileList.seqno+"&ts="+Math.random(),(str)=>{    
          if(str.errorCode==1200){
            alert("Validation successful!");
            fileList.UStatus = "VALIDATED";
            fileList.remark = "Total "+str.total+" Line Validated";
            // fileList.seqno = str.result;
            fileList.seqno = str.atdID;
            dispatch(actions.changeValue("UploadDTag",true));
            dispatch(actions.changeValue("UploadVTag",true));//20221222zgl
          }else if(str.errorCode==1500){
            alert("Total "+str.total+" line but "+str.failLineNo+" line failed.");
            fileList.UStatus = "VALIDATE_FAIL";
            //fileList.remark = str.msg;
            fileList.remark = "Total "+str.total+" line but "+str.failLineNo+" line failed.";
            dispatch(actions.changeValue("UploadVTag",true));
          }else{
            alert(str);
            fileList.UStatus = "VALIDATE_FAIL";
            //fileList.remark = str.msg;
            fileList.remark = str;
            dispatch(actions.changeValue("UploadVTag",true));
          }
          dispatch(actions.changeValue("VLoad",false));
          _this.setState({
            dataList:[fileList],
          });
        });
        
      }
    },
    upload: function(fileList,_this){
      return function(dispatch,getState){
        let {admin} = getState().propertyBulkUploadInfo;
        //let formData = new FormData();
        //formData.append("files", fileList.fileContent ,fileList.fileName );
        //formData.append("user",admin);
        dispatch(actions.changeValue("ULoad",true));
        dispatch(actions.changeValue("UploadDTag",false));  
        getServiceTools("uploadDataModel?user="+admin+"&atdID="+fileList.seqno+"&ts="+Math.random(),(str)=>{    
          if(str.errorCode==1200){
            alert("Files data are upload!");
            fileList.UStatus = "PROCESSED";
            fileList.seqno = str.result;
            fileList.remark = "Total "+str.total+" Line Processed";
            dispatch(actions.changeValue("UploadFTag",true));
          }
          // ----add by zgl 20220721
          else if(str.errorCode==1600){
            alert(str.msg);
            fileList.UStatus = "PROCESS_FAIL";
            //fileList.remark = str.msg;//---20211225 zgl
            fileList.remark = str.msg;
            dispatch(actions.changeValue("UploadDTag",true));
          }
          //---------
          
          else{
            alert(str.msg);
            fileList.UStatus = "PROCESS_FAIL";
            //fileList.remark = str.msg;//---20211225 zgl
            fileList.remark = "Total "+str.total+" line but "+str.failLineNo+" line failed."
            dispatch(actions.changeValue("UploadDTag",true));
          }
          dispatch(actions.changeValue("ULoad",false));
          //dispatch(actions.changeValue("dataList",[fileList]));
          _this.setState({
            dataList:[fileList],
          });
        });
      }
      
  },
  
    _getLastUpload: function(admin,_this){
      return function(dispatch,getState){
        getServiceTools("getLastUpload?user="+admin+"&sysCode=PROP&ts="+Math.random(),(str)=>{    
          if(str.errorCode==1200){
            let file = {};
            let data = str.result;
            let tag = true;
            if(data && data.id){
              file.fileName = data.fileOrgName;
              file.seqno = data.runId;
              
              //UPL_STATUS: 'UPLOADED', 'PROCESSED', 'DOWNLOADED', 'FAIL'
              if(data.status == "UPLOADED"){
                dispatch(actions.changeValue("ULoad",true));
                dispatch(actions.changeValue("UploadDTag",false));
                file.UStatus = "P"; 
                tag = false;
              }else if(data.status == "FAIL"){
                dispatch(actions.changeValue("ULoad",false));
                dispatch(actions.changeValue("UploadDTag",false)); 
                file.UStatus = "F";
                file.remark = data.message || "";
              }else if(data.status == "PROCESSED"){
                dispatch(actions.changeValue("ULoad",false));
                dispatch(actions.changeValue("UploadDTag",false)); 
                dispatch(actions.changeValue("UploadFTag",true));
                file.UStatus = "S";
              }
            }
            if(data.status && ["PROCESSED","FAIL","UPLOADED"].indexOf(data.status) >= 0){
              _this.setState({
                dataList:[file],
              });
              //dispatch(actions.changeValue("dataList",[file]));
            }else{
              _this.setState({
                dataList:[],
              });
            }
            if(tag){
              dispatch(actions.closeTimer());
            }
          }else{
            console.log(str.msg);
          }
          
        });
      }
    },
    getLastUpload:function(admin,_this,time1=10,time2=3600){
      return function(dispatch,getState){
        dispatch(actions.closeTimer());
        timer1 = setInterval(() => {
          dispatch(actions._getLastUpload(admin,_this))},parseInt(time1)*1000
        )
        dispatch(actions._getLastUpload(admin,_this));
        timer2 = setTimeout(()=>{dispatch(actions.closeTimer());},parseInt(time2)*1000);
      }
    },
    closeTimer:()=>{
      return ()=>{
        if(!isEmpty(timer1)){clearInterval(timer1);} 
        if(!isEmpty(timer2)){clearTimeout(timer2);}
      }
    },
    changeValue: (name,data) => ({
        type: 'propertyBulk/CHANGE_VALUE',
        name: name,
        value: data
    }),
    clear: () => ({
        type: 'propertyBulk/CLEAR'
    }),
    initUpload: () => ({
        type: 'propertyBulk/INIT_UPLOAD'
    }),
    initTemplateInfo:() => ({
      type: 'propertyBulk/INIT_TEMPLATE_INFO'
    }),
    changeValues: (data) => ({
        type: 'propertyBulk/CHANGE_VALUES',
        data: data
    }),
    addMgFeeItem: () => ({
        type: 'propertyBulk/ADD_MGFEE_ITEM'
    })
};
export default actions;
