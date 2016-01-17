import "./main.html"

import angular from "angular"
import ListController from "list/ListController.js"
import ItemController from "list/ItemController.js"

jQuery.extend({

    getQueryParameters : function(str) {
        return (str || document.location.search).replace(/(^\?)/,'').split("&").map(function(n){return n = n.split("="),this[n[0]] = n[1],this}.bind({}))[0];
    }

});

angular
    .module('app', [])
    .controller('ListController', ListController)
    .controller('ItemController', ItemController)
;

