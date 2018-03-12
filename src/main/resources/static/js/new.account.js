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


function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();
    document.body.removeChild(element);
}

$('#save_key').click(function(){
    var k1 = $('#key_active').text();
    var k2 = $('#key_owner').text();
    var text = "Active\n" + k1 + "\n\nOwner\n" + k2;
    download("eos.wallet.key.txt", text);

    $('#next_key').removeClass("disabled");
    $('#next_key').removeClass("btn-secondary");
    $('#next_key').addClass("btn-success");
});

$('#new_account').click(function(){
    var k1 = $('#key_active').text();
    var k2 = $('#key_owner').text();
    var name = $('#account_name').val();

    axios.get('/testnet1/new/account/' + name + "/" + k2 + "/" + k1).then(response => {
        alert(response);
    });
});