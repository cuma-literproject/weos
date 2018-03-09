$('#connect').click(function(){
    location.href = '/' + $('#server option:selected').val() + '/wallet';
});

