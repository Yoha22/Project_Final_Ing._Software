<template>
  <div class="client-invoices">
    <div class="invoices-header">
      <h2>Mis Facturas</h2>
      <div class="filters">
        <select v-model="statusFilter" class="filter-select">
          <option value="">Todos los estados</option>
          <option value="paid">Pagada</option>
          <option value="pending">Pendiente</option>
          <option value="overdue">Vencida</option>
        </select>
      </div>
    </div>
    
    <div class="invoices-list">
      <div v-for="invoice in filteredInvoices" :key="invoice.id" class="invoice-card">
        <div class="invoice-header">
          <div class="invoice-info">
            <h3>Factura #{{ invoice.id }}</h3>
            <p class="invoice-date">{{ formatDate(invoice.date) }}</p>
            <p class="invoice-order">Pedido #{{ invoice.orderId }}</p>
          </div>
          <div class="invoice-status">
            <span :class="['status-badge', invoice.status]">{{ getStatusText(invoice.status) }}</span>
          </div>
        </div>
        
        <div class="invoice-details">
          <div class="detail-row">
            <span>Subtotal:</span>
            <span>${{ invoice.subtotal }}</span>
          </div>
          <div class="detail-row">
            <span>IVA (19%):</span>
            <span>${{ invoice.tax }}</span>
          </div>
          <div class="detail-row total">
            <span>Total:</span>
            <span>${{ invoice.total }}</span>
          </div>
        </div>
        
        <div class="invoice-footer">
          <div class="payment-info">
            <p v-if="invoice.status === 'pending'">
              <strong>Vence:</strong> {{ formatDate(invoice.dueDate) }}
            </p>
            <p v-if="invoice.status === 'paid'">
              <strong>Pagada:</strong> {{ formatDate(invoice.paidDate) }}
            </p>
          </div>
          <div class="invoice-actions">
            <button @click="downloadInvoice(invoice)" class="btn-secondary">
              <span class="material-icons">download</span>
              Descargar
            </button>
            <button v-if="invoice.status === 'pending'" @click="payInvoice(invoice)" class="btn-primary">
              <span class="material-icons">payment</span>
              Pagar
            </button>
            <button @click="viewInvoiceDetails(invoice)" class="btn-secondary">
              <span class="material-icons">visibility</span>
              Ver Detalles
            </button>
          </div>
        </div>
      </div>
      
      <div v-if="filteredInvoices.length === 0" class="empty-invoices">
        <span class="material-icons">description</span>
        <h3>No tienes facturas</h3>
        <p>Cuando hagas tu primer pedido, aparecerá aquí la factura correspondiente.</p>
        <router-link to="/products" class="btn-primary">Ver Productos</router-link>
      </div>
    </div>
    
    <!-- Invoice Details Modal -->
    <div v-if="showInvoiceModal" class="modal-overlay" @click="closeInvoiceModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>Factura #{{ selectedInvoice?.id }}</h3>
          <button @click="closeInvoiceModal" class="close-btn">×</button>
        </div>
        <div class="modal-body">
          <div class="invoice-details-modal">
            <div class="detail-section">
              <h4>Información de la Factura</h4>
              <div class="detail-grid">
                <div class="detail-item">
                  <strong>Número:</strong> {{ selectedInvoice?.id }}
                </div>
                <div class="detail-item">
                  <strong>Fecha:</strong> {{ formatDate(selectedInvoice?.date) }}
                </div>
                <div class="detail-item">
                  <strong>Estado:</strong> {{ getStatusText(selectedInvoice?.status) }}
                </div>
                <div class="detail-item">
                  <strong>Pedido:</strong> #{{ selectedInvoice?.orderId }}
                </div>
              </div>
            </div>
            
            <div class="detail-section">
              <h4>Productos</h4>
              <div v-for="item in selectedInvoice?.items" :key="item.id" class="invoice-item">
                <div class="item-info">
                  <h5>{{ item.name }}</h5>
                  <p>Cantidad: {{ item.quantity }}</p>
                </div>
                <div class="item-prices">
                  <span>Precio unitario: ${{ item.unitPrice }}</span>
                  <span class="item-total">Total: ${{ item.total }}</span>
                </div>
              </div>
            </div>
            
            <div class="detail-section">
              <h4>Resumen de Pagos</h4>
              <div class="payment-summary">
                <div class="summary-row">
                  <span>Subtotal:</span>
                  <span>${{ selectedInvoice?.subtotal }}</span>
                </div>
                <div class="summary-row">
                  <span>IVA (19%):</span>
                  <span>${{ selectedInvoice?.tax }}</span>
                </div>
                <div class="summary-row total">
                  <span>Total:</span>
                  <span>${{ selectedInvoice?.total }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ClientInvoices',
  data() {
    return {
      statusFilter: '',
      showInvoiceModal: false,
      selectedInvoice: null,
      invoices: [
        {
          id: 'F001',
          orderId: '001',
          date: '2024-01-15',
          dueDate: '2024-01-30',
          paidDate: '2024-01-18',
          status: 'paid',
          subtotal: 105.46,
          tax: 20.04,
          total: 125.50,
          items: [
            {
              id: 1,
              name: 'Panela de Mandarina',
              quantity: 2,
              unitPrice: 45.00,
              total: 90.00
            },
            {
              id: 2,
              name: 'Tisana Limonaria',
              quantity: 1,
              unitPrice: 35.50,
              total: 35.50
            }
          ]
        },
        {
          id: 'F002',
          orderId: '002',
          date: '2024-01-10',
          dueDate: '2024-01-25',
          status: 'pending',
          subtotal: 75.62,
          tax: 14.37,
          total: 89.99,
          items: [
            {
              id: 3,
              name: 'Panela Helada Limón',
              quantity: 1,
              unitPrice: 89.99,
              total: 89.99
            }
          ]
        },
        {
          id: 'F003',
          orderId: '003',
          date: '2024-01-05',
          dueDate: '2024-01-20',
          status: 'overdue',
          subtotal: 131.72,
          tax: 25.03,
          total: 156.75,
          items: [
            {
              id: 4,
              name: 'Panela Grande Mandarina',
              quantity: 1,
              unitPrice: 156.75,
              total: 156.75
            }
          ]
        }
      ]
    };
  },
  computed: {
    filteredInvoices() {
      if (!this.statusFilter) {
        return this.invoices;
      }
      return this.invoices.filter(invoice => invoice.status === this.statusFilter);
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
        paid: 'Pagada',
        pending: 'Pendiente',
        overdue: 'Vencida'
      };
      return statusMap[status] || status;
    },
    viewInvoiceDetails(invoice) {
      this.selectedInvoice = invoice;
      this.showInvoiceModal = true;
    },
    closeInvoiceModal() {
      this.showInvoiceModal = false;
      this.selectedInvoice = null;
    },
    downloadInvoice(invoice) {
      // Aquí iría la lógica para descargar la factura
      console.log('Descargando factura:', invoice.id);
      alert('Descargando factura...');
    },
    payInvoice(invoice) {
      // Aquí iría la lógica para procesar el pago
      console.log('Procesando pago para factura:', invoice.id);
      alert('Redirigiendo al sistema de pagos...');
    }
  }
};
</script>

