import $ from "jquery";


export const GET_SERVICE_URL = {
    // // for Local
    // serviceUrl: "http://localhost:8090/",
    // uploadUrl: "http://localhost:8090/HMMS_report_download/",
    //  for VM
    serviceUrl: "http://"+window.location.host+"/PBC_UPLOAD_WS/",
    uploadUrl: "http://"+window.location.host+"/HMMS_report_download/",
    debug: false,
    baseUrl: "http://" + window.location.host + "/",
    MenuUrl: "http://" + window.location.host + "/iHousing_WS/services/MenuWebService/",
    //MenuUrl:"http://192.9.200.61:8090/iHousing_WS/services/MenuWebService/",
    iHousingUrl: "http://" + window.location.hostname + "/"
};


export const nAsyncService = (method, data, successFun) => {
    $.ajaxSettings.async = false;  //在$.get()前把ajax设置为同步
    $.get(GET_SERVICE_URL.MenuUrl + method + data,
        (soapResponse) => {
            if (soapResponse.documentElement) {
                var str = soapResponse.documentElement.childNodes[0].childNodes[0].nodeValue;
                if (str && typeof (str) != 'object')
                    str = JSON.parse(str);
                successFun(str);
            }

        });
    $.ajaxSettings.async = true;
}

export const postServiceTools = (method, data, successFun) => {
    $.post(GET_SERVICE_URL.serviceUrl + method, data,
        (soapResponse) => {
            //let str = htmlDecodeByRegEx($(soapResponse).text() );
            let str = soapResponse;
            if (str && typeof (str) != 'object')
                str = JSON.parse(str);
            successFun(str);
        }
    );
};
export const getServiceTools = (method, successFun) => {
    $.get(GET_SERVICE_URL.serviceUrl + method,
        (soapResponse) => {
            //let str = htmlDecodeByRegEx($(soapResponse).text() );
            let str = soapResponse;
            if (str && typeof (str) != 'object')
                str = JSON.parse(str);
            successFun(str);
        }
    );
};

export const formPostService = (method, formData, successFun, errorFun) => {
    $.ajax({
        url: GET_SERVICE_URL.serviceUrl + method,
        //contentType:"multipart/form-data",
        //contentType:"application/octet-stream",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,

        success: function (res) {
            let str = res; //htmlDecodeByRegEx($(soapResponse).text() );
            if (str && typeof (str) != 'object')
                str = JSON.parse(str);
            successFun(str);
        },
        error: function (XMLHttpRequest, textStatus) {
            alert("XMLHttpRequest.state:" + XMLHttpRequest.state + "-XMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "-textStatus:" + textStatus + "-XMLHttpRequest.responseText:" + XMLHttpRequest.responseText);
            if (errorFun) {
                errorFun();
            }
        }
    });
};

export const htmlDecodeByRegEx = function (str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    return s;
}

const range = len => {
    const arr = [];
    for (let i = 0; i < len; i++) {
        arr.push(i);
    }
    return arr;
};
