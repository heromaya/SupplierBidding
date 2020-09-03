import Vue from 'vue'
import Vuex from 'vuex'
import '../utils/sockjs'
import '../utils/stomp'
import {getRequest} from "../utils/api";
import { Notification } from 'element-ui';
Vue.use(Vuex)
const now = new Date();
const store = new Vuex.Store({
  state: {
    user: {
      code: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).code,
    },
    routes: [],
    sessions: {},
    currentSession: null,
    currentUser: JSON.parse(window.sessionStorage.getItem("user")),
    currentSessionId: -1,
    filterKey: '',
    stomp: null,
    users: [],
    isDot: {}
  },
  mutations: {
    login(state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    logout(state) {
      state.user = '',
        window.localStorage.removeItem('user')
    },
    register(state, supplier) {
      state.supplier = supplier
    },
    initRoutes(state, data) {
      state.routes = data
    },

    changeCurrentSession(state, currentSession) {
      Vue.set(state.isDot, state.currentUser.code + '#' + currentSession.code, false);
      state.currentSession = currentSession;
    },
    addMessage(state, msg) {
      let mss = state.sessions[state.currentUser.code + '#' + msg.to];
      if (!mss) {
        // state.sessions[state.currentHr.username+'#'+msg.to] = [];
        Vue.set(state.sessions, state.currentUser.code + '#' + msg.to, []);
      }
      state.sessions[state.currentUser.code + '#' + msg.to].push({
        content: msg.content,
        date: new Date(),
        self: !msg.notSelf
      })
    },
    INIT_DATA(state) {
      //浏览器本地的历史聊天记录可以在这里完成
      let data = localStorage.getItem('vue-chat-session');
      if (data) {
        state.sessions = JSON.parse(data);
      }
    },
    INIT_USER(state, data) {
      state.users = data;
    },
    INIT_CURRENTUSER(state, user) {
      state.currentUser = user;
    }
  },
  actions: {
    initData(context) {
      context.commit('INIT_DATA')
      getRequest("/tendering/getAllUsers").then(resp => {
        if (resp) {
          context.commit('INIT_USER', resp);
        }
      })
    },
    connect(context) {
      context.state.stomp = Stomp.over(new SockJS('/ws/projectChat'));
      context.state.stomp.connect({}, success => {
        context.state.stomp.subscribe('/user/queue/chat', msg => {
          let receiveMsg = JSON.parse(msg.body);
          if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.code) {
            Notification.info({
              title: '【' + receiveMsg.from + '】发来一条消息',
              message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
              position: 'bottom-right'
            })
            Vue.set(context.state.isDot, context.state.currentUser.code + '#' + receiveMsg.from, true);
          }
          receiveMsg.notSelf = true;
          receiveMsg.to = receiveMsg.from;
          context.commit('addMessage', receiveMsg);
        })
      }, error => {

      })
    }
  },

})
store.watch(function (state) {
  return state.sessions
}, function (val) {
  localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
  deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export default store;
