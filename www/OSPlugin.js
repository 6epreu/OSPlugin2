//const cordova = require('cordova');
const exec = require('cordova/exec');

let OSPlugin = {
    showToast: (message, duration, successCallback, errorCallback) => {
        exec(successCallback, errorCallback, 'OSPlugin', 'showToast', [message, duration]);
    },
    coolMethod: (message, successCallback, errorCallback) => {
        alert(message)
        successCallback()
    }
};

module.exports = OSPlugin;



