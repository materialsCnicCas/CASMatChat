<template>
    <n-space vertical class="chat">
        <n-layout has-sider>
            <n-layout-sider :native-scrollbar="false" bordered collapse-mode="width" :collapsed-width="0" :width="260"
                :collapsed="collapsed" class="layout">
                <!-- <n-layout-sider :native-scrollbar="false" bordered collapse-mode="width" :collapsed-width="0" :width="260"
                :collapsed="collapsed" show-trigger @collapse="collapsed = true" @expand="collapsed = false" class="layout"> -->
                <!-- <div class="add">
                    <n-button @click="add">
                        <template #icon>
                            <n-icon size="20">
                                <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-ea893728="">
                                    <path fill="currentColor"
                                        d="M480 480V128a32 32 0 0 1 64 0v352h352a32 32 0 1 1 0 64H544v352a32 32 0 1 1-64 0V544H128a32 32 0 0 1 0-64h352z">
                                    </path>
                                </svg>
                            </n-icon>
                        </template>
                        新对话
                    </n-button>
                    <div class="history">
                        历史问题
                    </div>
                </div>
                <n-menu v-model:value="activeKey" :collapsed="collapsed" :collapsed-width="0" :options="menuOptions"
                    class="menu" />
                <div class="setting" @click="isLogin">
                    <div class="left">
                        <div>
                            <img src="../assets/imgs/user2.png" alt="" class="image">
                        </div>
                        <div>{{ '登录' }}</div>
                    </div>
                    <n-icon size="20">
                        <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round"
                            stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em"
                            xmlns="http://www.w3.org/2000/svg">
                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                            <polyline points="16 17 21 12 16 7"></polyline>
                            <line x1="21" y1="12" x2="9" y2="12"></line>
                        </svg>
                    </n-icon>
                </div> -->
                <div class="header">
                    <div class="addbtn" @click="add">
                        <n-icon size="20">
                            <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 shrink-0" height="1em"
                                width="1em" xmlns="http://www.w3.org/2000/svg">
                                <line x1="12" y1="5" x2="12" y2="19"></line>
                                <line x1="5" y1="12" x2="19" y2="12"></line>
                            </svg>
                        </n-icon>
                        <div class="new">新对话</div>
                    </div>
                    <div class="openhistory" @click="showhistory">
                        <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round"
                            stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em"
                            xmlns="http://www.w3.org/2000/svg">
                            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="9" y1="3" x2="9" y2="21"></line>
                        </svg>
                    </div>
                </div>
                <div class="center">
                    <div class="history">历史记录</div>
                    <div class="lists">
                        <div class="list" v-for="(item, index) in menuOptions" :key="item.key" @click="cliList(index)"
                            :class="showlist == index ? 'listact' : ''">
                            <div class="starticon">
                                <n-icon size="12">
                                    <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                        stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em"
                                        width="1em" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                                    </svg>
                                </n-icon>
                            </div>
                            <div class="text">{{ item.label }}</div>
                            <div class="endicon" v-if="showlist == index && showdelete">
                                <!-- <n-icon>
                                    <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                        stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em"
                                        width="1em" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M12 20h9"></path>
                                        <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path>
                                    </svg>
                                </n-icon> -->
                                <n-icon size="12">
                                    <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                        stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em"
                                        width="1em" xmlns="http://www.w3.org/2000/svg">
                                        <polyline points="3 6 5 6 21 6"></polyline>
                                        <path
                                            d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2">
                                        </path>
                                        <line x1="10" y1="11" x2="10" y2="17"></line>
                                        <line x1="14" y1="11" x2="14" y2="17"></line>
                                    </svg>
                                </n-icon>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="footer">
                    <div class="left">
                        <div>
                            <img src="../assets/imgs/user2.png" alt="" class="image">
                        </div>
                        <div class="islogin">{{ islogin }}</div>
                    </div>
                    <n-icon size="16" @click="logout" v-if="islogin == '已登录'">
                        <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round"
                            stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em"
                            xmlns="http://www.w3.org/2000/svg">
                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                            <polyline points="16 17 21 12 16 7"></polyline>
                            <line x1="21" y1="12" x2="9" y2="12"></line>
                        </svg>
                    </n-icon>
                </div>
            </n-layout-sider>
            <n-layout :native-scrollbar="false" bordered class="container">
                <div class="openhistory1" @click="showhistory" v-if="collapsed == true">
                    <n-icon size="20">
                        <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round"
                            stroke-linejoin="round" class="h-4 w-4 text-black dark:text-white" height="1em" width="1em"
                            xmlns="http://www.w3.org/2000/svg">
                            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="9" y1="3" x2="9" y2="21"></line>
                        </svg>
                    </n-icon>
                </div>
                <h1 class="title" v-if="showSocket">
                    Chat
                </h1>
                <div class="socket" v-if="!showSocket">
                    <div class="header">Chat</div>
                    <div class="messages" ref="messagesRef">
                        <div v-for="(item, index) in messages" :key="item" class="mes" ref="contaierRef">
                            <div class="item">
                                <div v-if="index % 2 == 0">
                                    <img src="../assets/imgs/user2.png" alt="" class="image">
                                </div>
                                <div v-else>
                                    <img src="../assets/imgs/chat.png" alt="" class="image">
                                </div>
                                <p class="text">{{ item }}</p>
                            </div>
                        </div>
                        <div class="bottom"></div>
                        <div class="toBottom" @click="toBottom">
                            <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 m-1" height="1em" width="1em"
                                xmlns="http://www.w3.org/2000/svg">
                                <line x1="12" y1="5" x2="12" y2="19"></line>
                                <polyline points="19 12 12 19 5 12"></polyline>
                            </svg>
                        </div>
                    </div>
                    <!-- <n-back-top :bottom="100" :visibility-height="300"></n-back-top> -->
                    <!-- <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-ea893728="">
                            <path fill="currentColor"
                                d="M600.704 64a32 32 0 0 1 30.464 22.208l35.2 109.376c14.784 7.232 28.928 15.36 42.432 24.512l112.384-24.192a32 32 0 0 1 34.432 15.36L944.32 364.8a32 32 0 0 1-4.032 37.504l-77.12 85.12a357.12 357.12 0 0 1 0 49.024l77.12 85.248a32 32 0 0 1 4.032 37.504l-88.704 153.6a32 32 0 0 1-34.432 15.296L708.8 803.904c-13.44 9.088-27.648 17.28-42.368 24.512l-35.264 109.376A32 32 0 0 1 600.704 960H423.296a32 32 0 0 1-30.464-22.208L357.696 828.48a351.616 351.616 0 0 1-42.56-24.64l-112.32 24.256a32 32 0 0 1-34.432-15.36L79.68 659.2a32 32 0 0 1 4.032-37.504l77.12-85.248a357.12 357.12 0 0 1 0-48.896l-77.12-85.248A32 32 0 0 1 79.68 364.8l88.704-153.6a32 32 0 0 1 34.432-15.296l112.32 24.256c13.568-9.152 27.776-17.408 42.56-24.64l35.2-109.312A32 32 0 0 1 423.232 64H600.64zm-23.424 64H446.72l-36.352 113.088-24.512 11.968a294.113 294.113 0 0 0-34.816 20.096l-22.656 15.36-116.224-25.088-65.28 113.152 79.68 88.192-1.92 27.136a293.12 293.12 0 0 0 0 40.192l1.92 27.136-79.808 88.192 65.344 113.152 116.224-25.024 22.656 15.296a294.113 294.113 0 0 0 34.816 20.096l24.512 11.968L446.72 896h130.688l36.48-113.152 24.448-11.904a288.282 288.282 0 0 0 34.752-20.096l22.592-15.296 116.288 25.024 65.28-113.152-79.744-88.192 1.92-27.136a293.12 293.12 0 0 0 0-40.256l-1.92-27.136 79.808-88.128-65.344-113.152-116.288 24.96-22.592-15.232a287.616 287.616 0 0 0-34.752-20.096l-24.448-11.904L577.344 128zM512 320a192 192 0 1 1 0 384 192 192 0 0 1 0-384zm0 64a128 128 0 1 0 0 256 128 128 0 0 0 0-256z">
                            </path>
                        </svg> -->
                </div>
                <div class="chatInput">
                    <n-input :disabled="inputDis" size="large" placeholder="搜索" v-model:value="chatvalue">
                        <template #suffix>
                            <n-button :disabled="showDis" :bordered="false" @click="search" class="chatbtn">
                                <n-icon>
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="none"
                                        class="h-4 w-4 m-1 md:m-0" stroke-width="2">
                                        <path
                                            d="M.5 1.163A1 1 0 0 1 1.97.28l12.868 6.837a1 1 0 0 1 0 1.766L1.969 15.72A1 1 0 0 1 .5 14.836V10.33a1 1 0 0 1 .816-.983L8.5 8 1.316 6.653A1 1 0 0 1 .5 5.67V1.163Z"
                                            fill="currentColor"></path>
                                    </svg>
                                </n-icon>
                            </n-button>
                        </template>
                    </n-input>
                </div>
            </n-layout>
        </n-layout>
    </n-space>
