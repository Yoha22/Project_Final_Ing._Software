<template>
  <div class="client-profile">
    <div class="profile-header">
      <div class="profile-avatar">
        <span class="material-icons">person</span>
      </div>
      <div class="profile-info">
        <h2>{{ user?.name || 'Mi Perfil' }}</h2>
        <p>{{ user?.email || 'usuario@ejemplo.com' }}</p>
        <span class="member-since">Miembro desde {{ formatDate(user?.createdAt) }}</span>
      </div>
    </div>
    
    <div class="profile-content">
      <div class="profile-section">
        <h3>Información Personal</h3>
        <form @submit.prevent="updateProfile" class="profile-form">
          <div class="form-group">
            <label for="name">Nombre Completo</label>
            <input 
              type="text" 
              id="name" 
              v-model="form.name" 
              required
              class="form-input"
            />
          </div>
          
          <div class="form-group">
            <label for="email">Correo Electrónico</label>
            <input 
              type="email" 
              id="email" 
              v-model="form.email" 
              required
              class="form-input"
            />
          </div>
          
          <div class="form-group">
            <label for="phone">Teléfono</label>
            <input 
              type="tel" 
              id="phone" 
              v-model="form.phone" 
              class="form-input"
            />
          </div>
          
          <div class="form-group">
            <label for="address">Dirección</label>
            <textarea 
              id="address" 
              v-model="form.address" 
              rows="3"
              class="form-input"
            ></textarea>
          </div>
          
          <button type="submit" class="btn-primary">Actualizar Perfil</button>
        </form>
      </div>
      
      <div class="profile-section">
        <h3>Cambiar Contraseña</h3>
        <form @submit.prevent="changePassword" class="password-form">
          <div class="form-group">
            <label for="currentPassword">Contraseña Actual</label>
            <input 
              type="password" 
              id="currentPassword" 
              v-model="passwordForm.currentPassword" 
              required
              class="form-input"
            />
          </div>
          
          <div class="form-group">
            <label for="newPassword">Nueva Contraseña</label>
            <input 
              type="password" 
              id="newPassword" 
              v-model="passwordForm.newPassword" 
              required
              class="form-input"
            />
          </div>
          
          <div class="form-group">
            <label for="confirmPassword">Confirmar Nueva Contraseña</label>
            <input 
              type="password" 
              id="confirmPassword" 
              v-model="passwordForm.confirmPassword" 
              required
              class="form-input"
            />
          </div>
          
          <button type="submit" class="btn-secondary">Cambiar Contraseña</button>
        </form>
      </div>
      
      <div class="profile-section">
        <h3>Preferencias</h3>
        <div class="preferences">
          <div class="preference-item">
            <label class="checkbox-label">
              <input type="checkbox" v-model="preferences.emailNotifications" />
              <span class="checkmark"></span>
              Recibir notificaciones por correo
            </label>
          </div>
          
          <div class="preference-item">
            <label class="checkbox-label">
              <input type="checkbox" v-model="preferences.smsNotifications" />
              <span class="checkmark"></span>
              Recibir notificaciones por SMS
            </label>
          </div>
          
          <div class="preference-item">
            <label class="checkbox-label">
              <input type="checkbox" v-model="preferences.newsletter" />
              <span class="checkmark"></span>
              Suscribirse al boletín de noticias
            </label>
          </div>
          
          <button @click="savePreferences" class="btn-primary">Guardar Preferencias</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../store/auth';

export default {
  name: 'ClientProfile',
  data() {
    return {
      form: {
        name: '',
        email: '',
        phone: '',
        address: ''
      },
      passwordForm: {
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      preferences: {
        emailNotifications: true,
        smsNotifications: false,
        newsletter: true
      }
    };
  },
  computed: {
    user() {
      return useAuthStore().user;
    }
  },
  mounted() {
    this.loadUserData();
  },
  methods: {
    loadUserData() {
      if (this.user) {
        this.form.name = this.user.name || '';
        this.form.email = this.user.email || '';
        this.form.phone = this.user.phone || '';
        this.form.address = this.user.address || '';
      }
    },
    formatDate(dateString) {
      if (!dateString) return 'Fecha no disponible';
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long'
      });
    },
    async updateProfile() {
      try {
        // Aquí iría la llamada al servicio para actualizar el perfil
        console.log('Actualizando perfil:', this.form);
        alert('Perfil actualizado correctamente');
      } catch (error) {
        console.error('Error al actualizar perfil:', error);
        alert('Error al actualizar el perfil');
      }
    },
    async changePassword() {
      if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
      }
      
      try {
        // Aquí iría la llamada al servicio para cambiar la contraseña
        console.log('Cambiando contraseña');
        alert('Contraseña cambiada correctamente');
        this.passwordForm = {
          currentPassword: '',
          newPassword: '',
          confirmPassword: ''
        };
      } catch (error) {
        console.error('Error al cambiar contraseña:', error);
        alert('Error al cambiar la contraseña');
      }
    },
    async savePreferences() {
      try {
        // Aquí iría la llamada al servicio para guardar las preferencias
        console.log('Guardando preferencias:', this.preferences);
        alert('Preferencias guardadas correctamente');
      } catch (error) {
        console.error('Error al guardar preferencias:', error);
        alert('Error al guardar las preferencias');
      }
    }
  }
};
</script>

<style scoped>
.client-profile {
  max-width: 800px;
  margin: 0 auto;
}

.profile-header {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  gap: 2rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.profile-avatar {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #2d5a27 0%, #4a7c59 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.profile-avatar .material-icons {
  font-size: 2.5rem;
}

.profile-info h2 {
  margin: 0 0 0.5rem 0;
  color: #1a1a1a;
  font-size: 1.5rem;
  font-weight: 600;
}

.profile-info p {
  margin: 0 0 0.5rem 0;
  color: #6c757d;
  font-size: 1rem;
}

.member-since {
  font-size: 0.9rem;
  color: #adb5bd;
}

.profile-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.profile-section {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.profile-section h3 {
  margin: 0 0 1.5rem 0;
  color: #1a1a1a;
  font-size: 1.3rem;
  font-weight: 600;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #1a1a1a;
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #2d5a27;
  box-shadow: 0 0 0 3px rgba(45, 90, 39, 0.1);
}

textarea.form-input {
  resize: vertical;
  min-height: 80px;
}

.btn-primary, .btn-secondary {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
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

.preferences {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.preference-item {
  display: flex;
  align-items: center;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  font-weight: 500;
  color: #1a1a1a;
}

.checkbox-label input[type="checkbox"] {
  display: none;
}

.checkmark {
  width: 20px;
  height: 20px;
  border: 2px solid #ddd;
  border-radius: 4px;
  position: relative;
  transition: all 0.2s;
}

.checkbox-label input[type="checkbox"]:checked + .checkmark {
  background: #2d5a27;
  border-color: #2d5a27;
}

.checkbox-label input[type="checkbox"]:checked + .checkmark::after {
  content: '✓';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 12px;
  font-weight: bold;
}

@media (max-width: 768px) {
  .profile-header {
    flex-direction: column;
    text-align: center;
    gap: 1rem;
  }
  
  .profile-section {
    padding: 1.5rem;
  }
}
</style> 