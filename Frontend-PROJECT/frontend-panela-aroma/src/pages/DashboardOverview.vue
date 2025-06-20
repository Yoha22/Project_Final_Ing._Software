<template>
  <div class="dashboard-overview">
    <!-- Stats Cards -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon orders">
          <span class="material-icons">receipt_long</span>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalOrders }}</h3>
          <p>Total Pedidos</p>
          <span class="stat-change positive">+12% este mes</span>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon revenue">
          <span class="material-icons">attach_money</span>
        </div>
        <div class="stat-content">
          <h3>${{ stats.totalRevenue.toLocaleString() }}</h3>
          <p>Ingresos Totales</p>
          <span class="stat-change positive">+8% este mes</span>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon products">
          <span class="material-icons">inventory</span>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalProducts }}</h3>
          <p>Productos Activos</p>
          <span class="stat-change neutral">Sin cambios</span>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon customers">
          <span class="material-icons">people</span>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalCustomers }}</h3>
          <p>Clientes Registrados</p>
          <span class="stat-change positive">+5% este mes</span>
        </div>
      </div>
    </div>
    
    <!-- Recent Activity -->
    <div class="content-grid">
      <div class="recent-orders">
        <div class="section-header">
          <h2>Pedidos Recientes</h2>
          <router-link to="/dashboard/orders" class="view-all">Ver todos</router-link>
        </div>
        <div class="orders-list">
          <div v-for="order in recentOrders" :key="order.id" class="order-item">
            <div class="order-info">
              <h4>Pedido #{{ order.id }}</h4>
              <p>{{ order.customerName }}</p>
              <span class="order-date">{{ formatDate(order.date) }}</span>
            </div>
            <div class="order-status">
              <span :class="['status-badge', order.status]">{{ order.status }}</span>
              <span class="order-amount">${{ order.amount }}</span>
            </div>
          </div>
        </div>
      </div>
      
      <div class="low-stock">
        <div class="section-header">
          <h2>Productos con Bajo Stock</h2>
          <router-link to="/dashboard/inventory" class="view-all">Ver inventario</router-link>
        </div>
        <div class="stock-list">
          <div v-for="product in lowStockProducts" :key="product.id" class="stock-item">
            <img :src="product.image" :alt="product.name" class="product-img" />
            <div class="product-info">
              <h4>{{ product.name }}</h4>
              <p>Stock: {{ product.stock }} unidades</p>
            </div>
            <div class="stock-warning">
              <span class="material-icons">warning</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Quick Actions -->
    <div class="quick-actions">
      <h2>Acciones Rápidas</h2>
      <div class="actions-grid">
        <router-link to="/dashboard/products" class="action-card">
          <span class="material-icons">add</span>
          <h3>Agregar Producto</h3>
          <p>Crear un nuevo producto en el catálogo</p>
        </router-link>
        
        <router-link to="/dashboard/orders" class="action-card">
          <span class="material-icons">receipt_long</span>
          <h3>Ver Pedidos</h3>
          <p>Gestionar pedidos pendientes</p>
        </router-link>
        
        <router-link to="/dashboard/inventory" class="action-card">
          <span class="material-icons">warehouse</span>
          <h3>Gestionar Inventario</h3>
          <p>Actualizar stock de productos</p>
        </router-link>
        
        <router-link to="/dashboard/clients" class="action-card">
          <span class="material-icons">people</span>
          <h3>Ver Clientes</h3>
          <p>Administrar base de clientes</p>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DashboardOverview',
  data() {
    return {
      stats: {
        totalOrders: 156,
        totalRevenue: 45230,
        totalProducts: 24,
        totalCustomers: 89
      },
      recentOrders: [
        {
          id: '001',
          customerName: 'María González',
          date: '2024-01-15',
          status: 'pending',
          amount: 125.50
        },
        {
          id: '002',
          customerName: 'Carlos Rodríguez',
          date: '2024-01-14',
          status: 'completed',
          amount: 89.99
        },
        {
          id: '003',
          customerName: 'Ana Martínez',
          date: '2024-01-13',
          status: 'processing',
          amount: 156.75
        }
      ],
      lowStockProducts: [
        {
          id: 1,
          name: 'Panela de Mandarina',
          stock: 5,
          image: '/src/assets/Panela_Mandarina_X20.jpg'
        },
        {
          id: 2,
          name: 'Tisana Limonaria',
          stock: 3,
          image: '/src/assets/Tisana_Limonaria.jpg'
        }
      ]
    };
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
    }
  }
};
</script>

<style scoped>
.dashboard-overview {
  max-width: 1200px;
  margin: 0 auto;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.15);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-icon.orders {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.revenue {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.products {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.customers {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-icon .material-icons {
  font-size: 2rem;
}

.stat-content h3 {
  margin: 0 0 0.25rem 0;
  font-size: 1.8rem;
  font-weight: 700;
  color: #1a1a1a;
}

.stat-content p {
  margin: 0 0 0.5rem 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.stat-change {
  font-size: 0.8rem;
  font-weight: 600;
}

.stat-change.positive {
  color: #28a745;
}

.stat-change.negative {
  color: #dc3545;
}

.stat-change.neutral {
  color: #6c757d;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin-bottom: 2rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.section-header h2 {
  margin: 0;
  color: #1a1a1a;
  font-size: 1.3rem;
  font-weight: 600;
}

.view-all {
  color: #667eea;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 500;
}

.view-all:hover {
  text-decoration: underline;
}

.recent-orders, .low-stock {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.orders-list, .stock-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.order-item, .stock-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-radius: 8px;
  background: #f8f9fa;
  transition: background 0.2s;
}

.order-item:hover, .stock-item:hover {
  background: #e9ecef;
}

.order-info h4 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1rem;
}

.order-info p {
  margin: 0 0 0.25rem 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.order-date {
  font-size: 0.8rem;
  color: #adb5bd;
}

.order-status {
  text-align: right;
}

.status-badge {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
  margin-bottom: 0.5rem;
}

.status-badge.pending {
  background: #fff3cd;
  color: #856404;
}

.status-badge.processing {
  background: #cce5ff;
  color: #004085;
}

.status-badge.completed {
  background: #d4edda;
  color: #155724;
}

.order-amount {
  display: block;
  font-weight: 600;
  color: #1a1a1a;
}

.stock-item {
  align-items: center;
}

.product-img {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  object-fit: cover;
}

.product-info {
  flex: 1;
  margin-left: 1rem;
}

.product-info h4 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1rem;
}

.product-info p {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.stock-warning {
  color: #ffc107;
}

.quick-actions {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.quick-actions h2 {
  margin: 0 0 1.5rem 0;
  color: #1a1a1a;
  font-size: 1.3rem;
  font-weight: 600;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.action-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 1.5rem;
  border-radius: 8px;
  background: #f8f9fa;
  text-decoration: none;
  color: inherit;
  transition: all 0.2s;
}

.action-card:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.action-card .material-icons {
  font-size: 2.5rem;
  color: #667eea;
  margin-bottom: 1rem;
}

.action-card h3 {
  margin: 0 0 0.5rem 0;
  color: #1a1a1a;
  font-size: 1.1rem;
  font-weight: 600;
}

.action-card p {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }
  
  .actions-grid {
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  }
}
</style> 