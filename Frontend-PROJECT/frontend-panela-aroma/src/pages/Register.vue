<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-header">
        <router-link to="/" class="back-link">
          <span class="material-icons">arrow_back</span>
          Volver al inicio
        </router-link>
      </div>
      <div class="register-content">
        <div class="register-brand">
          <img src="/src/assets/Logo.png" alt="Panela, Aroma y Sabor" class="brand-logo" />
          <h1>Panela, Aroma y Sabor</h1>
        </div>
        <RegisterForm />
      </div>
    </div>
  </div>
</template>

<script>
import RegisterForm from '../components/RegisterForm.vue';
export default {
  name: 'Register',
  components: { RegisterForm }
};
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--primary-green) 0%, var(--secondary-green) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.register-container {
  background: var(--white);
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 100%;
  max-width: 500px;
}

.register-header {
  padding: 1.5rem 2rem;
  border-bottom: 1px solid var(--gray-200);
}

.back-link {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--gray-600);
  text-decoration: none;
  font-weight: 500;
  font-size: 0.9rem;
  transition: color 0.2s;
}

.back-link:hover {
  color: var(--primary-green);
}

.back-link .material-icons {
  font-size: 1.1rem;
}

.register-content {
  padding: 2rem;
}

.register-brand {
  text-align: center;
  margin-bottom: 2rem;
}

.brand-logo {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-bottom: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.register-brand h1 {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0;
}

/* Responsive */
@media (max-width: 480px) {
  .register-page {
    padding: 1rem;
  }
  .register-container {
    border-radius: 12px;
  }
  .register-header,
  .register-content {
    padding: 1.5rem;
  }
  .brand-logo {
    width: 60px;
    height: 60px;
  }
  .register-brand h1 {
    font-size: 1.25rem;
  }
}
</style> 