<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const { setUserData, persistUserData } = useApplicationStore();

const loading = ref(false);
const credentials = ref({
  username: '',
  email: '',
  password: ''
});
const invalidCredentials = ref(false);

const onFormSubmit = () => {
  loading.value = true;
  invalidCredentials.value = false;

  fetch(`${backendEnvVar}/api/auth/signup`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials.value)
  })
    .then((response) => {
      if (response.ok) {
        response.json().then((data) => {
          setUserData(data);
          persistUserData();
          router.push({ name: 'home' });
        });
      } else {
        invalidCredentials.value = true;
      }
    })
    .catch((err) => {
      console.warn(err);
      invalidCredentials.value = true;
    })
    .finally(() => {
      loading.value = false;
    });
};
</script>

<template>
  <div class="container">
    <div class="signup">
      <h1>Sign Up</h1>
      <div class="content">
        <div class="loader" v-if="loading">
          <span class="visually-hidden">Loading...</span>
        </div>
        <form class="form" v-else>
          <div class="alert-danger" v-if="invalidCredentials">
            Invalid Credentials!
          </div>
          <div class="inputBox">
            <input v-model="credentials.username" type="text" placeholder="Username" required>
          </div>
          <div class="inputBox">
            <input v-model="credentials.email" type="email" placeholder="Email" required>
          </div>
          <div class="inputBox">
            <input v-model="credentials.password" type="password" placeholder="Password" required>
          </div>
          <div class="links">
            <span>Already have an account?</span> <router-link to="/login">Login</router-link>
          </div>
          <div class="inputBox">
            <input @click="onFormSubmit" type="submit" value="Sign Up">
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '../assets/form.css';
</style>