import { combineReducers } from 'redux';
import homeReducer from './homeReducer';
import propertyBulkUploadInfoReducer from './propertyBulkUploadInfoReducer';

const rootReducer = combineReducers({
    HomeInfo:homeReducer,
    propertyBulkUploadInfo:propertyBulkUploadInfoReducer
});

export default rootReducer;