</template>
  
<script setup>
import { io } from "socket.io-client";
import { NIcon, useMessage } from 'naive-ui'
import { useRoute } from 'vue-router';
import { addChatRecord } from '../api/index';
import { getToken } from "../utils/auth";
import {
    BookOutline as BookIcon,
    PersonOutline as PersonIcon,
    WineOutline as WineIcon
} from '@vicons/ionicons5'

function renderIcon(icon) {
    return () => h(NIcon, null, { default: () => h(icon) })
}

const route = useRoute()

onMounted(() => {
    console.log(route);
    if (getToken() == '' || getToken() == undefined) {
        message.error(
            `暂未登录请前往登录`,
            { duration: 2000 }
        )
        // setInterval(() => {
        //     window.location.href = "https://www.baidu.com";
        // }, 2000);
    } else {
        islogin.value = '已登录'
    }
})

const message = useMessage()

const islogin = ref('未登录')

// 点击列表项
const cliList = (i) => {
    showlist.value = i
    showdelete.value = true
}

// 侧边栏是否显示
const showhistory = () => {
    collapsed.value = !collapsed.value
}
// 控制删除按钮
const showdelete = ref(false)

// 点击列表项高亮
const showlist = ref(null)

// 消息列表
const messages = ref([
    "123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123123123123123123123123123123123123dasdasjciodjciosdjccnjdscnksdjcnsdjkcn12311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
    "123123123123",
    "qweqweqweqweq",
])

