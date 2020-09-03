import Vue from 'vue'
import Router from 'vue-router'
import Home from "../views/Home";
import Login from "../views/Login";
import Register from "../views/Register";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [

    {
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      hidden: true
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      hidden: true
    },
  ]
})
