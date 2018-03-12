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
            axios.get('/key/k1/2').then(response => {
                this.key1 = response.data[0];
                this.key2 = response.data[1];
            });
        }
    }
})
