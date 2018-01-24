import React from 'react';
import { routerRedux, Switch } from 'dva/router';
import { Spin } from 'antd';
import dynamic from 'dva/dynamic';
import { connect } from 'dva';
import { IntlProvider, addLocaleData } from 'react-intl';
import zh from 'react-intl/locale-data/zh';
import ja from 'react-intl/locale-data/ja';
import { getRouterData } from './common/router';
import Authorized from './utils/Authorized';
import styles from './index.less';

addLocaleData([...zh, ...ja]);

const ConnectIntlProvider = connect((state) => {
  return {
    locale: state.global.locale,
    messages: state.global.messages,
    defaultLocale: state.global.defaultLocale,
  };
})(IntlProvider);

const { ConnectedRouter } = routerRedux;
const { AuthorizedRoute } = Authorized;
dynamic.setDefaultLoadingComponent(() => {
  return <Spin size="large" className={styles.globalSpin} />;
});

function RouterConfig({ history, app }) {
  const routerData = getRouterData(app);
  const UserLayout = routerData['/user'].component;
  const BasicLayout = routerData['/'].component;
  return (
    <ConnectIntlProvider>
      <ConnectedRouter history={history}>
        <Switch>
          <AuthorizedRoute
            path="/user"
            render={props => <UserLayout {...props} />}
            redirectPath="/"
          />
          <AuthorizedRoute
            path="/"
            render={props => <BasicLayout {...props} />}
            authority={['admin', 'user']}
            redirectPath="/user/login"
          />
        </Switch>
      </ConnectedRouter>
    </ConnectIntlProvider>
  );
}

export default RouterConfig;
