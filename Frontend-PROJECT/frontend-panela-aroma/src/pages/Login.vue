<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <router-link to="/" class="back-link">
          <span class="material-icons">arrow_back</span>
          Volver al inicio
        </router-link>
      </div>
      
      <div class="login-content">
        <div class="login-brand">
          <img src="/src/assets/Logo.png" alt="Panela, Aroma y Sabor" class="brand-logo" />
          <h1>Panela, Aroma y Sabor</h1>
        </div>
        
        <LoginForm />
      </div>
    </div>
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';

export default {
  name: 'Login',
  components: { LoginForm }
};
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--primary-green) 0%, var(--secondary-green) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.login-container {
  background: var(--white);
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 100%;
  max-width: 500px;
}

.login-header {
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

.login-content {
  padding: 2rem;
}

.login-brand {
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

.login-brand h1 {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0;
}

/* Responsive */
@media (max-width: 480px) {
  .login-page {
    padding: 1rem;
  }
  
  .login-container {
    border-radius: 12px;
  }
  
  .login-header,
  .login-content {
    padding: 1.5rem;
  }
  
  .brand-logo {
    width: 60px;
    height: 60px;
  }
  
  .login-brand h1 {
    font-size: 1.25rem;
  }
}
</style> 