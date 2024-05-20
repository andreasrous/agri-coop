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
  <div class="signin">
    <h1>Login</h1>
    <div class="content">
      <div class="spinner-border" role="status" v-if="loading">
        <span class="visually-hidden">Loading...</span>
      </div>
      <form class="form" v-else>
        <div class="mb-2" v-if="authenticationFailed">
          <!--
              @EXERCISE: Be more specific.
              E.g., user does not exist, credentials are not valid, etc.
              Always consider security, i.e., sometimes you may not want to unveil information.
              -->
          <div class="alert alert-danger" role="alert">
            Authentication failed!
          </div>
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
          <input @click="onFormSubmit" type="submit" value="Login">
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-family: 'Inter', sans-serif;
}

.signin {
  position: absolute;
  transform: translate(-50%, -50%);
  top: 50%;
  left: 50%;
  width: 400px;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 5px 80px rgba(0, 0, 0, 0.1);
}

.signin h1 {
  padding: 10px;
  color: #fff;
  background: #42b682;
  text-align: center;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.signin .content {
  padding: 20px;
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 5px;
  /* gap: 40px; */
}

.signin .content .form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.signin .content .form .inputBox {
  position: relative;
  width: 100%;
}

.signin .content .form .inputBox input {
  position: relative;
  width: 100%;
  /* background: #eee; */
  border: none;
  outline: none;
  padding: 25px 10px 7.5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-weight: 300;
  font-size: 1em;
  padding: 12px 15px;
}

.signin .content .form .links {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.signin .content .form .links a {
  color: #42b682;
  text-decoration: none;
  font-weight: 600;
}

.signin .content .form .links a:hover {
  text-decoration: underline;
}

.signin .content .form .inputBox input[type="submit"] {
  background: #027afc;
  color: #fff;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: 0.1s ease-out;
}

.signin .content .form .inputBox input[type="submit"]:hover {
  background: rgba(2, 122, 252, 0.9);
}

.alert-danger {
  color: #e7195a;
}
</style>