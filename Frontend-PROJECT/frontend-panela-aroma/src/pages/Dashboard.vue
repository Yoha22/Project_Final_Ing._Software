<template>
  <div class="admin-dashboard">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
      <div class="sidebar-header">
        <img src="/src/assets/Logo.png" alt="Logo" class="logo" />
        <h3 v-if="!sidebarCollapsed">Admin Panel</h3>
        <button @click="toggleSidebar" class="toggle-btn">
          <span class="material-icons">{{ sidebarCollapsed ? 'menu' : 'close' }}</span>
        </button>
      </div>
      
      <nav class="sidebar-nav">
        <router-link to="/dashboard/overview" class="nav-item" active-class="active">
          <span class="material-icons">dashboard</span>
          <span v-if="!sidebarCollapsed">Dashboard</span>
        </router-link>
        
        <router-link to="/dashboard/products" class="nav-item" active-class="active">
          <span class="material-icons">inventory</span>
          <span v-if="!sidebarCollapsed">Productos</span>
        </router-link>
        
        <router-link to="/dashboard/inventory" class="nav-item" active-class="active">
          <span class="material-icons">warehouse</span>
          <span v-if="!sidebarCollapsed">Inventario</span>
        </router-link>
        
        <router-link to="/dashboard/orders" class="nav-item" active-class="active">
          <span class="material-icons">receipt_long</span>
          <span v-if="!sidebarCollapsed">Órdenes</span>
        </router-link>
        
        <router-link to="/dashboard/clients" class="nav-item" active-class="active">
          <span class="material-icons">people</span>
          <span v-if="!sidebarCollapsed">Clientes</span>
        </router-link>
        
        <router-link to="/dashboard/invoices" class="nav-item" active-class="active">
          <span class="material-icons">description</span>
          <span v-if="!sidebarCollapsed">Facturas</span>
        </router-link>
        
        <router-link to="/dashboard/users" class="nav-item" active-class="active">
          <span class="material-icons">admin_panel_settings</span>
          <span v-if="!sidebarCollapsed">Usuarios</span>
        </router-link>
        
        <router-link to="/dashboard/settings" class="nav-item" active-class="active">
          <span class="material-icons">settings</span>
          <span v-if="!sidebarCollapsed">Configuración</span>
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
            <span class="user-name">{{ user?.name || 'Administrador' }}</span>
            <span class="user-role">Administrador</span>
          </div>
        </div>
      </header>
      
      <div class="content">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script>
import { useAuthStore } from '../store/auth';

export default {
  name: 'Dashboard',
  data() {
    return {
      sidebarCollapsed: false
    };
  },
  computed: {
    user() {
      return useAuthStore().user;
    },
    pageTitle() {
      const route = this.$route.name;
      const titles = {
        'DashboardOverview': 'Dashboard',
        'DashboardProducts': 'Gestión de Productos',
        'DashboardInventory': 'Gestión de Inventario',
        'DashboardOrders': 'Gestión de Órdenes',
        'DashboardClients': 'Gestión de Clientes',
        'DashboardInvoices': 'Gestión de Facturas',
        'DashboardUsers': 'Gestión de Usuarios',
        'DashboardSettings': 'Configuración'
      };
      return titles[route] || 'Dashboard';
    },
    breadcrumb() {
      return `Administrador > ${this.pageTitle}`;
    }
  },
  methods: {
    toggleSidebar() {
      this.sidebarCollapsed = !this.sidebarCollapsed;
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
.admin-dashboard {
  display: flex;
  min-height: 100vh;
  background: #f8f9fa;
}

.sidebar {
  width: 280px;
  background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
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
  color: #1a1a1a;
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
  color: #1a1a1a;
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