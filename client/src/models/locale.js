// 国际化
import jaJP from '../locale/ja_JP';
import zhCN from '../locale/zh_CN';

const locale = localStorage.getItem('locale') || navigator.languages[1];
const messages = locale === 'zh' ? zhCN : jaJP;

export default {
  namespace: 'locale',

  state: {
    locale,
    messages,
    defaultLocale: 'zh',
  },

  reducers: {
    addMessage: (state, { payload }) => {
      return {
        ...state,
        messages: payload,
      };
    },
    changeLocale: (state, { payload }) => {
      return {
        ...state,
        locale: payload.locale,
        messages: payload.locale === 'zh' ? zhCN : jaJP,
      };
    },
  },
};
