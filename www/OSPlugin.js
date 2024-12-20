//const cordova = require('cordova');
const exec = require('cordova/exec');

let OSPlugin = {
    showToast: (message, duration, successCallback, errorCallback) => {
        exec(successCallback, errorCallback, 'OSPlugin', 'showToast', [message, duration]);
    },
    coolMethod: (message, successCallback, errorCallback) => {
        alert(message)
        successCallback()
    },
    sumMethod: (arg1, arg2, successCallback, errorCallback) => {
        successCallback(arg1 + arg2)
    },
    set: function (key, value, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "OSPlugin", "set", [key, value]);
    },
    get: function (key, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "OSPlugin", "get", [key]);
    }
};

module.exports = OSPlugin;



