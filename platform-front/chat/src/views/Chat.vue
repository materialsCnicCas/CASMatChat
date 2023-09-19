<template>
    <n-space vertical class="chat">
        <n-layout has-sider>
            <n-layout-sider :native-scrollbar="false" bordered collapse-mode="width" :collapsed-width="0" :width="260"
                :collapsed="collapsed" class="layout">
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
                        <div class="list" v-for="(item, index) in menuOptions" :key="item.key"
                            @click="cliList(index, item.id)" :class="showlist == index ? 'listact' : ''">
                            <div class="starticon">
                                <n-icon size="12">
                                    <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24"
                                        stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em"
                                        width="1em" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                                    </svg>
                                </n-icon>
                            </div>
                            <div class="text">{{ item.chatName }}</div>
                            <div class="endicon" v-if="showlist == index && showdelete"
                                @click.stop="deleteChatcli(item.id)">
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
                    <div class="header">CAS MatChat（仅支持英文输入，专注于化学合成）</div>
                    <div class="messages" ref="messagesRef">
                        <div v-for="(item, index) in messages" :key="item" class="mes" ref="contaierRef">
                            <div class="item">
                                <div class="user">
                                    <img src="../assets/imgs/user2.png" alt="" class="image">
                                    <p class="text">{{ item.sendMessage }}</p>
                                </div>
                                <div class="chat">
                                    <img src="../assets/imgs/chat.png" alt="" class="image">
                                    <p class="text">{{ item.receiveMessage }}</p>
                                </div>
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
                </div>
                <div class="chatInput">
                    <n-input :disabled="inputDis" size="large" placeholder="CAS MatChat（仅支持英文输入，专注于化学合成）" v-model:value="chatvalue"
                        @keyup.enter="search">
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
import { chatList, addChatRecord, chatRecordList, deleteChat, interference } from '../api/index';
import { getToken, setToken } from "../utils/auth";

const route = useRoute()

onMounted(() => {
    // console.log(route.query.pid);
    setToken('asdfgh')
    if (route.query.pid !== undefined && route.query.pid !== '') {
        setToken(route.query.pid);
        islogin.value = '已登录'
        getchatList()
        // socket.value = io.connect('http://chat.aicnic.cn', {
        //     query: {
        //         token: getToken()
        //     },
        //     timeout: 10000,
        // });
        // // socket.value = io.connect(`http://localhost:5000?token=${getToken()}`);
        // socket.value.on('connect', function () {
        //     console.log('连接成功');
        // });
        // socket.value.on('connect_error', () => {
        //     message.error(
        //         "连接失败",
        //         { duration: 1000 }
        //     )
        //     socket.value.disconnect();
        // })
    } else {
        if (getToken() == '' || getToken() == undefined || getToken() == 'undefined') {
            message.error(
                `暂未登录请前往登录`,
                { duration: 2000 }
            )
            setTimeout(() => {
                window.location.href = "https://passport.escience.cn/";
            }, 2000);
        } else {
            islogin.value = '已登录'
            getchatList()
            // socket.value = io.connect(`http://localhost:5000?token=${getToken()}`);
            // socket.value = io.connect('http://chat.aicnic.cn/', {
            //     query: {
            //         token: getToken()
            //     },
            //     timeout: 10000,
            // });
            // socket.value.on('connect', function () {
            //     console.log('连接成功');
            // });
            // socket.value.on('connect_error', () => {
            //     message.error(
            //         "连接失败",
            //         { duration: 1000 }
            //     )
            //     socket.value.disconnect();
            // })
        }
    }
})

onUnmounted(() => {
    if (socket.value == null) return
    socket.value.on('disconnect', function () {
        console.log('断开连接');
    });
})

const getchatList = () => {
    chatList().then(res => {
        // console.log(res.data);
        menuOptions.value = res.data
        // console.log(menuOptions.value);
    })
}

const socket = ref(null)
const socketMessage = ref('')

const message = useMessage()

const islogin = ref('未登录')

// 点击列表项
const cliList = (i, id) => {
    chatId.value = id
    showlist.value = i
    showdelete.value = true
    messages.value = []
    let data = {
        chatId: id
    }
    chatRecordList(data).then(res => {
        // console.log(res);
        messages.value = res.data
    })
}

