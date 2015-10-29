const ORGANIZACJA_URL = "/google/spreadsheets/d/1bxwCKnfeFvco3FDRJuqGiJ8VxyYUpbkC1jQV7h5yZ7g/pubhtml?gid=0&single=true";
const POTWIERDZENIA_URL = "/google/spreadsheets/d/1u7111G23sBasrOyxcqdXQqnTSQw2Byzjkn9os0ZsmRc/pubhtml?gid=0&single=true";
import jQuery from "jQuery";
var $ = jQuery;
$.get(ORGANIZACJA_URL, (response)=> {
    var parser = new Parser(response, 3, 2);
    var coluns = parser.getColumns();
});

class Parser {
    constructor(contentString, headerCount, nameHeader) {
        this.content = $(contentString);
        this.headerCount = headerCount;
        this.nameHeader = nameHeader;

    }

    getColumns() {
        var els = this.content.find('table tr')[this.nameHeader];
        var names = $(els).find('td').get().map(a=>a.innerText).filter(a=>a.trim() != "");
        debugger
    }
}