// 国际化
import jaJP from '../locale/ja_JP';
import zhCN from '../locale/zh_CN';

export const localeKey = 'locale';

const locale = localStorage.getItem(localeKey) || navigator.languages[1];
const messages = locale === 'zh' ? zhCN : jaJP;

function saveLocale(l) {
  localStorage.setItem(localeKey, l);
}

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
      saveLocale(payload.locale);
      return {
        ...state,
        locale: payload.locale,
        messages: payload.locale === 'zh' ? zhCN : jaJP,
      };
    },
  },
};