// 历史记录
const menuOptions = [
    {
        label: "且听风吟123123123123123123123312",
        key: "1",
        icon: renderIcon(BookIcon)
    },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },

    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },

    // {
    //     label: "且听风吟123123123123123123123312",
    //     key: "1",
    //     icon: renderIcon(BookIcon)
    // },
    // {
    //     label: "寻羊冒险记",
    //     key: "2",
    //     // icon: renderIcon(BookIcon)
    // },
];

// 选择历史记录
const activeKey = ref(null);
// 折叠选项
const collapsed = ref(false);

// 输入
const chatvalue = ref('')
// 是否禁用按钮
const showDis = ref(true)
const inputDis = ref(false)

// 是否显示新对话
const showSocket = ref(true)

const messagesRef = ref(null);
const contaierRef = ref(null);

// 退出登录
const logout = () => {
    console.log('退出登录');
}

// 移动到底部
const toBottom = () => {
    // if(messagesRef.value.scrollTop >= messagesRef.value.scrollHeight) return
    messagesRef.value.scrollTo({ top: messagesRef.value.scrollHeight, behavior: 'smooth' })
}


const add = () => {
    // activeKey.value = null
    showSocket.value = true
    showlist.value = null
    inputDis.value = true
}

const search = () => {
    // materiaChart().then(res => {
    //     console.log(res);
    // })
    // const socket = io("wss://server-domain.com");
    // socket.emit('message', { text: chatvalue.value });
    // socket.on("connect", () => {
    //     console.log(socket.connected); // true
    // });
    showSocket.value = false
    messages.value = []
    messages.value.push(chatvalue.value);
    messages.value.push(1);
    messagesRef.value.scrollTo({ top: messagesRef.value.scrollHeight, behavior: 'smooth' });
    console.log(chatvalue.value);
    chatvalue.value = ''
}

watch(activeKey, (newval) => {
    if (newval !== null) {
        showSocket.value = false
    }
})

watch(showlist, (newval) => {
    if (newval !== null) {
        showSocket.value = false
    }
})

