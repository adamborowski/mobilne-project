export default class ListController {
    constructor($http) {

        this.rest = "/resources/";
        this.howMany = 1;
        this.message = "Siema";
        this.items = [];
        this.$http = $http;
    }

    login(username, password){
        this.username = username;
        this.password = password;
        this.$http.defaults.headers.common['Authorization'] = btoa(username + ":" + password)
        this.getList();
    }

    getList() {
        this.$http.get(this.rest).then(result=> {
                this.items = result.data;
            }
        )
    }


    add(item, howMany) {
        this.$http.put(this.rest + item.name, {
            name: item.name,
            count: item.count + howMany
        }).then(result=> {
            item.count += howMany
        });
    }

    sub(item, howMany) {
        this.$http.put(this.rest + item.name, {
            name: item.name,
            count: item.count - howMany
        }).then(result=> {
            item.count -= howMany
        });
    }

    create(name, count) {
        this.$http.post(this.rest, {
            name: name,
            count: count
        }).then(result=> {
            this.items.push({
                name: name,
                count: count
            });
        });

    }

    remove(item) {
        this.$http.delete(this.rest + item.name).then(request=> {
            this.items.splice(this.items.indexOf(item), 1);
        });
    }
}
