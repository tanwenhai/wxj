import { queryNotices } from '../services/api';
import jaJP from '../locale/ja_JP';
import zhCN from '../locale/zh_CN';

export const localeKey = 'locale';

const locale = localStorage.getItem(localeKey) || navigator.languages[1];
const messages = locale === 'zh' ? zhCN : jaJP;

function saveLocale(l) {
  localStorage.setItem(localeKey, l);
}

export default {
  namespace: 'global',

  state: {
    collapsed: false,
    notices: [],
    locale,
    messages,
    defaultLocale: 'zh',
  },

  effects: {
    *fetchNotices(_, { call, put }) {
      const data = yield call(queryNotices);
      yield put({
        type: 'saveNotices',
        payload: data,
      });
      yield put({
        type: 'user/changeNotifyCount',
        payload: data.length,
      });
    },
    *clearNotices({ payload }, { put, select }) {
      yield put({
        type: 'saveClearedNotices',
        payload,
      });
      const count = yield select(state => state.global.notices.length);
      yield put({
        type: 'user/changeNotifyCount',
        payload: count,
      });
    },
  },

  reducers: {
    changeLayoutCollapsed(state, { payload }) {
      return {
        ...state,
        collapsed: payload,
      };
    },
    saveNotices(state, { payload }) {
      return {
        ...state,
        notices: payload,
      };
    },
    saveClearedNotices(state, { payload }) {
      return {
        ...state,
        notices: state.notices.filter(item => item.type !== payload),
      };
    },
    addMessage: (state, { payload }) => {
      return {
        ...state,
        messages: payload,
      };
    },
    changeLocale: (state, { payload }) => {
      saveLocale(payload.locale);
      return {
        ...state,
        locale: payload.locale,
        messages: payload.locale === 'zh' ? zhCN : jaJP,
      };
    },
  },

  subscriptions: {
    setup({ history }) {
      // Subscribe history(url) change, trigger `load` action if pathname is `/`
      return history.listen(({ pathname, search }) => {
        if (typeof window.ga !== 'undefined') {
          window.ga('send', 'pageview', pathname + search);
        }
      });
    },
  },
};
