$(function () {

    console.log('t');
    // tableHeader();
    tableBody('test_table',designers);
    addSortListenets('test_table');

    addChangeSelectListener('my_filter_office','test_table');
    addChangeSelectListener('my_filter_role','test_table');
});
//********************************************************************************************
function table_clear(id) {
    document.getElementById(id).getElementsByTagName('tbody')[0].innerHTML = '';
}


/*
function tableBody33(list) {
    $("#test_table").append('<tbody>');
    for (var i = 0; i < list.length; i++) {
        var btnId = "btn" + list[i].id;
        var fname = "form" + list[i].id;
        $("#test_table tr:first").after(
            "<tr><form id='" + fname + "' action='/js/" + user.id + "/users/" + list[i].id + "' method='get' >" +
            "<td>" + list[i].id + "</td>" +
            "<td>" + list[i].firstName + "</td>" +
            "<td>" + list[i].lastName + "</td>" +
            "<td>" + list[i].office.name + "</td>" +
            "<td>" + list[i].phone + "</td>" +
            "<td>" + list[i].role + "</td>" +
            "<td>" +
            "<button class='button_jqui' style='font-size: .85em' type='submit' onclick='submit()' " +
            "id='" + btnId + "' " +
            ">bb</abutton></td>" +
            "</form></tr>");
        var sendButton = document.getElementById(btnId);
        sendButton.addEventListener("click", function () {
            var form = document.getElementById(fname);
            form.submit();
        });
    };
    $("#test_table").append('</tbody>');
};
*/

function tableBody(id,list) {
    for (var i = 0; i < list.length; i++) {
        addRow(id, list[i]);
    };
};

function addRow(id, user){
    var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
    var row = document.createElement("TR")
    var keys = [user.login, user.firstName, user.lastName, user.office.name, user.phone, user.role];
    for ( kk of keys){
        var td1 = document.createElement("TD");
        td1.appendChild(document.createTextNode(kk));
        row.appendChild(td1);
    }
    var td2 = document.createElement("TD");
    var btn = document.createElement('button');
    btn.type='submit';
    btn.innerText="Смотреть";
    btn.value = user.id;
    btn.className = "button_jqui";
    // btn.classname = "button_jqui ui-button ui-corner-all ui-widget";
    btn.style = "font-size: .85em";
    btn.addEventListener("click",designerPage);
    td2.appendChild(btn);
    row.appendChild(td2);
    tbody.appendChild(row);

}

function designerPage(event) {
    document.location = "/js/" + user.id + "/users/" + event.target.value;
}

function sortUsers(list, prop, reverse) {
    list.sort((a, b) => a[prop] > b[prop] ? 1 : -1);
    if(reverse){
        list.reverse();
    }
    return list;
}

function addSortListenets(tab_id){
    let myMap = new Map(
        [
            ["user_sort_login", "login"],
            ["user_sort_first", "firstName"],
            ["user_sort_last", "lastName"],
            ["user_sort_office", "office"],
            ["user_sort_phone", "phone"],
            ["user_sort_role", "role"]
        ]);
    myMap.forEach(function(value1, value2, map){
        // console.log(value2, value1);
        addButtonSortListener(value1,value2,tab_id);
    })
}

function addButtonSortListener(prop, button_id, table_id) {
    document.getElementById(button_id).addEventListener("click",function (event) {
        // console.log("sort - " + sessionStorage.getItem("usersort"))
        // console.log("asc - " + sessionStorage.getItem("usersortasc"))

        var field = "f_" + table_id;
        var field_asc = "f_d_" + table_id;
        var reverse = false;

        if(sessionStorage.getItem(field) == null){
            sessionStorage.setItem(field, prop);
            sessionStorage.setItem(field_asc, '1');
        }else {
            if(sessionStorage.getItem(field_asc) === '1'){
                reverse = true;
                sessionStorage.setItem(field_asc, '0');
            }else {
                reverse = false;
                sessionStorage.setItem(field_asc, '1');
            }
        }

        table_clear(table_id);
        tableBody(table_id, sortUsers(designers,prop, reverse));
    })
}

function filterList(list, prop, val) {
    return list.filter( e => e[prop] === val);
}

function addChangeSelectListener(item_id, table_id) {

   document.getElementById(item_id).onchange = function (e){
       var selectedOption = this.options[this.selectedIndex];
       if(selectedOption.value === '-1'){
           table_clear(table_id);
           tableBody(table_id, designers);
       }
       else {
           let newList = [];
           for(des of designers){
               if(item_id === 'my_filter_office'){
                   if(des.office.id == selectedOption.value){
                       newList.push(des);
                   }
               } else
               if(item_id === 'my_filter_role'){
                   if(des.role == selectedOption.value){
                       newList.push(des);
                   }
               } else {

               }


           }
           table_clear(table_id);
           tableBody(table_id, newList);
       }
   }
}
