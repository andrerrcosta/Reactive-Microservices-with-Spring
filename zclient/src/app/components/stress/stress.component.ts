import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { SystemStresserService } from './../../services/stresser.service';

@Component({
  selector: 'app-stress',
  templateUrl: 'stress.component.html',
  styleUrls: ['./stress.component.css'],
})
export class StressComponent implements OnInit {
  logs: String[];
  subscription: Subscription = new Subscription();
  log: String;

  constructor(private systemStresser: SystemStresserService) {
    this.logs = [];
    this.log = "";
  }

  ngOnInit() {}

  start(): void {
    this.systemStresser.start((res: any) => {
      this.handleLog(res);
    })
  }

  private handleLog(log: any) {
    console.log("log", log)
    this.log += log.data + "\n";
  }
}