watch(chatvalue, (newval) => {
    if (newval !== '') {
        showDis.value = false
    } else {
        showDis.value = true
    }
}, {
    immediate: true
})
</script>
<style lang="scss" scoped>
.chat {

    .layout {
        position: relative;
        // height: calc(100vh - 1rem - 70px);
        height: calc(100vh - 70px);
        text-align: center;
        padding: 10px 1px 1px 1px;
        // padding: 0.5rem;
        background-color: #202123;
        color: #ECECF1;
    }

    .add {
        width: 100%;
        position: absolute;
        background-color: #202123;
        left: 0;
        top: 0;
        z-index: 4;

        .history {
            text-align: left;
            margin-left: 10px;
            margin-top: 10px;
            font-size: 10px;
        }

        .n-button {
            color: #ECECF1;
            width: 220px;
            height: 45px;
            line-height: 45px;
            justify-content: start;
            border-radius: 6px;
        }

        .n-button:hover {
            background-color: #2b2c2f;
        }
    }

    .menu {
        margin: 70px 0 60px;

        :deep(.n-menu-item-content-header) {
            color: #ECECF1;
        }
    }

    .setting {
        width: 100%;
        height: 60px;
        border-top: 1px solid #e9e9ee;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        padding: 0 10px;
        position: absolute;
        background-color: #202123;
        left: 0;
        bottom: 0;
        z-index: 4;

        .left {
            display: flex;
            align-items: center;

            .image {
                margin-right: 10px;
                color: #ECECF1;
                display: flex;
                align-items: center;
                justify-content: center;
                // background-color: #ec417a;
                width: 40px;
                height: 40px;
                border-radius: 6px;
            }
        }
    }

    .setting:hover {
        background-color: #343541;
    }

    :deep(.n-layout-scroll-container) {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        margin: 0 auto;


        .openhistory1 {
            position: absolute;
            z-index: 3;
            top: 0;
            left: 0;
            width: 60px;
            height: 60px;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            border-radius: 4px;
        }

        .openhistory1:hover {
            transition-duration: .2s;
            background-color: #f7f7f8;
        }

        .title {
            margin-top: 200px;
            font-weight: 600;
            color: #d9d9e3;
            text-align: center;
        }

        .socket {
            .header {
                width: 100%;
                height: 60px;
                line-height: 60px;
                padding: 0 8px;
                text-align: center;
                border-bottom: 1px solid #d9d9e3;
                box-sizing: border-box;
                position: absolute;
                top: 0;
                left: 0;
                background-color: #FFF;
                box-shadow: rgba(0, 0, 0, 0.05) 0px 4px 24px 0
            }

            .messages {
                width: 100%;
                height: calc(100vh - 130px);
                background-color: #ffffff;
                margin-top: 60px;
                overflow: auto;

                .mes {
                    width: 100%;
                    padding: 24px 16px;
                    border-bottom: 1px solid #d9d9e3;
                    box-sizing: border-box;
                    // background-color: #f7f7f8;

                    .item {
                        width: 50%;
                        display: flex;
                        // align-items: center;
                        margin: 0 auto;
                        flex-wrap: wrap;

                        .image {
                            margin-right: 10px;
                            color: #ECECF1;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            width: 40px;
                            min-width: 40px;
                            height: 40px;
                            min-height: 40px;
                            border-radius: 6px;
                        }

                        .text {
                            width: calc(100% - 50px);
                            min-width: calc(100% - 50px);
                            word-wrap: break-word;
                        }
                    }

                }

                .mes:nth-child(odd) {
                    background-color: #FFF;
                }

                .mes:nth-child(even) {
                    background-color: #f7f7f8;
                }

                .bottom {
                    width: 100%;
                    height: 180px;
                    background-color: #FFF;
                }

                .toBottom {
                    position: absolute;
                    bottom: 100px;
                    right: 50px;
                    width: 20px;
                    height: 20px;
                    border: 1px solid #aeafb2;
                    text-align: center;
                    border-radius: 50%;
                    background-color: #f7f7f8;
                    box-shadow: rgba(0, 0, 0, 0.05) 0px 4px 24px 0
                }
            }
        }

        .chatInput {
            width: 40%;
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            bottom: 0;
            margin: 0 auto 60px;
            box-shadow: 0 0 15px rgba(0, 0, 0, .1);

            .chatbtn {
                border-radius: 4px;
                border: none;
            }
        }
    }

}

