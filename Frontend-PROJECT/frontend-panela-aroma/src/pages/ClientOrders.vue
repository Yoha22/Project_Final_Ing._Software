<template>
  <div class="client-orders">
    <div class="orders-header">
      <h2>Mis Pedidos</h2>
      <div class="filters">
        <select v-model="statusFilter" class="filter-select">
          <option value="">Todos los estados</option>
          <option value="pending">Pendiente</option>
          <option value="processing">En proceso</option>
          <option value="completed">Completado</option>
          <option value="cancelled">Cancelado</option>
        </select>
      </div>
    </div>
    
    <div class="orders-list">
      <div v-for="order in filteredOrders" :key="order.id" class="order-card">
        <div class="order-header">
          <div class="order-info">
            <h3>Pedido #{{ order.id }}</h3>
            <p class="order-date">{{ formatDate(order.date) }}</p>
          </div>
          <div class="order-status">
            <span :class="['status-badge', order.status]">{{ getStatusText(order.status) }}</span>
          </div>
        </div>
        
        <div class="order-items">
          <div v-for="item in order.items" :key="item.id" class="order-item">
            <img :src="item.image" :alt="item.name" class="item-image" />
            <div class="item-details">
              <h4>{{ item.name }}</h4>
              <p>Cantidad: {{ item.quantity }}</p>
              <span class="item-price">${{ item.price }}</span>
            </div>
          </div>
        </div>
        
        <div class="order-footer">
          <div class="order-total">
            <span>Total: <strong>${{ order.total }}</strong></span>
          </div>
          <div class="order-actions">
            <button @click="viewOrderDetails(order)" class="btn-secondary">Ver Detalles</button>
            <button v-if="order.status === 'pending'" @click="cancelOrder(order.id)" class="btn-danger">Cancelar</button>
          </div>
        </div>
      </div>
      
      <div v-if="filteredOrders.length === 0" class="empty-orders">
        <span class="material-icons">receipt_long</span>
        <h3>No tienes pedidos</h3>
        <p>Cuando hagas tu primer pedido, aparecerá aquí.</p>
        <router-link to="/products" class="btn-primary">Ver Productos</router-link>
      </div>
    </div>
    
    <!-- Order Details Modal -->
    <div v-if="showOrderModal" class="modal-overlay" @click="closeOrderModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>Detalles del Pedido #{{ selectedOrder?.id }}</h3>
          <button @click="closeOrderModal" class="close-btn">×</button>
        </div>
        <div class="modal-body">
          <div class="order-details">
            <div class="detail-section">
              <h4>Información del Pedido</h4>
              <p><strong>Fecha:</strong> {{ formatDate(selectedOrder?.date) }}</p>
              <p><strong>Estado:</strong> {{ getStatusText(selectedOrder?.status) }}</p>
              <p><strong>Total:</strong> ${{ selectedOrder?.total }}</p>
            </div>
            
            <div class="detail-section">
              <h4>Productos</h4>
              <div v-for="item in selectedOrder?.items" :key="item.id" class="detail-item">
                <img :src="item.image" :alt="item.name" class="detail-item-image" />
                <div class="detail-item-info">
                  <h5>{{ item.name }}</h5>
                  <p>Cantidad: {{ item.quantity }}</p>
                  <p>Precio: ${{ item.price }}</p>
                </div>
              </div>
            </div>
            
            <div class="detail-section">
              <h4>Dirección de Entrega</h4>
              <p>{{ selectedOrder?.shippingAddress }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ClientOrders',
  data() {
    return {
      statusFilter: '',
      showOrderModal: false,
      selectedOrder: null,
      orders: [
        {
          id: '001',
          date: '2024-01-15',
          status: 'completed',
          total: 125.50,
          shippingAddress: 'Calle Principal 123, Ciudad',
          items: [
            {
              id: 1,
              name: 'Panela de Mandarina',
              quantity: 2,
              price: 45.00,
              image: '/src/assets/Panela_Mandarina_X20.jpg'
            },
            {
              id: 2,
              name: 'Tisana Limonaria',
              quantity: 1,
              price: 35.50,
              image: '/src/assets/Tisana_Limonaria.jpg'
            }
          ]
        },
        {
          id: '002',
          date: '2024-01-10',
          status: 'processing',
          total: 89.99,
          shippingAddress: 'Calle Principal 123, Ciudad',
          items: [
            {
              id: 3,
              name: 'Panela Helada Limón',
              quantity: 1,
              price: 89.99,
              image: '/src/assets/Panela_Helada_Limon.jpg'
            }
          ]
        },
        {
          id: '003',
          date: '2024-01-05',
          status: 'pending',
          total: 156.75,
          shippingAddress: 'Calle Principal 123, Ciudad',
          items: [
            {
              id: 4,
              name: 'Panela Grande Mandarina',
              quantity: 1,
              price: 156.75,
              image: '/src/assets/Panela_Grande_Mandarina.jpg'
            }
          ]
        }
      ]
    };
  },
  computed: {
    filteredOrders() {
      if (!this.statusFilter) {
        return this.orders;
      }
      return this.orders.filter(order => order.status === this.statusFilter);
    }
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
    },
    getStatusText(status) {
      const statusMap = {
        pending: 'Pendiente',
        processing: 'En proceso',
        completed: 'Completado',
        cancelled: 'Cancelado'
      };
      return statusMap[status] || status;
    },
    viewOrderDetails(order) {
      this.selectedOrder = order;
      this.showOrderModal = true;
    },
    closeOrderModal() {
      this.showOrderModal = false;
      this.selectedOrder = null;
    },
    async cancelOrder(orderId) {
      if (confirm('¿Estás seguro de que quieres cancelar este pedido?')) {
        try {
          // Aquí iría la llamada al servicio para cancelar el pedido
          console.log('Cancelando pedido:', orderId);
          const orderIndex = this.orders.findIndex(order => order.id === orderId);
          if (orderIndex !== -1) {
            this.orders[orderIndex].status = 'cancelled';
          }
          alert('Pedido cancelado correctamente');
        } catch (error) {
          console.error('Error al cancelar pedido:', error);
          alert('Error al cancelar el pedido');
        }
      }
    }
  }
};
</script>

