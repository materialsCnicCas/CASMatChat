<template>
  <div>
    <div class="imgs">
      <n-carousel autoplay>
        <img class="carousel-img" src="../assets/imgs/OIP-C2.jpg" />
        <img class="carousel-img" src="../assets/imgs/OIP-C3.jpg" />
        <img class="carousel-img" src="../assets/imgs/R-C1.jpg" />
        <img class="carousel-img" src="../assets/imgs/R-C2.jpg" />
        <template #dots="{ total, currentIndex, to }">
          <ul class="custom-dots">
            <li v-for="index of total" :key="index" :class="{ ['is-active']: currentIndex === index - 1 }"
              @click="to(index - 1)" />
          </ul>
        </template>
      </n-carousel>
      <!-- <div class="opacitcy"> -->
        <div class="text">
        <!-- <div class="header">材料智能设计与创新服务云平台</div> -->
        <div class="header">
          Material Intelligent Design and Innovation Service Cloud Platform
        </div>
        <!-- 平台目标：以信息化技术为基础，集材料模拟、数据管理与应用于一体的材料智能化设计平台
          <br /> -->
          <div class="twotitle">
          An Intelligent material design platform based on information
          technology,<br />
          integrating material simulation, data collection, data management and applications
        </div>
        <!-- 平台功能：材料模拟、材料数据、人工智能模型<br /> -->
        <!-- Material Simulation, Material Data, Material AI Model -->
      </div>
      <!-- </div> -->
    </div>
    <div class="content">
      <div class="left">
        <h1 v-if="router.currentRoute.value.query.id == 1">Material Simulation</h1>
        <h1 v-if="router.currentRoute.value.query.id == 2">Material Data</h1>
        <n-menu :options="options" default-value="0" v-model:value="active" @update:value="handleUpdateValue" />
        <!-- <n-menu v-if="router.currentRoute.value.query.id == 1" :options="options" default-value="0" @update:value="handleUpdateValue"/> -->
        <!-- <n-menu v-if="router.currentRoute.value.query.id == 2" :options="options" default-value="0" @update:value="handleUpdateValue"/> -->
      </div>
      <!-- <n-divider vertical /> -->
      <div class="right">
        <h4 class="title">{{ options[activeText].label }}</h4>
        <p class="cont">{{ options[activeText].text }}</p>
        <div class="btn">
          <!-- <n-button type="info" @click="detail(options[activeText].url)">Detail</n-button> -->
          <n-button type="error" @click="detail(options[activeText].url)">Detail</n-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const options = ref([]);
const activeText = ref(0)
const active = ref('0')
const handleUpdateValue = (key, i) => {
  activeText.value = key
}

const detail = (url) => {
  if (url !== '' && url !== undefined && url !== '/collection') {
    window.location.href = url
  } else if (url == '/collection') {
    sessionStorage.setItem('activeTab','/collection')
    router.push({ path: '/collection' })
  }
}



const router = useRouter()
watch(() => router.currentRoute.value.path, (toPath) => {
  activeText.value = 0
  active.value = '0'
  if (router.currentRoute.value.query.id == 1) {
    options.value = [
      {
        label: "CrysPAI",
        img: "./imgs/header.png",
        text: "A crystal structure prediction package based on artificial intelligence (CrySPAI)",
        url: "http://mat.aicnic.cn/cryspai/index",
        key: "0"
      },
      {
        label: "TDAP",
        img: "./imgs/header1.png",
        text: "Time Dependent Ab-initio Package",
        // url: "http://mat.aicnic.cn/simulation/cryspai",
        key: "1"
      },
    ]
  } else if (router.currentRoute.value.query.id == 2) {
    options.value = [
      {
        label: "Materials Data Collection",
        img: "./imgs/header.png",
        text: "A data collection software for material science, building database for any calculation software.",
        // url: "http://mat.aicnic.cn/simulation/cryspai",
        url: "/collection",
        key: "0"
      },
      {
        label: "DataBase",
        img: "./imgs/header1.png",
        text: "Material database facilitates materials scientific data sharing and application.",
        url: "http://mat.aicnic.cn/search",
        key: "1"
      },
    ]
  }
  // else if (router.currentRoute.value.query.id == 3) {
  //   options.value = [{
  //     label: "DataBase",
  //     img: "./imgs/header.png",
  //     text: "Material database facilitates materials scientific data sharing and application.",
  //     url: "http://mat.aicnic.cn/data/search",
  //     key: "0"
  //   }]
  // }
}, { immediate: true, deep: true })
</script>

<style lang="scss" scoped>
:deep(.n-menu .n-menu-item-content.n-menu-item-content--selected .n-menu-item-content-header) {
  color: #fff;
  font-size: 16px;
}

:deep(.n-menu .n-menu-item-content:not(.n-menu-item-content--disabled).n-menu-item-content--selected:hover .n-menu-item-content-header) {
  color: #fff;
}

:deep(.n-menu .n-menu-item-content.n-menu-item-content--selected::before) {
  background-color: #f30000;
}

:deep(.n-menu .n-menu-item-content:not(.n-menu-item-content--disabled).n-menu-item-content--selected:hover::before) {
  background-color: #f30000;
}
:deep(.n-menu-item-content){
  background-color: #eeeeee;
}
:deep(.n-menu .n-menu-item-content::before){
  left: 0;
  right: 0;
}
.content {
  display: flex;
  margin: 2rem 20rem;

  .left {
    flex: 1;
  }

  .right {
    flex: 3;
    padding: 2rem;
    border: 1px solid #efefef;
    margin-top: 2rem;
    margin-left: 2rem;

    .title {
      margin-bottom: 1rem;
    }

    .btn {
      display: flex;
      flex-direction: row-reverse;
    }
  }
}


.imgs {
  position: relative;
  width: 100%;
  height: 50vh;
  background: #000;
}

.text {
  position: absolute;
  top: 30%;
  left: 10%;
  right: 10%;
  /* transform: translate(-75%,-90%); */
  color: #fff;
  user-select: none;
  font-size: 18px;
}

.header {
  font-size: 50px;
}

.twotitle{
  // margin: 0 auto;
  text-align: center;
  // margin-top: 2rem;
}


.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 0.6;
}

.custom-dots {
  display: flex;
  margin: 0;
  padding: 0;
  position: absolute;
  bottom: 60px;
  left: 60px;
}

.custom-dots li {
  display: inline-block;
  width: 12px;
  height: 4px;
  margin: 0 3px;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.4);
  transition: width 0.3s,
    background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}

.custom-dots li.is-active {
  width: 40px;
  background: #f30000;
}
</style>