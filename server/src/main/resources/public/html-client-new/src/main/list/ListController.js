export default class ListController {
    constructor($http) {
        this.state = "login";
        this.rest = "/resources/";
        this.howMany = 1;
        this.message = "Siema";
        this.items = [];
        this.$http = $http;
        this.online = false;
        this.itemByGuid = {};
    }

    guid() {
        return Math.random().toString();
    }

    login(username, password, deviceId) {
        this.username = username;
        this.password = password;
        this.deviceId = deviceId;
        this.$http.defaults.headers.common['Authorization'] = "Basic " + btoa(username + ":" + password)
        this.getList();
        this.state = "list";
    }

    goHome() {
        this.state = "login";
    }

    getList() {
        this.$http.get(this.rest + "?deviceId=" + this.deviceId).then(result=> {
            this.initItems(result.data.resources);
        });
    }

    initItems(items) {
        this.items = items;
        for (var i = 0; i < items.length; i++) {
            this.itemByGuid[items[i].id] = items[i]
        }
    }


    add(item, howMany) {
        this.updateLocalStore(item, item.delta + howMany);

    }

    sub(item, howMany) {
        this.updateLocalStore(item, item.delta - howMany);
    }

    create(name, sum) {
        var found = false;
        for(var i=0;i<this.items.length;i++){
            if (this.items[i].name == name && this.items[i].deleteRequested != true) {
                //why create new item where there is an item of that name and is not to delete?
                if(this.items[i].sum <0){
                    sum-=this.items[i].sum;
                }
                this.items[i].sum += sum;
                this.items[i].delta += sum;
                delete this.items[i].deleteRequested;
                found = true;
                break;
            }
        }
        if(!found)
        {
            var item = {
                id: this.guid(),
                name: name,
                sum: sum,
                delta: sum,
                phantom: true,
                createRequested: true
            };
            this.items.push(item);
            this.itemByGuid[item.id] = item;

        }
        this.syncIfOnline();
    }

    remove(item) {
        item.deleteRequested = true;
        this.syncIfOnline();
    }

    syncIfOnline() {
        if (this.online) {
            this.sync();
        }
    }

    updateLocalStore(item, newDelta) {
        var addChange = newDelta - item.delta;
        item.sum += addChange;
        item.delta = newDelta;
        this.syncIfOnline();
    }

    sync() {
        console.table(this.items);
        var items = [];
        for (var i = 0; i < this.items.length; i++) {
            var item = this.items[i];

            if (~(item.phantom && item.deleteRequested)) {
                //we don't want to send removed phantoms
                items.push(item);
            }
        }

        var data = {
            deviceId: this.deviceId,
            deltas: this.items
        };
        this.errors = [];
        this.$http.put(this.rest, data).then(result=> {
            this.initItems(result.data.resources);
            for (var i = 0; i < result.data.errors.length; i++) {
                var error = result.data.errors[i];
                error.item = this.itemByGuid[error.guid];
                this.errors.push(error);
            }
        });

    }
}
