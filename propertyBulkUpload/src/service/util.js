
export const isEmpty=(obj) => {
    if (obj == undefined || obj == null || obj == "null" || obj.length == 0 || obj == "undefined")
        return true;
    if (obj === null || obj === undefined) {
        return true
    }

    if (typeof obj === 'number' && isNaN(obj)) {
        return true
    }

    if (obj.length !== undefined) {
        return obj.length === 0
    }

    if (obj instanceof Date) {
        return false
    }

    if (typeof obj === 'object') {
        return Object.keys(obj).length === 0
    }
    return false;
}

export const saveLocal = (name, value)  => {
    if (typeof (value) == "object") {
        value = JSON.stringify(value);
    }
    localStorage[name] = value;
}
export const getLocal = name => {
    let value = localStorage[name];
    if (value == null || value == undefined) return null;
    if (value.indexOf("{") >= 0) {
        value = JSON.parse(value);
    }
    return value;
}
export const saveSession = (name, value) => {
    if(typeof (value) == "object"){
        value = JSON.stringify(value);
    }
    sessionStorage[name] = value;
}
export const getSession = name => {
    let value = sessionStorage[name];
    if (value == null || value == undefined) return null;
    if (value.indexOf("{") >= 0) {
        value = JSON.parse(value);
    }
    return value;
}

/*日期格式转换  yyyy-mm-dd hh:mm:ss 转 dd/mm/yyyy */
export const dateTostring = function(date){
    if(date){
        let _date=new Date(date.replace(/-/,"/"));
        let str =_date.getDate()+"/"+(_date.getMonth()+1)+"/"+_date.getFullYear();
        return str
    }
    return
}
/*日期格式转换*/
export const dateTostring2 = function(date){
    if(date){
        let str =date.getFullYear()+"-"+date.getMonth()+"-"+date.getDay();
        return str
    }
    return
}
export const dateTostring3 = function(str){
    if(str){
        let list = str.split("/");
        let _str = list[2]+"-"+list[1]+"-"+list[0];
        return _str
    }
    return
}
/*日期格式转换 DD/MM/YYYY 转 日期 */
export const stringToDate = function(str){
    if(str && typeof(str)=="string"){
        //let date=new Date(str.replace(/-/,"/"));
        str = str.split("/").reverse().join("/");
        str = str.replace(/-/g, '/');
        let date = new Date(str);
        return date
    }
    return
}


//输入框返回数字
export const toNumber = function(value){
    if(value){
        if(value.substr(0,1)=='-'){
            return '-' + value.replace(/\D/g,'');
        }else{
            return value.replace(/\D/g,'')
        }
    }
    return
}
//输入框限制字节长度
export const byteCheck=(str, maxLen)=>{
    let w = 0;
    let tempCount = 0;
    //length 获取字数数，不区分汉子和英文
    for (let i=0; i<str.length; i++) {
      //charCodeAt()获取字符串中某一个字符的编码
      let c = str.charCodeAt(i);
      //单字节加1
      if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
      w++;
      } else {
      w+=3;
      }
      if (w > maxLen) {
        return false; 
      }
    }
    return true
  } 

export const getHashParameters=()=> {
    //let arr = (window.location.hash || '').split('?')[1].split('&')
    if( window.location.hash.split('?').length>1){
      let arr = window.location.hash.split('?')[1].split('&');
      let params = {}
      for (let i = 0; i < arr.length; i++) {
        let data = arr[i].split('=')
        if (data.length === 2) {
          params[data[0]] = data[1]
        }
      }
      return params
    }
    return null
}
export const getUrlParam=(name) => {
    let params = getHashParameters();
    if(params){
      return params[name]
    }
    return null
}
export const getUrlCode=()=>{
    if( window.location.hash.split('?').length>1){
        let index = window.location.hash.indexOf("?ps=")+4;
        let params = window.location.hash.substr(index);
        return params
      }
      return null
}
export const r_n = (text, map) => {
    // Generic ROT-n algorithm for keycodes in MAP.
    var R = new String()
    var i, j, c, len = map.length
    for(i = 0; i < text.length; i++) {
      c = text.charAt(i)
      j = map.indexOf(c)
      if (j >= 0) {
        c = map.charAt((j + len / 2) % len)
      }
      R = R + c
    }
    return R;
  }
  
export const  rDecode = (text,time) => {
    // Hides all ASCII-characters from 33 ("!") to 126 ("~").  Hence can be used
    // to obfuscate virtually any text, including URLs and emails.
    var R = new String()
    R = r_n(text,
    "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~")
    return R;
}
export const getParameters=(str) => {
    let paramsStr="";
    if(str != null && str !=""){
        paramsStr = rDecode(str);
    }else{return null}
    let arr = paramsStr.split('&');
    let params = {}
      for (let i = 0; i < arr.length; i++) {
        let data = arr[i].split('=')
        if (data.length === 2) {
          params[data[0]] = data[1]
        }
      }
    return params
}

export const ticksTime = (ticks,time=1000*60*60*24*365*2) => {
    let a = new Date().getTime()/1000; 
    if(a - ticks <= time/1000){
        return true 
    } 
    return false
}
let uid = Date.now()
export default function nextUid () {
  return (uid++).toString(36)
}

export const orderByDatalist = [
    { value: "F", desc: "P-Form No." },
    { value: "P", desc: "Priority No." },
    { value: "I", desc: "IPMS No." },
  ];
