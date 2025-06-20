import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import { useAuthStore } from './store/auth'

const app = createApp(App)
const pinia = createPinia()

app.use(router)
app.use(pinia)

// Inicializar el store de autenticación
const authStore = useAuthStore()
authStore.initializeAuth()

app.mount('#app')
