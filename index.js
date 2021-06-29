/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';

AppRegistry.registerComponent(appName, () => App);

// import React from 'react';
// import { NativeModules, NativeEventEmitter, Platform } from 'react-native';

// const Speech = NativeModules.Speech;

// const speechEmitter =
//   Platform.OS !== 'web' ? new NativeEventEmitter(Speech) : null;

// class RNSpeech {
//   _loaded = false;
//   _listeners = null;
//   _events = {}

//   constructor() {
//     this._loaded = false;
//     this._listeners = null;
//     this._events = {
//       onSpeechStart: () => {},
//       onSpeechEnd: () => {},
//       onSpeechResults: () => {},
//       onSpeechPartialResults: () => {},
//     };
//   }

//   removeAllListeners() {
//     Voice.onSpeechStart = undefined;
//     Voice.onSpeechEnd = undefined;
//     Voice.onSpeechResults = undefined;
//     Voice.onSpeechPartialResults = undefined;
//   }


//   start() {
//     if (!this._loaded && !this._listeners && voiceEmitter !== null) {
//       this._listeners = (Object.keys(this._events)).map(
//         (key) => speechEmitter.addListener(key, this._events[key]),
//       );
//     }

//     return new Promise((resolve, reject) => {
//       const callback = (error) => {
//         if (error) {
//           reject(new Error(error));
//         } else {
//           resolve();
//         }
//       };
//       if (Platform.OS === 'android') {
//         Speech.startListening('testName', callback);
//         }
//     });
//   }
//   stop() {
//     if (!this._loaded && !this._listeners) {
//       return Promise.resolve();
//     }
//     return new Promise((resolve, reject) => {
//         Speech.stopSpeech(error => {
//         if (error) {
//           reject(new Error(error));
//         } else {
//           resolve();
//         }
//       });
//     });
//   }




//   set onSpeechStart(fn) {
//     this._events.onSpeechStart = fn;
//   }


//   set onSpeechEnd(fn) {
//     this._events.onSpeechEnd = fn;
//   }

//   set onSpeechResults(fn) {
//     this._events.onSpeechResults = fn;
//   }
//   set onSpeechPartialResults(fn) {
//     this._events.onSpeechPartialResults = fn;
//   }
// }


// export default new RNSpeech();
