import React, { Component } from 'react';
import './dropdown.scss';
import $ from "jquery";
import DropMenu from './DropMenu';


class HeadDropdown extends Component {
  
  constructor(props) {
    super(props);
    this.state=this.props.menuInfo;
  }
  generateMenu=(menuObj)=>{let list = [];if (menuObj && typeof (menuObj) != 'object') menuObj=JSON.parse(menuObj);menuObj.forEach((item)=>{if(item.menuitem.submenus){let list2=[];{item.menuitem.submenus.forEach((element)=>{list2.push(<DropMenu key={element.menuitem.id} menu={element} mName={item.menuitem.moduleName} history={this.props.history} url={this.state.url} iHousingURL={this.state.iHousingUrl} appName={this.state.appName} />);})}list.push(<li className="drop-button" key={item.menuitem.id}><div className="dropdown-root"><div className="Dropdown-control" ><div className="Dropdown-placeholder" ><a>{item.menuitem.label}</a></div><div className="Dropdown-arrow-wrapper"><span className="Dropdown-arrow"></span></div></div></div><ul>{list2}</ul></li>);}else{list.push(<li className="drop-button" key={item.menuitem.id}><div className="dropdown-root"><div className="Dropdown-control" ><div className="Dropdown-placeholder" ><a>{item.menuitem.label}</a></div><div className="Dropdown-arrow-wrapper"><span className="Dropdown-arrow"></span></div></div></div></li>);}});return list;} 
  componentDidMount(){$(window["\x64\x6f\x63\x75\x6d\x65\x6e\x74"])['\x63\x6c\x69\x63\x6b'](function(ZRazcO1){var aI$2=$('\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x3e\x6c\x69');if(!aI$2['\x69\x73'](ZRazcO1['\x74\x61\x72\x67\x65\x74'])&&aI$2['\x68\x61\x73'](ZRazcO1['\x74\x61\x72\x67\x65\x74'])['\x6c\x65\x6e\x67\x74\x68']===0){$('\x2e\x79\x79\x75\x69\x5f\x6d\x65\x6e\x75 \x3e\x6c\x69 \x75\x6c')['\x68\x69\x64\x65']()}});}

  render() {
    let {menus} = this.state;
    console.log(menus);
    return (
    <ul className="grad2 yyui_menu">
        {
            this.generateMenu(menus)
        }
        
        
      </ul>

    )
  }
}


export default HeadDropdown;
