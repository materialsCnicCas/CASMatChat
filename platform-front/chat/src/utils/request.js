import axios from "axios";
import { tansParams } from "./params";
import { saveAs } from "file-saver";
import { userDetailStore } from "../stores/user";
import { getToken, removeToken } from "./auth";
// import { useMessage } from "naive-ui";

// const Message = useMessage();
// 是否显示重新登录
// export let isRelogin = { show: false };

// axios.defaults.headers["Content-Type"] = "application/json;charset=utf-8";
// axios.defaults.headers["Accept"] = "application/json"
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  // baseURL: "http://223.193.8.235:8849",
  baseURL: "http://chat.aicnic.cn/",
  //   baseURL: import.meta.VITE_APP_DEV_URL,
  // withCredentials: true,
  // 超时
  timeout: 3 * 60 * 1000,
});

// request拦截器
service.interceptors.request.use(
  (config) => {
    // 是否需要设置 token
    const isToken = (config.headers || {}).isToken === false;
    // 是否需要防止数据重复提交
    const isRepeatSubmit = (config.headers || {}).repeatSubmit === false;
    if (getToken() && !isToken) {
      config.headers["Authorization"] = getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    // get请求映射params参数
    if (config.method === "get" && config.params) {
      let url = config.url + "?" + tansParams(config.params);
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }
    if (
      !isRepeatSubmit &&
      (config.method === "post" || config.method === "put")
    ) {
    }
    return config;
  },
  (error) => {
    console.log(error);
    Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (res) => {
    // 未设置状态码则默认成功状态
    const code = res.data.status || 200;
    const msg = res.data.msg;
    // 获取错误信息
    // const msg = errorCode[code] || res.data.msg || errorCode["default"];
    // 二进制数据则直接返回
    if (
      res.request.responseType === "blob" ||
      res.request.responseType === "arraybuffer"
    ) {
      return res.data;
    }
    if (code === 401 || code == 403) {
      // console.log(code);
      // Message.error(msg, { duration: 2000 });
      // const userDetail = userDetailStore();
      // removeToken();
      // userDetail.user.token = "";
      // userDetail.user.name = "";
      // localStorage.removeItem("Admin-webSql-username");
      // location.href = "/login";
      // return Promise.reject("请重新登录。");
    } else if (code === 500) {
      // Message.error(msg, { duration: 2000 });

      return Promise.reject(new Error(msg));
    } else if (code === 601) {
      // Message.error(msg, { duration: 2000 });

      return Promise.reject(new Error(msg));
      // } else if (code !== '200') {
    } else if (code == 200) {
      return Promise.resolve(res.data);
    } else if (code !== "M0200") {
      // ElNotification.error({
      //   title: msg,
      // });
      // Message.error(msg, { duration: 2000 });
      return Promise.reject("error");
    } else {
      return Promise.resolve(res.data);
    }
  },
  (error) => {
    console.log("err" + error);
    // let { message } = error;
    // if (message == "Network Error") {
    //   message = "后端接口连接异常";
    // } else if (message.includes("timeout")) {
    //   message = "系统接口请求超时";
    // } else if (message.includes("Request failed with status code")) {
    //   // if (message.substr(message.length - 3) == 401) {
    //   //   // message = "系统接口" + message.substr(message.length - 3) + "异常";
    //   //   message = "请重新登录";
    //   //   const userDetail = userDetailStore();
    //   //   removeToken();
    //   //   userDetail.user.token = "";
    //   //   userDetail.user.name = "";
    //   //   localStorage.removeItem("Admin-webSql-username");
    //   //   location.href = "/login";
    //   // } else {
    //   //   message = "系统接口" + message.substr(message.length - 3) + "异常";
    //   // }
    // }
    // Message.error(msg, { duration: 5000 });

    return Promise.reject(error);
  }
);

// export function download(url, params) {
//   let downloadLoadingInstance = ElLoading.service({
//     text: "正在下载数据，请稍候",
//     background: "rgba(0, 0, 0, 0.7)",
//   });
//   let fileType = "txt";
//   if (params.fileName == "file") {
//     fileType = "zip";
//   }
//   return service
//     .post(url, params, {
//       headers: { "Content-Type": "application/x-www-form-urlencoded" },
//       responseType: "blob",
//     })
//     .then(async (data) => {
//       console.log(data);
//       const blob = new Blob([data]);
//       saveAs(blob, `${params.fileName}.${fileType}`);
//       downloadLoadingInstance.close();
//     })
//     .catch((r) => {
//       console.error(r);
//       Message.error("下载文件错误", { duration: 2000 });

//       downloadLoadingInstance.close();
//     });
// }

export default service;
