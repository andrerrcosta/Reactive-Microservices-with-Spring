export class Point {
  x: number;
  y: number;
  z?: number;

  constructor(x?: number, y?: number, z?: number) {
    this.x = x ? x : 0;
    this.y = y ? y : 0;
    this.z = z;
  }

  WithX(x: number): Point {
    return new Point(x, this.y, this.z);
  }

  WithY(y: number): Point {
    return new Point(this.x, y, this.z);
  }

  WithZ(z: number): Point {
    return new Point(this.x, this.y, z);
  }
}
