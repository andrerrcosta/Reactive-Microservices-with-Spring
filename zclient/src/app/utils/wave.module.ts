import { getElement } from 'src/app/utils/dom.functions';
import { Undefined } from 'src/app/utils/optional';
import { Point } from './point.model';

export class WaveMapper {
  private wave: Wave;

  constructor(userId: String) {
    this.wave = new Wave(userId);
  }

  store(target: Point, timestamp: number): void {
    this.wave.store(target, timestamp);
  }

  getWave(userId: String): Wave {
    let z = this.wave.points[this.wave.points.length - 1].z;
    let action = z === -1 ? "End Game" : z === 1 ? "Score" : "Fail";
    return this.wave.withUserId(userId)
      .withAction(action);
  }

  clear(): void {
    this.wave = new Wave();
  }
}

export class Wave {
  userId: String;
  points: WavePoint[];
  action: String;

  constructor(userId?: String) {
    this.userId = Undefined(userId, "");
    this.points = [];
    this.action = '';
  }

  store(point: Point, timestamp: number): void {
    this.points.push(new WavePoint(this.index, point, timestamp));
  }

  withUserId(userId: String) {
    return new Wave()
      .setUserId(userId)
      .setWavePoints(this.points)
      .setAction(this.action);
  }

  withPoints(wavePoints: WavePoint[]) {
    return new Wave()
    .setUserId(this.userId)
    .setWavePoints(wavePoints)
    .setAction(this.action);
  }

  withAction(action: String) {
    return new Wave()
      .setUserId(this.userId)
      .setWavePoints(this.points)
      .setAction(action);
  }

  private get index(): number {
    return this.points.length;
  }

  private setUserId(userId: String) {
    this.userId = userId;
    return this;
  }

  private setAction(action: String) {
    this.action = action;
    return this;
  }

  private setWavePoints(points: WavePoint[]) {
    this.points = points;
    return this;
  }
}

export class WavePoint {
  index: number;
  x: number;
  y: number;
  z: number;
  timestamp: number;

  constructor(index: number, target: Point, timestamp: number) {
    this.index = index;
    this.x = target.x;
    this.y = target.y;
    this.z = Undefined(target.z, 0);
    this.timestamp = timestamp;
  }
}
