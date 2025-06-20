<template>
  <div class="products-page">
    <ProductList />
  </div>
</template>

<script>
import ProductList from '../components/ProductList.vue';
export default {
  components: { ProductList }
};
</script>

<style scoped>
.products-page {
  padding: 2rem;
}
</style> 