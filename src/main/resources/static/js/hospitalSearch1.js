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
                            text: item.name + " - " + item.address,
                            value: item.name + " - " + item.address
                        }
                    }));
                }
            });
        },
        autoFocus: true,
        minLength: 0,
        select: function( event, ui ) {
            debugger;
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
                        $("#hospital_id").val("");
                        return {
                            id: item.id,
                            text: item.name,
                            value: item.name + " - " + item.address,
                            name: item.name,
                            address: item.address
                        }
                    }));
                }
            });
        },
        autoFocus: true,
        minLength: 0,
        select: function( event, ui ) {
            $("#hospital_id").val(ui.item.id);
            $("#hospital_name").val(ui.item.name);
            $("#hospitalAddress").val(ui.item.address);
            document.querySelectorAll('.form-outline').forEach((formOutline) => {
                new mdb.Input(formOutline).update();
            });
        },
        open: function() {
            $(this).autocomplete('widget').css('z-index', 1051000);
            return false;
        }
    });
});
