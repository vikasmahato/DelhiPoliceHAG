$(document).ready(function () {
    $('#refer_hospital_name').autocomplete({
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
            $("#refer_hospital_id").val(ui.item.id);
        },
        open: function() {
            $(this).autocomplete('widget').css('z-index', 1051000);
            return false;
        }
    });

    $('#hospital_name').autocomplete({
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
        },
        open: function() {
            $(this).autocomplete('widget').css('z-index', 1051000);
            return false;
        }
    });
});
