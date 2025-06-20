# <h1 align="center">Documentación del Proyecto: Panela, Aroma y Sabor</h1>

<h1>Integrantes:</h1>

- <h4>Yohan David Morelo Julio</h4>
- <h4>Miguel Andrés de Aguas Martinez</h4>

## Índice
1. [Descripción General](#descripción-general)
2. [Arquitectura del Backend](#arquitectura-backend)
3. [Arquitectura del Proyecto Frontend](#arquitectura-del-proyecto-frontend)
4. [Tecnologías Utilizadas](#tecnologías-utilizadas)
5. [Estructura de Carpetas](#estructura-de-carpetas)
6. [Configuración del Proyecto](#configuración-del-proyecto)
7. [Autenticación y Autorización](#autenticación-y-autorización)
8. [Gestión de Estado](#gestión-de-estado)
9. [Servicios API](#servicios-api)
10. [Componentes Principales](#componentes-principales)
11. [Páginas y Rutas](#páginas-y-rutas)
12. [Estilos y Diseño](#estilos-y-diseño)
13. [Guía de Desarrollo](#guía-de-desarrollo)
15. [Troubleshooting](#troubleshooting)

---

## Descripción General

**Panela, Aroma y Sabor** es una plataforma web moderna para la venta de productos aromáticos y de panela. El frontend está desarrollado en Vue.js 3 con una arquitectura basada en **microservicios** desarrollada en **Java con Spring Boot**, con integración al frontend mediante un **API Gateway** y comunicación entre servicios por medio de **HTTP REST**.

### Características Principales
- **Doble Dashboard**: Separación de roles entre clientes y administradores
- **Catálogo de Productos**: Visualización y gestión de productos aromáticos
- **Sistema de Carrito**: Gestión de compras con resumen modal
- **Autenticación JWT**: Sistema seguro de login/registro
- **Diseño Responsivo**: Interfaz moderna y adaptable a dispositivos móviles
- **Gestión de Inventario**: Control de stock y productos
- **Sistema de Facturación**: Generación y gestión de facturas
- **Gestión de Usuarios**: Administración de clientes y usuarios del sistema

---

## Arquitectura Backend

El sistema está compuesto por múltiples microservicios independientes, cada uno con su propia lógica y base de datos (o esquema compartido para desarrollo local), conectados a través de **HTTP REST** mediante el uso de `FeignClient` y gestionados externamente por un **API Gateway**.

### Tecnologías principales:

- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.5
- **Gestión de APIs**: Spring Web + Spring Cloud OpenFeign
- **Seguridad**: Spring Security + JWT
- **Base de datos**: PostgreSQL
- **Gestión de dependencias**: Maven

---

### Patrones de Diseño Implementados
Este proyecto aplica varios patrones de diseño de software para asegurar una arquitectura limpia, escalable y mantenible dentro de sus microservicios:

#### Microservicios
Cada dominio del negocio (clientes, productos, pedidos, facturas, usuarios y autenticación) está desacoplado en un microservicio independiente, facilitando el despliegue y mantenimiento modular.

#### Facade (API Gateway)
Se implementa un API Gateway como punto único de entrada desde el frontend. Este actúa como una fachada para centralizar, redirigir y simplificar las llamadas hacia los servicios internos.

#### Singleton (Spring Context)
Gracias a Spring, los componentes como @Service y @Repository se gestionan como Singletons, garantizando una sola instancia activa por contexto de aplicación.

#### DTO (Data Transfer Object)
Se utilizan DTOs para transferir datos entre capas y entre servicios, evitando exponer directamente las entidades y permitiendo un mayor control de la información que se transmite.

#### Strategy (Autenticación JWT)
La autenticación con JWT aplica el patrón Strategy mediante filtros personalizados de seguridad en Spring Security, que permiten manejar rutas protegidas y públicas según el contexto del usuario.

#### Repository
Cada servicio implementa el patrón Repository con interfaces JpaRepository, separando la lógica de acceso a datos y promoviendo una estructura desacoplada y testeable.

## Microservicios disponibles

| Servicio           | Funcionalidad principal                           |
|--------------------|---------------------------------------------------|
| `auth-service`     | Registro, login y gestión de tokens JWT           |
| `users-service`    | Administración de usuarios y roles                |
| `clients-service`  | Información de clientes (dirección, ciudad, etc.) |
| `products-service` | Gestión de productos e inventario                 |
| `orders-service`   | Registro de pedidos                               |
| `invoices-service` | Generación y consulta de facturas                 |
| `inventory-service`| Entradas y salidas del stock                      |

---

## Seguridad

- Los usuarios se autentican mediante `auth-service`, que devuelve un **JWT token**.
- Cada microservicio valida el token en las cabeceras HTTP (`Authorization: Bearer <token>`).
- Se controla el acceso a los endpoints según el **rol del usuario** (`CLIENT`, `ADMIN`).
- Contraseñas encriptadas con BCrypt.

---

## API Gateway

El gateway es el punto único de entrada para el frontend y enruta las peticiones a los microservicios correspondientes.

| Ruta Gateway         | Redirección al Servicio  |
|----------------------|--------------------------|
| `/api/auth/**`       | `auth-service`           |
| `/api/products/**`   | `products-service`       |
| `/api/orders/**`     | `orders-service`         |
| `/api/clients/**`    | `clients-service`        |
| `/api/inventory/**`  | `inventory-service`      |
| `/api/invoices/**`   | `invoices-service`       |

---

## API por Servicio

### `auth-service` – Autenticación
**Base URL**: `/api/auth`

| Método | Ruta        | Descripción               |
|--------|-------------|---------------------------|
| POST   | `/register` | Registra un nuevo usuario |
| POST   | `/login`    | Inicia sesión y retorna JWT |

---

### `clients-service` – Perfil de cliente
**Base URL**: `/api/clients`

| Método | Ruta   | Descripción                                 |
|--------|--------|---------------------------------------------|
| POST   | `/`    | Crea el perfil del cliente                  |
| GET    | `/me`  | Obtiene datos del cliente autenticado       |

---

### `products-service` – Productos
**Base URL**: `/api/products`

| Método | Ruta       | Descripción                            |
|--------|------------|----------------------------------------|
| GET    | `/`        | Lista todos los productos              |
| POST   | `/`        | Crea un nuevo producto (ADMIN)         |
| PUT    | `/{id}`    | Actualiza un producto existente        |
| DELETE | `/{id}`    | Elimina un producto                    |

---

### `orders-service` – Pedidos
**Base URL**: `/api/orders`

| Método | Ruta     | Descripción                              |
|--------|----------|------------------------------------------|
| POST   | `/`      | Registra un nuevo pedido (CLIENT)        |
| GET    | `/`      | Lista los pedidos del usuario autenticado|
| GET    | `/{id}`  | Detalle de un pedido                     |

---

### `invoices-service` – Facturas
**Base URL**: `/api/invoices`

| Método | Ruta       | Descripción                                 |
|--------|------------|---------------------------------------------|
| GET    | `/`        | Lista facturas del cliente                  |
| GET    | `/admin`   | Lista todas las facturas (solo ADMIN)       |

---

### `inventory-service` – Control de Stock
**Base URL**: `/api/inventory`

| Método | Ruta       | Descripción                                       |
|--------|------------|---------------------------------------------------|
| POST   | `/`        | Registra entrada o salida de inventario          |
| GET    | `/{id}`    | Muestra historial de movimientos por producto    |

---

## Arquitectura del Proyecto Frontend

### Patrón de Arquitectura
El proyecto sigue una arquitectura **SPA (Single Page Application)** con:

- **Vue.js 3**: Framework principal con Composition API
- **Vue Router 4**: Enrutamiento y navegación
- **Pinia**: Gestión de estado centralizada
- **Axios**: Cliente HTTP para comunicación con APIs
- **Vite**: Build tool y servidor de desarrollo

### Flujo de Datos
```
Usuario → Componentes Vue → Stores (Pinia) → Servicios API → Backend Microservicios
```

### Separación de Responsabilidades
- **Componentes**: Presentación y lógica de UI
- **Stores**: Estado global y lógica de negocio
- **Servicios**: Comunicación con APIs externas
- **Router**: Navegación y protección de rutas
- **Utils**: Funciones auxiliares y helpers

---

## Tecnologías Utilizadas

### Frontend
- **Vue.js 3.5.13**: Framework de JavaScript progresivo
- **Vue Router 4.5.1**: Enrutador oficial de Vue
- **Pinia 3.0.3**: Store de estado para Vue
- **Axios 1.10.0**: Cliente HTTP basado en promesas
- **Vite 6.3.5**: Build tool y bundler
- **@vitejs/plugin-vue 5.2.3**: Plugin oficial de Vue para Vite

### Herramientas de Desarrollo
- **Node.js**: Runtime de JavaScript
- **npm**: Gestor de paquetes
- **ESLint**: Linter de código (recomendado)
- **Prettier**: Formateador de código (recomendado)

### Estilos y UI
- **CSS Variables**: Sistema de diseño con colores naturales
- **Material Icons**: Iconografía consistente
- **Responsive Design**: Diseño adaptable a múltiples dispositivos

---

## Estructura de Carpetas

```
frontend-panela-aroma/
├── public/                    # Archivos estáticos
│   └── vite.svg
├── src/                       # Código fuente principal
│   ├── assets/               # Recursos estáticos (imágenes, logos)
│   │   ├── Logo.png
│   │   ├── Banner.jpg
│   │   └── [otros archivos de imagen]
│   ├── components/           # Componentes reutilizables
│   │   ├── CartSummary.vue
│   │   ├── HeaderNav.vue
│   │   ├── LoginForm.vue
│   │   ├── ProductCard.vue
│   │   ├── ProductList.vue
│   │   └── RegisterForm.vue
│   ├── pages/                # Páginas principales
│   │   ├── Home.vue
│   │   ├── Login.vue
│   │   ├── Register.vue
│   │   ├── Products.vue
│   │   ├── Dashboard.vue
│   │   ├── ClientDashboard.vue
│   │   └── [páginas de dashboard específicas]
│   ├── router/               # Configuración de rutas
│   │   └── index.js
│   ├── services/             # Servicios de API
│   │   ├── axios.js
│   │   ├── AuthService.js
│   │   ├── ProductService.js
│   │   ├── OrderService.js
│   │   ├── InvoiceService.js
│   │   ├── UserService.js
│   │   ├── ClientService.js
│   │   └── InventoryService.js
│   ├── store/                # Stores de Pinia
│   │   ├── auth.js
│   │   └── cart.js
│   ├── App.vue               # Componente raíz
│   ├── main.js               # Punto de entrada
│   └── style.css             # Estilos globales
├── index.html               # HTML principal
├── package.json             # Dependencias y scripts
└── vite.config.js           # Configuración de Vite
```

---

## Configuración del Proyecto

### Requisitos Previos
- Node.js 16+ 
- npm 8+

### Instalación
```bash
# Clonar el repositorio
git clone [URL_DEL_REPOSITORIO]
cd frontend-panela-aroma

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm run dev

# Construir para producción
npm run build

# Previsualizar build de producción
npm run preview
```

### Variables de Entorno
Crear archivo `.env` en la raíz del proyecto:
```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_TITLE=Panela, Aroma y Sabor
```

### Configuración de Vite
```javascript
// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
```

---

## Autenticación y Autorización

### Sistema de Autenticación
El proyecto utiliza **JWT (JSON Web Tokens)** para la autenticación:

#### Flujo de Login
1. Usuario ingresa credenciales
2. Frontend envía datos al endpoint `/auth/login`
3. Backend valida y retorna JWT + datos del usuario
4. Token se almacena en localStorage y Pinia store
5. Usuario es redirigido según su rol

#### Flujo de Registro
1. Usuario completa formulario de registro
2. Frontend envía datos al endpoint `/auth/register`
3. Backend crea usuario y cliente (microservicios)
4. Retorna JWT + datos del usuario
5. Usuario es redirigido según su rol

### Protección de Rutas
```javascript
// Router guard en router/index.js
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('userRole');
  
  if (to.meta.requiresAuth && !token) {
    next('/login');
  } else if (to.meta.role && to.meta.role !== userRole) {
    // Redirigir según el rol
    if (userRole === 'ADMIN') {
      next('/dashboard');
    } else if (userRole === 'CLIENT') {
      next('/client');
    } else {
      next('/');
    }
  } else {
    next();
  }
});
```

### Roles de Usuario
- **CLIENT**: Acceso a catálogo, carrito, órdenes, facturas y perfil
- **ADMIN**: Acceso completo al dashboard administrativo

### Store de Autenticación
```javascript
// store/auth.js
export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
    isAuthenticated: false
  }),
  
  getters: {
    isAdmin: (state) => state.user?.role === 'ADMIN',
    isClient: (state) => state.user?.role === 'CLIENT'      
  },
  
  actions: {
    async login(credentials) { /* ... */ },
    async register(userData) { /* ... */ },
    async logout() { /* ... */ },
    async fetchUser() { /* ... */ }
  }
});
```

---

## Gestión de Estado

### Pinia Stores

#### Auth Store (`store/auth.js`)
- **Estado**: Usuario, token, estado de autenticación
- **Getters**: Verificación de roles (isAdmin, isClient)
- **Actions**: Login, registro, logout, fetchUser

#### Cart Store (`store/cart.js`)
- **Estado**: Items del carrito
- **Getters**: Total del carrito
- **Actions**: addToCart, removeFromCart, clearCart

### Patrones de Uso
```javascript
// En componentes
import { useAuthStore } from '../store/auth';
import { useCartStore } from '../store/cart';

const authStore = useAuthStore();
const cartStore = useCartStore();

// Acceder a estado
console.log(authStore.user);
console.log(cartStore.total);

// Ejecutar acciones
await authStore.login(credentials);
cartStore.addToCart(product);
```

---

## Servicios API

### Configuración Base (`services/axios.js`)
```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptors para manejo automático de tokens
api.interceptors.request.use(/* ... */);
api.interceptors.response.use(/* ... */);
```

### Servicios Disponibles

#### AuthService
```javascript
export default {
  login(credentials),
  register(data),
  logout(),
  getProfile()
};
```

#### ProductService
```javascript
export default {
  getAll(),
  getById(id),
  add(product),
  update(id, product),
  delete(id)
};
```

#### OrderService
```javascript
export default {
  getAll(),
  getById(id),
  create(order),
  update(id, order),
  delete(id)
};
```

#### InvoiceService
```javascript
export default {
  getAll(),
  getById(id),
  create(invoice),
  update(id, invoice),
  delete(id)
};
```

#### UserService
```javascript
export default {
  getAll(),
  getById(id),
  create(user),
  update(id, user),
  delete(id)
};
```

#### ClientService
```javascript
export default {
  getAll(),
  getById(id),
  create(client),
  update(id, client),
  delete(id)
};
```

#### InventoryService
```javascript
export default {
  getAll(),
  getById(id),
  updateStock(id, quantity),
  getLowStock()
};
```

---

## Componentes Principales

### HeaderNav (`components/HeaderNav.vue`)
- **Propósito**: Navegación principal y header de la aplicación
- **Características**: 
  - Logo de la empresa
  - Menú de navegación responsivo
  - Carrito de compras con badge
  - Dropdown de usuario autenticado
  - Botones de login/registro para usuarios no autenticados

### LoginForm (`components/LoginForm.vue`)
- **Propósito**: Formulario de inicio de sesión
- **Características**:
  - Validación de campos
  - Manejo de errores
  - Estado de loading
  - Redirección automática según rol

### RegisterForm (`components/RegisterForm.vue`)
- **Propósito**: Formulario de registro de usuarios
- **Características**:
  - Campos: nombre, email, contraseña, teléfono, dirección
  - Validación completa
  - Creación automática de cliente
  - Redirección según rol

### ProductCard (`components/ProductCard.vue`)
- **Propósito**: Tarjeta de producto individual
- **Características**:
  - Imagen del producto
  - Información: nombre, precio, descripción
  - Botón de agregar al carrito
  - Diseño responsivo

### ProductList (`components/ProductList.vue`)
- **Propósito**: Lista/grid de productos
- **Características**:
  - Grid responsivo de productos
  - Filtros y búsqueda
  - Paginación
  - Integración con ProductCard

### CartSummary (`components/CartSummary.vue`)
- **Propósito**: Resumen del carrito de compras
- **Características**:
  - Lista de items
  - Cálculo de totales
  - Botones de acción (checkout, limpiar)
  - Modal/popup

---

## Páginas y Rutas

### Rutas Públicas
- **`/`**: Página de inicio (Home.vue)
- **`/products`**: Catálogo de productos (Products.vue)
- **`/login`**: Página de inicio de sesión (Login.vue)
- **`/register`**: Página de registro (Register.vue)

### Rutas Protegidas - Clientes
- **`/client`**: Dashboard principal de cliente (ClientDashboard.vue)
  - **`/client/profile`**: Perfil del cliente (ClientProfile.vue)
  - **`/client/orders`**: Historial de órdenes (ClientOrders.vue)
  - **`/client/invoices`**: Facturas del cliente (ClientInvoices.vue)

### Rutas Protegidas - Administradores
- **`/dashboard`**: Dashboard principal de administrador (Dashboard.vue)
  - **`/dashboard/overview`**: Vista general (DashboardOverview.vue)
  - **`/dashboard/products`**: Gestión de productos (DashboardProducts.vue)
  - **`/dashboard/users`**: Gestión de usuarios (DashboardUsers.vue)
  - **`/dashboard/orders`**: Gestión de órdenes (DashboardOrders.vue)
  - **`/dashboard/invoices`**: Gestión de facturas (DashboardInvoices.vue)
  - **`/dashboard/inventory`**: Gestión de inventario (DashboardInventory.vue)
  - **`/dashboard/clients`**: Gestión de clientes (DashboardClients.vue)
  - **`/dashboard/settings`**: Configuraciones (DashboardSettings.vue)

### Configuración de Router
```javascript
// router/index.js
const routes = [
  // Rutas públicas
  { path: '/', component: Home },
  { path: '/products', component: Products },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  
  // Rutas para clientes
  {
    path: '/client',
    component: ClientDashboard,
    meta: { requiresAuth: true, role: 'CLIENT' },
    children: [/* ... */]
  },
  
  // Rutas para administradores
  {
    path: '/dashboard',
    component: Dashboard,
    meta: { requiresAuth: true, role: 'ADMIN' },
    children: [/* ... */]
  }
];
```

---

## Estilos y Diseño

### Sistema de Diseño

#### Paleta de Colores
```css
:root {
  --primary-green: #2d5a27;      /* Verde principal */
  --secondary-green: #4a7c59;    /* Verde secundario */
  --accent-brown: #b85c00;       /* Marrón acento */
  --light-green: #e8f5e8;        /* Verde claro */
  --white: #ffffff;              /* Blanco */
  --gray-50: #f8f9fa;           /* Gris muy claro */
  --gray-100: #f1f3f4;          /* Gris claro */
  --gray-200: #e9ecef;          /* Gris medio claro */
  --gray-300: #dee2e6;          /* Gris medio */
  --gray-400: #ced4da;          /* Gris medio oscuro */
  --gray-500: #adb5bd;          /* Gris */
  --gray-600: #6c757d;          /* Gris oscuro */
  --gray-700: #495057;          /* Gris muy oscuro */
  --gray-800: #343a40;          /* Gris casi negro */
  --gray-900: #1a1a1a;          /* Negro */
}
```

#### Tipografía
- **Familia**: Inter, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif
- **Tamaños**: Escala modular (0.875rem, 1rem, 1.125rem, 1.25rem, 1.5rem, 1.75rem, 2rem, 2.5rem)
- **Pesos**: 300 (light), 400 (normal), 500 (medium), 600 (semibold), 700 (bold)

#### Componentes de UI

##### Botones
```css
.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.2s;
}

.btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(45, 90, 39, 0.3);
}
```

##### Formularios
```css
.form-input {
  padding: 0.75rem;
  border: 1px solid var(--gray-300);
  border-radius: 8px;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus {
  border-color: var(--primary-green);
  box-shadow: 0 0 0 3px rgba(45, 90, 39, 0.1);
}
```

##### Tarjetas
```css
.card {
  background: var(--white);
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  transition: transform 0.2s, box-shadow 0.2s;
}
```

### Responsive Design
- **Breakpoints**: 768px, 480px
- **Grid System**: CSS Grid y Flexbox
- **Mobile First**: Diseño optimizado para móviles

### Utilidades CSS
- **Spacing**: Sistema de márgenes y padding (m-1 a m-6, p-1 a p-6)
- **Flexbox**: Clases utilitarias para flex
- **Grid**: Clases para layouts de grid
- **Typography**: Clases para texto y alineación
- **Colors**: Clases para colores de texto y fondo

---

## Guía de Desarrollo

### Convenciones de Código

#### Nomenclatura
- **Componentes**: PascalCase (ej: `ProductCard.vue`)
- **Archivos**: kebab-case (ej: `product-service.js`)
- **Variables**: camelCase (ej: `userName`)
- **Constantes**: UPPER_SNAKE_CASE (ej: `API_BASE_URL`)


### Patrones de Desarrollo

#### Manejo de Estado
```javascript
// Usar Pinia para estado global
const authStore = useAuthStore();
const cartStore = useCartStore();

// Usar ref/reactive para estado local
const localData = ref([]);
const formData = reactive({
  name: '',
  email: ''
});
```

#### Manejo de Errores
```javascript
try {
  const response = await apiService.getData();
  // Procesar respuesta
} catch (error) {
  if (error.response?.status === 401) {
    // Manejar error de autenticación
    authStore.logout();
  } else {
    // Mostrar error genérico
    showError(error.response?.data?.message || 'Error inesperado');
  }
}
```

#### Validación de Formularios
```javascript
const validateForm = () => {
  const errors = [];
  
  if (!formData.email) {
    errors.push('El email es requerido');
  }
  
  if (!formData.password || formData.password.length < 6) {
    errors.push('La contraseña debe tener al menos 6 caracteres');
  }
  
  return errors;
};
```

### Testing (Recomendado)
```bash
# Instalar dependencias de testing
npm install -D @vue/test-utils vitest

# Ejecutar tests
npm run test
```

### Linting y Formateo
```bash
# Instalar ESLint y Prettier
npm install -D eslint prettier @vue/eslint-config-prettier

# Configurar scripts en package.json
{
  "scripts": {
    "lint": "eslint src --ext .vue,.js",
    "lint:fix": "eslint src --ext .vue,.js --fix",
    "format": "prettier --write src/**/*.{vue,js,css}"
  }
}
```
---

## Troubleshooting

### Problemas Comunes

#### Error de CORS
```javascript
// En el backend, configurar CORS
@CrossOrigin(origins = "http://localhost:3000")
```

#### Token Expirado
```javascript
// El interceptor de axios maneja automáticamente
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      authStore.logout();
      router.push('/login');
    }
    return Promise.reject(error);
  }
);
```

#### Problemas de Rutas
```javascript
// Verificar que las rutas estén correctamente configuradas
// y que los guards funcionen correctamente
console.log('Token:', localStorage.getItem('token'));
console.log('Role:', localStorage.getItem('userRole'));
```

#### Problemas de Estado
```javascript
// Verificar el estado de Pinia
import { useAuthStore } from './store/auth';
const authStore = useAuthStore();
console.log('Auth State:', authStore.$state);
```

### Debugging

#### Console Logging
```javascript
// Agregar logs para debugging
console.log('Component mounted:', this.$options.name);
console.log('Props received:', props);
console.log('API Response:', response.data);
```
---

## Conclusión

Este proyecto representa una aplicación web moderna y escalable para la gestión de ventas de productos aromáticos. La arquitectura basada en Vue.js 3, Pinia y microservicios proporciona una base sólida para el crecimiento futuro.

---

*Documentado: [19/06/2025]*
*Versión del proyecto: 1.0.0* 