<style scoped>
.client-invoices {
  max-width: 1000px;
  margin: 0 auto;
}

.invoices-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.invoices-header h2 {
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

.invoices-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.invoice-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.invoice-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.15);
}

.invoice-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #f0f0f0;
}

.invoice-info h3 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1.2rem;
  font-weight: 600;
}

.invoice-date, .invoice-order {
  margin: 0 0 0.25rem 0;
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

.status-badge.paid {
  background: #d4edda;
  color: #155724;
}

.status-badge.pending {
  background: #fff3cd;
  color: #856404;
}

.status-badge.overdue {
  background: #f8d7da;
  color: #721c24;
}

.invoice-details {
  margin-bottom: 1rem;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.detail-row:last-child {
  border-bottom: none;
}

.detail-row.total {
  font-weight: 600;
  font-size: 1.1rem;
  color: #1a1a1a;
  border-top: 2px solid #f0f0f0;
  padding-top: 1rem;
  margin-top: 0.5rem;
}

.invoice-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid #f0f0f0;
}

.payment-info p {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.invoice-actions {
  display: flex;
  gap: 0.5rem;
}

.btn-primary, .btn-secondary {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background: #2d5a27;
  color: white;
}

.btn-primary:hover {
  background: #1e3d1a;
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
}

.empty-invoices {
  text-align: center;
  padding: 3rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.empty-invoices .material-icons {
  font-size: 4rem;
  color: #adb5bd;
  margin-bottom: 1rem;
}

.empty-invoices h3 {
  margin: 0 0 0.5rem 0;
  color: #1a1a1a;
  font-size: 1.3rem;
}

.empty-invoices p {
  margin: 0 0 1.5rem 0;
  color: #6c757d;
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
  max-width: 700px;
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

.detail-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.detail-item {
  padding: 0.75rem;
  background: #f8f9fa;
  border-radius: 6px;
  color: #6c757d;
}

.invoice-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.invoice-item:last-child {
  border-bottom: none;
}

.item-info h5 {
  margin: 0 0 0.25rem 0;
  color: #1a1a1a;
  font-size: 1rem;
}

.item-info p {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.item-prices {
  text-align: right;
}

.item-prices span {
  display: block;
  color: #6c757d;
  font-size: 0.9rem;
}

.item-total {
  font-weight: 600;
  color: #1a1a1a;
  margin-top: 0.25rem;
}

.payment-summary {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1rem;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px solid #e9ecef;
}

.summary-row:last-child {
  border-bottom: none;
}

.summary-row.total {
  font-weight: 600;
  font-size: 1.1rem;
  color: #1a1a1a;
  border-top: 2px solid #e9ecef;
  padding-top: 1rem;
  margin-top: 0.5rem;
}

@media (max-width: 768px) {
  .invoices-header {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }
  
  .invoice-footer {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }
  
  .invoice-actions {
    justify-content: center;
  }
  
  .invoice-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
  
  .item-prices {
    text-align: left;
  }
}
</style> 