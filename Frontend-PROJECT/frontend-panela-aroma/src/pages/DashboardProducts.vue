<script>
import ProductService from '../services/ProductService';
export default {
  data() {
    return {
      products: []
    };
  },
  async created() {
    try {
      const res = await ProductService.getAll();
      this.products = res.data;
    } catch (e) {
      this.products = [];
    }
  }
};
</script>

<template>
  <div>
    <h2>Productos</h2>
    <table v-if="products.length" class="products-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Precio</th>
          <th>Stock</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in products" :key="p.id">
          <td>{{ p.id }}</td>
          <td>{{ p.name }}</td>
          <td>{{ p.description }}</td>
          <td>${{ p.price }}</td>
          <td>{{ p.stock }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else>No hay productos para mostrar.</p>
  </div>
</template>

<style scoped>
.products-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1.5rem;
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 8px 0 rgba(60,60,60,0.06);
}
.products-table th, .products-table td {
  padding: 0.8rem 1rem;
  border-bottom: 1px solid #eee;
  text-align: left;
}
.products-table th {
  background: #f6f8fa;
  font-weight: 700;
}
.products-table tr:last-child td {
  border-bottom: none;
}
</style> 