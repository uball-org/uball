$(document).ready(function(){

    //THIS FUNCTION IN MATERIALIZE HAS NOT BEEN UPDATED IN THEIR DOCS. THEY DID AWAY WITH THE JQUERY VERSION OF THE CODE BELOW.
    // $('.modal').modal();
    // $(".dropdown-trigger").dropdown({ hover: false });


    //THIS CODE SHOULD BE USED IN PLACE IN ORDER TO UPDATE THE CODE AND HAVE A WORKING NAV BAR WITH DROP DOWN FUNCTIONALITY.
    var dropdowns = document.querySelectorAll('.dropdown-trigger');
    for (var i = 0; i < dropdowns.length; i++){
        M.Dropdown.init(dropdowns[i]);
    }


});