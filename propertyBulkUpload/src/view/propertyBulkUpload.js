import React, { Component } from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import "../style/interview.css";
import { getSession, getParameters,isEmpty } from "../service/util";
import { confirmAlert } from 'react-confirm-alert';
import ReactLoading from 'react-loading';
import ReactTable from "react-table";
import "react-table/react-table.css";
import '../style/react-comfirm-alert.css';

import actions from '../action/propertyBulkUploadAction';
import DownloadTemplateModal from '../component/modal/downloadTemplateModal';
// import DownloadMgFeeModal from '../component/modal/downloadMgFeeModal';
// import DownloadRentModal from '../component/modal/downloadRentModal';

/*status:
UPLOADED:File Uploaded
UPLOAD_FAIL:File Upload Fail
VALIDATED:Validated
VALIDATE_FAIL:Validate Fail
PROCESSED:Processed
PROCESS_FAIL:Process Fail
**/
class propertyBulkUpload extends Component {
  state = {
    dataList:[]
  }
  componentDidMount (){
    let {actions} = this.props;
    let _this = this;
    let admin = this.getLoginAdminName();
    actions.changeValue("admin",admin);
    
    //this.props.actions.getLastUpload(admin,_this,10,3600);
  };
  componentWillUnmount (){
    this.props.actions.closeTimer();
  }
  getLoginAdminName = () => {
    let param = getSession("param");
    if(param == null)
    {
      return "admin";
    }
    let parameters = getParameters(decodeURIComponent(param));
    let userId = parameters.loginid;
    return userId;
    // let up = parameters.up;
    // console.log("--getLoginInfo----->", parameters);
  };
  returnMain = () => {
    this.props.history.push({ pathname: "/" });
  }

  clickDldTemp = () => {
    let { actions } = this.props;
    actions.initTemplateInfo();
    actions.changeValue("openDownloadModal",true);
  }

  download = (event) => {
    if(event){
      event.preventDefault();
    }
    this.props.actions.downloadTemplate();
  }


  selectFile=(event)=>{
    let {propertyBulkUploadInfo,actions} = this.props;
    let {UploadFTag,UploadDTag} = propertyBulkUploadInfo;
    let _this = this;
    actions.closeTimer();
    //actions.changeValue("ULoad",false);
    //let {dataList} = this.state;
    let dataList = [];
    let file = {
      fileName:"",
      fileContent:null,
      UStatus:"",
      DStatus:"",
      seqno:0,
      remark:null
    };
    if(this.refs.selectfile.files[0] && dataList.findIndex(item => item.fileName === this.refs.selectfile.files[0].name)<0){
      let str=this.refs.selectfile.files[0].name.substring(0,4);
      file["fileName"] = this.refs.selectfile.files[0].name;
      file["fileContent"] = this.refs.selectfile.files[0];
      file["UStatus"] = "";
      file["DStatus"] = "";
      file["seqno"] = 0;
      dataList.push(file);
      this.setState({
        dataList:dataList,
      });
      //console.log(dataList);
      event.target.value = null
      actions.uploadFile(dataList[0],_this);
      //if(UploadFTag){actions.changeValue("UploadFTag",false);}
      //if(!UploadDTag){actions.changeValue("UploadDTag",true);}
    }
  }
  Validation = event => {
    let {actions} = this.props;
    let {dataList}=this.state;
    let _this=this;
    if(dataList.length<=0){
      alert("Please select Data File");
      return
    }
    confirmAlert({
      title: '',
      message: "Do you want to validate the file?",
      buttons: [
        {
          label: 'Y',
          onClick: () => { 
            actions.Validation(dataList[0],_this); 
          }
        },
        {
          label: 'N',
          onClick: () => { }
        }
      ]
    })
  }
  upload = event => {
    let {actions} = this.props;
    let {dataList}=this.state;
    let _this=this;
    if(dataList.length<=0){
      alert("Please select Data File");
      return
    }
    confirmAlert({
      title: '',
      message: "Do you want to upload the file?",
      buttons: [
        {
          label: 'Y',
          onClick: () => { 
            actions.upload(dataList[0],_this); 
          }
        },
        {
          label: 'N',
          onClick: () => { }
        }
      ]
    })
  }
  start = event =>{
    let {actions} = this.props;
    let {dataList} = this.state;
    let _this = this;
    actions.download(dataList[0],_this);
  }

  canValidation = ()=>{
    let {UploadVTag} = this.props.propertyBulkUploadInfo;
    let {dataList} = this.state;
    let tag=false;
    dataList.map(item=>{
      if(item.UStatus=="UPLOADED" || item.UStatus=="VALIDATE_FAIL"){
        tag=true;
      }
    });
    if(UploadVTag==false){
      tag=false;
    }
    return tag
  }

