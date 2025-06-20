<template>
  <aside class="cart-summary">
    <h3>Cart Summary</h3>
    <div v-if="cart.items.length === 0" class="empty-cart">
      <p>Your cart is empty</p>
    </div>
    <ul v-else>
      <li v-for="item in cart.items" :key="item.id" class="cart-item">
        <div class="item-info">
          <span class="item-name">{{ item.name }}</span>
          <span class="item-quantity">Qty: {{ item.quantity }}</span>
        </div>
        <div class="item-actions">
          <span class="item-price">${{ (item.price * item.quantity).toFixed(2) }}</span>
          <button @click="remove(item.id)" class="remove-btn">×</button>
        </div>
      </li>
    </ul>
    <div v-if="cart.items.length > 0" class="cart-total">
      <strong>Total</strong>
      <span>${{ cart.total.toFixed(2) }}</span>
    </div>
    <button v-if="cart.items.length > 0" class="checkout" @click="checkout">Checkout</button>
  </aside>
</template>

<script>
import { useCartStore } from '../store/cart';

export default {
  setup() {
    const cart = useCartStore();
    const remove = (id) => cart.removeFromCart(id);
    const checkout = () => {
      alert('Order placed!');
      cart.clearCart();
    };
    return { cart, remove, checkout };
  }
};
</script>

<style scoped>
.cart-summary {
  padding: 1.5rem;
  background: #fff;
  min-height: 200px;
}

.cart-summary h3 {
  margin-bottom: 1.5rem;
  color: #222;
  font-size: 1.3rem;
  font-weight: 600;
}

.empty-cart {
  text-align: center;
  padding: 2rem 0;
  color: #666;
}

.cart-summary ul {
  list-style: none;
  padding: 0;
  margin: 0 0 1.5rem 0;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.cart-item:last-child {
  border-bottom: none;
}

.item-info {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.item-name {
  font-weight: 500;
  color: #222;
  margin-bottom: 0.25rem;
}

.item-quantity {
  font-size: 0.9rem;
  color: #666;
}

.item-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.item-price {
  font-weight: 600;
  color: #b85c00;
}

.remove-btn {
  background: #ff4444;
  color: white;
  border: none;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 14px;
  transition: background 0.2s;
}

.remove-btn:hover {
  background: #cc0000;
}

.cart-total {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-top: 2px solid #f0f0f0;
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.checkout {
  width: 100%;
  padding: 0.75rem;
  background: #b85c00;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}

.checkout:hover {
  background: #a05000;
}
</style> 