// 删除对话
const deleteChatcli = (id) => {
    chatId.value = ''
    let data = {
        chatId: id
    }
    deleteChat(data).then(res => {
        // console.log(res);
        if (res.status == "M0200") {
            message.success(
                res.msg,
                { duration: 1000 }
            )
            getchatList()
            showSocket.value = true
            showlist.value = null
            chatvalue.value = ''
        } else {
            message.error(
                res.msg,
                { duration: 1000 }
            )
        }
        // messages.value = res.data
    })
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
const messages = ref([])

// 历史记录
const menuOptions = ref([])

// 折叠选项
const collapsed = ref(false);

// 输入
const chatvalue = ref('')
// 是否禁用按钮
const showDis = ref(true)
const inputDis = ref(false)

// 是否显示新对话
const showSocket = ref(true)

// 点击对话id
const chatId = ref('')

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

// 新对话
const add = () => {
    showSocket.value = true
    showlist.value = null
    chatvalue.value = ''
    chatId.value = ''
    messages.value = []
    // inputDis.value = true
}

// 搜索
// const search = () => {
//     // messagesRef.value.scrollTo({ top: messagesRef.value.scrollHeight, behavior: 'smooth' });
//     showSocket.value = false

//     if (socket.value == null || chatvalue.value == '') return
//     inputDis.value = true
//     socketMessage.value = ''
//     messages.value.push({ sendMessage: chatvalue.value, receiveMessage: socketMessage.value });

//     socket.value.emit('instruction', chatvalue.value);
//     socket.value.on('model_result', (message) => {
//         console.log(message);
//         if (message.data == 'end441') {
//             inputDis.value = false
//             let data = {
//                 sendMessage: chatvalue.value,
//                 receiveMessage: socketMessage.value,
//                 chatId: chatId.value
//             }
//             addChatRecord(data).then(res => {
//                 console.log(res);
//                 if (res.status == 'M0200') {
//                     if (chatId.value == '') {
//                         addgetchatlist()
//                     } else {
//                         let chatlist = {
//                             chatId: chatId.value
//                         }
//                         chatRecordList(chatlist).then(res => {
//                             messages.value = res.data
//                         })
//                     }
//                 }
//             })
//         } else {
//             socketMessage.value += message.data
//             console.log(socketMessage.value);
//             messages.value[messages.value.length - 1].receiveMessage = socketMessage.value
//         }
//     });
//     // socketMessage.value = 'qweasd'
//     console.log(messages.value, socketMessage.value);
//     chatvalue.value = ''
// }

const search = () => {
    // messagesRef.value.scrollTo({ top: messagesRef.value.scrollHeight, behavior: 'smooth' });
    showSocket.value = false
    inputDis.value = true
    socketMessage.value = ''
    messages.value.push({ sendMessage: chatvalue.value, receiveMessage: socketMessage.value });
    let instructions = {
        instruction: chatvalue.value,
        token: getToken()
    }
    interference(instructions).then(res => {
        console.log(res, 'instructions');
        socketMessage.value = res.message
        messages.value[messages.value.length - 1].receiveMessage = socketMessage.value
        inputDis.value = false
        let data = {
            sendMessage: chatvalue.value,
            receiveMessage: socketMessage.value,
            chatId: chatId.value
        }
        addChatRecord(data).then(res => {
            console.log(res);
            if (res.status == 'M0200') {
                if (chatId.value == '') {
                    addgetchatlist()
                } else {
                    let chatlist = {
                        chatId: chatId.value
                    }
                    chatRecordList(chatlist).then(res => {
                        messages.value = res.data
                        chatvalue.value = ''
                    })
                }
            }
        })
    }).catch(err => {
        console.log(err);
        message.error(
            "连接超时",
            { duration: 5000 }
        )
        messages.value.pop()
        inputDis.value = false
    })

    console.log(messages.value, socketMessage.value);
}

const addgetchatlist = () => {
    chatList().then(res => {
        menuOptions.value = res.data
        let chatlist = {
            chatId: menuOptions.value[0].id
        }
        chatRecordList(chatlist).then(res => {
            messages.value = res.data
        })
        chatvalue.value = ''
        chatId.value = menuOptions.value[0].id
    })
}

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
        height: calc(100vh - 70px);
        text-align: center;
        padding: 10px 1px 1px 1px;
        background-color: #202123;
        color: #ECECF1;

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

                .islogin {
                    font-weight: 600;
                }
            }
        }

        .footer:hover {
            background-color: #343541;
        }
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
                    // padding: 24px 16px;
                    // border-bottom: 1px solid #d9d9e3;
                    box-sizing: border-box;
                    // background-color: #f7f7f8;

                    .item {
                        // width: 50%;
                        // display: flex;
                        // align-items: center;
                        margin: 0 auto;

                        // flex-wrap: wrap;
                        .user {
                            display: flex;
                            // align-items: center;
                            background-color: #FFF;
                            border-bottom: 1px solid #d9d9e3;
                            padding: 24px 20%;
                        }

                        .chat {
                            display: flex;
                            // align-items: center;
                            background-color: #f7f7f8;
                            border-bottom: 1px solid #d9d9e3;
                            padding: 24px 20%;
                        }

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

                // .mes:nth-child(odd) {
                //     background-color: #FFF;
                // }

                // .mes:nth-child(even) {
                //     background-color: #f7f7f8;
                // }

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
</style>