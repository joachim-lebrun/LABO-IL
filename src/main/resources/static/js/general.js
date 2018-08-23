$(document).ready(function () {
    $('.modal').modal();
    $('.dropdown-trigger').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrain_width: true,
            hover: false,
            gutter: 0,
            belowOrigin: false
        }
    );
});

document.addEventListener('DOMContentLoaded', function () {
    let elems = document.querySelectorAll('.sidenav');
    let instances = M.Sidenav.init(elems, {});
});

// Initialize collapsible (uncomment the lines below if you use the dropdown letiation)
// let collapsibleElem = document.querySelector('.collapsible');
// let collapsibleInstance = M.Collapsible.init(collapsibleElem, options);

// Or with jQuery

$(document).ready(function () {
    $('.sidenav').sidenav();
});

M.AutoInit();

$('.carousel.carousel-slider').carousel({
    fullWidth: true,
    indicators: true
});

document.addEventListener('DOMContentLoaded', function () {
    let elems = document.querySelectorAll('.datepicker');
    let instances = M.Datepicker.init(elems, {
        format: "d/mm/yyyy"
    });
});


const refreshHistory = function (id,citizen) {
    $.get(`/demands/${id}/events?citizen=${citizen}`, function (data, status) {
        let html = "";
        for (event of data) {
            html += `<div class="row">${event.userName} (${event.time}): ${event.comment}</div>`
        }
        console.log(html);
        $('#chatBlock' + id).html(html)
    });
};
const detailsHistory = function (id) {
    $.get("/demands/" + id + "/events/detailed", function (data, status) {
        let html = "";
        for (event of data) {
            html += `<tr><td> ${event.time}</td><td> ${event.userName}</td><td> ${event.comment}</td><td> ${event.status}</td></tr>`
        }
        console.log(html);
        $('#chatBlockD' + id).replaceWith(html)
    });
};
