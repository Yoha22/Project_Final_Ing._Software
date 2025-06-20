<template>
  <div class="product-list">
    <ProductCard v-for="product in products" :key="product.id" :product="product" @add-to-cart="addToCart" />
  </div>
</template>

<script>
import ProductCard from './ProductCard.vue';
import ProductService from '../services/ProductService';
import { useCartStore } from '../store/cart';

export default {
  components: { ProductCard },
  data() {
    return {
      products: []
    };
  },
  async created() {
    const res = await ProductService.getAll();
    this.products = res.data;
  },
  methods: {
    addToCart(product) {
      const cart = useCartStore();
      cart.addToCart(product);
    }
  }
};
</script>

<style scoped>
.product-list {
  display: flex;
  gap: 1.5rem;
}
</style> 