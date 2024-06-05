import { ref } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const store = useApplicationStore();

export function useRemoteData(urlsRef, authRef, methodRef = ref('GET'), bodyRef = ref(null)) {
    const data = ref({});
    const error = ref(null);
    const loading = ref(false);

    const performRequest = async () => {
        loading.value = true;
        error.value = null;

        const headers = {
            'Content-Type': 'application/json'
        };

        if (authRef.value === true) {
            headers['Authorization'] = 'Bearer ' + store.userData.accessToken;
        }
        const config = {
            method: methodRef.value,
            headers: headers
        };

        if (bodyRef.value !== null) {
            config.body = JSON.stringify(bodyRef.value);
        }

        try {
            const responses = await Promise.all(
                urlsRef.value.map((url) => {
                    return fetch(url, config).then((response) => {
                        if (response.ok) {
                            return response.json();
                        } else {
                            throw new Error('Network response was not ok.');
                        }
                    });
                })
            );

            responses.forEach((responseData, index) => {
                data.value[urlsRef.value[index]] = responseData;
            });
        } catch (err) {
            error.value = err;
        } finally {
            loading.value = false;
        }
    };

    return { data, error, loading, performRequest };
}
