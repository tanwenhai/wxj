import { Card } from 'antd';
import React, { Component } from 'react';
import {
  addLocaleData,
  injectIntl,
  intlShape,
  FormattedMessage,
} from 'react-intl';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';

addLocaleData([{
  locale: 'zh-CN',
  hh: 'hhhhh',
}]);

class CityList extends Component {
  static propTypes = {
    // eslint-disable-next-line
    intl: intlShape.isRequired,
  }
  render() {
    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div>
            <FormattedMessage id="title" />
          </div>
        </Card>
      </PageHeaderLayout>
    );
  }
}

export default injectIntl(CityList);
