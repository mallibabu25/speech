/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  NativeModules, Button ,
  PermissionsAndroid,
  Platform,
  NativeEventEmitter
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';


const Speech = NativeModules.Speech;

const speechEmitter =
  Platform.OS !== 'web' ? new NativeEventEmitter(Speech) : null;

const Section = ({children, title}) => {
  const isDarkMode = useColorScheme() === 'dark';
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
};

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const [result,setResult] = React.useState("");
  const [partialResult,setPartialResult] = React.useState("")


  const requestCameraPermission = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
        {
          title: "Audio Permission",
          message:
            "App needs access to your audio ",
          buttonNeutral: "Ask Me Later",
          buttonNegative: "Cancel",
          buttonPositive: "OK"
        }
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        console.log("You can use the audio");
      } else {
        console.log("audio permission denied");
      }
    } catch (err) {
      console.warn(err);
    }
  };
  React.useEffect(() => {
    requestCameraPermission();
   
  }, []);

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  speechEmitter.addListener("onPartialResult", function(data) {
    console.log("onPartialResult"+JSON.stringify(data))
    setPartialResult(JSON.stringify(data))


});

speechEmitter.addListener("onResult", function(data) {
  console.log("onResult"+JSON.stringify(data))
  setResult(JSON.stringify(data));

})

  const onPress = () => {


    Speech.startListening('testName', (eventId) => {
      console.log(`startListening ${eventId}`);
    });
  };
  
  const stop = async () => {
  await Speech.stopSpeech((eventId) => {
    console.log(`startListening ${eventId}`);
  });
  };



  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <Button
      title="Click to invoke your native module!"
      color="#841584"
      onPress={onPress}
    />
       <Button
      title="Click to invoke your native module!"
      color="#841584"
      onPress={stop}
    />
  <View style={{flex:1}}>
    <Text>result</Text>
    <Text>{result}</Text>
    </View>
    <View style={{flex:1}}>

    <Text>partialResult</Text>
    <Text>{partialResult}</Text>
    </View>

      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
