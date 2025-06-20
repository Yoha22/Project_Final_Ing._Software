<template>
  <header class="header">
    <div class="header-left">      
      <span class="brand">Panela, Aroma y Sabor</span>
    </div>
    <nav class="header-nav">
      <router-link to="/">Home</router-link>
      <router-link to="/products">Products</router-link>
      <router-link to="/about">About Us</router-link>
      <router-link to="/contact">Contact</router-link>
    </nav>
    <div class="header-right">
      <!-- Usuario no autenticado -->
      <template v-if="!isAuthenticated">
        <router-link to="/login" class="btn btn-secondary">Iniciar Sesión</router-link>
        <router-link to="/register" class="btn btn-primary">Crear Cuenta</router-link>
      </template>
      
      <!-- Usuario autenticado -->
      <template v-else>
        <div class="user-menu">
          <button @click="toggleUserMenu" class="user-btn">
            <span class="material-icons">account_circle</span>
            <span class="user-name">{{ user?.name || 'Usuario' }}</span>
            <span class="material-icons">arrow_drop_down</span>
          </button>
          
          <div v-if="showUserMenu" class="user-dropdown">
            <div class="user-info">
              <span class="user-role">{{ userRoleText }}</span>
            </div>
            
            <!-- Opciones para clientes -->
            <template v-if="isClient">
              <router-link to="/client/profile" class="dropdown-item">
                <span class="material-icons">person</span>
                Mi Perfil
              </router-link>
              <router-link to="/client/orders" class="dropdown-item">
                <span class="material-icons">receipt_long</span>
                Mis Pedidos
              </router-link>
              <router-link to="/client/invoices" class="dropdown-item">
                <span class="material-icons">description</span>
                Mis Facturas
              </router-link>
            </template>
            
            <!-- Opciones para administradores -->
            <template v-if="isAdmin">
              <router-link to="/dashboard" class="dropdown-item">
                <span class="material-icons">dashboard</span>
                Dashboard
              </router-link>
              <router-link to="/dashboard/products" class="dropdown-item">
                <span class="material-icons">inventory</span>
                Productos
              </router-link>
              <router-link to="/dashboard/orders" class="dropdown-item">
                <span class="material-icons">receipt_long</span>
                Órdenes
              </router-link>
            </template>
            
            <div class="dropdown-divider"></div>
            <button @click="logout" class="dropdown-item logout-item">
              <span class="material-icons">logout</span>
              Cerrar Sesión
            </button>
          </div>
        </div>
        
        <!-- Carrito solo para clientes -->
        <button v-if="isClient" class="icon-btn cart-btn" @click="toggleCart">
          <span class="material-icons">shopping_cart</span>
          <span v-if="cartItemCount > 0" class="cart-badge">{{ cartItemCount }}</span>
        </button>
      </template>
    </div>
    
    <!-- Cart Modal -->
    <div v-if="showCart" class="cart-modal-overlay" @click="closeCart">
      <div class="cart-modal" @click.stop>
        <CartSummary />
        <button class="close-cart" @click="closeCart">×</button>
      </div>
    </div>
  </header>
</template>

<script>
import CartSummary from './CartSummary.vue';
import { useCartStore } from '../store/cart';
import { useAuthStore } from '../store/auth';

export default {
  name: 'HeaderNav',
  components: {
    CartSummary
  },
  data() {
    return {
      showCart: false,
      showUserMenu: false
    };
  },
  computed: {
    authStore() {
      return useAuthStore();
    },
    isAuthenticated() {
      return this.authStore.isAuthenticated;
    },
    user() {
      return this.authStore.user;
    },
    isAdmin() {
      return this.authStore.isAdmin;
    },
    isClient() {
      return this.authStore.isClient;
    },
    userRoleText() {
      return this.isAdmin ? 'Administrador' : 'Cliente';
    },
    cartItemCount() {
      const cart = useCartStore();
      return cart.items.reduce((total, item) => total + item.quantity, 0);
    }
  },
  mounted() {
    // Cerrar menú al hacer clic fuera
    document.addEventListener('click', this.handleClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside);
  },
  methods: {
    toggleCart() {
      this.showCart = !this.showCart;
    },
    closeCart() {
      this.showCart = false;
    },
    toggleUserMenu() {
      this.showUserMenu = !this.showUserMenu;
    },
    handleClickOutside(event) {
      if (!this.$el.contains(event.target)) {
        this.showUserMenu = false;
      }
    },
    async logout() {
      await this.authStore.logout();
      this.showUserMenu = false;
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 2rem;
  background: #fff;
  border-bottom: 1px solid #eee;
  box-shadow: 0 2px 8px 0 rgba(60,60,60,0.04);
}
.header-left {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.logo {
  width: 28px;
  height: 28px;
}
.brand {
  font-weight: 700;
  font-size: 1.2rem;
  margin-left: 0.5rem;
}
.header-nav {
  display: flex;
  gap: 2rem;
  flex: 1;
  justify-content: center;
}
.header-nav a {
  color: #222;
  font-weight: 500;
  text-decoration: none;
  font-size: 1.08rem;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  transition: background 0.2s, color 0.2s;
}
.header-nav a.router-link-exact-active,
.header-nav a:hover {
  background: #e6f0fa;
  color: #1976d2;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 1rem;
}
.btn {
  padding: 0.6rem 1.2rem;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.95rem;
  border: none;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}
.btn-primary {
  background: var(--primary-green);
  color: white;
}
.btn-primary:hover {
  background: var(--secondary-green);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(45, 90, 39, 0.3);
}
.btn-secondary {
  background: var(--gray-100);
  color: var(--gray-700);
  border: 1px solid var(--gray-300);
}
.btn-secondary:hover {
  background: var(--gray-200);
  color: var(--gray-800);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.icon-btn {
  background: #f5f5f5;
  border: none;
  border-radius: 6px;
  padding: 0.4rem 0.7rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: background 0.2s;
}
.icon-btn:hover {
  background: #e6f0fa;
}
.material-icons {
  font-size: 1.5rem;
  color: #222;
}
.close-cart {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #666;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background 0.2s;
}
.close-cart:hover {
  background: #f0f0f0;
}
.cart-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
  z-index: 1000;
  padding: 80px 20px 20px 20px;
}
.cart-modal {
  position: relative;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  max-width: 400px;
  width: 100%;
  max-height: 80vh;
  overflow-y: auto;
}
.cart-btn {
  position: relative;
}
.cart-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #ff4444;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 600;
  min-width: 20px;
}
.user-menu {
  position: relative;
}
.user-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: #f5f5f5;
  border: none;
  border-radius: 6px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: background 0.2s;
}
.user-btn:hover {
  background: #e6f0fa;
}
.user-name {
  font-weight: 500;
  color: #222;
}
.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.15);
  min-width: 200px;
  z-index: 1000;
  margin-top: 0.5rem;
}
.user-info {
  padding: 1rem;
  border-bottom: 1px solid #f0f0f0;
}
.user-role {
  font-size: 0.9rem;
  color: #6c757d;
  font-weight: 500;
}
.dropdown-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1rem;
  color: #222;
  text-decoration: none;
  transition: background 0.2s;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  cursor: pointer;
  font-size: 0.9rem;
}
.dropdown-item:hover {
  background: #f8f9fa;
}
.dropdown-item .material-icons {
  font-size: 1.2rem;
  color: #6c757d;
}
.dropdown-divider {
  height: 1px;
  background: #f0f0f0;
  margin: 0.5rem 0;
}
.logout-item {
  color: #dc3545;
}
.logout-item .material-icons {
  color: #dc3545;
}
</style> 