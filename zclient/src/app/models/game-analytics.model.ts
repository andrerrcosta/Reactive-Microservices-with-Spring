import { Point } from "../utils/point.model";

export class GameAnalyticsModel {

  id: String;
  userId: String;
  state: String;
  fullWave: ReportObjectPoint[]
  domains: ReportObjectDomain[];

  constructor() {
    this.id = "";
    this.userId = "";
    this.state = "";
    this.domains = [];
    this.fullWave = [];
  }
}

export class ReportObjectDomain {

  domain: Point;
  values: number[];

  constructor() {
    this.domain = new Point(0, 0, 0);
    this.values = [];
  }
}

export interface ReportObjectPoint {
  index: number;
  timestamp: number;
  x: number;
  y: number;
}
