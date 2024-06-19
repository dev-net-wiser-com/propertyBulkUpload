import React from 'react';
import {Route,Switch} from 'react-router';
import Home from '../view/home';
import Index from '../view/index';
import propertyBulk from '../view/propertyBulkUpload';

const MainRoutes = () => {
    return(
        <Switch>
            <Route exact path="/" component={Index} />
            <Route path="/H5_pbUpload_home" component={Home} />
            <Route path="/H5_pbUpload_probulk" component={propertyBulk} />
        </Switch>
    )
}
export default MainRoutes;
