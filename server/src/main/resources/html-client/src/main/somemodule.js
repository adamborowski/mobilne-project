export function sum(x, y) {
    return x + y;
}
export var pi = 3.141593;

export class Column {
    constructor(config = {sooper: 'dooper', another: 22}) {
        console.log(config.sooper, config.another);
    }
}