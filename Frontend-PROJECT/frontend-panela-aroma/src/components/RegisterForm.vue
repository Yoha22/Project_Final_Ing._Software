<template>
  <form class="register-form" @submit.prevent="onRegister" autocomplete="off">
    <div class="form-header">
      <h2>Crear Cuenta</h2>
      <p>Regístrate para disfrutar de nuestros productos</p>
    </div>
    
    <div class="form-group">
      <label for="name">Nombre Completo</label>
      <input 
        id="name"
        v-model="name"
        autocomplete="name"
        placeholder="Ingresa tu nombre completo"
        :disabled="loading"
        required
      />
    </div>
    <div class="form-group">
      <label for="email">Correo Electrónico</label>
      <input 
        id="email"
        v-model="email"
        type="email"
        autocomplete="email"
        placeholder="Ingresa tu correo electrónico"
        :disabled="loading"
        required
      />
    </div>
    <div class="form-group">
      <label for="password">Contraseña</label>
      <input 
        id="password"
        v-model="password"
        type="password"
        autocomplete="new-password"
        placeholder="Crea una contraseña"
        :disabled="loading"
        required
      />
    </div>
    <div class="form-group">
      <label for="phone">Teléfono</label>
      <input 
        id="phone"
        v-model="phone"
        autocomplete="tel"
        placeholder="Ingresa tu número de teléfono"
        :disabled="loading"
        required
      />
    </div>
    <div class="form-group">
      <label for="address">Dirección</label>
      <input 
        id="address"
        v-model="address"
        autocomplete="street-address"
        placeholder="Ingresa tu dirección"
        :disabled="loading"
        required
      />
    </div>
    
    <button type="submit" :disabled="loading" class="btn-register">
      <span v-if="loading" class="loading-spinner"></span>
      <span v-else>Registrarse</span>
    </button>
    
    <div v-if="error" class="error-message">
      <span class="material-icons">error</span>
      {{ error }}
    </div>
    
    <div class="form-footer">
      <p>¿Ya tienes cuenta? <router-link to="/login">Inicia sesión aquí</router-link></p>
    </div>
  </form>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../store/auth';

const name = ref('');
const email = ref('');
const password = ref('');
const phone = ref('');
const address = ref('');
const error = ref('');
const loading = ref(false);

const router = useRouter();
const authStore = useAuthStore();

const onRegister = async () => {
  error.value = '';
  loading.value = true;
  try {
    await authStore.register({
      name: name.value,
      email: email.value,
      password: password.value,
      phone: phone.value,
      address: address.value
    });
    // Redirige según el rol
    if (authStore.isAdmin) {
      router.push('/dashboard');
    } else if (authStore.isClient) {
      router.push('/client');
    } else {
      router.push('/products');
    }
  } catch (err) {
    error.value = err.response?.data?.message || 'Error al registrar. Intenta nuevamente.';
  } finally {
    loading.value = false;
  }
};

watch([name, email, password, phone, address], () => {
  error.value = '';
});
</script>

<style scoped>
.register-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 6px 32px 0 rgba(34, 197, 94, 0.10), 0 1.5px 6px 0 rgba(0,0,0,0.04);
  padding: 2.5rem 2rem 2rem 2rem;
}

.form-header {
  text-align: center;
  margin-bottom: 1rem;
}

.form-header h2 {
  font-size: 1.75rem;
  font-weight: 700;
  color: var(--gray-900);
  margin-bottom: 0.5rem;
}

.form-header p {
  color: var(--gray-600);
  font-size: 0.95rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: 600;
  color: var(--gray-700);
  font-size: 0.9rem;
}

.form-group input {
  padding: 0.75rem 1rem;
  border: 2px solid var(--gray-200);
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s, box-shadow 0.2s;
  background: var(--white);
}

.form-group input:focus {
  outline: none;
  border-color: var(--primary-green);
  box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.1);
}

.form-group input:disabled {
  background: var(--gray-100);
  cursor: not-allowed;
}

.btn-register {
  padding: 0.875rem 1.5rem;
  background: linear-gradient(135deg, var(--primary-green) 0%, var(--secondary-green) 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  min-height: 48px;
}

.btn-register:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(34, 197, 94, 0.3);
}

.btn-register:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.loading-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top: 2px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-message {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: #fef2f2;
  border: 1px solid #fecaca;
  border-radius: 8px;
  color: #dc2626;
  font-size: 0.9rem;
  font-weight: 500;
}

.error-message .material-icons {
  font-size: 1.1rem;
  flex-shrink: 0;
}

.form-footer {
  text-align: center;
  margin-top: 1rem;
}

.form-footer p {
  color: var(--gray-600);
  font-size: 0.9rem;
}

.form-footer a {
  color: var(--primary-green);
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
}

.form-footer a:hover {
  color: var(--secondary-green);
  text-decoration: underline;
}

/* Responsive */
@media (max-width: 480px) {
  .register-form {
    padding: 0 1rem;
  }
  
  .form-header h2 {
    font-size: 1.5rem;
  }
  
  .btn-register {
    padding: 0.75rem 1rem;
  }
}
</style> 