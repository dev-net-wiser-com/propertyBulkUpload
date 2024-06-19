import {fromJS} from "immutable";

const initialState = {
    UploadFTag:false,
    UploadVTag:true,
    UploadDTag:true,
    fileName:"",
    totalRecord:0,
    dataList:[],
    ids:[],
    admin:"", //login user
    batchRef:"",
    dataSummary:"",
    openPage:"MAIN", //-MAIN -STANDARD -MGFEE -RENT
    openDownloadModal:false,
    openMgFeeDownloadModal:false,
    openRentDownloadModal:false,
    FTitle:"",
    status:"",
    failMsg:[],
    status2:"",
    hasMsg:false,
    FLoad:false, //file upload
    VLoad:false, //Validation 
    ULoad:false, //data upload

    estate:"",
    estateList:[],
    block:"",
    blockList:[],
    elementCode:"",
    elementList:[],
    startDate:"",
    endDate:"",
    percentage:"",
    btnDownloadDisable:true,

    seqno:0,

    disableValidate:false

};


export default (state = initialState, action) => {

    switch (action.type) {
        case "propertyBulk/CHANGE_VALUE":
            return fromJS(state).set(action.name,action.value).toJS();
        case 'propertyBulk/CLEAR':
            let admin=state.admin;
            state=initialState;
            state.admin=admin;
            return fromJS(state).toJS();
        case 'propertyBulk/INIT_UPLOAD':
            state.UploadDTag=false;
            state.hasMsg=false;
            state.dataSummary="";
            state.batchRef="";
            state.status="";
            state.status2="";
            state.dataList=[];
            return fromJS(state).toJS();
        case 'propertyBulk/INIT_TEMPLATE_INFO':
            state.estate = "";
            state.block = "";
            state.elementCode = "";
            state.startDate = "";
            state.endDate = "";
            state.percentage = "";
            state.blockList = [];
            state.lSelectBlockList = [];
            state.rSelectBlockList = [];
            state.valueBlockList = [];
            state.dataGrid1 = [];
            state.btnDownloadDisable = false;
            return fromJS(state).toJS();
        case "propertyBulk/CHANGE_VALUES":
            if(Object.keys(action.data)){
                Object.keys(action.data).forEach(e => {
                    state[e] = action.data[e];
                })
            }
            return fromJS(state).toJS();
        case "propertyBulk/ADD_MGFEE_ITEM":
            state.dataItem = initialState.dataItem;
            state.dataGrid1.push(initialState.dataItem);
            return fromJS(state).toJS();
        default:
            return state;
    }

};
