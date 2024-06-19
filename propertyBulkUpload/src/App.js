import React, { Component } from 'react';
import { Router, Switch, Route } from 'react-router-dom';
import { createBrowserHistory, createHashHistory } from 'history';
import MainRoutes from './routes/MainRoutes';
import './style/App.scss';
import { GET_SERVICE_URL, nAsyncService } from './service/service';
import { isEmpty, ticksTime, getParameters, getUrlCode, saveSession, getSession } from './service/util';
import reducer from './reducer/index';
import thunk from 'redux-thunk';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import Head from './component/HeadDropdown/Head';

const browserHistory = createHashHistory();
const store = createStore(reducer, applyMiddleware(thunk));

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fontsize: "l-app",
      today: "",
      loginName: "",
      menuInfo: {
        menus: [],
        url: GET_SERVICE_URL.baseUrl, //local URL
        iHousingUrl: "", //mainURL
        appName:"" //module Name
      },
      platform: ""
    }
  }
  changeFontsize = (type, event) => {
    this.setState({
      fontsize: type
    })
    saveSession("fsize", type);
  }
  componentWillMount() {
    let date = new Date();
    let currentDate = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
    let tag = false;
    let param
    if (getSession("ps")) {
      param = getSession("ps");
    } else {
      param = getUrlCode("ps");
      saveSession("ps", param);
    }
    let loginid = "";
    let up = "";
    let platform="";
    //let para= "loginid=hwlee&up=test&ts=1557217829";
    //let paraEncode= encodeURIComponent(rDecode(para));
    //alert(paraEncode);
    if (!isEmpty(param)) {
      let parameters = getParameters(decodeURIComponent(param));
      tag = ticksTime(parameters.ts);
      saveSession("param", param);
      saveSession("tag", tag);
      loginid = parameters.loginid;
      up = parameters.up; //permission
      if(parameters.platform){
        platform=parameters.platform;
      }
    }
    /*
    if (!tag) {
      alert("The session is expired! The page will be closed! ");
      window.close();
    }*/
    if (getSession("fsize")) {
      this.setState({
        fontsize: getSession("fsize")
      });
    }
    this.setState({
      today: currentDate,
      loginName: loginid,
      platform:platform
    });

    //get and set menuInfo
    if (getSession("menu")) {
      this.setState({
        menuInfo: {
          menus: getSession("menu"),
          url: GET_SERVICE_URL.baseUrl, //local URL
          iHousingUrl: GET_SERVICE_URL.iHousingUrl + "iHousing/mainH5.jsp?uid=" + loginid + "&up=" + up, //mainURL
          appName: 'pbUpload'
        }
      });
    } else {
      nAsyncService("getValidMenuByGroupListJson", "?param0=" + up, (str) => {
        this.setState({
          menuInfo: {
            menus: str,
            url: GET_SERVICE_URL.baseUrl, //local URL
            iHousingUrl: GET_SERVICE_URL.iHousingUrl + "iHousing/mainH5.jsp?uid=" + loginid + "&up=" + up, //mainURL
            appName: 'pbUpload'
          }
        });
        saveSession("menu", JSON.stringify(str));
      });
    }
  }

  render() {

    let { fontsize } = this.state;
    return (
      <Provider store={store}>
        <Router history={browserHistory} >
          <div className={fontsize} >
            <Head history={browserHistory} info={this.state} changeFontsize={(fSize) => { this.changeFontsize(fSize) }} />
            <MainRoutes />
          </div>
        </Router>
      </Provider>
    )

  }
}


export default App;
