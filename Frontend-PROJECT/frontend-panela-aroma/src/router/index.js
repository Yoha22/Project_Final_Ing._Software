import { createRouter, createWebHistory } from 'vue-router';
import Home from '../pages/Home.vue';
import Products from '../pages/Products.vue';
import Login from '../pages/Login.vue';
import Register from '../pages/Register.vue';
import Dashboard from '../pages/Dashboard.vue';
import ClientDashboard from '../pages/ClientDashboard.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/products', component: Products },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  
  // Rutas para clientes
  {
    path: '/client',
    component: ClientDashboard,
    meta: { requiresAuth: true, role: 'CLIENT' },
    children: [
      { path: '', redirect: '/client/profile' },
      { path: 'profile', component: () => import('../pages/ClientProfile.vue') },
      { path: 'orders', component: () => import('../pages/ClientOrders.vue') },
      { path: 'invoices', component: () => import('../pages/ClientInvoices.vue') }
    ]
  },
  
  // Rutas para administradores
  {
    path: '/dashboard',
    component: Dashboard,
    meta: { requiresAuth: true, role: 'ADMIN' },
    children: [
      { path: '', redirect: '/dashboard/overview' },
      { path: 'overview', component: () => import('../pages/DashboardOverview.vue') },
      { path: 'products', component: () => import('../pages/DashboardProducts.vue') },
      { path: 'users', component: () => import('../pages/DashboardUsers.vue') },
      { path: 'orders', component: () => import('../pages/DashboardOrders.vue') },
      { path: 'invoices', component: () => import('../pages/DashboardInvoices.vue') },
      { path: 'inventory', component: () => import('../pages/DashboardInventory.vue') },
      { path: 'clients', component: () => import('../pages/DashboardClients.vue') },
      { path: 'settings', component: () => import('../pages/DashboardSettings.vue') }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('userRole');
  
  if (to.meta.requiresAuth && !token) {
    next('/login');
  } else if (to.meta.role && to.meta.role !== userRole) {
    // Redirigir según el rol
    if (userRole === 'ADMIN') {
      next('/dashboard');
    } else if (userRole === 'CLIENT') {
      next('/client');
    } else {
      next('/');
    }
  } else {
    next();
  }
});

export default router; 