import React, { Component } from "react";
import "./dropdown.scss";
import $ from "jquery";

class DropMenu extends Component{
    goNext=(path,mName,event)=>{        event["\x70\x72\x65\x76\x65\x6e\x74\x44\x65\x66\x61\x75\x6c\x74"]();        let tag=true;        if(window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x61\x73\x68"]["\x73\x70\x6c\x69\x74"]("\x2f")["\x6c\x65\x6e\x67\x74\x68"]>1 && (window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x61\x73\x68"]["\x73\x70\x6c\x69\x74"]("\x2f")[1]!="" && window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x61\x73\x68"]["\x73\x70\x6c\x69\x74"]("\x2f")[1]!=null) && mName==this["\x70\x72\x6f\x70\x73"]["\x61\x70\x70\x4e\x61\x6d\x65"])        {            tag = window["\x63\x6f\x6e\x66\x69\x72\x6d"]('\x41\x72\x65 \x79\x6f\x75 \x73\x75\x72\x65 \x79\x6f\x75 \x77\x61\x6e\x74 \x74\x6f \x6c\x65\x61\x76\x65 \x74\x68\x69\x73 \x70\x61\x67\x65\x3f');        }        if(tag){            let host = "";             let arr = path["\x73\x70\x6c\x69\x74"]("\x40");            let module = path;            if(arr[0]){                module = arr[0];            }            if (arr[1]) {                host=arr[1];            }			            let param = this["\x67\x65\x74\x53\x65\x73\x73\x69\x6f\x6e"]("\x70\x73");            let loginid = "";            let up = "";            let platform="";            if (!this["\x69\x73\x45\x6d\x70\x74\x79"](param)) {                let parameters = this["\x67\x65\x74\x50\x61\x72\x61\x6d\x65\x74\x65\x72\x73"](decodeURIComponent(param));                loginid = parameters["\x6c\x6f\x67\x69\x6e\x69\x64"];                up = parameters["\x75\x70"];                 if(parameters["\x70\x6c\x61\x74\x66\x6f\x72\x6d"]){                  platform=parameters["\x70\x6c\x61\x74\x66\x6f\x72\x6d"];                }              }            if(module["\x73\x75\x62\x73\x74\x72\x69\x6e\x67"](0,3)=="\x48\x35\x5f"){                let pl=[];                pl = module["\x73\x70\x6c\x69\x74"]("\x5f");                if(pl[1] && pl[1]==this["\x70\x72\x6f\x70\x73"]["\x61\x70\x70\x4e\x61\x6d\x65"]){                    this["\x70\x72\x6f\x70\x73"]["\x68\x69\x73\x74\x6f\x72\x79"]["\x70\x75\x73\x68"]({pathname:'\x2f'+module});                }else if(pl[1]){                    if(!this["\x69\x73\x45\x6d\x70\x74\x79"](host) && host != window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x6f\x73\x74\x6e\x61\x6d\x65"] ){                        window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x72\x65\x66"]= "\x68\x74\x74\x70\x3a\x2f\x2f"+host+"\x3a\x38\x30\x38\x30\x2f\x69\x48\x6f\x75\x73\x69\x6e\x67\x48\x35\x2f\x69\x6e\x64\x65\x78\x2e\x68\x74\x6d\x6c\x3f\x70\x73\x3d"+this["\x67\x65\x74\x53\x65\x73\x73\x69\x6f\x6e"]("\x70\x73")+"\x26\x6c\x6f\x67\x69\x6e\x69\x64\x3d"+loginid+"\x26\x75\x70\x3d"+up+"\x26\x6d\x6f\x64\x75\x6c\x65\x4e\x61\x6d\x65\x3d"+module;                    }else{                        window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x72\x65\x66"]=this["\x70\x72\x6f\x70\x73"]["\x75\x72\x6c"]+pl[1]+"\x2f\x23\x2f"+module;                    }                }else{                    this["\x70\x72\x6f\x70\x73"]["\x68\x69\x73\x74\x6f\x72\x79"]["\x70\x75\x73\x68"]({pathname:'\x2f'+module});                }            }else{                if(!this["\x69\x73\x45\x6d\x70\x74\x79"](host) && host != window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x6f\x73\x74\x6e\x61\x6d\x65"] ){                    window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x72\x65\x66"]="\x68\x74\x74\x70\x3a\x2f\x2f"+host+"\x3a\x38\x30\x38\x30\x2f\x69\x48\x6f\x75\x73\x69\x6e\x67\x2f\x6d\x61\x69\x6e\x48\x35\x2e\x6a\x73\x70\x3f\x75\x69\x64\x3d" + loginid + "\x26\x75\x70\x3d" + up+"\x26\x6d\x6f\x64\x75\x6c\x65\x4e\x61\x6d\x65\x3d"+module;                }                else{                    window["\x6c\x6f\x63\x61\x74\x69\x6f\x6e"]["\x68\x72\x65\x66"]=this["\x70\x72\x6f\x70\x73"]["\x69\x48\x6f\x75\x73\x69\x6e\x67\x55\x52\x4c"]+"\x26\x6d\x6f\x64\x75\x6c\x65\x4e\x61\x6d\x65\x3d"+module;                }                            }        }                $("\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x6c\x69")["\x63\x68\x69\x6c\x64\x72\x65\x6e"]("\x75\x6c")["\x68\x69\x64\x65"]();    };        getSession = name => {        let value = sessionStorage[name];        if (value == null || value == undefined) return null;        if (value["\x69\x6e\x64\x65\x78\x4f\x66"]("\x7b") >= 0) {            value = JSON["\x70\x61\x72\x73\x65"](value);        }        return value;    };    saveSession = (name, value) => {        if(typeof (value) == "\x6f\x62\x6a\x65\x63\x74"){            value = JSON["\x73\x74\x72\x69\x6e\x67\x69\x66\x79"](value);        }        sessionStorage[name] = value;    };        isEmpty = str => {        if (str == undefined || str == null || str == "\x6e\x75\x6c\x6c" || str["\x6c\x65\x6e\x67\x74\x68"] == 0 || str == "\x75\x6e\x64\x65\x66\x69\x6e\x65\x64")            return true;        return false;    };    getParameters = (str) => {        let paramsStr="";        if(str != null && str !=""){            paramsStr = this["\x72\x44\x65\x63\x6f\x64\x65"](str);        }else{return null}        let arr = paramsStr["\x73\x70\x6c\x69\x74"]('\x26');        let params = {};          for (let i = 0; i < arr["\x6c\x65\x6e\x67\x74\x68"]; i++) {            let data = arr[i]["\x73\x70\x6c\x69\x74"]('\x3d');            if (data["\x6c\x65\x6e\x67\x74\x68"] === 2) {              params[data[0]] = data[1];            }          }        return params;    };    rDecode = (text,time) => {        let R = new window["\x53\x74\x72\x69\x6e\x67"]();        R = this["\x72\x5f\x6e"](text,        "\x21\"\x23\x24\x25\x26\x27\x28\x29\x2a\x2b\x2c\x2d\x2e\x2f\x30\x31\x32\x33\x34\x35\x36\x37\x38\x39\x3a\x3b\x3c\x3d\x3e\x3f\x40\x41\x42\x43\x44\x45\x46\x47\x48\x49\x4a\x4b\x4c\x4d\x4e\x4f\x50\x51\x52\x53\x54\x55\x56\x57\x58\x59\x5a\x5b\\\x5d\x5e\x5f\x60\x61\x62\x63\x64\x65\x66\x67\x68\x69\x6a\x6b\x6c\x6d\x6e\x6f\x70\x71\x72\x73\x74\x75\x76\x77\x78\x79\x7a\x7b\x7c\x7d\x7e");        return R;    };    r_n = (text, map) => {        let R = new window["\x53\x74\x72\x69\x6e\x67"]();        let i, j, c, len = map["\x6c\x65\x6e\x67\x74\x68"];        for(i = 0; i < text["\x6c\x65\x6e\x67\x74\x68"]; i++) {          c = text["\x63\x68\x61\x72\x41\x74"](i);          j = map["\x69\x6e\x64\x65\x78\x4f\x66"](c);          if (j >= 0) {            c = map["\x63\x68\x61\x72\x41\x74"]((j + len / 2) % len);          }          R = R + c;        }        return R;      };    componentDidMount(){        $(window["\x64\x6f\x63\x75\x6d\x65\x6e\x74"])["\x72\x65\x61\x64\x79"](function(){          $("\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x6c\x69 \x75\x6c \x6c\x69")["\x68\x6f\x76\x65\x72"](function(){            $(this)["\x73\x69\x62\x6c\x69\x6e\x67\x73"]()["\x63\x68\x69\x6c\x64\x72\x65\x6e"]("\x75\x6c")["\x68\x69\x64\x65"]();            $(this)["\x63\x68\x69\x6c\x64\x72\x65\x6e"]("\x75\x6c")["\x73\x68\x6f\x77"]();           });          $("\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x6c\x69")["\x63\x6c\x69\x63\x6b"](function(Hs$1){            $("\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x6c\x69 \x75\x6c")["\x68\x69\x64\x65"]();            $(this)["\x63\x68\x69\x6c\x64\x72\x65\x6e"]("\x75\x6c")["\x73\x68\x6f\x77"]();          });        });      };
 

    render(){
        let menu = this.props.menu;
        let mName = this.props.mName;
        let history = this.props.history;
        let url = this.props.url;
        let iHousingURL = this.props.iHousingURL;
        let appName = this.props.appName;
        
        if(menu.menuitem.submenus && menu.menuitem.submenus.length>0){
            const list=[];
            menu.menuitem.submenus.forEach((item)=>{
                list.push  (<DropMenu menu={item} mName={mName} iHousingURL={iHousingURL} appName={appName} url={url} history={history} />);
            })
            return(
                <li key={menu.menuitem.id} >
                    <a className="Dropdown-option">{menu.menuitem.label}</a>
                    <ul className="Dropdown-menu">
                        {list}
                    </ul>
                    <span className="Dropdown-arrow2"></span>
                </li>
            )
        }else{
            return(
                <li key={menu.menuitem.id}   onClick={this.goNext.bind(this,menu.menuitem.moduleName,mName)}>
                    <a className="Dropdown-option"  >{menu.menuitem.label}</a>
                </li>
            )
        }
        
    }

};



export default DropMenu;