<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { storeToRefs } from 'pinia';
import { useRemoteData } from '@/composables/useRemoteData.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const router = useRouter();
const route = useRoute();

const applicationStore = useApplicationStore();
const { userData } = storeToRefs(applicationStore);

const userIdRef = ref(null);
const cooperativeIdRef = ref(null);
const formDataRef = ref({
    notes: ''
});
const urlsRef = ref([]);
const authRef = ref(true);
const methodRef = ref('GET');

const { data, error, loading, performRequest } = useRemoteData(urlsRef, authRef, methodRef);

onMounted(() => {
    userIdRef.value = userData.value.id;
    cooperativeIdRef.value = route.params.id;
    urlsRef.value = [`${backendEnvVar}/api/user/cooperative/${cooperativeIdRef.value}/check`];
    performRequest();
});

const checkResult = computed(() => {
    const url = `${backendEnvVar}/api/user/cooperative/${cooperativeIdRef.value}/check`;
    if (data.value[url] && data.value[url].checkResult) {
        return data.value[url].checkResult.split(',').filter((result) => result.trim() !== '');
    }
    return [];
});

const isGreenText = computed(() => {
    const validMessages = [
        'Application already approved.',
        'Application already rejected.',
        'Application is valid.'
    ];
    return checkResult.value.some((result) => validMessages.includes(result));
});

const approveUrl = ref([]);
const rejectUrl = ref([]);
const submitMethod = ref('POST');

const { performRequest: approveForm } = useRemoteData(
    approveUrl,
    authRef,
    submitMethod,
    formDataRef
);
const { performRequest: rejectForm } = useRemoteData(rejectUrl, authRef, submitMethod, formDataRef);

const onApprove = async () => {
    approveUrl.value = [
        `${backendEnvVar}/api/user/${userIdRef.value}/approve/${cooperativeIdRef.value}`
    ];
    await approveForm();
    router.push({ name: 'cooperatives', params: { id: userData.value.id } });
};

const onReject = async () => {
    rejectUrl.value = [
        `${backendEnvVar}/api/user/${userIdRef.value}/reject/${cooperativeIdRef.value}`
    ];
    await rejectForm();
    router.push({ name: 'cooperatives', params: { id: userData.value.id } });
};
</script>

<template>
    <div class="container">
        <div class="create">
            <h2>Validation Check</h2>
            <div class="content">
                <div class="loader" v-if="loading">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <form class="form" v-else>
                    <div v-if="error">{{ error.message }}</div>
                    <div v-if="checkResult.length > 0">
                        <ul :class="isGreenText ? 'green-text' : 'red-text'">
                            <li v-for="(result, index) in checkResult" :key="index">
                                {{ result }}
                            </li>
                        </ul>
                    </div>
                    <div class="inputBox">
                        <label for="notes">Notes:</label>
                        <textarea
                            v-model="formDataRef.notes"
                            id="notes"
                            class="short-border"
                            required
                        ></textarea>
                    </div>
                    <div class="inputBox button-group">
                        <input
                            @click="onApprove"
                            id="approve-button"
                            class="short-border"
                            type="button"
                            value="Approve"
                        />
                        <input
                            @click="onReject"
                            id="reject-button"
                            class="short-border"
                            type="button"
                            value="Reject"
                        />
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/form.css';
</style>
