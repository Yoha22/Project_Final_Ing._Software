import { defineStore } from 'pinia';
import AuthService from '../services/AuthService';

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
    async login(credentials) {
      try {
        const response = await AuthService.login(credentials);
        this.token = response.data.token;
        this.user = response.data.user;
        this.isAuthenticated = true;
        localStorage.setItem('token', this.token);
        localStorage.setItem('userRole', this.user.role);
        return response;
      } catch (error) {
        throw error;
      }
    },
    
    async register(userData) {
      try {
        const response = await AuthService.register(userData);
        this.token = response.data.token;
        this.user = response.data.user;
        this.isAuthenticated = true;
        localStorage.setItem('token', this.token);
        localStorage.setItem('userRole', this.user.role);
        return response;
      } catch (error) {
        throw error;
      }
    },
    
    async logout() {
      try {
        await AuthService.logout();
      } catch (error) {
        console.error('Logout error:', error);
      } finally {
        this.token = null;
        this.user = null;
        this.isAuthenticated = false;
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
      }
    },
    
    async fetchUser() {
      try {
        const response = await AuthService.getProfile();
        this.user = response.data;
        this.isAuthenticated = true;
        return response;
      } catch (error) {
        this.logout();
        throw error;
      }
    },
    
    initializeAuth() {
      if (this.token) {
        this.fetchUser();
      }
    }
  }
}); 