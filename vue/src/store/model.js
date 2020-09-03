import { getModelList, getById, save, del, deployment } from '@/api/model'
const state = {
  total: 0,
  list: [],
  modelInfo: {},
  columns: [
    {
      field: 'id',
      label: 'ID',
      align: 'center',
      minWidth: '80'
    }, {
      field: 'name',
      label: '模型名称',
      align: 'center',
      minWidth: '180'
    }, {
      field: 'key',
      label: '模型标识',
      align: 'center',
      minWidth: '150'
    }, {
      field: 'category',
      label: '模型类别',
      align: 'center',
      minWidth: '100'
    }, {
      field: 'deploymentId',
      label: '部署ID',
      align: 'center',
      minWidth: '180'
    }, {
      field: 'status',
      slot: true
    }, {
      field: 'createTime',
      label: '创建时间',
      align: 'center',
      minWidth: '150'
    }, {
      field: 'action',
      slot: true
    }
  ],
  formOptions: {
    formData: {
      name: '',
      key: ''
    },
    formItems: [
      {
        type: 'input',
        field: 'name',
        label: '',
        placeholder: '模型名称'
      }, {
        type: 'input',
        field: 'key',
        label: '',
        placeholder: '模型标识'
      }, {
        type: 'button',
        field: 'search',
        name: '搜索'
      }
    ],
    toolBar: [
      {
        type: 'button',
        field: 'add',
        name: '创建一个空模型',
        icon: 'el-icon-plus',
        btnType: 'success'
      }
    ]
  }
}

const mutations = {
  SET_LIST: (state, list) => {
    state.list = list
  },
  SET_TOTAL: (state, total) => {
    state.total = total
  },
  SET_MODELINFO: (state, modelInfo) => {
    state.modelInfo = modelInfo || {}
  }
}

const actions = {
  async getList({ commit }, { pageNum, pageSize, name, key, success }) {
    const query = { pageNum, pageSize, name, key }
    await getModelList(query).then(res => {
      const { data } = res
      commit('SET_LIST', data.list)
      commit('SET_TOTAL', parseInt(data.total))
      success && success()
    })
  },
  async getModelById({ commit }, modelId) {
    if (modelId) {
      await getById(modelId).then(res => {
        const { data } = res
        commit('SET_MODELINFO', data)
      })
    } else {
      commit('SET_MODELINFO', {})
    }
  },
  async saveModel({ commit }, { data, success }) {
    await save(data).then(res => {
      success && success()
    })
  },
  async deleteModel({ commit }, { modelId, success }) {
    await del(modelId).then(res => {
      success && success()
    })
  },
  async deploy({ commit }, { modelId, success }) {
    await deployment(modelId).then(res => {
      success && success()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
