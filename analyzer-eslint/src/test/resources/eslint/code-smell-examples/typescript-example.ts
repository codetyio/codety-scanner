
declare const array: string[];
for (const i in array) {
  console.log(array[i]);
}
for (const i in array) {
  console.log(i, array[i]);
}

//no array delete:
declare const arr: number[];
delete arr[0];

function f(a = 0, b: number) {}
function f(a: number, b = 0, c: number) {}
function f(a: number, b?: number, c: number) {}
class Foo {
  constructor(
    public a = 10,
    private b: number,
  ) {}
}
class Foo {
  constructor(
    public a?: number,
    private b: number,
  ) {}
}