import "./main.html"

import angular from "angular"
import ListController from "list/ListController.js"
import ItemController from "list/ItemController.js"

angular
    .module('app', [])
    .controller('ListController', ListController)
    .controller('ItemController', ItemController)
;

