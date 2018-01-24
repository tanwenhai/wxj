import React from 'react';
import classNames from 'classnames';
import { connect } from 'dva';
import { Menu, Dropdown, Icon } from 'antd';
import styles from './index.less';

const globalFooter = ({ className, links, copyright, dispatch }) => {
  const changeLocale = ({ key }) => {
    dispatch({
      type: 'global/changeLocale',
      payload: {
        locale: key,
      },
    });
    return key;
  };

  const clsString = classNames(styles.globalFooter, className);
  const menu = (
    <Menu onClick={changeLocale}>
      <Menu.Item key="zh">
        中文
      </Menu.Item>
      <Menu.Item key="ja">
        日本語
      </Menu.Item>
    </Menu>
  );
  return (
    <div className={clsString}>
      {
        links && (
          <div className={styles.links}>
            {links.map(link => (
              <a
                key={link.key}
                target={link.blankTarget ? '_blank' : '_self'}
                href={link.href}
              >
                {link.title}
              </a>
            ))}
          </div>
        )
      }
      <Dropdown overlay={menu}>
        <span className="ant-dropdown-link">
        切换语言 <Icon type="down" />
        </span>
      </Dropdown>
      {copyright && <div className={styles.copyright}>{copyright}</div>}
    </div>
  );
};

export default connect()(globalFooter);
