import React, { Component } from 'react';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import { Card } from 'antd';

export default class CityList extends Component {
  render () {
    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div>
            list
          </div>
        </Card>
      </PageHeaderLayout>
    );
  }
};
