<script setup>
// @EXERCISE: If user is authenticated redirect to the requested URL.
// @EXERCISE: If user is not authenticated, keep the requested URL and after a successful authentication redirect to the requested resource.
import { onBeforeMount, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const { setUserData, persistUserData, isAuthenticated } = useApplicationStore();

const loading = ref(false);
const credentials = ref({
  username: '',
  password: ''
});
const authenticationFailed = ref(false);

const onFormSubmit = () => {
  loading.value = true;
  authenticationFailed.value = false;

  fetch(`${backendEnvVar}/api/auth/signin`, {
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
        authenticationFailed.value = true;
      }
    })
    .catch((err) => {
      console.warn(err);
      authenticationFailed.value = true;
    })
    .finally(() => {
      loading.value = false;
    });
};

onBeforeMount(() => {
  if (isAuthenticated === true) {
    router.push({ name: 'home' });
  }
});
</script>

<template>
  <div class="container">
    <div class="login">
      <h1>Login</h1>
      <div class="content">
        <div class="loader" v-if="loading">
          <span class="visually-hidden">Loading...</span>
        </div>
        <form class="form" v-else>
          <div class="alert-danger" v-if="authenticationFailed">
            Authentication failed!
          </div>
          <div class="inputBox">
            <input v-model="credentials.username" type="text" placeholder="Email or Username" required>
          </div>
          <div class="inputBox">
            <input v-model="credentials.password" type="password" placeholder="Password" required>
          </div>
          <div class="links">
            <span>Don't have an account?</span> <router-link to="/signup">Sign Up</router-link>
          </div>
          <div class="inputBox">
            <input @click="onFormSubmit" type="button" value="Login">
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '../assets/form.css';
</style>