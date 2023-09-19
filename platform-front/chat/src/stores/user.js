import { defineStore } from "pinia";
import { getToken } from "../utils/auth";

export const userDetailStore = defineStore("userDetailStore", {
  state: () => {
    return {
      user: {
        token: getToken(),
        name: "",
      },
    };
  },
});