:deep(.n-menu-item-content) {
    padding-right: 32px;
}

:deep(.n-menu .n-menu-item-content:not(.n-menu-item-content--disabled).n-menu-item-content--selected:hover .n-menu-item-content-header) {
    color: #ECECF1;
}

:deep(.n-menu .n-menu-item-content.n-menu-item-content--selected::before) {
    background: #343541;
}

:deep(.n-menu .n-menu-item-content.n-menu-item-content--selected .n-menu-item-content__icon) {
    color: #ECECF1;
}

:deep(.n-menu .n-menu-item-content:not(.n-menu-item-content--disabled).n-menu-item-content--selected:hover .n-menu-item-content__icon) {
    color: #ECECF1;
}

:deep(.n-menu .n-menu-item-content .n-menu-item-content__icon) {
    color: #ECECF1;
}

:deep(.n-menu .n-menu-item-content:not(.n-menu-item-content--disabled).n-menu-item-content--selected:hover::before) {
    background-color: #343541;
}

:deep(.n-button:not(.n-button--disabled):hover .n-button__state-border) {
    border: none;
}

:deep(.n-button:not(.n-button--disabled):active .n-button__state-border) {
    border: none;
}

::-webkit-scrollbar {
    width: 0;
}

.layout {

    .header {
        position: absolute;
        left: 0;
        top: 0;
        z-index: 4;
        background-color: #202123;
        width: 100%;
        height: 50px;
        display: flex;
        align-items: center;
        box-sizing: border-box;
        padding: 2px 6px;

        .addbtn {
            flex: 1;
            height: 45px;
            display: flex;
            align-items: center;
            border-radius: 4px;
            border: 1px solid #4d4d4f;
            box-sizing: border-box;
            margin-right: 8px;
            padding: 0 12px;

            .new {
                margin-left: 10px;
            }
        }

        .addbtn:hover {
            transition-duration: .2s;
            background-color: #2b2c2f;
        }


        .openhistory {
            width: 45px;
            height: 45px;
            line-height: 45px;
            text-align: center;
            border-radius: 4px;
            border: 1px solid #4d4d4f;
        }

        .openhistory:hover {
            transition-duration: .2s;
            background-color: #2b2c2f;
        }
    }

    .center {
        width: 100%;

        .history {
            position: absolute;
            left: 0;
            top: 50px;
            z-index: 4;
            background-color: #202123;
            width: 100%;
            padding: 12px 8px;
            color: #8E8EA0;
            text-align: left;
            box-sizing: border-box;
            font-size: 12px;
        }

        .lists {
            margin: 100px 0 60px;
            width: 100%;
            overflow: auto;
            padding: 6px;
            box-sizing: border-box;

            .list {
                width: 100%;
                display: flex;
                align-items: center;
                // justify-content: space-between;
                padding: 12px;
                box-sizing: border-box;
                margin-bottom: 2px;

                .starticon {
                    // flex: 1;
                    margin-right: 10px;
                }

                .text {
                    // max-width: 160px;
                    // min-width: 160px;
                    max-width: 180px;
                    min-width: 180px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                }

                .endicon {
                    // display: flex;
                    // align-items: center;
                    margin-left: 10px;
                }
            }

            .list:hover {
                border-radius: 4px;
                background-color: #2a2a31;
            }

            .listact {
                border-radius: 4px;
                // border: 1px solid #4d4d4f;
                background-color: #343541;
            }
        }
    }

    .footer {
        width: 100%;
        height: 60px;
        border-top: 1px solid #e9e9ee;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        padding: 0 20px;
        position: absolute;
        background-color: #202123;
        left: 0;
        bottom: 0;
        z-index: 4;

        .left {
            display: flex;
            align-items: center;

            .image {
                margin-right: 14px;
                color: #ECECF1;
                display: flex;
                align-items: center;
                justify-content: center;
                // background-color: #ec417a;
                width: 40px;
                height: 40px;
                border-radius: 6px;
            }
            .islogin{
                font-weight: 600;
            }
        }
    }

    .footer:hover {
        background-color: #343541;
    }
}
</style>