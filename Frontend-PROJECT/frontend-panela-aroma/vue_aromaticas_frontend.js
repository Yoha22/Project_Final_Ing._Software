// Proyecto Vue 3 - Frontend para "Panela, Aroma y Sabor"

// 1. Estructura de carpetas y archivos

src/
├── assets/
│   └── logo.png
├── components/
│   ├── HeaderNav.vue
│   ├── ProductCard.vue
│   ├── ProductList.vue
│   ├── CartSummary.vue
│   ├── LoginForm.vue
│   └── RegisterForm.vue
├── pages/
│   ├── Home.vue
│   ├── Products.vue
│   ├── Login.vue
│   └── Register.vue
├── router/
│   └── index.js
├── services/
│   ├── ProductService.js
│   ├── AuthService.js
│   └── ClientService.js
├── store/
│   └── cart.js
├── App.vue
└── main.js


// main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.mount('#app');


// App.vue
<template>
  <HeaderNav />
  <router-view />
</template>

<script>
import HeaderNav from './components/HeaderNav.vue';
export default {
  components: { HeaderNav }
};
</script>


// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../pages/Home.vue';
import Products from '../pages/Products.vue';
import Login from '../pages/Login.vue';
import Register from '../pages/Register.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/products', component: Products, meta: { requiresAuth: true } },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.meta.requiresAuth && !token) {
    next('/login');
  } else {
    next();
  }
});

export default router;


// components/HeaderNav.vue
<template>
  <nav>
    <a href="/">Inicio</a>
    <a href="/products">Productos</a>
    <a href="/login">Iniciar sesión</a>
    <a href="/register">Registrarse</a>
  </nav>
</template>

<script>
export default {
  name: 'HeaderNav'
};
</script>


// components/ProductCard.vue
<template>
  <div class="product">
    <h4>{{ product.name }}</h4>
    <p>{{ product.description }}</p>
    <p>$ {{ product.price }}</p>
    <button @click="addToCart">Añadir al carrito</button>
  </div>
</template>

<script>
import { useCartStore } from '../store/cart';

export default {
  props: ['product'],
  setup(props) {
    const cart = useCartStore();
    const addToCart = () => cart.addToCart(props.product);
    return { addToCart };
  }
};
</script>


// components/ProductList.vue
<template>
  <div class="product-list">
    <ProductCard v-for="p in products" :key="p.id" :product="p" />
  </div>
</template>

<script>
import ProductCard from './ProductCard.vue';
import ProductService from '../services/ProductService';

export default {
  components: { ProductCard },
  data() {
    return { products: [] };
  },
  async created() {
    const res = await ProductService.getAll();
    this.products = res.data;
  }
};
</script>


// components/CartSummary.vue
<template>
  <div>
    <h3>Resumen del carrito</h3>
    <ul>
      <li v-for="item in cart.items" :key="item.id">
        {{ item.name }} ({{ item.quantity }}) - ${{ item.price * item.quantity }}
        <button @click="remove(item.id)">x</button>
      </li>
    </ul>
    <p>Total: ${{ cart.total }}</p>
    <button @click="checkout">Finalizar pedido</button>
  </div>
</template>

<script>
import { useCartStore } from '../store/cart';

export default {
  setup() {
    const cart = useCartStore();

    const remove = (id) => {
      cart.removeFromCart(id);
    };

    const checkout = () => {
      alert('Pedido enviado. Implementa la lógica de orden aquí.');
      cart.clearCart();
    };

    return { cart, remove, checkout };
  }
};
</script>


// components/LoginForm.vue
<template>
  <form @submit.prevent="login">
    <div>
      <label>Email:</label>
      <input v-model="email" type="email" required />
    </div>
    <div>
      <label>Contraseña:</label>
      <input v-model="password" type="password" required />
    </div>
    <button type="submit">Iniciar sesión</button>
    <p v-if="error">{{ error }}</p>
  </form>
</template>

<script>
import AuthService from '../services/AuthService';

export default {
  data() {
    return {
      email: '',
      password: '',
      error: ''
    };
  },
  methods: {
    async login() {
      try {
        const res = await AuthService.login({
          email: this.email,
          password: this.password
        });
        localStorage.setItem('token', res.data.token);
        this.$router.push('/products');
      } catch (err) {
        this.error = 'Credenciales incorrectas';
      }
    }
  }
};
</script>


// components/RegisterForm.vue
<template>
  <form @submit.prevent="register">
    <div>
      <label>Nombre:</label>
      <input v-model="name" required />
    </div>
    <div>
      <label>Email:</label>
      <input v-model="email" type="email" required />
    </div>
    <div>
      <label>Contraseña:</label>
      <input v-model="password" type="password" required />
    </div>
    <button type="submit">Registrarse</button>
    <p v-if="error">{{ error }}</p>
  </form>
</template>

<script>
import AuthService from '../services/AuthService';

export default {
  data() {
    return {
      name: '',
      email: '',
      password: '',
      error: ''
    };
  },
  methods: {
    async register() {
      try {
        const res = await AuthService.register({
          name: this.name,
          email: this.email,
          password: this.password
        });
        localStorage.setItem('token', res.data.token);
        this.$router.push('/products');
      } catch (err) {
        this.error = 'Error al registrar';
      }
    }
  }
};
</script>


// pages/Home.vue
<template>
  <div class="home">
    <h1>Bienvenido a Panela, Aroma y Sabor</h1>
    <p>Descubre nuestras aromáticas artesanales.</p>
  </div>
</template>


// pages/Products.vue
<template>
  <div>
    <ProductList />
    <CartSummary />
  </div>
</template>

<script>
import ProductList from '../components/ProductList.vue';
import CartSummary from '../components/CartSummary.vue';

export default {
  components: { ProductList, CartSummary }
};
</script>


// pages/Login.vue
<template>
  <div>
    <h2>Iniciar sesión</h2>
    <LoginForm />
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';
export default {
  components: { LoginForm }
};
</script>


// pages/Register.vue
<template>
  <div>
    <h2>Registrarse</h2>
    <RegisterForm />
  </div>
</template>

<script>
import RegisterForm from '../components/RegisterForm.vue';
export default {
  components: { RegisterForm }
};
</script>


// services/ProductService.js
import axios from 'axios';
const API = 'http://localhost:8080/api/products';

const token = localStorage.getItem('token');

const axiosInstance = axios.create({
  baseURL: API,
  headers: {
    Authorization: token ? `Bearer ${token}` : ''
  }
});

export default {
  getAll() {
    return axiosInstance.get();
  },
  add(product) {
    return axiosInstance.post('', product);
  }
};


// services/AuthService.js
import axios from 'axios';
const API = 'http://localhost:8080/api/auth';

export default {
  login(credentials) {
    return axios.post(`${API}/login`, credentials);
  },
  register(data) {
    return axios.post(`${API}/register`, data);
  }
};
