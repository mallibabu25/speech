import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  NativeModules,
  Button,
  PermissionsAndroid,
  Platform,
  NativeEventEmitter,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import Speech from './Speech';

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const [result, setResult] = React.useState('');
  const [partialResult, setPartialResult] = React.useState('');
  const [isListening, setIsListening] = React.useState(false);

  const requestCameraPermission = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
        {
          title: 'Audio Permission',
          message: 'App needs access to your audio ',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        },
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        console.log('You can use the audio');
      } else {
        console.log('audio permission denied');
      }
    } catch (err) {
      console.warn(err);
    }
  };

  React.useEffect(() => {
    requestCameraPermission();

    Speech.onSpeechResults = onSpeechResults;
    Speech.onSpeechPartialResults = onSpeechPartialResults;
    Speech.onSpeechStart = onSpeechStart;
    Speech.onSpeechEnd = onSpeechEnd;
    return () => {
      Speech.removeAllListeners();
    };
  }, []);

  const onSpeechResults = e => {
    console.log('result ' + JSON.stringify(e));
    setResult(JSON.stringify(e));
  };

  const onSpeechPartialResults = e => {
    console.log('Partial Results ' + JSON.stringify(e));
    setPartialResult(JSON.stringify(e));
  };
  const onSpeechStart = e => {
    setIsListening(true);
    console.log('onSpeechStart ' + JSON.stringify(e));
  };

  const onSpeechEnd = e => {
    setIsListening(false);

    console.log('onSpeechEnd ' + JSON.stringify(e));
  };

  const onPress = () => {
    setIsListening(true);

    Speech.start('eat', 'digits');
  };

  const stop = async () => {
    Speech.stop();
  };

  return (
    <SafeAreaView>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Header />
        <Button
          title="start!"
          color="#841584"
          onPress={onPress}
          style={{marginBottom: 10}}
        />
        <Button
          title="stop!"
          color="#841584"
          onPress={stop}
          style={{margin: 10}}
        />
        <View style={{flex: 1}}>
          <Text>result</Text>
          <Text>{result}</Text>
        </View>
        <View style={{flex: 1}}>
          <Text>partialResult</Text>
          <Text>{partialResult}</Text>
        </View>
        <View style={{flex: 1}}>
          <Text>IsListening</Text>
          <Text>{isListening ? 'true' : 'false'}</Text>
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
