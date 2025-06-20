import axios from 'axios';
const API = 'http://localhost:8080/api/invoices';

function getAuthHeader() {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
}

export default {
  getAll() {
    return axios.get(API, { headers: getAuthHeader() });
  },
  getById(id) {
    return axios.get(`${API}/${id}`, { headers: getAuthHeader() });
  }
}; 