<script setup>
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const applicationStore = useApplicationStore();
function openMenu() {
  const header = document.querySelector('header');
  header.classList.toggle('open');
}
</script>

<template>
  <header>
    <div class="logo">
      <img src="../assets/logo.svg">
      <a href="/"><span>agricoop</span></a>
    </div>
    <nav>
      <ul>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'home' }">Home</router-link>
        </li>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'cooperatives' }">Cooperatives</router-link>
        </li>
        <!-- <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'products' }">Products</router-link>
        </li>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'cultivation-locations' }">Cultivation Locations</router-link>
        </li> -->
      </ul>
    </nav>
    <div class="auth">
      <router-link v-if="applicationStore.isAuthenticated === false" :to="{ name: 'login' }">Login</router-link>
      <router-link v-if="applicationStore.isAuthenticated === true" :to="{ name: 'logout' }">Logout</router-link>
    </div>
    <button id="menuButton" @click="openMenu">
      <i class="bx bx-menu"></i>
    </button>
  </header>
</template>