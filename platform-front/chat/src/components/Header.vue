<template>
  <div>
    <div class="tabs">
      <n-tabs type="line" animated v-model:value="activeTab">
        <n-tab-pane name="Home" tab="Home"></n-tab-pane>
        <n-tab-pane name="Material Simulation" tab="Material Simulation"></n-tab-pane>
        <n-tab-pane name="Material Data" tab="Material Data"></n-tab-pane>
        <n-tab-pane name="Material AI Model" tab="Material AI Model"></n-tab-pane>
        <n-tab-pane name="About" tab="About"></n-tab-pane>
        <n-tab-pane name="Chat" tab="Chat"></n-tab-pane>
        <!-- <n-tab-pane name="Materials Data Collection" tab="Materials Data Collection"></n-tab-pane> -->
      </n-tabs>
    </div>
  </div>
</template>
  
<script setup>
import { useRouter } from 'vue-router';
const activeTab = ref("Home");
onMounted(() => {
  if (sessionStorage.getItem('activeTab') == null) {
    activeTab.value = 'Home'
  } else {
    activeTab.value = sessionStorage.getItem('activeTab');
  }
})
const router = useRouter()
watch(activeTab, (newval, oldval) => {
  if (activeTab.value == 'Material AI Model') {
    sessionStorage.setItem('activeTab', 'Home')
  } else {
    sessionStorage.setItem('activeTab', activeTab.value)
  }
  if (newval == 'Material Simulation') {
    router.push({ path: '/about', query: { id: 1 } })
  } else if (newval == 'Material Data') {
    router.push({ path: '/about', query: { id: 2 } })
  } else if (newval == 'Material AI Model') {
    window.location.href = 'http://mat.aicnic.cn/idip'
    // window.open('http://mat.aicnic.cn/idip')
  } else if (newval == 'Home') {
    router.push({ path: '/' })
  } else if (newval == 'Chat') {
    // router.push({ path: '/chat', query: { pid: '1' } })
    router.push({ path: '/onchat' })
  }
  // else if (newval == 'Materials Data Collection') {
  //   router.push({ path: '/collection' })
  // }
})
watch(() => router.currentRoute.value.path, (toPath) => {
  if (router.currentRoute.value.query.id == 1) {
    activeTab.value = 'Material Simulation'
  } else if (router.currentRoute.value.query.id == 2 && router.currentRoute.value.path == '/about') {
    activeTab.value = 'Material Data'
  } else if (router.currentRoute.value.path == '/') {
    activeTab.value = 'Home'
  }
}, { immediate: true, deep: true })
</script>
  
<style lang="scss">
.tabs {
  // padding: 0.5rem 15rem;
  padding: 0 15rem;
  height: 70px;
  user-select: none;
  border-bottom: 1px solid #efeff5;
  box-sizing: border-box;
}

.n-tabs .n-tabs-tab {
  font-size: 20px;
}

.n-tabs.n-tabs--top .n-tab-pane {
  padding: 0;
}

.n-tabs .n-tabs-bar {
  background-color: #f30000;
}

.n-tabs.n-tabs--line-type .n-tabs-tab.n-tabs-tab--active,
.n-tabs.n-tabs--bar-type .n-tabs-tab.n-tabs-tab--active {
  color: #f30000;
}

.n-tabs .n-tabs-nav.n-tabs-nav--line-type.n-tabs-nav--top .n-tabs-nav-scroll-content {
  padding: 0.5rem 0;
}

.n-tabs.n-tabs--line-type .n-tabs-tab:hover,
.n-tabs.n-tabs--bar-type .n-tabs-tab:hover {
  color: #f30000;
}

.n-tabs .n-tabs-nav.n-tabs-nav--line-type.n-tabs-nav--top .n-tabs-nav-scroll-content {
  border: none;
}
</style>