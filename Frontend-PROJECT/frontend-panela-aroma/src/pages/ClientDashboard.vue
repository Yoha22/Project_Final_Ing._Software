<template>
  <div class="client-dashboard">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
      <div class="sidebar-header">
        <img src="/src/assets/Logo.png" alt="Logo" class="logo" />
        <h3 v-if="!sidebarCollapsed">Panela, Aroma y Sabor</h3>
        <button @click="toggleSidebar" class="toggle-btn">
          <span class="material-icons">{{ sidebarCollapsed ? 'menu' : 'close' }}</span>
        </button>
      </div>
      
      <nav class="sidebar-nav">
        <router-link to="/client/profile" class="nav-item" active-class="active">
          <span class="material-icons">person</span>
          <span v-if="!sidebarCollapsed">Mi Perfil</span>
        </router-link>
        
        <router-link to="/products" class="nav-item" active-class="active">
          <span class="material-icons">store</span>
          <span v-if="!sidebarCollapsed">Ver Productos</span>
        </router-link>
        
        <button @click="toggleCart" class="nav-item">
          <span class="material-icons">shopping_cart</span>
          <span v-if="!sidebarCollapsed">Mi Carrito</span>
          <span v-if="!sidebarCollapsed && cartItemCount > 0" class="cart-badge">{{ cartItemCount }}</span>
        </button>
        
        <router-link to="/client/orders" class="nav-item" active-class="active">
          <span class="material-icons">receipt_long</span>
          <span v-if="!sidebarCollapsed">Mis Pedidos</span>
        </router-link>
        
        <router-link to="/client/invoices" class="nav-item" active-class="active">
          <span class="material-icons">description</span>
          <span v-if="!sidebarCollapsed">Mis Facturas</span>
        </router-link>
      </nav>
      
      <div class="sidebar-footer">
        <button @click="logout" class="logout-btn">
          <span class="material-icons">logout</span>
          <span v-if="!sidebarCollapsed">Cerrar Sesión</span>
        </button>
      </div>
    </aside>
    
    <!-- Main Content -->
    <main class="main-content">
      <header class="content-header">
        <div class="header-left">
          <h1>{{ pageTitle }}</h1>
          <p class="breadcrumb">{{ breadcrumb }}</p>
        </div>
        <div class="header-right">
          <div class="user-info">
            <span class="user-name">{{ user?.name || 'Usuario' }}</span>
            <span class="user-role">Cliente</span>
          </div>
        </div>
      </header>
      
      <div class="content">
        <router-view />
      </div>
    </main>
    
    <!-- Cart Modal -->
    <div v-if="showCart" class="cart-modal-overlay" @click="closeCart">
      <div class="cart-modal" @click.stop>
        <CartSummary />
        <button class="close-cart" @click="closeCart">×</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../store/auth';
import { useCartStore } from '../store/cart';
import CartSummary from '../components/CartSummary.vue';

export default {
  name: 'ClientDashboard',
  components: {
    CartSummary
  },
  data() {
    return {
      sidebarCollapsed: false,
      showCart: false
    };
  },
  computed: {
    user() {
      return useAuthStore().user;
    },
    cartItemCount() {
      const cart = useCartStore();
      return cart.items.reduce((total, item) => total + item.quantity, 0);
    },
    pageTitle() {
      const route = this.$route.name;
      const titles = {
        'ClientProfile': 'Mi Perfil',
        'ClientOrders': 'Mis Pedidos',
        'ClientInvoices': 'Mis Facturas'
      };
      return titles[route] || 'Dashboard';
    },
    breadcrumb() {
      return `Cliente > ${this.pageTitle}`;
    }
  },
  methods: {
    toggleSidebar() {
      this.sidebarCollapsed = !this.sidebarCollapsed;
    },
    toggleCart() {
      this.showCart = !this.showCart;
    },
    closeCart() {
      this.showCart = false;
    },
    async logout() {
      const authStore = useAuthStore();
      await authStore.logout();
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.client-dashboard {
  display: flex;
  min-height: 100vh;
  background: #f8f9fa;
}

.sidebar {
  width: 280px;
  background: linear-gradient(135deg, #2d5a27 0%, #4a7c59 100%);
  color: white;
  display: flex;
  flex-direction: column;
  transition: width 0.3s ease;
  box-shadow: 2px 0 10px rgba(0,0,0,0.1);
}

.sidebar-collapsed {
  width: 70px;
}

.sidebar-header {
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}

.logo {
  width: 40px;
  height: 40px;
  border-radius: 8px;
}

.sidebar-header h3 {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 600;
  flex: 1;
}

.toggle-btn {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 6px;
  transition: background 0.2s;
}

.toggle-btn:hover {
  background: rgba(255,255,255,0.1);
}

.sidebar-nav {
  flex: 1;
  padding: 1rem 0;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 1.5rem;
  color: white;
  text-decoration: none;
  transition: background 0.2s;
  position: relative;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  cursor: pointer;
}

.nav-item:hover {
  background: rgba(255,255,255,0.1);
}

.nav-item.active {
  background: rgba(255,255,255,0.2);
  border-right: 3px solid #ffd700;
}

.nav-item .material-icons {
  font-size: 1.3rem;
  min-width: 24px;
}

.cart-badge {
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
  margin-left: auto;
}

.sidebar-footer {
  padding: 1rem 1.5rem;
  border-top: 1px solid rgba(255,255,255,0.1);
}

.logout-btn {
  display: flex;
  align-items: center;
  gap: 1rem;
  width: 100%;
  padding: 0.75rem;
  background: rgba(255,255,255,0.1);
  border: none;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.logout-btn:hover {
  background: rgba(255,255,255,0.2);
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.content-header {
  background: white;
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left h1 {
  margin: 0 0 0.5rem 0;
  color: #2d5a27;
  font-size: 1.8rem;
  font-weight: 700;
}

.breadcrumb {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.user-info {
  text-align: right;
}

.user-name {
  display: block;
  font-weight: 600;
  color: #2d5a27;
}

.user-role {
  display: block;
  font-size: 0.9rem;
  color: #6c757d;
}

.content {
  flex: 1;
  padding: 2rem;
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

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: -280px;
    z-index: 1000;
    height: 100vh;
  }
  
  .sidebar:not(.sidebar-collapsed) {
    left: 0;
  }
  
  .main-content {
    margin-left: 0;
  }
  
  .content-header {
    padding: 1rem;
  }
  
  .content {
    padding: 1rem;
  }
}
</style> 