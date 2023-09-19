<template>
  <div>
    <n-config-provider :theme-overrides="themeOverrides">
      <Header></Header>
      <n-message-provider>
        <RouterView></RouterView>
      </n-message-provider>
    </n-config-provider>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
import Header from './components/Header.vue';
const themeOverrides = {
  // common: {
  //   // primaryColor: "#ff9292", //方法一：总体设置
  //   // borderHover: '1px solid #2d50d3'
  // },
  // Select: {
  //   peers: {
  //     InternalSelection: {
  //       borderHover: '1px solid #2d50d3',
  //       borderActive: '1px solid #2d50d3',
  //       borderFocus: '1px solid #2d50d3',
  //       loadingColor: '#2d50d3',
  //       caretColor: '#2d50d3',
  //       boxShadowFocus: '#2d50d3',
  //       boxShadowActive: '0 0 0 2px #99acf0',
  //     },
  //     InternalSelectMenu: {
  //       optionTextColorActive: '#2d50d3',
  //       optionCheckColor: '#2d50d3',
  //       loadingColor: '#2d50d3',
  //       optionTextColorPressed: '#2d50d3'
  //     }
  //   }
  // },
  // Input: {
  //   borderHover: '1px solid #2d50d3',
  //   borderActive: '1px solid #2d50d3',
  //   borderFocus: '1px solid #2d50d3',
  //   loadingColor: '#2d50d3',
  //   caretColor: '#2d50d3',
  //   boxShadowFocus: '0 0 0 2px #99acf0',
  //   boxShadowActive: '0 0 0 2px #99acf0',
  // },
  // Button:{
  //   borderHover: '1px solid #2d50d3',
  //   textColorFocus: '#2d50d3',
  //   textColorHover: '#2d50d3',
  //   rippleColor: '#2d50d3',
  //   textColorPressed: '#2d50d3',
  //   draggerBorderHover: '#2d50d3',
  //   borderFocus: '1px solid #2d50d3',
  // },
  // Menu:{
  //   itemHeight: '55px'
  // }
}

</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
}
</style>