var app = new Vue({
    el: '#app',
    created() {
        this.getKey();
    },
    data: {
        key1: '-',
        key2: '-'
    },
    methods: {
        getKey() {
            axios.get('/key/k1').then(response => {
                this.key1 = response.data;
            });
        }
    }
})
