<template>
  <div class="dashboard-settings">
    <h2>Configuración del Sistema</h2>
    <div class="settings-section">
      <div class="settings-card">
        <span class="material-icons settings-icon">settings</span>
        <div>
          <h3>Configuraciones generales</h3>
          <p>Aquí podrás modificar parámetros generales del sistema, como nombre de la tienda, logo, colores, etc.</p>
        </div>
      </div>
      <div class="settings-card">
        <span class="material-icons settings-icon">security</span>
        <div>
          <h3>Seguridad</h3>
          <p>Opciones para cambiar contraseñas de administrador, políticas de acceso y más.</p>
        </div>
      </div>
      <div class="settings-card">
        <span class="material-icons settings-icon">notifications</span>
        <div>
          <h3>Notificaciones</h3>
          <p>Configura notificaciones por correo, SMS y otros canales para los usuarios.</p>
        </div>
      </div>
      <div class="settings-card">
        <span class="material-icons settings-icon">backup</span>
        <div>
          <h3>Respaldo y restauración</h3>
          <p>Opciones para exportar, importar o restaurar datos del sistema.</p>
        </div>
      </div>
    </div>
    <div class="settings-placeholder">
      <p>Próximamente podrás editar la configuración avanzada del sistema desde aquí.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DashboardSettings'
};
</script>

<style scoped>
.dashboard-settings {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem 0;
}

h2 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--primary-green);
  margin-bottom: 2rem;
}

.settings-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 2rem;
  margin-bottom: 2.5rem;
}

.settings-card {
  background: var(--white);
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  display: flex;
  align-items: flex-start;
  gap: 1.5rem;
  padding: 2rem 1.5rem;
  transition: box-shadow 0.2s;
}

.settings-card:hover {
  box-shadow: 0 4px 16px rgba(0,0,0,0.13);
}

.settings-icon {
  font-size: 2.5rem;
  color: var(--accent-brown);
  background: var(--gray-50);
  border-radius: 50%;
  padding: 1rem;
  margin-right: 0.5rem;
}

.settings-card h3 {
  margin: 0 0 0.5rem 0;
  font-size: 1.2rem;
  color: var(--primary-green);
}

.settings-card p {
  color: var(--gray-600);
  font-size: 1rem;
}

.settings-placeholder {
  text-align: center;
  color: var(--gray-500);
  font-size: 1.1rem;
  margin-top: 2rem;
}
</style> 