  canUpload = ()=>{
    let {UploadDTag} = this.props.propertyBulkUploadInfo;
    let {dataList} = this.state;
    let tag=false;
    dataList.map(item=>{
      if(item.UStatus=="VALIDATED" || item.UStatus=="PROCESS_FAIL"){
        tag=true;
      }
    });
    if(UploadDTag==false){
      tag=false;
    }
    return tag
  }
  showStatus = (status) => {
    switch (status) {
      case "UPLOADED":
        return "File Uploaded"
      case "UPLOAD_FAIL":
        return "File Upload Fail"
      case "VALIDATED":
        return "Validated"
      case "VALIDATE_FAIL":
        return "Validate Fail"
      case "PROCESSED":
        return "Processed"
      case "PROCESS_FAIL":
        return "Process Fail"
      default:
        break;
    }
  }
  render() {
   const columns = [
    {

      Header: 'Step 1',
      Footer: "step1",
      columns: [
        {
          Header: 'File Selected',
          accessor: 'fileName',
          maxWidth: 300
        },
      ],
    },
    {
      Header: 'Step 2',
      Footer: "step2",
      columns: [
        {
          Header: 'Upload Status',
          accessor: 'UStatus',
          maxWidth: 200,
          Cell: ({ original }) => {
            if(isEmpty(original.UStatus)){
              return ""
            }else {
              return this.showStatus(original.UStatus)
            }
          }
        },
        {
          Header: 'Message',
          accessor: 'remark',
          style:{ 'whiteSpace': 'unset' }
          
        }
      ],
    }
   ];
  let {propertyBulkUploadInfo,actions,history} = this.props;
  let {UploadFTag,UploadDTag,FTitle,ULoad,FLoad,VLoad,openDownloadModal,openMgFeeDownloadModal,openRentDownloadModal, disableValidate} = propertyBulkUploadInfo;
  let {dataList} = this.state;
    return (
      <div className="panel" style={{position:"static"}}>
        <DownloadTemplateModal info = {propertyBulkUploadInfo} actions = {actions} isOpen = {openDownloadModal} title = "Download Template"/>
        {/*
        <DownloadMgFeeModal info = {propertyBulkUploadInfo} actions = {actions} isOpen = {openMgFeeDownloadModal} title = "Download Template - Management Fee Revision (From - to)"/>
        <DownloadRentModal info = {propertyBulkUploadInfo} actions = {actions} isOpen = {openRentDownloadModal} title = "Download Template - Rent Revision" /> */}
         <div className="navigation">
            <span className="panel-title">Property Bulk Upload</span>
            <input type="button" value="Return" className="nav-button" onClick={this.returnMain} />
            {/* <input type="button" value="Download Template" className="nav-button" onClick={this.clickDldTemp}/> */}
            <input type="button" value="Download Template" className="nav-button" onClick={this.download}/>
        </div> 
        
        {/* <div class="navbar">
          <div class="dropdown">
            <input type="button" value="Download Template" class="dropbtn" />
            <div class="dropdown-content">
              <a onClick={this.clickDldTemp}>Download Standard Template</a>
              <a onClick={this.clickMFeeTemp}>Download M.Fee.Rev. Template</a>
              <a onClick={this.clickRentTemp}>Download Rent Rev. Template</a>
            </div>
          </div> 
        </div> */}
        <div className="topMain" style={{marginTop:"15px",display:"table"}}>
          <div>
            <div className="topRow">

              <div className="selectView" style={{marginRight:"125px",fontWeight:"bold"}}>
                <span className="title">Select and Upload File</span>
              </div>
              <div className="selectView" style={{marginRight:"135px",fontWeight:"bold"}}>
                <span className="title" >Validation</span>
              </div>
              <div className="selectView" style={{marginRight:"80px",fontWeight:"bold"}}>
                <span className="title" >Upload Data</span>
              </div>
              {/*
              <div className="selectView" style={{fontWeight:"bold"}}>
                <span className="title">Download File</span>
              </div>
              */}
            </div>

            <div className="topRow">
              <div className="selectView">
                <input type="button"  style={{width:"135px",marginRight:"100px",borderRadius:"2px"}} disabled={FLoad} value={FLoad?"Waiting...":"Select and Upload"} onClick={()=>{this.refs.selectfile.click()}} />
                <input ref="selectfile" type="file" accept=".CSV,.dat" className="hidden" onChange={this.selectFile} />
                {/* <input type="button" style={{width:"135px", marginRight:"100px"}} className="button-white" disabled={!this.canValidation()} onClick={this.Validation} value={VLoad?"Waiting...":"Validation"} /> */}
                <input type="button" style={{width:"135px", marginRight:"100px"}} className="button-white" disabled={disableValidate} onClick={this.Validation} value={VLoad?"Waiting...":"Validation"} />
                <input type="button" style={{width:"135px", marginRight:"100px"}} className="button-white" disabled={!this.canUpload()} onClick={this.upload} value={ULoad?"Waiting...":"Upload"} />
                

              </div>
            </div>
          </div>
        </div>


        <div className="tableMain">
          <ReactTable
            data={dataList}
            noDataText=""
            minRows={10}
            className="-striped -highlight"
            showPageJump={false}
            showPageSizeOptions={false}
            showPagination={false}
            columns={columns}
          ></ReactTable>
        </div>
      </div>
    )
  }
}



const mapStateToProps = state =>({
  propertyBulkUploadInfo: state.propertyBulkUploadInfo
});

const mapDispatchToProps = dispatch =>({
  actions: bindActionCreators(actions,dispatch)
});
export default connect(mapStateToProps,mapDispatchToProps)(propertyBulkUpload);
