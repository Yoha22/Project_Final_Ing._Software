<template>
  <div class="product-card">
    <h4>{{ product.name }}</h4>
    <p>{{ product.description }}</p>
    <p>$ {{ product.price }}</p>
    <button @click="addToCart">Add to Cart</button>
  </div>
</template>

<script>
export default {
  props: ['product'],
  methods: {
    addToCart() {
      this.$emit('add-to-cart', this.product);
    }
  }
};
</script>

<style scoped>
.product-card {
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 1rem;
  width: 160px;
  background: #fafafa;
  text-align: center;
}
.product-card h4 {
  margin-bottom: 0.5rem;
}
.product-card button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background: #222;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style> 