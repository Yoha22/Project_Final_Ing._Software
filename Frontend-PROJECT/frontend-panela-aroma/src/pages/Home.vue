<template>
  <div class="home">
    <!-- Hero Section -->
    <section class="hero">
      <div class="hero-content">
        <div class="hero-text">
          <h1 class="hero-title">
            Descubre el <span class="highlight">Aroma y Sabor</span> Natural
          </h1>
          <p class="hero-subtitle">
            Productos artesanales de panela y aromáticas que conectan con la naturaleza. 
            Sabor auténtico, calidad premium.
          </p>
          <div class="hero-actions">
            <router-link to="/register" class="btn btn-primary btn-large">
              <span class="material-icons">person_add</span>
              Crear Cuenta
            </router-link>
            <router-link to="/login" class="btn btn-outline btn-large">
              <span class="material-icons">login</span>
              Iniciar Sesión
            </router-link>
          </div>
        </div>
        <div class="hero-image">
          <img src="/src/assets/Logo.png" alt="Panela, Aroma y Sabor" class="hero-logo" />
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="features">
      <div class="container">
        <h2 class="section-title">¿Por qué elegirnos?</h2>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <span class="material-icons">eco</span>
            </div>
            <h3>100% Natural</h3>
            <p>Productos elaborados con ingredientes naturales sin conservantes artificiales</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <span class="material-icons">local_shipping</span>
            </div>
            <h3>Envío Rápido</h3>
            <p>Entrega a domicilio en toda la ciudad con seguimiento en tiempo real</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <span class="material-icons">verified</span>
            </div>
            <h3>Calidad Garantizada</h3>
            <p>Productos de la más alta calidad con certificaciones de sanidad</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Products Preview -->
    <section class="products-preview">
      <div class="container">
        <h2 class="section-title">Nuestros Productos Destacados</h2>
        <div class="products-grid">
          <div class="product-card" v-for="product in featured" :key="product.id">
            <img :src="product.image" :alt="product.name" class="product-img" />
            <div class="product-info">
              <h3 class="product-name">{{ product.name }}</h3>
              <p class="product-description">{{ product.description || 'Producto artesanal de alta calidad' }}</p>
              <div class="product-price">${{ product.price }}</div>
            </div>
          </div>
        </div>
        <div class="products-cta">
          <router-link to="/products" class="btn btn-primary">
            <span class="material-icons">store</span>
            Ver Todos los Productos
          </router-link>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
      <div class="container">
        <div class="cta-content">
          <h2>¿Listo para descubrir nuestros sabores?</h2>
          <p>Únete a nuestra comunidad y disfruta de productos naturales de la más alta calidad</p>
          <div class="cta-actions">
            <router-link to="/register" class="btn btn-primary btn-large">
              <span class="material-icons">person_add</span>
              Crear Cuenta Gratis
            </router-link>
            <router-link to="/login" class="btn btn-outline btn-large">
              <span class="material-icons">login</span>
              Ya tengo cuenta
            </router-link>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import ProductService from '../services/ProductService';

export default {
  name: 'Home',
  data() {
    return {
      featured: []
    };
  },
  async created() {
    try {
      const res = await ProductService.getAll();
      this.featured = res.data.slice(0, 3);
    } catch (e) {
      // Productos de ejemplo si no hay datos
      this.featured = [
        {
          id: 1,
          name: 'Panela de Mandarina',
          price: 45.00,
          image: '/src/assets/Panela_Mandarina_X20.jpg',
          description: 'Panela artesanal con sabor natural de mandarina'
        },
        {
          id: 2,
          name: 'Tisana Limonaria',
          price: 35.50,
          image: '/src/assets/Tisana_Limonaria.jpg',
          description: 'Infusión relajante de hierbas naturales'
        },
        {
          id: 3,
          name: 'Panela Helada Limón',
          price: 89.99,
          image: '/src/assets/Panela_Helada_Limon.jpg',
          description: 'Panela helada con refrescante sabor a limón'
        }
      ];
    }
  }
};
</script>

<style scoped>
.home {
  min-height: 100vh;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

/* Hero Section */
.hero {
  background: linear-gradient(135deg, var(--primary-green) 0%, var(--secondary-green) 100%);
  color: white;
  padding: 4rem 0;
  min-height: 80vh;
  display: flex;
  align-items: center;
}

.hero-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 4rem;
  align-items: center;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: 700;
  line-height: 1.2;
  margin-bottom: 1.5rem;
}

.highlight {
  color: var(--accent-brown);
}

.hero-subtitle {
  font-size: 1.25rem;
  line-height: 1.6;
  margin-bottom: 2.5rem;
  opacity: 0.9;
}

.hero-actions {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.btn-large {
  padding: 1rem 2rem;
  font-size: 1.1rem;
  font-weight: 600;
}

.hero-image {
  display: flex;
  justify-content: center;
  align-items: center;
}

.hero-logo {
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  padding: 2rem;
  backdrop-filter: blur(10px);
}

/* Features Section */
.features {
  padding: 5rem 0;
  background: var(--white);
}

.section-title {
  text-align: center;
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--gray-900);
  margin-bottom: 3rem;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.feature-card {
  text-align: center;
  padding: 2rem;
  border-radius: 12px;
  background: var(--gray-50);
  transition: transform 0.2s, box-shadow 0.2s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, var(--primary-green) 0%, var(--secondary-green) 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1.5rem auto;
  color: white;
}

.feature-icon .material-icons {
  font-size: 2.5rem;
}

.feature-card h3 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: 1rem;
}

.feature-card p {
  color: var(--gray-600);
  line-height: 1.6;
}

/* Products Preview */
.products-preview {
  padding: 5rem 0;
  background: var(--gray-50);
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.product-card {
  background: var(--white);
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.product-img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.product-info {
  padding: 1.5rem;
}

.product-name {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: 0.5rem;
}

.product-description {
  color: var(--gray-600);
  margin-bottom: 1rem;
  line-height: 1.5;
}

.product-price {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--accent-brown);
}

.products-cta {
  text-align: center;
}

/* CTA Section */
.cta-section {
  background: linear-gradient(135deg, var(--accent-brown) 0%, #d2691e 100%);
  color: white;
  padding: 5rem 0;
  text-align: center;
}

.cta-content h2 {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
}

.cta-content p {
  font-size: 1.25rem;
  margin-bottom: 2.5rem;
  opacity: 0.9;
}

.cta-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

/* Responsive */
@media (max-width: 768px) {
  .hero-content {
    grid-template-columns: 1fr;
    text-align: center;
    gap: 2rem;
  }
  
  .hero-title {
    font-size: 2.5rem;
  }
  
  .hero-actions {
    justify-content: center;
  }
  
  .hero-logo {
    width: 200px;
    height: 200px;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .features-grid,
  .products-grid {
    grid-template-columns: 1fr;
  }
  
  .cta-content h2 {
    font-size: 2rem;
  }
  
  .cta-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-large {
    width: 100%;
    max-width: 300px;
  }
}
</style> 