export const formatStr = "DD/MM/YYYY";
//数字转money格式
export function   formatNum(num)
{   
    num = num.toString();
    num = num.replace(/,/g, "");
    if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(num)) {return   false}   
    var   a   =   RegExp.$1,   b   =   RegExp.$2,   c   =   RegExp.$3;   
    var   re   =   new   RegExp().compile("(\\d)(\\d{3})(,|$)");   
    while(re.test(b))   b   =   b.replace(re,   "$1,$2$3");   
    return   a   +""+   b   +""+   c;   

}
//money格式转数字
export function   moneyToNum(money)
{   
    money = money.toString();
    money = money.replace(/,/g, ""); 
    if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(money)) {return   0} 
    return money
}
//返回日期类型
export function getDateType(type){
    switch (type) {
        case "Y":
            return "yyyy"
        case "M":
            return "MM/yyyy"
        case "D":
            return "dd/MM/yyyy"
            break;
    
        default:
            return "dd/MM/yyyy"
            break;
    }
}
//返回日期类型
export function getDateType2(type){
    switch (type) {
        case "Y":
            return "YYYY"
        case "M":
            return "MM/YYYY"
        case "D":
            return "DD/MM/YYYY"
            break;
    
        default:
            return "DD/MM/YYYY"
            break;
    }
}
//返回Relationship
export function getRelationshipDesc(value,relationShipList){
    let label="";
    if(!isEmpty(value)){
        relationShipList.map((x,i)=>{
            if(x.value==value){
                label = x.label
            }
        });
    }
    return label
}

export const checkHKIDcard=(str)=> {
    var reg = /^[A-Z]{1,2}[0-9]{6}\([0-9A]\)$/;
    var reg1 = /^[A-Z]{1,2}[0-9]{6}[0-9A]$/;
    if(!str.match(reg) && !str.match(reg1)) return false;

    var temp = str.replace(/[\(\)]/g, '');

    var len = temp.length;
    var sum = (len === 9) 
              ? 9 * (temp[0].charCodeAt() - 64) + 8 * (temp[1].charCodeAt() - 64)
              : 8 * (temp[0].charCodeAt() - 64);

    var arr = temp.split('').reverse().join('').substr(1, 6);
    var arrResult = [2, 3, 4, 5, 6, 7];
    
    for(var i = 0; i< arr.length; i++) {
        sum += Number(arr[i]) * arrResult[i];
    }

    var mod = sum % 11;
    var lastCode = temp[len -1];
    if(mod === 0 && Number(lastCode) === 0 ) return true;
    
    var checkCode = 11 - mod;
    if(checkCode === 10 && lastCode === 'A') return true;
    
    if(checkCode > 0 && checkCode < 10 && Number(lastCode) === checkCode) return true;
    return false;
}

export const checkFloat = (value, val1, val2) => {
    let val, strVal2, arrVal2, arrInput;
    if (isEmpty(value)) {
        return true
    }
    val = om(value) + "";
    strVal2 = val2 + "";
    arrInput = val.split(".");
    arrVal2 = strVal2.split(".");
    if (arrVal2.length < arrInput.length) {
        return false
    }
    if (arrVal2.length == 2 && arrInput.length == 2) {
        if (arrVal2[1].length < arrInput[1].length)
            return false
    }
    if (isFloat(val)) {
        if ((val1 <= Number(val)) && (Number(val) <= val2)) {
            return true;
        }
        else {
            return false
        }
    }
    else {
        return false
    }
}

export const om = (s) => {
    let s1;
    let t = "";
    if (s) {
        for (let i = 0; i < s.length; i++) {
            s1 = s.substring(i, i + 1);
            if (s1 != ",")
                t += s1;
        }
    }
    return t;
}

export const em = (s) => {
    let s1, t = "", rlen, k, sign;
    if (!isEmpty(s)) {
        s = om(s);
        rlen = -1;
        for (let i = 0; i < s.length; i++) {
            s1 = s.substring(i, i + 1);
            if (s1 == ".") {
                rlen = i;
                break
            }
        }
        if (rlen != -1) {
            t = s.substring(rlen, s.length);
            s = s.substring(0, rlen);
        }
        k = 0;
        sign = s.substring(0, 1);
        if (sign === "-") {
            s = s.substring(1, s.length);
        }
        if (s.length <= 3) {
            t = s + t;
        } else {
            for (let i = s.length - 1; i >= 0; i--) {
                s1 = s.substring(i, i + 1);
                if (k % 3 == 0 && k > 0 && (i == 0 && s1 == "-")) {
                    t = s1 + "," + t;
                } else {
                    t = s1 + t;
                }
                k++
            }
        }
        if (sign == "-") {
            t = "-" + t;
        }
    }
    return t
}

export const ms = (x) => {
    let v, ln, lx;
    x = Number(x || "0");
    lx = 0.00 + x;
    if (x < 0) {
        x = -x;
    }
    v = Math.round((x + 0.001) * 100) + "";
    ln = v.length;
    for (let i = 0; i < 3 - ln; i++) {
        v = "0" + v;
    }
    ln = v.length;
    v = v.substring(0, ln - 2) + "." + v.substring(ln - 2, ln);
    if (lx < 0) {
        v = "-" + v;
    }
    return v;

}

export const isFloat = (s) => {
    let seenDecimalPoint = false;
    if (isEmpty(s)) {
        return false
    }
    if (s === ".") {
        return false;
    }
    for (let i = 0; i < s.length; i++) {
        let c = s.charAt(i);
        if (i === 0 && c === "-") {

        } else {
            if (c === "." && !seenDecimalPoint) {
                seenDecimalPoint = true;
            } else if (!isDigit(c)) {
                return false
            }
        }
    }
    return true
}


export const isDigit = (s) => {
    let regu=/^([1-9]*[0-9]*)$/;
    if (regu.test(s))
    {
       return true;
    }
    else
    {
       return false;
    }
}

export const formatPrice = (price) => {
    return String(price).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}