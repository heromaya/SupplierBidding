// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router';
import store from './store'
import {postKeyValueRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {initMenu} from "./utils/menus";
import {uploadRequest} from "./utils/api";
import  'font-awesome/css/font-awesome.min.css'
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8888/'
axios.defaults.withCredentials = true
// 全局注册,之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.uploadRequest = uploadRequest;
Vue.config.productionTip = false
Vue.use(ElementUI);

router.beforeEach((to, from, next) => {
  if(to.path == '/' || to.path == '/register'){
    next()
  }else{
    if(window.sessionStorage.getItem("user")){
    initMenu(router,store)
    next();
    }else{
      next('/?redirect=' + to.path);
    }
  }
})


new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: {App},
  template: '<App/>'
})