<style scoped>
.client-orders {
  max-width: 1000px;
  margin: 0 auto;
}

.orders-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.orders-header h2 {
  margin: 0;
  color: #1a1a1a;
  font-size: 1.8rem;
  font-weight: 600;
}

.filter-select {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  background: white;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.order-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.order-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.15);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #f0f0f0;
}

.order-info h3 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1.2rem;
  font-weight: 600;
}

.order-date {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
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

.status-badge.cancelled {
  background: #f8d7da;
  color: #721c24;
}

.order-items {
  margin-bottom: 1rem;
}

.order-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.order-item:last-child {
  border-bottom: none;
}

.item-image {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  object-fit: cover;
}

.item-details h4 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1rem;
}

.item-details p {
  margin: 0 0 0.25rem 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.item-price {
  font-weight: 600;
  color: #2d5a27;
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid #f0f0f0;
}

.order-total {
  font-size: 1.1rem;
  color: #1a1a1a;
}

.order-actions {
  display: flex;
  gap: 0.5rem;
}

.btn-secondary, .btn-danger {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
}

.btn-danger {
  background: #dc3545;
  color: white;
}

.btn-danger:hover {
  background: #c82333;
}

.empty-orders {
  text-align: center;
  padding: 3rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.empty-orders .material-icons {
  font-size: 4rem;
  color: #adb5bd;
  margin-bottom: 1rem;
}

.empty-orders h3 {
  margin: 0 0 0.5rem 0;
  color: #1a1a1a;
  font-size: 1.3rem;
}

.empty-orders p {
  margin: 0 0 1.5rem 0;
  color: #6c757d;
}

.btn-primary {
  padding: 0.75rem 1.5rem;
  background: #2d5a27;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
  transition: background 0.2s;
}

.btn-primary:hover {
  background: #1e3d1a;
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}

.modal-content {
  background: white;
  border-radius: 12px;
  max-width: 600px;
  width: 100%;
  max-height: 80vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid #f0f0f0;
}

.modal-header h3 {
  margin: 0;
  color: #1a1a1a;
  font-size: 1.3rem;
  font-weight: 600;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #6c757d;
  padding: 0.5rem;
  border-radius: 50%;
  transition: background 0.2s;
}

.close-btn:hover {
  background: #f0f0f0;
}

.modal-body {
  padding: 1.5rem;
}

.detail-section {
  margin-bottom: 2rem;
}

.detail-section h4 {
  margin: 0 0 1rem 0;
  color: #1a1a1a;
  font-size: 1.1rem;
  font-weight: 600;
}

.detail-section p {
  margin: 0 0 0.5rem 0;
  color: #6c757d;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.detail-item:last-child {
  border-bottom: none;
}

.detail-item-image {
  width: 50px;
  height: 50px;
  border-radius: 6px;
  object-fit: cover;
}

.detail-item-info h5 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1rem;
}

.detail-item-info p {
  margin: 0 0 0.25rem 0;
  color: #6c757d;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .orders-header {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }
  
  .order-footer {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }
  
  .order-actions {
    justify-content: center;
  }
}
</style> 