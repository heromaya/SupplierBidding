  import {getRequest} from "./api";

export const initMenu = (router, store) => {
  // 正常跳转
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest("/menu").then(data => {
    if (data) {
      let fmtRoutes = formatRoutes(data);
      router.addRoutes(fmtRoutes);
      store.commit('initRoutes', fmtRoutes);
      store.dispatch('connect');
    }
  })
}

export const formatRoutes = (routes) => {
  let fmRoutes = [];
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      icon,
      children
    } = router;
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      name: name,
      icon: icon,
      children: children,
      component(resolve) {
        if (component.startsWith("Home")) {
          require(['../views/' + component + '.vue'], resolve);
        }else if (component.startsWith("Bid")) {
          require(['../views/bid/' + component + '.vue'], resolve);
        } else if (component.startsWith("Expert")) {
          require(['../views/expert/' + component + '.vue'], resolve);
        } else if (component.startsWith("Notice")) {
          require(['../views/notice/' + component + '.vue'], resolve);
        } else if (component.startsWith("Project")) {
          require(['../views/project/' + component + '.vue'], resolve);
        } else if (component.startsWith("Supplier")) {
          require(['../views/supplier/' + component + '.vue'], resolve);
        } else if (component.startsWith("System")) {
          require(['../views/system/' + component + '.vue'], resolve);
        } else if (component.startsWith("Tendering")) {
          require(['../views/tendering/' + component + '.vue'], resolve);
        } else if (component.startsWith("WorkFlow")) {
          require(['../views/workflow/' + component + '.vue'], resolve);
        }
      }
    }
    fmRoutes.push(fmRouter)
  })
  return fmRoutes;
}
