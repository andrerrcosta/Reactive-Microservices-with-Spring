import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { isValid } from 'src/app/utils/optional';
import { GameAnalyticsModel } from './../../models/game-analytics.model';
import { SystemService } from './../../services/system.service';

@Component({
  selector: 'app-game-report',
  templateUrl: 'game-report.component.html',
  styleUrls: ['game-report.component.css'],
})
export class GameReportComponent implements OnInit, OnDestroy {

  data: ChartModelSet[];
  subscription: Subscription;
  config: ChartConfig;
  mapper: ChartMapper;
  currentUser: UserModel;

  constructor(private system: SystemService) {
    this.data = [];
    this.mapper = new ChartMapper();
    this.subscription = new Subscription();
    this.config = new ChartConfig();
    this.currentUser = system.currentUser;
  }

  ngOnInit() {
    this.system.getUserAnalytics().subscribe((analytics) => {
      analytics.forEach((data) => {
        console.log('game-analysis', data);
        this.data.push(this.mapper.map(data));
      });
      console.log("Data Mapped", this.data);
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onSelect(event: any): void {
    console.log(event);
  }
}

class ChartConfig {
  view: number[] = [];
  scheme: any = {
    domain: ['#2e2e61', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
  };
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Mouse Position X';
  yAxisLabel: string = 'Mouse Position Y';
  timeline: boolean = true;

  constructor() {}
}

class ChartMapper {
  constructor() {}

  map(model: GameAnalyticsModel): ChartModelSet {
    let output: ChartModelSet = new ChartModelSet();

    let fullWave = new ChartModel();
    fullWave.setName(`Full Wave ${model.id}`);

    model.fullWave.forEach(point => {
      fullWave.addSerie(point.y, String(point.x))
    });

    model.domains.forEach((domain) => {
      let serie = new ChartModel();
      serie.setName(`Wave ${model.id} domain of Point(${domain.domain.x}, ${domain.domain.y})`);
      domain.values.forEach((value) => {
        let point = model.fullWave.find(p => p.index === value);
        if(point) {
          serie.addSerie(point.y, String(point.x));
        } else {
          console.error("Error. Domain point was not found on the wave");
        }
      });
      output.addDomain(serie);
    });


    output.setFullWave(fullWave);
    return output;
  }

}

class ChartModel {
  name: string = '';
  series: { value: number; name: string }[] = [];

  constructor() {}

  setName(name: string) {
    this.name = name;
    return this;
  }

  addSerie(value: number, name: string) {
    this.series.push({ value: value, name: name });
    return this;
  }

  stringify(): String {
    return JSON.stringify(this);
  }
}

class ChartModelSet {

  domains: ChartModel[];
  fullWave: ChartModel | undefined;

  constructor() {
    this.domains = [];
  }

  addDomain(...domains: ChartModel[]) {
    domains.forEach(domain => this.domains?.push(domain));
  }

  setDomains(domains: ChartModel[]) {
    this.domains = domains;
    return this;
  }

  setFullWave(fullWave: ChartModel) {
    this.fullWave = fullWave;
    return this;
  }

}
