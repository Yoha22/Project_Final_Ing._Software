import { defineStore } from 'pinia';

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: []
  }),
  getters: {
    total(state) {
      return state.items.reduce((sum, item) => sum + item.price * (item.quantity || 1), 0);
    }
  },
  actions: {
    addToCart(product) {
      const found = this.items.find(item => item.id === product.id);
      if (found) {
        found.quantity += 1;
      } else {
        this.items.push({ ...product, quantity: 1 });
      }
    },
    removeFromCart(id) {
      this.items = this.items.filter(item => item.id !== id);
    },
    clearCart() {
      this.items = [];
    }
  }
}); 