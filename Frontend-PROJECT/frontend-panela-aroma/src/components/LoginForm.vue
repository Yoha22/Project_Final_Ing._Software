<template>
  <form class="login-form" @submit.prevent="onLogin" autocomplete="off">
    <div class="form-header">
      <h2>Iniciar Sesión</h2>
      <p>Accede a tu cuenta para continuar</p>
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
        autocomplete="current-password" 
        placeholder="Ingresa tu contraseña"
        :disabled="loading"
        required
      />
    </div>
    
    <button type="submit" :disabled="loading" class="btn-login">
      <span v-if="loading" class="loading-spinner"></span>
      <span v-else>Iniciar Sesión</span>
    </button>
    
    <div v-if="error" class="error-message">
      <span class="material-icons">error</span>
      {{ error }}
    </div>
    
    <div class="form-footer">
      <p>¿No tienes cuenta? <router-link to="/register">Regístrate aquí</router-link></p>
    </div>
  </form>
</template>

<script>
import { useAuthStore } from '../store/auth';

export default {
  name: 'LoginForm',
  data() {
    return {
      email: '',
      password: '',
      error: '',
      loading: false
    };
  },
  methods: {
    async onLogin() {
      // Validación básica
      if (!this.email || !this.password) {
        this.error = 'Por favor completa todos los campos';
        return;
      }
      
      this.loading = true;
      this.error = '';
      
      try {
        const authStore = useAuthStore();
        const response = await authStore.login({
          email: this.email,
          password: this.password
        });
        
        // Navegación basada en el rol del usuario
        if (authStore.isAdmin) {
          this.$router.push('/dashboard');
        } else if (authStore.isClient) {
          this.$router.push('/client-dashboard');
        } else {
          this.$router.push('/products');
        }
        
        // Mostrar mensaje de éxito (opcional)
        console.log('Login exitoso:', response.data);
        
      } catch (error) {
        console.error('Error de login:', error);
        
        // Manejo de errores específicos del backend
        if (error.response) {
          const status = error.response.status;
          const data = error.response.data;
          
          switch (status) {
            case 401:
              this.error = 'Credenciales incorrectas. Verifica tu correo y contraseña.';
              break;
            case 404:
              this.error = 'Usuario no encontrado. Verifica tu correo electrónico.';
              break;
            case 422:
              this.error = data.message || 'Datos de entrada inválidos.';
              break;
            case 500:
              this.error = 'Error del servidor. Intenta nuevamente más tarde.';
              break;
            default:
              this.error = data.message || 'Error al iniciar sesión. Intenta nuevamente.';
          }
        } else if (error.request) {
          this.error = 'No se pudo conectar con el servidor. Verifica tu conexión a internet.';
        } else {
          this.error = 'Error inesperado. Intenta nuevamente.';
        }
      } finally {
        this.loading = false;
      }
    },
    
    clearError() {
      this.error = '';
    }
  },
  
  watch: {
    email() {
      this.clearError();
    },
    password() {
      this.clearError();
    }
  }
};
</script>

<style scoped>
.login-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
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

.btn-login {
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

.btn-login:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(34, 197, 94, 0.3);
}

.btn-login:disabled {
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
  .login-form {
    padding: 0 1rem;
  }
  
  .form-header h2 {
    font-size: 1.5rem;
  }
  
  .btn-login {
    padding: 0.75rem 1rem;
  }
}
</style> 