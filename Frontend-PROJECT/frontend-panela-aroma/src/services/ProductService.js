import api from './axios';

export default {
  getAll() {
    return api.get('/products');
  },
  add(product) {
    return api.post('/products', product);
  },
  getById(id) {
    return api.get(`/products/${id}`);
  },
  update(id, product) {
    return api.put(`/products/${id}`, product);
  },
  delete(id) {
    return api.delete(`/products/${id}`);
  }
}; 