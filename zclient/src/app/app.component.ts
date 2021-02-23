import { SystemService } from './services/system.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'zclient';

  constructor(private system: SystemService) {
    this.system.getUser();
  }
}
