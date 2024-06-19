import {fromJS} from "immutable";

const initialState = {

};


export default (state = initialState, action) => {

    switch (action.type) {
        case "home/CHANGE_VALUE":
            return fromJS(state).set(action.name,action.value).toJS();
        
        default:
            return state;
    }

};
