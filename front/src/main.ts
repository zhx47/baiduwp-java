import {createApp} from "vue";
import VueCookies from 'vue-cookies'
import App from './App.vue';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import router from './router';

import Verify from './utils/Verify'
import Message from './utils/Message'
import Request from './utils/Request'
import Confirm from './utils/Confirm'
import Utils from './utils/Utils'


// 自定义组件
import Dialog from "@/components/Dialog.vue"
import Avatar from "@/components/Avatar.vue"
import Table from "@/components/Table.vue"
import Icon from "@/components/Icon.vue"
import Navigation from "@/components/Navigation.vue"
import Window from "@/components/Window.vue"

const app = createApp(App);

app.use(ElementPlus);
app.use(router);

app.component('Dialog', Dialog)
app.component('Avatar', Avatar)
app.component('Table', Table)
app.component('Icon', Icon)
app.component('Navigation', Navigation)
app.component('Window', Window)

app.config.globalProperties.VueCookies = VueCookies;
app.config.globalProperties.Verify = Verify;
app.config.globalProperties.Message = Message;
app.config.globalProperties.Request = Request;
app.config.globalProperties.Confirm = Confirm;
app.config.globalProperties.Utils = Utils;

app.mount('#app');