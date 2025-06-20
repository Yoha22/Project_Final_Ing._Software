import api from './axios';

export default {
  login(credentials) {
    return api.post('/auth/login', credentials);
  },
  register(data) {
    return api.post('/auth/register', data);
  },
  logout() {
    return api.post('/auth/logout');
  },
  getProfile() {
    return api.get('/auth/profile');
  }
};  