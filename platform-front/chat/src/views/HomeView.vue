<template>
  <div>
    <!-- <div class="tabs">
      <n-tabs type="line" animated v-model:value="activeTab">
        <n-tab-pane name="Material Simulation" tab="Material Simulation"></n-tab-pane>
        <n-tab-pane name="Material Data" tab="Material Data"></n-tab-pane>
        <n-tab-pane name="Material AI Model" tab="Material AI Model"></n-tab-pane>
      </n-tabs>
    </div> -->
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
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
const activeTab = ref("");
watch(activeTab,(newval,oldval)=>{
  router.push({path:'/about'})
})
</script>

<style lang="scss" scoped>
.imgs {
  position: relative;
  width: 100%;
  height: calc(100vh - 70px);
  background: #000;
}

.text {
  position: absolute;
  top: 30%;
  // left: 10%;
  /* transform: translate(-75%,-90%); */
  color: #fff;
  user-select: none;
  font-size: 18px;
  left: 10%;
  right: 10%;
}

.header {
  font-size: 50px;
}

.twotitle{
  // margin: 0 auto;
  text-align: center;
  margin-top: 2rem;
}

.carousel-img {
  width: 100%;
  /* height: calc(100vh - 74px); */
  height: 100%;
  object-fit: cover;
  opacity: 0.6;
  /* filter: alpha(opacity=60); */
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