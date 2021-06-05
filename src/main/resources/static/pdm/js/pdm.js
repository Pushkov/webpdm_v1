

$(function(){

    // $('.a_jqui').button().click(function(e){
    //     e.preventDefault();
    //     alert('Виджет button');
    // });

    $('.button_jqui').button();

    $( "#filter_office" ).selectmenu();

    $("#accordion").accordion({active: false, collapsible:true});

    $("#dialog1").dialog({autoOpen:false,buttons:{
            OK:function(){
                $(this).dialog("close");
                alert("Вы нажали ОК.");}}
    });
    $("#dialog2").dialog({autoOpen:false,buttons:{
            OK:function(){
                $(this).dialog("close");
                alert("Вы нажали ОК.");},
            Отмена:function(){
                $(this).dialog("close");
                alert("Вы нажали Отмена.");}}
    });
    $("#but1").click(function(){
        $("#dialog1").dialog("open");
    });
    $("#but2").click(function(){
        $("#dialog2").dialog("open");
    });

});
