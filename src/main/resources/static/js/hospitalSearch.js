$(document).on('focus','#hospital_name',function(){
    $(this).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url : '/searchHospital',
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                method: 'get',
                data: {
                    q: request.term,
                },
                success: function( data ) {
                    response( $.map( data, function( item ) {
                        return {
                            id: item.id,
                            text: item.name,
                            value: item.name
                        }
                    }));
                }
            });
        },
        autoFocus: true,
        minLength: 0,
        select: function( event, ui ) {
            $("#hospital_id").val(ui.item.id);
        }
    });
});