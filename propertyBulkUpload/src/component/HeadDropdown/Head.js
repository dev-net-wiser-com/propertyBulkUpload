import React, { Component } from 'react';
import './dropdown.scss';
import HeadDropdown from './HeadDropdown';

class Head extends Component{
    constructor(props) {
        super(props);
    }
    changeSize = (fSize,event) =>{
        this.props.changeFontsize(fSize);
    }
    logout= (event) =>{
        let {iHousingUrl} = this.props.info.menuInfo;
        window.location.href=iHousingUrl+"&moduleName=H5_logout";
    }

    render(){
        let {fontsize,loginName,today,platform,menuInfo}=this.props.info;
        return(
            
            <div>
                <div className="app-logo">
                    <img className="logo" src={require("./img/iHousing_Design.gif")} />
                    <div className="app-top">
                    <div className="select-size">
                        <input type="button" className={fontsize==="s-app"?"active":""} onClick={this.changeSize.bind(this,"s-app")} />
                        <input type="button" className={fontsize==="l-app"?"active":""} onClick={this.changeSize.bind(this,"l-app")} />
                        <input type="button" className={fontsize==="e-app"?"active":""} onClick={this.changeSize.bind(this,"e-app")} />
                    </div>
                    <input className="btn-out" type="button" onClick={this.logout} value="Logout"></input>
                    </div>
                    
                    <div className="app-user">
                    <p style={{'text-decoration':'underline'}}>{platform}</p>
                    <p>{loginName}</p>
                    <p>{today}</p>
                    </div>
                </div>
                <div className="grad1"> </div>
                <HeadDropdown history={this.props.history} actions={this.props.actions} menuInfo={menuInfo} />
            </div>
        )
    }

}



export default Head;