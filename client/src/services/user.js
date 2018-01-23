import request from '../utils/request';

export async function query() {
  return request('/api/users');
}

export async function queryCurrent() {
  return request('/api/user/current');
}

export async function userAuth(params) {
  return request('/api/user/auth', {
    method: 'POST',
    body: {
      ...params,
      method: 'POST',
    },
  });
}