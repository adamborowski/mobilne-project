export default class ListController {
    constructor($http) {
        this.state = "login";
        this.rest = "/resources/";
        this.howMany = 1;
        this.message = "Siema";
        this.items = [];
        this.$http = $http;
        this.online = false;
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
                this.items = result.data;
            }
        )
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
            if(this.items[i].name == name) {
                this.items[i].sum += sum;
                this.items[i].delta += sum;
                found = true;
                break;
            }
        }
        if(!found)
        {
            this.items.push({
                name: name,
                sum: sum,
                delta: sum
            });
        }
    }

    remove(item) {
        this.updateLocalStore(item, -item.sum + item.delta);
    }

    updateLocalStore(item, newDelta) {
        var addChange = newDelta - item.delta;
        item.sum += addChange;
        item.delta = newDelta;
        if (this.online) {
            this.sync();
        }
    }

    sync() {
        console.table(this.items);
        var data = {
            deviceId: this.deviceId,
            deltas:this.items
        };
        this.$http.put(this.rest, data).then(result=> {
                this.items = result.data;
            }
        )

    }
}
