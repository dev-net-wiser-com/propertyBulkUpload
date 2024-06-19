import React, { Component } from 'react';
import './dialog.scss';
import ReactTable from 'react-table';
import Calendar from "react-input-calendar";
import '../../style/react-comfirm-alert.css';
import { isEmpty, getDateType, stringToDate, checkHKIDcard, formatNum, moneyToNum } from '../../service/util';
import Dialog from './dialog';
import { confirmAlert } from 'react-confirm-alert';
import "react-datepicker/dist/react-datepicker.css";
import "../../style/inputCalendar.css";

class DownloadTemplateModal extends Component {
  constructor(props) {

    super(props);
  }

  componentDidMount(){
    this.props.actions.getEstateList();
    this.props.actions.getBlockList();
    this.props.actions.getElementList();
  }


  hideModal = (event) => {
    if (event) {
      event.preventDefault();
    }
    this.props.actions.changeValue("openDownloadModal", false);
    this.props.hideModal();  //弹出框位置还原

  }
  download = (event) => {
    if(event){
      event.preventDefault();
    }
    this.props.actions.downloadTemplate();
  }
  changeValue = (e,name,date) => {
    let { actions } = this.props;
    if(name==="startDate" || name==="endDate"){
      actions.changeValue(name,date);
    } else{
      actions.changeValue(name,e.target.value);
      if(name==="estate"){
        actions.getBlockList(e.target.value);
      }
    }

  }
  canDownload = () => {
    let { info } = this.props;
    let { btnDownload ,estate ,block ,elementCode ,startDate } = info;
    /*
    if(isEmpty(estate) || isEmpty(block) || isEmpty(elementCode) || isEmpty(startDate) || !btnDownload){
      return false
    }*/
    return true
  }
  showElementDesc = () => {
    let { info } = this.props;
    let { elementCode,elementList} = info;
    if(Array.isArray(elementList)){
      let element = elementList.find(item => item.code == elementCode);
      if (element && element.description) {
        return element.description
      }
    }
    return ""
  }
  render() {
    let { info, actions } = this.props;
    let { estate,estateList,block,blockList,elementCode,elementList,startDate,endDate,btnDownload } = info;
    return (<div>
      <div className="modal-body" style={{display:"table" }}>
        <div className="line-content">
          <span className="w2">Estate:</span>
          <select className="w3" value={estate} onChange={(e) => {this.changeValue(e,"estate")}}>
            {estateList.map(ele=>{
              return <option value={ele.code} key={"est"+ele.code+Math.random()}>{ele.code}</option>
            })}
          </select>
        </div>
        <div className="line-content">
          <span className="w2">Block:</span>
          <select className="w3" value={block} onChange={(e) => {this.changeValue(e,"block")}}>
            {blockList.map(ele=>{
              return <option value={ele.code} key={"blc"+ele.code+Math.random()}>{ele.code}</option>
            })}
          </select>
        </div>
        <div className="line-content">
          <span className="w2">Element Code:</span>
          <select className="w3" value={elementCode} onChange={(e) => {this.changeValue(e,"elementCode")}}>
            {elementList.map(ele=>{
              return <option value={ele.code} key={"ele"+ele.code+Math.random()}>{ele.code}</option>
            })}
          </select>
          <span style={{marginLeft:"10px"}}>{this.showElementDesc()}</span>
        </div>
        <div className="line-content">
          <span className="w2">Start Date:</span>
          <Calendar
            format="DD/MM/YYYY"
            computableFormat="DD/MM/YYYY"
            closeOnSelect={true}
            date={startDate}
            onChange={(date) => {this.changeValue(null,"startDate",date)}}
          />
        </div>
        <div className="line-content">
          <span className="w2">End Date:</span>
          <Calendar
            format="DD/MM/YYYY"
            computableFormat="DD/MM/YYYY"
            closeOnSelect={true}
            date={endDate}
            onChange={(date) => {this.changeValue(null,"endDate",date)}}
          />
        </div>
      </div>


      <div className="dialog-con-alert-button-group">
        <button onClick={this.download} disabled={!this.canDownload()} className={this.canDownload()?"":"btn_disable"}>{btnDownload?"Download":"Waiting..."}</button>
        <button onClick={this.hideModal}>Cancel</button>
      </div>
    </div>

    );
  }
}

export default Dialog()(DownloadTemplateModal);
